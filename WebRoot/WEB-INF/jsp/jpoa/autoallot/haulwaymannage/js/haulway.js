var haulway = {
		init : function() {
			// 模态框是否可移动 
			$("#myModal").draggable();
		}
};

/* 表单提交 */
function search() {
	$("#Form").submit();  
}

$("#Form").find('input[type=checkbox]').bind('click', function(){  
    $("#Form").find('input[type=checkbox]').not(this).attr("checked", false);  
});  

//新增标签
$("#newLabel").bind("click",function(){
	var transit =  $('tr[id^="transit_"]').length;
	var startdiv =  $("#startDiv");
	var $tr = $("<tr id=\'transit_"+transit+"\ '></tr>");// 创建tr
	var $tdSelec = $("<td style=\'text-align:center;vertical-align:middle;\'></td>"); //创建td
	var data=$("#transitCenterList").val();
	var option = "<option value='-1' selected='selected'>--经过集散中心--</option>";
	// 填充下拉框
	$("#startTransitCenterid_modal option").each(function(){
		var key = $(this).text();
		var val = $(this).val();
		if(val!='-1'){
			option = option+"<option value='"+val+"'>"+key+"</option>";		
		}
	});
	$tdSelec.html("<select  id=\'selecTransit_"+transit+"\'  style='vertical-align:top;width: 170px;'>"+option+"</select>"); //拼接内容
	var $tdTSpan = $("<td style=\'text-align:center;vertical-align:middle;\'></td>"); //创建td
	$tdTSpan.html("<input autocomplete='off' type='text'  id=\'tspan_"+transit+"'  placeholder='请输入时间间隔' style='width:120px' />"); //拼接内容
	$tdTSpan.append("&nbsp;&nbsp;&nbsp;<img   style='background-position:50px 50px;' src='"+IMG_URL_PATH.CANCEL_URL.value+"' id=\'"+transit+"\' class='img-circle' onclick='cancelLabel(this)' > ");
	$tr.append($tdSelec);
	$tr.append($tdTSpan);
	startdiv.before($tr);
});

//删除标签
function cancelLabel(obj){
	 $(obj).parent().parent().remove(); 
};

/* 保存取派员信息 */
$("#btnSaveHaulWay").click(function() {
	valiParam();
	// 出发地
	var startTransit =  $("#startTransitCenterid_modal").attr("selected",true).val();
	var  startpan = $("#startpan").val();
	//目的地
	var endTransit =  $("#endTransitCenterid_modal").attr("selected",true).val();
	var  endspan = $("#endspan").val();
	var transit =  $('tr[id^="transit_"]').length;
	var transitArr = new Array();
	var spanArr = new Array();	
	transitArr.push(startTransit);
	spanArr.push(startpan);
	for(var i=0;i<transit;i++){
		transitArr.push($("#selecTransit_"+i).attr("selected",true).val());
		spanArr.push($("#tspan_"+i).val());
	}
	transitArr.push(endTransit);
	spanArr.push(endspan);
	$.ajax({
		url : "./autoallot/saveAutoAllotWay",
		data : {
			startTransit : startTransit,
			endTransit : endTransit,
			transitArr : JSON.stringify(transitArr),
			spanArr : JSON.stringify(spanArr)
		},
		dataType : "text",
		type : "POST",
		traditional : true,
		success : function(data) {
			layer.alert(data, {
				skin : 'layui-layer-molv' // 样式类名 自定义样式
				,
				closeBtn : 1 // 是否显示关闭按钮
				,
				anim : 1 // 动画类型
				,
				btn : [ '确认' ] // 按钮
				,
				icon : 6 // icon
				,
				yes : function() {
					var returnStatus = data.split(":");
					if(returnStatus[0] == "SUCCESS") {
						var url = "./autoallot/toHaulwayPage.do";
						$("#Form").attr("action", url);
						$("#Form").submit();
					} 
				}
			});
		},
		error : function(data) {
			layer.msg(data, {
				icon : 5
			});
		}
	});
});

function valiParam(){
	// 出发地
	var startTransit =  $("#startTransitCenterid_modal").attr("selected",true).val();
	if(!startTransit||startTransit=='-1'){
		toastr.warning('请选择出发地集散中心');
		return;
	}
	//目的地
	var endTransit =  $("#endTransitCenterid_modal").attr("selected",true).val();
	var  endspan = $("#endspan").val();
	if(!endTransit||endTransit=='-1'){
		toastr.warning('请选择目的地集散中心');
		return;
	}
	if(!endspan){
		toastr.warning('请选择目的地时间间隔');
		return;
	}
	if(startTransit == endTransit){
		toastr.warning('出发地集散中心不等于目的地集散中心');
		return;
	}
	var transit =  $('tr[id^="transit_"]').length;
	for(i=0;i<transit.length;i++){
		var selecTransit = $("#selecTransit_"+i).attr("selected",true).val();
		var tspan = $("#tspan_"+i).val();
		if(!selecTransit ||selecTransit=='-1'|| !tspan){
			   toastr.warning('请选择经过的集散中心和时间间隔');
			   return;
		}
		if(selecTransit==startTransit || selecTransit==endTransit){
			   toastr.warning('各节点集散中心不能相同');
			   return;
		}
	}
}

$("button[id*='delete_']").click(function() {
	var idStr =  $(this).attr("id");
	var id = idStr.split("_")[1];
	$.ajax({
		url : "./autoallot/deleteHualWay",
		data : {
			id : id,
		},
		dataType : "text",
		type : "POST",
		traditional : true,
		success : function(data) {
			layer.alert(data, {
				skin : 'layui-layer-molv' // 样式类名 自定义样式
				,
				closeBtn : 1 // 是否显示关闭按钮
				,
				anim : 1 // 动画类型
				,
				btn : [ '确认' ] // 按钮
				,
				icon : 6 // icon
				,
				yes : function() {
					var returnStatus = data.split(":");
					if(returnStatus[0] == "SUCCESS") {
						var url = "./autoallot/toHaulwayPage.do";
						$("#Form").attr("action", url);
						$("#Form").submit();
					} 
				}
			});
		},
		error : function(data) {
			layer.msg(data, {
				icon : 5
			});
		}
	});
});

$("button[id*='pre_']").click(function() {
	var idStr =  $(this).attr("id");
	var id = idStr.split("_")[1];
	$.ajax({
		url : "./autoallot/previewMap",
		data : {
			id : id
		},
		dataType : "json",
		type : "POST",
		traditional : true,
		success : function(data) {
			map.clearOverlays();  
			var json = eval(data);
			var pois = [];
			$.each(json, function (i, item) {
				var currentgps = eval('(' + json[i].tranGps + ')');
				pois.push(new BMap.Point(currentgps.lng, currentgps.lat));
				
            });
			var sy = new BMap.Symbol(BMap_Symbol_SHAPE_BACKWARD_OPEN_ARROW, {
			    scale: 0.6,//图标缩放大小
			    strokeColor:'#fff',//设置矢量图标的线填充颜色
			    strokeWeight: '1',//设置线宽
			});
			var icons = new BMap.IconSequence(sy, '8', '26');
			// 创建polyline对象
			var polyline =new BMap.Polyline(pois, {
			   enableEditing: false,//是否启用线编辑，默认为false
			   enableClicking: true,//是否响应点击事件，默认为true
			   icons:[icons],
			   strokeWeight:'6',//折线的宽度，以像素为单位
			   strokeOpacity: 0.8,//折线的透明度，取值范围0 - 1
			   strokeColor:"#18a45b" //折线颜色
			});
			map.addOverlay(polyline); //增加折线
		},
		error : function(data) {
			layer.msg(data, {
				icon : 5
			});
		}
	});
});


