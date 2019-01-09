// 初始化数据
$("[name='srcserviceCenter']").hide();
$("[name='destserviceCenter']").hide();
// 如果是机场则
$('#srctype').change(function() {
	var srctype = $(this).attr('selected', true).val();
	
	if(ORDER_ADDRESS_TYPE.AIRPORTCOUNTER.value == srctype) {
		$("[name='srcserviceCenter']").show();
		// 加载机场信息
		loadcounter("#srcserviceCenter");
		$('#srcaddress').hide();
	} else {
		// 加载查询模糊地址
		loadMapAutocomplete("srcaddress", "srcaddressshow");
		$("[name='srcserviceCenter']").hide();
		$('#srcaddress').show();
	}
});
//
$('#desttype').change(function() {
	var desttype = $(this).attr('selected', true).val();
	
	if(ORDER_ADDRESS_TYPE.AIRPORTCOUNTER.value == desttype) {
		$("[name='destserviceCenter']").show();
		// 加载机场信息
		loadcounter("#destserviceCenter");
		$('#destaddress').hide();
	} else {
		// 加载查询模糊地址
		loadMapAutocomplete("destaddress", "destaddressshow");
		$("[name='destserviceCenter']").hide();
		$('#destaddress').show();
	}
});

$('#taketime').datetimepicker({
    format: 'yyyy-mm-dd hh:ii',
    minuteStep:30
});
$('#sendtime').datetimepicker({
    format: 'yyyy-mm-dd hh:ii',
    minuteStep:30
});

/* 查询条件框_省市联动 */
$("#seleProv").change( function() {
	provCityLinkage($(this).val(), 'seleProv', null);
});

/* 模态框_省市联动 */
$("#seleProvid_modal").change(function() {
	provCityLinkage($(this).val(), 'seleProvid_modal', null);
});

function provCityLinkage(param, type, selectedValue){
    $.ajax({
		url : "./city/findCityByProvid",
		data : {
			provid : param
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			if(type == 'seleProv') {
				$("#seleCity").empty();
				$("#seleCity").append("<option value='-1' selected='selected'>--城市--</option>");
				var json = eval(data);
				$.each(json, function (i, item) {
					$("#seleCity").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	            });
				return;
			}
			
			if(type == 'seleProvid_modal') {
				$("#seleCityid_modal").empty();
				$("#seleCityid_modal").append("<option value='-1' selected='selected'>--城市--</option>");
				var json = eval(data);
				$.each(json, function (i, item) {
					$("#seleCityid_modal").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	            });
				
				/* 增加按钮时效果*/ 
				if(selectedValue != null && selectedValue != '') {
					$("#seleCityid_modal").find("option[value='" + selectedValue +"']").attr("selected",true);
				}
				return;
			}
		}
	});	 
}

/* 出发地类型 */
$("#srctype").change( function() {
	var srctype = $("#srctype").attr("selected",true).val();
	$("#mailingway_msg").show();
	$("#mailingway_div").show();
	$("#mailingway").empty();
	$("#mailingway").append(" <option value='-1' selected='selected' >请选择出发地类型</option> ");
	$("#mailingway").append(" <option value='ONESELF' >本人</option> ");
	$("#mailingway").append(" <option value='OTHERSSEND' >他人代理</option> ");
	if(srctype == "HOTEL"){
		$("#mailingway").append(" <option value='FRONTDESK'  >酒店前台</option> ");		
	}
});

/* 目的地类型 */
$("#desttype").change( function() {
	var desttype = $("#desttype").attr("selected",true).val();
	$("#backway_msg").show();
	$("#backway_div").show();
	$("#backway").empty();
	$("#backway").append(" <option value='-1' selected='selected' >请选择目的地类型</option> ");
	$("#backway").append(" <option value='ONESELF' >本人</option> ");
	$("#backway").append(" <option value='OTHERSSEND' >他人代理</option> ");
	if(desttype =="HOTEL"){
		$("#backway").append(" <option value='FRONTDESK'  >酒店前台</option> ");		
	}
});

/* 出发地类型 */
$("#mailingway").change( function() {
	var mailingway = $("#mailingway").attr("selected",true).val();
	if(mailingway == "OTHERSSEND"){
		$("#real_div").show();
		$("#send_div").hide();
	}else{
		$("#real_div").hide();
		$("#send_div").show();
	}
	if(mailingway == "ONESELF"){
		$("#sendername").val($("#cusname").val());
		$("#senderphone").val($("#cusmobile").val());
	}
});

/* 目的地类型 */
$("#backway").change( function() {
	var backway = $("#backway").attr("selected",true).val();
	if(backway == "OTHERSSEND"){
		$("#realb_div").show();
		$("#back_div").hide();
	}else{
		$("#realb_div").hide();
		$("#back_div").show();
	}
	if(backway == "ONESELF"){
		$("#receivername").val($("#cusname").val());
		$("#receiverphone").val($("#cusmobile").val());
	}
});

/* 数量 */
$("#insurance").change( function() {
	var insuranceStr = $("#insurance").attr("selected",true).val();
	var insurance =  parseInt(insuranceStr);
	var numStr = $("#num").val();
	if(numStr ==null || numStr ==""){
		numStr =0;
	}
	var num = parseInt(numStr);
	 $("#totalmoney").val(num*50+insurance);
});

/* 数量 */
$("#num").change( function() {
	var insuranceStr = $("#insurance").attr("selected",true).val();
	var insurance =  parseInt(insuranceStr);
	var numStr = $("#num").val();
	if(numStr ==null || numStr ==""){
		numStr =0;
	}
	var num = parseInt(numStr);
	 $("#totalmoney").val(num*50+insurance);
});

/* 保存按钮 */
$("#btnSaveOrderMain").click(function() {
	var backway = $("#backway").attr("selected",true).val();
	var  receivername = $("#realbname").val();
	var  receiverphone = $("#realbphone").val();
	if(backway == "ONESELF"){
		  receivername = $("#cusname").val();
		  receiverphone = $("#cusmobile").val();
	}
	
	var srcaddress = '';
	var scrlandmark = '';
	var srctype = $("#srctype").attr("selected",true).val();
	if(ORDER_ADDRESS_TYPE.HOTEL.value == srctype || ORDER_ADDRESS_TYPE.RESIDENCE.value == srctype) {
		srcaddress = $("#srcaddress").val();
	} else {
		scrlandmark = $("#srctype").find("option:selected").text();
		srcaddress = $("#srctype").find("option:selected").title();
	}
	
	var destaddress = '';
	var destlandmark = '';
	var desttype = $("#desttype").attr("selected",true).val();
	if(ORDER_ADDRESS_TYPE.HOTEL.value == desttype || ORDER_ADDRESS_TYPE.RESIDENCE.value == desttype) {
		destaddress = $("#destaddress").val();
	} else {
		destlandmark = $("#desttype").find("option:selected").text();
		destaddress = $("#desttype").find("option:selected").title();
	}
	
	$.ajax({
		url : "orderinfo/saveOrderMain",
		data : {
			srcprovid : $("#seleProv").attr("selected",true).val(),
			srccityid   : $("#seleCity").attr("selected",true).val(),
			srcprovname : $("#seleProv").find("option:selected").text(),
			srccityname   : $("#seleCity").find("option:selected").text(),
			destprovid : $("#seleProv").attr("selected",true).val(),
			destcityid   : $("#seleCity").attr("selected",true).val(),
			destprovname : $("#seleProv").find("option:selected").text(),
			destcityname   : $("#seleCity").find("option:selected").text(),
			type:$("#srctype").attr("selected",true).val()+"TO"+$("#desttype").attr("selected",true).val(),
			typeDesc:$("#srctype").find("option:selected").text()+"-"+$("#desttype").find("option:selected").text(),
			cusid :$("#cusid").val(),
			cusname :$("#cusname").val(),
			cusmobile :$("#cusmobile").val(),
			status :'WAITPAY', 
			totalmoney :$("#totalmoney").val(),
			actualmoney :$("#totalmoney").val(),
			insurecode :'TEST',//TODO 测试编码 tangqm
			notes :$("#notes").val(),
			taketime :$("#taketime").val(),
			sendtime :$("#sendtime").val(),
			flightno :$("#flightno").val(),
			srcaddrtype : srctype,
			destaddrtype : desttype,
			srclandmark : scrlandmark,
			destlandmark : destlandmark,
			srcaddress :srcaddress,
			destaddress :destaddress,
			num :$("#num").val(),
			channel :'weixin',
			sendername :$("#sendername").val(),
			senderphone :$("#senderphone").val(),
			realname :$("#realname").val(),
			realphone :$("#realphone").val(),
			receivername :$("#receivername").val(),
			receiverphone :$("#receiverphone").val(),
			cusid :$("#cusid").val(),
			cusname :$("#cusname").val(),
			cusmobile :$("#cusmobile").val(),
			mailingway :$("#mailingway").attr("selected",true).val(),
			backway :$("#backway").attr("selected",true).val()
		},
		dataType : "text",
		type : "POST",
		traditional :true,
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
					location.href ="./orderinfo/listOrderInfo";
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

/* 保存按钮 */
$("#btnSaveOrderPayMain").click(function() {
	var backway = $("#backway").attr("selected",true).val();
	var  receivername = $("#realbname").val();
	var  receiverphone = $("#realbphone").val();
	if(backway == "ONESELF"){
		  receivername = $("#cusname").val();
		  receiverphone = $("#cusmobile").val();
	}
	
	var srcaddress = '';
	var scrlandmark = '';
	var srctype = $("#srctype").attr("selected",true).val();
	if(ORDER_ADDRESS_TYPE.HOTEL.value == srctype || ORDER_ADDRESS_TYPE.RESIDENCE.value == srctype) {
		srcaddress = $("#srcaddress").val();
	} else {
		scrlandmark = $("#srcserviceCenter").find("option:selected").text();
		srcaddress = $("#srcserviceCenter").find("option:selected").attr('title');
	}
	
	var destaddress = '';
	var destlandmark = '';
	var desttype = $("#desttype").attr("selected",true).val();
	if(ORDER_ADDRESS_TYPE.HOTEL.value == desttype || ORDER_ADDRESS_TYPE.RESIDENCE.value == desttype) {
		destaddress = $("#destaddress").val();
	} else {
		destlandmark = $("#destserviceCenter").find("option:selected").text();
		destaddress = $("#destserviceCenter").find("option:selected").attr('title');
	}
	
	$.ajax({
		url : "orderinfo/saveOrderMain",
		data : {
			srcprovid : $("#seleProv").attr("selected",true).val(),
			srccityid   : $("#seleCity").attr("selected",true).val(),
			srcprovname : $("#seleProv").find("option:selected").text(),
			srccityname   : $("#seleCity").find("option:selected").text(),
			destprovid : $("#seleProv").attr("selected",true).val(),
			destcityid   : $("#seleCity").attr("selected",true).val(),
			destprovname : $("#seleProv").find("option:selected").text(),
			destlandmark : $("#desttype").find("option:selected").text(),
			srcaddrtype : srctype,
			destaddrtype : desttype,
			srclandmark : scrlandmark,
			destlandmark : destlandmark,
			srcaddress :srcaddress,
			destaddress :destaddress,
			cusid :$("#cusid").val(),
			cusname :$("#cusname").val(),
			cusmobile :$("#cusmobile").val(),
			status :'PREPAID', 
			totalmoney :$("#totalmoney").val(),
			actualmoney :$("#totalmoney").val(),
			type:$("#srctype").attr("selected",true).val()+"TO"+$("#desttype").attr("selected",true).val(),
			typeDesc:$("#srctype").find("option:selected").text()+"-"+$("#desttype").find("option:selected").text(),
			insurecode :'TEST',//TODO 测试编码 tangqm
			notes :$("#notes").val(),
			taketime :$("#taketime").val(),
			sendtime :$("#sendtime").val(),
			flightno :$("#flightno").val(),
			num :$("#num").val(),
			channel :'weixin',
			sendername :$("#sendername").val(),
			senderphone :$("#senderphone").val(),
			realname :$("#realname").val(),
			realphone :$("#realphone").val(),
			receivername :$("#receivername").val(),
			receiverphone :$("#receiverphone").val(),
			cusid :$("#cusid").val(),
			cusname :$("#cusname").val(),
			cusmobile :$("#cusmobile").val(),
			mailingway :$("#mailingway").attr("selected",true).val(),
			backway :$("#backway").attr("selected",true).val()
		},
		dataType : "text",
		type : "POST",
		traditional :true,
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
					location.href ="./orderinfo/listOrderInfo";
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