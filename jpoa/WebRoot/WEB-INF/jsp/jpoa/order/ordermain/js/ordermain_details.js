var layer;
layui.use('layer', function(){
  layer = layui.layer;
});

function showImg_coolect(param) {
	var coolect_list_temp = $(param).parents('td').children("[name='coolect']");// 
	var json = "{" +
	  "'title':'收取行李'," + //相册标题
	  "'id': 111," +//相册id
	  "'start': 0," + //初始显示的图片序号，默认0
	  "'data': [" ;   //相册包含的图片，数组格式
	
	for(var i = 0; i< coolect_list_temp.length; i++) {
		var coolectvalue = coolect_list_temp.eq(i).val();
		json = json + "{" +
				    	"'alt': '"+ i +"'," +
				        "'pid': "+ i +"," +//图片id
				        "'src': '"+ coolectvalue +"'," +//原图地址
				        "'thumb': ''" +//缩略图地址
				      "},"; 
	}
	
	json = json + "]" +
				"}";
				    
	
	var json_temp = eval('(' + json + ')');
	
	 layer.photos({
	    photos: json_temp //格式见API文档手册页
	    ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机
	 });
}

function showImg_release(param) {
	var coolect_list_temp = $(param).parents('td').children("[name='release']");// 
	var json = "{" +
	  "'title':'收取行李'," + //相册标题
	  "'id': 112," +//相册id
	  "'start': 0," + //初始显示的图片序号，默认0
	  "'data': [" ;   //相册包含的图片，数组格式
	
	for(var i = 0; i< coolect_list_temp.length; i++) {
		var coolectvalue = coolect_list_temp.eq(i).val();
		json = json + "{" +
				    	"'alt': '"+ i +"'," +
				        "'pid': "+ i +"," +//图片id
				        "'src': '"+ coolectvalue +"'," +//原图地址
				        "'thumb': ''" +//缩略图地址
				      "},"; 
	}
	
	json = json + "]" +
				"}";
				    
	
	var json_temp = eval('(' + json + ')');
	
	 layer.photos({
	    photos: json_temp //格式见API文档手册页
	    ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机
	 });
}

//修改取派员 针对一个订单修改取件地址相关地址
function modifyaddr_Send(param) {
	var orderAddrId = $(param).parents('tr').find('.orderAddrId').val();
	var srcaddrtype = $(param).parents('tr').find('.srcaddrtype').val();
	var orderid = $(param).parents('body').find('.orderid').val();
	layer.open({
	  type: 2,
	  icon: 7,
	  title: ['地址修改', 'font-size:18px;'],
	  offset: ['10px', '300px'],
	  area: ['400px', '450px'],
	  fixed: false, //不固定
	  content:  "./orderinfo/modifyOrderAddr?orderid=" + orderid + "&addrType=SEND" + "&addrTypeValue=" + srcaddrtype + "&orderAddrId=" + orderAddrId + "&addrType1=SEND"
	});
}

//修改取派员 针对一个订单修改取件地址相关地址
function modifyaddr_Dest(param) {
	var orderAddrId = $(param).parents('tr').find('.orderAddrId').val();
	var destaddrtype = $(param).parents('tr').find('.destaddrtype').val();
	var orderid = $(param).parents('body').find('.orderid').val();
	layer.open({
	  type: 2,
	  icon: 7,
	  title: ['地址修改', 'font-size:18px;'],
	  offset: ['10px', '300px'],
	  area: ['400px', '450px'],
	  fixed: false, //不固定
	  content:  "./orderinfo/modifyOrderAddr?orderid=" + orderid + "&addrType=DEST" + "&addrTypeValue=" + destaddrtype + "&orderAddrId=" + orderAddrId + "&addrType1=TASK"
	});
}

// 回填订单地址
function backfillOrderAdrr(OrderAddr, addrType) {
	if('SEND' == addrType) {
		$('.srcaddrtype').val(OrderAddr.srcaddrtype);
		$('.srcaddrtypedesc').html(ORDER_ADDRESS_TYPE[OrderAddr.srcaddrtype].name);
		$('.scrlandmark').html(OrderAddr.scrlandmark);
		$('.srcaddress').html(OrderAddr.srcaddress);
	} else {
		$('.destaddrtype').val(OrderAddr.destaddrtype);
		$('.destaddrtypedesc').html(ORDER_ADDRESS_TYPE[OrderAddr.destaddrtype].name);
		$('.destlandmark').html(OrderAddr.destlandmark);
		$('.destaddress').html(OrderAddr.destaddress);
	}
}

// 寄送时间
function edit_taketime(param) {
	var orderid = $(param).parents('body').find('.orderid').val();
	
	layer.open({
	  type: 2,
	  icon: 8,
	  title: ['时间修改', 'font-size:18px;'],
	  offset: ['95px', '376px'],
	  area: ['248px;', '330px'],
	  fixed: false, //不固定
	  content: "./orderinfo/modifyTaskTimeOrSendTime?orderid=" + orderid + "&timeType=taketime"
	});
}

// 收件时间
function edit_sendtime(param) {
	var orderid = $(param).parents('body').find('.orderid').val();
	layer.open({
	  type: 2,
	  icon: 8,
	  title: ['时间修改', 'font-size:18px;'],
	  offset: ['95px', '376px'],
	  area: ['248px;', '330px'],
	  fixed: false, //不固定
	  content: "./orderinfo/modifyTaskTimeOrSendTime?orderid=" + orderid + "&timeType=sendtime",
	});
}


//回填订单寄送时间收件时间
function backfillOrderTaketimeAndSendTime(time, timeType) {
	if('taketime' == timeType) {
		$('.taketime').html(time);
	} else {
		$('.sendtime').html(time);
	}
}