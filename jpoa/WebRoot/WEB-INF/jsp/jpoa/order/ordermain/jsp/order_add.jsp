<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/static/common/jsp/common.jsp"%>
</head>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<table>
				     <tr >
				     	<td>
				     		<h1>
				     			<span class="label label-default">选择寄送件地址</span>
				     		</h1>
				     	</td>
				     </tr>
				     
                     <tr>
						<td style="vertical-align:top;"> 
						 	<select name="provid" id="seleProv" data-placeholder="省份" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--省份--</option>
								<c:forEach items="${provList}" var="obj" varStatus="vs">
									<option value="${obj.id}" <c:if test="${obj.id==pd.provid}">selected</c:if>>${obj.name }</option>
								</c:forEach>
						  	</select>
						  	 	<select name="cityid" id="seleCity" data-placeholder="" style="vertical-align:top;width: 120px;">
								<option value="-1" selected="selected">--城市--</option>
						  	</select>
						</td>
					</tr>
					
					<tr>
					   <td><span class="input-icon">		     
			        	   <select data-placeholder="出发地类型" style="vertical-align:top;width: 120px;" name="srctype" id="srctype">
								<option value="-1" selected="selected">---出发地类型---</option>
								<option value="HOTEL" >酒店</option>
								<option value="RESIDENCE" >住宅</option>
					     		<option value="AIRPORTCOUNTER">机场</option>
					  	   </select></span>
				       </td>	
					</tr>
					<tr>
					    <td>
						     <div id='divaddr' style="float: left;">
								 <div class="col-sm-8" id="r-result">
						            <input style="width: 300px;height: 30px" id="srcaddress" name="srcaddress" type="text" class="form-control" placeholder="街道详细（精确到门牌号） " required="required">
					        	 </div>
						         <div id="srcaddressshow" style="border: 1px solid #C0C0C0; width: 150px; height: auto; display: none;"></div>
						         <div id="l-map"></div>
					         </div>
				    		<select id="srcserviceCenter" name="srcserviceCenter" title="柜台中心"></select>
					    </td>
							
						<td>
							<span class="input-icon">
								<input type="text" name="taketime" id="taketime" autocomplete="off" placeholder="请输入寄件时间" class='ECalendar' />
							</span>
						</td>
					 </tr>
				
					<tr>
				      <td>
				        	<select data-placeholder="目的地类型" style="vertical-align:top;width: 120px;" name="desttype" id="desttype"> 
								<option value="-1" selected="selected">---目的地类型---</option>
							<option value="HOTEL" >酒店</option>
								<option value="RESIDENCE" >住宅</option>
					     		<option value="AIRPORTCOUNTER">机场</option>
						  	</select>
				      </td>
				
					<tr>
					    <td>
				    		 <div id='divaddr' style="float: left;">
								 <div class="col-sm-8" id="r-result">
						            <input style="width: 300px;height: 30px" id="destaddress" name="destaddress" type="text" class="form-control" placeholder="街道详细（精确到门牌号） " required="required">
					        	 </div>
						         <div id="destaddressshow" style="border: 1px solid #C0C0C0; width: 150px; height: auto; display: none;"></div>
						         <div id="l-map"></div>
					         </div>
				    		<select id="destserviceCenter" name="destserviceCenter" title="柜台中心"></select>
					    </td>
		              	<td>
		              		<span class="input-icon">
		              			<input type="text" name="sendtime" id="sendtime" autocomplete="off" placeholder="请输入领回时间" class='ECalendar' />
		              		</span>
		              	</td>
					 </tr>
					 <tr>
					
					 </tr>
					 <tr ><td>
				        <h1><span class="label label-default">航班信息</span></h1>
				     </td></tr>
				     	  <tr>
				      <td><span class="input-icon"> <input	autocomplete="off" type="text" name="flightno" id="flightno" placeholder="请输入航班号" />
							</span></td>
				    </tr>
					 <tr ><td>
				     <h1><span class="label label-default">客户信息</span></h1>
				     </td></tr>
					  <tr>
				      <td><span class="input-icon"> <input	autocomplete="off" type="text" name="cusid" id="cusid" placeholder="请输入客户id" />
							</span></td>
					  <td><span class="input-icon"> <input	autocomplete="off" type="text" name="cusname" id="cusname" placeholder="请输入客户姓名" />
							</span></td>
					 <td><span class="input-icon"> <input	autocomplete="off" type="text" name="cusmobile" id="cusmobile" placeholder="请输入客户手机号码" />
							</span></td>
				    </tr>
					    <tr ><td>
				     <h1><span class="label label-default">选择保险</span></h1>
				     </td></tr>
					 <tr>
					  <td>				     
				        	<select data-placeholder="保险" style="vertical-align:top;width: 120px;" id="insurance">
								<option value="0" selected="selected">0元(保价1000)</option>
								<option value="5" >5元(保价2000)</option>
					     		<option value="10">10元(保价3000)</option>
							   <option value="15">15元(保价4000)</option>
							   <option value="20" >20元(保价5000)</option>
						  	</select>
				     </td>
					 </tr>
					  <tr>
					    <td><span class="input-icon"> <input	autocomplete="off" type="text" name="num" id="num"  placeholder="请输入行李数量" />
							</span>
						</td>
					 </tr>
					 <tr>
					    <td>
					       <h1><span class="label label-default">预估价格</span></h1>
					    </td>
					  </tr>
					    <tr>
					    <td><span class="input-icon"> <input	autocomplete="off" type="text" name="totalmoney" id="totalmoney"  placeholder="预估价格" />
							</span>
						</td>
					 </tr>
					<tr  id="mailingway_msg" style="display:none">
					<td>
				     <h1><span class="label label-default">寄件方式</span></h1>
				     </td>
				     </tr>
				     <tr  id ="mailingway_div" style="display:none">
				         <td>				     
				        	<select data-placeholder="寄件方式" style="vertical-align:top;width: 120px;" id="mailingway" name="mailingway">
						  	</select>
				     </td>
				     </tr>
				     <tr id="real_div" style="display:none">
				    <td><span class="input-icon"> <input	autocomplete="off" type="text" name="porderno" id="realname" placeholder="请输入待寄人姓名" />
							</span></td>
				     <td><span class="input-icon"> <input	autocomplete="off" type="text" name="porderno" id="realphone" placeholder="请输入待寄人手机号码" />
							</span></td>
				     </tr>
				    <tr id="send_div"  style="display:none">
				    <td><span class="input-icon"> <input	autocomplete="off" type="text" name="sendername" id="sendername" placeholder="请输入寄件人姓名" />
							</span></td>
				     <td><span class="input-icon"> <input	autocomplete="off" type="text" name="senderphone" id="senderphone" placeholder="请输入寄件人手机号码" />
							</span></td>
				    </tr>
				   <tr  id="backway_msg"  style="display:none">
				          <td>				          
				           <h1><span class="label label-default">领回方式</span></h1>
				          </td>
				     </tr>
				     <tr id="backway_div"  style="display:none">
				         <td>				     
				        	<select data-placeholder="领回方式" style="vertical-align:top;width: 120px;" id="backway">
						  	</select>
				     </tr>
				    <tr id="back_div" style="display:none">
				    <td><span class="input-icon"> <input	autocomplete="off" type="text" name="receivername" id="receivername" placeholder="请输入收件人姓名" />
							</span></td>
				     <td><span class="input-icon"> <input	autocomplete="off" type="text" name="receiverphone" id="receiverphone" placeholder="请输入收件人手机号码" />
							</span></td>
				     </tr>
				    <tr id="realb_div"  style="display:none"> 
				    <td><span class="input-icon"> <input	autocomplete="off" type="text" name="realbname" id="realbname" placeholder="请输入代收人姓名" />
							</span></td>
				     <td><span class="input-icon"> <input	autocomplete="off" type="text" name="realbphone" id="realbphone" placeholder="请输入代收人手机号码" />
							</span></td>
				    </tr>
				      <tr>
					    <td>
					       <h1><span class="label label-default">备注</span></h1>
					    </td>
					  </tr>
					  <tr>
					    <td><span class="input-icon"> <input	autocomplete="off" type="text" name="notes" id="notes" placeholder="请输入备注" />
					 		</span>
					    </td>
					 </tr>
				     <tr>
				      <td style="vertical-align:top;">
							<a id = "btnSaveOrderMain" onclick="btnSaveOrderMain(WAITPPAY)" class="btn btn-small btn-success">保存</a>
							<a id="btnSaveOrderPayMain" class="btn btn-small btn-success">保存并支付</a>
					  </td>
				     </tr>
				</table>
			</div>
		</div>
	</div>
	<!-- 返回顶部  -->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i
		class="icon-double-angle-up icon-only"></i>
	</a>
	
	<script type="text/javascript" src="${ctx}/jpoa/common/js/baidu.js?r=<%=new Random().nextInt() %>"></script>
	<script type="text/javascript" src="${ctx}/jpoa/common/js/baseoperation.js?r=<%=new Random().nextInt() %>"></script>
	<script type="text/javascript" src="${ctx}/jpoa/common/js/common.js?r=<%=new Random().nextInt() %>"></script>
	<script type="text/javascript" src="${ctx}/jpoa/common/js/ENUM.js?r=<%=new Random().nextInt() %>"></script>
	<script type="text/javascript" src="${ctx}/jpoa/order/ordermain/js/order_add.js?r=<%=new Random().nextInt() %>"></script>

</body>
</html>

