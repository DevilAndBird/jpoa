// 全局：加载百度地图容器
var map = new BMap.Map("container");
// 保存绘制的点集合
var overlays = []; 

// 初始化地区绘制信息
function addRegionDmanInit() {
	// 画区域颜色加载
	loadDrawRegionCityColor();
	/* 模态框是否可移动 */
	$("#myModal").draggable();
	//加载百度地图基础信息
	loadBaseBaiduMapInfo();
	// 异步加载历史绘制区域信息
	ajaxLoadHistoryInfo();
}

//加载绘制区域颜色
function loadDrawRegionCityColor() {
	$("#seleDrawColor").empty();
	for(i in DARWDMANCOLOR) {
		$("#seleDrawColor").append("<option value='"+DARWDMANCOLOR[i].value+"'>"+DARWDMANCOLOR[i].name+"</option>");
	}
	
	$("#seleDrawColor_modal").empty();
	for(i in DARWDMANCOLOR) {
		$("#seleDrawColor_modal").append("<option value='"+DARWDMANCOLOR[i].value+"'>"+DARWDMANCOLOR[i].name+"</option>");
	}
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
}

// 异步加载历史绘制区域信息
function ajaxLoadHistoryInfo() {
	$.ajax({
		url : "./regionManage/loadRegiondman",
		data : {},
		dataType : "json",
		type : "POST",
		success : function(data) {
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
//				polygon.addEventListener("mouseover", function(){
//					// 事件触发时，弹出信息框
//				    var infoWindow = new BMap.InfoWindow();
//				    infoWindow.setContent(regionname);
//				    map.openInfoWindow(infoWindow, polygon.getBounds().getCenter());
//				});
				//增加多边形
				map.addOverlay(polygon);
            });
			
			
		}
	});
}

// 绘制区域
function draw(){
	// 获取区域绘画颜色
	var selectDarwColor = $("#seleDrawColor").attr("selected",true).val();
	
	// 绘制区域
    var overlaycomplete = function(e){  
        overlays.push(e.overlay);   
    }; 
    
    // 颜色问题
    var styleOptions = {
    		strokeColor : selectDarwColor,   //边线颜色： "red","blue","black"
	        fillColor : selectDarwColor,     //填充颜色。当参数为空时，圆形将没有填充效果。   
	        strokeWeight: 3,     //边线的宽度，以像素为单位。
	        strokeOpacity: 0.5,  //边线透明度，取值范围0 - 1。  
	        fillOpacity: 0.2,    //填充的透明度，取值范围0 - 1
	        strokeStyle: 'solid' //边线的样式，solid或dashed。   
    };  
	      
    // 实例化鼠标绘制工具
    var drawingManager = new BMapLib.DrawingManager(map, {  
        isOpen: false, //是否开启绘制模式  
        //enableDrawingTool: true, //是否显示工具栏  
        drawingToolOptions: {  
            anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
            offset: new BMap.Size(5, 5), //偏离值  
        },
        polygonOptions: styleOptions //多边形的样式  
    });
	      
    //添加鼠标绘制工具监听事件，用于获取绘制结果  
    drawingManager.addEventListener('overlaycomplete', overlaycomplete); 
	
    drawingManager.open();   
    drawingManager.setDrawingMode(BMAP_DRAWING_POLYGON);  
}

// 保存绘制的地图
function saveRegionDmanPoint(){  
	var gpsRegion = "";
    for(var i = 0; i < overlays.length; i++){  
        var overlay=overlays[i].getPath();
        gpsRegion = "[";
        for(var j = 0; j < overlay.length; j++){  
            var grid =overlay[j];  
            gpsRegion = gpsRegion + "{'lng':'" + grid.lng+"','lat':'" + grid.lat + "'},";  
        }
        gpsRegion = gpsRegion.slice(0,gpsRegion.length-1);
        gpsRegion = gpsRegion + "]";
        
        
        $("#seleDrawColor_modal").find("option[value='" + $("#seleDrawColor").attr("selected",true).val() +"']").attr("selected",true);
        $("#gps_modal").val(gpsRegion);
    }  
} 

// 保存
$("#btnSaveRegionDmanGps").click(function() {
	var provid = $("#provid").attr("selected",true).val();
	var cityid = $("#cityid").attr("selected",true).val();
	var gps =  $("#gps_modal").val();
	var regionname =  $("#regionname").val();
	var remark = "{'regionColor':'" + $("#seleDrawColor_modal").attr("selected",true).val() + "'}";
	if(!provid || !cityid || !regionname || !gps || !remark ) {
		toastr.warning('请输入数据');
		return ;
	}
	$.ajax({
		url : "regionManage/saveRegionDman",
		data : {
			regionname : regionname,
			provid : provid,
			cityid : cityid,
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
						location.href = "./regionManage/addRegionDman.do";
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

// 清除绘制绘制的地图
function clearAll() {  
    for(var i = 0; i < overlays.length; i++){  
        map.removeOverlay(overlays[i]);  
    }  
    overlays.length = 0;
}

/* 编辑 */
function Editing(state){  
    for(var i = 0; i < overlays.length; i++){  
        state=='enable'?overlays[i].enableEditing():overlays[i].disableEditing();  
    }  
}


