var detailreport = {
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

function btnDownloadInvoice() {
	var url = "./reportforms/outDetailExcel.do";
	$("#Form").attr("action", url);
	$("#Form").submit();
}

function search() {
	 var url = "./reportforms/detailReportForms";
	 $("#Form").attr("action", url);
	 $("#Form").submit();
}
