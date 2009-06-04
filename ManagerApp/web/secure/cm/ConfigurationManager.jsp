<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*, java.util.*, org.ict.oamp.config.*, org.ict.oamp.cm.*, org.ict.oamp.manager.*, org.ict.oamp.utils.*, org.ict.util.*"  errorPage="" %>
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
<link href="<%=request.getContextPath()%>/resources/css/styles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/common.js"></script>
<script type="text/javascript">
	totalVariables = 0;
	editValue = null;
	indexString = "-1";
	
	<%
		if(selectedElement != null) {
			out.println("totalVariables = " + selectedElement.getConfigurationVariables().size() + ";");
		}
	%>
	
	function editVariable(btnEdit) {
		indexString = "-1";
		try {
			indexString = btnEdit.name.substring(7, btnEdit.name.length);
			parseInt(indexString);
		} catch (ex) {
			alert("Unknown Edit Request. Please reload the page and then Retry!");
			return;
		}
		saveButtonShow = "document.forms['frmControls" + indexString + "'].btnSave" + indexString + ".style.visibility = 'visible'";	
		cancelButtonShow = "document.forms['frmControls" + indexString + "'].btnCancel" + indexString + ".style.visibility = 'visible'";
        txtValueEnable = "document.forms['frmValues" + indexString + "'].txtValue" + indexString + ".disabled = false";
		txtValueText = "document.forms['frmValues" + indexString + "'].txtValue" + indexString + ".value";
		editValue = eval(txtValueText);
		for(i=0; i<totalVariables; i++) {
			editButtonHide = "document.forms['frmControls" + i + "'].btnEdit" + i + ".disabled = true";
			eval(editButtonHide);
		}
		eval(saveButtonShow);
		eval(cancelButtonShow);
		eval(txtValueEnable);
	}
	
	function cancelEdit(btnCancel) {
		indexString = "-1";
		try {
			indexString = btnCancel.name.substring(9, btnCancel.name.length);
			parseInt(indexString);
		} catch (ex) {
			alert("Unknown Request. Please reload the page and then Retry!");
			return;
		}
		btnCancel.style.visibility = "hidden";
		saveButtonHide = "document.forms['frmControls" + indexString + "'].btnSave" + indexString + ".style.visibility = 'hidden'";	
		txtValueDisable = "document.forms['frmValues" + indexString + "'].txtValue" + indexString + ".disabled = true";
		eval(saveButtonHide);
		eval(txtValueDisable);
		for(i=0; i<totalVariables; i++) {
			editButtonShow = "document.forms['frmControls" + i + "'].btnEdit" + i + ".disabled = false";
			eval(editButtonShow);
		}
		if(editValue != null) {
			txtValueText = "document.forms['frmValues" + indexString + "'].txtValue" + indexString + ".value='" + editValue + "'";
		} else {
			txtValueText = "document.forms['frmValues" + indexString + "'].txtValue" + indexString + ".value=" + null ;					
		}
		try {
			eval(txtValueText);
		} catch (ex) {
		}
	}
	
	function saveVariable(btnSave) {
		indexString = "-1";
		try {
			indexString = btnSave.name.substring(7, btnSave.name.length);
			parseInt(indexString);
		} catch (ex) {
			alert("Unknown Update Request. Please reload the page and then Retry!");
			return;
		}
		submitForm = "document.forms['frmValues" + indexString + "'].submit()";
		eval(submitForm);
	}
	
	function updateResults(success, value) {
		if(success) {
			saveButtonHide = "document.forms['frmControls" + indexString + "'].btnSave" + indexString + ".style.visibility = 'hidden'";	
			cancelButtonHide = "document.forms['frmControls" + indexString + "'].btnCancel" + indexString + ".style.visibility = 'hidden'";
			txtValueDisable = "document.forms['frmValues" + indexString + "'].txtValue" + indexString + ".disabled = true";
			txtValueReset = "document.forms['frmValues" + indexString + "'].txtValue" + indexString + ".value = " + value;
			for(i=0; i<totalVariables; i++) {
				editButtonHide = "document.forms['frmControls" + i + "'].btnEdit" + i + ".disabled = false";
				eval(editButtonHide);
			}
			eval(saveButtonHide);
			eval(cancelButtonHide);
			eval(txtValueDisable);
			eval(txtValueReset);
		} else {
			alert("Error occured while updating variabile:\n" + value);			
			txtValueReset = "document.forms['frmValues" + indexString + "'].txtValue" + indexString + ".value = " + editValue;
			eval(txtValueReset);
		}
	}

</script>
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
          <td colspan="3" class="BoxContents"><table cellpadding="0" cellspacing="0" width="65%" align="center" border="0">
              <tr>
                <td width="25%" class="Heading" nowrap>Configuration Manager </td>
                <td ><img src="<%=request.getContextPath()%>/resources/images/corner.gif"></td>
              </tr>
              <tr>
                <td colspan="2"><table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="FormTable">
                    <tr>
                      <td width="25%" valign="top"><span class="ListHeading2">Select an Element: (<a href="<%=request.getContextPath()%>/secure/configurationmanager">Refresh</a>)</span> <br/>
                        <div align="center" style="width:100%; vertical-align:top;  overflow:auto">
                          <%
			                for(int i=0; i<elements.size(); i++) {
            		        ElementManager element = (ElementManager) elements.get(i);
            			%>
                          <table width="100%" align="center">
                            <tr>
                              <td><a href="<%=request.getContextPath()%>/secure/configurationmanager?lstElements=<%=element.getElementId()%>">
                                <%
												if(element.getCategory() != null) {
											%>
                                <img src="<%= request.getContextPath()%>/secure/icons/<%= element.getCategory().getCategoryId()%>.bmp" height="50" width="50"/><br/>
                                <%= element.getElementIdentifier()%><br/>
                                <%= element.getDestinationAddresss()%>
                                <%
												}
											%>
                                </a> </td>
                            </tr>
                          </table>
                          <%
							}
						%>
                      </div></td>
                      <td rowspan="2" valign="top"><table width="100%" border="0" cellspacing="1" cellpadding="1">
                          <%
        if(selectedElement != null) {
            for(int i=0; i<selectedElement.getConfigurationVariables().size(); i++) {
                Vector <VarInfo> variables = selectedElement.getConfigurationVariables();
  %>
                          <tr>
                            <td width="20%" align="right"><%=CommonFunctions.capitalize(variables.get(i).getKey())%> :</td>
                            <td width="20%" align="left"><form id="frmValues<%=i%>" name="frmValues<%=i%>" method="post" action="<%=request.getContextPath()%>/secure/confupdate" target="frmResults">
                                <input name="txtValue<%=i%>" type="text" disabled="disabled" class="txtBox" id="txtValue<%=i%>" value="<%=variables.get(i).getValue()%>"/>
                                <input name="tag<%=i%>" type="hidden" value="<%=variables.get(i).getTag()%>"/>
                                <input name="type<%=i%>" type="hidden" value="<%=variables.get(i).getType()%>"/>
                                <input name="elementId<%=i%>" type="hidden" value="<%=selectedElement.getElementId()%>"/>
                            </form></td>
                            <td width="60%" align="left"><form id="frmControls<%=i%>" name="frmControls<%=i%>" method="post" action="#">
                                <input name="btnEdit<%=i%>" type="button" onclick="javascript:editVariable(this)" value="Edit" class="frmButton"/>
                                <input name="btnSave<%=i%>" type="button" style="visibility:hidden" onclick="javascript:saveVariable(this)" value="Save" class="frmButton"/>
                                <input name="btnCancel<%=i%>" type="button" style="visibility:hidden" onclick="javascript:cancelEdit(this)" value="Cancel" class="frmButton"/>
                            </form></td>
                          </tr>
                          <%		
  			}
  		}
  %>
                        </table></td>
                    </tr>
                    <!--<tr>
                      <td width="30%" align="center" valign="top" bgcolor="white"></td>
                    </tr>-->
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
<p>&nbsp;</p>
<iframe name="frmResults" id="frmResults" style="visibility:hidden"></iframe>
</body>
</html>
