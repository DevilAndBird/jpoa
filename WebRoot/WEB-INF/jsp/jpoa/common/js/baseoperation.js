// js 用于所有跟业务有关系的数据加载信息


// 加载集散中心
function loadtransit(param) {
	$.ajax({
		url : "./transitCenter/findTransitAll",
		data : {
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			$(param).empty();
			$(param).append("<option value='-1' selected='selected'>--选择集散中心--</option>");
			
			var json = eval(data);
			$.each(json, function (i, item) {
				var gpstemp = json[i].gps;
				var gps = eval('(' + gpstemp + ')');
				$(param).append("<option title='" + json[i].address+ ":" + gps.lng + "@" + gps.lat + "' value='" + json[i].id +"'>" + json[i].name + "</option>");
			});
		}
	}); 	
}

// 加载机场
function loadcounter(param, transmittype) {
	
	$.ajax({
		url : "./counterServiceCenter/findCounterAll",
		data : {
			transmittype : transmittype
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			$(param).empty();
			$(param).append("<option value='-1' selected='selected'>--选择柜台服务中心 --</option>");
			var json = eval(data);
			$.each(json, function (i, item) {
				var gpstemp = json[i].gps;
				var gps = eval('(' + gpstemp + ')');
				$(param).append("<option title='" + json[i].servicecentername + json[i].remark + ":" + gps.lng + "@" + gps.lat +  "' value='"+ json[i].id +"'>" + json[i].servicecentername + json[i].remark + "</option>");
			});
		}
	}); 	
}



//加载省份信息
function loadProvAll(){
    $.ajax({
		url : "./prov/loadProvAll",
		data : {
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			$("#seleProvid_modal").empty();
			$("#seleProvid_modal").append("<option value='-1' selected='selected'>--省份--</option>");
			var json = eval(data);
			$.each(json, function (i, item) {
				$("#seleProvid_modal").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
            });
		}
	});	 
}

/* 模态框_省市联动 */
$("#seleProvid_modal").change(function() {
	provCityLinkage_($(this).val());
});

function provCityLinkage_(param){
    $.ajax({
		url : "./city/findCityByProvid",
		data : {
			provid : param
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			$("#seleCityid_modal").empty();
			$("#seleCityid_modal").append("<option value='-1' selected='selected'>--城市--</option>");
			var json = eval(data);
			$.each(json, function (i, item) {
				$("#seleCityid_modal").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
            });
		}
	});	 
}

function baseConfigSelectDefault(url,id,codetype,value,defaultstr){
	$.ajax({
		type:"GET",
		url:url,
		data : {
			orderType : codetype
		},
		dataType: "text",
		success:function(data){
			$("#"+id).empty();
			var optionsList = eval(data);
			if(defaultstr){
                $("#"+id).append("<option>" + "------"+defaultstr+"------" + "</option>");
			}
			for(var i=0;i<=optionsList.length;i++){
				var selectdefault = "";
				if(optionsList[i].code==value || optionsList[i].codename==value){
					selectdefault = "selected"; 
				}
				$("#"+id).append("<option "+selectdefault+"  value='" + optionsList[i].code+ "'>" + optionsList[i].codename + "</option>");
			}
		}
	}); 
}


