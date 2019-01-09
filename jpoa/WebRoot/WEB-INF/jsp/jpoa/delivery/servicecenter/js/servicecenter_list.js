var serviceCenter_list = {
	init : function() {
		/* 查询条件款_服务中心类型 */
		loadServiceCenterType();
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

/* 查询条件款_服务中心类型 */
function loadServiceCenterType() {
	$("#seleServiceCenterType").empty();
	$("#seleServiceCenterType").append("<option value='-1' selected='selected'>--服务中心类型--</option>");
	for(i in SERVICECENTER_TYPE) {
		$("#seleServiceCenterType").append("<option value='"+SERVICECENTER_TYPE[i].value+"'>"+SERVICECENTER_TYPE[i].name+"</option>");
	}
	
	$("#seleServiceCenterType_modal").empty();
	$("#seleServiceCenterType_modal").append("<option value='-1' selected='selected'>--服务中心类型--</option>");
	for(i in SERVICECENTER_TYPE) {
		$("#seleServiceCenterType_modal").append("<option value='"+SERVICECENTER_TYPE[i].value+"'>"+SERVICECENTER_TYPE[i].name+"</option>");
	}
	
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

/* 集散中心点击修改时回填充 */
function fillServiceCenter_modal(param)  {
	 var tdList =$(param).parent().parent().find("td");
	 $("#seleServiceCenterType_modal").find("option[value='" + tdList.eq(1).find("input").val() +"']").attr("selected",true);
	 $("#inpServicecentername_modal").val(tdList.eq(2).find("input").eq(0).val());
	 $("#inpServicecentercode_modal").val(tdList.eq(2).find("input").eq(1).val());
	 $("#seleProvid_modal").find("option[value='" + tdList.eq(3).find("input").val() +"']").attr("selected",true);
	 provCityLinkage(tdList.eq(3).find("input").val(), "seleProvid_modal", tdList.eq(4).find("input").val());
	 $("#inpAddress_modal").val(tdList.eq(5).text());
	 $("#inpLinkman_modal").val(tdList.eq(6).text());
	 $("#inpLinkphone_modal").val(tdList.eq(7).text());
	 $("#seleIsvalid_modal").find("option[value='" + tdList.eq(8).find("input").val() +"']").attr("selected",true);
	 $("#seleRegion_modal").find("option[value='" + tdList.eq(9).find("input").val() +"']").attr("selected",true);
	 $("#btnUpdateServiceCenter").val($(param).val());
	 $("#h4UpdateServiceCenter").text("编辑柜台服务中心信息");
	 $("#btnSaveServiceCenter").hide();
};

/* 编辑集散中心信息 */
$("#btnUpdateServiceCenter").click(function() {
	var id = $(this).val();
	var type = $("#seleServiceCenterType_modal").attr("selected",true).val();
	var servicecentername = $("#inpServicecentername_modal").val();
	var servicecentercode = $("#inpServicecentercode_modal").val();
	var provid = $("#seleProvid_modal").attr("selected",true).val();
	var cityid = $("#seleCityid_modal").attr("selected",true).val();
	var regionid = $("#seleRegion_modal").attr("selected",true).val();
	var address = $("#inpAddress_modal").val();
	var linkman = $("#inpLinkman_modal").val();
	var linkphone =  $("#inpLinkphone_modal").val();
	var isvalid = $("#seleIsvalid_modal").attr("selected",true).val();
	
	if(!id) {
		toastr.warning('未获取柜台服务中心id，请联系系统给维护人员');
		return ;
	}
	if(type ==-1 || !type || provid == -1 || cityid == -1 ||regionid==-1|| !provid || !cityid  || isvalid == -1  || !isvalid || !servicecentername || !servicecentercode ||!regionid) {
		toastr.warning('请输入数据');
		return ;
	}
	if(!address || !linkman || !linkphone){
		toastr.warning('请输入数据');
		return ;
	}
	
	$.ajax({
		url : "./counterServiceCenter/updateCounterServiceCenter",
		data : {
			id : id,
			type : type,
			servicecentername : servicecentername,
			servicecentercode : servicecentercode,
			provid : provid,
			cityid : cityid,
			regionid : regionid,
			address : address,
			linkman : linkman,
			linkphone : linkphone,
			isvalid : isvalid
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			location.href="./counterServiceCenter/serviceCenterlistPage";
		}
	});
});


/* 新增柜台服务中心信息 */
function resetServiceCenter() {
	 $("#seleProvid_modal").find("option[value='" + -1 +"']").attr("selected",true);
	 $("#seleCityid_modal").find("option[value='" + -1 +"']").attr("selected",true);
	 $("#seleServiceCenterType_modal").find("option[value='" + -1 +"']").attr("selected",true);
	 $("#inpServicecentername_modal").val("");
	 $("#inpServicecentercode_modal").val("");
	 $("#inpAddress_modal").val("");
	 $("#inpLinkman_modal").val("");
	 $("#inpLinkphone_modal").val("");
	 $("#seleIsvalid_modal").hide();
	 
	 $("#h4UpdateServiceCenter").text("新增柜台服务中心");
	 $("#btnUpdateServiceCenter").hide();
	 $("#btnSaveServiceCenter").show();
}

$("#btnSaveServiceCenter").click(function() {
	 var provid = $("#seleProvid_modal").attr("selected",true).val();
	 var cityid = $("#seleCityid_modal").attr("selected",true).val();
	 var type = $("#seleServiceCenterType_modal").attr("selected",true).val();
	 var regionid = $("#seleRegion_modal").attr("selected",true).val();
	 var servicecentername = $("#inpServicecentername_modal").val();
	 var servicecentercode = $("#inpServicecentercode_modal").val();
	 var address = $("#inpAddress_modal").val();
	 var linkman = $("#inpLinkman_modal").val();
	 var linkphone = $("#inpLinkphone_modal").val();
	 
	 if(!provid || provid ==-1 || !cityid || cityid == -1 || !type || type == -1 
			 || !servicecentername || !servicecentercode || !address || !linkman || !linkphone || !regionid) {
		toastr.warning('请输入数据');
		return ;
	 }
	 
	 $.ajax({
			url : "./counterServiceCenter/saveServiceCenter",
			data : {
				provid : provid,
				cityid : cityid,
				type : type,
				regionid : regionid,
				servicecentername : servicecentername,
				servicecentercode : servicecentercode,
				address : address,
				linkman : linkman,
				linkphone : linkphone
			},
			dataType : "json",
			type : "POST",
			success : function(data) {
				location.href="./counterServiceCenter/serviceCenterlistPage";
			}
		});
});

