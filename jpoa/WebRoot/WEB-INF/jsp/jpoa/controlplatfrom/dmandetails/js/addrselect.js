// 初始重置按钮隐藏
$('.div_addr_query_reset').hide();

// 输入框油值则重置按钮显示，没值则不显示
$('.inp_addr_query').keyup(function() {
	var addr = $('.inp_addr_query').val().trim();
	if(addr == null || addr == '') {
		$('.div_addr_query_reset').hide();
	} else {
		$('.div_addr_query_reset').show();
	}
});

$('.div_addr_query_reset').click(function() {
	$('.inp_addr_query').val('');
	$('.div_addr_query_reset').hide();
});

// 关闭地址查询页面
$('.div_addr_query_close').click(function() {
	$('.inp_addr_query').val("");
	$('#showform').empty();
	parent.closeaddrselectpath();
	 parent.fillhotelinfo('', '', '');
});

//高德 start ====================================================
$('.inp_addr_query').on("input propertychange",function(){
	var httpRequest = new XMLHttpRequest();
	var keyword = $(".inp_addr_query").val();
	var url = 'http://restapi.amap.com/v3/assistant/inputtips?key=e67da935e5e104d78a830519b619bf10&keywords='+keyword+'&city=shanghai&citylimit=true';
	if (!httpRequest) {
	        alert('Giving up :( Cannot create an XMLHTTP instance');
	        return false;
	 }
	httpRequest.onreadystatechange = alertContents;
	httpRequest.open('GET', url);
	httpRequest.send();
	function alertContents() {
	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
	        if (httpRequest.status === 200) {
	        	$("#showform").empty();
	        	var  showform = $("#showform");
	        	var returnList =  JSON.parse(httpRequest.responseText);
	        	var tips =  returnList.tips;
	            $.each(tips , function(obj,i){
	            	   var gdname = i.name;
	            	   var name = gdname.replace(keyword, "<strong style='color: #fbc400;'>" + keyword + "</strong>");
	            	   showform.append("<div id='"+ obj +"' style='height:45px;width: 100%;border-bottom: 1px solid #e6e6e6;'  onclick='getMapInfo(this)' >" +
					            	   		"<input type='hidden' class='name' value= '"+i.name+"'>" +
					            	   		"<input type='hidden' class='location' value= '"+i.location+"'>" +
					            	   		"<input type='hidden' class='address' value= '"+i.address+"'>" +
					            	   	    "<div style='width: 70%;height:45px;float:left;'>" +
						            	   	    "<p style='overflow: hidden;white-space: nowrap;text-overflow: ellipsis;margin-top:5px;margin-left:5px;'>" +
						            	   	    	"<font size='2px'>"+name+"</font>" +
						            	   	    	"<br />" +
						            	   	    	"<font size='2px' color='#B0B0B0'>"+i.address+"</font>" +
						            	   	    "</p>" +
					            	   	    "</div>" +
					            	   	    "<div style='width: 30%;float:left;height:45px;line-height:45px;'>" +
					            	   	    	"<font style='color:#333333;margin-left:20px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;' size='2px'>"+ i.district +"</font>" +
					            	   	    "</div>" +
					            	   "</div>");
	            });
	        } else {
	            alert('There was a problem with the request.');
	        }
	    }
	}
} );
       
function getMapInfo(param){
 var location =  $(param).children(".location").val();
 var name =  $(param).children(".name").val();
 var points = location.split(",");
 var landmark = name;
 var addresstemp = $(param).children(".address").val();
 var gpstemp =  "{'lng':'"+ points[0] +"','lat':'"+ points[1] +"'}";
 // 默认酒店
 var addrtype = DESTINSATION_TYPE.HOTEL.value;
 if($('.div_RESIDENCE').hasClass('on')) {
	 // 住宅
	 addrtype = DESTINSATION_TYPE.RESIDENCE.value;
 }
 
 $('.inp_addr_query').val("");
 $("#showform").empty();
 parent.fillhotelinfo(gpstemp, landmark, addresstemp, addrtype);
 parent.closeaddrselectpath();
};

// 高德 end ====================================================

// 选择住宅or酒店
$('.hotelorresidence div').click(function() {
	$(this).addClass("on").siblings().removeClass("on");
});
