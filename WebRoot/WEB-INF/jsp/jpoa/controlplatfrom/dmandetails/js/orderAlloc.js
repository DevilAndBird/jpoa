// 全局
var layer;
layui.use('layer', function(){
	  layer = layui.layer;
});              

// 分派取派员页面
function orderallocpage (param) {
	var orderid = $(param).next(" [name='orderid']").val();
	$("#inp_Alloc_orderid").val(orderid);
	
	//模态框初始化加载
	$("#orderAlloc").modal(); 
	// 模态框可移动
	$("#orderAlloc").draggable();
	orderallocfillpage();
	fillchangeallocdman();
}

// 订单分配页面填充
function orderallocfillpage() {
	// 获取取派员userid
	var orderid = $("#inp_Alloc_orderid").val();
	var dmanuserid = $("#dmanuserid").val().trim();
	
	
	$.ajax({
		url : "./controlplatform_dmanDatails/orderallocfillpage",
		data : {
			orderid : orderid,
			dmanuserid : dmanuserid
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			$("#divdmanAllocDetails").html("");
			// 订单信息
			var ordermain = data.ordermain;
			
			var dmanOrderTask = data.dmanOrderTask;
			
			var taskid = "";
			var srcname = "无";
			var  src = "";
			var taskisfinishvalue = "";
			var taskIsfinish = "--";
			var taskarrivedtime = "--";
			var taskarrivedtime_Hm = "--";
			var taskDestaddrname = "--";
			var taskDestaddrdesc = "--";
			var taskroletype = '';
			if(dmanOrderTask != "") {
				taskid = dmanOrderTask.id;
				
				src = dmanOrderTask.desttype;
				srcname = DESTINSATION_TYPE[src].name;
				
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
			
			var sendid = "";
			var destname = "无";
			var dest = "";
			var destaddress = "";
			var sendisfinishvalue = "";
			var sendIsfinish = "--";
			var sendDestaddrname = "--";
			var sendDestaddrdesc = "--";
			var sendarrivedtime = "--";
			var sendarrivedtime_Hm = "--";
			var dmanOrderSend = data.dmanOrderSend;
			var sendroletype = '';
			if(dmanOrderSend != "") {
				sendid = dmanOrderSend.id;
				
				dest = dmanOrderSend.desttype;
				destname = DESTINSATION_TYPE[dest].name;
				destaddress = dmanOrderSend.destaddress;
				
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
			
			// 订单id
			var orderid = ordermain.id;
			
			// 订单号后四位截取
			var orderno = ordermain.orderno;
			orderno = orderno.substring((orderno.length-4),orderno.length); 
			
			// 取件类型
			var mailingway = ordermain.mailingway;
			var mailingwayname = '';
			if(mailingway != null && mailingway != '') {
				mailingwayname = MAILING_WAY[mailingway].name;
			}
			var mailingwaytype = mailingwayname;
			if(DESTINSATION_TYPE.TRANSITCERTER.value == src
					|| src == "") {
				mailingwaytype = "无";
			}
			
			// 收件类型
			var backway = ordermain.backway;
			var backwayname = '';
			if(backway != null && backway != '') {
				backwayname =  MAILING_WAY[backway].name;
			}
			var backwaytype = backwayname;
			if(DESTINSATION_TYPE.TRANSITCERTER.value == dest
					|| dest == "") {
				backwaytype = "无";
			}
			
			// 订单行李数
			var lug = ordermain.num;
			
			$("#taskid").val(taskid);
			$("#sendid").val(sendid);
			
			var dmanDetails = $("#divdmanAllocDetails").html();
			
			dmanDetails = dmanDetails + "<table>" +
											"<input type='hidden' name='orderid' value='"+ orderid +"'>" +
											"<tr>" +
												"<td colspan='2' >" +
													"<label style='font-weight:bold;'>&nbsp;&nbsp;&nbsp;&nbsp;" + srcname +" - "+ destname + "</label>" +
								 				"</td>" +
								 				"<td colspan='4' >" +
								  					"<label style='font-weight:bold;'>&nbsp;&nbsp;&nbsp;&nbsp;# " + orderno + "</label>" +
								 				"</td>" +
								 				"<td colspan='4'>" +
								  					"<label style='font-weight:bold;'>&nbsp;&nbsp;&nbsp;&nbsp;" + mailingwaytype + " - " + backwaytype + "</label>" +
								 				"</td>" +
								 				"<td colspan='2'><label style='font-weight:bold;'>&nbsp;&nbsp;&nbsp;&nbsp;行李数量：" + lug + "</label></td>" +
								 			"</tr>" +
								 			
								 			"<tr style='height:10px;'>" +
			
				                           "<tr>" +
								 				"<td rowspan='2' colspan='1' style='width: 50px'>&nbsp;&nbsp;&nbsp;&nbsp;<img alt='' src='/jpoa/static/img/tasklug.png'></td>" +
												"<td colspan='4'><label style='font-weight:bold;'>" + taskDestaddrname + "</label></td>" +
												"<td colspan='1'><label style='font-weight:bold;'>" + taskIsfinish + "</label></td>" +
												"<td rowspan='2' colspan='1' style='width: 50px'>&nbsp;&nbsp;&nbsp;&nbsp;<img alt='' src='/jpoa/static/img/sendlug.png'></td>" +
												"<td colspan='4'><label style='font-weight:bold;'>" + sendDestaddrname + "</label></td>" +
												"<td colspan='1'><label style='font-weight:bold;'>" + sendIsfinish + "</label></td>" +
								 			"</tr>" +
								 			"<tr>" +
												"<td colspan='4'><div name='taskDeskaddrdesc' style='width: 320px;font-weight:bold;'>" + taskDestaddrdesc ;
				if (IS_FINISH.UNFINISHED.value == taskisfinishvalue || IS_FINISH.ONGOING.value == taskisfinishvalue) {
					dmanDetails = dmanDetails + "&nbsp;<img onclick='modifyaddr(this)' src='/jpoa/static/img/edit.png'/>" +
												"<input type='hidden' value='"+ taskroletype +"'>" +
												"<input type='hidden' value='"+ taskDestaddrdesc +"'>" +
												"<input type='hidden' value='"+ taskid +"'>" +
												"<input type='hidden' class='addrType' value='SEND'>";
				}
					dmanDetails = dmanDetails + "</div></td>" +
												"<td colspan='1'><div style='font-weight:bold;'>" + taskarrivedtime_Hm ;
					dmanDetails = dmanDetails + "&nbsp;<img onclick='modifyaddivedtime(this)' src='/jpoa/static/img/edit.png'/>" +
					                             "<input type='hidden' value='"+ taskid +"'>" +
					                             "<input type='hidden' value='"+ taskroletype +"'>";
					dmanDetails = dmanDetails + "</td>" +
												"<td colspan='4'><div name='sendDeskaddrdesc' style='width: 320px;font-weight:bold;'>" + sendDestaddrdesc;
				if (IS_FINISH.UNFINISHED.value == sendisfinishvalue || IS_FINISH.ONGOING.value == sendisfinishvalue) {								
					dmanDetails = dmanDetails + "&nbsp;<img onclick='modifyaddr(this)' src='/jpoa/static/img/edit.png'/>"+
													"<input type='hidden' value='"+ sendroletype +"'>" +
													"<input type='hidden' value='"+ sendDestaddrdesc +"'>" +
													"<input type='hidden' value='"+ sendid +"'>" +
													"<input type='hidden' class='addrType' value='TASK'>";
				}
					dmanDetails = dmanDetails +	"</div></td>" +
												"<td colspan='1'><div style='font-weight:bold;'>" + sendarrivedtime_Hm ;
					dmanDetails = dmanDetails +	"&nbsp;<img onclick='modifyaddivedtime(this)' src='/jpoa/static/img/edit.png'/>" +
												 "<input type='hidden' value='"+ sendid +"'>" +
												 "<input type='hidden' value='"+ sendroletype +"'>";
					dmanDetails = dmanDetails +  "</td>" +
											"</tr>" +  
										 "</table>";
								 	  
			
			$("#divdmanAllocDetails").html(dmanDetails);
		}
	});	 
}

function fillchangeallocdman() {
	var divdmanuserid =  $("#dmanuserid").val().trim();
	$("#changedmanuserid").val(divdmanuserid);
	
	$.ajax({
		url : "./controlplatform_dmanDatails/findDmanList",
		data : {
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			$("#divchangeallocdman").html("");
			var json = eval(data);
			$.each(json, function (i, item) {
				var divchangeallocdman = $("#divchangeallocdman").html();
				
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
													   
				$("#divchangeallocdman").html(divchangeallocdman);
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
//	var beforeuserid = $("#changedmanuserid").val();
	$("[id*='dmanuserid']").css("border","solid medium #efefef");
	
	var userid = $(param).children(" [name='userid']").val();
	$("#changedmanuserid").val(userid);
	$(param).css("border","solid thick #D21736");
}

//修改取派员 针对一个订单修改取件地址相关地址
function modifyaddr(param) {
	var roletype = $(param).next().val();
	var orderroleid = $(param).next().next().next().val();
	var addrType = $(param).next().next().next().next().val();
	var orderid = $(param).parents('table').find('[name="orderid"]').val();
	
	layer.open({
	  type: 2,
	  icon: 7,
	  title: ['地址修改', 'font-size:18px;'],
	  offset: ['10px', '1200px'],
	  area: ['400px', '450px'],
	  fixed: false, //不固定
	  content:  "./controlplatform_dmanDatails/modifydmanalloc?orderroleid="+orderroleid+'&roletype='+roletype + "&orderid=" + orderid + "&addrType=" + addrType
	});
}

function modifyaddivedtime(param) {
	var orderroleid = $(param).next().val();
	var roletype = $(param).next().next().val();
	var orderid = $(param).parents('table').find('[name="orderid"]').val();
	
	layer.open({
	  type: 2,
	  icon: 8,
	  title: ['时间修改', 'font-size:18px;'],
	  offset: ['300px', '1200px'],
	  area: ['248px;', '330px'],
	  fixed: false, //不固定
	  content:  "./controlplatform_dmanDatails/modifyaddivedtime?orderroleid="+orderroleid + "&roletype=" + roletype + "&orderid=" + orderid,
	});
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
				url : "./controlplatform_dmanDatails/ichangedman",
				data : {
					taskid : taskid,
					sendid : sendid,
					roleid : currentuserid
				},
				dataType : "text",
				type : "POST",
				success : function(data) {
					layer.alert('更新成功', {icon: 6}, function(index) {
						layer.close(index);
						findDmanDetails();
						$("#orderAlloc").modal('hide');
						dmancurrentGps();
					});
		        }
			});
		    
		  }
		});
		
});
