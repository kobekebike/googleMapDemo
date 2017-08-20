/**
 *	readme: please use charset gbk
 */

var currentIndex = -1;

function appendRow(table,newRow){
	var cloneRow = {};
	$.extend(cloneRow,newRow);
	
	//���ж���û��ѡ���У��еĻ�����ѡ���к������
	
	
	var checkedRows = table.datagrid("getChecked");
	if(checkedRows && checkedRows.length > 0){
		var index = table.datagrid("getRowIndex",checkedRows[checkedRows.length - 1]);
		table.datagrid("insertRow",{index:index+1,row:cloneRow});
	}else{
		table.datagrid("appendRow",cloneRow);
	}
	var rowIndex = table.datagrid("getRowIndex",cloneRow);
	currentIndex = rowIndex;
	table.datagrid("beginEdit",rowIndex);
	editorFocus(table,rowIndex);
}

function removeRow(table,newRow){
	var rows = table.datagrid("getChecked");
	for(var i = 0 ; i < rows.length ; i++){
		if(rows[i].id == null || rows[i].id == ""){
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
//jsonData:�����б����ݵ���̨ʹ�õ�������
function submit(table,jsonData,editForm){
	var allData = table.datagrid("getData");
	
	table.datagrid('acceptChanges');
	
	var rows = allData.rows;

	if(!validateRows(table,rows)){
		return;
	}
	
	jsonData.val($.toJSON(allData.rows));
	editForm.submit();
}

function validateNulls(table,row,fieldArr,titleArr){
	for(var i = 0 ; i < fieldArr.length ; i++){
		var field = fieldArr[i];
		if(validateNull(table,row,field,"\""+titleArr[i]+"\" ����Ϊ�գ�")){
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
		title:'������ʾ',
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
		//������������js�������
		window[attrArr] = [];
		
		//��js������������ó�ʼֵ
		var attrData = dataArr[i];
		attrData = attrData.replace(/&#034;/gi,'"');
		
		if($.trim(attrData) != ""){
			var _dataArr = $.parseJSON(attrData);
			
			parseData(window[attrArr],_dataArr);
			
		}
		
		//��̬����js formatter����
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

//���������б�
function parseData(contain,dataArr){
	for(var i = 0 ; i < dataArr.length ; i++){
		var dataObj = dataArr[i];
	
		contain.push(dataObj);
		
		if(dataObj.children && dataObj.children.length > 0){
			parseData(contain,dataObj.children);
		}
	}
}

//�۽�
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
	}else{
		$(editor.target).focus();
	}
}
/**
 * ���ܣ��ļ�ͼƬ��С�ĸ�ʽ��
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
