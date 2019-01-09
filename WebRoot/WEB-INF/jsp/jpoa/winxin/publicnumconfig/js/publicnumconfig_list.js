function edit_yes(param) {
	layer.msg('你确认需要修改吗？', {
		  time: 0, //不自动关闭
		  btn: ['确定', '关闭'],
		  yes: function(index){
		    layer.close(index);
		    fillmodifypublicnum(param);
		  }
		});
}

function fillmodifypublicnum(param) {
	$('#divmodify').show();
	$('#divsave').hide();
	$('#butmodifyconfig').show();
	$('#butsaveconfig').hide();
	
	$('#butmodifyconfig').val($(param).parents('tr').find('.publicnumconfigid').val());
	$('#model_publicnum').val($(param).parents('tr').find('.publicnum').html());
	$('#model_businesskey').val($(param).parents('tr').find('.businesskey').html());
	$('#model_businessvalue').val($(param).parents('tr').find('.businessvalue').html());
	
	$('#myModal').modal('show');
}

$('#butmodifyconfig').click(function() {
	var id = $('#butmodifyconfig').val();
	var publicnum = $('#model_publicnum').val();
	var businesskey = $('#model_businesskey').val();
	var businessvalue = $('#model_businessvalue').val().trim();
	
	if(publicnum == null || publicnum == '') {
		layer.msg('公众号不能为空');
		return;
	}
	
	if(businesskey == null || businesskey == '') {
		layer.msg('业务key不能为空');
		return;
	}
	
	if(businessvalue == null || businessvalue == '') {
		layer.msg('业务value不能为空');
		return;
	}
	
	$.ajax({
		url : "./wxpublicnumconfig/updatePubileNumConfig",
		data : {
			id : id,
			publicnum : publicnum,
	        businesskey : businesskey,
	        businessvalue : businessvalue
		},
		dataType : "text",
		type : "POST",
		success : function(data) {
			var result = data.split(":");
			if('SUCCESS' == result[0]) {
				location.href = "./wxpublicnumconfig/publicNumConfiglistPage";
			} else {
				layer.msg(result[1]);
			}
		}
	});
});


function save_yes(param) {
	layer.msg('你确认需要增加吗？', {
		  time: 0, //不自动关闭
		  btn: ['确定', '关闭'],
		  yes: function(index){
		    layer.close(index);
		    fillSavepublicnum(param);
		  }
		});
}

function fillSavepublicnum(param) {
	$('#divmodify').hide();
	$('#divsave').show();
	$('#butmodifyconfig').hide();
	$('#butsaveconfig').show();
	
	$('#myModal').modal('show');
}

$('#butsaveconfig').click(function() {
	var publicnum = $('#model_publicnum_save').val().trim();
	var businesskey = $('#model_businesskey_save').val().trim();
	var businessvalue = $('#model_businessvalue_save').val().trim();
	
	if(publicnum == null || publicnum == '') {
		layer.msg('公众号不能为空');
		return;
	}
	
	if(businesskey == null || businesskey == '') {
		layer.msg('业务key不能为空');
		return;
	}
	
	if(businessvalue == null || businessvalue == '') {
		layer.msg('业务value不能为空');
		return;
	}
	
	$.ajax({
		url : "./wxpublicnumconfig/savePubileNumConfig",
		data : {
			publicnum : publicnum,
	        businesskey : businesskey,
	        businessvalue : businessvalue
		},
		dataType : "text",
		type : "POST",
		success : function(data) {
			var result = data.split(":");
			if('SUCCESS' == result[0]) {
				location.href = "./wxpublicnumconfig/publicNumConfiglistPage";
			} else {
				layer.msg(result[1]);
			}
		}
	});
});

function refash_publicnum() {
	$.ajax({
		url : "./wxpublicnumconfig/refresh_publicnumconfig",
		data : {},
		dataType : "text",
		type : "POST",
		success : function(data) {
			var result = data.split(":");
			if('SUCCESS' == result[0]) {
				layer.msg(result[1]);
			} else {
				layer.msg(result[1]);
			}
		}
	});
}

function delete_yes(param) {
	layer.msg('你确认需要删除吗？', {
		  time: 0, //不自动关闭
		  btn: ['确定', '关闭'],
		  yes: function(index){
		    layer.close(index);
		    deletepublicnumconfig(param);
		  }
		});
}

function deletepublicnumconfig(param) {
	$.ajax({
		url : "./wxpublicnumconfig/delete_publicnumconfig",
		data : {
			id : $(param).parents('tr').find('.publicnumconfigid').val()
		},
		dataType : "text",
		type : "POST",
		success : function(data) {
			var result = data.split(":");
			if('SUCCESS' == result[0]) {
				location.href = "./wxpublicnumconfig/publicNumConfiglistPage";
			} else {
				layer.msg(result[1]);
			}
		}
	});
}
