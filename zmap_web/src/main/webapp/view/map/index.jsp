<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page isELIgnored ="false" %>
<%@page import="java.net.URLDecoder" %>
<%
    String ctxPathName = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<html>
<!-- <script src="http://ditu.google.cn/maps?file=api&v=3&key=AIzaSyBrRBLVg_bH4N0eADAxzPOKCpxi28C_0VQ&sensor=false" type="text/javascript"></script>
 -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>谷歌地图演示实例</title>
<link rel="stylesheet" href="<%=ctxPathName %>/js/jquery-easyui/themes/bootstrap/easyui.css">
<script src="http://maps.google.cn/maps/api/js?language=zh-TW&v=3.exp&key=AIzaSyCoy86qvSNN4A1nEA5Kg-jVdNNjjyVbyFI&libraries=places,drawing,geometry" type="text/javascript"></script>
<script src="<%=ctxPathName %>/js/jquery-2.1.3.min.js" type="text/javascript"></script>
<script src="<%=ctxPathName%>/js/common.js" type="text/javascript"></script>
<script src="<%=ctxPathName %>/js/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="<%=ctxPathName %>/js/jquery-easyui/extension/edatagrid/jquery.edatagrid.js" type="text/javascript"></script>
</head>
<body>
	<div  class="easyui-layout" data-options="fit:true">   
	    <div data-options="region:'west',split:true,minWidth:180" style="width:400px;">
	    	<div  class="easyui-accordion" data-options="fit:true,border:false" >   
			    <div title="實例1：地址搜索" data-options="" >   
			    	 <div class="form-group">
						<div class="col-sm-12" >
							<input type="text" id="autocomplete" style="width:100%">
						</div>
					</div>
					
					<div class="form-group" >
						<div class="col-sm-12" >
							&nbsp;&nbsp;纬度：<input class="field"
              id="latAutoFill" disabled="true"></input></td>
        <td class="label">
						</div>
					</div>
					<div class="form-group" >
						<div class="col-sm-12" >
							&nbsp;&nbsp;经度：<input class="field"
              id="lngAutoFill" disabled="true">
						</div>
					</div>
<!-- 
					<div class="form-group" >
						<div class="col-sm-4" >
							Zip code
						</div>
						<div class="col-sm-8" >
							<input class="field" id="postal_code"
              disabled="true">
						</div>
					</div>
					<div class="form-group" >
						<div class="col-sm-4" >
							room
						</div>
						<div class="col-sm-8" >
							<input class="field" id="room"
              disabled="true">
						</div>
					</div>
					<div class="form-group" >
						<div class="col-sm-4" >
							floor
						</div>
						<div class="col-sm-8" >
							<input class="field" id="floor"
              disabled="true">
						</div>
 					</div>
			-->		
			    </div>   
			    <div title="實例2：選區設置"  >   
			        <table id="areadg"></table>     
			    </div>   
			    <div title="實例3：地址和地理座標轉換">   
			        <div class="form-group" >
						<div class="col-sm-4" >
							地址
						</div>
						<div class="col-sm-8" >
							<input type="text" class="form-control" id="address" name="address" placeholder="請輸入地址">
						</div>
					</div>
					<div class="form-bottom">
					<button class="btn btn-form headBtn determine" id="submitId" margin-right: 10px;">地理編碼</button>
					<span id="lngDetail">經緯度詳細信息：</span>
					</div>
					<div class="form-group" >
						<div class="col-sm-4" >
							經度
						</div>
						<div class="col-sm-8" >
							<input type="text" class="form-control" id="lng" name="lng" placeholder="请输入經度">
						</div>
					</div>
					<div class="form-group" >
						<div class="col-sm-4" >
							緯度
						</div>
						<div class="col-sm-8" >
							<input type="text" class="form-control" id="lat" name="lat" placeholder="请输入緯度">
						</div>
					</div>
					<div class="form-bottom">
					<button class="btn btn-form headBtn determine" id="submitId1" margin-right: 10px;">反向地理編碼</button>
					<span id="addressDetail">地址詳細信息：</span>
					</div>
			    </div>  
			    <div title="實例4:用戶數據">   
			        <table id="userdg"></table>  
			    </div> 
			</div>  
	    </div>   
	    <div data-options="region:'center'" id="map" >
	    </div>   
	</div>  
</body>
<script type="text/javascript">
var map = null;
var lat = 22.28218;  
var lng = 114.1577;  
$(function(){
	$.messager.progress({
		title:"地圖加載中..."
	});
	var mapProp = {
	  	center:new google.maps.LatLng(lat, lng),
	  	zoom:18,
	  	mapTypeId:google.maps.MapTypeId.ROADMAP,
	  	disableDefaultUI: true
	};
	map=new google.maps.Map(document.getElementById("map"),mapProp);
	genareadata();//从数据库中获取选区信息
  	for(var i in areaData){
		var nodeList = genareaNodeByAreaId(areaData[i].id);//根据选区信息获取选区所包含的结点集合
		var tempTriangle = new google.maps.Polygon({
		    paths: nodeList,
		    strokeColor: areaData[i].fillColor,
		    strokeOpacity: 0.8,
		    strokeWeight: 3,
		    fillColor: areaData[i].fillColor,
		    fillOpacity: 0.35,
		    areaname:areaData[i].areaName
		});
		tempTriangle.setMap(map);
		tempTriangle.addListener('click', showArrays);
	}
	  infoWindow = new google.maps.InfoWindow;
	  initAutocomplete();
	
    genuserdata();
	$('#userdg').datagrid({    
	    data:userData,    
	    fit:true,
	    rownumbers:true,
	    border:false,
	    columns:[[    
	        {field:'name',title:'姓名',width:60},    
	        {field:'area',title:'選區',width:40},    
	        {field:'lat',title:'維度',width:70}, 
	        {field:'lng',title:'經度',width:70}, 
	        {field:'xianshi',title:'顯示',width:20,formatter:function(value,row,index){
	        	return '<input type="checkbox" onchange="showOrHidemarker(this,'+index+')">' ;
	        }}, 
	    ]]    
	});
	
	genareadata();
	$('#areadg').edatagrid({    
	    data:areaData,    
	    fit:true,
	    border:false,
	    rownumbers:true,
	    columns:[[    
			{field:'areaYear',title:'年份',width:80, editor:'text'},        
	        {field:'areaName',title:'選區',width:80, editor:'text'},    
	        {field:'fillColor',title:'顏色',width:80, editor:'text'},
	        {field:'xianshi',title:'是否顯示',width:40,formatter:function(value,row,index){
	        	return '<input type="checkbox" id="xianshi'+index+'" onchange="showOrHidepolygon(this,'+index+')">' ;
	        }}, 
	        {field:'huaquyu',title:'是否畫區域',width:60,formatter:function(value,row,index){
	        	return '<input type="checkbox" targetxianshiId="xianshi'+index+'" name="huaquyu" onchange="showOrHidedrawingManager(this,'+index+')">' ;
	        }}, 
	    ]]    
	});
	$.messager.progress("close");
	$("#submitId").click(function(){
		var address = $("#address").val();
		$.ajax({
			   type: "POST",
			   url: "<%=ctxPathName %>/view/zmap.do?method=ajaxLatLngByAddress" ,
			   data:{"address":address},
			   success: function(data){
				   $("#lngDetail").html(data.message);
			   }
			});
		
	})
	$("#submitId1").click(function(){
		var lng = $("#lng").val();
		var lat = $("#lat").val();
		$.ajax({
			   type: "POST",
			   url: "<%=ctxPathName %>/view/zmap.do?method=ajaxAddressByLatLng" ,
			   data:{"lng":lng,"lat":lat},
			   success: function(data){
				   $("#addressDetail").html(data.message);
			   }
			});
	})
	
});



var userData = [];
function genuserdata() {
	var firstname=["李","西门","沈","张","上官","司徒","欧阳","轩辕","林","劉"];
    var nameq=["彪","巨昆","锐","翠花","小小","撒撒","熊大","宝强","春花","秋月","夏日","冬天"];
	for(var i = 0;i<1000;i++) {
		var xingxing = firstname[Math.floor(Math.random() * (firstname.length))];
        var mingming = nameq[Math.floor(Math.random() * (nameq.length))];
        var xingming = xingxing+mingming;
        var data = {
    			name:xingming,
    			code:"會員"+(i+1),
    			lng:lng + (0.01 * (Math.random()*2 -1)),
    			lat:lat + (0.01 * (Math.random()*2 -1))
    		};
		var marker = new google.maps.Marker({
		    position: {lat: data.lat, lng:  data.lng},
		    title: data.name,
		    icon:'<%=ctxPathName %>/js/jquery-easyui/themes/icons/man.png',
		    map:map
		  });
		data.marker=marker;
		userData.push(data);
	}
}

/*
 * 选区信息，需要改成读数据库，
 */
var areaData = [];
function genareadata() {
	var url = "<%=ctxPathName %>/view/electoralArea.do?method=getElectoralAreaListByYear";
	var result = requestAjaxData(url, false, true);
	areaData = result.rows;
}

function genareaNodeByAreaId(areaId) {
	var url = "<%=ctxPathName %>/view/electoralArea.do?method=getNodeByAreaId&areaId="+areaId;
	var result = requestAjaxData(url, false, true);
	console.log("genareaNodeByAreaId------"+result.rows);
	return result.rows;
}

//显示或者隐藏选民标记
function showOrHidemarker(thisObj,index){
	if(thisObj.checked) {
		userData[index].marker.setMap(map);
	}else {
		userData[index].marker.setMap(null);
	}
}
//显示或者隐藏选区
function showOrHidepolygon(thisObj,index){
	if(areaData[index].polygon) {
		$.each(areaData[index].polygon,function(){
			if(thisObj.checked) {
				this.setMap(map);
			}else {
				this.setMap(null);
			}
		})
	}
}

//划区域
function showOrHidedrawingManager(thisObj,index){
	if(!areaData[index].drawingManager) {
		var drawingManager = new google.maps.drawing.DrawingManager({
		    drawingMode: google.maps.drawing.OverlayType.POLYGON,
		    drawingControl: false,
		    editable : true,
		    drawingControlOptions: {
		      	position: google.maps.ControlPosition.TOP_CENTER,
		      	drawingModes: [
		        	google.maps.drawing.OverlayType.POLYGON,
		      	]
		    },
		    polygonOptions: {
		      fillColor: areaData[index].fillColor,
		      fillOpacity: 0.5,//填充透明度
		      strokeColor:areaData[index].strokeColor,
		      strokeWeight: 2,
		      editable: true,
		      zIndex: 1
		   }
		  });
		  drawingManager.setMap(map);
		  areaData[index].drawingManager = drawingManager;
		  google.maps.event.addListener(drawingManager, 'polygoncomplete', function(polygon) {
			  alert("需要保存结点信息");
			  if(!areaData[index].polygon) {
				  areaData[index].polygon = [];
			  }
			  //环球大厦地理坐标
			  var coordinate = new google.maps.LatLng(22.28255,114.1577184);  
		      
			  areaData[index].polygon.push(polygon);
			  polygon.addListener('click', showpolygoninfo);
			  polygon.areaname = areaData[index].areaName
			  polygon.areaid = (new Date()).getTime();
			  var isWithinPolygon = google.maps.geometry.poly.containsLocation(coordinate, polygon)
		      alert("判断环球大厦是否在多边形中LatLng(22.28255,114.1577184)---环球大厦-------------"+isWithinPolygon);
			  
			  
			  var vertices = polygon.getPath();//Retrieves the first path.
			  //polygon.getPaths();Retrieves the paths for this polygon.  这里很奇怪，getPaths结果只有一个值。
			  var nodeArr = [];	
			  for (var i =0; i < vertices.getLength(); i++) {
				    var xy = vertices.getAt(i);
				    var areaNode = {};
				    areaNode.lng = xy.lng();
				    areaNode.lat = xy.lat();
				    areaNode.zoneId = areaData[index].id;
				    areaNode.nodeOrder = i;
				    nodeArr.push(areaNode);
			  }
			  
			  //将nodeArr传到后台提交
			  var url = "<%=ctxPathName %>/view/areaNode.do?method=saveAreaNodes&jsonListStr="+JSON.stringify(nodeArr);
			  requestAjaxData(url, false, true);
			  
			  // Replace the info window's content and position.
			  //infoWindow.setContent(contentString);
			  //infoWindow.setPosition(polygon.latLng);

			 // infoWindow.open(map);
		      
			});
		  /*
			google.maps.event.addListener(drawingManager, 'polygoncomplete', showArrays);
		  */
	}
	if(thisObj.checked) {
		areaData[index].drawingManager.setMap(map);
		$("#"+$(thisObj).attr("targetxianshiId"))[0].checked = true;
	}else {
		areaData[index].drawingManager.setMap(null);
	}
}

function showpolygoninfo(event,areaname) {
	infoWindow = null;
	if(this.infoWindow) {
		infoWindow = this.infoWindow;
	}else {
		infoWindow = new google.maps.InfoWindow;
		this.infoWindow = infoWindow;
	}
	
	var vertices = this.getPath();
	var thisobj = this;
	  var contentString = '<b>所屬選區：'+this.areaname+'</b><a href="javascript:void(0)" onclick="deleteArea(\''+this.areaid +'\')">刪除</a><br>' +
	      '<b>擁有會員： </b> <br>';
	  
	  // Iterate over the vertices.
	 	$.each(userData,function(){
	 		if(google.maps.geometry.poly.containsLocation(this.marker.position, thisobj)) {
	 			contentString += "&nbsp;&nbsp;" + this.name
			}
	 	})
	  infoWindow.setContent(contentString);
	  infoWindow.setPosition(event.latLng);
	  infoWindow.open(map);
}

function deleteArea(_id){
	$.each(areaData,function(){
		var thisobj = this;
		var polygons = this.polygon;
 		if(polygons) {
 			$.each(polygons,function(i){
 				if(this.areaid == _id) {
 					Array.remove(polygons,i);
 					this.setMap(null);
 					if(this.infoWindow) {
 						this.infoWindow.setMap(null);
 					}
 					return false;
 				}
 			}) 
 		}
 	})
}

Array.remove = function(array, from, to) {  
	  var rest = array.slice((to || from) + 1 || array.length);  
	  array.length = from < 0 ? array.length + from : from;  
	  return array.push.apply(array, rest);  
	};  

var placeSearch, autocomplete;

function geolocate() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
      var geolocation = {
        lat: position.coords.latitude,
        lng: position.coords.longitude
      };
      var circle = new google.maps.Circle({
        center: geolocation,
        radius: position.coords.accuracy
      });
      autocomplete.setBounds(circle.getBounds());
    });
  }
}

function initAutocomplete() {
  // Create the search box and link it to the UI element.
  var input = document.getElementById('autocomplete');
  var searchBox = new google.maps.places.SearchBox(input);
  //map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

  // Bias the SearchBox results towards current map's viewport.
  map.addListener('bounds_changed', function() {
    searchBox.setBounds(map.getBounds());
  });

  var markers = [];
  // Listen for the event fired when the user selects a prediction and retrieve
  // more details for that place.
  searchBox.addListener('places_changed', function() {
    var places = searchBox.getPlaces();

    if (places.length == 0) {
      return;
    }

    // Clear out the old markers.
    markers.forEach(function(marker) {
      marker.setMap(null);
    });
    markers = [];

    // For each place, get the icon, name and location.
    var bounds = new google.maps.LatLngBounds();
    places.forEach(function(place) {
      var icon = {
        url: "<%=ctxPathName %>/js/jquery-easyui/themes/icons/maps.png",
        size: new google.maps.Size(100, 100),
        origin: new google.maps.Point(0, 0),
        anchor: new google.maps.Point(17, 34),
        scaledSize: new google.maps.Size(50, 50)
      };

      // Create a marker for each place.
      markers.push(new google.maps.Marker({
        map: map,
        icon: icon,
        title: place.name,
        position: place.geometry.location
      }));

      if (place.geometry.viewport) {
        // Only geocodes have viewport.
        bounds.union(place.geometry.viewport);
      } else {
        bounds.extend(place.geometry.location);
      }
      //alert(place.formatted_address+"地图对应的idplace_id-----"+place.place_id);
      //alert(place.formatted_address+"的纬度---"+place.geometry.location.lat()+"经度-----"+place.geometry.location.lng());
      $("#latAutoFill").val(place.geometry.location.lat());
      $("#lngAutoFill").val(place.geometry.location.lng());
    });
    map.fitBounds(bounds);
    map.setZoom(18);
  });
}

//展示多边行的所有节点
function showArrays(event) {
	  // Since this polygon has only one path, we can call getPath() to return the
	  // MVCArray of LatLngs.
	  var vertices = this.getPath();
	  var contentString = '<b>多边形节点信息</b><br>' +
	      '当前点击位置信息: <br>' + event.latLng.lat() + ',' + event.latLng.lng() +
	      '<br>';
	  // Iterate over the vertices.
	  for (var i =0; i < vertices.getLength(); i++) {
	    var xy = vertices.getAt(i);
	    contentString += '<br>' + '多边形状各节点信息 ' + i + ':<br>' + xy.lat() + ',' +
	        xy.lng();
	  }
	  // Replace the info window's content and position.
	  infoWindow.setContent(contentString);
	  infoWindow.setPosition(event.latLng);
	  infoWindow.open(map);
}	

</script>
</html>