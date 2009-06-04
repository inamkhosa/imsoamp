<%-- 
    Document   : AppError
    Created on : Dec 19, 2008, 2:38:12 PM
    Author     : Alam Sher
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import="org.ict.oamp.config.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%= AppConfig.getInstance().getApplicationName()%></title>
        <link href="<%= request.getContextPath()%>/resources/interface.css" rel="stylesheet" type="text/css">
    </head>
    <body>
	<%@ include file="/header.jsp" %>
    <div class="error_block" id="error">Error occured while processing your request:<br/>
            <%= request.getParameter("error")%><br/>
        <a href="javascript:history.back();">Go Back</a> | <a href="/webmanager">Go to Home</a> </div>
    </body>
</html>
