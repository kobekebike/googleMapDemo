
//判断是否金额
function isMoney(s) {
    var fmtstr = s.toString().replace(/[^\d\.-]/g, "");
    var regu = /^([+-]?)((\d{1,3}(,\d{3})*)|(\d+))(\.\d{0,4})?$/;
    var re = new RegExp(regu);
    if (re.test(fmtstr)) {
        return true;
    }
    return false;
}

/* 
功能：将货币数字（阿拉伯数字）(小写)转化成中文(大写） 
    
参数：Num为字符型,小数点之后保留两位,例：Arabia_to_Chinese("1234.06") 
说明：1.目前本转换仅支持到 拾亿（元） 位，金额单位为元，不能为万元，最小单位为分 
2.不支持负数 
*/
function numtochinese(Num) {
    for (i = Num.length - 1; i >= 0; i--) {
        Num = Num.replace(",", "")//替换tomoney()中的“,” 
        Num = Num.replace(" ", "")//替换tomoney()中的空格 
    }

    Num = Num.replace("￥", "")//替换掉可能出现的￥字符 
    if (isNaN(Num)) {
        //验证输入的字符是否为数字 
        alert("请检查小写金额是否正确");
        return;
    }
    //---字符处理完毕，开始转换，转换采用前后两部分分别转换---// 
    part = String(Num).split(".");
    newchar = "";
    //小数点前进行转化 
    for (i = part[0].length - 1; i >= 0; i--) {
        if (part[0].length > 10) { alert("位数过大，无法计算"); return ""; } //若数量超过拾亿单位，提示 
        tmpnewchar = ""
        perchar = part[0].charAt(i);
        switch (perchar) {
            case "0": tmpnewchar = "零" + tmpnewchar; break;
            case "1": tmpnewchar = "壹" + tmpnewchar; break;
            case "2": tmpnewchar = "贰" + tmpnewchar; break;
            case "3": tmpnewchar = "叁" + tmpnewchar; break;
            case "4": tmpnewchar = "肆" + tmpnewchar; break;
            case "5": tmpnewchar = "伍" + tmpnewchar; break;
            case "6": tmpnewchar = "陆" + tmpnewchar; break;
            case "7": tmpnewchar = "柒" + tmpnewchar; break;
            case "8": tmpnewchar = "捌" + tmpnewchar; break;
            case "9": tmpnewchar = "玖" + tmpnewchar; break;
        }
        switch (part[0].length - i - 1) {
            case 0: tmpnewchar = tmpnewchar + "元"; break;
            case 1: if (perchar != 0) tmpnewchar = tmpnewchar + "拾"; break;
            case 2: if (perchar != 0) tmpnewchar = tmpnewchar + "佰"; break;
            case 3: if (perchar != 0) tmpnewchar = tmpnewchar + "仟"; break;
            case 4: tmpnewchar = tmpnewchar + "万"; break;
            case 5: if (perchar != 0) tmpnewchar = tmpnewchar + "拾"; break;
            case 6: if (perchar != 0) tmpnewchar = tmpnewchar + "佰"; break;
            case 7: if (perchar != 0) tmpnewchar = tmpnewchar + "仟"; break;
            case 8: tmpnewchar = tmpnewchar + "亿"; break;
            case 9: tmpnewchar = tmpnewchar + "拾"; break;
        }
        newchar = tmpnewchar + newchar;
    }
    //小数点之后进行转化 
    if (Num.indexOf(".") != -1) {
        if (part[1].length > 2) {
            alert("小数点之后只能保留两位,系统将自动截段");
            part[1] = part[1].substr(0, 2)
        }
        for (i = 0; i < part[1].length; i++) {
            tmpnewchar = ""
            perchar = part[1].charAt(i)
            switch (perchar) {
                case "0": tmpnewchar = "零" + tmpnewchar; break;
                case "1": tmpnewchar = "壹" + tmpnewchar; break;
                case "2": tmpnewchar = "贰" + tmpnewchar; break;
                case "3": tmpnewchar = "叁" + tmpnewchar; break;
                case "4": tmpnewchar = "肆" + tmpnewchar; break;
                case "5": tmpnewchar = "伍" + tmpnewchar; break;
                case "6": tmpnewchar = "陆" + tmpnewchar; break;
                case "7": tmpnewchar = "柒" + tmpnewchar; break;
                case "8": tmpnewchar = "捌" + tmpnewchar; break;
                case "9": tmpnewchar = "玖" + tmpnewchar; break;
            }
            if (i == 0) tmpnewchar = tmpnewchar + "角";
            if (i == 1) tmpnewchar = tmpnewchar + "分";
            newchar = newchar + tmpnewchar;
        }
    }
    //替换所有无用汉字 
    while (newchar.search("零零") != -1)
        newchar = newchar.replace("零零", "零");
    newchar = newchar.replace("零亿", "亿");
    newchar = newchar.replace("零万", "万");
    newchar = newchar.replace("零元", "元");
    newchar = newchar.replace("亿万", "亿");
    newchar = newchar.replace("零角", "");
    newchar = newchar.replace("零分", "");

    if (newchar.charAt(newchar.length - 1) == "元" || newchar.charAt(newchar.length - 1) == "角")
        newchar = newchar + "整"
    return newchar;
}

/**
 * add by ligz
 *ajax 提交form表单
 * @param formid
 * @param url
 */
function ajaxFormSubmit(formid,url,async)
{
	var retResult;
	$("#"+formid).ajaxSubmit({
 		url: url,
 		async: async,
 		success:function(result) {
 			retResult = result;
 		},
 		error:function(XMLHttpRequest, textStatus, errorThrown) {
 			alert("form表单提交失败");
 		}
	});
	return retResult;
}

/**
 * add by ligz
 * ajax 请求获取数据
 * @param url
 * @param formdata
 * @param json
 * @returns
 */
function requestAjaxData(url, async, json) {
    var result; 
    var dataType = "json";
    if(!json)
    	dataType = "text";
    $.ajax({
        type: 'post',
        url: url,
        async: async,
        dataType: dataType,
        success: function (datas) {
            result = datas;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        	alert("requestAjaxData请求失败");
        }
    });
    return result;
}

function requestAjaxDataWithJson (url, async, json, jsonData) {
    var result;
    var dataType = "json";
    if(!json)
        dataType = "text";
    $.ajax({
        type : 'post',
        url : url,
        async: async,
        data: jsonData,
        dataType : dataType,
        success : function (datas) {
            result = datas;
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            console.log("requestAjaxDataAsync请求失败");
        }
    });

    return result;

}

function requestAjaxDataAsync(url, json, $success) {
    var dataType = "json";
    if (!json)
        dataType = "text";
    $.ajax({
        type : 'post',
        url : url,
        dataType : dataType,
        success : $success,
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            console.log("requestAjaxDataAsync请求失败");
        }
    });
}

/**
 * add by ligz
 * 正则表达式将前后空格
 * @param str
 * @returns
 */
function strTrim(str) {
	if(str){
		str = str.replace(/(^\s*)|(\s*$)/g, "");
		str = str.replace(/　/g, "");
		return str;
	}
}

/**
 * 清空form表单数据
 */
function clearForm(){
	$('input').val("");
}

/**
 * 单个多选按钮的选择影响全选的选择
 * @param checkboxArrayId 多选集合的id，通过这个id查找到所有全选涉及的多选
 * @param checkboxAll 全选id
 * @param isChecked 当前（单个多选）是否选中
 */
function selectOneAndAll(checkboxArrayId, checkboxAll, isChecked) {
    var flag = true;
    $('#'+checkboxArrayId).each(function (index, value) {
        if ($(value).prop('checked') != isChecked) {
            flag = false;
            return;
        }
    });
    if (flag && isChecked) { // 所有多选状态一直，并且都是选中的状态
        $('#'+checkboxAll).prop('checked', true);
    } else {
        $('#'+checkboxAll).prop('checked', false);
    }
}

/**
 * 格式化 已被Java序列号化的Date 为 yyyy-MM-dd
 * @param dateStr
 */
function formatDateToYMD(dateStr) {
    return new Date(dateStr).toLocaleDateString().replace(/\//g, '-');
}

function formatDate2YMD(strDate) {
	if (strDate != null && strDate != "") {
		var myDate = new Date(strDate.replace("-", "/").replace("-", "/"));
		var year = myDate.getFullYear();
		var month = myDate.getMonth() + 1;
		var day = myDate.getDay();
		month = month < 10 ? "0" + month : month;
		day = day < 10 ? "0" + day : day;
		return year + "-" + month + "-" + day;

	} else {
		return "";
	}

}

/** 提示框，限于提示功能 */
function message(tip){
    layer.open({
        content: tip,
        icon: 0,
        btn: ['确定'],
        yes: function(index, layero){
            layer.close(index);
        }
    });
}
/**  渲染图片 */
function readerAttach(ctxPathName ,attachment, renderPidId) {
    var picHtml = "";
    picHtml += "<div style=\"overflow: hidden\" id=\"pic" + attachment.attachmentId+ "\"";
    picHtml +="class=\"file-item thumbnail upload-state-done\">";
    picHtml += "<image";
    picHtml +=" src=\"" + ctxPathName + attachment.attachmentUrl +"\" />";
    picHtml += "<div class=\"info\">";
    picHtml += "<a class=\"cancel\" href=\"javascript:;\" onclick=\"toDelete('" + attachment.attachmentId + "',1)\">删除</a>";
    picHtml += "</div>";
    picHtml += "</div>";

    $("#"+renderPidId).append(picHtml);
}
/**  渲染多张图片 */
function readerAttachList(ctxPathName ,attachmentList, renderPidId) {
    for (index in attachmentList) {
        readerAttach(ctxPathName ,attachmentList[index], renderPidId)
    }
}

function Set() {
    this.elements = new Array();
    /** 获取Set元素个数 */
    this.size = function() {
        return this.elements.length;
    }

    /** 判断Set是否为空 */
    this.isEmpty = function() {
        return (this.elements.length < 1);
    }

    /** 清除Set */
    this.clear = function() {
        this.elements = new Array();
    }
    /** 增加一个元素，不重复 */
    this.add = function(value) {
        //alert(this.containsKey(_key));
        if(this.containsValue(value)){
            this.remove(value);
        }
        this.elements.push(value);
    }
    /** 移除一个值 */
    this.remove = function(value) {
        var bln = false;
        try {

            for (i = 0; i < this.elements.length; i++) {

                if (this.elements[i] == value) {

                    this.elements.splice(i, 1);

                    return true;
                }
            }
        } catch (e) {
            bln = false;
        }
        return bln;
    }

    /** 移除一个值,索引 */
    this.kill=function (index){
        this.remove(this.get(index));
    }

    /** 得到一个值,索引 */
    this.get = function(_index) {
        if (_index < 0 || _index >= this.elements.length) {
            return null;
        }
        return this.elements[_index];
    }

    /** 查看是否包含一个值 */
    this.containsValue = function(value) {
        var bln = false;
        try {
            for (i = 0; i < this.elements.length; i++) {
                if (this.elements[i] == value) {
                    bln = true;
                }
            }
        } catch (e) {
            bln = false;
        }
        return bln;
    }
}

function HashMap() {
    /** Map 大小 **/
    var size = 0;
    /** 对象 **/
    var entry = new Object();

    /** 存 **/
    this.put = function (key, value) {
        if (!this.containsKey(key)) {
            size++;
        }
        entry[key] = value;
    }

    /** 取 **/
    this.get = function (key) {
        if (this.containsKey(key)) {
            return entry[key];
        }
        else {
            return null;
        }
    }

    /** 删除 **/
    this.remove = function (key) {
        if (delete entry[key]) {
            size--;
        }
    }

    /** 清空Map */
    this.removeAll = function() {
        size = 0;
        entry = new Object();
    };

    /** 是否包含 Key **/
    this.containsKey = function (key) {
        return (key in entry);
    }

    /** 是否包含 Value **/
    this.containsValue = function (value) {
        for (var prop in entry) {
            if (entry[prop] == value) {
                return true;
            }
        }
        return false;
    }

    /** 所有 Value **/
    this.values = function () {
        var values = new Array();
        for (var prop in entry) {
            values.push(entry[prop]);
        }
        return values;
    }

    /** 所有 Key **/
    this.keys = function () {
        var keys = new Array();
        for (var prop in entry) {
            keys.push(prop);
        }
        return keys;
    }

    /** Map Size **/
    this.size = function () {
        return size;
    }
}

function getSystemEnv(ctxPathName,key) {
	var url = "/view/sysEnv.do?method=getSystemEnv&key=" + key;
	var result = requestAjaxData(ctxPathName + url, false, false);
	return result;
}

/**
 * 根据ParentId 获取list
 * @param ctxPathName
 * @param key
 * @returns
 */
function getDictionaryByPId(ctxPathName, key, keyword) {
	var url = ctxPathName+"/view/dictionary.do?method=getDictListByPId";
	var result = requestAjaxDataWithJson(url, false, true, {
        "parentId" : key,
        "keyword" : keyword
    });
	return result;
}

/**
 * 渲染下拉框
 * @param id Select的id
 * @param list 结果集
 * @param val 默认选中的值，没有则为null
 */
function ergodicOption(id,list,val){
	var html = "";
	if(val != null && val != ""){
		for(var i = 0; i < list.length; i++){
			html += "<option value=\""+ list[i].dictionaryValue + "\"";
			if(val == list[i].dictionaryValue)
				html += " selected=\"selected\"";
			html += ">" + list[i].dictionaryName + "</option>";
		}
	}else{
		for(var i = 0; i < list.length; i++){
			html += "<option value=\""+ list[i].dictionaryValue + "\">" + list[i].dictionaryName + "</option>";
		}
	}
	
	$("#" + id).append(html);
}

/**
 * 选择省份和市区
 * 
 * @param variable
 * @param ctxPathName
 */
function loadProvince(variable, ctxPathName) {
	var provinceData = requestAjaxData(ctxPathName + "/province.do", false,
			true);
	if (provinceData != null) {
		var selectHtml1 = "";
		for (var i = 0; i < provinceData.length; i++) {
			selectHtml1 += "<option id=\"province" + provinceData[i].provinceId
					+ "\" value=\"" + provinceData[i].provinceId + "\">"
					+ provinceData[i].provinceName + "</option>";
		}
		$("#" + variable).html(selectHtml1);

	}
}
function loadCity(obj, citySelectId) {
	var provinceId = $(obj).val();
	var cityData = requestAjaxData(ctxPathName + "/city.do?provinceId="
			+ provinceId, false, true);
	if (cityData != null) {
		var cityHtml = "";
		for (var i = 0; i < cityData.length; i++) {
			cityHtml += "<option id=\"city" + cityData[i].cityId
					+ "\" value=\"" + cityData[i].cityId + "\">"
					+ cityData[i].cityDesc + "</option>";
		}
		$("#" + citySelectId).html(cityHtml);
	}
}

function getFilePathAddress(ctxPathName) {
	var url = ctxPathName + "/view/attachment.do?method=getFilePathAddress";
	var data = requestAjaxData(url, false, true);
	if (data != null) {
		return data.value;
	} else
		return "";
}
function OpenNewWindow(url)
{
    window.open(url, '', 'top=0,left=0,width=' + (window.screen.availWidth) + ',height=' + (window.screen.availHeight) + ',toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no');
}
function DoCancel() {
	window.close();
	//刷新父级页面
	window.opener.location.href=window.opener.location.href;    
}
/**
 * 获取当前时间
* */
function getNowFormatDate() {
	var date = new Date();
	var seperator1 = "-";
	var seperator2 = ":";
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
		+ " " + date.getHours() + seperator2 + date.getMinutes()
		+ seperator2 + date.getSeconds();
	return currentdate;
}

function getJsonValue(json , name){
    for(name in json){
        return json[name];
    }
}

function requestAjaxDataByQuery(url, isJson, data) {
    var dataType = "json";
    var result; 
    if (!isJson)
        dataType = "text";
    $.ajax({
        type : 'post',
        url : url,
        async: false,
        data:data,
        dataType : dataType,
        success: function (datas) {
            result = datas;
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            console.log("requestAjaxDataAsync请求失败");
        }
    });
    return result;
}

function serializeForm(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};
