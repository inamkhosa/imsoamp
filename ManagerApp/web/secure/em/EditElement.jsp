<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*, java.util.*,org.ict.oamp.config.*, org.ict.oamp.manager.*, org.ict.oamp.em.*, org.ict.oamp.mib.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%!    Vector<MibFile> mibFiles = new Vector();
    Vector<ElementCategory> categories = new Vector();
    int action = 0;
    ElementManager element = new ElementManager();
    String elementIdentifier = "";
    String elementId = "";
    String destinationAddresss = "";
    String securityName = "";
    String authProtocol = "";
    String authPass = "";
    String privProtocol = "";
    String privPass = "";
    String categoryName = "";
%>
<%
        if (request.getParameter("action") != null) {
            try {
                action = Integer.parseInt(request.getParameter("action"));
            } catch (Exception ex) {
                action = 0;
            }
            if (action != EditElementManagerServlet.ACTION_ADD) {
                try {
                    element = (ElementManager) request.getAttribute("element");
                    elementIdentifier = element.getElementIdentifier();
                    elementId = String.valueOf(element.getElementId());
                    destinationAddresss = element.getDestinationAddresss();
                    securityName = element.getSecurityName();
                    authProtocol = element.getAuthProtocol();
                    authPass = element.getAuthPass();
                    privProtocol = element.getPrivProtocol();
                    privPass = element.getPrivPass();
                    mibFiles = element.getMibFiles();
                    if (mibFiles == null) {
                        mibFiles = new Vector<MibFile>();
                    }
                    try {
                        categoryName = element.getCategory().getCategoryName();
                        if(categoryName == null) {
                            categoryName = "";
                        }
                    } catch(Exception ex2) {
                        categoryName = "";
                    }
                } catch (Exception ex) {
                    element = new ElementManager();
                }

            } else {
                elementIdentifier = "";
                elementId = "";
                destinationAddresss = "";
                securityName = "";
                authProtocol = "";
                authPass = "";
                privProtocol = "";
                privPass = "";
                categoryName = "";
            }
        }
        try {
            categories = (Vector<ElementCategory>) request.getAttribute("categories");
            if(categories == null) {
                ElementCategoryDAO dao = new ElementCategoryDAO();
                categories = dao.getElementCategories();;
            }
        } catch (Exception ex) {
            categories = new Vector<ElementCategory>();
        }
        if(categories == null) {
            categories = new Vector<ElementCategory>();
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
<%@ include file="../../header2.jsp" %>
<%@ include file="../../result.jsp" %>
<table width="100%" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="8"><table cellpadding="0" cellspacing="0" width="95%" align="center" border="0">
        <tr>
          <td width="5"><img src="<%=request.getContextPath()%>/resources/images/bluetableft.gif"></td>
          <td width="10%" class="BlueTabBG" align="center"><a href="<%=request.getContextPath()%>/secure/elementmanager">Elements</a></td>
          <td width="5"><img src="<%=request.getContextPath()%>/resources/images/bluetabright.gif"></td>
          <td width="7"></td>
          <td width="4"><img src="<%=request.getContextPath()%>/resources/images/graytableft.gif"></td>
          <td width="11%" class="GrayTabBG" align="center"><a href="<%=request.getContextPath()%>/secure/elementcategorymanager">Element Categories</a></td>
          <td width="5"><img src="<%=request.getContextPath()%>/resources/images/graytabright.gif"></td>
          <td>&nbsp;</td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td colspan="8"><table cellpadding="0" cellspacing="0" width="98%" align="center">
        <tr>
          <td><img src="<%=request.getContextPath()%>/resources/images/topleft.gif" /></td>
          <td width="100%" class="BoxTobBG"></td>
          <td><img src="<%=request.getContextPath()%>/resources/images/topright.gif"></td>
        </tr>
        <tr>
          <td colspan="3" class="BoxContents">
		  <table width="<%= (action == 0 ? "50" : "80")%>%" align="center" cellpadding="5" cellspacing="5">
		  	<tr>
				<td valign="top">
		  <form action="<%=request.getContextPath()%>/secure/editelement?action=<%=request.getParameter("action")%>" method="post" name="form1" id="form1">
              <table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
                <tr>
                  <td width="30%" class="Heading" nowrap><%= (action == 0 ? "Add New Element" : "Edit Element Information")%></td>
                  <td ><img src="<%=request.getContextPath()%>/resources/images/corner.gif"></td>
                </tr>
                <tr>
                  <td colspan="2"><table width="100%" height="300px" border="0" align="center" cellpadding="0" cellspacing="0" class="FormTable">
                      <tr>
                        <td width="35%" align="right">Element Identifier: </td>
                        <td width="70%"><input name="elementIdentifier" type="text" class="txtBox" id="elementIdentifier" value="<%= elementIdentifier%>"/>                        </td>
                      </tr>
                      <tr>
                        <td align="right">Destination Address: </td>
                        <td ><input name="destAddress" type="text" class="txtBox" id="destAddress" value="<%= destinationAddresss%>"/></td>
                      </tr>
                      <tr>
                        <td align="right">Element Category: </td>
                        <td ><select name="category" class="DropDown" id="category">
                            <option value="">None</option>
                            <%
                                for (int i = 0; i < categories.size(); i++) {
                            %>
                            <option value="<%= categories.get(i).getCategoryId()%>" <%= categoryName.equals(categories.get(i).getCategoryName()) ? "selected=\"selected\"" : ""%>><%= categories.get(i).getCategoryName()%></option>
                            <%
                                }
                            %>
                          </select>                        </td>
                      </tr>
                      <tr>
                        <td align="right">Security Name: </td>
                        <td ><input name="secName" type="text" class="txtBox" id="secName" value="<%= securityName%>"/></td>
                      </tr>
                      <tr>
                        <td align="right">Authentication Protocol: </td>
                        <td ><input name="authProtocol" type="text" class="txtBox" id="authProtocol" value="<%= authProtocol%>"/></td>
                      </tr>
                      <tr>
                        <td align="right">Authentication Password: </td>
                        <td ><input name="authPassword" type="password" class="txtBox" id="authPassword" value="<%= authPass%>"/></td>
                      </tr>
                      <tr>
                        <td align="right">Privacy Protocol: </td>
                        <td ><input name="privProtocol" type="text" class="txtBox" id="privProtocol" value="<%= privProtocol%>"/></td>
                      </tr>
                      <tr>
                        <td align="right">Privacy Password: </td>
                        <td ><input name="privPassword" type="password" class="txtBox" id="privPassword" value="<%= privPass%>"/></td>
                      </tr>
                      <tr>
                        <td align="right">&nbsp;</td>
                        <td ><input name="btnSave" type="submit" id="btnSave" value="Save Element"  class="frmButton"/>
                          <input name="elementId" type="hidden" id="elementId" value="<%= elementId%>" />                        </td>
                      </tr>
                    </table></td>
                </tr>
              </table>
            </form>
				</td>
				<td valign="top">
					<%
						if(action == EditElementManagerServlet.ACTION_EDIT) {
					%>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					  <tr>
						<td width="30%" class="Heading" nowrap>Element's Associated Mib(s)</td>
					  <td ><img src="<%=request.getContextPath()%>/resources/images/corner.gif"></td>
					  </tr>
					  <tr>
						<td colspan="2">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="FormTable">
								<tr class="ListHeading2">
									<td><a href="<%=request.getContextPath()%>/secure/listelementmibs?elementId=<%=elementId%>" target="frmMibs">View Mibs</a> | <a href="<%=request.getContextPath()%>/secure/addelementmibs?elementId=<%=elementId%>" target="frmMibs">Configure Mibs</a></td>
								</tr>
								<tr valign="top">
									<td>
                                        <iframe align="top" frameborder="0" id="frmMibs" name="frmMibs" width="100%" height="280px" scrolling="auto" src="<%=request.getContextPath()%>/secure/listelementmibs?elementId=<%=elementId%>">
                                        </iframe>
									</td>
								</tr>
							</table>
						</td>
					  </tr>
					</table> 
					<%
						}
					%>         		
				</td>
			</tr>
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
