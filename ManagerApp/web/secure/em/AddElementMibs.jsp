<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*, java.util.*, org.ict.oamp.manager.ElementManager, org.ict.oamp.mib.*, org.ict.oamp.config.*" errorPage="" %>
<%!
	ElementManager elementManager = null;
	Vector<MibFile> mibFiles = new Vector<MibFile>();
%>
<%
	try {
		mibFiles = (Vector<MibFile>) request.getAttribute("mibFiles");
	} catch (Exception ex){
	}
	elementManager = (ElementManager)request.getAttribute("elementManager");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><%= AppConfig.getInstance().getApplicationName()%></title>
<link href="<%= request.getContextPath()%>/resources/interface.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/resources/css/styles.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%@ include file="../../result.jsp"%>
<form name="frmMibs" action="<%=request.getContextPath()%>/secure/addelementmibs?elementId=<%=elementManager.getElementId()%>&action=add" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ListTable">
  <tr class="ListHeading2">
  	<td colspan="4"><a href="javascript:document.forms['frmMibs'].submit();">Add Selected Mibs</a></td>
  </tr> 	
  <tr class="ListHeading3">
    <td width="5%">&nbsp;</td>
    <td align="left">Mib Name </td>
    <td align="left">Mib File Name </td>
    <td> Variables </td>
  </tr>
  <%
  	for(int i=0; i<mibFiles.size(); i++) {
  %>
  <tr style="background-color:<%= (i % 2 == 0) ? "white" : ""%>">
    <td><input name="mibFiles" type="checkbox" id="mibFiles" value="<%= mibFiles.get(i).getMibName()%>"/></td>
    <td align="left"><%= mibFiles.get(i).getMibName()%></td>
    <td align="left"><%= mibFiles.get(i).getFileName()%></td>
    <td><%= mibFiles.get(i).getVariables().size()%></td>
  </tr>
  <%
  	}
  %>
  <tr class="ListHeading2">
  	<td colspan="4"><a href="javascript:document.forms['frmMibs'].submit();">Add Selected Mibs</a></td>
  </tr> 	
</table>
</form>
</body>
</html>
