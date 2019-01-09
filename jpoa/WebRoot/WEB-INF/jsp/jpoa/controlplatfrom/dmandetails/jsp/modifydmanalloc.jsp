<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<%@ include file="/static/common/jsp/common.jsp"%>
  
  </head>
  
  <body>
  	<select style="height:35px;width:100% ;margin-top: 9px;" id="seletRoleType" name="seletRoleType" onchange="orderTypeCharge()" title="订单动作" ></select>
	<select style="height:35px;width:100% ;margin-top: 9px;" id="transitCenter" name="transitCenter" title="集散中心"></select>
	<select style="height:35px;width:100% ;margin-top: 9px;" id="serviceCenter" name="serviceCenter" title="柜台中心"></select>
	<div id='divaddr' style="margin-top: 9px;" >
		<div id='addrDetails' style='float: left;width:69% ;'>
        	<input readonly="readonly" style="width:100% ;height: 35px" id="suggestIdshou" name="addr" type="text" placeholder="输入地址" required="required"/>
        </div>
        <div style='float: left;width: 29%;'>
        	<input style="width:100% ;height: 35px" id="hoursenum" name="hoursenum" type="text"  placeholder="门牌号" required="required"/>
   		</div>
    </div>
    
    <button id="editdmanAllocDetails" onclick="editdmanAllocDetails(this)"	class="layui-btn layui-btn-fluid">修改</button>
    
  	<input type='hidden' id ='addrType' value='${pd.addrType}'>
  	<input type='hidden' id ='roletype' value='${pd.roletype}'>
  	<input type='hidden' id ='orderroleid' value='${pd.orderroleid}'>
  	<input type='hidden' id ='orderid' value='${pd.orderid}'>
  </body>
  
  <script type="text/javascript" src="${ctx}/jpoa/common/js/baidu.js?r=<%=new Random().nextInt() %>"></script>
  <script type="text/javascript" src="${ctx}/jpoa/common/js/baseoperation.js?r=<%=new Random().nextInt() %>"></script>
  <script type="text/javascript" src="${ctx}/jpoa/common/js/common.js?r=<%=new Random().nextInt() %>"></script>
  <script type="text/javascript" src="${ctx}/jpoa/common/js/ENUM.js?r=<%=new Random().nextInt() %>"></script>
  <script type="text/javascript" src="${ctx}/jpoa/controlplatfrom/dmandetails/js/modifydmanalloc.js?r=<%=new Random().nextInt() %>"></script>
	 
</html>
