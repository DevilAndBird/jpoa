var layer,form;
layui.use('form', function(){
	  form = layui.form;
});
layui.use('layer', function(){
	  layer = layui.layer;
});

function mouseenterwaitallot (param) {
	$(param).css("border","solid thick #f2f2f2");
}

function mouseleavewaitallot (param) {
	$(param).css("border","solid medium #ffffff");
}

// 单机显示出发地目的地坐标图标
function onclickload_img(param) {
	// 清除出发地目的地坐标图标;
	parent.removeordersrcanddest();
	
	var srcgps_lng = $(param).parents('table').find("[name='srcgps_lng']").val();
	var srcgps_lat = $(param).parents('table').find("[name='srcgps_lat']").val();
	var destgps_lng = $(param).parents('table').find("[name='destgps_lng']").val();
	var destgps_lat = $(param).parents('table').find("[name='destgps_lat']").val();
	// 加载出发地目的地;
	parent.loadordersrcanddest(srcgps_lng, srcgps_lat, destgps_lng, destgps_lat);
}

// 查询待分配订单列表
$("#nav-search-refresh").click(function() {
	fillwaitmanualallotlist();
});

// 初始化待人工分配订单
fillwaitmanualallotlist();

function fillwaitmanualallotlist() {
	var ordernosuffix = $("#orderno").val();
	var mobile = $("#mobile").val();
	 
	 $.ajax({
			url : "./waitmanualalloc/fillWaitmanualAlloc",
			data : {
				orderno : ordernosuffix,
				mobile : mobile
			},
			dataType : "json",
			type : "POST",
			success : function(data) {
				$("#divwaitallotlist").html("");
				var json = eval(data);
				var waitlisttemp = '';
				
				$.each(json, function (i, item) {
					var srcgpstemp = json[i].srcgps;
					var destgpstemp = json[i].destgps;
					
					var srcgps = eval('(' + srcgpstemp + ')');
					var destgps = eval('(' + destgpstemp + ')');
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
					
					
					waitlisttemp = waitlisttemp + "<table style='background:url("+ spec_flag_url +") no-repeat;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+ spec_flag_url +"',sizingMethod='scale'); background-size:100% 100%;' class='tablewaitmanual' title='单击显示地址位置' onmouseenter='mouseenterwaitallot(this)' onmouseleave='mouseleavewaitallot(this)'>" +
														"<tr>" +
															"<td colspan='3'>" +
																"&nbsp;&nbsp;&nbsp;&nbsp;" + json[i].ordertypedesc + "" +
																"&nbsp;&nbsp;&nbsp;&nbsp;#" + json[i].ordernosuffix + "" +
																"&nbsp;&nbsp;&nbsp;&nbsp;" + json[i].mailingwayDesc + airport_tasked_flag + " - " + json[i].backwayDesc + "" +
											 				"</td>" +
											 				"<td colspan='1'>" +
										  					
											 				"</td>" +
											 				"<td colspan='3'>" +
											  					
											 				"</td>" +
											 			"</tr>" +
											 			"<tr style='height: 10px' />" +
														"<tr>" +
															"<td rowspan='2' colspan='1' class='tdimg'>" +
																"&nbsp;&nbsp;&nbsp;&nbsp;<img  alt='' src='" + IMG_URL_PATH.SRCADDRESS_URL.value + "'/>" +
															"</td>" +
															"<td colspan='4'>";
					
																if(json[i].scrlandmark != null) {
																	waitlisttemp = waitlisttemp + json[i].scrlandmark;
																} else {
																	waitlisttemp = waitlisttemp + "------";
																};
																
							 waitlisttemp = waitlisttemp + "</td>" +
															"<td colspan='1'></td>" +
											 			"</tr>" +
											 			"<tr>" +
															"<td colspan='4'>" +
																"<div class='srcaddr'>";
							 										if(json[i].srcprovname != null) {
							 											waitlisttemp = waitlisttemp + json[i].srcprovname;
							 										}
																	if(json[i].srccityname != null) {
																		waitlisttemp = waitlisttemp + json[i].srccityname;
																	}
																	if(json[i].srcdistrictname != null) {
																		waitlisttemp = waitlisttemp + json[i].srcdistrictname;
																	}
																	if(json[i].srcaddress != null) {
																		waitlisttemp = waitlisttemp + json[i].srcaddress;
																	}
							       waitlisttemp = waitlisttemp + "</div>"+
															"</td>"+ 
															"<td colspan='1'>&nbsp;&nbsp;&nbsp;&nbsp;" + json[i].taketimetime + "</td>"+
											 			"</tr>" +
											 			
														"<tr style='height: 10px' />" +
														
														"<tr>" +
											 				"<td rowspan='2' colspan='1' class='tdimg'>" +
											 					"&nbsp;&nbsp;&nbsp;&nbsp;<img src='" + IMG_URL_PATH.DESTADDRESS_URL.value + "'>" +
											 				"</td>" +
															"<td colspan='4'>";
																if(json[i].destlandmark != null) {
																	waitlisttemp = waitlisttemp + json[i].destlandmark;
																} else {
																	waitlisttemp = waitlisttemp + "------";
																}
							    waitlisttemp = waitlisttemp + "</td>" +
														"<td colspan='1'></td>" +
										 			"</tr>" +
										 			"<tr>" +
														"<td colspan='4'>" +
															"<div class='destaddr'>";
															    if(json[i].destprovname != null) {
																		waitlisttemp = waitlisttemp + json[i].destprovname;
																	}
																if(json[i].destcityname != null) {
																	waitlisttemp = waitlisttemp + json[i].destcityname;
																}
																if(json[i].destdistrictname != null) {
																	waitlisttemp = waitlisttemp + json[i].destdistrictname;
																}
																if(json[i].destaddress != null) {
																	waitlisttemp = waitlisttemp + json[i].destaddress;
																}
								waitlisttemp = waitlisttemp + "</div>" +
														"</td>" +
														"<td colspan='1'>&nbsp;&nbsp;&nbsp;&nbsp;" + json[i].sendtimetime + "</td>" +
										 			"</tr>" +
										 			"<tr style='height: 10px' />" +
													"<tr>" +
														"<td rowspan='1' colspan='1' class='tdimg'>" +
															"&nbsp;&nbsp;&nbsp;&nbsp;<img src='" + IMG_URL_PATH.LUG_URL.value + "'>" +
														"</td>" +
														"<td colspan='4'>行李数量：" +  json[i].num + "</td>" +
														"<td colspan='1'>" +
															"&nbsp;&nbsp;" +
															"<img title='单击：进入订单详细信息' onclick='loadorderdetails(this)' src='" + IMG_URL_PATH.DETAILS_URL.value + "'>" +
															"<img title='单击：查看订单路径' onclick='onclickload_img(this)' src='" + IMG_URL_PATH.ROUTE_URL.value + "'>" +
															"&nbsp;&nbsp;<img title='双击点击进入编辑页' onclick='loadwaitmanualallotedit(this)' src='" + IMG_URL_PATH.EDIT_URL.value + "'>" +
															"<input type='hidden' name='orderid' value=' " + json[i].id + " '>" +
															"<input type='hidden' name='srcgps_lng' value=' " + srcgps.lng + " '>" +
															"<input type='hidden' name='srcgps_lat' value=' " + srcgps.lat + " '>" +
															"<input type='hidden' name='destgps_lng' value=' " + destgps.lng + " '>" +
															"<input type='hidden' name='destgps_lat' value=' " + destgps.lat + " '>" +
														"</td>" +
													"</tr>" +
												"</table>" + 
											    "<hr>";
				});
				
				$("#divwaitallotlist").html(waitlisttemp);
				
			}
		});
}

//layer 模块加载
var layer;
layui.use('layer', function(){
	  layer = layui.layer;
});

function loadwaitmanualallotedit(param) {
	var orderid = $(param).next().val();
	parent.loadwaitmanualallotedit(orderid);
}

// 订单详情
function loadorderdetails(param) {
	var orderid = $(param).parents("table").find(" [name='orderid']").val().trim();
	parent.loadorderdetails(orderid);
}

