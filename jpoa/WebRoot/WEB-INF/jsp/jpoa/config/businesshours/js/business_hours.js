//获取下拉框
 baseConfigSelectDefault("./config/getSelectValue","sendtype","ORDERADDRESSTYPE","");
 baseConfigSelectDefault("./config/getSelectValue","tasktype","ORDERADDRESSTYPE","");
 baseConfigSelectDefault("./config/getSelectValue","isovernight","ISOVERNIGHT","");
 $('#sendstarttime').datetimepicker({
	    format: 'yyyy-mm-dd hh:ii',
	    minuteStep:15
 });
 $('#sendendtime').datetimepicker({
	 format: 'yyyy-mm-dd hh:ii',
	 minuteStep:15
 });
 $('#taskstarttime').datetimepicker({
	 format: 'yyyy-mm-dd hh:ii',
	 minuteStep:15
 });
 $('#taskendtime').datetimepicker({
	 format: 'yyyy-mm-dd hh:ii',
	 minuteStep:15
 });

 function cancel_yes(param) {
	 var id = $(param).parents('tr').find('#hiddenid').val();
		layer.msg('你确认需要增加吗？', {
			  time: 0, //不自动关闭
			  btn: ['确定', '关闭'],
			  yes: function(index){
			    layer.close(index);
			    $.ajax({
			    	url : "./businesshours/cancelBusinessHours",
			    	data : {
			    		id : id
			    	},
			    	dataType : "text",
			    	type : "POST",
			    	success : function(data) {
			    		var result = data.split(":");
			    		if('SUCCESS' == result[0]) {
			    			layer.msg(result[1]);
			    			location.href = "./businesshours/businessHourslistPage";
			    		} else {
			    			layer.msg(result[1]);
			    		}
			    	}
			    });
			  }
			});
	}
 
 function edit_yes(param) {
		layer.msg('你确认需要修改吗？', {
			  time: 0, //不自动关闭
			  btn: ['确定', '关闭'],
			  yes: function(index){
			    layer.close(index);
			    showmodify(param);
			  }
			});
	}
 
 function showmodify(param) {
		$('#modifyconfig').show();
		$('#provandcity').hide();
		$('#saveconfig').hide();
		var sendtypename =  $(param).parents('tr').find('.sendtypename').html();
		var tasktypename =  $(param).parents('tr').find('.tasktypename').html();
		var isovernightname =  $(param).parents('tr').find('.isovernightname').html();
		$('#sendtype').val(sendtypename);
		$('#sendstarttime').val($(param).parents('tr').find('.sendstarttime').html());
		$('#sendendtime').val($(param).parents('tr').find('.sendendtime').html());
		$('#tasktype').val(tasktypename);
		$('#taskstarttime').val($(param).parents('tr').find('.taskstarttime').html());
		$('#taskendtime').val($(param).parents('tr').find('.taskendtime').html());
		$('#isovernight').val($(param).parents('tr').find('.isovernightname').html());
		$('#hiddenupdateid').val($(param).parents('tr').find('#hiddenid').val());
		baseConfigSelectDefault("./config/getSelectValue","sendtype","ORDERADDRESSTYPE",sendtypename);
		baseConfigSelectDefault("./config/getSelectValue","tasktype","ORDERADDRESSTYPE",tasktypename);
		baseConfigSelectDefault("./config/getSelectValue","isovernight","ISOVERNIGHT",isovernightname);
		$('#myModal').modal('show');
	}
 
$('#modifyconfig').click(function() {
	var sendtype = $('#sendtype').val().trim();
	var sendstarttime = $('#sendstarttime').val().trim();
	var sendendtime = $('#sendendtime').val().trim();
	var tasktype = $('#tasktype').val().trim();
	var taskstarttime = $('#taskstarttime').val().trim();
	var taskendtime = $('#taskendtime').val().trim();
	var isovernight = $('#isovernight').val().trim();
	var id = $('#hiddenupdateid').val().trim();
	
	if(sendtype == null || sendtype == '') {
		layer.msg('寄件类型不能为空');
		return;
	}
	
	if(sendstarttime == null || sendstarttime == '') {
		layer.msg('寄件开始时间不能为空');
		return;
	}
	
	if(sendendtime == null || sendendtime == '') {
		layer.msg('寄件结束时间不能为空');
		return;
	}
	
	if(tasktype == null || tasktype == '') {
		layer.msg('收件类型不能为空');
		return;
	}
	
	if(taskstarttime == null || taskstarttime == '') {
		layer.msg('送件开始时间不能为空');
		return;
	}
	
	if(taskendtime == null || taskendtime == '') {
		layer.msg('送件结束时间不能为空');
		return;
	}
	
	if(isovernight == null || isovernight == '') {
		layer.msg('是否隔夜不能为空');
		return;
	}
	
	$.ajax({
		url : "./businesshours/updateBusinessHours",
		data : {
			id:id,
			sendtype : sendtype,
			sendstarttime : sendstarttime,
			sendendtime : sendendtime,
			tasktype : tasktype,
			taskstarttime : taskstarttime,
			taskendtime : taskendtime,
			isovernight : isovernight,
		},
		dataType : "text",
		type : "POST",
		success : function(data) {
			var result = data.split(":");
			if('SUCCESS' == result[0]) {
				location.href = "./businesshours/businessHourslistPage";
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
		    showSave(param);
		  }
		});
}
function showSave(param) {
	$('#divmodify').hide();
	$('#divsave').show();
	$('#butmodifyconfig').hide();
	$('#butsaveconfig').show();
	$('#myModal').modal('show');
}

$('#saveconfig').click(function() {
	var provid = $('#seleProvid_modal').val().trim();
	var cityid = $('#seleCityid_modal').val().trim();
	var sendtype = $('#sendtype').val().trim();
	var sendstarttime = $('#sendstarttime').val().trim();
	var sendendtime = $('#sendendtime').val().trim();
	var tasktype = $('#tasktype').val().trim();
	var taskstarttime = $('#taskstarttime').val().trim();
	var taskendtime = $('#taskendtime').val().trim();
	var isovernight = $('#isovernight').val().trim();
	
	if(provid == null || provid == '' || provid == '-1') {
		layer.msg('省份不能为空');
		return;
	}
	
	if(cityid == null || cityid == '' || cityid == '-1') {
		layer.msg('城市不能为空');
		return;
	}
	
	if(sendtype == null || sendtype == '') {
		layer.msg('寄件类型不能为空');
		return;
	}
	
	if(sendstarttime == null || sendstarttime == '') {
		layer.msg('寄件开始时间不能为空');
		return;
	}
	
	if(sendendtime == null || sendendtime == '') {
		layer.msg('寄件结束时间不能为空');
		return;
	}
	
	if(tasktype == null || tasktype == '') {
		layer.msg('收件类型不能为空');
		return;
	}
	
	if(taskstarttime == null || taskstarttime == '') {
		layer.msg('送件开始时间不能为空');
		return;
	}
	
	if(taskendtime == null || taskendtime == '') {
		layer.msg('送件结束时间不能为空');
		return;
	}
	
	if(isovernight == null || isovernight == '') {
		layer.msg('是否隔夜不能为空');
		return;
	}
	
	$.ajax({
		url : "./businesshours/saveBusinessHours",
		data : {
			provid : provid,
			cityid : cityid,
			sendtype : sendtype,
			sendstarttime : sendstarttime,
			sendendtime : sendendtime,
			tasktype : tasktype,
			taskstarttime : taskstarttime,
			taskendtime : taskendtime,
			isovernight : isovernight,
		},
		dataType : "text",
		type : "POST",
		success : function(data) {
			var result = data.split(":");
			if('SUCCESS' == result[0]) {
				location.href = "./businesshours/businessHourslistPage";
			} else {
				layer.msg(result[1]);
			}
		}
	});
});

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
