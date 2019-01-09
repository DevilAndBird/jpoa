
var  ordermain_list  = {
	init : function() {
		loadStatusType();
		$("#myModal").hide();
	}

};

baseConfigSelectDefault("./config/getSelectValue","paystatus","ORDERPAYSTUTAS","","支付状态");

$('#starttime').datetimepicker({
    format: 'yyyy-mm-dd hh:ii',
    minuteStep:15
});
$('#endtime').datetimepicker({
    format: 'yyyy-mm-dd hh:ii',
    minuteStep:15
});

/**
 * 加载状态下拉框
 */
function loadStatusType() {
	$("#seleType_modal").empty();
	var  status =  $("#status").val();
	if(status ==null ||status =='' ){
		$("#seleType_modal").append("<option value='-1' selected='selected'>------状态类型------</option>");
	}else{
		$("#seleType_modal").append("<option value='-1' selected='selected'>"+STATUS_TYPE[status].name+"</option>");		
	}
	for(i in STATUS_TYPE) {
		if(status == STATUS_TYPE[i].value) {
			continue;
		}
		$("#seleType_modal").append("<option value='"+STATUS_TYPE[i].value+"'>"+STATUS_TYPE[i].name+"</option>");
	}
}

$("#seleType_modal").blur(function(){
	var status=$("#seleType_modal").attr("selected",true).val();
	$("#status").val(status);
});
/* 表单提交 */
function search() {
	$(" [name='orderno']").val($(" [name='orderno']").val().trim());
	$(" [name='porderno']").val($(" [name='porderno']").val().trim());
	$(" [name='name']").val($(" [name='name']").val().trim());
	$(" [name='mobile']").val($(" [name='mobile']").val().trim());
	$("#Form").submit();
}

function toExcel() {
	var url = "./orderinfo/outExcel.do";
	$("#Form").attr("action", url);
	$("#Form").submit();
}

function selectDetail(parma) {
	layer.open({
		  type: 2,
		  title: ['订单详情', 'font-size:18px;'],// 不需要标题
		  closeBtn: 1,// 没有关闭按钮
		  id : 'console_orderdetails_temp',//不管是什么类型的层，都只允许同时弹出一个
		  area: ['100%', '100%'],
		  fixed: false, //不固定
		  content: "./orderinfo/listOrderDetail.do?id=" + $(parma).next().val()
		});
}

function showImg(parma) {
	var imgurl = $(parma).val();
	var img = "<img src='"+imgurl+"' />";  
//	layer.open({
//		  type: 1,
//		  title: false,
//		  shade: 0.8,
//		  area:['auto','auto'],
//		  area: [img.width + "px", img.height + "px"],
//		  closeBtn: 0,
//		  shadeClose: true,
//		  content: img
//		});
	layer.open({  
	    type: 1,  
	    shade: 0.8,  
	    title: false, //不显示标题  
	    area:['auto','auto'],  
	    area: [img.width + 'px', img.height+'px'],
	    content: img //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响  
	});  
}

function showAddPage(id) {
	$("#id").val(id);
	$("#orderType").val($("#orderType" + id).val());
	
	$("#scrlandmark").val($("#scrlandmark"+ id).val());
	$("#destlandmark").val($("#destlandmark"+ id).val());

	$("#srcaddress").val($("#srcaddress"+ id).val());
	$("#destaddress").val($("#destaddress"+ id).val());
		
	var url = "./orderinfo/listOrderUserDelivery.do";
	$("#Form").attr("action", url);
	$("#Form").submit();
}

function showAddPickerPage(id) {
	$("#id").val(id);
	var url = "./orderinfo/listOrderUserPicker.do";
	$("#Form").attr("action", url);
	$("#Form").submit();
}

/*$("#saveOrderMain").click(function() {
	var url = "./orderinfo/saveOrderMainPage.do";
	$("#Form").attr("action", url);
	$("#Form").submit();
});*/

$(" [name='cancelOrder']").click(function() {
	var orderid = $(this).val();
	$("#btnCancelDMan").val(orderid);
	$("#myModal").show();
	var currentStatus = $(this).next().val();
	$("#btnCancelDMan").next().val(currentStatus);
	
	$("#h4CancelOrder").show();
	$("#divCancelOrder").show();
	$("#btnCancelDMan").show();
	
	$("#h4UpdateStatus").hide();
	$("#divUpdateStatus").hide();
	$("#btnUpdateStatus").hide();
});

// 取消订单
$("#btnCancelDMan").click(function() {
	var orderid = $("#btnCancelDMan").val();
	var refundmoney = $("#refundmoney").val();
	var refundreason = $("#refundreason").val();
	var currentStatus = $("#btnCancelDMan").next().val();
	if(refundreason=="" || refundreason==null){
		toastr.warning('请输入退款原因!');
		return ;
	}
	if(refundmoney=="" || refundmoney==null){
		toastr.warning('请输入实际退款金额!');
		return ;
	}
	if(isNaN(refundmoney)){
		toastr.warning('请输入正确格式!');
		return ;
	}
	$.ajax({
		url : "./orderinfo/cancelOrder",
		data : {
			id : orderid,
			refundmoney:refundmoney,
			refundreason:refundreason
		},
		dataType : "text",
		type : "POST",
		traditional : true,
		success : function(data) {
			layer.alert(data, {
				skin : 'layui-layer-molv' // 样式类名 自定义样式
				,
				closeBtn : 1 // 是否显示关闭按钮
				,
				anim : 1 // 动画类型
				,
				btn : [ '确认' ] // 按钮
				,
				icon : 6 // icon
				,
				yes : function() {
						var url = "./orderinfo/listOrderInfo.do";
						$("#Form").attr("action", url);
						$("#Form").submit();
				}
			});
		},
		error : function(data) {
			layer.msg(data, {
				icon : 5
			});
		}
	});
	
	
});

$(" [name='updateStatus']").click(function() {
	var orderid = $(this).val();
	$("#btnUpdateStatus").val(orderid);
	var status = $(this).next().val();
	
	$("#h4CancelOrder").hide();
	$("#divCancelOrder").hide();
	$("#btnCancelDMan").hide();
	
	$("#h4UpdateStatus").show();
	$("#divUpdateStatus").show();
	$("#btnUpdateStatus").show();
	
	// 获取订单状态
	$("#seleStatus_modal").empty();
	$("#seleStatus_modal").append("<option value='-1' selected='selected'>--选择订单状态--</option>");

	for(i in STATUS_TYPE) {
		if(status == STATUS_TYPE[i].value) {
			continue;
		}
		$("#seleStatus_modal").append("<option value='"+STATUS_TYPE[i].value+"'>"+STATUS_TYPE[i].name+"</option>");
	}
});

// 更新订单状态
$("#btnUpdateStatus").click(function() {
	var orderid = $("#btnUpdateStatus").val();
	
	// 获取修改后的取派员信息
	var currentStatus = $("#seleStatus_modal").attr("selected",true).val();
	
	// 取值校验
	if(currentStatus ==-1 || !currentStatus || orderid == -1 || !orderid) {
		toastr.warning('请输入数据');
		return ;
	}
	
	$.ajax({
		url : "./orderinfo/updateStatus",
		data : {
			id : orderid,
			status : currentStatus
		},
		dataType : "text",
		type : "POST",
		traditional : true,
		success : function(data) {
			layer.alert(data, {
				skin : 'layui-layer-molv' // 样式类名 自定义样式
				,
				closeBtn : 1 // 是否显示关闭按钮
				,
				anim : 1 // 动画类型
				,
				btn : [ '确认' ] // 按钮
				,
				icon : 6 // icon
				,
				yes : function() {
					var returnStatus = data.split(":");
					if(returnStatus[0] == 'SUCCESS') {
						var url = "./orderinfo/listOrderInfo.do";
						$("#Form").attr("action", url);
						$("#Form").submit();
					}
				}
			});
		},
		error : function(data) {
			layer.msg(data, {
				icon : 5
			});
		}
	});
});
