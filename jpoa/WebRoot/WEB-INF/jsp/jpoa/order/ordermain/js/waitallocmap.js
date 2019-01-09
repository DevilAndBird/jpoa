// ============================初始化地图============================================
var map = new BMap.Map("container");

var city_lng = $("#loginperson_city_lng").val();
var city_lat = $("#loginperson_city_lat").val();
//创建地图动态(上海)坐标中心
var point = new BMap.Point(city_lng, city_lat);
		
// 初始化地图，设置中心点坐标和地图级别  
map.centerAndZoom(point,12);
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
  $(div).html("<img title='点击刷新地图' src='" + IMG_URL_PATH.MAP_REFRESH_URL.value + "'/>");
  		     
  // 绑定事件,点击一次放大两级
  div.onclick = function(e){
	  // 刷新地图
	  gpsinit();
  };
  // 添加DOM元素到地图中
  map.getContainer().appendChild(div);
  // 将DOM元素返回
  return div;
};
// 创建控件
var myZoomCtrl = new ZoomControl();

//定义一个控件类,即function
function ZoomControlback(){
  // 默认停靠位置和偏移量
  this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
  this.defaultOffset = new BMap.Size(15, 250);
}
// 通过JavaScript的prototype属性继承于BMap.Control
ZoomControlback.prototype = new BMap.Control();
// 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
// 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
ZoomControlback.prototype.initialize = function(map){
  // 创建一个DOM元素
  var div = document.createElement("div");
  $(div).html("<img title='点击返回订单列表' src='" + IMG_URL_PATH.BACK_URL.value + "'/>");
  // 绑定事件,点击一次放大两级
  div.onclick = function(e){
	  // 刷新地图
	  backorderinfo_list();
  };
  // 添加DOM元素到地图中
  map.getContainer().appendChild(div);
  // 将DOM元素返回
  return div;
};
// 创建控件
var myZoomCtrl = new ZoomControl();
//创建控件
var myZoomCtrlback = new ZoomControlback();

// 添加到地图当中
map.addControl(myZoomCtrl);
map.addControl(myZoomCtrlback);
// =================================初始化结束=============================================


// =================================地图数据填充===========================================
// 返回订单列表
function backorderinfo_list() {
	location.href = './orderinfo/listOrderInfo';
}


var srcgps_lngtemp;
var srcgps_lattemp;
var destgps_lngtemp;
var destgps_lattemp;

//地图填充刷新一次 （1分钟）
editInterval();
function editInterval() {
	gpsinit();
	setInterval(gpsinit, TIMING_TIME.CONSOLE_WAIT_MANUAL_ALLOT_MAP.value);
}
function gpsinit () {
	map.clearOverlays();
	//加载取派员区域
	viewRegiondman(map);// TODO 是否只需要取派员实时位置更新，其他的都不需要跟新
	// 填充节点信息
	fillrolegpsData(map,"");
	
	loadordersrcanddest(srcgps_lngtemp, srcgps_lattemp, destgps_lngtemp, destgps_lattemp);
}

// 组装参数
function fillrolegpsData(map,dmanuserid) {
	$.ajax({
		url : "./consolecommon/fillRoleGpsData",// TODO 这个sql多查询了两个sql比较冗余
		data : {
			dmanuserid : dmanuserid
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			// 取派员gps
			dman(map,data["dman"]);
			
			// 任务订单数查询gps
			taskOrderNum(map,data["taskOrderNum"]);
		}
	});
}
//=================================地图数据填充结束===========================================


//=================================加载待人工分配订单加载窗口====================================
//layer 模块加载
var layer;
layui.use('layer', function(){
	  layer = layui.layer;
});


// 子页面窗口index;
var waitmanualalloteditindex;

//加载待人工分配订单编辑
loadwaitmanualallotedit();
function loadwaitmanualallotedit() {
	waitmanualalloteditindex = layer.open({
										  type: 2,// iframe
										  title: ['订单', 'font-size:18px;'],
										  offset: ['70px', '1300px'],
										  area: ['1000px', '1050px'],
										  closeBtn: false,
										  shade: 0,// 不想显示遮罩
										 /* move : '.layui-layer-content',*/ // 整体移动
										  id : 'console_waitmanualallotedit',//不管是什么类型的层，都只允许同时弹出一个
										  isOutAnim: false,// 关闭层时会有一个过度动画,如果你不想开启
										  content: "./orderinfo/findWaitmanualAllotedit?orderid=" + $('#orderid').val().trim(),
										  success: function(layero, index){
											  waitalloteditiframeWin = window[layero.find('iframe')[0]['name']];//得到iframe页的窗口对象，执行iframe页的方法：
										  }
								});
}
// 关闭待人工分配编辑页面
function closewaitmanualallotedit() {
	layer.close(waitmanualalloteditindex);
}

var srcmarker,destmarker;

// 加载订单的出发地坐标、目的地坐标
function loadordersrcanddest(srcgps_lng, srcgps_lat, destgps_lng, destgps_lat) {
	srcgps_lngtemp = srcgps_lng;
	srcgps_lattemp = srcgps_lat;
	destgps_lngtemp = destgps_lng;
	destgps_lattemp = destgps_lat;
	
	
	// 出发地
	var srcpoint = new BMap.Point(srcgps_lng, srcgps_lat);
	var srcmyIcon = new BMap.Icon(IMG_URL_PATH.FETCH_ACTIVE_URL.value, new BMap.Size(200,100), {
	    anchor: new BMap.Size(10, 20),
	});
	srcmarker = new BMap.Marker(srcpoint,{icon:srcmyIcon});  // 创建标注
	map.addOverlay(srcmarker);
	srcmarker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
	
	// 目的地
	var destpoint = new BMap.Point(destgps_lng, destgps_lat);
	var destmyIcon = new BMap.Icon(IMG_URL_PATH.DELIVERY_ACTIVE_URL.value, new BMap.Size(200,100), {
	    anchor: new BMap.Size(10, 20),
	});
	destmarker = new BMap.Marker(destpoint,{icon:destmyIcon});  // 创建标注
	map.addOverlay(destmarker);
	destmarker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
}

// 去除订单的出发地坐标、目的地坐标
function removeordersrcanddest() {
	map.removeOverlay(srcmarker);
	map.removeOverlay(destmarker);
}

//查看订单经过路径
var currentPath;
function lookpath(orderid) {
    orderid = orderid.trim();
	$.ajax({
		url : "./console_autoalloc/orderPathWay",
		data : {
			orderid : orderid,
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			var json = eval(data);
			
			var pois = [];
			$.each(json, function (i, item) {
				var destgps = eval('(' + json[i].destgps + ')');
				pois.push(new BMap.Point(destgps.lng, destgps.lat));
			});
			
			// 如果分配的动作小于1的话则不展示
			if(pois.length <= 1) {
				return;
			}
			
			currentPath = buildpath(pois);
		}
	});
}

function buildpath(pois) {
	var sy = new BMap.Symbol(BMap_Symbol_SHAPE_BACKWARD_OPEN_ARROW, {
	    scale: 0.6,//图标缩放大小
	    strokeColor:'#fff',//设置矢量图标的线填充颜色
	    strokeWeight: '2',//设置线宽
	});
	var icons = new BMap.IconSequence(sy, '10', '30');
	// 创建polyline对象
	
	 var pathtemp = new BMap.Polyline(pois, {
	    enableEditing: false,//是否启用线编辑，默认为false
	    enableClicking: true,//是否响应点击事件，默认为true
	    enableMassClear : true,
	    icons:[icons],
	    strokeWeight:'6',//折线的宽度，以像素为单位
	    strokeOpacity: 0.8,//折线的透明度，取值范围0 - 1
	    strokeColor:"#18a45b" //折线颜色
	 });
	map.addOverlay(pathtemp); 
	return pathtemp;
}