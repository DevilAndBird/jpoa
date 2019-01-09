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

// 刷新自动分配列表详情
function refreshautoallotdetail() {
	fillAutoallotdetail();
}

// 初始化待人工分配订单
fillAutoallotdetail();

function fillAutoallotdetail() {
	 var orderid = $("#orderid").val().trim();
	 $.ajax({
			url : "./console_autoalloc/orderpathdetails",
			data : {
				orderid : orderid
			},
			dataType : "json",
			type : "POST",
			success : function(data) {
				$("#divAutoallotdetail").html("");
				
				$("#ordertypedesc").text(data.ordertypedesc);
				$("#ordersuffix").text("#"+data.ordernosuffix);
				$("#orderlug").text("行李数:" + data.num);
				$("#mailingway").text(data.mailingwayDesc + "-" + data.backwayDesc);
				$("#cusname").text(data.cusname);
				$("#cusmobile").text(data.cusmobile);
				
				var json = eval(data.orderlinkdmanList);
				var autolistdetail = '';
				
				var partindex = 0;
				
				$.each(json, function (i, item) {
					partindex = partindex +1;
					
					var taskgps_lng = '';
					var taskgps_lat = '';
					if(json[i].task != '') {
						var taskgpstemp = eval("("+ json[i].task.destgps +")");
						taskgps_lng = taskgpstemp.lng;
						taskgps_lat = taskgpstemp.lat;
					}
					var sendgps_lng = '';
					var sendgps_lat = '';
					if(json[i].send != '') {
						var sendgpstemp = eval("("+ json[i].send.destgps +")");
						sendgps_lng = sendgpstemp.lng;
						sendgps_lat = sendgpstemp.lat;
					}
					
					autolistdetail = autolistdetail + "<table title='双击：取消路径查看'  class='tableautoallot' ondblclick='cancelpartdetailpatch(this)' onmouseenter='mouseenterautoallot(this)' onmouseleave='mouseleaveautoallot(this)'>" +
														"<input type='hidden' name='taskgps_lng' value='" + taskgps_lng + "'>" +
														"<input type='hidden' name='taskgps_lat' value='" + taskgps_lat + "'>" +
														"<input type='hidden' name='sendgps_lng' value='" + sendgps_lng + "'>" +
														"<input type='hidden' name='sendgps_lat' value='" + sendgps_lat + "'>" +
					
														"<input type='hidden' value='" + json[i].roleid + "'>" +
														"<tr>" +
															"<td colspan='3'>" +
																"&nbsp;&nbsp;&nbsp;&nbsp;" +
															    json[i].dmaninfo.name + 
															    "&nbsp;&nbsp;&nbsp;&nbsp;"+
																"取:" +json[i].dmaninfo.waitpick_Count +
																 "&nbsp;&nbsp;&nbsp;&nbsp;"+
																"派:" +json[i].dmaninfo.waitsend_count +
											 				"</td>" +
											 			"</tr>" +
											 			
											 			"<tr style='height: 10px' />" +
											 			
														"<tr>" +
															"<td rowspan='2' colspan='1' class='tdimg'>" +
																"&nbsp;&nbsp;&nbsp;&nbsp;<img  alt='' src='" + IMG_URL_PATH.SRCADDRESS_URL.value + "'/>" +
															"</td>" +
															"<td colspan='4'>";
																if(json[i].task != '' || json[i].task.destaddrname != null || json[i].task.destaddrname != '') {
																	autolistdetail = autolistdetail + json[i].task.destaddrname;
																} else {
																	autolistdetail = autolistdetail + "------";
																};
						autolistdetail = autolistdetail + "</td>" +
														  "<td colspan='1'>" +
						 									"";
																if(json[i].task != '' ) {
																	if(IS_FINISH.FINISHED.value == json[i].task.isfinish) {
																		autolistdetail = autolistdetail + "<img src='" + IMG_URL_PATH.TRUE_URL.value + "'>&nbsp;<lable class='actionfinishtime'>" + fomate_Hm(json[i].task.actionfinishtime) + "</lable>";
																	} else {
																		if(ROLE_TYPE.ROLE_AIRPORT_TASK.value == json[i].task.roletype || ROLE_TYPE.ROLE_HOTEL_TASK.value == json[i].task.roletype) {
																			// 动作类型是 去酒店取、去机场取 则显示原始数据
																			autolistdetail = autolistdetail + IS_FINISH[json[i].task.isfinish].name;
																		} else {
																			// 在30分钟之外
																			var taskisovertime = isOvertime(json[i].task.arrivedtime);
																			if(taskisovertime == -2) {
																				autolistdetail = autolistdetail + IS_FINISH[json[i].task.isfinish].name;
																			} else if (taskisovertime == -1) {
																				autolistdetail = autolistdetail + "<img src='" + IMG_URL_PATH.BOMB_OVERTIME_24_URL.value + "'>&nbsp;<lable class='lableovertime'>超时!</lable>";
																			} else {
																				autolistdetail = autolistdetail + "<img id='imgtaskcountdown" + json[i].task.id + "' src='" + IMG_URL_PATH.BOMB_COUNTDOWN_URL.value + "'>&nbsp;<lable class='classcountdown' id='taskcountdown" + json[i].task.id + "'></lable><input type='hidden' name='taskcountdown' alt='" + json[i].task.id + "' value='" + taskisovertime + "'>";
																			}
																		}
																	}
																} else {
																	autolistdetail = autolistdetail + "----";
																};
						autolistdetail = autolistdetail +  "</td>" +
											 			"</tr>" +
											 			"<tr>" +
															"<td colspan='4'>" +
																"<div class='addrdesc'>";
							 										if(json[i].task != '') {
							 											autolistdetail = autolistdetail + json[i].task.destaddrdesc;
																	} else {
																		autolistdetail = autolistdetail + "--------------";
																	}
							  autolistdetail = autolistdetail + "</div>"+
															"</td>"+ 
															"<td colspan='1'>";
								   								if(json[i].task != '') {
								   									if(ROLE_TYPE.ROLE_AIRPORT_TASK.value == json[i].task.roletype || ROLE_TYPE.ROLE_HOTEL_TASK.value == json[i].task.roletype) {
																		// 动作类型是 去酒店取、去机场取 则显示原始数据
								   autolistdetail = autolistdetail + "最早:" + isgreaterTodata(json[i].task.arrivedtime);
																	} else {
								   autolistdetail = autolistdetail + "最迟:" + isgreaterTodata(json[i].task.arrivedtime);
																	}
																} 
						   autolistdetail = autolistdetail +"</td>"+
											 			"</tr>" +
											 			
														"<tr style='height: 10px' />" +
														
														"<tr>" +
											 				"<td rowspan='2' colspan='1' class='tdimg'>" +
											 					"&nbsp;&nbsp;&nbsp;&nbsp;<img src='" + IMG_URL_PATH.DESTADDRESS_URL.value + "'>" +
											 				"</td>" +
															"<td colspan='4'>";
																if(json[i].task != '' || json[i].send.destaddrname != null || json[i].send. destaddrname != '') {
																	autolistdetail = autolistdetail + json[i].send.destaddrname;
																} else {
																	autolistdetail = autolistdetail + "------";
																}
						 autolistdetail = autolistdetail + "</td>" +
														"<td colspan='1'>" +
						 									"";
															if(json[i].send != '') {
																if(IS_FINISH.FINISHED.value == json[i].send.isfinish) {
																	autolistdetail = autolistdetail + "<img src='" + IMG_URL_PATH.TRUE_URL.value + "'>&nbsp;<lable class='actionfinishtime'>" + fomate_Hm(json[i].send.actionfinishtime) + "</lable>";
																} else {
																	if(ROLE_TYPE.ROLE_AIRPORT_TASK.value == json[i].send.roletype || ROLE_TYPE.ROLE_HOTEL_TASK.value == json[i].send.roletype) {
																		// 动作类型是 去酒店取、去机场取 则显示是否完成状态
																		autolistdetail = autolistdetail + IS_FINISH[json[i].send.isfinish].name;
																	} else {
																		// 在30分钟之外
																		var sendisovertime = isOvertime(json[i].send.arrivedtime);
																		if(sendisovertime == -2) {
																			autolistdetail = autolistdetail + IS_FINISH[json[i].send.isfinish].name;
																		} else if (sendisovertime == -1) {
																			autolistdetail = autolistdetail + "<img src='" + IMG_URL_PATH.BOMB_OVERTIME_24_URL.value + "'>&nbsp;<lable class='lableovertime'>超时!</lable>";
																		} else {
																			autolistdetail = autolistdetail + "<img id='imgsendcountdown" + json[i].send.id + "' src='" + IMG_URL_PATH.BOMB_COUNTDOWN_URL.value + "'>&nbsp;<lable class='classcountdown' id='sendcountdown" + json[i].send.id + "'></lable><input type='hidden' name='sendcountdown' alt='" + json[i].send.id + "' value='" + sendisovertime + "'>";
																		}
																	}
																}
															} else {
																autolistdetail = autolistdetail + "----";
															};
						autolistdetail = autolistdetail +"</td>" +
										 			"</tr>" +
										 			"<tr>" +
														"<td colspan='4'>" +
															"<div class='addrdesc'>";
															    if(json[i].send != '') {
																	autolistdetail = autolistdetail + json[i].send.destaddrdesc;
																} else {
																	autolistdetail = autolistdetail + "--------------";
																}
								autolistdetail = autolistdetail + "</div>" +
														"</td>" +
														"<td colspan='1'>";
															 if(json[i].send != '') {
																 if(ROLE_TYPE.ROLE_AIRPORT_TASK.value == json[i].send.roletype || ROLE_TYPE.ROLE_HOTEL_TASK.value == json[i].send.roletype) {
																	// 动作类型是 去酒店取、去机场取 则显示原始数据
								 autolistdetail = autolistdetail + "最早:" + isgreaterTodata(json[i].send.arrivedtime);
																 } else {
							     autolistdetail = autolistdetail + "最迟:" + isgreaterTodata(json[i].send.arrivedtime);
																 }
															 }
				       autolistdetail = autolistdetail +"</td>" +
										 			"</tr>" +
										 			"<tr style='height: 10px' />" +
													"<tr>" +
														"<td rowspan='1' colspan='5' class='tdimg'>" +
														"</td>" +
														"<td colspan='1'>" +
															"&nbsp;" +
															"<img title='单击：查看局部路径' onclick='partpathway(this)' src='" + IMG_URL_PATH.ROUTE_URL.value + "'>" +
															"<input type='hidden' name='partpathindex' value='"+ partindex +"'>" +
															"&nbsp;" +
															"<img title='单击：修改分派信息' onclick='loadautoallotdetailedit(this)' src='" + IMG_URL_PATH.EDIT_URL.value + "'>" +
															"&nbsp;" +
															"<img onclick='loadDeletedman(this)' src='" + IMG_URL_PATH.DELETE_URL.value + "'>" +
															"<input type='hidden' name='orderid' value=' " + data.id + " ' >" +
															"<input type='hidden' name='dmanuserid' value=' " + json[i].roleid + " ' >" +
														"</td>" +
													"</tr>" +
												"</table>";
				});
				
				$("#divAutoallotdetail").html(autolistdetail);
				loadcountdown();
			}
		});
}

var maptemp = [];
// 局部路径查看
function partpathway(param) {
	var cacheindex = $(param).parents('table').find("[name='partpathindex']").val();
	parent.removepartdetailpath(maptemp[cacheindex]);
	var taskgps_lng = $(param).parents('table').find("[name='taskgps_lng']").val();
	var taskgps_lat = $(param).parents('table').find("[name='taskgps_lat']").val();
	var sendgps_lng = $(param).parents('table').find("[name='sendgps_lng']").val();
	var sendgps_lat = $(param).parents('table').find("[name='sendgps_lat']").val();
	var tempindex = parent.orderpartdetailpath(taskgps_lng, taskgps_lat, sendgps_lng, sendgps_lat);
	maptemp[cacheindex] = tempindex;
}

// 取消订单路径信息
function cancelpartdetailpatch(param) {
	var tempindex = $(param).find("[name='partpathindex']").val();
	parent.removepartdetailpath(maptemp[tempindex]);
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

function loadautoallotdetailedit(param) {
	var orderid = $(param).parents('table').find("[name='orderid']").val();
	var dmanuserid = $(param).parents('table').find("[name='dmanuserid']").val();
	
	parent.loadautoallotdetailedit(orderid, dmanuserid);
}

// 删除自动分配取派员信息
function loadDeletedman(param) {
	// 关闭编辑窗口
	parent.closewaitmanualalloteditindex();
	
	 layer.msg('你确定要删除该取派员吗？', {
		  time: 0 //不自动关闭
		  ,btn: ['确认删除!','再想想...']
		  ,yes: function(index2){
			  deleteorderallotdman(param);
		  }
	 });
}

function deleteorderallotdman(param) {
	var dmanuserid = $(param).parents('table').find("[name='dmanuserid']").val().trim();
	var orderid = $(param).parents('table').find("[name='orderid']").val().trim();
	
	$.ajax({
		url : "./console_autoalloc/delectByOrderidAndRoleid",
		data : {
			roleid : dmanuserid,
			orderid : orderid
		},
		dataType : "text",
		type : "POST",
		traditional : true,
		success : function(data) {
			var returnStatus = data.split(":");
			
			if(returnStatus[0] == "SUCCESS") {
				layer.msg('删除成功', {icon: 6});
				fillAutoallotdetail();
			} else if(returnStatus[0] == "FAIL") {
				layer.msg('删除失败，请及时联系IT', {icon: 5});
			}
		}	
	});
}

// 分派取派员
function allotdman() {
	// 关闭编辑窗口
	parent.closewaitmanualalloteditindex();
	
	var orderid = $("#orderid").val().trim();
	parent.loadallotdman(orderid);
}

