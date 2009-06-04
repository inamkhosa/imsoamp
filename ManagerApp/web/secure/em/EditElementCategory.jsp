<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*, java.util.*, org.ict.oamp.config.*, org.ict.oamp.manager.*, org.ict.oamp.em.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%!    
    int action = 0;
    ElementCategory category = new ElementCategory();
    String categoryId = "";
    String categoryName = "";
    String description = "";
%>
<%
        if (request.getParameter("action") != null) {
            try {
                action = Integer.parseInt(request.getParameter("action"));
            } catch (Exception ex) {
                action = 0;
            }
            if (action != EditElementCategoryServlet.ACTION_ADD) {
                try {
                    category = (ElementCategory) request.getAttribute("category");
                    categoryName = category.getCategoryName();
                    categoryId = String.valueOf(category.getCategoryId());
                    description = category.getDescription();
                } catch (Exception ex) {
                    category = new ElementCategory();
                }

            } else {
                categoryName = "";
                categoryId = "";
                description = "";
            }
        }
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><%= AppConfig.getInstance().getApplicationName()%></title>
<link href="<%=request.getContextPath()%>/resources/css/styles.css" rel="stylesheet" type="text/css" />
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
          <td colspan="3" class="BoxContents"><form action="<%=request.getContextPath()%>/secure/editelementcategory?action=<%=request.getParameter("action")%>" method="post" enctype="multipart/form-data" name="frmElementCategory" id="frmElementCategory">
              <table cellpadding="0" cellspacing="0" width="35%" align="center" border="0">
                <tr>
                  <td width="30%" class="Heading" nowrap><%= (action == 0 ? "Add New Element Category" : "Edit Element Category Information")%></td>
                  <td ><img src="<%=request.getContextPath()%>/resources/images/corner.gif"></td>
                </tr>
                <tr>
                  <td colspan="2"><table width="100%" border="0" align="center" cellpadding="4" cellspacing="0" class="FormTable">
                      <tr>
                        <td width="30%" align="right">Category Name: </td>
                        <td ><input name="categoryName" type="text" class="txtBox" value="<%= categoryName%>"/></td>
                      </tr>
                      <tr>
                        <td width="30%" align="right">Icon:</td>
                        <td ><input name="iconFile" type="file" class="txtBox" id="iconFile" /></td>
                      </tr>
					  <%if(action != 0){%>
						  <tr bgcolor="white">
							<td width="30%" align="right">Currently Selected: </td>
							<td ><img height="40" width="40" src="<%=request.getContextPath()%>/secure/icons/<%=categoryId%>.bmp" /></td>
						  </tr>
					  <%}%>
                      <tr>
                        <td rowspan="2" valign="top" align="right">Descripton:</td>
                        <td><textarea name="description" cols="40" rows="5" class="txtArea"><%= description%></textarea></td>
                      </tr>
                      <tr>
                        <td><input name="btnSave" type="submit" id="btnSave" value="Save Category" class="frmButton"/>
                          <input name="categoryId" type="hidden" id="categoryId" value="<%= categoryId%>" /></td>
                      </tr>
                    </table>
				  </td>
                </tr>
              </table>
            </form></td>
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
