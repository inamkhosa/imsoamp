<%@page contentType="text/html" pageEncoding="UTF-8" import="org.ict.oamp.config.*, java.util.*, org.ict.oamp.manager.*, org.ict.oamp.fm.*" %>
<%!
	Vector<TrapCategory> categories = new Vector();
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
<link href="../../resources/css/styles.css" rel="stylesheet" type="text/css">
<link href="resources/css/styles.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@ include file="../../header2.jsp" %>
<%@ include file="../../result.jsp" %>
<table width="100%" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="8"><table cellpadding="0" cellspacing="0" width="95%" align="center" border="0">
        <tr>
          <td width="5"><img src="resources/images/graytableft.gif"></td>
          <td width="10%" class="GrayTabBG" align="center"><a href="<%=request.getContextPath()%>/faultmanager">Trap Report </a></td>
          <td width="5"><img src="resources/images/graytabright.gif"></td>
          <td width="7"></td>
          <td width="4"><img src="resources/images/bluetableft.gif"></td>
          <td width="11%" class="BlueTabBG" align="center"><a href="<%=request.getContextPath()%>/trapcategorymanager">Trap Categories</a></td>
          <td width="5"><img src="resources/images/bluetabright.gif"></td>
          <td>&nbsp;</td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td colspan="8"><table cellpadding="0" cellspacing="0" width="98%" align="center">
        <tr>
          <td><img src="resources/images/topleft.gif"></td>
          <td width="100%" class="BoxTobBG"></td>
          <td><img src="resources/images/topright.gif"></td>
        </tr>
        <tr>
          <td colspan="3" class="BoxContents"><table width="70%" border="0" align="center" cellpadding="0" cellspacing="0" class="ListTable">
              <tr>
                <td colspan="7" class="ListHeading">Available Trap Categories </td>
              </tr>
              <tr class="ListHeading2">
                <td colspan="6"><a href="<%= request.getContextPath()%>/secure/fm/EditTrapCategory.jsp?action=<%=EditTrapCategoryServlet.ACTION_ADD%>">Add New Trap Category</a> | <a href="<%= request.getContextPath()%>/trapcategorymanager">Refresh</a> </td>
              </tr>
              <tr class="ListHeading3">
                <td width="2%" class="forms_title_2">#</td>
                <td width="15%" class="forms_title_2">Category Name </td>
                <td width="20%" class="forms_title_2">Description </td>
                <td width="20%" class="forms_title_2">Associated Traps </td>
                <td width="13%" class="forms_title_2">Color Indicator </td>
                <td width="8%" class="forms_title_2"><a href="<%= request.getContextPath()%>/edittrapcategory?action=<%=EditTrapCategoryServlet.ACTION_DELETE_ALL%>">Delete All</a></td>
              </tr>
              <%
  	for(int i=0; i<categories.size(); i++){
	TrapCategory category = (TrapCategory) categories.get(i);
  %>
              <tr <%=(i%2==0 ? "bgcolor=\"white\"" : "")%> height="24">
                <td><%= i+1 %></td>
                <td><a href="<%= request.getContextPath()%>/edittrapcategory?action=<%= EditTrapCategoryServlet.ACTION_POPULATE%>&categoryId=<%= category.getCategoryId()%>"><%= category.getCategoryName()%></a></td>
                <td><%= category.getDescription() %></td>
                <td><%
			if(category.getTraps() != null && category.getTraps().size() > 0) {
                for(int y=0; y<category.getTraps().size(); y++) {
                    out.print(category.getTraps().get(y) + "<br/>");
                }
            }
		%>
                </td>
                <td><div align="center" style="background-color:<%= category.getColorCode()%>; vertical-align:middle; height:30; width:30">&nbsp;</div></td>
                <td><a href="<%= request.getContextPath()%>/edittrapcategory?action=<%=EditTrapCategoryServlet.ACTION_DELETE%>&categoryId=<%= category.getCategoryId()%>">Delete</a></td>
              </tr>
              <%
  	}
  %>
            </table></td>
        </tr>
        <tr>
          <td><img src="resources/images/bottomleft.gif"></td>
          <td class="BoxBottomBG"></td>
          <td><img src="resources/images/bottomright.gif"></td>
        </tr>
      </table></td>
  </tr>
</table>
<jsp:include flush="true" page="../../footer2.jsp"></jsp:include>
</body>
</html>
