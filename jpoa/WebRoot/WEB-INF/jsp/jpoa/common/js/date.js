// 格式化时间，使之能返回 H:m
function fomate_Hm(date){
	return isgreaterTodata(date);
	/*var date = new Date(date);
	var hours = date.getHours();
	if(date.getHours() < 10) {
		hours = "0" + date.getHours();
	}
	var min = date.getMinutes();
	if(date.getMinutes() < 10) {
		min = "0" + date.getMinutes();
	}
	return hours + ":" + min;*/
}


// 日期校验 大于今天返回true
function isgreaterTodata(param) {
//	var todate = new Date();
	var bydate = new Date(param);
	
//	var toYear = todate.getUTCFullYear(); //获取完整的年份(4位,1970)
//	var toMonth = todate.getMonth() + 1; //获取当前月份(0-11,0代表1月)
//	var toDate = todate.getDate(); //获取当前日(1-31)
	
//	var byYear = bydate.getUTCFullYear(); //获取完整的年份(4位,1970)
	var byMonth = bydate.getMonth() +1; //获取当前月份(0-11,0代表1月)
	var byDate = bydate.getDate(); //获取当前日(1-31)
	
	return formatTime(byMonth) + "-" + formatTime(byDate) + " " + formatTime(bydate.getHours()) + ":" + formatTime(bydate.getMinutes());
	/*if(toYear != byYear || toMonth > byMonth) {
		return formatTime(byMonth) + "-" + formatTime(byDate) + " " + formatTime(bydate.getHours()) + ":" + formatTime(bydate.getMinutes());
	}
	
	if(toMonth == byMonth && toDate > byDate) {
		return formatTime(byMonth) + "-" + formatTime(byDate) + " " + formatTime(bydate.getHours()) + ":" + formatTime(bydate.getMinutes());
	}
	
	// 今天之前
	if(toMonth == byMonth && toDate < byDate) {
		return formatTime(byMonth) + "-" + formatTime(byDate) + " " + formatTime(bydate.getHours()) + ":" + formatTime(bydate.getMinutes());
	}
	
	// 今天
	if(toMonth == byMonth && toDate == byDate) {
		return formatTime(bydate.getHours()) + ":" + formatTime(bydate.getMinutes());
	}
	
	// 今天之后
	if(toMonth < byMonth) {
		return formatTime(byMonth) + "-" + formatTime(byDate) + " " + formatTime(bydate.getHours()) + ":" + formatTime(bydate.getMinutes());
	}*/
}

// 格式时间
function formatTime(param) {
	return param < 10? "0" + param : param;
}

// 是否超时
function isOvertime(time) {
	var todate = new Date();
	var bydate = new Date(time);
	
	var to = todate.getTime();
	var by = bydate.getTime();
	
	var differ = by- to;
	
	if(differ <=0) {
		return -1;
	}
	
	//计算相差分钟数  
    var minutes = Math.floor(differ/(60*1000));
    
    if(minutes <= 30) {
    	return by;
    } else {
    	return -2;
    }
}

function countTime(arrivedtime, selector, selector2) {  
	
    //获取当前时间  
    var date = new Date();  
    var now = date.getTime();  
    //设置截止时间  
    var end = arrivedtime;  
    //时间差  
    var leftTime = end-now;  
    //定义变量 d,h,m,s保存倒计时的时间  
    var m,s;  
    if (leftTime>=0) {  
        m = Math.floor(leftTime/1000/60%60);  
        s = Math.floor(leftTime/1000%60);                     
    }  else {
    	 $(selector).html("<lable class='lableovertime'>超时!</lable>");
    	 $(selector2).attr("src", IMG_URL_PATH.BOMB_OVERTIME_24_URL.value);
    	 return;
    }
    
    if(m<10) {
    	m = "0" + m;
    }
    if(s<10) {
    	s = "0" + s;
    }
    $(selector).html(m+":"+s);
    
    //递归每秒调用countTime方法，显示动态时间效果  
    setTimeout(countTime,1000, arrivedtime, selector, selector2);  

}  