;(function($){
	function setSize(target){
		var opts = $.data(target, 'ocr').options;
		var t = $(target);
		var _object = t.find("object");
		if(opts.fit) {
			_object.width(t.width());
			_object.height(t.height());
		}else{
			_object.width(t.width);
			_object.height(t.height);
		}
	}
	
	
	function init(target){
		var opts = $.data(target, 'ocr').options;
		opts.onBeforeInit.call(this);
		//????object
		$(target).find("object").remove();
		$(target)[0].innerHTML = '<object classid="'+opts.classid+'"  > </object>';
		opts.onAfterInit.call(this);
		setSize(target);
		$(target).bind('_resize', function(){
			var opts = $.data(target, 'ocr').options;
			if (opts.fit == true){
				setSize(target);
			}
			return false;
		});
		
		//????
		$(target).find("object")[0].attachEvent('AreaRecogWithBookMark',function(_a,_b,_c){
			opts.areaRecogWithBookMark.call(this,_a,_b,_c);
		})
		$(target).find("object")[0].attachEvent('ImageRecog',function(_a,_b,_c){
			opts.imageRecog.call(this,_a,_b,_c);
		})
		$(target).find("object")[0].attachEvent('AreaRecog',function(_a,_b){
			opts.areaRecog.call(this,_a,_b);
		})
	};
	
	
	$.fn.ocr = function(options, param){
		if (typeof options == 'string'){
			if($.fn.ocr.methods[options]) {
				return $.fn.ocr.methods[options](this, param);
			}else{
				return alert("OCR插件不支持的方法！");
			}
		}
		
		options = options || {};
		return this.each(function(){
			var state = $.data(this, 'ocr');
			if (state){
				$.extend(state.options, options);
			} else {
				state = $.data(this, 'ocr', {
					options:$.extend({}, $.fn.ocr.defaults, $.fn.ocr.parseOptions(this), options)
				});
			}
			init(this);
		});
	};
	
		
	function saveAs(target,myPath,iType) {
		try{
			$(target).find("object")[0].SaveAs(myPath,iType);
		}catch(e){}
	}
	
	/**
	 * 功能：隐藏按钮
	 * @param _array 隐藏按钮的数组
	 */
	function setButtonHide(target,_array){
		try{
			if($.isArray(_array)) {
				$.each(_array,function(i,n){
					$(target).find("object")[0].SetButtonHide(n);
				})
			}
		}catch(e){}
	}
	
	/**
	 * 功能：显示按钮
	 * @param _array 显示按钮的数组
	 */
	function setButtonShow(target,_array){
		try{
			if($.isArray(_array)) {
				$.each(_array,function(i,n){
					$(target).find("object")[0].SetButtonShow(n);
				})
			}
		}catch(e){}
	}
	
	function setSignName(target,_array){
		try{
			if($.isArray(_array)) {
				var str = _array.join(";");
				$(target).find("object")[0].SetSignName(str);
			}
		}catch(e){}
	}
	
	//打开文件
	function openFile(target,filePath) {
		try{
			$(target).find("object")[0].OpenFile(filePath);
		}catch(e){}
	}
	
	/**
	 * 获取当前文件的路径
	 */
	function getCurPath(target){
		try{
			return $(target).find("object")[0].GetCurPath();
		}catch(e){}
	}
	
	$.fn.ocr.methods = {
		resize: function(jq){
			return jq.each(function(){
				setSize(this);
			});
		},
		saveAs:function(jq, param){
			if(param) {
				return jq.each(function(){
					saveAs(this,param.strFile,param.iType);
				});
			}
		},
		setSignName:function(jq,_array){
			return jq.each(function(){
				setSignName(this,_array);
			});
		},
		getCurPath:function(jq) {
			return getCurPath(jq[0]);
		},
		setButtonHide:function(jq,_array){
			return jq.each(function(){
				setButtonHide(this,_array);
			});
		},
		setButtonShow:function(jq,_array){
			return jq.each(function(){
				setButtonShow(this,_array);
			});
		},
		openFile:function(jq,_path) {
			return jq.each(function(){
				openFile(this,_path);
			});
		}
	};
	
	$.fn.ocr.parseOptions = function(target){
		var t = $(target);
		return $.extend({}, $.parser.parseOptions(target, 
			['classid','codebase',{fit:'boolean'}]));
	};
	
	//???
	$.fn.ocr.defaults = {
		classid:"clsid:1F9FFFF5-571C-4070-BBAD-BE9C59E6902B",
		codebase:"./WebOcr.cab#version=1,1,0,5",
		onBeforeInit:function(){},
		fit:false,
		openurl:'',
		saveurl:'',
		width:800,
		height:500,
		onAfterInit:function(){},
		areaRecogWithBookMark:function(reogResult,strBookMark,recogFilePath){},
		imageRecog:function(iResult,imgfile,tagfinish){},
		areaRecog:function(iResult ,imgfile){}
	};
	
	//??????easyui?
	$.parser.plugins.push("ocr");
})(jQuery);