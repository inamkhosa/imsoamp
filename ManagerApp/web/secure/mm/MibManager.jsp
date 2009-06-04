<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*, java.util.*, org.ict.oamp.config.*, org.ict.oamp.mib.*"  errorPage="" %>
<%!    Vector<MibFile> mibFiles = new Vector<MibFile>();
%>
<%
        try {
            mibFiles = (Vector<MibFile>) request.getAttribute("mibFiles");
        } catch (Exception ex) {
            ex.printStackTrace();
            mibFiles = new Vector<MibFile>();
        }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <title><%= AppConfig.getInstance().getApplicationName()%></title>
        <link href="<%= request.getContextPath()%>/resources/interface.css" rel="stylesheet" type="text/css" />
        <link href="<%= request.getContextPath()%>/resources/css/styles.css" rel="stylesheet" type="text/css" />
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
                            <td colspan="3" class="BoxContents">
                                <table width="60%" align="center" cellpadding="5" cellspacing="5">
                                    <tr>
                                        <td valign="top">
                                            <form action="<%=request.getContextPath()%>/secure/mibmanager" method="post" enctype="multipart/form-data" name="frmMibFiles" id="frmMibFiles">
                                                <table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
                                                    <tr>
                                                        <td width="10%" class="Heading" nowrap>Mib Manager   </td>
                                                        <td ><img src="<%=request.getContextPath()%>/resources/images/corner.gif"></td>
                                                    </tr>
                                                    <tr>
                                                        <td colspan="2"><table width="100%" border="0" align="center" cellpadding="4" cellspacing="0" class="FormTable">
                                                                <tr>
                                                                    <td align="center" valign="middle" class"ListHeading3">Add New Mib File:
                                                                        <input name="mibfileControl" type="file" class="txtBox" id="mibfileControl" />
                                                                    <input name="btnAddFile" type="submit" id="btnAddFile" value="Load Mib" class="frmButton"/></td>
                                                                </tr>
                                                                <tr>
                                                                    <td align="center" >&nbsp;</td>
                                                                </tr>

                                                                <tr >
                                                                    <td>
                                                                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="ListTable">
                                                                            <tr class="ListHeading">
                                                                                <td width="5%" colspan="5">List of Loaded Mib(s)</td>
                                                                            </tr>
                                                                            <tr class="ListHeading3">
                                                                                <td width="5%">#</td>
                                                                                <td width="35%" align="left">Mib Name</td>
                                                                                <td width="40%" align="left">Mib File Name</td>
                                                                                <td width="8%"> Variables</td>
                                                                                <td width="12%"><a href="<%= request.getContextPath()%>/secure/mibmanager?action=<%=MibManagerServlet.ACTION_DELETE_ALL_MIB%>">Delete All</a></td>
                                                                            </tr>
                                                                            <%
                                                                                for (int i = 0; i < mibFiles.size(); i++) {
                                                                            %>
                                                                            <tr style="background-color:<%= (i % 2 == 0) ? "white" : ""%>">
                                                                                <td><%= (i + 1)%></td>
                                                                                <td align="left"><%=mibFiles.get(i).getMibName()%></td>
                                                                                <td align="left"><%=mibFiles.get(i).getFileName()%></td>
                                                                                <td><%= mibFiles.get(i).getVariables().size()%></td>
                                                                                <td><a href="<%= request.getContextPath()%>/secure/mibmanager?action=<%=MibManagerServlet.ACTION_DELETE_MIB%>&file=<%=mibFiles.get(i).getFileName()%>&mib=<%=mibFiles.get(i).getMibName()%>">Delete</a></td>
                                                                            </tr>
                                                                            <%
                                                                                }
                                                                            %>
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </form>
                                        </td>
                                    </tr>
                                </table>
                            </td>
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
