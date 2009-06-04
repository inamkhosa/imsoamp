<center>
<%
	if(request.getParameter("error")!= null){
%>
<div class="error_block" id="error">
	Error occured while processing your request:<br/>
	<%= request.getParameter("error")%><br/>
</div>
<%
	} else if( request.getParameter("message") != null) {
%>
<div class="message_block" id="message">
	<%= request.getParameter("message")%><br/>
</div>
<%
	}
%>
</center>
<br/>
