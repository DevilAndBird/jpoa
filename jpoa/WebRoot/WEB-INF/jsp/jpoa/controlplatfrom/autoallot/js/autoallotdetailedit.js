// 自动分配局部路线修改加载
fillautoallotdetailbaseinfo();
function fillautoallotdetailbaseinfo() {
	var orderid = $("#orderid").val().trim();
	var dmanuserid = $("#dmanuserid").val().trim();
	
	$.ajax({
		url : "./consolecommon/finddmanpathbaseinfo",
		data : {
			orderid : orderid,
			dmanuserid : dmanuserid
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			$("#divfillautoallotdetailedit").html("");
			
			var orderid = data.ordermain.id;
			
			// 取派员取件信息
			var dmanOrderTask = data.dmanOrderTask;
			var taskid = "";
			var taskisfinishvalue = "";
			var taskIsfinish = "--";
			var taskarrivedtime = "--";
			var taskarrivedtime_Hm = "--";
			var taskDestaddrname = "--";
			var taskDestaddrdesc = "--";
			var taskroletype = '';
			if(dmanOrderTask != "") {
				taskid = dmanOrderTask.id;
				
				// 是否完成
				taskisfinishvalue = dmanOrderTask.isfinish;
				taskIsfinish = IS_FINISH[dmanOrderTask.isfinish].name;
				
				// 最迟到达时间
				taskarrivedtime = dmanOrderTask.arrivedtime;
				taskarrivedtime_Hm = isgreaterTodata(taskarrivedtime);
				// 目的地
			    taskDestaddrname = dmanOrderTask.destaddrname;
				taskDestaddrdesc = dmanOrderTask.destaddrdesc;
				if(!taskDestaddrname) {
					taskDestaddrname = "酒店/住宅";
				}
				
				taskroletype = dmanOrderTask.roletype;
			}
			
			// 取派员派件信息
			var dmanOrderSend = data.dmanOrderSend;
			var sendid = "";
			var sendisfinishvalue = "";
			var sendIsfinish = "--";
			var sendDestaddrname = "--";
			var sendDestaddrdesc = "--";
			var sendarrivedtime = "--";
			var sendarrivedtime_Hm = "--";
			var sendroletype = '';
			if(dmanOrderSend != "") {
				sendid = dmanOrderSend.id;
				
				// 已完成
				sendisfinishvalue = dmanOrderSend.isfinish;
				sendIsfinish = IS_FINISH[dmanOrderSend.isfinish].name;
				// 最迟到达时间
				sendarrivedtime = dmanOrderSend.arrivedtime;
				sendarrivedtime_Hm = isgreaterTodata(sendarrivedtime);
				// 目的地
				sendDestaddrname = dmanOrderSend.destaddrname;
				sendDestaddrdesc = dmanOrderSend.destaddrdesc;
				if(!sendDestaddrname) {
					sendDestaddrname = "酒店/住宅";
				}
				
				// 动作类型
				sendroletype = dmanOrderSend.roletype;
			}
			
			$("#taskid").val(taskid);
			$("#sendid").val(sendid);
			
			var dmanDetails = '';
			
			dmanDetails = dmanDetails + "<table  style='width:960px;'>" +
										   "<input type='hidden' name='orderid' value='"+ orderid +"'>" +
				                           "<tr>" +
								 				"<td rowspan='2' colspan='1' style='width: 50px'>&nbsp;&nbsp;&nbsp;&nbsp;<img alt='' src='" + IMG_URL_PATH.TASKLUG_URL.value + "'></td>" +
												"<td colspan='4'><label style='font-weight:bold;'>" + taskDestaddrname + "</label></td>" +
												"<td colspan='1'><label style='font-weight:bold;'>" + taskIsfinish + "</label></td>" +
												"<td rowspan='2' colspan='1' style='width: 50px'>&nbsp;&nbsp;&nbsp;&nbsp;<img alt='' src='" + IMG_URL_PATH.SENDLUG_URL.value + "'></td>" +
												"<td colspan='4'><label style='font-weight:bold;'>" + sendDestaddrname + "</label></td>" +
												"<td colspan='1'><label style='font-weight:bold;'>" + sendIsfinish + "</label></td>" +
								 			"</tr>" +
								 			"<tr>" +
												"<td colspan='4'><div name='taskDeskaddrdesc' style='width: 320px;font-weight:bold;'>" + taskDestaddrdesc ;
				if (IS_FINISH.UNFINISHED.value == taskisfinishvalue || IS_FINISH.ONGOING.value == taskisfinishvalue) {
					dmanDetails = dmanDetails + "&nbsp;<img onclick='modifyaddr(this)' src='" + IMG_URL_PATH.EDIT_URL.value + "'/>" +
												"<input type='hidden' value='"+ taskroletype +"'>" +
												"<input type='hidden' value='"+ taskDestaddrdesc +"'>" +
												"<input type='hidden' value='"+ taskid +"'>" + 
												"<input type='hidden' class='addrType' value='SEND'>";
				}
					dmanDetails = dmanDetails + "</div></td>" +
												"<td colspan='1'><div style='font-weight:bold;'>" + taskarrivedtime_Hm;
					dmanDetails = dmanDetails + "&nbsp;<img onclick='modifyaddivedtime(this)' src='" + IMG_URL_PATH.EDIT_URL.value + "'/>" +
					                             "<input type='hidden' value='"+ taskid +"'>" +
					                             "<input type='hidden' value='"+ taskroletype +"'>";
					dmanDetails = dmanDetails + "</td>" +
												"<td colspan='4'><div name='sendDeskaddrdesc' style='width: 320px;font-weight:bold;'>" + sendDestaddrdesc;
				if (IS_FINISH.UNFINISHED.value == sendisfinishvalue || IS_FINISH.ONGOING.value == sendisfinishvalue) {								
					dmanDetails = dmanDetails + "&nbsp;<img onclick='modifyaddr(this)' src='" + IMG_URL_PATH.EDIT_URL.value + "'/>"+
													"<input type='hidden' value='"+ sendroletype +"'>" +
													"<input type='hidden' value='"+ sendDestaddrdesc +"'>" +
													"<input type='hidden' value='"+ sendid +"'>" + 
													"<input type='hidden' class='addrType' value='TASK'>";
				}
					dmanDetails = dmanDetails +	"</div></td>" +
												"<td colspan='1'><div style='font-weight:bold;'>" + sendarrivedtime_Hm ;
					dmanDetails = dmanDetails +	"&nbsp;<img onclick='modifyaddivedtime(this)' src='" + IMG_URL_PATH.EDIT_URL.value + "'/>" +
												 "<input type='hidden' value='"+ sendid +"'>" +
												 "<input type='hidden' value='"+ sendroletype +"'>";
					dmanDetails = dmanDetails +  "</td>" +
											"</tr>" +  
										 "</table>";
					
			$("#divfillautoallotdetailedit").html(dmanDetails);
		}
	});	 
}

//修改取派员 针对一个订单修改取件地址相关地址
function modifyaddr(param) {
	var roletype = $(param).next().val();
	var orderroleid = $(param).next().next().next().val();
	var addrType =$(param).next().next().next().next().val();
	var orderid = $(param).parents('table').find("[name='orderid']").val();
	
	layer.open({
	  type: 2,
	  icon: 7,
	  title: ['地址修改', 'font-size:18px;'],
	  offset: ['10px', '300px'],
	  area: ['400px', '450px'],
	  fixed: false, //不固定
	  content:  "./consolecommon/modifydmanalloc?orderroleid="+orderroleid+'&roletype='+roletype + '&orderid=' + orderid + "&addrType=" + addrType
	});
}



function modifyaddivedtime(param) {
	var orderroleid = $(param).next().val();
	var roletype = $(param).next().next().val();
	var orderid = $(param).parents('table').find("[name='orderid']").val();
	
	layer.open({
	  type: 2,
	  icon: 8,
	  title: ['时间修改', 'font-size:18px;'],
	  offset: ['95px', '376px'],
	  area: ['248px;', '330px'],
	  fixed: false, //不固定
	  content:  "./consolecommon/modifyaddivedtime?orderroleid="+orderroleid + "&orderid=" + orderid + "&roletype=" + roletype,
	});
}

// 操纵autoallocmap.manageautoallotlist方法
function manageautoallotlist() {
	parent.manageautoallotlist();
}

//操纵autoallocmap.manageautoallotdetailiframeWin方法
function manageautoallotdetailiframeWin() {
	parent.manageautoallotdetailiframeWin();
}

// 刷新地图
function fillrolegpsData () {
	parent.gpsinit();
}

fillchangeallocdman();
function fillchangeallocdman() {
	var divdmanuserid =  $("#dmanuserid").val().trim();
	$("#changedmanuserid").val(divdmanuserid);
	
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
				if(divdmanuserid == json[i].userid) {
					color = '#D21736';
				}
				
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
													   
				$("#divdmanlist").html(divchangeallocdman);
            });
        }
	}); 	
}

function mouseover1 (param) {
	var userid = $(param).children(" [name='userid']").val().trim();
	var divdmanuserid =  $("#changedmanuserid").val();
	if(userid != divdmanuserid) {
		$(param).css("border","solid thick #efefef");
	} 
	else {
		$(param).css("border","solid thick #D21736");
	}
}

function mouseout1 (param) {
	var userid = $(param).children(" [name='userid']").val();
	var divdmanuserid =  $("#changedmanuserid").val();
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
	$("#changedmanuserid").val(userid);
	$(param).css("border","solid thick #D21736");
}

$("#ichangedman").click(function() {
	var currentuserid = $("#changedmanuserid").val().trim();
	var taskid = $("#taskid").val().trim();
	var sendid = $("#sendid").val().trim();
	layer.msg('你确定？', {
		  time: 0 //不自动关闭
		  ,btn: ['别墨迹...', '在想想...']
		  ,yes: function(index){
		    layer.close(index);
		    
		    $.ajax({
				url : "./consolecommon/ichangedman",
				data : {
					taskid : taskid,
					sendid : sendid,
					roleid : currentuserid
				},
				dataType : "text",
				type : "POST",
				success : function(data) {
					layer.alert('更新成功', {icon: 6}, function(index) {
						parent.manageautoallotdetailiframeWin();
						parent.closewaitmanualalloteditindex();
						fillautoallotdetailbaseinfo();
						fillrolegpsData();
						layer.close(index);
					});
		        }
			});
		  }
		});
		
});
