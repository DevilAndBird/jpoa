var  transitCenter_list  = {
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

/* 模态框填充_集散中心 */
function fillTransitCenter_Modal(param)  {
	 var tdList =$(param).parent().parent().find("td");
	 $("#seleProvid_modal").find("option[value='" + tdList.eq(1).find("input").val() +"']").attr("selected",true);
	 provCityLinkage(tdList.eq(1).find("input").val(), "seleProvid_modal", tdList.eq(2).find("input").val());
	 
	 $("#inpName_modal").val(tdList.eq(3).text());
	 $("#inpAddress_modal").val(tdList.eq(4).text());
	 $("#inpLinkman_modal").val(tdList.eq(5).text());
	 $("#inpLinkphone_modal").val(tdList.eq(6).text());
	 $("#seleIsvalid_modal").find("option[value='" + tdList.eq(7).find("input").val() +"']").attr("selected",true);
     
	 
	 $("#h4TransitCenter").text("编辑集散中心");
     $("#h4TransitCenter").show();
     $("#divTransitCenter").show();
     $("#btnUpdateTransitCenter").show();
     $("#btnSaveTransitCenter").hide();
	 
	 $("#btnUpdateTransitCenter").val($(param).val());
};

/* 编辑集散中心信息 */
$("#btnUpdateTransitCenter").click(function() {
	var id = $(this).val();
	var provid = $("#seleProvid_modal").attr("selected",true).val();
	var cityid = $("#seleCityid_modal").attr("selected",true).val();
	var name = $("#inpName_modal").val();
	var address = $("#inpAddress_modal").val();
	var linkman =  $("#inpLinkman_modal").val();
	var linkphone = $("#inpLinkphone_modal").val();
	var isvalid = $("#seleIsvalid_modal").attr("selected",true).val();
	
	$.ajax({
		url : "./transitCenter/updateTransitCenter",
		data : {
			id : id,
			provid : provid,
			cityid : cityid,
			name : name,
			address : address,
			linkman : linkman,
			linkphone : linkphone,
			isvalid : isvalid
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			location.href="./transitCenter/findTransitCenterlistPage";
		}
	});
	
});

/* 新增集散中心信息 */
function resetTransitCenter() {
	 $("#seleProvid_modal").find("option[value='" + -1 +"']").attr("selected",true);
	 $("#seleCityid_modal").find("option[value='" + -1 +"']").attr("selected",true);
	 $("#inpName_modal").val("");
	 $("#inpAddress_modal").val("");
	 $("#inpLinkman_modal").val("");
	 $("#inpLinkphone_modal").val("");
	 $("#seleIsvalid_modal").hide();
	 
	 $("#h4TransitCenter").text("新增集散中心");
	 $("#btnUpdateTransitCenter").hide();
	 $("#btnSaveTransitCenter").show();
}

$("#btnSaveTransitCenter").click(function() {
	 var provid = $("#seleProvid_modal").attr("selected",true).val();
	 var cityid = $("#seleCityid_modal").attr("selected",true).val();
	 var name = $("#inpName_modal").val();
	 var address = $("#inpAddress_modal").val();
	 var linkman = $("#inpLinkman_modal").val();
	 var linkphone = $("#inpLinkphone_modal").val();
	 
	 if(!provid || provid ==-1 || !cityid || cityid == -1 || !name || !address || !linkman || !linkphone) {
		toastr.warning('请输入数据');
		return ;
	 }
	 
	 $.ajax({
			url : "./transitCenter/saveTransitCenter",
			data : {
				provid : provid,
				cityid : cityid,
				name : name,
				address : address,
				linkman : linkman,
				linkphone : linkphone
			},
			dataType : "json",
			type : "POST",
			success : function(data) {
				location.href="./transitCenter/findTransitCenterlistPage";
			}
		});
});
