layui.use('form', function(){
	  var form = layui.form;
});
var layer;
layui.use('layer', function(){
	  layer = layui.layer;
});

function mouseenterautoallot (param) {
	$(param).css("border","solid thick #efefef");
	
}

function mouseleaveautoallot (param) {
	$(param).css("border","solid medium #ffffff");
	
	
}

// 单击订单途径路径
function orderpathway(param) {
	parent.moveorderpath();
	var orderid = $(param).parents('table').find("[name='orderid']").val();
	parent.lookpath(orderid);
}

// 查询待分配订单列表
$("#nav-search-refresh").click(function() {
	fillAutoallotlist();
});

// 初始化待人工分配订单
fillAutoallotlist();

function fillAutoallotlist() {
	var ordernosuffix = $("#orderno").val().trim();
	
	if(ordernosuffix.length > 4) {
		 toastr.warning('订单号模糊查询不能超过4位数');
	 }
	
	var mobile = $("#mobile").val().trim();
	var timeScreenType = $("#timeScreenType").val().trim();
	var screenmin = $("#screenmin").val().trim();
	
	 if(timeScreenType != '-1' && (screenmin == null || screenmin == '')) {
		 layer.msg('请填写筛选时间');
		return;
	 }
	 
	 $.ajax({
			url : "./console_autoalloc/fillAutoAlloc",
			data : {
				orderno : ordernosuffix,
				mobile : mobile,
				timeScreenType : timeScreenType,
				screenmin : screenmin
			},
			dataType : "json",
			type : "POST",
			success : function(data) {
				$("#divAutoallotlist").html("");
				var json = eval(data);
				var autolisttemp = '';
				
				
				$.each(json, function (i, item) {
					// 特殊处理_判断是否为专车
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
					
					autolisttemp = autolisttemp + "<table style='background:url("+ spec_flag_url +") no-repeat;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+ spec_flag_url +"',sizingMethod='scale'); background-size:100% 100%;' title='双击：进入订单路径详情列表' class='tableautoallot' ondblclick='loadautoallotdetatil(this)' onmouseenter='mouseenterautoallot(this)' onmouseleave='mouseleaveautoallot(this)'>" +
														"<tr>" +
															"<td colspan='2'>" +
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
																"&nbsp;&nbsp;&nbsp;&nbsp;<img src='" + IMG_URL_PATH.SRCADDRESS_URL.value + "'/>" +
															"</td>" +
															"<td colspan='4'>";
					
																if(json[i].scrlandmark != null) {
																	autolisttemp = autolisttemp + json[i].scrlandmark;
																} else {
																	autolisttemp = autolisttemp + "------";
																};
																
							 autolisttemp = autolisttemp + "</td>" +
															"<td colspan='1'></td>" +
											 			"</tr>" +
											 			"<tr>" +
															"<td colspan='4'>" +
																"<div class='addrdesc'>";
							 										if(json[i].srcprovname != null) {
							 											autolisttemp = autolisttemp + json[i].srcprovname;
							 										}
																	if(json[i].srccityname != null) {
																		autolisttemp = autolisttemp + json[i].srccityname;
																	}
																	if(json[i].srcdistrictname != null) {
																		autolisttemp = autolisttemp + json[i].srcdistrictname;
																	}
																	if(json[i].srcaddress != null) {
																		autolisttemp = autolisttemp + json[i].srcaddress;
																	}
							       autolisttemp = autolisttemp + "</div>"+
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
																	autolisttemp = autolisttemp + json[i].destlandmark;
																} else {
																	autolisttemp = autolisttemp + "------";
																}
							    autolisttemp = autolisttemp + "</td>" +
														"<td colspan='1'></td>" +
										 			"</tr>" +
										 			"<tr>" +
														"<td colspan='4'>" +
															"<div class='addrdesc'>";
															    if(json[i].destprovname != null) {
																		autolisttemp = autolisttemp + json[i].destprovname;
																	}
																if(json[i].destcityname != null) {
																	autolisttemp = autolisttemp + json[i].destcityname;
																}
																if(json[i].destdistrictname != null) {
																	autolisttemp = autolisttemp + json[i].destdistrictname;
																}
																if(json[i].destaddress != null) {
																	autolisttemp = autolisttemp + json[i].destaddress;
																}
								autolisttemp = autolisttemp + "</div>" +
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
															"<img title='单击：查看订单路径' onclick='orderpathway(this)' src='" + IMG_URL_PATH.ROUTE_URL.value + "'>" +
															"&nbsp;&nbsp;" +
															"<img title='单击：进入订单详细信息' onclick='loadorderdetails(this)' src='" + IMG_URL_PATH.DETAILS_URL.value + "'>" +
															"<input type='hidden' name='orderid' value=' " + json[i].id + " '>" +
															"</td>" +
													"</tr>" +
												"</table>";
				});
				
				$("#divAutoallotlist").html(autolisttemp);
				
			}
		});
}

//layer 模块加载
var layer;
layui.use('layer', function(){
	  layer = layui.layer;
});

// 订单分派动作详情
function loadautoallotdetatil(param) {
	var orderid = $(param).find(" [name='orderid']").val().trim();
	parent.fillautoallotdetail(orderid);
}

function loadorderdetails(param) {
	var orderid = $(param).parents("table").find(" [name='orderid']").val().trim();
	parent.loadorderdetails(orderid);
}

