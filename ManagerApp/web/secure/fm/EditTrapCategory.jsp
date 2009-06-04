<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*,org.ict.oamp.fm.*, org.ict.oamp.config.*, java.util.*" errorPage="" %>
<%!    
    int action = 0;
    TrapCategory category = new TrapCategory();
    String categoryId = "";
    String categoryName = "";
    String colorCode = "";
    String description = "";
    Vector<String> availableTypes = new Vector<String>();
    Vector<String> selectedTypes = new Vector<String>();
%>
<%
        if (request.getParameter("action") != null) {
            try {
                action = Integer.parseInt(request.getParameter("action"));
            } catch (Exception ex) {
                action = 0;
            }
            if (action != EditTrapCategoryServlet.ACTION_ADD) {
                try {
                    category = (TrapCategory) request.getAttribute("category");
                    categoryName = category.getCategoryName();
                    colorCode = category.getColorCode();
                    categoryId = String.valueOf(category.getCategoryId());
                    description = category.getDescription();
                    selectedTypes = category.getTraps();
                    availableTypes = (Vector<String>)request.getAttribute("availableTypes");
                } catch (Exception ex) {
                    category = new TrapCategory();
                }

            } else {
                categoryName = "";
                categoryId = "";
                colorCode = "";
                description = "";
                try {
                    TrapCategoryDAO dao = new TrapCategoryDAO();
                    availableTypes = dao.getAvailableTrapTypes();
                } catch (Exception ex) {
                }
                if(availableTypes == null) {
                    availableTypes = new Vector<String>();
                }
                selectedTypes = new Vector<String>();
            }
        }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><%=AppConfig.getInstance().getApplicationName()%></title>
<script language="JavaScript" src="<%=request.getContextPath()%>/resources/colorpicker/picker.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/resources/option.js"></script>
<script language="javascript">
	function processRequest() {
		totalOptions = document.frmTrapCategory.selectedTypes.length;
		document.frmTrapCategory.selectedTypes.multiple = true;
		for(i=0; i<totalOptions; i++) {
			document.frmTrapCategory.selectedTypes.options[i].selected = true;
		}
		document.frmTrapCategory.submit();
	}
</script>
<link href="../../resources/css/styles.css" rel="stylesheet" type="text/css" />
<link href="resources/css/styles.css" rel="stylesheet" type="text/css" />
</head>
<body onload="createListObjects()">
<%@ include file="../../header2.jsp" %>
<%@ include file="../../result.jsp" %>
<table width="100%" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="8"><table cellpadding="0" cellspacing="0" width="95%" align="center" border="0">
        <tr>
          <td width="5"><img src="<%=request.getContextPath()%>/resources/images/graytableft.gif"></td>
          <td width="10%" class="GrayTabBG" align="center"><a href="<%=request.getContextPath()%>/faultmanager">Trap Report </a></td>
          <td width="5"><img src="<%=request.getContextPath()%>/resources/images/graytabright.gif"></td>
          <td width="7"></td>
          <td width="4"><img src="<%=request.getContextPath()%>/resources/images/bluetableft.gif"></td>
          <td width="11%" class="BlueTabBG" align="center"><a href="<%=request.getContextPath()%>/trapcategorymanager">Trap Categories</a></td>
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
          <td colspan="3" class="BoxContents"><table cellpadding="0" cellspacing="0" width="40%" align="center" border="0">
              <tr>
                <td width="30%" class="Heading" nowrap><%= action == EditTrapCategoryServlet.ACTION_ADD ? "Add" : "Edit"%> Trap Category  </td>
                <td ><img src="<%=request.getContextPath()%>/resources/images/corner.gif"></td>
              </tr>
              <tr>
                <td colspan="2"><form id="frmTrapCategory" name="frmTrapCategory" method="post" action="<%=request.getContextPath()%>/edittrapcategory?action=<%=request.getParameter("action")%>">
                    <table width="100%" border="0" align="center" cellpadding="4" cellspacing="0" class="FormTable">
                      <tr>
                        <td width="30%" align="right">Category Name:</td>
                        <td width="74%"><input name="categoryName" type="text" class="txtBox" id="categoryName" value="<%=categoryName%>"/></td>
                      </tr>
                      <tr>
                        <td align="right">Color Indicator: </td>
                        <td ><input name="colorCode" type="text" class="txtBox" id="colorCode" value="<%=colorCode%>"/>
                          <a href="javascript:TCP.popup(document.forms['frmTrapCategory'].elements['colorCode'])"><img src="<%=request.getContextPath()%>/resources/colorpicker/img/sel.gif" width="15" height="13" align="absmiddle" /></a></td>
                      </tr>
                      <tr>
                        <td valign="top" align="right">Trap Types: </td>
                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="40%" class="ListHeading3">Selected Types </td>
                              <td width="20%" rowspan="2" align="center"><input name="btnDeleteAll" class="frmButton" type="button" id="btnDeleteAll" value="&gt;&gt;" onclick="delAll()" style="width:40px"/>
                                <br/>
                                <input name="btnDelete" class="frmButton" type="button" id="btnDelete" value="&gt;" onclick="delAttribute()" style="width:40px"/>
                                <br/>
                                <input name="btnAdd" class="frmButton" type="button" id="btnAdd" value="&lt;" onclick="addAttribute()" style="width:40px"/>
                                <br/>
                                <input name="btnAddAll" class="frmButton" type="button" id="btnAddAll" value="&lt;&lt;" onclick="addAll()" style="width:40px"/>
                              </td>
                              <td width="40%" class="ListHeading3">Available Types </td>
                            </tr>
                            <tr>
                              <td><select name="selectedTypes" size="5" id="selectedTypes" class="ListContents">
                                  <%
                    for(int i=0; i<selectedTypes.size(); i++) {
                %>
                                  <option value="<%= selectedTypes.get(i)%>"><%= selectedTypes.get(i)%></option>
                                  <%
                    }
                %>
                                </select>
                              </td>
                              <td><select name="availableTypes" size="5" id="availableTypes" class="ListContents">
                                  <%
                    for(int i=0; i<availableTypes.size(); i++) {
                %>
                                  <option value="<%= availableTypes.get(i)%>"><%= availableTypes.get(i)%></option>
                                  <%
                    }
                %>
                                </select>
                              </td>
                            </tr>
                          </table></td>
                      </tr>
                      <tr>
                        <td valign="top" align="right" valign="top">Description:</td>
                        <td ><textarea name="description" class="txtArea" cols="30" rows="5" id="description" style="width:100%"><%= description%></textarea></td>
                      </tr>
                      <tr>
                        <td valign="top">&nbsp;</td>
                        <td align="left"><input type="button" class="frmButton" name="Submit" value="Save Category" onclick="processRequest();"/>
                          <input name="categoryId" type="hidden" id="categoryId" value="<%= categoryId%>"/></td>
                      </tr>
                    </table>
                  </form></td>
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
