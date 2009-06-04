<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*, org.ict.oamp.config.*, org.ict.oamp.fm.Trap" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%!
    Trap trap = null;
%>
<%
    try {
        trap = (Trap) request.getAttribute("trap");
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    if(trap == null) {
        trap = new Trap();
    }
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><%= AppConfig.getInstance().getApplicationName()%></title>
<link href="../../resources/css/styles.css" rel="stylesheet" type="text/css" />
<link href="resources/css/styles.css" rel="stylesheet" type="text/css" />

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
          <td colspan="3" class="BoxContents"><table width="50%" border="0" align="center" cellpadding="1" cellspacing="1" class="ListTable">
              <tr class="ListHeading">
                <td colspan="2">Trap Details </td>
              </tr>
              <tr>
                <td width="28%" class="ListHeading2">Trap Id: </td>
                <td width="72%" align="left"><%=trap.getTrapId()%></td>
              </tr>
              <tr>
                <td class="ListHeading2">Request Id: </td>
                <td align="left"><%=trap.getRequestId()%></td>
              </tr>
              <tr>
                <td class="ListHeading2">Trap Host: </td>
                <td align="left"><%= trap.getTrapHost()%></td>
              </tr>
              <tr>
                <td class="ListHeading2">Timestamp:</td>
                <td align="left"><%=trap.getTimestamp()%></td>
              </tr>
              <tr>
                <td class="ListHeading2">Security Name: </td>
                <td align="left"><%=trap.getSecurityName()%></td>
              </tr>
              <tr>
                <td class="ListHeading2">Security Model: </td>
                <td align="left"><%=trap.getSecurityModel()%></td>
              </tr>
              <tr>
                <td class="ListHeading2">Security Level: </td>
                <td align="left"><%=trap.getSecurityLevel()%></td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td><table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="ListTable">
                    <tr class="ListHeading">
                      <td width="50%">OID Variables </td>
                      <td width="50%">OID Values </td>
                    </tr>
                    <%
        java.util.Enumeration keys = trap.getVariables().keys();
        while(keys.hasMoreElements()){
            String OIDVar = (String)keys.nextElement();
            String OIDVal = (String)trap.getVariables().get(OIDVar);
      %>
                    <tr>
                      <td ><%= OIDVar%></td>
                      <td ><%= OIDVal%></td>
                    </tr>
                    <%
        }
      %>
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
