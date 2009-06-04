<%-- 
    Document   : index
    Created on : Oct 29, 2008, 2:12:32 PM
    Author     : Alam Sher
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ICT -- Network Manager</title>
        <link href="<%=request.getContextPath()%>/resources/interface.css" rel="stylesheet" type="text/css">
    </head>
    <body>
		<%@ include file="../header.jsp" %>
		<center>
        <h5>Welcome!</h5>
		1. <a href="<%=request.getContextPath()%>/secure/elementcategorymanager">Element Category Managemer</a><br/>
		2. <a href="<%=request.getContextPath()%>/secure/elementmanager">Element Managemer</a><br/>
		3. <a href="<%=request.getContextPath()%>/secure/trapcategorymanager">Trap Category Managemer</a><br/>
		4. <a href="<%=request.getContextPath()%>/secure/faultmanager">Fault Managemer<br/></a>
        5. <a href="<%=request.getContextPath()%>/secure/performancemanager">Performance Manager</a><br/>
        6. <a href="<%=request.getContextPath()%>/secure/configurationmanager">Configuration Manager</a><br/>
        7. <a href="<%=request.getContextPath()%>/secure/mibmanager">Mib Manager</a><br/>
		</center>
    </body>
</html>
