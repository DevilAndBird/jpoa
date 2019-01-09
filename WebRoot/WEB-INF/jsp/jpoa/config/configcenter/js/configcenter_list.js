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
	
	$('#butmodifyconfig').val($(param).parents('tr').find('#hiddenid').val());
	$('#model_cityid').val($(param).parents('tr').find('#hiddencityid').val());
	$('#model_businesskey').val($(param).parents('tr').find('.business_key').html());
	$('#model_businessvalue').val($(param).parents('tr').find('.business_value').html());
	$('#model_remark').val($(param).parents('tr').find('.remark').html());
	
	$('#myModal').modal('show');
}

$('#butmodifyconfig').click(function() {
	var id = $('#butmodifyconfig').val();
	var cityid = $('#model_cityid').val();
	var business_key = $('#model_businesskey').val();
	var business_value = $('#model_businessvalue').val().trim();
	var isvalid = $('#model_isvalid').val().trim();
	var remark = $('#model_remark').val().trim();
	
	if(business_value == null || business_value == '') {
		layer.msg('value不能为空');
		return;
	}
	
	if(remark == null || remark == '') {
		layer.msg('备注信息不能为空');
		return;
	}
	
	$.ajax({
		url : "./configcenter/updateConfig",
		data : {
			id : id,
			cityid : cityid,
			business_key : business_key,
			business_value : business_value,
			isvalid : isvalid,
			remark : remark
		},
		dataType : "text",
		type : "POST",
		success : function(data) {
			var result = data.split(":");
			if('SUCCESS' == result[0]) {
				location.href = "./configcenter/configCenterlistPage";
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
	var provid = $('#seleProvid_modal').val().trim();
	var cityid = $('#seleCityid_modal').val().trim();
	var business_key = $('#model_key').val().trim();
	var business_value = $('#model_value').val().trim();
	var remark = $('#model_remark').val().trim();
	
	if(provid == null || provid == '' || provid == '-1') {
		layer.msg('省份不能为空');
		return;
	}
	
	if(cityid == null || cityid == '' || cityid == '-1') {
		layer.msg('城市不能为空');
		return;
	}
	
	if(business_key == null || business_key == '') {
		layer.msg('业务key不能为空');
		return;
	}
	
	if(business_value == null || business_value == '') {
		layer.msg('业务value不能为空');
		return;
	}
	
	if(remark == null || remark == '') {
		layer.msg('业务remark不能为空');
		return;
	}
	
	$.ajax({
		url : "./configcenter/saveConfig",
		data : {
			provid : provid,
	        cityid : cityid,
	        business_key : business_key,
	        business_value : business_value,
	        remark : remark
		},
		dataType : "text",
		type : "POST",
		success : function(data) {
			var result = data.split(":");
			if('SUCCESS' == result[0]) {
				location.href = "./configcenter/configCenterlistPage";
			} else {
				layer.msg(result[1]);
			}
		}
	});
});

function reflush_yes(param) {
	var cityid = $(param).parents('tr').find('#hiddencityid').val();
	var key = $(param).parents('tr').find('.business_key').html();
	$.ajax({
		url : "./configcenter/reflushConfig",
		data : {
			cityid : cityid,
			business_key : key
		},
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

/* 省市联动 */
$("#seleProv").change( function() {
	provCityLinkage($(this).val(), '-1');
});

var provid = $("#seleProv").val();

if(provid != '-1') {
	var cityid = $('#hidden_cityid').val();
	provCityLinkage(provid, cityid);
}

/* 省市联动 */
function provCityLinkage(param, cityid){
    $.ajax({
		url : "./city/findCityByProvid",
		data : {
			provid : param
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			$("#seleCity").empty();
			$("#seleCity").append("<option value='-1' selected='selected'>--城市--</option>");
			var json = eval(data);
			$.each(json, function (i, item) {
				if(cityid != '-1') {
					if(json[i].id == cityid) {
						$("#seleCity").append("<option value='"+json[i].id+"' selected='selected'>"+json[i].name+"</option>");
					} else {
						$("#seleCity").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
					}
					
				} else {
					$("#seleCity").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
				}
				
            });
		}
	});	 
}

// 加载省份信息
loadProvAll();
