<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
>
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'syspost.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!-- <link rel="stylesheet" href="plugins/zTree/3.5/demoStyle/demo.css" type="text/css">  -->
<link rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="plugins/zTree/3.5/jquery-1.8.3.js"></script>
<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.exedit.js"></script>
<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>

<script type="text/javascript">
	var zTreeNodes;
	var treeid = "null";
	var treefid = "null";
	var treename = "";
	function OnClickztree(event, treeId, treeNode) {
		treefid = treeNode.fid;
		treename = treeNode.name;
		treeid = treeNode.id;
		SelectNodes=treeNode;
	}
	var setting = {
		view : {
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom,
			selectedMulti: false,
			showLine : true
		},
		edit : {
			enable : true,
			showRemoveBtn:true,
			removeTitle : "删除图标",
		/* 	showRenameBtn: true,
			renameTitle : "修改图标" */	
		},
		data : {
			key : {
				name : "name"
			},
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "fid",
				rootId : 0
			}
		},
		callback : {
			onClick : OnClickztree,
			beforeRemove: zTreeBeforeRemove,
			onRemove: zTreeOnRemove,
		}
	};
	function myztree() {
		var zNodes = ${json};
		zTreeNodes = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
	}

	$(document).ready(function() {
		myztree();
	});
  	
	
	function zTreeBeforeRemove(treeId, treeNode) {
		return false;
	}
	
	function zTreeOnRemove(event, treeId, treeNode) {
		alert(treeNode.tId + ", " + treeNode.name);
	}
	
	function addHoverDom(treeId, treeNode) {
		alert(treeNode);
		var aObj = $("#" + treeNode.tId + "_a");
		if ($("#diyBtn_"+treeNode.id).length>0) return;
		var editStr = "<span id='diyBtn_space_" +treeNode.id+ "' > </span>"
			+ "<button type='button' class='diyBtn1' id='diyBtn_" + treeNode.id
			+ "' title='"+treeNode.name+"' onfocus='this.blur();'></button>";
		aObj.append(editStr);
		var btn = $("#diyBtn_"+treeNode.id);
		if (btn) btn.bind("click", function(){alert("diy Button for " + treeNode.name);});
	};
	function removeHoverDom(treeId, treeNode) {
		$("#diyBtn_"+treeNode.id).unbind().remove();
		$("#diyBtn_space_" +treeNode.id).unbind().remove();
	};
		function addztree() {
		if ( treeid=="null" ) {
			alert("请选择节点");
		} else {
			document.getElementById("addText").style.display = "block";
			document.getElementById( "postFid" ).value = treefid;
		}
	}
	
	function deleteztree() {
		if ( treeid=="null" ) {
			alert("请选择节点");
		} else {
			        if (confirm("确认删除"+treename+"么?")) {
			        	if(SelectNodes.isParent){
			        		alert("父节点不可以删除");
			        	}else{			        		
				     location.href="<%=basePath%>sp/godelete.do?postid="+treeid;			        		
			        	}
		            }				 					 
		}
	}
	
	function updateztree() {
		if ( treeid=="null" ) {
			alert("请选择节点");
		} else {
			document.getElementById("updateText").style.display = "block";
			document.getElementById( "postid" ).value = treeid;
			document.getElementById( "editpostName" ).value = treename;
		}
	}
</script>

</head>

<body>
	<div>
		<input type="button" value="新增" onclick="addztree()" /> <input
			type="button" value="修改" onclick="updateztree()" /> <input
			type="button" value="删除" onclick="deleteztree()" />
		<ul id="treeDemo" class="ztree"></ul>
	</div>
	<div id="addText" style="display:none">
	<form action="<%=basePath %>sp/goAdd">
		<input type="text"  id="postName" name="postName" placeholder="这里输入岗位名"/> 
		<input type="hidden" name="postFid" id="postFid">
		<input type="submit" value="新增" />
	</form>
	</div>
	<div id="updateText" style="display:none">
	<form action="<%=basePath %>sp/goUpdate">
		<input type="text"  id="editpostName" name="editpostName" /> 
		<input type="hidden" name="postid" id="postid">
		<input type="submit" value="修改" />
	</form>
	</div>
</body>
</html>
