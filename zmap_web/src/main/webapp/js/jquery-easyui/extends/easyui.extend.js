//extend by 2012-08-27 pengsuyun
$.extend({
	serializeForm:function(form){
		var o = {};
		$.each(form.serializeArray(), function(index) {
			if (o[this['name']]) {
				o[this['name']] = o[this['name']] + "," + this['value'];
			} else {
				o[this['name']] = this['value'];
			}
		});
		return o;
	}
});

//extend by 2012-08-27 pengsuyun
$.fn.datagrid.defaults.loader = function(param,success,error){
	var forms = $('form');
	var firstForm = null;
	if(forms.length > 0){
		firstForm = $(forms[0]);
	}
	
	if(firstForm){
		var queryObject = $.serializeForm(firstForm);
		if(queryObject){
			for(var attr in queryObject){
				param[attr] = queryObject[attr];
			}
		}
		
	}		

	var opts=$(this).datagrid("options");
	if(!opts.url){
		return false;
	}

	$.ajax({
			type:opts.method,
			url:opts.url,
			data:param,
			dataType:"json",
			success:function(data){
				success(data);
			},error:function(){
				error.apply(this,arguments);
			}
		}
	);
}

$.fn.datagrid.defaults.pageSize=20;

//extend by 2013-11-26 wusj
$.fn.combobox.defaults.filter = function(q, row){
									var opts = $(this).combobox('options');
									if(!row[opts.textField]){
										return false;
									}
									return row[opts.textField].indexOf(q) >= 0;
								}
$.fn.combogrid.defaults.filter = function(q, row){
									var opts = $(this).combogrid('options');
									if(!row[opts.textField]){
										return false;
									}
									return row[opts.textField].indexOf(q) >= 0;
								}

//extend by 2014-01-14 linjl ??combo??????
$.extend($.fn.validatebox.defaults.rules, {    
    comboRequied: {    
        validator: function(value,param){
        	if(param[0]) {
        		_value = $("#"+param[0]).combo('getValue');
        		return _value;
        	}else{
        		return true;
        	}
        },    
        message: $.fn.validatebox.defaults.missingMessage
    }    
});
