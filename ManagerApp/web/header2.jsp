<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="Top">
		<td width="37%" valign="top"><img src="<%=request.getContextPath()%>/resources/images/advoss_NMS.gif"></td>
	    <td width="9%" class="TopImage" ><a href="<%=request.getContextPath()%>"><img src="<%=request.getContextPath()%>/resources/images/dashboard.gif" width="48" height="52" border="0"></a></td>
	    <td width="9%" class="TopImage"><a href="<%=request.getContextPath()%>/secure/elementmanager"><img src="<%=request.getContextPath()%>/resources/images/elements.gif" width="48" height="52" border="0"></a></td>
	    <td width="9%" class="TopImage"><a href="<%= request.getContextPath()%>/secure/mibmanager"><img src="<%=request.getContextPath()%>/resources/images/reports.gif" width="48" height="52" border="0"></a></td>
	    <td width="9%" class="TopImage"><a href="<%=request.getContextPath()%>/secure/performancemanager"><img src="<%=request.getContextPath()%>/resources/images/monitoring.gif" width="48" height="52" border="0"></a></td>
	    <td width="9%" class="TopImage"><a href="<%=request.getContextPath()%>/secure/configurationmanager"><img src="<%=request.getContextPath()%>/resources/images/configuration.gif" width="48" height="52" border="0"></a></td>
	    <td width="9%" class="TopImage"><a href="<%=request.getContextPath()%>/secure/faultmanager"><img src="<%=request.getContextPath()%>/resources/images/notifications.gif" width="48" height="52" border="0"></a></td>
	    <td width="9%" class="TopImage"><a href="#"><img src="<%=request.getContextPath()%>/resources/images/graphs.gif" width="48" height="52" border="0"></a></td>
	</tr>
	<tr><td class="HeightOne" colspan="8"></td></tr>
	<tr><td class="HeightOne" colspan="8" bgcolor="#1C4A82"></td></tr>
	<tr class="TopMenu">
		<td >
			<table cellpadding="0" cellspacing="0" width="100%" border="0">
				<tr style="color:white;">
					<%
						if(request.getSession(false) != null && request.getUserPrincipal() != null) {
					%>
					<td align="center"><img src="<%=request.getContextPath()%>/resources/images/user.gif"></td>
					<td>
						<b>Logged in: </b>[ <%=request.getUserPrincipal().getName()%>]
						&nbsp;&nbsp;&nbsp; 
						<a href="<%=request.getContextPath()%>/logout" class="logout">Logout</a>
					<%
						}
					%>	
					</td>
				</tr>
			</table>
		</td>
	  <td align="center"><a href="<%=request.getContextPath()%>" class="topLink">Dashboard</a></td>
	  <td align="center"><a href="<%=request.getContextPath()%>/secure/elementmanager" class="topLink">Elements</a></td>
	  <td align="center"><a href="<%= request.getContextPath()%>/secure/mibmanager" class="topLink">Mib Manager </a></td>
	  <td align="center"><a href="<%=request.getContextPath()%>/secure/performancemanager" class="topLink">Monitoring</a></td>
	  <td align="center"><a href="<%=request.getContextPath()%>/secure/configurationmanager" class="topLink">Configuration</a></td>
	  <td align="center"><a href="#" class="topLink">Notifications</a></td>
	  <td align="center"><a href="#" class="topLink">Graphs</a></td>
	</tr>
	<tr><td colspan="8" height="12"></td></tr>
</table>