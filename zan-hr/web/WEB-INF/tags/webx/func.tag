<%@tag trimDirectiveWhitespaces="true" %>
<%@tag body-content="scriptless" language="java" pageEncoding="UTF-8"%>
<%@tag import="java.util.*"%>
<%@tag import="org.apache.commons.logging.*"%>
<%@tag import="com.epolleo.webx.tag.TagUtil"%>
<%@attribute name="id" required="true"%>

<%!static final Log log = LogFactory.getLog(TagUtil.class);%>
<%--
        <w:func id="bp.role.update">特权内容</w:func>
        
        =>>
        
		特权内容
 --%>
<%
    try {
        Set<String> fSet = (Set<String>) session
            .getAttribute("webx.UserFunctions");
        if (id == null || id.isEmpty() || id.equals("public")
            || fSet.contains(id)) {
            getJspBody().invoke(out);
        }
    } catch (Exception e) {
        log.warn(e.getMessage(), e);
    }
%>