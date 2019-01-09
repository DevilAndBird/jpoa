// 地址自动补全 ==================================================================================
/*function G(id) {
    return document.getElementById(id);
}
function loadMapAutocomplete(suggestId, searchResultPanel) {
    var checkValue;
    Ac = new BMap.Autocomplete( //建立一个自动完成的对象
        {
            "input": suggestId,
        });
    Ac.addEventListener("onhighlight", function(e) { //鼠标放在下拉列表上的事件
        var str = "";
        var _value = e.fromitem.value;
        var value = "";
        if(e.fromitem.index > -1) {
            value =  _value.street + _value.business;
        }
        str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;

        value = "";
        if(e.toitem.index > -1) {
            _value = e.toitem.value;
            value =  _value.street + _value.business;
        }
        str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
        G(searchResultPanel).innerHTML = str;
    });

    Ac.addEventListener("onconfirm", function(e) { //鼠标点击下拉列表后的事件
        var _value = e.item.value;
        checkValue = _value.street + _value.business;
        G(searchResultPanel).innerHTML = "onconfirm<br />index = " + e.item.index + "<br />myValue = " + checkValue;
    });
}*/
// 补充 jsp 代码如下
//<div id='divaddr' style="float: left;">
	//<div class="col-sm-8" id="r-result">
	//   <input style="width: 300px;height: 30px" id="suggestIdshou" name="addr" type="text" class="form-control" placeholder="街道详细（精确到门牌号） " required="required">
	//</div>
	//<div id="searchResultPanelshou" style="border: 1px solid #C0C0C0; width: 150px; height: auto; display: none;"></div>
	//<	div id="l-map"></div>
//</div>
// =============================================================================================

var regionnameindex;

// 取派员区域查看
function viewRegiondman(map) {
	$.ajax({
		url : "./regionManage/loadRegiondman",
		data : {},
		dataType : "json",
		type : "POST",
		success : function(data) {
			var json = eval(data);
			$.each(json, function (i, item) {
				var regionname = json[i].regionname;
				var cityGpsList = eval(json[i].gps);
			    var remark = eval('(' + json[i].remark + ')');
				
				// 增加坐标点从数据库中
				var pointsArray = [];
				$.each(cityGpsList, function (j, item) {
					pointsArray[j] = new BMap.Point(cityGpsList[j].lng, cityGpsList[j].lat);
				});
				
				/* 显示多边形样式 */
				var polygonStyle = {
				        strokeColor : remark.regionColor,   //边线颜色
				        fillColor : remark.regionColor,     //填充颜色。当参数为空时，圆形将没有填充效果。   
				        strokeWeight: 3,     //边线的宽度，以像素为单位。
				        strokeOpacity: 0.5,  //边线透明度，取值范围0 - 1。  
				        fillOpacity: 0.2,    //填充的透明度，取值范围0 - 1
				        strokeStyle: 'solid' //边线的样式，solid或dashed。 
				};
				
				// 创建多边形:加载既定坐标，并默认绘制多边形
				var polygon = new BMap.Polygon(pointsArray, polygonStyle);
				//增加多边形
				map.addOverlay(polygon);
				
				polygon.addEventListener("mouseover", function(){
					//正上方
					regionnameindex = layer.msg(regionname, {offset: 't'});
				});
	        });
		}
	});
}
