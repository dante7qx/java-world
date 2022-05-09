<?xml version="1.0" encoding="gb2312" standalone="no"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/TR/WD-xsl">
<xsl:template match="/">
<HTML>
 <HEAD>
  	<TITLE>
   		����ģ��Ԥ��
  	</TITLE> 	
 	<style>
	 	<![CDATA[
			body{margin:0px;padding:0px;font-size:12px;text-align:center;}
			h2{font-size:20px;line-height:40px;}
			
			.divbody{width:960px;margin:0px auto;}
			
			table{border-collapse:collapse;width:100%;}
			
			table.tbcontent{border-collapse:collapse;font-size:12px;}
			table.tbcontent td,th{border:1px solid #333333;}
			.trcolor{background-color:#d7e2da;line-height:25px;font-size:13px;}
			
			.tbspace{margin-top:20px;}
			table.tbcontent .subtb td{border:0px;text-align:center;}
			
		]]>
 	</style>
 </HEAD>
 <BODY>
 	<DIV class="divbody">
 	<DIV>
		<H2 ALIGN="CENTER">
			<a name="main">���ݱ�Ŀ¼</a><p/>
		</H2>
	</DIV>
 	<xsl:for-each select="schema">		
		<TABLE cellspacing="0" cellpadding="5" class="tbcontent">
		<THEAD>
			<TR class="trcolor">
				<TH colspan="5"><a>�������б�������</a></TH>
			</TR>
			<TR class="trcolor">
				<TH>���</TH>
				<TH>����</TH>
				<TH>ҵ������</TH>
				<TH>ģ������</TH>
				<TH>��ע��</TH>					
			</TR>
		</THEAD>
		<xsl:for-each select="table" order-by="+ @index">
			<TBODY>
				<TR><xsl:attribute name="style">background-color:<xsl:eval>if(formatindex(childnumber(this),"01")%2==1)"#f2f3dd"</xsl:eval><xsl:eval>if(formatindex(childnumber(this),"01")%2==0)"#E7EBE9"</xsl:eval></xsl:attribute>
			        <TD style="width:30px;"><xsl:eval>formatindex(childnumber(this),"01")</xsl:eval></TD>
			        <TD><a><xsl:attribute name="href">#<xsl:value-of select="@name"/></xsl:attribute><xsl:value-of select="@name"/></a></TD>
					<TD><xsl:value-of select="@title"/></TD>
					<TD><xsl:value-of select="@module"/></TD>
					<TD><xsl:value-of select="@note"/></TD>
		       </TR>
			</TBODY>
		</xsl:for-each>
		</TABLE>				
		<xsl:for-each select="table" order-by="+ @index">				
			<TABLE cellspacing="0" cellpadding="5" class="tbcontent tbspace">
		        <THEAD>
		        <TR class="trcolor">
		        	<TH colspan="9"><a><xsl:attribute name="name">#<xsl:value-of select="@name"/></xsl:attribute><xsl:value-of select="@name"/> (<xsl:value-of select="@title"/>)</a></TH>
		         </TR>
		         <TR class="trcolor">
		          <TH>���</TH>
		          <TH>����</TH>
		          <TH>ҵ������</TH>
		          <TH>����</TH>
		          <TH>����</TH>
		          <TH>����</TH>
		          <TH>���</TH>
		          <TH>�Ƿ�ǿ�</TH>
		          <!-- <TH>Ĭ��ֵ</TH><TH>��Чֵ</TH><TH>queryShow</TH><TH>isGet</TH><TH>readOnlyU</TH><TH>readOnlyI</TH> -->
		          <TH>ҵ��ע</TH>
		         </TR>
		        </THEAD>
		        <TBODY>
		         <xsl:for-each select="column">    
		          <TR><xsl:attribute name="style">background-color:<xsl:eval>if(formatindex(childnumber(this),"01")%2==1)"#f2f3dd"</xsl:eval><xsl:eval>if(formatindex(childnumber(this),"01")%2==0)"#E7EBE9"</xsl:eval></xsl:attribute>
		           <TD style="width:30px;"><xsl:eval>formatindex(childnumber(this),"01")</xsl:eval></TD>
		           <TD><xsl:value-of select="@name"/></TD>
		           <TD style="width:150px;"><xsl:value-of select="@title"/></TD>
		           <TD><xsl:value-of select="@type"/><xsl:if test="@size[.!='']">(<xsl:value-of select="@size"/>)</xsl:if></TD>
		           <TD><xsl:value-of select="@size"/></TD>
		           <TD><xsl:if test="@primaryKey[.='true']"><xsl:value-of select="@primaryKey"/></xsl:if></TD>
		           
		           <TD></TD>
		           
		           <TD><xsl:if test="@required[.='true']"><xsl:value-of select="@required"/></xsl:if></TD>
		           <!--<TD><xsl:value-of select="@default"/></TD>					           
		           <TD><xsl:value-of select="@enumValue"/></TD>
		            <TD><xsl:if test="@queryShow[.='true']"><xsl:value-of select="@queryShow"/></xsl:if></TD>
		           <TD><xsl:if test="@isGet[.='true']"><xsl:value-of select="@isGet"/></xsl:if></TD>
		           <TD><xsl:if test="@readOnlyU[.='true']"><xsl:value-of select="@readOnlyU"/></xsl:if></TD>
		           <TD><xsl:if test="@readOnlyI[.='true']"><xsl:value-of select="@readOnlyI"/></xsl:if></TD> -->
		           <TD style="width:100px"><xsl:value-of select="@note"/></TD>
		          </TR>
		         </xsl:for-each>
		        </TBODY>
	       </TABLE>
	       
	      
	      <TABLE  cellspacing="0" cellpadding="5" class="tbcontent">
	          <THEAD>
			   	   <TR bgcolor="#d7e2da">
			    	    <TH>�����</TH>			    	    
			    	    <TH>������</TH>
			    	    <TH>�Ƿ��������</TH>  
			    	    <TH>Լ������</TH>  
			    	    <TH>ע��</TH> 
			    	    <TH>��������</TH> 
			    	    <TH>��������</TH> 
			   	   </TR>
		  	  </THEAD>
		   	  <TBODY>
	      <xsl:for-each select="foreignKey" >
		      	<TR> <xsl:attribute name="style">background-color:<xsl:eval>if(formatindex(childnumber(this),"01")%2==1)"#f2f3dd"</xsl:eval><xsl:eval>if(formatindex(childnumber(this),"01")%2==0)"#E7EBE9"</xsl:eval></xsl:attribute>		
		      	    <TD><xsl:value-of select="@name"/></TD>
		      	    <TD><a><xsl:attribute name="href">#<xsl:value-of select="@foreignTable"/></xsl:attribute><xsl:value-of select="@foreignTable"/></a></TD>
			  	  	<TD><xsl:value-of select="@real"/></TD>
			  	    <TD><xsl:value-of select="@constraint"/></TD>
			  	    <TD><xsl:value-of select="@note "/></TD>	
			  	    <TD colspan="2">
				  	   <xsl:for-each select="reference">
				  	   		<TABLE class="subtb">
				  	   			<TR>
				  	   				<TD><xsl:value-of select="@local"/></TD>
				    	     		<TD><xsl:value-of select="@foreign"/></TD>
				  	   			</TR>
				  	   		</TABLE>
				  	   </xsl:for-each> 
			  	   </TD>
		 		</TR>				 
			
		 </xsl:for-each>  
		  </TBODY>
		 	</TABLE>    
		</xsl:for-each>
	</xsl:for-each>
	</DIV>
</BODY>
</HTML>

</xsl:template>
</xsl:stylesheet>