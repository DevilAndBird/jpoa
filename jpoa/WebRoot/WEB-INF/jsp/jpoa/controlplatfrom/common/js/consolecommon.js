

// 取派员gps
function dman(map,param) {
	if(!param) {
		return;
	}
	
	var json = eval(param);
	
	$.each(json, function (i, item) {
		var dmanname = json[i].name;
		var waitpack = json[i].waitpick_Count;
		var waitsend = json[i].waitsend_count;
		var dman = "" + dmanname + " 取:" + waitpack + " 派:" + waitsend;
		
		// 取派员位置定位
		var currentgpsTemp = json[i].currentgps;
		
		if(!currentgpsTemp) {
			return;
		}
		
		var currentgps = eval('(' + currentgpsTemp + ')');
		
		//创建小狐狸
		var pt = new BMap.Point(currentgps.lng, currentgps.lat);
		var myIcon = new BMap.Icon(IMG_URL_PATH.DMAN_CAR_URL.value, new BMap.Size(200,100), {
		    anchor: new BMap.Size(10, 20),
		});
		var marker = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
	    
		var label = new BMap.Label(dman,{offset:new BMap.Size(20,-10)});
		marker.setLabel(label);
		
		map.addOverlay(marker); 
	});
}

// 酒/住 取件gps
function hoteltaskaddress (map,param) {
	if(!param) {
		return;
	}
	
	var json = eval(param);
	
	$.each(json, function (i, item) {
		// 酒店位置定位
		var destgpsTemp = json[i].destgps;
	
		if(!destgpsTemp) {
			return;
		}
		
		var currentgps = eval('(' + destgpsTemp + ')');

		var pt = new BMap.Point(currentgps.lng, currentgps.lat);
		var myIcon = new BMap.Icon(IMG_URL_PATH.SRCPOINT_URL.value, new BMap.Size(200,100), {
		    anchor: new BMap.Size(10, 20),
		});
		var marker = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
		
		map.addOverlay(marker); 
	});
}

// 酒/住 派件gps
function hotelsendaaddress (map,param) {
	if(!param) {
		return;
	}
	
	var json = eval(param);
	
	$.each(json, function (i, item) {
		// 酒店位置定位
		var destgpsTemp = json[i].destgps;
	
		if(!destgpsTemp) {
			return;
		}
		
		var currentgps = eval('(' + destgpsTemp + ')');

		//创建小狐狸
		var pt = new BMap.Point(currentgps.lng, currentgps.lat);
		var myIcon = new BMap.Icon(IMG_URL_PATH.DESTPOINT_URL.value, new BMap.Size(200,100), {
		    anchor: new BMap.Size(10, 20),
		});
		var marker = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
		
		map.addOverlay(marker); 
	});
}

// 集/柜 任务数量
function taskOrderNum(map,param) {
	var transitList = param["transitList"];
	transit(map,transitList);
	var counterList = param["counterList"];
	counter(map,counterList);
}

function transit(map,param) {
	if(!param) {
		return;
	}
	
	var json = eval(param);
	
	$.each(json, function (i, item) {
		var transitname = json[i].name;
		var task_count = json[i].task_count;
		var send_count = json[i].send_count;
		var content = "" + transitname + " 取:" + task_count + " 派:" + send_count;
		
		// 取派员位置定位
		var currentgpsTemp = json[i].gps;
		
		if(!currentgpsTemp) {
			return;
		}
		
		var currentgps = eval('(' + currentgpsTemp + ')');
		
		//创建小狐狸
		var pt = new BMap.Point(currentgps.lng, currentgps.lat);
		var myIcon = new BMap.Icon(IMG_URL_PATH.TRANSIT_URL.value, new BMap.Size(200,100), {
		    anchor: new BMap.Size(10, 20),
		});
		var marker2 = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
	    
		var label = new BMap.Label(content,{offset:new BMap.Size(20,-10)});
		marker2.setLabel(label);
		
		map.addOverlay(marker2); 
	});
}

function counter(map,param) {
	if(!param) {
		return;
	}
	
	var json = eval(param);
	
	$.each(json, function (i, item) {
		var servicecentername = json[i].servicecentername;
		var task_count = json[i].task_count;
		var send_count = json[i].send_count;
		var content = "" + servicecentername + " 取:" + task_count + " 派:" + send_count;
		
		// 取派员位置定位
		var currentgpsTemp = json[i].gps;
		
		if(!currentgpsTemp) {
			return;
		}
		
		var currentgps = eval('(' + currentgpsTemp + ')');
		
		//创建小狐狸
		var pt = new BMap.Point(currentgps.lng, currentgps.lat);
		var myIcon = new BMap.Icon(IMG_URL_PATH.AIRPORT_URL.value, new BMap.Size(200,100), {
		    anchor: new BMap.Size(10, 30),
		});
		var marker2 = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
	    
		var label = new BMap.Label(content,{offset:new BMap.Size(30,-10)});
		marker2.setLabel(label);
		
		map.addOverlay(marker2); 
	});
}


