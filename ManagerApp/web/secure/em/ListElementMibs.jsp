<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*, java.util.*, org.ict.oamp.manager.ElementManager, org.ict.oamp.config.AppConfig, org.ict.oamp.mib.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%!
    Vector<MibFile> mibFiles = new Vector<MibFile>();
    int elementId = -1;
%>
<%
    try {
        mibFiles = (Vector<MibFile>) request.getAttribute("mibFiles");
        elementId = Integer.parseInt(request.getParameter("elementId"));
    } catch (Exception ex){
    }
    if(mibFiles == null) {
        mibFiles = new Vector<MibFile>();
    }
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><%= AppConfig.getInstance().getApplicationName()%></title>
<link href="<%= request.getContextPath()%>/resources/interface.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/resources/css/styles.css" rel="stylesheet" type="text/css" />
</head>

<body>
<%@ include file="../../result.jsp" %>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="ListTable">
  <tr class="ListHeading3">
    <td width="5%">#</td>
    <td align="left">Mib Name </td>
    <td align="left">Mib File Name </td>
    <td><a href="<%=request.getContextPath()%>/secure/addelementmibs?action=removeAll&elementId=<%= elementId%>">Remove All </a></td>
  </tr>
  <%
    for(int i=0; i<mibFiles.size(); i++) {
  %>
  <tr style="background-color:<%= (i % 2 == 0) ? "white" : ""%>">
  <td width="5%"><%= (i+1)%></td>
  <td align="left"><%= mibFiles.get(i).getMibName()%></td>
  <td align="left"><%= mibFiles.get(i).getFileName()%></td>
  <td><a href="<%=request.getContextPath()%>/secure/addelementmibs?action=remove&mibId=<%=mibFiles.get(i).getId()%>&elementId=<%= mibFiles.get(i).getElementId()%>">Remove</a></td>
  </tr>
  <%
    }
  %>
</table>
</body>
</html>
