<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*, java.util.*, org.ict.oamp.config.*, org.ict.oamp.em.*, org.ict.oamp.manager.*, org.ict.oamp.utils.*, org.ict.util.*"  errorPage="" %>
<%!
    Vector<ElementManager> elements = new Vector<ElementManager>();
    ElementManager selectedElement = null;
    int seletedElementId = -1;
%>
<%
    try {
        elements = OAMPManager.registeredElements;
    } catch (Exception ex) {
        ex.printStackTrace();
        elements = new Vector();
    }
    try {
        seletedElementId = Integer.parseInt(request.getAttribute("selectedElementId").toString());
    } catch (Exception ex) {
        seletedElementId = -1;
    }
    if(seletedElementId == -1) {
        if(elements.size() > 0) {
            selectedElement = elements.get(0);
            seletedElementId = selectedElement.getElementId();
        }
    } else {
        if(elements.size() > 0) {
            for(int i=0; i<elements.size(); i++) {
                if(elements.get(i).getElementId() == seletedElementId) {
                    selectedElement = elements.get(i);
                    seletedElementId = selectedElement.getElementId();
                }
            }
        } else {
            selectedElement = null;
            seletedElementId = -1;
        }
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><%= AppConfig.getInstance().getApplicationName()%></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/common.js"></script>
<link href="<%=request.getContextPath()%>/resources/css/styles.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%@ include file="../../header2.jsp" %>
<%@ include file="../../result.jsp" %>
<table width="100%" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="8"><table cellpadding="0" cellspacing="0" width="98%" align="center">
        <tr>
          <td><img src="<%=request.getContextPath()%>/resources/images/topleft.gif"></td>
          <td width="100%" class="BoxTobBG"></td>
          <td><img src="<%=request.getContextPath()%>/resources/images/topright.gif"></td>
        </tr>
        <tr>
          <td colspan="3" class="BoxContents"><table cellpadding="0" cellspacing="0" width="80%" align="center" border="0">
              <tr>
                <td width="10%" class="Heading" nowrap>Performance Monitor </td>
                <td ><img src="<%=request.getContextPath()%>/resources/images/corner.gif"></td>
              </tr>
              <tr>
                <td colspan="2"><table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="FormTable">
                    <tr>
                      <td width="20%" valign="top">
					  <span class="ListHeading2" >Select an Element: (<a href="<%= request.getContextPath()%>/secure/performancemanager">Refresh</a>)</span><br/>
					  <div align="center" style="width:100%; height:450px; overflow:auto;">
						<%
			                for(int i=0; i<elements.size(); i++) {
            		        ElementManager element = (ElementManager) elements.get(i);
            			%>
					  		<table width="100%" align="center">
								<tr>
									<td>
										<a href="<%=request.getContextPath()%>/secure/performancemanager?lstElements=<%=element.getElementId()%>">
											<%
												if(element.getCategory() != null) {
											%>
												<img src="<%= request.getContextPath()%>/secure/icons/<%= element.getCategory().getCategoryId()%>.bmp" height="50" width="50"/><br/><%= element.getElementIdentifier()%><br/><%= element.getDestinationAddresss()%>
											<%
												}
											%>
															
									  </a>
								  </td>
								</tr>
							</table>			
						<%
							}
						%>	
					  </div>
					  </td>
                      <td width="30%" style="vertical-align:top"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
                          <tr class="ListHeading2">
                            <td colspan="2" >Select OIDs/Variables to view Report: 
                          </tr>
                          <tr class="ListHeading2">
                            <td colspan="2" >Select <a href="#" onclick="javascript:setAllCheckBoxes('frmVariables', 'chkVariables', true);return false;">All</a> | <a href="javascript:void(0);" onclick="javascript:setAllCheckBoxes('frmVariables', 'chkVariables', false);return false;">None</a> 
                          </tr>
                          <tr>
                            <td bgcolor="white"><form action="<%=request.getContextPath()%>/secure/pmstatsreport" name="frmVariables" method="POST" target="frmReport" id="frmVariables">
                                <div id="divVariables" style="overflow:auto;">
                                  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
                                    <%
        if(selectedElement != null) {
            for(int i=0; i<selectedElement.getPerformanceVariables().size(); i++) {
                Vector <VarInfo> variables = selectedElement.getPerformanceVariables();
      %>
                                    <tr>
                                      <td ><input name="chkVariables" type="checkbox" id="chkVariables" value="<%=variables.get(i).getTag()%>" /></td>
                                      <td ><%=CommonFunctions.capitalize(variables.get(i).getKey())%><span class="forms_title_3"> </span></td>
                                    </tr>
                                    <%
            }
        }
      %>
                                  </table>
                                </div>
                                <input name="elementId" type="hidden" id="elementId" value="<%=seletedElementId%>" />
                                <input name="cached" type="hidden" id="cached" value="0" />
                              </form></td>
                          </tr>
                        </table></td>
                      <td width="50%" style="vertical-align:top"><table width="100%" height="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="forms2">
                          <tr class="ListHeading2">
                            <td colspan="3" class="forms_title_3">Performance Stats Report: <a href="#" onclick="javascript:getStats(0);">Get Stats From Cache</a> | <a href="#" onclick="javascript:getStats(1);">Get Live Stats</a> </td>
                          </tr>
                          <tr>
                            <td colspan="3" ><iframe align="bottom" frameborder="0" width="100%" height="450px" id="frmReport" name="frmReport" scrolling="auto" src="<%=request.getContextPath()%>/secure/pm/PMStatsBlankReport.jsp" style="vertical-align:bottom"> </iframe></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
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
<jsp:include flush="true" page="/footer2.jsp"></jsp:include>
</body>
</html>
