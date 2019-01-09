// 初始化集散中心
function viewregiondmaninit() {
	var map = new BMap.Map("container");
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
		
	$.ajax({
		url : "./regionManage/loadRegiondman",
		data : {},
		dataType : "json",
		type : "POST",
		success : function(data) {
			var json = eval(data);
			$.each(json, function (i, item) {
				var deliveryManName = json[i].deliveryManName;
				var cityGpsList = eval(json[i].gps);
			    var remark = eval('(' + json[i].remark + ')');
				
				// 增加坐标点从数据库中
				var pointsArray = [];
				$.each(cityGpsList, function (j, item) {
					pointsArray[j] = new BMap.Point(cityGpsList[j].lng, cityGpsList[j].lat);
				});
				
				/* 显示多边形样式 */
				var polygonStyle = {
				        strokeColor : remark.regionColor,   //边线颜色
				        fillColor : remark.regionColor,     //填充颜色。当参数为空时，圆形将没有填充效果。   
				        strokeWeight: 3,     //边线的宽度，以像素为单位。
				        strokeOpacity: 0.5,  //边线透明度，取值范围0 - 1。  
				        fillOpacity: 0.2,    //填充的透明度，取值范围0 - 1
				        strokeStyle: 'solid' //边线的样式，solid或dashed。 
				};
				
				// 创建多边形:加载既定坐标，并默认绘制多边形
				var polygon = new BMap.Polygon(pointsArray, polygonStyle);
				// 添加事件,当鼠标移动到指定区域时，会显示信息框
				polygon.addEventListener("mouseover", function(){
					// 事件触发时，弹出信息框
				    var infoWindow = new BMap.InfoWindow();
				    infoWindow.setContent(deliveryManName);
				    map.openInfoWindow(infoWindow, polygon.getBounds().getCenter());
				});
				//增加多边形
				map.addOverlay(polygon);
            });
		}
	});

}
