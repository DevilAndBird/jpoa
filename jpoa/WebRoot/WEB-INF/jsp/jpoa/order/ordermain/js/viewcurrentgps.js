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
	
	dmancurrentGps();
	centerGps();
	transitGps();
	srcaddessGps(param);
	destaddessGps(param);
}

//寄件地址定位
function srcaddessGps(param) {
	var dmanid =$(param).val();
	var orderid = $("#order" + dmanid).val();	
	$.ajax({
		url : "./userDeliveryMan/findsrcaddessGps",
		data : {
			orderid:orderid
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			var json = eval(data);		
						
			var dmanname = "寄件地址";

			var dmanmobile = json.srcaddressname;
			
			var dman = "" + dmanname + ":" + dmanmobile;
			var currentgps = eval('(' + json.srcaddress + ')');						
			
			//创建小狐狸
			var pt = new BMap.Point(currentgps.lng, currentgps.lat);
			
			//var myIcon = new BMap.Icon("../img/fox.gif", new BMap.Size(100,50)); 
			var myIcon = new BMap.Icon("http://www.yantiansf.cn/mapImage/1.gif", new BMap.Size(100,50)); 	
			
			var marker2 = new BMap.Marker(pt,{icon:myIcon});  // 创建标注		
			
			var label = new BMap.Label(dman,{offset:new BMap.Size(20,-10)});
			marker2.setLabel(label);
						
			map.addOverlay(marker2); 			
			
		}
	});
}

//收件地址定位
function destaddessGps(param) {
	var dmanid =$(param).val();
	var orderid = $("#order" + dmanid).val();	
	
	$.ajax({
		url : "./userDeliveryMan/findsrcaddessGps",
		data : {
			orderid:orderid
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			var json = eval(data);		
							
			var dmanname = "收件地址";
			
			var dmanmobile = json.destaddressname;
			
			var dman = "" + dmanname + ":" + dmanmobile;
			var currentgps = eval('(' + json.destaddress + ')');
			//创建小狐狸
			var pt = new BMap.Point(currentgps.lng, currentgps.lat);
			var myIcon = new BMap.Icon("http://www.yantiansf.cn/mapImage/1.gif", new BMap.Size(100,50));
			var marker2 = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
			
			var label = new BMap.Label(dman,{offset:new BMap.Size(20,-10)});
			marker2.setLabel(label);
			
			map.addOverlay(marker2); 			
			
		}
	});
}

//取派员位置定位
function dmancurrentGps() {
	var srccityid =$("#srccity").val();
	
	$.ajax({
		url : "./userDeliveryMan/findDmanCuttentgps",
		data : {
			srccityid:srccityid
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			
			var json = eval(data);
			$.each(json, function (i, item) {
				
				var dmanname = json[i].name;
				var dmanmobile = json[i].mobile;
				var dman = "" + dmanname + "/" + dmanmobile;

				var currentgps = eval('(' + json[i].currentgps + ')');

				//创建小狐狸
				var pt = new BMap.Point(currentgps.lng, currentgps.lat);
				var myIcon = new BMap.Icon("http://lbsyun.baidu.com/jsdemo/img/fox.gif", new BMap.Size(200,100));
				var marker2 = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
			    
				var label = new BMap.Label(dman,{offset:new BMap.Size(20,-10)});
				marker2.setLabel(label);
				
				map.addOverlay(marker2); 
//				marker2.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
				
			});
		}
	});
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
				
				var label = new BMap.Label(dman,{offset:new BMap.Size(20,-10)});
				marker2.setLabel(label);
				
				map.addOverlay(marker2); 
				//marker2.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
				
			});
		}
	});
}




