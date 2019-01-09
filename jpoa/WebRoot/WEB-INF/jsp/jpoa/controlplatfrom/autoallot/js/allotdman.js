
fillwaitallotorderinfo();

function fillwaitallotorderinfo() {
	var orderid = $("#orderid").val().trim();
	$.ajax({
		url : "./console_autoalloc/fillAutoAlloc",
		data : {
			orderid : orderid
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			$("#divfillorderinfolist").html("");
			var json = eval(data);
			var waitlisttemp = '';
			$.each(json, function (i, item) {
				var srcgpstemp = json[i].srcgps;
				var destgpstemp = json[i].destgps;
				var srcgps = eval('(' + srcgpstemp + ')');
				var destgps = eval('(' + destgpstemp + ')');
				
				waitlisttemp = waitlisttemp + "<table id='tablefillorderinfo'>" +
													"<input type='hidden' name='srcgps_lng' value=' " + srcgps.lng + " '>" +
													"<input type='hidden' name='srcgps_lat' value=' " + srcgps.lat + " '>" +
													"<input type='hidden' name='destgps_lng' value=' " + destgps.lng + " '>" +
													"<input type='hidden' name='destgps_lat' value=' " + destgps.lat + " '>" +
													/*"<tr style='height:10px;'>" +
													"<tr>" +
														"<td colspan='3'>" +
															"&nbsp;&nbsp;&nbsp;&nbsp;" + json[i].ordertypedesc + 
										 				"</td>" +
										 				"<td colspan='3' >" +
										  					"&nbsp;&nbsp;&nbsp;&nbsp;#" + json[i].ordernosuffix + 
										 				"</td>" +
										 				"<td colspan='3'>" +
										  					"&nbsp;&nbsp;&nbsp;&nbsp;" + json[i].mailingwayDesc + " - " + json[i].backwayDesc + 
										 				"</td>" +
										 				"<td colspan='3'>" +
										 					"&nbsp;&nbsp;&nbsp;&nbsp;行李数量：" + json[i].num +
										 				"</td>" +
										 			"</tr>" +*/
								 			
										 			"<tr style='height:10px;'>" +
			
										 			"<tr>" +
											 			"<td rowspan='2' colspan='1' class='tdimg'>" +
											 				"&nbsp;&nbsp;&nbsp;<img src='" + IMG_URL_PATH.TASKLUG_URL.value + "'>" +
											 			"</td>" +
											 			"<td colspan='4' id='scrlandmark'>";
											 				if(json[i].scrlandmark != null) {
											 					waitlisttemp = waitlisttemp + json[i].scrlandmark;
															} else {
																waitlisttemp = waitlisttemp + "------";
															};
						  waitlisttemp = waitlisttemp + "</td>" +
														"<td colspan='1'></td>" +
														"<td rowspan='2' colspan='1' class='tdimg'>" +
															"&nbsp;&nbsp;&nbsp;<img src=' " + IMG_URL_PATH.SENDLUG_URL.value + "'>" +
														"</td>" +
														"<td colspan='4' id='destlandmark'>";
															if(json[i].destlandmark != null) {
											 					waitlisttemp = waitlisttemp + json[i].destlandmark;
															} else {
																waitlisttemp = waitlisttemp + "------";
															}; 
						  waitlisttemp = waitlisttemp + "</td>" +
												         "<td colspan='1'></td>" +
												    "</tr>" +
												    "<tr>" +
												    	 "<td colspan='4' id='srcaddress'>" +
												    	 	"<div class='addrdesc'>";
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
							 waitlisttemp = waitlisttemp + "</div>" +
							 		"					 </td>" +
												"<td colspan='1'>" +
													"&nbsp;&nbsp;&nbsp;&nbsp;" + json[i].taketimetime +
												"</td>" +
												"<td colspan='4'>" +
													"<div class='addrdesc' id='destaddress'>";
														if(json[i].destprovname != null) {
				 											waitlisttemp = waitlisttemp + json[i].destprovname;
				 										}
														if(json[i].destcityname != null) {
															waitlisttemp = waitlisttemp + json[i].destcityname;
														}
														if(json[i].srcdistrictname != null) {
															waitlisttemp = waitlisttemp + json[i].destdistrictname;
														}
														if(json[i].destaddress != null) {
															waitlisttemp = waitlisttemp + json[i].destaddress;
														}
				 	 waitlisttemp = waitlisttemp + "</div>" +
												"</td>" +
												"<td colspan='1'>" +
													"&nbsp;&nbsp;&nbsp;&nbsp;" + json[i].sendtimetime +
												"</td>" +
											"</tr>" +  
										 "</table>";
								 	  
			});
			$("#divfillorderinfolist").html(waitlisttemp);
		}
	});	 
}

// 取派员
filldmanlist();

function filldmanlist() {
	$.ajax({
		url : "./consolecommon/findDmanList",
		data : {
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			$("#divdmanlist").html("");
			var json = eval(data);
			var divchangeallocdman = '';
			$.each(json, function (i, item) {
				var color = "#efefef";
				divchangeallocdman = divchangeallocdman + "<div style='float: left;'><table id='dmanuserid+"+ json[i].userid +"' onmouseenter='mouseover1(this)' onmouseleave='mouseout1(this)' onclick='changedman(this)' style='margin-top:20px;border:solid medium " + color + "; margin-left:70px; width:370px;height:70px;'>" +
													     			"<input type='hidden' name='userid' value='" + json[i].userid + "'/>" +
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
													     	   "</table></div>" ;
													   
            });
			
			$("#divdmanlist").html(divchangeallocdman);
        }
	}); 	
}

function mouseover1 (param) {
	var userid = $(param).children(" [name='userid']").val().trim();
	var divdmanuserid =  $("#dmanuserid").val();
	if(userid != divdmanuserid) {
		$(param).css("border","solid thick #efefef");
	} 
	else {
		$(param).css("border","solid thick #D21736");
	}
}

function mouseout1 (param) {
	var userid = $(param).children(" [name='userid']").val();
	var divdmanuserid =  $("#dmanuserid").val();
	if(userid != divdmanuserid) {
		$(param).css("border","solid medium #efefef");
	}
	else {
		$(param).css("border","solid medium #D21736");
	}
}

function changedman(param) {
	$("[id*='dmanuserid']").css("border","solid medium #efefef");
	var userid = $(param).children(" [name='userid']").val();
	$("#dmanuserid").val(userid);
	$(param).css("border","solid thick #D21736");
}

// ==========================动作指派====================================
/*$("#transitCenter").hide();
$("#serviceCenter").hide();
$("#serviceCenter").hide();
$("#divaddr").hide();
loadRoleType();
orderTypeCharge();

 加载订单动作类型 
function loadRoleType() {
	$(" [name='seletRoleType']").empty();
	$(" [name='seletRoleType']").append("<option value='-1' selected='selected'>--订单动作类型--</option>");
	for(i in ROLE_TYPE) {
		$(" [name='seletRoleType']").append("<option value='"+ROLE_TYPE[i].value+"' >"+ROLE_TYPE[i].name+"</option>");
	}
}

function orderTypeCharge() {
	// 如果动作是集散中心的话则显示选择到集散中心
	var roletypeCharge = $(" [name='seletRoleType']").attr("selected",true).val();
	if(roletypeCharge == ROLE_TYPE.ROLE_TRANSIT_SEND.value.toString()
			|| roletypeCharge == ROLE_TYPE.ROLE_TRANSIT_TASK.value.toString()) {
		$("#transitCenter").show();
		loadtransit("#transitCenter");
	} else {
		$("#transitCenter").hide();
	}
	
	// 如果动作是服务中心
	if(roletypeCharge == ROLE_TYPE.ROLE_AIRPORT_SEND.value.toString()
			|| roletypeCharge == ROLE_TYPE.ROLE_AIRPORT_TASK.value.toString()) {
		$("#serviceCenter").show();
		loadcounter("#serviceCenter");
	} else {
		$("#serviceCenter").hide();
	}
	
	// 如果酒店
	if(roletypeCharge == ROLE_TYPE.ROLE_HOTEL_SEND.value.toString()
			|| roletypeCharge == ROLE_TYPE.ROLE_HOTEL_TASK.value.toString()) {
		$("#divaddr").show();
	} else {
		$("#divaddr").hide();
	}
}

//时间选择器
$('#inputaddrivedtime').datetimepicker({
    format: 'yyyy-mm-dd hh:ii',
    minuteStep:15
});*/



// 保存按钮
$("#btnsaveactive").click(function() {
	$("#btnsaveactive").attr("disabled",true); 
	// 订单ID
	var orderid = $("#orderid").val().trim();
	
	if(!orderid) {
    	layer.msg('订单id未获取,尽快联系IT！！!', function(){});
	    return;
    }
	
	// 取派员userid
	var dmanuserid = $("#dmanuserid").val().trim();
	
    if(!dmanuserid) {
    	layer.msg('选择取派员！！', function(){});
	    return;
    }
    
    // 初始化
    /*var desttype = DESTINSATION_TYPE.HOTEL.value.toString();// TODO 酒店_住宅 目前缺少个住宅
	var destaddress = '';
	var destaddrname = '';
	var destaddrdesc = '';
	var destgps = '';
    
    // 动作类型
	var roletype =  $("#seletRoleType").attr("selected",true).val();
	
	if(roletype == -1) {
		layer.msg('选择动作类型', function(){});
	    return;
	}
	
	if(roletype == ROLE_TYPE.ROLE_HOTEL_SEND.value.toString()) {
		destaddrname =  $("#destlandmark").val();
		destaddrdesc = $("#destaddress").text();
		destgps = "{'lng':'"+ $("[name='destgps_lng']").val() +"','lat':'"+ $("[name='destgps_lat']").val() +"'}";
	}
	if(roletype == ROLE_TYPE.ROLE_HOTEL_TASK.value.toString()) {
		destaddrname =   $("#scrlandmark").val();
		destaddrdesc = $("#srcaddress").text();
		destgps = "{'lng':'"+ $("[name='srcgps_lng']").val() +"','lat':'"+ $("[name='srcgps_lat']").val() +"'}";
	}
	
	// 校验 服务中心
	if(roletype == ROLE_TYPE.ROLE_AIRPORT_SEND.value.toString()
			|| roletype == ROLE_TYPE.ROLE_AIRPORT_TASK.value.toString()) {
		   desttype = DESTINSATION_TYPE.SERVICECERTER.value.toString();
		   destaddress = $("#serviceCenter").attr("selected",true).val();
		   destaddrname = $("#serviceCenter").find("option:selected").text();
		   var temp1 = $("#serviceCenter option:selected").attr("title");
		   var temp2 = temp1.split(":");
		   var temp3 = temp2[1].split("@");
		   destaddrdesc = temp2[0];
		   destgps = "{'lng':'"+ temp3[0] +"','lat':'"+ temp3[1] +"'}";
		   
		   if(!destaddress || destaddress == -1) {
			   layer.msg('请选择服务中心', function(){});
			   return;
	       }
    }
	
	// 校验集散中心
	if(roletype == ROLE_TYPE.ROLE_TRANSIT_TASK.value.toString()
			|| roletype == ROLE_TYPE.ROLE_TRANSIT_SEND.value.toString()) {
		   desttype = DESTINSATION_TYPE.TRANSITCERTER.value.toString();
		   destaddress = $("#transitCenter").attr("selected",true).val();
		   destaddrname = $("#transitCenter").find("option:selected").text();
		   var temp1 = $("#transitCenter option:selected").attr("title");
		   var temp2 = temp1.split(":");
		   var temp3 = temp2[1].split("@");
		   destaddrdesc = temp2[0];
		   destgps = "{'lng':'"+ temp3[0] +"','lat':'"+ temp3[1] +"'}";
		   
		   if(!destaddress || destaddress == -1) {
			   layer.msg('请选择集散中心', function(){});
			   return;
	       }
    }*/

	/*var arrivedtime = $("#inputaddrivedtime").val();
	
	if(!arrivedtime){
		layer.msg('请输入最迟到达时间!!!', function(){});
		return;
	}*/
	
	$.ajax({
		url : "./consolecommon/manualallotorder",
		data : {
			orderid : orderid,
			userid : dmanuserid,
			/*roletype : roletype,
			desttype : desttype,
			destaddress : destaddress,
			destaddrname : destaddrname,
			destaddrdesc : destaddrdesc,
			arrivedtime : arrivedtime,
			destgps : destgps*/
		},
		dataType : "text",
		type : "POST",
		traditional : true,
		success : function(data) {
			var returnStatus = data.split(":");
			
			if(returnStatus[0] == "SUCCESS") {
				layer.msg('添加成功', {icon: 6});
				$("#btnsaveactive").attr("disabled",false); 
				fillorderactiveallot();
				parent.manageautoallotdetailiframeWin();
			} else if(returnStatus[0] == "FAIL") {
				layer.msg('添加失败，请及时联系IT', {icon: 5});
				$("#btnsaveactive").attr("disabled",false); 
			}
		},
		error : function(data) {
			layer.msg(data, {
				icon : 5
			});
			$("#btnsaveactive").attr("disabled",false); 
		}
	});
});

// 加载分派信息
fillorderactiveallot();
function fillorderactiveallot() {
	var orderid = $("#orderid").val().trim();
	
	$.ajax({
		url : "./consolecommon/viewOrderRole",
		data : {
			id : orderid
		},
		dataType : "text",
		type : "POST",
		traditional : true,
		success : function(data) {
			$("#tbodyactionlistfill").html("");
			var actionlist = '';
			var json = eval(data);
			$.each(json, function (i, item) {
				actionlist = actionlist + "<tr>" +
											"<td class='center'>" + json[i].rolename + "</td>" +
											"<td class='center'>" + json[i].rolemobile + "</td>" +
											"<td class='center'>" + json[i].usertypeDesc + "</td>" +
											"<td class='center'>" + json[i].roletypename + "</td>" +
											"<td class='center'>" + json[i].isfinishDesc + "</td>" +
											"<td class='center'>";
												if(json[i].destaddrname != null && json[i].destaddrname != '') {
													actionlist = actionlist + json[i].destaddrname + "/" ;
												}
												if(json[i].destaddrdesc != null && json[i].destaddrdesc != '') {
													actionlist = actionlist + json[i].destaddrdesc;
												}
                  actionlist = actionlist +"</td>" +
											"<td class='center'>";
												if(json[i].arrivedtime != null && json[i].arrivedtime != '') {
													actionlist = actionlist + json[i].arrivedtime;
												}
				 actionlist = actionlist +  "</td>" +
											"<td class='center'>";
											if(json[i].isfinish != 'FINISHED') {
					actionlist = actionlist + "<button onclick='delectaction(this)' class='btn btn-mini btn-info' value=''>删</button>" +
											  "<input type='hidden' value='" + json[i].id + "'>";
											}
				  actionlist = actionlist +"</td>" +
										"</tr>";
			});
			$("#tbodyactionlistfill").html(actionlist);
		}	
	});
}

function delectaction(param) {
	var orderroleid = $(param).next().val();
	
	$.ajax({
		url : "./consolecommon/delectOrderRoleByid",
		data : {
			id : orderroleid
		},
		dataType : "text",
		type : "POST",
		traditional : true,
		success : function(data) {
			var returnStatus = data.split(":");
			
			if(returnStatus[0] == "SUCCESS") {
				layer.msg('删除成功', {icon: 6});
				fillorderactiveallot();
			} else if(returnStatus[0] == "FAIL") {
				layer.msg('删除失败，请及时联系IT', {icon: 5});
			}
			
		}	
	});
}
