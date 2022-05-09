package com.dante.ldap;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class LdapTest {

	public static void main(String[] args) {
		String url = "ldap://domsrvhk04.HNA.NET:389/";
		String domain = "dc=hna,dc=net";
		String user = "devops";
		String password = "qwe123!@";
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory"); // LDAP 工厂
		env.put(Context.SECURITY_AUTHENTICATION, "simple"); // LDAP访问安全级别
		env.put(Context.PROVIDER_URL, url);
//		env.put(Context.SECURITY_PRINCIPAL, user + "," + domain); // 填DN
		env.put(Context.SECURITY_PRINCIPAL, user); // 填DN
		env.put(Context.SECURITY_CREDENTIALS, password); // AD Password
		
		LdapContext ldapCtx = null;  
	    try {  
	        ldapCtx = new InitialLdapContext(env , null);  
	        NamingEnumeration<?> list = ldapCtx.list("OU=海航集团,DC=HNA,DC=NET");
			while (list.hasMore()) {
				try {
					NameClassPair nc = (NameClassPair)list.next();
					System.out.println(nc.getName());
				} catch (Exception e) {
					continue;
				}
			}
	        System.out.println("Auth Successfully!");
	    } catch (NamingException e) {  
	        e.printStackTrace();  
	    } finally {  
	        if(ldapCtx != null) {  
	            try {  
	                ldapCtx.close();  
	            } catch (NamingException e) {  
	            	e.printStackTrace();
	            }  
	        }  
	    } 
	}

}
