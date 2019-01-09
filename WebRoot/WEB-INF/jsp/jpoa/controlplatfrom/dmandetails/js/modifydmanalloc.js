// ==================================修改取件地址===================================
// 修改区初始化被隐藏
//$("#divmodify").hide();
$("#transitCenter").hide();
$("#serviceCenter").hide();
$("#serviceCenter").hide();
$("#divaddr").hide();
loadRoleType();
orderTypeCharge();

/* 加载订单动作类型 */
function loadRoleType() {
	var param = $("#roletype").val();
	
	
	$(" [name='seletRoleType']").empty();
	$(" [name='seletRoleType']").append("<option value='-1' selected='selected'>--订单动作类型--</option>");
	for(i in ROLE_TYPE) {
		var roletype = ROLE_TYPE[i].value;
		
		if(roletype.substring(roletype.length-4, roletype.length) != param.substring(param.length-4, param.length)) {
			continue;
		}
		
		if(ROLE_TYPE[i].value == param) {
			$(" [name='seletRoleType']").append("<option value='"+ROLE_TYPE[i].value+"' selected='selected'>"+ROLE_TYPE[i].name+"</option>");
		} else {
			$(" [name='seletRoleType']").append("<option value='"+ROLE_TYPE[i].value+"' >"+ROLE_TYPE[i].name+"</option>");
		}
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
		loadcounter("#serviceCenter", $('#addrType').val());
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

var addrselectindex;
//点击进入地址输入页面
$('#addrDetails').click(function() {
	addrselectindex = layer.open({
						  type: 2,
						  title: false,// 不需要标题
						  closeBtn: 0,//没有按钮
						  id : 'GDADDRSELECT1',//不管是什么类型的层，都只允许同时弹出一个
						  area: ['100%', '100%'],
						  fixed: false, //不固定
						  content: "./controlplatform_dmanDatails/addrselect"
						});
});

function closeaddrselectpath() {
	  layer.close(addrselectindex);
}

var hotelgps = '';
var hotellandmark = '';
var hoteladdr = '';
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
	var orderroleid = $("#orderroleid").val();
	var orderid = $("#orderid").val();
	var roletypeCharge = $("#seletRoleType").attr("selected",true).val();
	var address = "";
	var addrname = "";
	var addrdesc = "";
	var housenum = $('#hoursenum').val();
	var desttype = "";
	var gps = '';
	var istrans = '0';

	if(roletypeCharge == ROLE_TYPE.ROLE_TRANSIT_SEND.value.toString()
			|| roletypeCharge == ROLE_TYPE.ROLE_TRANSIT_TASK.value.toString()) {
		address = $("#transitCenter").attr("selected",true).val();
		addrname = $("#transitCenter").find("option:selected").text();
		var temp1 = $("#transitCenter option:selected").attr("title");
		var temp2 = temp1.split(":");
		var temp3 = temp2[1].split("@");
		addrdesc = temp2[0];
		gps = "{'lng':'"+ temp3[0] +"','lat':'"+ temp3[1] +"'}";
		
		desttype = DESTINSATION_TYPE.TRANSITCERTER.value;
		
		if(address == -1) {
			toastr.warning('请选择/填写');
			return;
		}
	}
	// 如果动作是服务中心
	if(roletypeCharge == ROLE_TYPE.ROLE_AIRPORT_SEND.value.toString()
			|| roletypeCharge == ROLE_TYPE.ROLE_AIRPORT_TASK.value.toString()) {
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
	if(roletypeCharge == ROLE_TYPE.ROLE_HOTEL_SEND.value.toString()
			|| roletypeCharge == ROLE_TYPE.ROLE_HOTEL_TASK.value.toString()) {
		desttype = hoteladdrtype;
		addrdesc = hoteladdr + ' ' + housenum;
		addrname = hotellandmark;
		gps = hotelgps;
		istrans = '1';
		
		if(!addrdesc) {
			toastr.warning('请选择/填写');
			return;
		}
	} 
	
	$.ajax({
		url : "./controlplatform_dmanDatails/updatedestbyid",
		data : {
			id : orderroleid,
			roletype : roletypeCharge,
			address : address,
			addrname : addrname,
			addrdesc : addrdesc,
			desttype : desttype,
			destgps : gps,
			istrans : istrans,
			orderid : orderid
		},
		dataType : "text",
		type : "POST",
		success : function(data) {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
			parent.gpsinit();
			parent.orderallocfillpage();
			parent.findDmanDetails();
			parent.dmancurrentGps();
		}
	});
};

