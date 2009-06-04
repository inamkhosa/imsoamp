<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*, java.util.*, org.ict.oamp.config.*, org.ict.oamp.utils.*, org.ict.util.*" errorPage="" %>
<%!
Vector<VarInfo> variables = new Vector<VarInfo>();
%>
<%
if(request.getAttribute("variables") != null) {
    try {
        variables = (Vector<VarInfo>)request.getAttribute("variables");
    } catch (Exception ex){
        variables = new Vector<VarInfo>();
    }
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><%= AppConfig.getInstance().getApplicationName()%></title>
<link href="<%=request.getContextPath()%>/resources/css/styles.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%@ include file="../../result.jsp" %>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="ListTable">
  <tr class="ListHeading">
    <td width="33%">Variable Name/OID </td>
    <td width="33%">Variable Value </td>
    <td width="33%"><p>Updated On </p>
    </td>
  </tr>
  <%
    for(int i=0; i<variables.size(); i++) {
  %>
  <tr <%= (i%2==0 ? "bgcolor=\"white\"" : "") %>>
	  <td><%=CommonFunctions.capitalize(variables.get(i).getKey())%></td>
	  <td><%= variables.get(i).getValue()%></td>
	  <td><%= CommonFunctions.formatDate(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), variables.get(i).getUpdatedAt())%></td>
  </tr>
  <%
    }
  %>
</table>

</body>
</html>
