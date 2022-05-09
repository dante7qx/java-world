<%@tag trimDirectiveWhitespaces="true" %>
<%@tag body-content="empty" language="java" pageEncoding="UTF-8"%>
<%@tag import="java.util.*"%>
<%@tag import="com.epolleo.webx.tag.TagUtil"%>
<%@tag import="org.apache.commons.logging.*"%>
<%@tag import="org.ibatis.client.SqlMapClient"%>
<%@attribute name="id" required="true"%>
<%@attribute name="valueField" required="false"%>
<%@attribute name="textField" required="false"%>
<%@attribute name="keyName" required="false"%>
<%@attribute name="valueName" required="false"%>
<%@attribute name="arg1" required="false" rtexprvalue="true"%>
<%@attribute name="arg2" required="false" rtexprvalue="true"%>
<%@attribute name="arg3" required="false" rtexprvalue="true"%>
<%@attribute name="arg4" required="false" rtexprvalue="true"%>
<%!static final Log log = LogFactory.getLog(TagUtil.class);%>
<%--
 data: <w:query2json id="sqlId" keyName="ID" valueName="NAME" arg1="<%=type%>" />
 --%>
<%
    try {
        SqlMapClient sqlc = (SqlMapClient) TagUtil.getBean(request, "sqlMapClient");

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("1", arg1);
        args.put("2", arg2);
        args.put("3", arg3);
        args.put("4", arg4);
        if (valueField == null) {
            valueField = "key";
        }
        if (textField == null) {
            textField = "value";
        }
        if (keyName == null) {
            keyName = "ID";
        }
        if (valueName == null) {
            valueName = "NAME";
        }
        Map map = sqlc.queryForMap(id, args, keyName,
            valueName);
        out.print("[");
        if (map != null) {
            boolean first = true;
            for (Object key : map.keySet()) {
                if (!first) {
                    out.print(",");
                }
                String value = TagUtil.escapeJson(map.get(key));
                key = TagUtil.escapeJson(key);
                out.print("{" + valueField + ":'" + key + "',"
                    + textField + ":'" + value + "'}");
                first = false;
            }
        }
        out.print("]");
    } catch (Exception e) {
        log.warn(e.getMessage(), e);
        out.print("[{key:0,value:'" + id + "未定义?'},]");
    }
%>
