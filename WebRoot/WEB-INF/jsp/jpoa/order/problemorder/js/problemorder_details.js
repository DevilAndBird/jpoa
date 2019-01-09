function process1(param) {
	$('#btnProcess').val($(param).next().val());
	$('#myModal').modal('show');
}

function process2() {
	$.ajax({
		url : "./problemorder/process",
		data : {
			id : $('#btnProcess').val(),
			processdesc : $('#processdesc_modal').val()
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			if(data.success) {
				var parent_ = $('.pro' + $('#btnProcess').val());
				parent_.children('.processdesc').html(data.problemOrder.processdesc);
				parent_.children('.processname').html(data.problemOrder.processname);
				parent_.children('.processmobile').html(data.problemOrder.processmobile);
				parent_.children('.processtimeformat').html(data.problemOrder.processtimeformat);
				$('#myModal').modal('hide');
			} else {
				alert(data.errMeg);
			}	
		}
	});
	
};