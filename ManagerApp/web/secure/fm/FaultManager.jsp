<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*,org.ict.oamp.fm.*, org.ict.oamp.config.*, java.util.*, org.ict.oamp.manager.ElementManager" errorPage="" %>
<%!
    Vector elements = new Vector();
    ElementManager selected = null;
	Vector traps = new Vector();
%>
<%
    try {
        elements = (Vector) request.getAttribute("elements");
    } catch (Exception ex) {
        ex.printStackTrace();
        elements = new Vector();
    }
    try {
        selected = (ElementManager) request.getAttribute("selected");
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    try {
        traps = (Vector) request.getAttribute("traps");
    } catch (Exception ex) {
        ex.printStackTrace();
        traps = new Vector();
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><%=AppConfig.getInstance().getApplicationName()%></title>
<link href="../../resources/css/styles.css" rel="stylesheet" type="text/css" />
<link href="resources/css/styles.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%@ include file="../../header2.jsp" %>
<%@ include file="../../result.jsp" %>
<table width="100%" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="8"><table cellpadding="0" cellspacing="0" width="95%" align="center" border="0">
        <tr>
          <td width="5"><img src="resources/images/bluetableft.gif"></td>
          <td width="10%" class="BlueTabBG" align="center"><a href="<%=request.getContextPath()%>/faultmanager">Trap Report </a></td>
          <td width="5"><img src="resources/images/bluetabright.gif"></td>
          <td width="7"></td>
          <td width="4"><img src="resources/images/graytableft.gif"></td>
          <td width="11%" class="GrayTabBG" align="center"><a href="<%=request.getContextPath()%>/trapcategorymanager">Trap Categories</a></td>
          <td width="5"><img src="resources/images/graytabright.gif"></td>
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
          <td colspan="3" class="BoxContents"><table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
	                <td width="10%" class="Heading" nowrap>Trap Report </td>
    	            <td ><img src="<%=request.getContextPath()%>/resources/images/corner.gif"></td>
       	      </tr>
              <tr>
                <td valign="top" width="20%" colspan="2" class="FormTable">
                    <span class="ListHeading2">Select an Element: <a href="<%=request.getContextPath()%>/faultmanager"> (Select All</a>)</span> <br/>
					
                     <div align="center" style="width:100%; vertical-align:top  overflow:auto">
						<%
			                for(int i=0; i<elements.size(); i++) {
            		        ElementManager element = (ElementManager) elements.get(i);
            			%>
					  		<table width="100%" align="center">
								<tr>
									<td>
										<a href="<%=request.getContextPath()%>/faultmanager?selectedElement=<%=element.getElementId()%>">
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
				  </div></td>
                <td width="70%" valign="top">
					<table width="100%" cellpadding="0" cellspacing="0" class="FormTable">
                    <tr class="ListHeading3" height="20">
                      <td width="6%">&nbsp;</td>
                      <td width="10%">Trap ID</td>
                      <td width="27%">Type</td>
                      <td width="42%">Host</td>
                      <td width="18%">Time Stamp </td>
                      <td>&nbsp;</td>
                    </tr>
                   <%
			for(int i=0; i<traps.size(); i++) {
				Trap trap = (Trap) traps.get(i);
		  %>
                    <tr <%=(i%2==0) ? "bgcolor=\"White\"" : ""%> height="24">
                      <td align="center" valign="middle"><div style="background-color:<%= trap.getColorCode()%>;width:40%;height:40%">&nbsp;</div></td>
                      <td align="center"><%= trap.getTrapId()%></td>
                      <td align="center" width="27%"><%=trap.getType()%></td>
                      <td align="center" width="42%"><%=trap.getTrapHost()%></td>
                      <td align="center" width="18%"><%=trap.getTimestamp()%></td>
                      <td align="center"><a href="<%=request.getContextPath()%>/trapDetails?trapId=<%=trap.getTrapId()%>">Details</a></td>
                    </tr>
                    <%
			}
		  %>
                  </table></td>
              </tr>
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
