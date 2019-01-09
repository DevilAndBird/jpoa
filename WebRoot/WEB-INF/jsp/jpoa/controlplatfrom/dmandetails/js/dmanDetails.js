//============================地图初始化=========================================
var map = new BMap.Map("container");

var city_lng = $("#loginperson_city_lng").val();
var city_lat = $("#loginperson_city_lat").val();
//创建地图动态(上海)坐标中心
var point = new BMap.Point(city_lng, city_lat);
		
// 初始化地图，设置中心点坐标和地图级别  
map.centerAndZoom(point,13);
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
// ============================地图填充=====================================
// 路线覆盖物
var currentPath, nextdmanPath;

//开启实时定位
editInterval();
function editInterval() {
	gpsinit();
	setInterval(gpsinit, TIMING_TIME.CONSOLE_DMANDETAILS_MAP.value);
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
	// 订单详情刷新
	findDmanDetails();
}

//刷新待分配订单数量
function countwaitmanualalloc() {
	parent.countwaitmanualalloc();
}
//刷新自动分配订单数量
function countautoalloc() {
	parent.countautoalloc();
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

// 移除覆盖物
function delentitypath() {
    // 移除polyline
    map.removeOverlay(currentPath);
}

function addnextpath(param) {
	var orderid = $(param).next().next(" [name='orderid']").val();
	var destaddress = $(param).next(" [name='destaddress']").val();
	
	$.ajax({
		url : "./controlplatform_dmanDatails/orderTransitLaterPath",
		data : {
			orderid : orderid,
			destaddress : destaddress
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			var dmanOrderTask = data['dmanOrderTask'];
			var dmanOrderSend = data['dmanOrderSend'];
			
			
			if(!dmanOrderTask) {
				return;
			}
			
			if(!dmanOrderSend) {
				return;
			}
			
			var taskgps = dmanOrderTask.destgps;
			var sendgps = dmanOrderSend.destgps;
			
			taskgps = eval('(' + taskgps + ')');
			sendgps = eval('(' + sendgps + ')');
			
			// 下个取派员如果送达是酒店或者是住宅则添加送件覆盖物
			var senddesttype = dmanOrderSend.desttype;
			if(DESTINSATION_TYPE.HOTEL.value == senddesttype || DESTINSATION_TYPE.RESIDENCE.value == senddesttype) {
				// 虚拟的送
				var pt = new BMap.Point(sendgps.lng, sendgps.lat);
				var myIcon = new BMap.Icon(IMG_URL_PATH.DESTPOINT_URL.value, new BMap.Size(200,100), {
				    anchor: new BMap.Size(10, 20),
				});
				
				var nexthotel = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
			    
				var label = new BMap.Label("送件",{offset:new BMap.Size(20,-10)});
				nexthotel.setLabel(label);
				
				map.addOverlay(nexthotel);
			}
			
			// 下个取派员路线图
			 var symbol = new BMap.Symbol(BMap_Symbol_SHAPE_BACKWARD_OPEN_ARROW, {
			    scale: 0.6,//图标缩放大小
			    strokeColor:'#fff',//设置矢量图标的线填充颜色
			    strokeWeight: '2',//设置线宽
			 });
			 var IconSequence = new BMap.IconSequence(symbol, '10', '30');
			 // 创建polyline对象
			 
			 pois = [
				new BMap.Point(taskgps.lng, taskgps.lat),
				new BMap.Point(sendgps.lng, sendgps.lat)
			 ];
			 nextdmanPath =new BMap.Polyline(pois, {
			    enableEditing: false,//是否启用线编辑，默认为false
			    enableClicking: true,//是否响应点击事件，默认为true
			    icons:[IconSequence],
			    strokeWeight:'6',//折线的宽度，以像素为单位
			    strokeOpacity: 0.8,//折线的透明度，取值范围0 - 1
			    strokeColor:"#18a45b" //折线颜色
			 });
			map.addOverlay(nextdmanPath); 
			return nextdmanPath;
		}
	});	 
	
}


findDmanDetails();
findDmanList();

//模态框初始化加载
$("#myModal").modal(); 
// 模态框可移动
$("#myModal").draggable();
// 初始化修改页面

function findDmanDetails() {
	 var dmanuserid = $("#dmanuserid").val().trim();
	 var orderno = $("#orderno").val().trim();
	 var countdown = $("#countdown").val().trim();
	 var isfinish = $("#isfinish").attr("selected", true).val();
	 var isfinishList = "['UNFINISHED','ONGOING']";
	 if(IS_FINISH.FINISHED.value == isfinish) {
		 isfinishList = "['FINISHED']";
	 }
	 // 校验
	 if(orderno.length > 4) {
		 toastr.warning('订单号模糊查询不能超过4位数');
	 }
	 if(!purenum_check(countdown)) {
		 toastr.warning('要是纯数字');
	 }
	 if(countdown >= 60) {
		 toastr.warning('填写60分钟以内');
	 }
	 
	$.ajax({
		url : "./controlplatform_dmanDatails/dmaDetails",
		data : {
			dmanuserid : dmanuserid,
			orderno : orderno,
			countdown : countdown,
			isfinishList : isfinishList
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			$("#dmanDetails").html("");
			var json = eval(data);
			
			var dmanDetails = '';
			
			var partpathindex = 0;
			$.each(json, function (i, item) {
				partpathindex = partpathindex + 1;
				
				// 取
				var dmanOrderTask = json[i].dmanOrderTask;
				var src = "";
				var srcname = "无";
				var taskisfinishvalue = IS_FINISH.FINISHED.value;
				var taskIsfinish = "";
				var taskDestaddrname = "";
				var taskDestaddrdesc = "";
				var taskarrivedtime_Hm = "";
				var taskgps_lng = '';
				var taskgps_lat = '';
				if(dmanOrderTask != "") {
					// 取件目的地类型
					src = dmanOrderTask.desttype;
					srcname = DESTINSATION_TYPE[src].name;
					// 动作是否完成
					taskisfinishvalue = dmanOrderTask.isfinish;
					// 动作实际达到时间展示
					if(IS_FINISH.FINISHED.value == taskisfinishvalue) {
						// 如果取动作已经完成，则展示实际到达时间
						taskIsfinish = "<img src='" + IMG_URL_PATH.TRUE_URL.value + "'>&nbsp;<lable class='actionfinishtime'>" + fomate_Hm(dmanOrderTask.actionfinishtime) + "</lable>";
					} else {
						if(ROLE_TYPE.ROLE_AIRPORT_TASK.value == dmanOrderTask.roletype || ROLE_TYPE.ROLE_HOTEL_TASK.value == dmanOrderTask.roletype) {
							taskIsfinish = IS_FINISH[dmanOrderTask.isfinish].name;
						} else {
							var isovertime = isOvertime(dmanOrderTask.arrivedtime);
							if(isovertime == -2) {
								// 时间大于30分钟
								taskIsfinish = IS_FINISH[dmanOrderTask.isfinish].name;
							} else if (isovertime == -1) {
								// 时间超时
								taskIsfinish = "<img src='" + IMG_URL_PATH.BOMB_OVERTIME_24_URL.value + "'>&nbsp;<lable class='lableovertime'>超时!</lable>";
							} else {
								// 时间小于30分钟
								taskIsfinish = "<img id='imgtaskcountdown" + dmanOrderTask.id + "' src='" + IMG_URL_PATH.BOMB_COUNTDOWN_URL.value + "'>&nbsp;<lable class='classcountdown' id='taskcountdown" + dmanOrderTask.id + "'></lable><input type='hidden' name='taskcountdown' alt='" + dmanOrderTask.id + "' value='" + isovertime + "'>";
							}
						}
					}
					
					// 动作最迟到达时间
					taskarrivedtime_Hm = isgreaterTodata(dmanOrderTask.arrivedtime);
					if(ROLE_TYPE.ROLE_AIRPORT_TASK.value == dmanOrderTask.roletype || ROLE_TYPE.ROLE_HOTEL_TASK.value == dmanOrderTask.roletype) {
						taskarrivedtime_Hm = "最早:" + taskarrivedtime_Hm;
					} else {
						taskarrivedtime_Hm = "最迟:" + taskarrivedtime_Hm;
					}
					
					// 取动作目的地地标
					taskDestaddrname = dmanOrderTask.destaddrname;
					
					// 取动作目的地详细地址
					taskDestaddrdesc = dmanOrderTask.destaddrdesc;
					
					var destgpstemp = eval("("+ dmanOrderTask.destgps +")");
					taskgps_lng = destgpstemp.lng;
					taskgps_lat = destgpstemp.lat;
				}
				
				// 送
				var dmanOrderSend = json[i].dmanOrderSend;
				var dest = "";
				var destname = "无";
				var destaddress = "";
				var sendisfinishvalue = IS_FINISH.FINISHED.value;
				var sendIsfinish = "";
				var sendDestaddrname = "";
				var sendDestaddrdesc = "";
				var sendarrivedtime_Hm = "";
				var sendgps_lng = '';
				var sendgps_lat = '';
				if(dmanOrderSend != "") {
					// 送件目的地类型
					dest = dmanOrderSend.desttype;
					destname = DESTINSATION_TYPE[dest].name;
					destaddress = dmanOrderSend.destaddress;
					
					// 动作是否完成
					sendisfinishvalue = dmanOrderSend.isfinish;
					// 动作实际达到时间
					if(IS_FINISH.FINISHED.value == sendisfinishvalue) {
						// 如果取动作已经完成，则展示实际到达时间
						sendIsfinish = "<img src='" + IMG_URL_PATH.TRUE_URL.value + "'>&nbsp;<lable class='actionfinishtime'>" + fomate_Hm(dmanOrderSend.actionfinishtime) + "</lable>";
					} else {
						if(ROLE_TYPE.ROLE_AIRPORT_TASK.value == dmanOrderSend.roletype || ROLE_TYPE.ROLE_HOTEL_TASK.value == dmanOrderSend.roletype) {
							sendIsfinish = IS_FINISH[dmanOrderSend.isfinish].name;
						} else {
							var isovertime = isOvertime(dmanOrderSend.arrivedtime);
							if(isovertime == -2) {
								// 时间大于30分钟
								sendIsfinish = IS_FINISH[dmanOrderSend.isfinish].name;
							} else if (isovertime == -1) {
								// 时间超时
								sendIsfinish = "<img src='" + IMG_URL_PATH.BOMB_OVERTIME_24_URL.value + "'>&nbsp;<lable class='lableovertime'>超时!</lable>";
							} else {
								// 时间小于30分钟
								sendIsfinish = "<img id='imgsendcountdown" + dmanOrderSend.id + "' src='" + IMG_URL_PATH.BOMB_COUNTDOWN_URL.value + "'>&nbsp;<lable class='classcountdown' id='sendcountdown" + dmanOrderSend.id + "'></lable><input type='hidden' name='sendcountdown' alt='" + dmanOrderSend.id + "' value='" + isovertime + "'>";
							}
						}
					}
					
					// 动作最迟到达时间
					sendarrivedtime_Hm = isgreaterTodata(dmanOrderSend.arrivedtime);
					if(ROLE_TYPE.ROLE_AIRPORT_TASK.value == dmanOrderSend.roletype || ROLE_TYPE.ROLE_HOTEL_TASK.value == dmanOrderSend.roletype) {
						sendarrivedtime_Hm = "最早:" + sendarrivedtime_Hm;
					} else {
						sendarrivedtime_Hm = "最迟:" + sendarrivedtime_Hm;
					}
					
					// 送动作目的地地标
					sendDestaddrname = dmanOrderSend.destaddrname;
					// 取动作目的地详细地址
					sendDestaddrdesc = dmanOrderSend.destaddrdesc;
					
					var sendgpstemp = eval("("+ dmanOrderSend.destgps +")");
					
					sendgps_lng = sendgpstemp.lng;
					sendgps_lat = sendgpstemp.lat;
				}
				
				// 订单id
				var orderid = json[i].id;
				// 订单号后四位截取
				var ordernosuffix = json[i].orderno.substring((json[i].orderno.length-4),json[i].orderno.length); 
				
				// 取件类型
				var mailingway = json[i].mailingway;
				var mailingwayname = '--';
				if(mailingway != null && mailingway != '') {
					mailingwayname = MAILING_WAY[mailingway].name;
				}
				var mailingwaytype = mailingwayname;
				if(DESTINSATION_TYPE.TRANSITCERTER.value == src || src == "") {
					mailingwaytype = "无";
				}
				
				// 收件类型
				var backway = json[i].backway;
				var backwayname = '--';
				if(backwayname != null && backwayname != '') {
					backwayname =  MAILING_WAY[backway].name;
				}
				var backwaytype = backwayname;
				if(DESTINSATION_TYPE.TRANSITCERTER.value == dest || dest == "") {
					backwaytype = "无";
				}
				
				// 订单行李数
				var lug = json[i].num;
				
				// 判断是否为专车
				var spec_flag_url = '';
				var channel = json[i].channel;
				var servicetype = channel.split('_')[1];
				if(SERVICE_TYPE.SC.value == servicetype ) {
					spec_flag_url = IMG_URL_PATH.SPECIAL_CAR_URL.value;
				}
				
				// 特殊处理_如果出发地为机场，判断机场是否取件成功
				var airport_tasked_flag = '';
				if('N' == json[i].airport_tasked_flag) {
					airport_tasked_flag = '<font style="font-weight: bold;color: #D21736">(机场未取件)</font>';
				}
				
				dmanDetails = dmanDetails + "<table style='background:url("+ spec_flag_url +") no-repeat;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+ spec_flag_url +"',sizingMethod='scale'); background-size:100% 100%;' ondblclick='cancelpartdetailpatch(this)' onmouseenter='moveaddentitypath(this)' onmouseleave='movedelentitypath(this)' style='margin-top:10px; width:500px;height:70px;'>" +
												"<input type='hidden' name='taskgps_lng' value='" + taskgps_lng + "'>" +
												"<input type='hidden' name='taskgps_lat' value='" + taskgps_lat + "'>" +
												"<input type='hidden' name='sendgps_lng' value='" + sendgps_lng + "'>" +
												"<input type='hidden' name='sendgps_lat' value='" + sendgps_lat + "'>" +
												"<tr>" +
													"<td colspan='3'>" +
														"&nbsp;&nbsp;&nbsp;&nbsp;" + srcname +" - "+ destname + 
														"&nbsp;&nbsp;&nbsp;&nbsp;# " + ordernosuffix + 
														"&nbsp;&nbsp;&nbsp;&nbsp;" + mailingwaytype + airport_tasked_flag + " - " + backwaytype + 
									 				"</td>" +
									 				"<td colspan='1'>" +
								  					
									 				"</td>" +
									 				"<td colspan='3'>" +
									  					
									 				"</td>" +
									 			"</tr>" +
									 			"<tr style='height: 10px' />" ;
				if(dmanOrderTask != "") {
					dmanDetails = dmanDetails + "<tr>" +
									 				"<td rowspan='2' colspan='1' style='width: 50px'>&nbsp;&nbsp;&nbsp;&nbsp;<img alt='' src='" + IMG_URL_PATH.SRCADDRESS_URL.value + "'></td>" +
													"<td colspan='4'><label style='font-weight:bold;'>" + taskDestaddrname + "</label></td>" +
													"<td colspan='1'><label style='font-weight:bold;'>" +
													"&nbsp;&nbsp;&nbsp;&nbsp;"
														
													+ taskIsfinish + 
													
													"</label></td>" +
									 			"</tr>" +
									 			"<tr>" +
													"<td colspan='4'><div name='taskDeskaddrdesc' style='width: 280px;'><label style='font-weight:bold;'>" + taskDestaddrdesc + "</label></div></td>" +
													"<td colspan='1'><label style='font-weight:bold;'>&nbsp;&nbsp;&nbsp;&nbsp;" + taskarrivedtime_Hm + "</label></td>" +
									 			"</tr>" +
									 			"<tr style='height: 10px' />" ; 
				}
				
				if(dmanOrderSend != "") {
					dmanDetails = dmanDetails +"<tr>" +
									 				"<td rowspan='2' colspan='1' style='width: 50px'>&nbsp;&nbsp;&nbsp;&nbsp;<img alt='' src='" + IMG_URL_PATH.DESTADDRESS_URL.value + "'></td>" +
													"<td colspan='4'><label style='font-weight:bold;'>" + sendDestaddrname + "</label></td>" +
													"<td colspan='1'><label style='font-weight:bold;'>&nbsp;&nbsp;&nbsp;&nbsp;" + sendIsfinish + "</label></td>" +
									 			"</tr>" +
									 			"<tr>" +
													"<td colspan='4'><div name='sendDeskaddrdesc' style='width: 280px;'><label style='font-weight:bold;'>" + sendDestaddrdesc + "</label></div></td>" +
													"<td colspan='1'><label style='font-weight:bold;'>&nbsp;&nbsp;&nbsp;&nbsp;" + sendarrivedtime_Hm + "</label></td>" +
									 			"</tr>" +
									 			"<tr style='height: 10px' />";
				}
									 			
					dmanDetails = dmanDetails +"<tr>" +
									 				"<td colspan='1' style='width: 50px'>&nbsp;&nbsp;&nbsp;&nbsp;<img alt='' src='" + IMG_URL_PATH.LUG_URL.value + "'></td>" +
													"<td colspan='4'><label style='font-weight:bold;'>行李数量：" + lug + "</label></td>" +
													"<td colspan='1'>&nbsp;&nbsp;" +
													"<img title='单击：查看局部路径' onclick='partpathway(this)' src='" + IMG_URL_PATH.ROUTE_URL.value + "'>" + 
													"<input type='hidden' name='partpathindex' value='"+ partpathindex +"'>" ;
					
				// 之后经过集散中心的时候才会显示后续的任务						
				if(DESTINSATION_TYPE.TRANSITCERTER.value == dest) {
					dmanDetails = dmanDetails +"<img onclick='onmouseenterLight(this)' title='单击：查看订单下个路线图' src='" + IMG_URL_PATH.LIGHT_URL.value + "'>" +
					 						   "<input type='hidden' name='destaddress' value='" + destaddress + "'>" +
											   "<input type='hidden' name='orderid' value='" + orderid + "'>";
				}
				
				if(IS_FINISH.FINISHED.value != taskisfinishvalue && IS_FINISH.FINISHED.value != sendisfinishvalue) {
					dmanDetails = dmanDetails +"&nbsp;&nbsp;" +
															"<img onclick='orderallocpage(this)' alt='' src='" + IMG_URL_PATH.EDIT_URL.value + "'> " +
															"<input type='hidden' name='orderid' value='" + orderid + "'>" ;
				}
					dmanDetails = dmanDetails +"</td>" +
									 			"</tr>" +
									 	   "</table>" +
									 	   "<hr/>";
            });
			$("#dmanDetails").html(dmanDetails);
			loadcountdown();
		}
	});	 
}

var maptemp = [];
//局部路径查看
function partpathway(param) {
	var cacheindex = $(param).parents('table').find("[name='partpathindex']").val();
	map.removeOverlay(maptemp[cacheindex]);
	var taskgps_lng = $(param).parents('table').find("[name='taskgps_lng']").val();
	var taskgps_lat = $(param).parents('table').find("[name='taskgps_lat']").val();
	var sendgps_lng = $(param).parents('table').find("[name='sendgps_lng']").val();
	var sendgps_lat = $(param).parents('table').find("[name='sendgps_lat']").val();
	var tempindex = orderpartdetailpath(taskgps_lng, taskgps_lat, sendgps_lng, sendgps_lat);
	maptemp[cacheindex] = tempindex;
}

//添加局部路径
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


var nextpath = [];
function onmouseenterLight (param) {
	var cacheindex = $(param).parents('table').find("[name='partpathindex']").val();
	var nextindex = addnextpath(param);
	nextpath[cacheindex] = nextindex;
}

//取消订单路径信息
function cancelpartdetailpatch(param) {
	var tempindex = $(param).find("[name='partpathindex']").val();
	map.removeOverlay(maptemp[tempindex]);
	map.removeOverlay(nextpath[tempindex]);
}

function loadcountdown() {
	var taskcountdownLIST = $("[name='taskcountdown']");
	taskcountdownLIST.each(function(){
		var temp = $(this).attr("alt");
		var countdowntime =  $(this).val();
		countTime(countdowntime,  "#taskcountdown"+ temp, "#imgtaskcountdown"+temp);
	});
	
	var sendcountdownLIST = $("[name='sendcountdown']");
	sendcountdownLIST.each(function(){
		var sendtemp = $(this).attr("alt");
		var countdowntime =  $(this).val();
		countTime(countdowntime,  "#sendcountdown"+ sendtemp, "#imgsendcountdown"+sendtemp);
	});
}

function findDmanList() {
	var dmanuserid = $("#dmanuserid").val().trim();
	
	$.ajax({
		url : "./controlplatform_dmanDatails/findDmanList",
		data : {
			dmanuserid : dmanuserid
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			var json = eval(data);
			$.each(json, function (i, item) {
				var dmanname = json[i].name;
				var waipack = json[i].waitpick_Count;
				var waitsend = json[i].waitsend_count;
				$("#dmanname").html(dmanname);
				$("#count").html("取：" + waipack + ", 派：" + waitsend);
            });
			
		}
	});	 
}

// 已完成的单子不需要查询是否要迟到了
$("#isfinish").change(function() {
	var isfinish = $("#isfinish").attr("selected", true).val();
	
	if(IS_FINISH.FINISHED.value == isfinish) {
		 $("#countdown").val("");
		 $("#countdown").attr("readOnly",true);
	 }
	
	if(IS_FINISH.ONGOING.value == isfinish
			|| IS_FINISH.UNFINISHED.value == isfinish) {
		 $("#countdown").val("");
		 $("#countdown").attr("readOnly",false);
	 }
});

$("#nav-search-refresh").click(function() {
	findDmanDetails();
	findDmanList();
});


function moveaddentitypath (param) {
	$(param).css("border","solid thick #efefef");
}

function movedelentitypath (param) {
	$(param).css("border","solid medium #ffffff");
}



