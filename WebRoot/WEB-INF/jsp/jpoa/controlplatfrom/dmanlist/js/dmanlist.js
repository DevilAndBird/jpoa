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
  $(div).html("<img  alt='' src='" + IMG_URL_PATH.MAP_REFRESH_URL.value + "'/>");
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
// 添加到地图当中
map.addControl(myZoomCtrl);
// =================================初始化结束=============================================
// =================================地图数据填充===========================================
//开启实时定位
editInterval();
function editInterval() {
	gpsinit();
	/*setInterval(gpsinit, TIMING_TIME.CONSOLE_DMANLIST_MAP.value);*/
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

// ===================================================================
findDmanList();

// 全局
var layer;
layui.use('layer', function(){
	  layer = layui.layer;
});              

//模态框初始化加载
$("#myModal").modal();
// 模态框可移动
$("#myModal").draggable();

$("#dmanname").keydown(function(e) {  
    if (e.keyCode == 13) {  
    	findDmanList();
    }  
});  

function findDmanList() {
	var dmanname = $("#dmanname").val().trim();
	
	$.ajax({
		url : "./controlplatform_dmanlist/findDmanList",
		data : {
			dmanname : dmanname
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			$("#dmanList").html("");
			var json = eval(data);
			$.each(json, function (i, item) {
				var olddmanList = $("#dmanList").html();
				
				olddmanList = olddmanList + "<table onmouseover='mouseover1(this)' onmouseout='mouseout1(this)' onclick='dmanDelis(this)' style='margin-top:10px;border:solid medium #efefef; width:370px;height:70px;'>" +
								     			"<input type='hidden' value='" + json[i].userid + "'/>" +
												"<tr>" +
								  					"<td colspan='2'>" +
								  						"<label style='font-weight:bold;'>&nbsp;&nbsp;&nbsp;&nbsp;" + json[i].name + "</label>" +
								     				"</td>"+
								     				"<td colspan='4'>" +
								      					"<label style='font-weight:bold;'>&nbsp;&nbsp;&nbsp;&nbsp;" + json[i].mobile + "</label>" +
								     				"</td>" +
								     			"</tr>" +
								     			"<tr>" +
													"<td colspan='2'><label style='font-weight:bold;'>&nbsp;&nbsp;&nbsp;&nbsp;待取：" + json[i].waitpick_Count + "</label></td>" +
													"<td colspan='2'><label style='font-weight:bold;'>&nbsp;&nbsp;&nbsp;&nbsp;待派：" + json[i].waitsend_count + "</label></td>" +
													"<td colspan='2'><label style='font-weight:bold;'>&nbsp;&nbsp;&nbsp;&nbsp;已完成：" + json[i].finished_count + "</label></td>" +
								     			"</tr>" +
								     	   "</table>" ;
				
				
				$("#dmanList").html(olddmanList);
            });
			
		}
	});	 
}

function mouseover1 (param) {
	$(param).css("border","solid thick #efefef");
}

function mouseout1 (param) {
	$(param).css("border","solid medium #efefef");
}

function dmanDelis(param) {
	var userid = $(param).children(" [type='hidden']").val();

	layer.open({
	  type: 2,
	  title: ['取派员详情', 'font-size:18px;'],// 不需要标题
	  id : 'dmanDelisOpen',//不管是什么类型的层，都只允许同时弹出一个
	  area: ['100%', '100%'],
	  fixed: false, //不固定
	  content: "./controlplatform_dmanDatails/thermaldeployment?dmanuserid=" + userid
	});
}





$("#nav-search-refresh").click(function() {
	findDmanList();
});

//刷新待分配订单数量
function countwaitmanualalloc() {
	parent.countwaitmanualalloc();
}
//刷新自动分配订单数量
function countautoalloc() {
	parent.countautoalloc();
}
