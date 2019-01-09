$('#arrivedtime').datetimepicker({
    format: 'yyyy-mm-dd hh:ii',
    minuteStep:15
});

//确认修改
function editdmanAllocDetails() {
	var arrivedtime = $("#arrivedtime").val();
	var orderid = $("#orderid").val();
	var timeType = $("#timeType").val();
	
	orderMainSpec = {};
	orderMainSpec.id = orderid;
	if(timeType == 'taketime') {
		orderMainSpec.taketime = arrivedtime;
	} else {
		orderMainSpec.sendtime = arrivedtime;
	}
	
	$.ajax({
		url : "./orderinfo/updateTakeTimeOrSendTime",
		data :orderMainSpec,
		dataType : "json",
		type : "POST",
		success : function(data) {
			parent.backfillOrderTaketimeAndSendTime(arrivedtime, timeType);
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	});
};
