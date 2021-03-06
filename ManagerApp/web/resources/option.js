var selectedList;
var availableList;
function createListObjects(){
    availableList = document.getElementById("availableTypes");
    selectedList = document.getElementById("selectedTypes");
}
function delAttribute(){
    var selIndex = selectedList.selectedIndex;
    if(selIndex < 0)
        return;
    availableList.appendChild(selectedList.options.item(selIndex))
    selectNone(selectedList,availableList);
    setSize(availableList,selectedList);
}
function addAttribute(){
    var addIndex = availableList.selectedIndex;
    if(addIndex < 0)
        return;
    selectedList.appendChild(availableList.options.item(addIndex));
    selectNone(selectedList,availableList);
    setSize(selectedList,availableList);
}
function delAll(){
    var len = selectedList.length -1;
    for(i=len; i>=0; i--){
        availableList.appendChild(selectedList.item(i));
    }
    selectNone(selectedList,availableList);
    setSize(selectedList,availableList);
    
}
function addAll(){
    var len = availableList.length -1;
    for(i=len; i>=0; i--){
        selectedList.appendChild(availableList.item(i));
    }
    selectNone(selectedList,availableList);
    setSize(selectedList,availableList);
    
}
function selectNone(list1,list2){
    list1.selectedIndex = -1;
    list2.selectedIndex = -1;
    addIndex = -1;
    selIndex = -1;
}
function setSize(list1,list2){
    list1.size = getSize(list1);
    list2.size = getSize(list2);
}
function getSize(list){
    /* Mozilla ignores whitespace, IE doesn't - count the elements in the list */
    var len = list.childNodes.length;
    var nsLen = 0;
    //nodeType returns 1 for elements
    for(i=0; i<len; i++){
        if(list.childNodes.item(i).nodeType==1)
            nsLen++;
    }
    if(nsLen<2)
        return 2;
    else
        return nsLen;
}
function showSelected(){
    var optionList = document.getElementById("selectedTypes").options;
    var data = '';
    var len = optionList.length;
    for(i=0; i<len; i++){
        if(i>0)
            data += ',';
        data += optionList.item(i).value;
    }
    alert(data);
}
