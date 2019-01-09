$('#arrivedtime').datetimepicker({
    format: 'yyyy-mm-dd hh:ii',
    minuteStep:15
});

//确认修改
function editdmanAllocDetails() {
	var orderroleid = $("#orderroleid").val();
	var arrivedtime = $("#arrivedtime").val();
	var orderid = $("#orderid").val();
	var roletype = $("#roletype").val();
	
	$.ajax({
		url : "./controlplatform_dmanDatails/updatearrivedtimebyid",
		data : {
			id : orderroleid,
			arrivedtime : arrivedtime,
			roletype : roletype,
			orderid : orderid
		},
		dataType : "text",
		type : "POST",
		success : function(data) {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
			parent.fillautoallotdetailbaseinfo();
			parent.manageautoallotdetailiframeWin();
			parent.fillrolegpsData();
		}
	});
};
