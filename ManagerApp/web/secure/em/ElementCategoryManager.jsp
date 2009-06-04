<%@page contentType="text/html" pageEncoding="UTF-8" import="org.ict.oamp.config.*, java.util.*, org.ict.oamp.manager.*, org.ict.oamp.em.*" %>
<%!
	Vector categories = new Vector();
%>
<%
	if(request.getAttribute("categories") != null) {
		try {
			categories = (Vector) request.getAttribute("categories");
		} catch (Exception ex) {
			ex.printStackTrace();
			categories = new Vector();
		}
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%= AppConfig.getInstance().getApplicationName()%></title>
<link href="<%=request.getContextPath()%>/resources/css/styles.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@ include file="../../header2.jsp" %>
<%@ include file="../../result.jsp" %>
<table width="100%" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="8"><table cellpadding="0" cellspacing="0" width="95%" align="center" border="0">
        <tr>
          <td width="5"><img src="<%=request.getContextPath()%>/resources/images/graytableft.gif"></td>
          <td width="10%" class="GrayTabBG" align="center"><a href="<%=request.getContextPath()%>/secure/elementmanager">Elements</a></td>
          <td width="5"><img src="<%=request.getContextPath()%>/resources/images/graytabright.gif"></td>
          <td width="7"></td>
          <td width="4"><img src="<%=request.getContextPath()%>/resources/images/bluetableft.gif"></td>
          <td width="11%" class="BlueTabBG" align="center"><a href="<%=request.getContextPath()%>/secure/elementcategorymanager">Element Categories</a></td>
          <td width="5"><img src="<%=request.getContextPath()%>/resources/images/bluetabright.gif"></td>
          <td>&nbsp;</td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td colspan="8"><table cellpadding="0" cellspacing="0" width="98%" align="center">
        <tr>
          <td><img src="<%=request.getContextPath()%>/resources/images/topleft.gif"></td>
          <td width="100%" class="BoxTobBG"></td>
          <td><img src="<%=request.getContextPath()%>/resources/images/topright.gif"></td>
        </tr>
        <tr>
          <td colspan="3" class="BoxContents">
		  
		  <table width="60%" border="0" align="center" cellpadding="0" cellspacing="0" class="ListTable">
              <tr class="ListHeading">
                <td colspan="6">Available Element Categories </td>
              </tr>
              <tr class="ListHeading2">
                <td colspan="5"><a href="<%= request.getContextPath()%>/secure/em/EditElementCategory.jsp?action=<%=EditElementCategoryServlet.ACTION_ADD%>">Add New Element Category</a> | <a href="<%= request.getContextPath()%>/secure/elementcategorymanager">Refresh</a> </td>
              </tr>
              <tr class="ListHeading3">
                <td width="2%" >#</td>
                <td width="15%" >Category Name </td>
                <td width="30%" >Description </td>
                <td width="15%" >Number of Elements </td>
                <td width="8%" ><a href="<%= request.getContextPath()%>/secure/editelementcategory?action=<%=EditElementCategoryServlet.ACTION_DELETE_ALL%>">Delete All</a></td>
              </tr>
              <%
  	for(int i=0; i<categories.size(); i++){
	ElementCategory category = (ElementCategory) categories.get(i);
  %>
              <tr <%= (i%2==0 ? "bgcolor=\"white\"" : "")%> height="24">
                <td><%= i+1 %></td>
                <td><a href="<%= request.getContextPath()%>/secure/editelementcategory?action=<%= EditElementCategoryServlet.ACTION_POPULATE%>&categoryId=<%= category.getCategoryId()%>"><img src="<%=request.getContextPath()%>/secure/icons/<%=category.getCategoryId()%>.bmp" width="40" height="40" border="0" /><br/>
                <%= category.getCategoryName()%></a></td>
                <td><%= category.getDescription() %></td>
                <td><%= (category.getElements() == null ? 0 : category.getElements().size()) %></td>
                <td><a href="<%= request.getContextPath()%>/secure/editelementcategory?action=<%=EditElementManagerServlet.ACTION_DELETE%>&categoryId=<%= category.getCategoryId()%>">Delete</a></td>
              </tr>
              <%
  	}
  %>
            </table></td>
        </tr>
        <tr>
          <td><img src="<%=request.getContextPath()%>/resources/images/bottomleft.gif"></td>
          <td class="BoxBottomBG"></td>
          <td><img src="<%=request.getContextPath()%>/resources/images/bottomright.gif"></td>
        </tr>
      </table></td>
  </tr>
</table>
<jsp:include flush="true" page="../../footer2.jsp"></jsp:include>
</body>
</html>
