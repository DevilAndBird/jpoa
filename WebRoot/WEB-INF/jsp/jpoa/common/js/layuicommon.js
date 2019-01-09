// 时间加载器
function loadtimeinput(selector, format, minscope, maxscope) {
	//日期时间选择器
	layui.use('laydate', function(){
		var laydate = layui.laydate;
		laydate.render({ 
			  elem: selector,
			  type: 'datetime',
			  format : format,
			  value: new Date(),
			  min: minscope,
			  max: maxscope
			});
	});
}