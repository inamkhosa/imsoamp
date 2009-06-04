<%@page contentType="text/html" pageEncoding="UTF-8" import="org.ict.oamp.config.*, java.util.*, org.ict.oamp.manager.*, org.ict.oamp.em.*" %>
<%!
	Vector elements = new Vector();
%>
<%
	if(request.getAttribute("elements") != null) {
		try {
			elements = (Vector) request.getAttribute("elements");
		} catch (Exception ex) {
			ex.printStackTrace();
			elements = new Vector();
		}
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%= AppConfig.getInstance().getApplicationName()%></title>
<script language="javascript">
			function updateStatus(index, status) {
				divIndex = "status" + index;	
				var statusDiv = document.getElementById(divIndex);
				if(!status) {
					statusDiv.style.background = '#FF0000';
					statusDiv.innerHTML = 'Offline';
				} else {
					statusDiv.style.background = '#006600';
					statusDiv.innerHTML = 'Online';
				}
			}
		</script>
<link href="<%=request.getContextPath()%>/resources/css/styles.css" rel="stylesheet" type="text/css">
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
          <td><img src="<%=request.getContextPath()%>/resources/images/topleft.gif"></td>
          <td width="100%" class="BoxTobBG"></td>
          <td><img src="<%=request.getContextPath()%>/resources/images/topright.gif"></td>
        </tr>
        <tr>
          <td colspan="3" class="BoxContents"><table width="60%" border="0" align="center" cellpadding="0" cellspacing="0" class="ListTable">
              <tr>
                <td colspan="6" class="ListHeading">Available Elements</td>
              </tr>
              <tr class="ListHeading2">
                <td colspan="6"><a href="<%= request.getContextPath()%>/secure/em/EditElement.jsp?action=<%=EditElementManagerServlet.ACTION_ADD%>">Add New Element</a> | <a href="<%= request.getContextPath()%>/secure/elementmanager">Refresh</a> </td>
              </tr>
              <tr class="ListHeading3">
                <td width="2%" class="forms_title_2">#</td>
                <td width="15%" class="forms_title_2">Element Identifier </td>
                <td width="15%" class="forms_title_2">Destination Address </td>
                <td width="15%" class="forms_title_2">Element Category</td>
                <td width="6%" class="forms_title_2">Status</td>
                <td width="9%" class="forms_title_2"><a href="<%= request.getContextPath()%>/secure/editelement?action=<%=EditElementManagerServlet.ACTION_DELETE_ALL%>">Delete All</a></td>
              </tr>
              <%
  	for(int i=0; i<elements.size(); i++){
	ElementManager element = (ElementManager) elements.get(i);
  %>
              <tr <%= (i%2==0 ? "bgcolor=\"white\"": "")%>>
                <td><%= i+1 %></td>
                <td><a href="<%= request.getContextPath()%>/secure/editelement?action=<%= EditElementManagerServlet.ACTION_POPULATE%>&elementId=<%= element.getElementId()%>">
				<%if(element.getCategory()!=null){%>
					<img src="<%=request.getContextPath()%>/secure/icons/<%= element.getCategory().getCategoryId()%>.bmp" width="40" height="40" border="0"/><br/>
				<%}%>
				<%= element.getElementIdentifier()%></a></td>
                <td><%= element.getDestinationAddresss() %></td>
                <td><%= (element.getCategory() != null ? element.getCategory().getCategoryName()  : "None")%></td>
                <td><div id="status<%=i%>" style="background-color:#FF0000; color:#FFFFFF; font-weight:bold">Offline</div>
                  <a href="<%=request.getContextPath()%>/secure/pingelement?elementId=<%= element.getElementId()%>&index=<%=i%>"  target="frmPing">Ping</a></td>
                <td><a href="<%= request.getContextPath()%>/secure/editelement?action=<%=EditElementManagerServlet.ACTION_DELETE%>&elementId=<%= element.getElementId()%>">Delete</a></td>
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
<iframe name="frmPing" id="frmPing" src="#" style="visibility:hidden"> </iframe>
</body>
</html>
