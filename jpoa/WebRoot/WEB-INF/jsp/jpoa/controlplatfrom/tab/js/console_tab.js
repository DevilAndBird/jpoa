//注意：选项卡 依赖 element 模块，否则无法进行功能性操作
var layer,element;
layui.use(['layer', 'element'], function(){
	  element = layui.element;
	  layer = layui.layer;
});

// 弹出层标识
var dmanlistindex,selfMotonAllocindex,manualAllocationindex,feedbackindex;

//初始化加载页面
dmanlist();
function dmanlist () {
	layui.use('layer', function(){
		dmanlistindex = layer.open({
			  type: 2,
			  title: false,// 不需要标题
			  offset: ['55px', '0px'],
			  area: ['100%', '96%'],
			  closeBtn: 0,// 没有关闭按钮
			  shade: 0,// 不想显示遮罩
			  id : 'console_dmanlist',//不管是什么类型的层，都只允许同时弹出一个
			  move: false,// 来禁止拖拽
			  isOutAnim: false,// 关闭层时会有一个过度动画,如果你不想开启
			  content: "./console_tab/dmanlist_main",
			  scrollbar: false
		});
		
		layer.close(selfMotonAllocindex);
		layer.close(manualAllocationindex);
	});
};

// 自动分配列表
function selfMotonAlloc() {
	layui.use('layer', function(){
		selfMotonAllocindex = layer.open({
			  type: 2,
			  title: false,// 不需要标题
			  offset: ['55px', '0px'],
			  area: ['100%', '95%'],
			  closeBtn: 0,// 没有关闭按钮
			  shade: 0,// 不想显示遮罩
			  id : 'console_selfMotonAlloclist',//不管是什么类型的层，都只允许同时弹出一个
			  move: false,// 来禁止拖拽
			  isOutAnim: false,// 关闭层时会有一个过度动画,如果你不想开启
			  content: "./console_tab/autoallocmap_main",
			  scrollbar: false // 不允许浏览器有滚动条
		});
		
		layer.close(dmanlistindex);
		layer.close(manualAllocationindex);
		layer.close(feedbackindex);
	});
}

//待人工分配
function manualAllocation() {
	layui.use('layer', function(){
		manualAllocationindex = layer.open({
			  type: 2,
			  title: false,// 不需要标题
			  offset: ['55px', '0px'],
			  area: ['100%', '95%'],
			  closeBtn: 0,// 没有关闭按钮
			  shade: 0,// 不想显示遮罩
			  id : 'console_manualAllocationlist',//不管是什么类型的层，都只允许同时弹出一个
			  move: false,// 来禁止拖拽
			  isOutAnim: false,// 关闭层时会有一个过度动画,如果你不想开启
			  content: "./console_tab/waitallocmap_main",
			  scrollbar: false // 不允许浏览器有滚动条
		});
		
		layer.close(dmanlistindex);
		layer.close(selfMotonAllocindex);
		layer.close(feedbackindex);
	});
}
//订单反馈
function feedback() {
	layui.use('layer', function(){
        feedbackindex = layer.open({
			  type: 2,
			  title: false,// 不需要标题
			  offset: ['55px', '0px'],
			  area: ['100%', '95%'],
			  closeBtn: 0,// 没有关闭按钮
			  shade: 0,// 不想显示遮罩
			  id : 'console_feedbacklist',//不管是什么类型的层，都只允许同时弹出一个
			  move: false,// 来禁止拖拽
			  isOutAnim: false,// 关闭层时会有一个过度动画,如果你不想开启
			  content: "./problemorder/listProblemOrder",
			  scrollbar: false // 不允许浏览器有滚动条
		});

		layer.close(dmanlistindex);
		layer.close(selfMotonAllocindex);
		layer.close(manualAllocationindex);
	});
}

// 实时刷新各个列表的订单数
/*editInterval();
function editInterval() {
	countwaitmanualalloc();
	countautoalloc();
	setInterval(countwaitmanualalloc, TIMING_TIME.CONSOLE_WAIT_MANUAL_ALLOT_COUNT.value);// 5分钟
	setInterval(countautoalloc, TIMING_TIME.CONSOLE_WAIT_MANUAL_ALLOT_COUNT.value);// 5分钟
}*/

// 人工分配单数量查询
function countwaitmanualalloc () {
	$.ajax({
		url : "./console_tab/countWaitManualAlloc",
		data : {
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			$("#manualAllocation").text(data);
		}
	});
}

// 自动分配数量查询
function countautoalloc () {
	$.ajax({
		url : "./console_tab/autoallotcount",
		data : {
		},
		dataType : "json",
		type : "POST",
		success : function(data) {
			$("#selfMotonAlloc").text(data);
		}
	});
}

var webSocket = new WebSocket('ws://'+document.location.host+'/jpoa/websocket');
webSocket.onerror = function(event) {
    onError(event)
};
//打开链接
webSocket.onopen = function(event) {
    onOpen(event)
};
//回调函数
webSocket.onmessage = function(event) {
    onMessage(event)
};
//实例化语音播报
WebSpeech.server = 'http://120.24.87.124/cgi-bin/ekho2.pl';
function onMessage(event) {
	var data = JSON.parse(event.data);
    var feedback = $("#feedback").text();
    var manualAllocation = $("#manualAllocation").text();
    $("#feedback").text(data.proNum);
    $("#manualAllocation").text(data.manualNum);
    $("#selfMotonAlloc").text(data.autoNum);
    if(data.manualNum>Number(manualAllocation)&&data.proNum>Number(feedback)){
        WebSpeech.speak('来新订单和新反馈了');
        return;
	}
    if(data.manualNum>Number(manualAllocation)){
        WebSpeech.speak('来新订单了');
    }
    if(data.proNum>Number(feedback)){
        WebSpeech.speak('订单来新反馈了');
    }
}

function onOpen(event) {
	//发送信息
    var feedback = $("#feedback").text();
    webSocket.send(feedback);
}
//报错
function onError(event) {
}
//关闭窗口，关闭连接
window.onbeforeunload = function () {
	webSocket.close();
}

