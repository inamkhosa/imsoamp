<%@page contentType="text/html" pageEncoding="UTF-8" import="org.ict.oamp.config.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><%= AppConfig.getInstance().getApplicationName()%></title>
<link href="<%=request.getContextPath()%>/resources/css/styles.css" rel="stylesheet" type="text/css">
</head>

<body>
	<jsp:include flush="true" page="/header2.jsp"></jsp:include>
	<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="8">
			<table cellpadding="0" cellspacing="0" width="98%" align="center">
				<tr>
					<td><img src="<%=request.getContextPath()%>/resources/images/topleft.gif"></td>
					<td width="100%" class="BoxTobBG"></td>
					<td><img src="<%=request.getContextPath()%>/resources/images/topright.gif"></td>
				</tr>
				<tr>
					<td colspan="3" class="BoxContents">
						 <form method="POST" action="j_security_check">
							  <table cellpadding="0" cellspacing="0" width="30%" align="center" border="0">
								  <tr>
									<td width="30%" class="Heading" nowrap>Restricted Access</td>
									<td ><img src="<%=request.getContextPath()%>/resources/images/corner.gif"></td>
								  </tr>
								  <tr>
									<td colspan="2">
										<table width="100%" border="0" align="center" cellpadding="4" cellspacing="0" class="FormTable">
										  <tr>
											<td colspan = "2" width="30%" height="8">&nbsp;</td>
										  </tr>
										  <tr>
											<td width="30%" align="right">Username:</td>
											<td><input type="text" name="j_username" class="txtBox"></td>
										  </tr>
										  <tr>
											<td width="30%" align="right">Password:</td>
											<td><input type="password" name="j_password" class="txtBox"></td>
										  </tr>
										  <tr>
											<td>&nbsp;</td>
											<td><input name="login" type="submit" class="frmButton" value="Login"/></td>
										  </tr>
										  <tr>
											<td colspan = "2" width="30%" height="8">&nbsp;</td>
										  </tr>
										</table>
									</td>
								  </tr>
							  </table>
					    </form>
					</td>
				</tr>
				<tr>
					<td><img src="<%=request.getContextPath()%>/resources/images/bottomleft.gif"></td>
					<td class="BoxBottomBG"></td>
					<td><img src="<%=request.getContextPath()%>/resources/images/bottomright.gif"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<jsp:include flush="true" page="/footer2.jsp"></jsp:include>
</body>
</html>
