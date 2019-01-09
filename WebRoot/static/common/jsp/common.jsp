<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.Random"%>
 <c:set var="ctx" value="${pageContext.request.contextPath}"/>

<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"+request.getServerName()+":"+request.getServerPort() + path+"/";
%>



	<base href="<%=basePath%>">
	<meta charset="utf-8" />

	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<!-- v2.0版本的引用方式：src="http://api.map.baidu.com/api?v=2.0&ak=您的密钥" -->
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=j6n1fiPOlBC2j26MjRd4TUrtnXaqSj49"></script>
	<!--加载鼠标绘制工具-->
    <script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
    <link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />
    <!--加载检索信息窗口-->
    <script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.js"></script>
    <link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.css" />

	<!-- jquery.js -->
	<script type="text/javascript" src="static/js/jquery-1.9.1.min.js"></script>
	<!-- jquery-ui -->
	<script type="text/javascript" src="static/js/jquery-ui.min.js"></script>
	<!-- bootstrap.css -->
	<link rel="stylesheet"  href="static/css/bootstrap.min.css" />
	<script type="text/javascript" src="static/js/bootstrap.min.js"></script>
	<!-- 字体图标库 -->
	<link rel="stylesheet" href="static/css/font-awesome.min.css" />
	<!-- ace后台模板 styles,基于bootstrap,jquery -->
	<link rel="stylesheet" href="static/css/ace.min.css" />
	<!-- 操作后提示框 -->
	<link rel="stylesheet" href="static/css/toastr.css" />
	<script type="text/javascript" src="static/js/toastr.js"></script>
	<!-- 下拉框（搜索） -->
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 中国风_时间插件 -->
	<script type="text/javascript" src="static/js/jquery.jedate.js"></script>
	<link rel="stylesheet" type="text/css" href="static/js/jedate.css" />
	<!-- datetimepicker -->
	<script type="text/javascript" src="static/js/bootstrap-datetimepicker.js"></script>
	<link rel="stylesheet" type="text/css" href="static/js/bootstrap-datetimepicker.css" />
	<!-- 时间插件 -->
	<script type="text/javascript" src="static/js/Ecalendar.jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="static/js/reset.css" />
    <link rel="stylesheet" type="text/css" href="static/js/style.css" />
    <!-- layui 框架 -->
    <script type="text/javascript" src="static/js/layer.js"></script>
    <script type="text/javascript" src="static/js/layui/2.3/layui-v2.3.0/layui/layui.js"></script>
    <link rel="stylesheet" type="text/css" href="static/js/layui/2.3/layui-v2.3.0/layui/css/layui.css" />
    <%--语音播报功能--%>
    <script type="text/javascript" src="static/js/WebSpeech.js"></script>