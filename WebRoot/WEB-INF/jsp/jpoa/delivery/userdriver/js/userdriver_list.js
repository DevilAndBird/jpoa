var userDriver_list = {
	init : function() {
		hideEdit();
		/* 模态框是否可移动 */
		$("#myModal").draggable();
		/* 模态框_是否可用状态加载 */
		loadIsValid();
	}

};
function hideEdit() {
	$("#inpName_modal").hide();
	$("#inpIdno_modal").hide();
	$("#inpMobile_modal").hide();
	$("#seleIsvalid_modal").hide();
}

/* 表单提交 */
function search() {
	$(" [name='name']").val($(" [name='name']").val().trim());
	$(" [name='idno']").val($(" [name='idno']").val().trim());
	$(" [name='mobile']").val($(" [name='mobile']").val().trim());
	$("#Form").submit();
}

/* 模态框_是否可用状态加载  */
function loadIsValid() {
	$("#seleIsvalid_modal").empty();
	$("#seleIsvalid_modal").append("<option value='-1' selected='selected'>--是否有效--</option>");
	for(i in ISVALID_TYPE) {
		$("#seleIsvalid_modal").append("<option value='"+ISVALID_TYPE[i].value+"'>"+ISVALID_TYPE[i].name+"</option>");
	}
}

/* 模态框填充_班车司机 */
function fillUserDriver_modal(param) {
	 var tdList =$(param).parent().parent().find("td");
	 $("#inpName_modal").val(tdList.eq(1).text());
	 $("#inpIdno_modal").val(tdList.eq(2).text());
	 $("#inpMobile_modal").val(tdList.eq(3).text());
	 $("#seleIsvalid_modal").find("option[value='" + tdList.eq(4).find("input").val() +"']").attr("selected",true);
	 
	 $("#inpName_modal").show();
	 $("#inpIdno_modal").show();
	 $("#inpMobile_modal").show();
	 $("#seleIsvalid_modal").show();
	 
	 $("#btnUpdateUserDriver").val($(param).val());
}

/* 编辑班车司机信息 */
$("#btnUpdateUserDriver").click(function() {
	var id = $(this).val();
	var name = $("#inpName_modal").val();
	var idno = $("#inpIdno_modal").val();
	var mobile =  $("#inpMobile_modal").val();
	var isvalid = $("#seleIsvalid_modal").attr("selected",true).val();
	
	if(!id) {
		toastr.warning('班车司机id无法获取');
		return ;
	}
	if( !name || !idno || !mobile || !isvalid || isvalid == -1){
		toastr.warning('请输入数据');
		return ;
	}
	
	$.ajax({
		url : "./userDriver/updateUserPicker",
		data : {
			id : id,
			name : name,
			idno : idno,
			mobile : mobile,
			isvalid : isvalid
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			location.href="./userDriver/findUserDriverlistPage";
		}
	});
	 
});