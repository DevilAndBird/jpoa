function edit(param) {
	$('#divSenderreceiver').show();
	$('#btnUpdateSenderreceiver').show();	
	
	$('#divUpdateNotes').hide();
	$('#divSaveNotes').hide();
	$('#btnUpdateNotes').hide();
	$('#btnSaveNotes').hide();
	
	var temp = $(param).parents('tr');
	var sendername = temp.find('.sendername').html();
	var senderphone = temp.find('.senderphone').html();
	var senderidno = temp.find('.senderidno').html();
	var receivername = temp.find('.receivername').html();
	var receiverphone = temp.find('.receiverphone').html();
	var receiveridno = temp.find('.receiveridno').html();
	var orderno = $(param).parents('body').find('.orderno').html();

	
	$('#sendername_modal').val(sendername);
	$('#senderphone_modal').val(senderphone);
	$('#senderidno_modal').val(senderidno);
	$('#receivername_modal').val(receivername);
	$('#receiverphone_modal').val(receiverphone);
	$('#receiveridno_modal').val(receiveridno);
	$('#btnUpdateSenderreceiver').val(orderno);
	
	/* 模态框是否可移动 */
	$('#myModal').modal('show');
}

$('#btnUpdateSenderreceiver').click(function () {
	var sendername = $('#sendername_modal').val();
	var senderphone = $('#senderphone_modal').val();
	var senderidno = $('#senderidno_modal').val();
	var receivername = $('#receivername_modal').val();
	var receiverphone = $('#receiverphone_modal').val();
	var receiveridno = $('#receiveridno_modal').val();
	var orderno  = $('#btnUpdateSenderreceiver').val();
	
	if(!sendername || !senderphone ||  !receivername || !receiverphone) {
		alert('参数不能为空');
	}
	
	if(!senderidno) {
		senderidno = '';
	}
	if(!receiveridno) {
		receiveridno = '';
	}
	
	$.ajax({
		url : "./orderinfo/updateSenderReceiver",
		data : {
			orderno : orderno,
			sendername : sendername,
			senderphone : senderphone,
			senderidno : senderidno,
			receivername  : receivername ,
			receiverphone : receiverphone,
			receiveridno : receiveridno 
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			if(data.success) {
				var body = $('#btnUpdateSenderreceiver').parents('body');
				body.find('.sendername').html(sendername);
				body.find('.senderphone').html(senderphone);
				body.find('.senderidno').html(senderidno);
				body.find('.receivername').html(receivername);
				body.find('.receiverphone').html(receiverphone);
				body.find('.receiveridno').html(receiveridno);
				$('#myModal').modal('hide');
			} else {
				alert(data.resMag);
			}
		}
	});
	
});

function editNotes (param) {
	$('#divUpdateNotes').show();
	$('#btnUpdateNotes').show();
	
	$('#divSenderreceiver').hide();
	$('#btnUpdateSenderreceiver').hide();	
	$('#divSaveNotes').hide();
	$('#btnSaveNotes').hide();
	
	var temp = $(param).parents('tr');
	var id = temp.find('[type="hidden"]').val();
	var notes = temp.find('.notes').html();
	
	$('#btnUpdateNotes').val(id);
	$('#update_notes_modal').val(notes);
	
	/* 模态框是否可移动 */
	$('#myModal').modal('show');
}

$('#btnUpdateNotes').click(function () {
	var id = $('#btnUpdateNotes').val();
	var notes = $('#update_notes_modal').val();
	if(!notes) {
		alert('参数不能为空');
	}
	
	$.ajax({
		url : "./orderinfo/updateOrderNotes",
		data : {
			id : id,
			notes : notes
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			if(data.success) {
				$('#noteid' + id).parents('tr').find('.notes').html(notes);
				$('#myModal').modal('hide');
			} else {
				alert(data.resMag);
			}
		}
	});
	
});

$('#saveNotes').click(function () {
	$('#divSaveNotes').show();
	$('#btnSaveNotes').show();
	
	$('#divSenderreceiver').hide();
	$('#btnUpdateSenderreceiver').hide();	
	
	$('#divUpdateNotes').hide();
	$('#btnUpdateNotes').hide();
	var orderid = $(this).parents('body').find('.orderid').val();
	$('#btnSaveNotes').val(orderid);
	/* 模态框是否可移动 */
	$('#myModal').modal('show');
}); 

$('#btnSaveNotes').click(function() {
	var orderid = $('#btnSaveNotes').val();
	var notes = $('#save_notes_modal').val();
	if(!notes) {
		alert('参数不能为空');
	}
	$.ajax({
		url : "./orderinfo/saveOrderNotes",
		data : {
			orderid : orderid,
			notes : notes
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			if(data.success) {
				var temp = "<tr>" + 
								"<input id='noteid" + data.notesid + "'  type='hidden' value='"+ data.notesid +"'/>" +
								"<td class='center'>" + data.addusername + "</td>" + 
								"<td class='center notes'>" + notes + "</td>" +
								"<td class='center'><a title='编辑' class='btn btn-mini btn-info' onclick='editNotes(this)'><i class= 'icon-edit'></i></a></td>" +
							"</tr>";
				
				$('.noteslist').html($('.noteslist').html() + temp);
				$('#myModal').modal('hide');
			} else {
				alert(data.resMag);
				$('#myModal').modal('hide');
			}
		}
	});
	
});