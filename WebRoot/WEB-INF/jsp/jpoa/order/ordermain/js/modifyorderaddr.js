// 全局
var layer;
layui.use('layer', function(){
	  layer = layui.layer;
});

// ==================================修改取件地址===================================
// 修改区初始化被隐藏
//$("#divmodify").hide();
$("#transitCenter").hide();
$("#serviceCenter").hide();
$("#serviceCenter").hide();
$("#divaddr").hide();
loadAddrType();
orderTypeCharge();

/* 加载订单动作类型 */
function loadAddrType() {
	$(" [name='seletRoleType']").empty();
	$(" [name='seletRoleType']").append("<option value='-1' selected='selected'>--地址类型--</option>");
	var param = $("#addrTypeValue").val();
	for(i in ORDER_ADDRESS_TYPE) {
		if(ORDER_ADDRESS_TYPE[i].value == param) {
			$(" [name='seletRoleType']").append("<option value='"+ORDER_ADDRESS_TYPE[i].value+"' selected='selected'>"+ORDER_ADDRESS_TYPE[i].name+"</option>");
		} else {
			$(" [name='seletRoleType']").append("<option value='"+ORDER_ADDRESS_TYPE[i].value+"' >"+ORDER_ADDRESS_TYPE[i].name+"</option>");
		}
	}
}

function orderTypeCharge() {
	// 如果动作是集散中心的话则显示选择到集散中心
	var roletypeCharge = $(" [name='seletRoleType']").attr("selected",true).val();
	
	// 如果地址类型是酒店类型
	if(roletypeCharge == ORDER_ADDRESS_TYPE.AIRPORTCOUNTER.value) {
		$("#serviceCenter").show();
		loadcounter("#serviceCenter", $("#addrType1").val());
	} else {
		$("#serviceCenter").hide();
	}
	
	// 如果酒店
	if(roletypeCharge == ORDER_ADDRESS_TYPE.HOTEL.value
			|| roletypeCharge == ORDER_ADDRESS_TYPE.RESIDENCE.value) {
		$("#divaddr").show();
	} else {
		$("#divaddr").hide();
	}
}

var addrselectindex;
// 点击进入地址输入页面
$('#addrDetails').click(function() {
	addrselectindex = layer.open({
						  type: 2,
						  title: false,// 不需要标题
						  closeBtn: 0,//没有按钮
						  id : 'GDADDR_SELECT',//不管是什么类型的层，都只允许同时弹出一个
						  area: ['100%', '100%'],
						  fixed: false, //不固定
						  content: "./orderinfo/addrselect"
						});
});

function closeaddrselectpath() {
	  layer.close(addrselectindex);
}

var hotelgps;
var hotellandmark;
var hoteladdr;
var hoteladdrtype = '';
function fillhotelinfo(gps, landmark, addr, addrtype) {
	$("#suggestIdshou").val(landmark);
	hotelgps = gps;
	hotellandmark = landmark;
	hoteladdr = addr;
	hoteladdrtype = addrtype;
}


// 确认修改
function editdmanAllocDetails() {
	var roletypeCharge = $("#seletRoleType").attr("selected",true).val();
	var address = '';
	var addrname = '';
	var addrdesc = '';
	var housenum = $('#hoursenum').val();
	var gps = '';
	var istrans = '0';

	
	// 如果动作是服务中心
	if(roletypeCharge == ORDER_ADDRESS_TYPE.AIRPORTCOUNTER.value) {
		address = $("#serviceCenter").attr("selected",true).val();
		addrname = $("#serviceCenter").find("option:selected").text();
		var temp1 = $("#serviceCenter").find("option:selected").attr("title");
		var temp2 = temp1.split(":");
		var temp3 = temp2[1].split("@");
		addrdesc = temp2[0];
		gps = "{'lng':'"+ temp3[0] +"','lat':'"+ temp3[1] +"'}";
		
		desttype = DESTINSATION_TYPE.SERVICECERTER.value;
		
		if(address == -1) {
			toastr.warning('请选择/填写');
			return;
		}
	}
	// 如果酒店
	if(roletypeCharge == ORDER_ADDRESS_TYPE.HOTEL.value
			|| roletypeCharge == ORDER_ADDRESS_TYPE.RESIDENCE.value) {
		addrname = hotellandmark;
		addrdesc = hoteladdr + ' ' + housenum;
		gps = hotelgps;
		istrans = '1';
		
		if(!$("#suggestIdshou").val()) {
			toastr.warning('请选择/填写');
			return;
		}
	} 
	
	var param = $("#addrType").val();
	orderAddr = {};
	orderAddr.id = $('#orderAddrId').val();
	orderAddr.istrans = istrans;
	if('SEND' == param) {
		orderAddr.srcaddrtype = roletypeCharge;
		orderAddr.srcaddressid = address;
		orderAddr.scrlandmark = addrname;
		orderAddr.srcaddress = addrdesc;
		orderAddr.srcgps = gps;
	} else {
		orderAddr.destaddrtype = roletypeCharge;
		orderAddr.destaddressid = address;
		orderAddr.destlandmark = addrname;
		orderAddr.destaddress = addrdesc;
		orderAddr.destgps = gps;
	}
	
	$.ajax({
		url : "./orderinfo/updateOrderAddr",
		data : orderAddr,
		dataType : "json",
		type : "POST",
		success : function(data) {
			if(data.success) {
				parent.backfillOrderAdrr(orderAddr, param);
				var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				parent.layer.close(index);
			} else {
				alert(data.resMsg);
			}
			
		}
	});
};
