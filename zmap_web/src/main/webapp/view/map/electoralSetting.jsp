<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@page import="java.net.URLDecoder" %>
<%
    String ctxPathName = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- <script src="http://ditu.google.cn/maps?file=api&v=3&key=AIzaSyBrRBLVg_bH4N0eADAxzPOKCpxi28C_0VQ&sensor=false" type="text/javascript"></script>
 -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>选区设置</title>
<link rel="stylesheet" href="<%=ctxPathName %>/js/jquery-easyui/themes/bootstrap/easyui.css">
<script src="http://maps.google.cn/maps/api/js?language=zh-TW&v=3.exp&key=AIzaSyCoy86qvSNN4A1nEA5Kg-jVdNNjjyVbyFI&libraries=places,drawing,geometry" type="text/javascript"></script>
<script src="<%=ctxPathName %>/js/jquery-2.1.3.min.js" type="text/javascript"></script>
<script src="<%=ctxPathName %>/js/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="<%=ctxPathName %>/js/jquery-easyui/extension/edatagrid/jquery.edatagrid.js" type="text/javascript"></script>
</head>
<body>
	<div  class="easyui-layout" data-options="fit:true">   
	    <div data-options="region:'west',split:true,minWidth:180" style="width:400px;">
	    	<div  class="easyui-accordion" data-options="fit:true,border:false" >   
			    
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
	
	// 初始化定义两个多边形点集合，用于默认展示。
	  var triangleCoords = [
	      {lat: 22.28158, lng: 114.1559},
	      {lat: 22.28232, lng: 114.1554},
	      {lat: 22.28248, lng: 114.1547}
	  ];
	  var triangleCoords1 = [
  	      {lat: 22.28128, lng: 114.1567},
  	      {lat: 22.28158, lng: 114.1567},
  	      {lat: 22.28218, lng: 114.1577},
  	      {lat: 22.28228, lng: 114.1587}
  	  ];


	  // 构建两个多边形
	  var bermudaTriangle = new google.maps.Polygon({
	    paths: triangleCoords,
	    strokeColor: '#0088cc',
	    strokeOpacity: 0.8,
	    strokeWeight: 3,
	    fillColor: '#0088cc',
	    fillOpacity: 0.35,
	    areaname:'区域C',
	    areaid:'123'
	  });
	  var bermudaTriangle1 = new google.maps.Polygon({
		    paths: triangleCoords1,
		    strokeColor: '#ff6161',
		    strokeOpacity: 0.8,
		    strokeWeight: 3,
		    fillColor: '#ff6161',
		    fillOpacity: 0.35,
		    areaname:'区域B',
			areaid:'456'
		  });
	  //默认在地图上展示
	  bermudaTriangle.setMap(map);
	  bermudaTriangle1.setMap(map);
	  // Add a listener for the click event.点击之后显示多边形点信息
	  bermudaTriangle.addListener('click', showArrays);
	  bermudaTriangle1.addListener('click', showArrays);
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
	        {field:'area',title:'選區',width:80, editor:'text'},    
	        {field:'color',title:'顏色',width:80, editor:'text'},
	        {field:'xianshi',title:'顯示',width:40,formatter:function(value,row,index){
	        	return '<input type="checkbox" id="xianshi'+index+'" onchange="showOrHidepolygon(this,'+index+')">' ;
	        }}, 
	        {field:'huaquyu',title:'畫區域',width:60,formatter:function(value,row,index){
	        	return '<input type="checkbox" targetxianshiId="xianshi'+index+'" name="huaquyu" onchange="showOrHidedrawingManager(this,'+index+')">' ;
	        }}, 
	    ]]    
	});
	$.messager.progress("close");
	$("#submitId").click(function(){
		var address = $("#address").val();
		$.ajax({
			   type: "POST",
			   url: "${cxt!}/m/hello/map/ajaxLatLngByAddress" ,
			   data:{"address":address},
			   success: function(data){
				   $("#lngDetail").html(data.data);
			   }
			});
		
	})
	$("#submitId1").click(function(){
		var lng = $("#lng").val();
		var lat = $("#lat").val();
		$.ajax({
			   type: "POST",
			   url: "${cxt!}/m/hello/map/ajaxAddressByLatLng" ,
			   data:{"lng":lng,"lat":lat},
			   success: function(data){
				   $("#addressDetail").html(data.data);
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
		    icon:'${cxt!}/ui/easyui/themes/icons/man.png',
		    map:map
		  });
		data.marker=marker;
		userData.push(data);
	}
}

var areaData = [];
function genareadata() {
	areaData.push({
		area:"選區A",
		color:"#70c24a"
	});
	areaData.push({
		area:"選區B",
		color:"#ff6161"
	});
	areaData.push({
		area:"選區C",
		color:"#0088cc"
	});
}


function showOrHidemarker(thisObj,index){
	if(thisObj.checked) {
		userData[index].marker.setMap(map);
	}else {
		userData[index].marker.setMap(null);
	}
}

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
		      fillColor: areaData[index].color,
		      fillOpacity: 0.5,//填充透明度
		      strokeColor:areaData[index].color,
		      strokeWeight: 2,
		      editable: true,
		      zIndex: 1
		    }
		  });
		  drawingManager.setMap(map);
		  areaData[index].drawingManager = drawingManager;
		  google.maps.event.addListener(drawingManager, 'polygoncomplete', function(polygon) {
			  
			  
			  if(!areaData[index].polygon) {
				  areaData[index].polygon = [];
			  }
			  //环球大厦地理坐标
			  var coordinate = new google.maps.LatLng(22.28255,114.1577184);  
		      
			  areaData[index].polygon.push(polygon);
			  polygon.addListener('click', showpolygoninfo);
			  polygon.areaname = areaData[index].area
			  polygon.areaid = (new Date()).getTime();
			  var isWithinPolygon = google.maps.geometry.poly.containsLocation(coordinate, polygon)
		      alert("判断环球大厦是否在多边形中LatLng(22.28255,114.1577184)---环球大厦-------------"+isWithinPolygon);
			  
			  
			  var vertices = polygon.getPath();//Retrieves the first path.
			  polygon.getPaths();//Retrieves the paths for this polygon.  这里很奇怪，getPaths结果只有一个值。
				
			  for (var i =0; i < vertices.getLength(); i++) {
			    var xy = vertices.getAt(i);
			    //alert("循环多边形的每个节点---经度--"+xy.lng()+"纬度----"+xy.lat());
			  }
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
	
	console.log(this);	
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

	  // Replace the info window's content and position.
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
/*
var componentForm = {
  street_number: 'short_name',
  route: 'long_name',
  locality: 'long_name',
  administrative_area_level_1: 'short_name',
  country: 'long_name',
  postal_code: 'short_name',
  room: 'room',
  floor: 'floor'	
};
function initAutocomplete() {
  // Create the autocomplete object, restricting the search to geographical
  // location types.
  autocomplete = new google.maps.places.Autocomplete(
      (document.getElementById('autocomplete')),
      {types: ['geocode']});

  // When the user selects an address from the dropdown, populate the address
  // fields in the form.
  autocomplete.addListener('place_changed', fillInAddress);
  
}
// [START region_fillform]
function fillInAddress() {
  // Get the place details from the autocomplete object.
  var place = autocomplete.getPlace();

  for (var component in componentForm) {
    document.getElementById(component).value = '';
    document.getElementById(component).disabled = false;
  }

  // Get each component of the address from the place details
  // and fill the corresponding field on the form.
  for (var i = 0; i < place.address_components.length; i++) {
    var addressType = place.address_components[i].types[0];
    if (componentForm[addressType]) {
      var val = place.address_components[i][componentForm[addressType]];
      document.getElementById(addressType).value = val;
    }
  }
}
*/
// [END region_fillform]

// [START region_geolocation]
// Bias the autocomplete object to the user's geographical location,
// as supplied by the browser's 'navigator.geolocation' object.
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
// [END region_geolocation]


// This example adds a search box to a map, using the Google Place Autocomplete
// feature. People can enter geographical searches. The search box will return a
// pick list containing a mix of places and predicted search terms.

// This example adds a search box to a map, using the Google Place Autocomplete
// feature. People can enter geographical searches. The search box will return a
// pick list containing a mix of places and predicted search terms.

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
        url: '${cxt!}/ui/easyui/themes/icons/maps.png',
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

	  var contentString = '<b>Bermuda Triangle polygon</b><br>' +
	      'Clicked location: <br>' + event.latLng.lat() + ',' + event.latLng.lng() +
	      '<br>';

	  // Iterate over the vertices.
	  for (var i =0; i < vertices.getLength(); i++) {
	    var xy = vertices.getAt(i);
	    contentString += '<br>' + 'Coordinate ' + i + ':<br>' + xy.lat() + ',' +
	        xy.lng();
	  }

	  // Replace the info window's content and position.
	  infoWindow.setContent(contentString);
	  infoWindow.setPosition(event.latLng);

	  infoWindow.open(map);
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