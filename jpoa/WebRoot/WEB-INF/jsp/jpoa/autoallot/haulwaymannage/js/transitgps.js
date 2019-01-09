var map = new BMap.Map("container");

//创建地图动态(上海)坐标中心
var lng = $("#lng").val();
var lat = $("#lat").val();
var point = new BMap.Point(lng, lat);
		
// 初始化地图，设置中心点坐标和地图级别  
map.centerAndZoom(point,11);
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

var ref = "";

// 开启实时定位
function editInterval(param) {
	gpsinit(param);
	setInterval(gpsinit, 60000, param);
}

//关闭实时定位
function removeInterval() {
	clearInterval(ref);
}

function gpsinit (param) {

	// 加载之前先移除
	map.clearOverlays();  
	centerGps();
	transitGps();
	transitRegionGps();
	
}

//机场服务中心定位
function centerGps() {
	var srcprovid =$("#srcprov").val();
	var srccityid =$("#srccity").val();
	
	$.ajax({
		url : "./counterServiceCenter/findServiceCentergps",
		data : {
			srcprovid:srcprovid,
			srccityid:srccityid
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			
			var json = eval(data);
			$.each(json, function (i, item) {
				
				var dmanname = json[i].servicecentername;
				var dmanmobile = json[i].address;
				var dman = dmanname;
				
				var currentgps = eval('(' + json[i].gps + ')');
				
				//创建小狐狸
				var pt = new BMap.Point(currentgps.lng, currentgps.lat);
				var myIcon = new BMap.Icon("http://www.yantiansf.cn/mapImage/1.gif", new BMap.Size(25,50));//300,157
				var marker2 = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
			    
				var label = new BMap.Label(dman,{offset:new BMap.Size(20,-10)});
				marker2.setLabel(label);
				
				map.addOverlay(marker2); 
//				marker2.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
				
			});
		}
	});
}

//集散中心定位
function transitGps() {
	var srcprovid =$("#srcprov").val();
	var srccityid =$("#srccity").val();
	
	$.ajax({
		url : "./transitCenter/findTransitCentergps",
		data : {
			srcprovid:srcprovid,
			srccityid:srccityid
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			
			var json = eval(data);
			$.each(json, function (i, item) {
				
				var dmanname = json[i].name;
				var dmanmobile = json[i].address;				
				var dman = dmanname;
				
				var currentgps = eval('(' + json[i].gps + ')');
				
				//创建小狐狸
				var pt = new BMap.Point(currentgps.lng, currentgps.lat);
				var myIcon = new BMap.Icon("http://www.yantiansf.cn/mapImage/1.gif", new BMap.Size(25,50));
				var marker2 = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
				marker2.disableMassClear();
				var label = new BMap.Label(dman,{offset:new BMap.Size(20,-10)});
				marker2.setLabel(label);
				map.addOverlay(marker2); 
				//marker2.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
				
			});
		}
	});
}

//异步加载历史绘制区域信息
function transitRegionGps() {
	$.ajax({
		url : "./regionManage/loadTransitCenter",
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
				//增加多边形
				map.addOverlay(polygon);
				
				var point = new BMap.Point(cityGpsList[0].lng,cityGpsList[0].lat);
				var opts = {
				  position : point,    // 指定文本标注所在的地理位置
//				  offset   : new BMap.Size(-10, -5)    //设置文本偏移量
				}
				var label = new BMap.Label(regionname, opts);  // 创建文本标注对象
					label.setStyle({
						 color : "red",
						 fontSize : "12px",
						 height : "20px",
						 lineHeight : "20px",
						 fontFamily:"微软雅黑"
					 });
				map.addOverlay(label); 
            });
			
		}
	});
}


