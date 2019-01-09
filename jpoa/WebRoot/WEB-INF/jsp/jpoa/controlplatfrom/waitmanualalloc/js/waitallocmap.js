// ============================初始化地图============================================
var map = new BMap.Map("container");

var city_lng = $("#loginperson_city_lng").val();
var city_lat = $("#loginperson_city_lat").val();
//创建地图动态(上海)坐标中心
var point = new BMap.Point(city_lng, city_lat);
		
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
  $(div).html("<img title='刷新地图列表数据' src='" + IMG_URL_PATH.MAP_REFRESH_URL.value + "'/>");
  // 绑定事件,点击一次放大两级
  div.onclick = function(e){
	  // 刷新地图
	  gpsinit();
	  // 待人工分配列表刷新
	  refreshwaitallotlist();
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
// =================================初始化结束=============================================


// =================================地图数据填充===========================================

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
// 子页面索引
var waitallotlistiframeWin;

// 加载待人工分配订单列表
loadwaitmanualalloclist();
function loadwaitmanualalloclist() {
	layer.open({
		  type: 2,// iframe
		  title: ['待人工分配列表', 'font-size:18px;'],
		  offset: ['50px', '1700px'],
		  area: ['500px', '900px'],
		  closeBtn: 0,// 没有关闭按钮
		  shade: 0,// 不想显示遮罩
		 /* move : '.layui-layer-content',*/ // 整体移动
		  id : 'console_waitmanualallotlist',//不管是什么类型的层，都只允许同时弹出一个
		  isOutAnim: false,// 关闭层时会有一个过度动画,如果你不想开启
		  content: "./waitmanualalloc/findWaitmanualAllotlist",
		  success: function(layero, index){
			  waitallotlistiframeWin = window[layero.find('iframe')[0]['name']];//得到iframe页的窗口对象，执行iframe页的方法：
		  }
	});
}

//地图填充刷新一次 （1分钟）
editInterval();
function editInterval() {
	gpsinit();
	/*setInterval(gpsinit, TIMING_TIME.CONSOLE_WAIT_MANUAL_ALLOT_MAP.value);*/
}
function gpsinit () {
	map.clearOverlays();
	//加载取派员区域
	viewRegiondman(map);//  是否只需要取派员实时位置更新，其他的都不需要跟新
	// 填充节点信息
	fillrolegpsData(map,"");
	
	// 自动分配列表数量刷新
	countwaitmanualalloc();
	// 待人工分配列表数量刷新
	countautoalloc();
}

//刷新待人工分配列表
function refreshwaitallotlist() {
	waitallotlistiframeWin.fillwaitmanualallotlist();
}


//加载待人工分配订单编辑
function loadwaitmanualallotedit(orderid) {
	orderid = orderid.trim();
	waitmanualalloteditindex = layer.open({
										  type: 2,// iframe
										  title: ['待人工分配编辑', 'font-size:18px;'],
										  offset: ['10px', '500px'],
										  area: ['1000px', '1050px'],
										  closeBtn: 2,// 没有关闭按钮
										  shade: 0,// 不想显示遮罩
										 /* move : '.layui-layer-content',*/ // 整体移动
										  id : 'console_waitmanualallotedit',//不管是什么类型的层，都只允许同时弹出一个
										  isOutAnim: false,// 关闭层时会有一个过度动画,如果你不想开启
										  content: "./waitmanualalloc/findWaitmanualAllotedit?orderid=" + orderid,
										  success: function(layero, index){
											  waitalloteditiframeWin = window[layero.find('iframe')[0]['name']];//得到iframe页的窗口对象，执行iframe页的方法：
										  }
								});
}
// 关闭待人工分配编辑页面
function closewaitmanualallotedit() {
	layer.close(waitmanualalloteditindex);
}


// 刷新待分配订单数量
function countwaitmanualalloc() {
	parent.countwaitmanualalloc();
}
//刷新自动分配订单数量
function countautoalloc() {
	parent.countautoalloc();
}

var srcmarker,destmarker;

// 加载订单的出发地坐标、目的地坐标
function loadordersrcanddest(srcgps_lng, srcgps_lat, destgps_lng, destgps_lat) {
	
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

function loadorderdetails (orderid) {
	layer.open({
		  type: 2,
		  title: ['订单详情', 'font-size:18px;'],// 不需要标题
		  closeBtn: 1,// 没有关闭按钮
		  id : 'console_orderdetails',//不管是什么类型的层，都只允许同时弹出一个
		  area: ['100%', '100%'],
		  fixed: false, //不固定
		  content: "./orderinfo/listOrderDetail?id=" + orderid
		});
}