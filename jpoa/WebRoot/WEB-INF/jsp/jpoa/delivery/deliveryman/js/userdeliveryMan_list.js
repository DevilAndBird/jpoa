var userDeliveryMan_list = {
	init : function() {
		/* 模态框是否可移动 */
		$("#myModal").draggable();
		/* 模态框_是否可用状态加载 */
		loadIsValid();
	}

};

/* 表单提交 */
function search() {
	$("#Form").submit();
}

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

/* 模态框_是否可用状态加载  */
function loadIsValid() {
	$("#seleIsvalid_modal").empty();
	$("#seleIsvalid_modal").append("<option value='-1' selected='selected'>--是否有效--</option>");
	for(i in ISVALID_TYPE) {
		$("#seleIsvalid_modal").append("<option value='"+ISVALID_TYPE[i].value+"'>"+ISVALID_TYPE[i].name+"</option>");
	}
}

/* 修改出勤 */
function updateAttendance(udmId,attendance) {
	updateAttendanceById(udmId, attendance);
}

function updateAttendanceById(udmId,attendance){
	$.ajax({
		url:"./userDeliveryMan/updateAttendance",
		data : {
			udmId : udmId,
			attendance:attendance
		},
		dateType:"json",
		type:"POST",
		traditional : true,
		success : function(msg){
			layer.alert(msg, {
				skin : 'layui-layer-molv', // 样式类名 自定义样式
				closeBtn : 1, // 是否显示关闭按钮
				anim : 1, // 动画类型
				btn : [ '确认' ], // 按钮
				icon : 6, // icon
				yes : function() {
					var returnStatus = data.split(":");
					if(returnStatus[0] == "SUCCESS") {
						var url = "./userDeliveryMan/deliveryManlistPage.do";
						$("#Form").attr("action", url);
						$("#Form").submit();
					} else if(returnStatus[0] == "FAIL") {
						var url = "./userDeliveryMan/deliveryManlistPage.do";
						$("#Form").attr("action", url);
						$("#Form").submit();
					}
				}
			});
		}
	});
}

/* 模态框填充_取派员 */
function filldeliveryMan_Modal(param)  {
	 var tdList =$(param).parent().parent().find("td");
	 $("#inpName_modal").val(tdList.eq(1).text());
	 $("#inpIdno_modal").val(tdList.eq(2).text());
	 $("#inpMobile_modal").val(tdList.eq(3).text());
	 $("#seleTransitCenterid_modal").find("option[value='" + tdList.eq(4).find("input").val() +"']").attr("selected",true);
	 $("#seleProvid_modal").find("option[value='" + tdList.eq(5).find("input").eq(0).val() +"']").attr("selected",true);
	 provCityLinkage(tdList.eq(5).find("input").eq(0).val(), "seleProvid_modal",tdList.eq(5).find("input").eq(1).val());
	 $("#seleIsvalid_modal").find("option[value='" + tdList.eq(6).find("input").val() +"']").attr("selected",true);
	 
     $("#h4DeliveryMan").show();
     $("#divDeliverMan").show();
     $("#btnUpdateDeliveryMan").show();
	 
	 $("#btnUpdateDeliveryMan").val($(param).val());
};
 
/* 编辑取派员信息 */
$("#btnUpdateDeliveryMan").click(function() {
	var id = $(this).val();
	var provid = $("#seleProvid_modal").attr("selected",true).val();
	var cityid = $("#seleCityid_modal").attr("selected",true).val();
	var name = $("#inpName_modal").val();
	var idno = $("#inpIdno_modal").val();
	var mobile =  $("#inpMobile_modal").val();
	var regionid = $("#regionDelivery_modal").attr("selected",true).val();
	var isvalid = $("#seleIsvalid_modal").attr("selected",true).val();
	
	if(provid == -1 || cityid == -1 || isvalid == -1 
			|| !provid || !cityid || !isvalid 
			|| !name || !idno || !mobile) {
		toastr.warning('请输入数据');
		return ;
	}
	
	if(!id) {
		toastr.warning('取派员id未获取，请检查代码');
		return ;
	}
	
	$.ajax({
		url : "./userDeliveryMan/updateUserDeliveryMan",
		data : {
			id : id,
			provid : provid,
			cityid : cityid,
			name : name,
			idno : idno,
			mobile : mobile,
			regionid : regionid,
			isvalid : isvalid
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			/* 这里需要增加提示框 */
			location.href="./userDeliveryMan/deliveryManlistPage";
		}
	});
});
