var autoallotlistiframeWin,autoalloteditiframeWin,autoallotdetailiframeWin,allotdmaniframeWin;
var autoallotlistindex, autoallotdetailindex, autoallotdetaileditindex,allotdmanindex; 

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
  /*$(div).css({  
	    width:"150px",  
	    overflow:"auto",  
	    border:"1px solid #aaa",  
	    backgroundColor:"#fff"  
	}); */ 
  $(div).html("<img title='刷新地图列表数据' src='" + IMG_URL_PATH.MAP_REFRESH_URL.value + "'/>");
  // 绑定事件,点击一次放大两级
  div.onclick = function(e){
	  // 刷新地图
	  gpsinit();
	  
	  if(autoallotlistindex != null &&  manageautoallotlist() != '') {
		  manageautoallotlist();
	  }
	 
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
		url : "./consolecommon/fillRoleGpsData",
		data : {
			dmanuserid : dmanuserid
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			// 取派员gps
			dman(map,data["dman"]);
			
			// 取件地址_酒店or住宅gps
			hoteltaskaddress(map,data["taskaddressgps_hotel"]);
			
			// 送件地址_酒店or住宅gps
			hotelsendaaddress(map,data["sendaddressgps_hotel"]);
			
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

// 加载自动分配订单列表
loadautoalloclist();

function loadautoalloclist() {
	 autoallotlistindex = layer.open({
									  type: 2,// iframe
									  title: ['自动分配列表', 'font-size:18px;'],
									  offset: ['50px', '1700px'],
									  area: ['500px', '900px'],
									  closeBtn: 0,// 没有关闭按钮
									  shade: 0,// 不想显示遮罩
									 /* move : '.layui-layer-content',*/ // 整体移动
									  id : 'console_autoallotlist',//不管是什么类型的层，都只允许同时弹出一个
									  isOutAnim: false,// 关闭层时会有一个过度动画,如果你不想开启
									  content: "./console_autoalloc/findAutoAllotlist",
									  success: function(layero, index){
										  autoallotlistiframeWin = window[layero.find('iframe')[0]['name']];//得到iframe页的窗口对象，执行iframe页的方法：
									  }
								});								
}

editInterval();
//地图填充刷新一次 （1分钟）
function editInterval() {
	gpsinit();
	/*setInterval(gpsinit, TIMING_TIME.CONSOLE_WAIT_MANUAL_ALLOT_MAP.value);*/
}
function gpsinit () {
	map.clearOverlays();
	//加载取派员区域
	viewRegiondman(map);
	// 填充节点信息
	fillrolegpsData(map,"");
	
	// 自动分配列表数量刷新
	countwaitmanualalloc();
	// 待人工分配列表数量刷新
	countautoalloc();
}

// 操纵autoallotlist
function manageautoallotlist() {
	autoallotlistiframeWin.fillAutoallotlist();
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

// 添加局部路径
function orderpartdetailpath(taskgps_lng, taskgps_lat, sendgps_lng, sendgps_lat) {
	var pois = [];
	pois.push(new BMap.Point(taskgps_lng, taskgps_lat));
	pois.push(new BMap.Point(sendgps_lng, sendgps_lat));
    return buildpath(pois);
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

//移除覆盖物
function moveorderpath() {
    // 移除polyline
    map.removeOverlay(currentPath);
}

function removepartdetailpath(index) {
	if(index == null || index == '') {
		return;
	}
	// 移除polyline
    map.removeOverlay(index);
}

// 填充订单路线详情
function fillautoallotdetail(param) {
	layer.close(autoallotlistindex);
	autoallotlistindex = null;
	
	param = param.trim();
	autoallotdetailindex = layer.open({
								  type: 2,// iframe
								  title: ['订单分派详情', 'font-size:18px;'],
								  offset: ['50px', '1700px'],
								  area: ['500px', '900px'],
								  closeBtn: 2,// 没有关闭按钮
								  shade: 0,// 不想显示遮罩
								 /* move : '.layui-layer-content',*/ // 整体移动
								  id : 'console_autoallotdetail',//不管是什么类型的层，都只允许同时弹出一个
								  isOutAnim: false,// 关闭层时会有一个过度动画,如果你不想开启
								  content: "./console_autoalloc/findAutoAllotdetail?orderid=" + param,
								  success: function(layero, index){
									  autoallotdetailiframeWin = window[layero.find('iframe')[0]['name']];//得到iframe页的窗口对象，执行iframe页的方法：
								  },
								  cancel: function(index1, layero){ 
									  	// 刷新地图
										gpsinit();
									    layer.close(index1);
									    layer.close(allotdmanindex);
									    loadautoalloclist();
									    closewaitmanualalloteditindex();
								      return false; 
								  }    
								            
							});
	// 查询地图
	gpsinit();
}

// 操纵autoallotdetail
function manageautoallotdetailiframeWin() {
	autoallotdetailiframeWin.fillAutoallotdetail();
}

// 关闭自动分配详情
function closeautoallotdetailindex() {
	 layer.close(autoallotdetailindex);
}

//加载待人工分配订单编辑
function loadautoallotdetailedit(orderid, dmanuserid) {
	orderid = orderid.trim();
	dmanuserid = dmanuserid.trim();
	autoallotdetaileditindex = layer.open({
										  type: 2,// iframe
										  title: ['编辑分配', 'font-size:18px;'],
										  offset: ['10px', '500px'],
										  area: ['1000px', '520px'],
										  closeBtn: 2,// 没有关闭按钮
										  shade: 0,// 不想显示遮罩
										 /* move : '.layui-layer-content',*/ // 整体移动
										  id : 'console_autoallotdetailedit',//不管是什么类型的层，都只允许同时弹出一个
										  isOutAnim: false,// 关闭层时会有一个过度动画,如果你不想开启
										  content: "./console_autoalloc/findautoallotdetailedit?orderid=" + orderid + "&dmanuserid=" + dmanuserid,
										  success: function(layero, index){
											  waitalloteditiframeWin = window[layero.find('iframe')[0]['name']];//得到iframe页的窗口对象，执行iframe页的方法：
										  }
									});
	$("#waitmanualalloteditindex").val(waitmanualalloteditindex);
}

// 关闭自动分配编辑窗口
function closewaitmanualalloteditindex() {
	layer.close(autoallotdetaileditindex);
}

// 分派取派员
//加载待人工分配订单编辑
function loadallotdman(orderid) {
	orderid = orderid.trim();
	allotdmanindex = layer.open({
								  type: 2,// iframe
								  title: ['分派取派员', 'font-size:18px;'],
								  offset: ['10px', '500px'],
								  area: ['1000px', '1050px'],
								  closeBtn: 2,// 没有关闭按钮
								  shade: 0,// 不想显示遮罩
								 /* move : '.layui-layer-content',*/ // 整体移动
								  id : 'console_allotdman',//不管是什么类型的层，都只允许同时弹出一个
								  isOutAnim: false,// 关闭层时会有一个过度动画,如果你不想开启
								  content: "./console_autoalloc/findallotdman?orderid=" + orderid,
								  success: function(layero, index){
									  allotdmaniframeWin = window[layero.find('iframe')[0]['name']];//得到iframe页的窗口对象，执行iframe页的方法：
								  }
						});
}
// 关闭分派取派员页面
function closeallotdmanindex() {
	layer.close(allotdmanindex);
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

//刷新待分配订单数量
function countwaitmanualalloc() {
	parent.countwaitmanualalloc();
}
//刷新自动分配订单数量
function countautoalloc() {
	parent.countautoalloc();
}