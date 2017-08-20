/**
 *	readme: please use charset gbk secret use this file
 */

var editIndex = undefined;
var currentIndex = 0;
function endEditing(table){
	if(editIndex == undefined){return true}
	if(table.datagrid('validateRow',editIndex)){
		table.datagrid('endEdit',editIndex);
		table.datagrid('unselectRow',editIndex);
		editIndex = undefined;
		return true;
	}else{
		return false;
	}
}

function appendRow(table,newRow){
	var cloneRow = {};
	$.extend(cloneRow,newRow);
	
	//先判断有没有选中行，有的话，在选中行后面添加
	var checkedRows = table.datagrid("getChecked");
	
	if(checkedRows && checkedRows.length > 0){
		var index = table.datagrid("getRowIndex",checkedRows[checkedRows.length - 1]);
		currentIndex = index+1;
		table.datagrid("insertRow",{index:window.currentIndex,row:cloneRow});
	}else{
		table.datagrid("appendRow",cloneRow);
		currentIndex = table.datagrid("getRowIndex",cloneRow);
	}
}

function removeRow(table,newRow){
	var rows = table.datagrid("getChecked");
	for(var i = 0 ; i < rows.length ; i++){
		if($.trim(rows[i].id) == ""){
			var rowIndex = table.datagrid("getRowIndex",rows[i]);
			table.datagrid("deleteRow",rowIndex);
		}
	}
	
	var allData = table.datagrid("getData");
	if(allData.total == 0){
		appendRow(table,newRow);
	}
}
//table:datagrid table
//jsonData:传送列表数据到后台使用的隐藏域
function submit(table,jsonData,editForm){
	var allData = table.datagrid("getData");
		
	endEditing(table);
	
	table.datagrid('acceptChanges');
	
	var rows = allData.rows;

	if(!validateRows(table,rows)){
		return;
	}
	
	jsonData.val($.toJSON(allData.rows));

	editForm.submit();
}

//验证后的处理
function validateHandler(table,row,field,content){
	//默认选中该行，打开该行的编辑状态，提示需要调整数据，提示需要编辑
	var rowIndex = table.datagrid("getRowIndex",row);
	table.datagrid("selectRow",rowIndex);
	table.datagrid("beginEdit",rowIndex);
	
	editorFocus(table,rowIndex,field);
	
	$.messager.show({
		title:'错误提示',
		msg:content,
		timeout:3000
	});
}

function validateNulls(table,row,fieldArr,content){
	for(var i = 0 ; i < fieldArr.length ; i++){
		var field = fieldArr[i];
		if(validateNull(table,row,field,content)){
			return true;
		}
	}
	return false;
}

function validateNullsAndArrMsg(table,row,fieldArr,content){
	for(var i = 0 ; i < fieldArr.length ; i++){
		var field = fieldArr[i];
		if(validateNull(table,row,field,content[i])){
			return true;
		}
	}
	return false;
}

function validateNull(table,row,field,content){
	if($.trim(row[""+field+""]) == ""){
		var rowIndex = table.datagrid("getRowIndex",row);
		table.datagrid("selectRow",rowIndex);
		table.datagrid("beginEdit",rowIndex);
		
		editorFocus(table,rowIndex,field);
		
		alertErrorMsg(content);
		
		return true;
	}else{
		return false;
	}
}

function alertErrorMsg(content){
	$.messager.show({
		title:'错误提示',
		msg:content,
		timeout:3000
	});
}

function formatterBirthday(value,row,index){
	if(value == undefined){
		return "&nbsp;";
	}else{
		return value.substr(0,10);
	}
}

function initFormatterFunc(attrNameArr,dataArr){
	var formaterScript = "<script type='text/javascript'>";
	for(var i = 0 ; i < attrNameArr.length ;i++){
		var attr = attrNameArr[i];
		
		var attrArr = attr + "Arr";
		//根据属性生成js数组对象
		window[attrArr] = [];
		
		//往js数组对象中设置初始值
		var attrData = dataArr[i];
		attrData = attrData.replace(/&#034;/gi,'"');
		
		if($.trim(attrData) != ""){
			var _dataArr = $.parseJSON(attrData);
			
			parseData(window[attrArr],_dataArr);
			
		}
		
		//动态生成js formatter函数
		var funcName = "formatter" + attr.substring(0,1).toUpperCase() + attr.substring(1);
		
		formaterScript += "window['"+funcName+"'] = function "+funcName+"(value){"+
			"for(var i = 0 ; i < window['"+attrArr+"'].length ;i++){"+
			"	if(window['"+attrArr+"'][i].itemValue && window['"+attrArr+"'][i].itemValue == value){"+
			"		return window['"+attrArr+"'][i].itemName;"+
			"	}"+
			"	if(window['"+attrArr+"'][i].id && window['"+attrArr+"'][i].id == value){"+
			"     	if(window['"+attrArr+"'][i].text){"+
			"			return window['"+attrArr+"'][i].text;"+
			"		}"+		
			"     	if(window['"+attrArr+"'][i].name){"+
			"			return window['"+attrArr+"'][i].name;"+
			"		}"+	
			"	}"+
			"}"+	
			"return value;};";
	}
	formaterScript += "</script>";

	$("head").append(formaterScript);
}

//解析部门列表
function parseData(contain,dataArr){
	for(var i = 0 ; i < dataArr.length ; i++){
		var dataObj = dataArr[i];
	
		contain.push(dataObj);
		
		if(dataObj.children && dataObj.children.length > 0){
			parseData(contain,dataObj.children);
		}
	}
}

function initDataGrid(table,jsonData){
	jsonData = jsonData.replace(/&#034;/gi,'"');
	table.datagrid("loadData",$.parseJSON(jsonData));
	
	table.datagrid({
		onDblClickCell:function(rowIndex,field,value){
			currentIndex = rowIndex;
			if(rowIndex == editIndex){
				return false;
			}
			
			var datagrid = $(this);
			
			datagrid.datagrid("beginEdit",rowIndex);
			editorFocus(datagrid,rowIndex,field);
		}
	});
}

//聚焦
function editorFocus(table,rowIndex,field){

	var editor = null;
	if(arguments.length < 3){
		editor = table.datagrid('getEditors', rowIndex)[0];
	}else{
		editor = table.datagrid('getEditor', {index:rowIndex,field:field});
	}
	if(!editor){
		editor = table.datagrid('getEditors', rowIndex)[0];
	}
	
	if(!editor){
		return;
	}
	
	endEditing(table);
	
	editIndex = rowIndex;
}

//选择部门名称树的叶子节点
function onBeforeSelectLeafNode(node){
	var isLeaf = $(this).tree("isLeaf",node.target);
	if(!isLeaf){
		return false;
	}
}

//选择部门名称后的处理
function onSelectDeptName(node){

	var table = $("#datagrid");
	var allData = table.datagrid("getData");
	var rows = allData.rows;
	
	//该值在datagrid的onDblClickCell中赋值
	if(currentIndex > -1){
		rows[currentIndex].pid = node.id;
		rows[currentIndex].deptName = node.text;
	}
}

//选择处室名称后的处理
function onSelectBureau(rec){
	var table = $("#datagrid")
	var allData = table.datagrid("getData");
	var parent = $(this).parents("tr.datagrid-row");
	var currentIndex = parent.attr("datagrid-row-index");
	var rows = allData.rows;
	if(currentIndex > -1){
		rows[currentIndex].bureauId = rec.id+"";
	}
}

/**
 * 功能：文件图片大小的格式化
 */
function formatterfileSize(value,row,index){
	if(!value){
		return '0 Bytes';
	}
	
	var sizeNames = [' Bytes',' KB',' MB',' GB',' TB',' PB',' EB',' ZB',' YB'];
	var i = Math.floor(Math.log(value)/Math.log(1024));
	var p = (i>1) ? 2 : 0;
	return (value/Math.pow(1024,Math.floor(i))).toFixed(p) + sizeNames[i];
}
