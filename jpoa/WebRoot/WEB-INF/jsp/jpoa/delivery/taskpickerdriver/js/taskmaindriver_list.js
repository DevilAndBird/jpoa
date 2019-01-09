var  taskPickerDriver_list  = {
	init : function() {
		/* 条件查询框_司机任务状态加载 */
		loadTaskMainDriverSatus();
		/* 模态框内容隐藏 */
		initHideModal();
		/* 模态框是否可移动 */
		$("#myModal").draggable();
		/* 模态框_是否可用状态加载 */
		loadIsValid();
		/* 模态框_任务类型加载 */
		loadTaskMainDriverType();
	}

};

/* 表单提交 */
function search() {
	$("#Form").submit();
}

function initHideModal() {
	$("#divTask").hide();
}

function loadTaskMainDriverSatus() {
	$("#seleStatus").empty();
	$("#seleStatus").append("<option value='-1' selected='selected'>--任务状态--</option>");
	for(i in TASKMAINDRIVER_STATUS) {
		$("#seleStatus").append("<option value='"+TASKMAINDRIVER_STATUS[i].value+"'>"+TASKMAINDRIVER_STATUS[i].name+"</option>");
	}
}

function loadTaskMainDriverType() {
	$("#seleType_modal").empty();
	$("#seleType_modal").append("<option value='-1' selected='selected'>--任务类型--</option>");
	for(i in TASKMAINDRIVER_TYPE) {
		$("#seleType_modal").append("<option value='"+TASKMAINDRIVER_TYPE[i].value+"'>"+TASKMAINDRIVER_TYPE[i].name+"</option>");
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

//修改司机任务状态
function updateTask(param) {
	var url = "./taskmaindriver/updatePickerStatus.do?id="+$(param).val();
	$("#Form").attr("action", url);
	$("#Form").submit();
}

//导出
function toExcel(){
	var url = './taskmaindriver/outExcel.do';
	$("#Form").attr("action", url );
	$("#Form").submit();
}

// 插入开始时间
$("#depdatetime_modal").ECalendar({
	type : "time",
	offset : [ 0, 2 ]
});
// 插入结束时间
$("#arrdatetime_modal").ECalendar({
	type : "time",
	offset : [ 0, 2 ]
});

//新增任务
function resetTask() {
	$("#divTask").show();
	$("#seleProvid_modal").find("option[value='" + -1 +"']").attr("selected",true);
	$("#seleCity_modal").find("option[value='" + -1 +"']").attr("selected",true);
	$("#seleDriverid_modal").find("option[value='" + -1 +"']").attr("selected",true);
	loadTaskMainDriverType();
	$("#srcaddr_model").val("");
	$("#destaddr_model").val("");
	$("#depdatetime_modal").val("");
	$("#arrdatetime_modal").val("");
}

$("#btnSaveTask").click(function(){
	var provid = $("#seleProvid_modal").attr("selected",true).val();
	var cityid = $("#seleCityid_modal").attr("selected",true).val();
	var driverid = $("#seleDriverid_modal").attr("selected",true).val();
	var type = $("#seleType_modal").attr("selected",true).val();
	var srcaddr = $("#srcaddr_model").val().trim();
	var destaddr = $("#destaddr_model").val().trim();
	var depdatetime = $("#depdatetime_modal").val().trim();
	var arrdatetime = $("#arrdatetime_modal").val().trim();
	
//	if( !provid || provid == -1 || !cityid || cityid == -1 || !driverid || driverid == -1 || !type || type == -1 
//			|| !srcaddr || !destaddr || !depdatetime || !arrdatetime){
//		toastr.warning('请输入数据');
//		return ;
//	}
	alert(srcaddr + destaddr);
	
	$.ajax({
		url : "./taskmaindriver/insertPickerTask.do",
		dataType:"json",
		type : "POST",
		data:{
			provid : provid,
			cityid : cityid,
			driverid : driverid,
			type : type,
			srcaddr : srcaddr,
			destaddr : destaddr,
			depdatetime : depdatetime,
			arrdatetime : arrdatetime
		},
		success:function(flag){
	    	  location.href="./taskmaindriver/findTaskpikerDriverlistPage";
	    }
		
	});
});



