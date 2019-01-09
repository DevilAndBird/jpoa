layui.use('layer', function(){
	  layer = layui.layer;
});

var appuser_list = {
	init : function() {
		/* 模态框是否可移动 */
		$("#myModal").draggable();
		/* 模态框_是否可用状态加载 */
		loadIsValid();
		/* 模态框_登陆角色类型加载 */
		loadAppUserType();
		// 首先隐藏编辑信息窗口
		$("#myModal").hide();
	},

	/* 查看角色信息 */
	lookUserRole : function(param) {
		/* 获取参数 */
		var tdList = $(param).parent().parent().find("td");
		var userid = tdList.eq(0).find("input").val();
		var userType = tdList.eq(4).find("input").val();

		var url = null;

		/* 取派员 */
		if (!url && userType == APPUSER_TYPE.DELIVERY_MAN.value) {
			url = "./userDeliveryMan/deliveryManlistPage.do?userid=" + userid;
		}

		/* 集散中心 */
		if (!url && userType == APPUSER_TYPE.TRANSIT_CENTER.value) {
			url = "./appUser/findUserTransitCenterByUserid.do?userid=" + userid;
		}

		/* 班车司机 */
		if (!url && userType == APPUSER_TYPE.REGULAR_DRIVER.value) {
			url = "./userDriver/findUserDriverlistPage.do?userid=" + userid;
		}

		/* 机场取件员 */
		if (!url && userType == APPUSER_TYPE.AIRPORT_PICKER.value) {
			url = "./userPicker/userPickerlistPage.do?userid=" + userid;
		}

		/* 服务中心 */
		if (!url && userType == APPUSER_TYPE.SERVICE_CENTER.value) {
			url = "./appUser/findUserServiceCenterByUserid.do?userid=" + userid;
		}

		/* 回跳appUser */
		if (!url) {
			url = "./appUser/appUserlistPage.do";
		}

		$("#Form").attr("action", url);
		$("#Form").submit();
	}
};

/* 表单提交 */
function search() {
	$(" [name='name']").val($(" [name='name']").val().trim());
	$(" [name='mobile']").val($(" [name='mobile']").val().trim());
	$("#Form").submit();
}

/* 模态框_省市联动 */
$("#seleDmanProvid_modal").change(function() {
	provCityLinkage($(this).val(), 'seleProvid_modal', null);
});

function provCityLinkage(param, type, selectedValue) {
	$
			.ajax({
				url : "./city/findCityByProvid",
				data : {
					provid : param
				},
				dataType : "json",
				type : "POST",
				success : function(data) {
					if (type == 'seleProvid_modal') {
						$("#seleDmanCityid_modal").empty();
						$("#seleDmanCityid_modal")
								.append(
										"<option value='-1' selected='selected'>--城市--</option>");
						var json = eval(data);
						$.each(json, function(i, item) {
							$("#seleDmanCityid_modal").append(
									"<option value='" + json[i].id + "'>"
											+ json[i].name + "</option>");
						});
					}
				}
			});
}

/* 条件框_模态框_登陆角色类型加载 */
function loadAppUserType() {
	// 条件框
	$("#seleType").empty();
	$("#seleType").append("<option value='-1' selected='selected'>--人员类型--</option>");
	for(i in APPUSER_TYPE) {
		$("#seleType").append("<option value='"+APPUSER_TYPE[i].value+"'>"+APPUSER_TYPE[i].name+"</option>");
	}
	
	// 模态框
	$("#seleAppUserType_modal").empty();
	$("#seleAppUserType_modal").append("<option value='-1' selected='selected'>--角色类型--</option>");
	for (i in APPUSER_TYPE) {
		$("#seleAppUserType_modal").append("<option value='" + APPUSER_TYPE[i].value + "'>"+ APPUSER_TYPE[i].name + "</option>");
	}
}

/* 模态框_是否可用状态加载 */
function loadIsValid() {
	$("#seleIsvalid_modal").empty();
	$("#seleIsvalid_modal").append(
			"<option value='-1' selected='selected'>--是否有效--</option>");
	for (i in ISVALID_TYPE) {
		$("#seleIsvalid_modal").append(
				"<option value='" + ISVALID_TYPE[i].value + "'>"
						+ ISVALID_TYPE[i].name + "</option>");
	}
}

/* 模态框填充_行李提取员 */
function fillAppUser_modal(param) {
	var tdList = $(param).parent().parent().find("td");
	$("#inpName_modal").val(tdList.eq(1).text());
	$("#inpMobile_modal").val(tdList.eq(3).text());
	$("#inpPassword_modal").val(tdList.eq(2).text());
	$("#seleIsvalid_modal").find(
			"option[value='" + tdList.eq(7).find("input").val() + "']").attr(
			"selected", true);

	$("#h4AppUser").text("编辑角色登陆信息");
	$("#divAppUser").show();
	$("#seleAppUserType_modal").hide();
	$("#btnSaveAppUser").hide();

	$("#divDeliveryMan").hide();
	$("#btnSaveDman").hide();

	$("#divLinkTransitCenter").hide();
	$("#btnLinkTransitCenter").hide();

	$("#divPicker").hide();
	$("#btnSavePicker").hide();

	$("#divLinkServiceCenter").hide();
	$("#btnlinkServiceCenter").hide();

	$("#divDriver").hide();
	$("#btnSaveDriver").hide();

	$("#btnUpdateAppUser").val($(param).val());
};

// 校验增加appUser时，name是否存在


/* 编辑Appuser信息 */
$("#btnUpdateAppUser").click(function() {
	var id = $(this).val();
	var name = $("#inpName_modal").val();
	var password = $("#inpPassword_modal").val();
	var mobile = $("#inpMobile_modal").val();
	var isvalid = $("#seleIsvalid_modal").attr("selected", true).val();

	if (!id) {
		toastr.warning('行李提取员id无法获取');
		return;
	}
	if (!name || !password || !mobile || !isvalid || isvalid == -1) {
		toastr.warning('请输入数据');
		return;
	}

	$.ajax({
		url : "./appUser/updateAppUserById",
		data : {
			id : id,
			name : name,
			password : password,
			mobile : mobile,
			isvalid : isvalid
		},
		dataType : "text",
		type : "POST",
		success : function(data) {
			var result = data.split(":");
			if('SUCCESS' == result[0]) {
				location.href = "./appUser/appUserlistPage";
			} else {
				layer.msg(result[1]);
			}
			
		}
	});

});

/* 模态框重置_角色登陆 */
function resetAppUser() {
	$("#inpName_modal").val("");
	$("#inpPassword_modal").val("");
	$("#inpMobile_modal").val("");
	loadAppUserType();
	$("#btnUpdateAppUser").val("");

	$("#h4AppUser").text("第一步：新增角色登陆信息");

	$("#divAppUser").show();
	$("#seleAppUserType_modal").show();
	$("#seleIsvalid_modal").hide();
	$("#btnSaveAppUser").show();
	$("#btnUpdateAppUser").hide();

	$("#divDeliveryMan").hide();
	$("#btnSaveDman").hide();

	$("#divLinkTransitCenter").hide();
	$("#btnLinkTransitCenter").hide();

	$("#divPicker").hide();
	$("#btnSavePicker").hide();

	$("#divLinkServiceCenter").hide();
	$("#btnlinkServiceCenter").hide();

	$("#divDriver").hide();
	$("#btnSaveDriver").hide();
}

/* 模态框_新增角色登陆按钮 */
$("#btnSaveAppUser").click(function() {
	var name = $("#inpName_modal").val();
	var password = $("#inpPassword_modal").val();
	var mobile = $("#inpMobile_modal").val();
	var type = $("#seleAppUserType_modal").attr("selected", true).val();

	if (!name || !password || !mobile || !type || type == -1) {
		toastr.warning('请输入数据');
		return;
	}

	$.ajax({
		url : "./appUser/saveAppUser",
		data : {
			name : name,
			password : password,
			mobile : mobile,
			type : type
		},
		dataType : "text",
		type : "POST",
		success : function(msg) {
			var returnStatus = msg.split(":");
			var data = "";
			if(returnStatus[0]=='FAIL'){
				toastr.warning(returnStatus[1]);
				return;
			}
			data = returnStatus[1];
			/* 取派员 */
			if (type == APPUSER_TYPE.DELIVERY_MAN.value) {
				saveDMan(data);
				return;
			}

			/* 集散中心 */
			if (type == APPUSER_TYPE.TRANSIT_CENTER.value) {
				linkTransitCenter(data);
				return;
			}

			/* 班车司机 */
			if (type == APPUSER_TYPE.REGULAR_DRIVER.value) {
				saveDriver(data);
				return;
			}

			/* 机场取件员 */
			if (type == APPUSER_TYPE.AIRPORT_PICKER.value) {
				savePicker(data);
				return;
			}

			/* 服务中心 */
			if (type == APPUSER_TYPE.SERVICE_CENTER.value) {
				linkServiceCenter(data);
				return;
			}
		}
	});
});

/*
 * ADD
 * 取派员-------------------------------------start----------------------------------------------
 */
/* 新增取派员 */
function saveDMan(userid) {
	$("#seleDmanProvid_modal").find("option[value='" + -1 + "']").attr(
			"selected", true);
	$("#seleDmanCityid_modal").find("option[value='" + -1 + "']").attr(
			"selected", true);
	$("#inpDManName_modal").val("");
	$("#inpDManIdno_modal").val("");
	$("#inpDManMobile_modal").val("");
	$("#seleDmanTransitid_modal").find("option[value='" + -1 + "']").attr(
			"selected", true);

	$("#h4AppUser").hide(500);
	$("#h4AppUser").text("第二步：新增取派员信息");
	$("#h4AppUser").show(500);

	$("#divAppUser").hide(500);
	$("#btnSaveAppUser").hide(500);
	$("#btnUpdateAppUser").hide();

	$("#divDeliveryMan").show(500);
	$("#btnSaveDman").show(500);

	$("#divLinkTransitCenter").hide();
	$("#btnLinkTransitCenter").hide();

	$("#divLinkServiceCenter").hide();
	$("#btnlinkServiceCenter").hide();

	$("#divDriver").hide();
	$("#btnSaveDriver").hide();

	$("#btnSaveDman").val(userid);
	var dmanname = $("#inpName_modal").val();
	var dmanmobile = $("#inpMobile_modal").val();
	$("#inpDManName_modal").val(dmanname);
	$("#inpDManMobile_modal").val(dmanmobile);
}

$("#btnSaveDman").click(
		function() {
			var userid = $(this).val();
			var provid = $("#seleDmanProvid_modal").attr("selected", true)
					.val();
			var cityid = $("#seleDmanCityid_modal").attr("selected", true)
					.val();
			var regionid = $("#seleDmanRegion_modal").attr("selected", true)
			.val();
			var name = $("#inpDManName_modal").val();
			var idno = $("#inpDManIdno_modal").val();
			var mobile = $("#inpDManMobile_modal").val();
//			var transitid = $("#seleDmanTransitid_modal")
//					.attr("selected", true).val();
			var platenumber = $("#inpDManplatenumber_modal").val();
			if (!userid) {
				toastr.warning('未获取userid，请及时联系系统维护人员');
				return;
			}

			if (!provid || provid == -1 || !cityid || cityid == -1 || !name
					|| !idno || !mobile ||!regionid ) {
				toastr.warning('输入数据不完整，请重新检查');
				return;
			}

			$.ajax({
				url : "./userDeliveryMan/saveUserDeliveryMan",
				data : {
					userid : userid,
					provid : provid,
					cityid : cityid,
					name : name,
					idno : idno,
					mobile : mobile,
					regionid : regionid,
					platenumber : platenumber
				},
				dataType : "json",
				type : "POST",
				success : function(data) {
					location.href = "./appUser/appUserlistPage";
				}
			});
		});
/*
 * ADD
 * 取派员-------------------------------------end------------------------------------------------
 */

/*
 * link
 * appuser与集散中心关联-----------------------start----------------------------------------------
 */
function linkTransitCenter(userid) {
	$("#seleLinkTransitid_modal").find("option[value='" + -1 + "']").attr(
			"selected", true);

	$("#h4AppUser").hide(500);
	$("#h4AppUser").text("第二步：关联集散中心信息");
	$("#h4AppUser").show(500);

	$("#divAppUser").hide(500);
	$("#btnSaveAppUser").hide(500);
	$("#btnUpdateAppUser").hide();

	$("#divDeliveryMan").hide(500);
	$("#btnSaveDman").hide(500);

	$("#divLinkTransitCenter").show(500);
	$("#btnLinkTransitCenter").show(500);

	$("#divPicker").hide();
	$("#btnSavePicker").hide();

	$("#divLinkServiceCenter").hide();
	$("#btnlinkServiceCenter").hide();

	$("#divDriver").hide();
	$("#btnSaveDriver").hide();

	$("#btnLinkTransitCenter").val(userid);
}

$("#btnLinkTransitCenter").click(function() {
	var userid = $(this).val();
	var transitid = $("#seleLinkTransitid_modal").attr("selected", true).val();

	if (!userid) {
		toastr.warning('未获取userid，请及时联系系统维护人员');
		return;
	}

	if (!transitid || transitid == -1) {
		toastr.warning('请输入数据');
		return;
	}

	$.ajax({
		url : "./appUser/appUserLinkTransitCenter",
		data : {
			userid : userid,
			transitid : transitid
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			location.href = "./appUser/appUserlistPage";
		}
	});
});
/*
 * link
 * appuser与集散中心关联-----------------------end------------------------------------------------
 */

/*
 * Add
 * 机场收件员----------------------------------start----------------------------------------------
 */
function savePicker(userid) {
	$("#inpPickerName_modal").val("");
	$("#inpPickerIdno_modal").val("");
	$("#inpPickerMobile_modal").val("");
	$("#seleAirportcode_modal").find("option[value='" + -1 + "']").attr(
			"selected", true);

	$("#h4AppUser").hide(500);
	$("#h4AppUser").text("第二步：增加机场取件员信息");
	$("#h4AppUser").show(500);

	$("#divAppUser").hide(500);
	$("#btnSaveAppUser").hide(500);
	$("#btnUpdateAppUser").hide();

	$("#divDeliveryMan").hide();
	$("#btnSaveDman").hide();

	$("#divLinkTransitCenter").hide();
	$("#btnLinkTransitCenter").hide();

	$("#divPicker").show(500);
	$("#btnSavePicker").show(500);

	$("#divLinkServiceCenter").hide();
	$("#btnlinkServiceCenter").hide();

	$("#divDriver").hide();
	$("#btnSaveDriver").hide();

	$("#btnSavePicker").val(userid);
}

$("#btnSavePicker").click(function() {
	var userid = $(this).val();
	var name = $("#inpPickerName_modal").val();
	var idno = $("#inpPickerIdno_modal").val();
	var mobile = $("#inpPickerMobile_modal").val();
	var airportcode = $("#seleAirportcode_modal").attr("selected", true).val();

	if (!userid) {
		toastr.warning('未获取userid，请及时联系系统维护人员');
		return;
	}

	if (!name || !idno || !mobile || !airportcode || airportcode == -1) {
		toastr.warning('请输入数据');
		return;
	}

	$.ajax({
		url : "./userPicker/savePicker",
		data : {
			userid : userid,
			name : name,
			idno : idno,
			mobile : mobile,
			airportcode : airportcode
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			location.href = "./appUser/appUserlistPage";
		}
	});
});

/*
 * Add
 * 机场收件员----------------------------------end------------------------------------------------
 */

/*
 * link
 * appuser与服务中心关联-----------------------start----------------------------------------------
 */
function linkServiceCenter(userid) {
	$("#seleLinkServicecenter_modal").find("option[value='" + -1 + "']").attr(
			"selected", true);

	$("#h4AppUser").hide(500);
	$("#h4AppUser").text("第二步：关联柜台服务中心信息");
	$("#h4AppUser").show(500);

	$("#divAppUser").hide(500);
	$("#btnSaveAppUser").hide(500);
	$("#btnUpdateAppUser").hide();

	$("#divDeliveryMan").hide();
	$("#btnSaveDman").hide();

	$("#divLinkTransitCenter").hide();
	$("#btnLinkTransitCenter").hide();

	$("#divPicker").hide();
	$("#btnSavePicker").hide();

	$("#divLinkServiceCenter").show(500);
	$("#btnlinkServiceCenter").show(500);

	$("#divDriver").hide();
	$("#btnSaveDriver").hide();

	$("#btnlinkServiceCenter").val(userid);
}

$("#btnlinkServiceCenter").click(
		function() {
			var userid = $(this).val();
			var servicecenterid = $("#seleLinkServicecenter_modal").attr(
					"selected", true).val();

			if (!userid) {
				toastr.warning('未获取userid，请及时联系系统维护人员');
				return;
			}

			if (!servicecenterid || servicecenterid == -1) {
				toastr.warning('请输入数据');
				return;
			}

			$.ajax({
				url : "./appUser/appUserLinkServiceCenter",
				data : {
					userid : userid,
					servicecenterid : servicecenterid
				},
				dataType : "json",
				type : "POST",
				success : function(data) {
					location.href = "./appUser/appUserlistPage";
				}
			});
		});
/*
 * link
 * appuser与服务中心关联-----------------------end----------------------------------------------
 */

/*
 * Add
 * 增加司机-----------------------------------start----------------------------------------------
 */
function saveDriver(userid) {
	$("#inpDriverName_modal").val("");
	$("#inpDriverIdno_modal").val("");
	$("#inpDriverMobile_modal").val("");

	$("#h4AppUser").hide(500);
	$("#h4AppUser").text("第二步：增加班车司机信息");
	$("#h4AppUser").show(500);

	$("#divAppUser").hide(500);
	$("#btnSaveAppUser").hide(500);
	$("#btnUpdateAppUser").hide();

	$("#divDeliveryMan").hide();
	$("#btnSaveDman").hide();

	$("#divLinkTransitCenter").hide();
	$("#btnLinkTransitCenter").hide();

	$("#divPicker").hide();
	$("#btnSavePicker").hide();

	$("#divLinkServiceCenter").hide();
	$("#btnlinkServiceCenter").hide();

	$("#divDriver").show(500);
	$("#btnSaveDriver").show(500);

	$("#btnSaveDriver").val(userid);
}

$("#btnSaveDriver").click(function() {
	var userid = $(this).val();
	var name = $("#inpDriverName_modal").val();
	var idno = $("#inpDriverIdno_modal").val();
	var mobile = $("#inpDriverMobile_modal").val();

	if (!userid) {
		toastr.warning('未获取userid，请及时联系系统维护人员');
		return;
	}

	if (!name || !idno || !mobile) {
		toastr.warning('请输入数据');
		return;
	}

	$.ajax({
		url : "./userDriver/insertUserDriver",
		data : {
			userid : userid,
			name : name,
			idno : idno,
			mobile : mobile
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			location.href = "./appUser/appUserlistPage";
		}
	});
});
/*
 * Add
 * 增肌司机-------------------------------------end----------------------------------------------
 */
