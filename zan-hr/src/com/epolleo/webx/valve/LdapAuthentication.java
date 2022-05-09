package com.epolleo.webx.valve;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.naming.directory.DirContext;

import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapEncoder;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.support.LdapUtils;

public class LdapAuthentication {

    private LdapTemplate ldapTemplate;

    private MessageFormat[] userDnFormat = null;

    private String searchBase = "";

    private MessageFormat searchDnFormat = null;

    private LdapAuthentication(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    public String authenticate(String username, String password) {
        // If DN patterns are configured, try authenticating with them directly
        for (String dn : getUserDns(username)) {
            if (bindWithDn(dn, password)) {
                return username;
            }
        }

        // Otherwise use the configured search object to find the user and
        // authenticate with the returned DN.
        if (searchDnFormat != null) {
            String searchFilter = searchDnFormat.format(new String[] { username });
            String userDnStr = null;
            try {
                userDnStr = (String) ldapTemplate.searchForObject(searchBase, searchFilter, new ContextMapper() {
                    @Override
                    public String mapFromContext(Object obj) {
                        DirContextAdapter context = (DirContextAdapter) obj;
                        return context.getDn().toString();
                    }
                });
            } catch (Exception e) {
                return null;
            }
            if (null != userDnStr && bindWithDn(userDnStr, password)) {
                return username;
            }
        }
        return null;
    }

    private boolean bindWithDn(String userDnStr, String password) {
        BaseLdapPathContextSource ctxSource = (BaseLdapPathContextSource) ldapTemplate.getContextSource();
        DistinguishedName userDn = new DistinguishedName(userDnStr);
        DistinguishedName fullDn = new DistinguishedName(userDn);
        fullDn.prepend(ctxSource.getBaseLdapPath());

        DirContext ctx = null;
        try {
            ctx = ldapTemplate.getContextSource().getContext(fullDn.toString(), password);
            return true;
        } catch (NamingException e) {
            if ((e instanceof org.springframework.ldap.AuthenticationException)
                    || (e instanceof org.springframework.ldap.OperationNotSupportedException)) {
            } else {
                throw e;
            }
        } finally {
            LdapUtils.closeContext(ctx);
        }

        return false;
    }

    public void setUserDnPatterns(String[] dnPattern) {
        userDnFormat = new MessageFormat[dnPattern.length];

        for (int i = 0; i < dnPattern.length; i++) {
            userDnFormat[i] = new MessageFormat(dnPattern[i]);
        }
    }

    protected List<String> getUserDns(String username) {
        if (userDnFormat == null) {
            return Collections.emptyList();
        }

        List<String> userDns = new ArrayList<String>(userDnFormat.length);
        String[] args = new String[] { LdapEncoder.nameEncode(username) };

        synchronized (userDnFormat) {
            for (MessageFormat formatter : userDnFormat) {
                userDns.add(formatter.format(args));
            }
        }

        return userDns;
    }

    public void setSearchBase(String searchBase) {
        this.searchBase = searchBase;
    }

    public void setSearchFilter(String searchFilter) {
        searchDnFormat = new MessageFormat(searchFilter);
    }
}
