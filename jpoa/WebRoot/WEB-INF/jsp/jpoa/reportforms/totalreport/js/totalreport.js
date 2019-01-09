var totalreport = {
	init : function() {
		/* 表单提交 */
		function search() {
			$("#Form").submit();
		}

		$('#starttime').datetimepicker({
		    format: 'yyyy-mm-dd hh:ii',
		    minuteStep:15
		});
		$('#endtime').datetimepicker({
		    format: 'yyyy-mm-dd hh:ii',
		    minuteStep:15
		});
	}
};

function toExcel() {
	var url = "./reportforms/outExcel.do";
	$("#Form").attr("action", url);
	$("#Form").submit();
	$("#Form").attr("action", "./reportforms/listReportForms");
}

function search() {
	 var url = "./reportforms/listReportForms";
	 $("#Form").attr("action", url);
	 $("#Form").submit();
}
