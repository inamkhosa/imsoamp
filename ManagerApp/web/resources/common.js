// JavaScript Document

function setAllCheckBoxes(FormName, FieldName, CheckValue)
{
	if(!document.forms[FormName]) {
		return;
	}

	var objCheckBoxes = document.forms[FormName].elements[FieldName];
	if(!objCheckBoxes) {
		return;	
	}
	
	var countCheckBoxes = objCheckBoxes.length;
	
	if(!countCheckBoxes) {
		objCheckBoxes.checked = CheckValue;
	} else {
		// set the check value for all check boxes
		for(var i = 0; i < countCheckBoxes; i++){
			objCheckBoxes[i].checked = CheckValue;	
		}
	}
}

function getStats(type) {
	document.forms['frmVariables'].cached.value = type;
	document.forms['frmVariables'].submit();
}

function editVariable(btnEdit) {
	alert(btnEdit.value);	
}


