function orderdetails(parma) {
	layer.open({
		  type: 2,
		  title: ['订单详情', 'font-size:18px;'],// 不需要标题
		  closeBtn: 1,// 没有关闭按钮
		  id : 'orderdetails_problem',//不管是什么类型的层，都只允许同时弹出一个
		  area: ['100%', '100%'],
		  fixed: false, //不固定
		  content: "./orderinfo/listOrderDetail.do?id=" + $(parma).next().val()
		});
}

function processlist(parma) {
	var orderid = $(parma).next().val();
	layer.open({
		  type: 2,
		  title: ['反馈详情列表', 'font-size:18px;'],// 不需要标题
		  closeBtn: 1,// 没有关闭按钮
		  id : 'problem_details',//不管是什么类型的层，都只允许同时弹出一个
		  area: ['100%', '100%'],
		  fixed: false, //不固定
		  content: "./problemorder/problemDetails.do?orderid=" + orderid,
		  cancel: function(index, layero){ 
			  refresh_unsolved(orderid);
			  layer.close(index);
		  } 
		});
}

// 订单列表
function refresh_unsolved(orderid) {
	
	$.ajax({
		url : "./problemorder/refresh_unsolved",
		data : {
			orderid : orderid,
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			if(data.success) {
				$('.pro' + orderid).children('.unsolved').html(data.unsolved_num);
			} else {
				alert(data.errMeg);
			}	
		}
	});
	
}
