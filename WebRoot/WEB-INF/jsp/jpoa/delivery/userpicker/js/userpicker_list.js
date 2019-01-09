var userPicker_list = {
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

/* 模态框_是否可用状态加载  */
function loadIsValid() {
	$("#seleIsvalid_modal").empty();
	$("#seleIsvalid_modal").append("<option value='-1' selected='selected'>--是否有效--</option>");
	for(i in ISVALID_TYPE) {
		$("#seleIsvalid_modal").append("<option value='"+ISVALID_TYPE[i].value+"'>"+ISVALID_TYPE[i].name+"</option>");
	}
}

/* 模态框填充_行李提取员 */
function fillUserPick_modal(param)  {
	 var tdList =$(param).parent().parent().find("td");
	 $("#inpName_modal").val(tdList.eq(1).text());
	 $("#inpIdno_modal").val(tdList.eq(2).text());
	 $("#inpMobile_modal").val(tdList.eq(3).text());
	 $("#seleIsvalid_modal").find("option[value='" + tdList.eq(4).find("input").val() +"']").attr("selected",true);
	 
	 $("#btnUpdateUserPicker").val($(param).val());
};

/* 编辑行李提取员信息 */
$("#btnUpdateUserPicker").click(function() {
	var id = $(this).val();
	var name = $("#inpName_modal").val();
	var idno = $("#inpIdno_modal").val();
	var mobile =  $("#inpMobile_modal").val();
	var isvalid = $("#seleIsvalid_modal").attr("selected",true).val();
	
	if(!id) {
		toastr.warning('行李提取员id无法获取');
		return ;
	}
	if( !name || !idno || !mobile || !isvalid || isvalid == -1){
		toastr.warning('请输入数据');
		return ;
	}
	
	$.ajax({
		url : "./userPicker/updateUserPicker",
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
			location.href="./userPicker/userPickerlistPage";
		}
	});
	
});