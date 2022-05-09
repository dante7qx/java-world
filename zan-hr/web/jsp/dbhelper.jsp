<%@ page import="java.io.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.sql.*"%>
<%@ page import="javax.naming.*"%>
<%@ page contentType="text/html;charset=utf-8" %>
<html>
<%!
    String title = "Sql Helper v6.1";
    //String DEFAULT_DSN = "java:comp/env/jdbc/*";
    String DEFAULT_DSN = "bean:dataSource";
    // /home/oracle/Oracle/Middleware/wlserver_10.3/server/lib/consoleapp/consolehelp
%>
<head><title><%=title%></title></head>
<body style='margin:0px 0px 0px 0px; overflow:visible'>

<%!
    String PANEL_STYLE = "border-top: silver 1px solid;font-size: 9pt; font-family: Courier New;  border-collapse:collapse";
    Vector getJndiUnderName(String name) {
        Vector vect = new Vector();
        try {
            Properties p = System.getProperties();
            InitialContext ctx = new InitialContext(p);
            NamingEnumeration ne = ctx.list(name);
            while (ne.hasMore()) {
                NameClassPair ncp = (NameClassPair) ne.next();
                vect.addElement(ncp);
            }
        } catch (Exception e) {
        }
       
        return vect;
    }
    String getJndiDetails(String prefix, Vector vect) {
        if (vect == null || vect.size() < 1) {
            return "";
        }
        StringBuffer buff = new StringBuffer();
        for (int i = 0; vect != null && i < vect.size(); i++) {
            buff.append("<tr nowrap><td><pre style='font-size: 9pt; font-family: Courier New'>");
            try {
                NameClassPair ncp = (NameClassPair) vect.elementAt(i);
                String name = ncp.getName();
                if (ncp.isRelative()) {
                    name = prefix + "/" + name;
                }
                String clz = ncp.getClassName();
                buff.append(name);
                buff.append("->");
                buff.append(clz);
                //buff.append("@");
             //buff.append(clz.getProtectionDomain().getCodeSource().getLocation().getFile());
            } catch (Exception e) {
                buff.append(e.getMessage());
            } finally {
                buff.append("</td></tr>\n");
            }
        }
       
        return buff.toString();
    }
    String getAlign(int type, String l, String c, String r) {
        switch (type) {
            case Types.CHAR :
                return c;
            case Types.VARCHAR :
                return l;
            case Types.DATE :
                return c;
            case Types.DECIMAL :
                return r;
            case Types.DOUBLE :
                return r;
            case Types.FLOAT :
                return r;
            case Types.INTEGER :
                return r;
            case Types.JAVA_OBJECT :
                return l;
            case Types.LONGVARCHAR :
                return l;
            case Types.NULL :
                return l;
            case Types.NUMERIC :
                return r;
            case Types.REAL :
                return r;
            case Types.REF :
                return l;
            case Types.SMALLINT :
                return r;
            case Types.CLOB :
                return l;
            case Types.TIME :
                return c;
            case Types.TIMESTAMP :
                return c;
            case Types.TINYINT :
                return r;
            default :
                return c;
        }
    }
    char[] HexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    String toStr(Object obj, int type) {
        String str = null;
        switch (type) {
            case Types.CLOB : {
                if (obj instanceof Clob) {
                    try {
                        Reader r = ((Clob)obj).getCharacterStream();
                        StringWriter sw = new StringWriter();
                        int line = 0;
                        for (int c = r.read(); c != -1;) {
                            sw.write(c);
                            c = r.read();
                            if (line++ > 80) {
                                sw.write("\n");
                                line = 0;
                            }
                        }
                        str = sw.toString();
                        break;
                    } catch (Exception e) {
                    }
                }
            }
            case Types.CHAR :
            case Types.VARCHAR :
            case Types.DATE :
            case Types.DECIMAL :
            case Types.DOUBLE :
            case Types.FLOAT :
            case Types.INTEGER :
            case Types.JAVA_OBJECT :
            case Types.LONGVARCHAR :
            case Types.NULL :
            case Types.NUMERIC :
            case Types.REAL :
            case Types.REF :
            case Types.SMALLINT :
            case Types.TIME :
            case Types.TIMESTAMP :
            case Types.TINYINT :
            default :
        }
        if (str == null) {
            if (obj instanceof byte[]) {
                byte[] bytes = (byte[]) obj;
                StringBuffer sb = new StringBuffer();
                sb.append("0x");
                int line = 0;
                for (int i=0; i < bytes.length; i++) {
                    sb.append(HexChars[(bytes[i] >> 4) & 0xf]);
                    sb.append(HexChars[bytes[i] & 0xf]);
                    if (line++ > 80) {
                        sb.append("\n");
                        line = 0;
                    }
                }
                return new String(sb);
            } else {
                str = String.valueOf(obj);
            }
        }
        return str.replace("&", "&amp;").replace(">", "&gt;").replace("<","&lt;");
    }
    String[] parseSqls(String str) {
        int base = 0;
        StringBuffer buffer = new StringBuffer();

        for (int idx = str.indexOf("//", 0);
            idx != -1;
            idx = str.indexOf("//", base)) {
            buffer.append(str.substring(base, idx));

            base = str.indexOf("\n", idx);
            if (base == -1) {
                base = str.length();
            }
        }
        buffer.append(str.substring(base));
        for (int i = 0; i < buffer.length(); i++) {
            char c = buffer.charAt(i);
            if (Character.isWhitespace(c)) {
                buffer.setCharAt(i, ' ');
            }
        }
        StringTokenizer st = new StringTokenizer(buffer.toString(), ";");
        String[] ret = new String[st.countTokens()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = st.nextToken();
            ret[i] = ret[i].trim();
        }
        return ret;
    }
    String getDivStyle(int total) {
        return (total == 0) ? "display:block;" : "display:none;";
    }
%>
<%
    StringBuffer sb = new StringBuffer();
    int total = 0;
    Connection conn = null;
    String scrh = request.getParameter("SCRH");
    int height = 768;
    try {
        if (scrh != null) {
            height = Integer.parseInt(scrh);
        }
        if (height > 900) {
            height = 900;
        }
    } catch (Exception e) {
    }
    height = height - 400;
    String scrw = request.getParameter("SCRW");
    int width = 1024;
    try {
        if (scrw != null) {
            width = Integer.parseInt(scrw);
        }
        if (width > 1200) {
            width = 1200;
        }
    } catch (Exception e) {
    }
    int mr = 500;
    String maxr = request.getParameter("MR");
    try {
        if (maxr != null) {
            mr = Integer.parseInt(maxr);
        }
    } catch (Exception e) {
    }
    String style = "";
    try {
        Object enable = application.getAttribute("bp.dbtoolEnable");
        if (!"true".equals(enable)) {
	        com.epolleo.bp.pub.LoginUser cu = com.epolleo.bp.pub.LoginUser.getCurrentUser();
	    	if (cu == null || !"superadmin".equals(cu.getUserId())) {
	    	    throw new IllegalAccessException("You must login as 'superadmin'");
	    	}
        }
        String dsn = request.getParameter("DSN");
        String[] sqla = request.getParameterValues("SQL");
        String sql = null;
        if (sqla != null && sqla.length > 0) {
            sql = sqla[0];
        }
        if (sql == null) {
            sql = "";
        }
        if ("tomcat:reboot".equals(sql)) {
            System.exit(13);
        }
        if (dsn == null || dsn.length() == 0) {
            dsn = (String) session.getAttribute("SqlHelper.DSN");
        } else {
            session.setAttribute("SqlHelper.DSN", dsn);
        }
        if (dsn == null || dsn.length() == 0) {
            dsn = DEFAULT_DSN;
            session.setAttribute("SqlHelper.DSN", dsn);
        }
        String dsns = "";
        if (dsn.endsWith("/**")) {
            String name = dsn.substring(0, dsn.length() - 3);
            session.removeAttribute("SqlHelper.DSNS:" + name);
            dsn = name + "/*";
        }
        if (dsn.endsWith("/*")) {
            String name = dsn.substring(0, dsn.length() - 2);
            dsns = (String) session.getAttribute("SqlHelper.DSNS:" + name);
            if (dsns == null) {
                Vector vect = (Vector) session.getAttribute("SqlHelper.DSN:" + name);
                if (vect == null) {
                    vect = getJndiUnderName(name);
                }
                session.setAttribute("SqlHelper.DSN:" + name, vect);
                dsns = getJndiDetails(name, vect);
                session.setAttribute("SqlHelper.DSNS:" + name, dsns);
            }
        }
%>

	<form method="post" onkeydown="doKeyDown">
		<input type="hidden" name="SCRH" id="SCRH">
		<input type="hidden" name="SCRW" id="SCRW">
        <font face='Courier New'>
        <center>
	        <H5 style='cursor:pointer' onClick='doShow()'><%=title%></H5>
	        <div id="FORM" style='display:none; margin:0px 0px 0px 0px; overflow:visible; width:100%; height:<%=height%>;'>
	        <table border=2 style='font-size: 9pt; font-family: Courier New; border-collapse:collapse'>
	        <tr nowrap>
	        <td><b>DSN:</b></td>
	        <td nowrap><input name=DSN onkeydown=doKeyDown title="java:comp/env/* | bean:dataSource" style='font-family: Courier New; font-weight:bold; color:red' type=text size=<%=width/11%> value='<%=dsn%>'>
	        MR:<input name=MR onkeydown=doKeyDown style='font-family: Courier New; font-weight:bold; color:green' type=text size=<%=width/60%> value='<%=mr%>'>
	        <input value=OK style='font-family: Courier New' type=submit></td>
	        </tr><tr>
	        <td style='cursor:pointer' onClick='doStr()'><b>SQL:</b></td>
	        <td>
	        <div id=STR style='display:block; overflow:auto'>
	        <textarea onkeydown=doKeyDown id=SQL style='font-family: Courier New; font-size: 10pt; color:blue;' name="SQL" cols=<%=width/9 + 2%> rows=<%=height/17%>><%=sql.trim()%></textarea>
	        </div>
	        </td>
	        </tr>
	        </table></div>
        </center>
<%
        if (sql != null && sql.trim().length() > 0 || dsns != null && dsns.length() > 0) {
%>
        <div id="RESULT" style="display:none; margin:0px 0px 0px 0px; overflow:visible; width:100%; height:100%">
        <span id=TAB style='display:none'></span>
        <div id="TABS" style='display:block; margin:0px 0px 0px 0px; overflow:scroll; width:100%;height:<%=height-40%>'>
<%
            String[] sqls = null;
            if (dsns != null && dsns.length() > 0) {
%>
                <div id='TAB<%=total%>' style='<%=getDivStyle(total++)%>'>
                <table border=1 style='<%=PANEL_STYLE%>'>
                <%=dsns%>
                </table></div>
<%
            } else {
                sqls = parseSqls(sql);
            }
            if (sqls != null) {
                if (dsn.startsWith("bean:")) {
                    DataSource ds = (DataSource) com.epolleo.webx.tag.TagUtil.getBean(request, dsn.substring(5));
	                conn = ds.getConnection();
	                conn.setAutoCommit(true);
                } else {
	                Properties p = System.getProperties();
	                InitialContext ctx = new InitialContext(p);
	                DataSource ds = (DataSource) ctx.lookup(dsn);
	                conn = ds.getConnection();
	                conn.setAutoCommit(true);
                }
            }

            for (int idx = 0; sqls != null && idx < sqls.length; idx++) {
                if (idx > 6) {
%>
                    <div id='TAB<%=total%>' style='<%=getDivStyle(total++)%>'>
                    <table border=1 style='<%=PANEL_STYLE%>'>
                    <tr nowrap>
                    <td><b>Too many statements to execute!</b></td>
                    </tr>
                    </table></div>
<%
                    break;
                }
                try {
                    if (sqls[idx].length() == 0) {
                        continue;
                    }
                   
                    PreparedStatement pstat = conn.prepareStatement(sqls[idx]);
                    try {
                        if (mr > 0)
                        	pstat.setMaxRows(mr);
                    } catch (SQLException sqle) {
                        sqle.printStackTrace();
                        mr = 0;
                    }
                    boolean hasRset = pstat.execute();
                    for (boolean b = hasRset; ; b = pstat.getMoreResults()) {
                        if (b) {
                            ResultSet rset = pstat.getResultSet();
                           
                            ResultSetMetaData rsmd = rset.getMetaData();
                            int cols = rsmd.getColumnCount();
%>
                            <div id='TAB<%=total%>' style='<%=getDivStyle(total++)%>'>
                            <table border=1 borderColor=silver style='<%=PANEL_STYLE%>'>
                            <tr nowrap bgcolor=silver>
<%
                            String[] aligns = new String[cols];
                            for (int i = 1; i <= cols; i++) {
                                aligns[i - 1] = getAlign(rsmd.getColumnType(i), "left", "center", "right");
                                out.println("<td align=center nowrap><b>" + String.valueOf(rsmd.getColumnName(i)) + "</b><br>"+ String.valueOf(rsmd.getColumnTypeName(i))+"</td>");
                            }
                            out.println("</tr>");
                            int r = 0;
                            for (; rset.next(); r++) {
                                if (r < mr) {
                                    out.println("<tr nowrap>");
                                    for (int i = 1; i <= cols; i++) {
                                        String content = null;
                                        try {
                                            content = toStr(rset.getObject(i), rsmd.getColumnType(i));
                                        } catch (Exception e) {
                                        }
                                        //if (content.length() < 32) {
                                        //    out.println("<td nowrap align=" + aligns[i - 1] + ">" + content + "</td>");
                                        //} else {
                                        content = content.replace("\r","").replace("\n", "<br>");
                                        out.println("<td nowrap align=" + aligns[i - 1] + ">" + content + "</td>");
                                        //}
                                    }
                                }
                            }
                            if (r >= mr) {
                                out.println("</tr>");
                                out.println("<tr nowrap>");
                                out.println("<td colspan=" + cols + ">" + mr + " rows of total: " + r + ". Too many rows to show!</td>");
                                out.println("</tr>");
                            } else {
                                out.println("</tr>");
                                out.println("<tr nowrap>");
                                out.println("<td colspan=" + cols + ">" + r + " row(s) returned successfully.</td>");
                                out.println("</tr>");
                            }
                            out.println("</table></div>");
                            rset.close();
                        } else {
                            int updCnt = pstat.getUpdateCount();
                            if (updCnt == -1) {
                                break; // no more results.
                            }
%>
                            <div id='TAB<%=total%>' style='<%=getDivStyle(total++)%>'>
                            <table border=1 style='<%=PANEL_STYLE%>'>
                            <tr nowrap>
                            <td><b><%=updCnt%> row(s) updated successfully!</b>
                            </tr>
                            </table></div>
<%
                        }
                    }
                } catch (Throwable e) {
%>
                    <div id='TAB<%=total%>' style='<%=getDivStyle(total++)%>'>
                    <table border=1 borderColor=silver style='<%=PANEL_STYLE%>'>
<%
                    if (e instanceof SQLException) {
%>
                    <tr bgcolor=silver>
                    <td>SQL Code:</td><td><b><%=((SQLException)e).getErrorCode()%></b></td>
                    </tr>
<%
                    }
%>
                    <tr>
                    <td>Error:</td><td><b><%=e.getMessage()%></b></td>
                    </tr>
                    </table></div>
<%
                }
            }
        }
       
    } catch (NamingException e) {
%>
        <div id='TAB<%=total%>' style='<%=getDivStyle(total++)%>'>
        <table border=1 borderColor=silver style='<%=PANEL_STYLE%>'>
        <tr nowrap bgcolor=silver>
        <td><b><%=e.getMessage()%></b></td>
        </tr>
<%
        if (e.getRootCause() != null) {
%>
        <tr nowrap>
        <td><pre style='font-family: Courier New'><%e.getRootCause().printStackTrace(new PrintWriter(out));%></td>
        </tr>
<%
        }
%>
        <tr nowrap>
        <td><pre style='font-family: Courier New'><%e.printStackTrace(new PrintWriter(out));%></td>
        </tr>
        </table></div>
<%
    } catch (Exception e) {
%>
        <div id='TAB<%=total%>' style='<%=getDivStyle(total++)%>'>
        <table border=1 borderColor=silver  style='<%=PANEL_STYLE%>'>
        <tr nowrap bgcolor=silver>
        <td><b><%=e.getMessage()%></b></td>
        </tr>
        <tr nowrap>
        <td><pre style='font-family: Courier New'><%e.printStackTrace(new PrintWriter(out));%></td>
        </tr>
        </table></div>
<%
    } finally {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
        }
        if (total > 0) {
            sb.append("<table border=1 style='border-bottom: silver 1px solid;cursor:pointer; font-size: 9pt; font-family: Courier New; border-collapse:collapse'>");
            sb.append("<tr height=20>");
            for (int i = 0; i < total; i++) {
                if (i == 0) {
                    sb.append("<td id=td"+i+ " onclick=doTab(this) style='background:silver'>&nbsp;Result: " + (i + 1) + "&nbsp;&nbsp;</td>");
                } else {
                    sb.append("<td id=td"+i+ " onclick=doTab(this) style='background:white'>&nbsp;Result: " + (i + 1) + "&nbsp;&nbsp;</td>");
                }
            }
            sb.append("</tr></table>");
            sb.append("<table width=100% height=3 border=0 style='border-top: silver 4px solid;border-collapse:collapse'/>");
        }
    }
%>
</div>
</div>
</font>
</form>
<script language="JavaScript">
var total = <%=total%>;
init();
function init() {
    try {
        document.attachEvent("onkeydown", doKeyDown);
        document.attachEvent("onkeypress", doKeyDown);
    } catch (e) {
    }
    var form = document.getElementById("FORM");
    var result = document.getElementById("RESULT");
    var scrh = document.getElementById("SCRH");
    if (scrh != null) {
        scrh.value = screen.height;
    }
    var scrw = document.getElementById("SCRW");
    if (scrw != null) {
        scrw.value = screen.width;
    }
    if (total > 0) {
        document.getElementById("TAB").innerHTML = "<%=sb.toString()%>";
        document.getElementById("TAB").style.display = "block";
        form.style.display = "none";
        result.style.display = "block";
    } else {
        form.style.display = "block";
        document.getElementById("SQL").focus();
    }
}

function doKeyDown(event) {
    if ((event.keyCode == 13 || event.keyCode == 76) && event.ctrlKey) {
        //alert(window.event.cancelBubble);
        try {
            event.cancelBubble = true;
        } catch (e) {
        }
        event.keyCode = 13;
        var form = document.getElementById("FORM");
        if (form != null && form.style.display == "none") {
            doShow();
            var sql = document.getElementById("SQL");
            if (sql != null) {
                sql.focus();
            }
        } else {
            document.forms[0].submit();
        }
    }
}
function doTab(td) {
    //var tr = td.parentElement;
    //alert(tr);
    var i = 0;
    for (; i < total; i++) {
        var id = "TAB" + i;
        var tdi = document.getElementById("td"+i)
        if (tdi === td) {
            document.getElementById(id).style.display = "block";
            tdi.style.background = "silver";
            //tdi.style.background = "white";
            //tr.cells[i].style.border.collapse = "collapse";
        }
        else {
            document.getElementById(id).style.display = "none";
            tdi.style.background = "white";            
            //tdi.style.background = "silver";
            //tr.cells[i].style.border.collapse = "separate";
        }
    }
}
function doStr() {
    if (document.getElementById("STR").style.display == "block") {
        document.getElementById("STR").style.display = "none";
    } else {
        document.getElementById("STR").style.display = "block";
    }
}
function doShow() {
    var form = document.getElementById("FORM");
    var result = document.getElementById("RESULT");
    if (form != null && result != null) {
        if (form.style.display == "none") {
            form.style.display = "block";
            result.style.display = "none";
        } else {
            form.style.display = "none";
            result.style.display = "block";
            return true;
        }
    }
    return false;
}
</script>

</body>
</html>
