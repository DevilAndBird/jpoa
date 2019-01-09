// 全局：加载百度地图容器
var map = new BMap.Map("container");
// 多边形覆盖物
var path; 

// 初始化地区绘制信息
function edittransitcenterInit() {
	//加载绘制区域颜色
	loadDrawRegionCityColor();
	/* 模态框是否可移动 */
	$("#myModal").draggable();
	//加载百度地图基础信息
	loadBaseBaiduMapInfo();
	// 异步加载历史绘制区域信息
	ajaxLoadHistoryInfo();
	/* 添加编辑 */
	Editing();
}

//加载绘制区域颜色
function loadDrawRegionCityColor() {
	$("#seleDrawColor_modal").empty();
	for(i in DARWTRANSITCOLOR) {
		$("#seleDrawColor_modal").append("<option value='"+DARWTRANSITCOLOR[i].value+"'>"+DARWTRANSITCOLOR[i].name+"</option>");
	}
}

/* 添加编辑 */
function Editing(){
	var id = $("#hidId").val();
	
	$.ajax({
		url : "./regionManage/findRegionTransitByid",
		data : {
			id : id
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			$("#hidProvid").val(data.provid);
			$("#hidCityid").val(data.cityid);
			$("#hidTransitid").val(data.transitid);
			var cityGpsList = eval(data.gps);
		    var remark = eval('(' + data.remark + ')');
			var color = remark.regionColor;
			$("#hidColorid").val(color);
			
			//显示多边形样式 
			var polygonStyle = {
			        strokeColor : color,   //边线颜色： "red","blue","green","black"
			        fillColor : color,     //填充颜色。当参数为空时，圆形将没有填充效果。   
			        strokeWeight: 3,     //边线的宽度，以像素为单位。
			        strokeOpacity: 0.5,  //边线透明度，取值范围0 - 1。  
			        fillOpacity: 0.2,    //填充的透明度，取值范围0 - 1
			        strokeStyle: 'solid' //边线的样式，solid或dashed。 
			};
			
			path = new BMap.Polygon([], polygonStyle); 
			path.setPath(cityGpsList); //添加若干坐标点
			map.addOverlay(path); 
			path.enableEditing();
		}
	});
}

//加载百度地图基础信息
function loadBaseBaiduMapInfo() {
	// 创建地图上海坐标中心
	var point = new BMap.Point(121.487, 31.249);
	// 初始化地图，设置中心点坐标和地图级别  
	map.centerAndZoom(point, 10);
	// 支持滚动缩放
	map.enableScrollWheelZoom(true);
	/* 控件 */
	map.addControl(new BMap.NavigationControl());
    map.addControl(new BMap.ScaleControl());
    map.addControl(new BMap.OverviewMapControl());    
    map.addControl(new BMap.MapTypeControl());
    map.setCurrentCity("上海"); // 仅当设置城市信息时，MapTypeControl的切换功能才能可用
    // 风格：默认地图样式(normal)、清新蓝风格(light)、高端灰风格(grayscale)、强边界风格(hardedge)
    var mapStyle={style : "normal"};  
	map.setMapStyle(mapStyle);
	
	//定义一个控件类,即function
	function ZoomControl(){
	  // 默认停靠位置和偏移量
	  this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
	  this.defaultOffset = new BMap.Size(15, 210);
	}
	// 通过JavaScript的prototype属性继承于BMap.Control
	ZoomControl.prototype = new BMap.Control();
	// 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
	// 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
	ZoomControl.prototype.initialize = function(map){
	  // 创建一个DOM元素
	  var div = document.createElement("div");
	  $(div).html("<img  alt='' src='" + IMG_URL_PATH.BACK_URL.value + "'/>");
	  // 绑定事件,点击一次放大两级
	  div.onclick = function(e){
		  location.href ="./regionManage/regionTransitCenter";
	  };
	  // 添加DOM元素到地图中
	  map.getContainer().appendChild(div);
	  // 将DOM元素返回
	  return div;
	};
	// 创建控件
	var myZoomCtrl = new ZoomControl();
	// 添加到地图当中
	map.addControl(myZoomCtrl);
}

// 异步加载历史绘制区域信息
function ajaxLoadHistoryInfo() {
	$.ajax({
		url : "./regionManage/loadTransitCenter",
		data : {},
		dataType : "json",
		type : "POST",
		success : function(data) {
			var id = $("#hidId").val();
			
			var json = eval(data);
			$.each(json, function (i, item) {
				var regionname = json[i].regionname;		
				var cityGpsList = eval(json[i].gps);
			    var remark = eval('(' + json[i].remark + ')');
				
				// 增加坐标点从数据库中
				var pointsArray = [];
				$.each(cityGpsList, function (j, item) {
					pointsArray[j] = new BMap.Point(cityGpsList[j].lng, cityGpsList[j].lat);
				});
				
				/* 显示多边形样式 */
				var polygonStyle = {
				        strokeColor : remark.regionColor,   //边线颜色： "red","blue","green","black"
				        fillColor : remark.regionColor,     //填充颜色。当参数为空时，圆形将没有填充效果。   
				        strokeWeight: 3,     //边线的宽度，以像素为单位。
				        strokeOpacity: 0.5,  //边线透明度，取值范围0 - 1。  
				        fillOpacity: 0.2,    //填充的透明度，取值范围0 - 1
				        strokeStyle: 'solid' //边线的样式，solid或dashed。 
				};
				
				// 创建多边形:加载既定坐标，并默认绘制多边形
				var polygon = new BMap.Polygon(pointsArray,polygonStyle);
				// 添加事件,当鼠标移动到指定区域时，会显示信息框
				polygon.addEventListener("mouseover", function(){
					// 事件触发时，弹出信息框
				    var infoWindow = new BMap.InfoWindow();
				    infoWindow.setContent(regionname);
				    map.openInfoWindow(infoWindow, polygon.getBounds().getCenter());
				});
				//增加多边形
				map.addOverlay(polygon);
            });
			
			
		}
	});
}

/* 模态框_省市联动 */
function provCityLinkage(param, selectedValue){
    $.ajax({
		url : "./city/findCityByProvid",
		data : {
			provid : param
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
				$("#seleCityid_modal").empty();
				$("#seleCityid_modal").append("<option value='-1' selected='selected'>--城市--</option>");
				var json = eval(data);
				$.each(json, function (i, item) {
					$("#seleCityid_modal").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	            });
				
				/* 增加按钮时效果*/ 
				if(selectedValue != null && selectedValue != '') {
					$("#seleCityid_modal").find("option[value='" + selectedValue +"']").attr("selected",true);
				}
		}
	});	 
}

// 保存绘制的地图
function editTransiCenterPoint(){
	$("#seleProvid_modal").find("option[value='" + $("#hidProvid").val() +"']").attr("selected",true).val();
	provCityLinkage($("#hidProvid").val(),$("#hidCityid").val());
	$("#seleTransitCenterid_modal").find("option[value='" + $("#hidTransitid").val() +"']").attr("selected",true);
	$("#seleDrawColor_modal").find("option[value='" + $("#hidColorid").val() +"']").attr("selected",true);
	
    var gpsRegion = "[";
    var a = path.getPath();
    for(var j = 0; j < a.length; j++){  
        var grid = a[j];
        gpsRegion = gpsRegion + "{'lng':'" + grid.lng+"','lat':'" + grid.lat + "'},";  
    }
    gpsRegion = gpsRegion.slice(0,gpsRegion.length-1);
    gpsRegion = gpsRegion + "]";
    $("#gps_modal").val(gpsRegion);
} 

// 保存
$("#btnEditTransitCenter").click(function() {
	var id = $("#hidId").val();
	var provid = $("#seleProvid_modal").attr("selected",true).val();
	var cityid = $("#seleCityid_modal").attr("selected",true).val();
	var transitid = $("#seleTransitCenterid_modal").attr("selected",true).val();
	var gps =  $("#gps_modal").val();
	var timespan =  $("#timespan").val();
	var remark = "{'regionColor':'" + $("#seleDrawColor_modal").attr("selected",true).val() + "'}";
	
	if(provid == -1 || cityid == -1 || !provid || !cityid || !gps || !timespan) {
		toastr.warning('请输入数据');
		return ;
	}
	
	$.ajax({
		url : "regionManage/updateRegionTransit",
		data : {
			id : id,
			provid : provid,
			cityid : cityid,
			transitid : transitid,
			timespan : timespan,
			gps : gps,
			remark : remark
		},
		dataType : "text",
		type : "POST",
		success : function(data) {
			layer.alert(data, {
				skin : 'layui-layer-molv' // 样式类名 自定义样式
				,
				closeBtn : 1 // 是否显示关闭按钮
				,
				anim : 1 // 动画类型
				,
				btn : [ '确认' ] // 按钮
				,
				icon : 6 // icon
				,
				yes : function() {
					var descs = data.split(":");
					if(descs[0] == "SUCCESS") {
						location.href = "./regionManage/regionTransitCenter.do";
					}
				}
			});
		},
		error : function(data) {
			layer.msg(data, {
				icon : 5
			});
		}
	});
});


