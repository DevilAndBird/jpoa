
fillwaitallotorderinfo();

function fillwaitallotorderinfo() {
	var orderid = $("#orderid").val().trim();
	$.ajax({
		url : "./waitmanualalloc/fillWaitmanualAlloc",
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
				waitlisttemp = waitlisttemp + "<table id='tablefillorderinfo'>" +
													"<tr style='height:10px;'>" +
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
										 			"</tr>" +
								 			
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
													"<div id='destaddress'>" +
							                            "<div class='addrdesc'>";
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
$("#transitCenter").hide();
$("#serviceCenter").hide();
$("#serviceCenter").hide();
$("#divaddr").hide();
loadRoleType();
orderTypeCharge();

/* 加载订单动作类型 */
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
		loadtransit();
	} else {
		$("#transitCenter").hide();
	}
	
	// 如果动作是服务中心
	if(roletypeCharge == ROLE_TYPE.ROLE_AIRPORT_SEND.value.toString()
			|| roletypeCharge == ROLE_TYPE.ROLE_AIRPORT_TASK.value.toString()) {
		$("#serviceCenter").show();
		loadcounter();
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

// 加载集散中心
function loadtransit() {
	$.ajax({
		url : "./transitCenter/findTransitAll",
		data : {
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			$("#transitCenter").empty();
			$("#transitCenter").append("<option value='-1' selected='selected'>--选择集散中心--</option>");
			
			var json = eval(data);
			$.each(json, function (i, item) {
				$("#transitCenter").append("<option title='" + json[i].address + "' value='" + json[i].id +"'>" + json[i].name + "</option>");
			});
		}
	}); 	
}

// 加载机场
function loadcounter() {
	$.ajax({
		url : "./counterServiceCenter/findCounterAll",
		data : {
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			$("#serviceCenter").empty();
			$("#serviceCenter").append("<option value='-1' selected='selected'>--选择柜台服务中心 --</option>");
			
			var json = eval(data);
			$.each(json, function (i, item) {
				$("#serviceCenter").append("<option title='" + json[i].address + "' value='"+ json[i].id +"'>" + json[i].servicecentername + "</option>");
			});
		}
	}); 	
}

//时间选择器
//loadtimeinput('#inputaddrivedtime', 'yyyy-MM-dd HH:mm', TIME_SCOPE.DMAM_ARRIVEDTIIME.starttime, TIME_SCOPE.DMAM_ARRIVEDTIIME.endtime);
$('#inputaddrivedtime').datetimepicker({
    format: 'yyyy-mm-dd hh:ii',
    minuteStep:15
});


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
    
	$.ajax({
		url : "./consolecommon/manualallotorder",
		data : {
			orderid : orderid,
			userid : dmanuserid,
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
			} else if(returnStatus[0] == "FAIL") {
				layer.msg(returnStatus[1], {icon: 5});
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
				     actionlist = actionlist + "</td>" +
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

$("#btnallotactivefinish").click(function() {
	layer.msg('订单分派完成进入自动分配列表？', {
		  time: 0, //不自动关闭
		  btn: ['是啊！！！', '回去加...'],
		  yes: function(index){
		    layer.close(index);
		    orderallotfinish();
		  }
		});
});

// 订单分配完成
function orderallotfinish() {
	var orderid = $("#orderid").val();
	
	$.ajax({
		url : "./waitmanualalloc/waitallotfinish",
		data : {
			orderid : orderid
		},
		dataType : "text",
		type : "POST",
		traditional : true,
		success : function(data) {
			var returnStatus = data.split(":");
			if(returnStatus[0] == "SUCCESS") {
				layer.msg('已经转入自动分配列表', {
				      icon: 6,
				      btn: ['知道了..'],
				      yes : function(index) {
				    	  layer.close(index);
				    	  parent.refreshwaitallotlist();
				    	  parent.countwaitmanualalloc();
				    	  parent.countautoalloc();
				    	  layer.close(parent.closewaitmanualallotedit());
				      }
				    });
			} else if(returnStatus[0] == "FAIL") {
				layer.msg('删除失败，请及时联系IT', {icon: 5});
			}
			
		}	
	});
}
