<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
	<head>
	     <%@ include file="/static/common/jsp/common.jsp"%>
	</head>
<body>
		
<div class="container-fluid" id="main-container">
<div id="page-content" class="clearfix">
  <div class="row-fluid">
	<div class="row-fluid">
		<!-- 检索  -->
		<form action="cusInvoice/list.do" method="post" name="Form" id="Form">
			<table>
				<tr>
					<td>
						<input autocomplete="off" type="text" name="title" id="title" value="${pd.title }" placeholder="抬头" style="width:174px;"/>
					</td>
					<td>
						<input autocomplete="off" type="text" name="cusname" id="cusname" value="${pd.cusname }" placeholder="客户名称" />
					</td>
					<td>
						<input autocomplete="off" type="text" name="mobile" id="mobile" value="${pd.mobile }" placeholder="客户电话" />
					</td>
					<td>
						<input autocomplete="off" type="text" name="sendname" id="sendname" value="${pd.sendname }" placeholder="收件人姓名" />
					</td>
					<td>
						<input autocomplete="off" type="text" name="sendphone" id="sendphone" value="${pd.sendphone }" placeholder="收件人手机号" />
					</td>
					<td>
						<input autocomplete="off" type="text" name="starttime" id="starttime" value="${pd.starttime }" placeholder="开始时间" />
					</td>
					<td>
						<input autocomplete="off" type="text" name="endtime" id="endtime" value="${pd.endtime }" placeholder="结束时间" />
					</td>
					<td style="vertical-align:top;">
						<button style='height: 30px;' class="btn btn-mini btn-light" onclick="search();" title="检索"><i id="nav-search-icon" class="icon-search">检 索</i></button>
					</td>
				</tr>
			</table>
			<!-- 检索  -->
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th>序号</th>
						<th>订单号</th>
						<th>客户名称</th>
						<th>手机号码</th>
						<th>发票类型</th>
						<th>金额</th>
						<th>抬头</th>
						<th>税号</th>
						<th>收件人姓名</th>
						<th>收件人手机号</th>
						<th>寄送地址</th>
						<th>添加时间</th>
					</tr>
				</thead>
				<tbody>
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty cusInvoiceList}">
						<c:forEach items="${cusInvoiceList}" var="obj" varStatus="vs">
							<tr>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<td>${obj.orderno }</td>
								<td>${obj.cusname }</td>
								<td>${obj.mobile }</td>
								<td>${obj.invoicetypedesc }</td>
								<td>${obj.money }</td>
								<td>${obj.title }</td>
								<td>${obj.taxno }</td>
								<td>${obj.sendname }</td>
								<td>${obj.sendphone }</td>
								<td>${obj.sendaddr }${obj.housenum}</td>
								<td><fmt:formatDate value="${obj.addtime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="10" class="center">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
			<div class="page-header position-relative">
				<table style="width:100%;">
					<tr>
						<td style="vertical-align:top;">
							<a onclick="toexcel()" class="btn btn-small btn-success" data-toggle="modal" data-target="#myModal" >下载</a>
						</td>
						<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
					</tr>
				</table>
			</div>
			
			<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
         <div class="modal-dialog">
             <div class="modal-content">
                 <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                     <h4 id="h4DeliveryMan" class="modal-title">确认下载？</h4>
                 </div>
                 <div class="modal-body">
                 </div>
                 <div class="modal-footer">
                     <button onclick="btnDownloadInvoice()" type="button" class="btn btn-primary">下载</button>
                 </div>
             </div><!-- /.modal-content -->
         </div><!-- /.modal -->
		</div>
		
		</form>
	</div>
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<script type="text/javascript">
			
			 $('#starttime').datepicker({
					autoclose: true, //自动关闭
					beforeShowDay: $.noop,    //在显示日期之前调用的函数
					calendarWeeks: false,     //是否显示今年是第几周
					clearBtn: false,          //显示清除按钮
					daysOfWeekDisabled: [],   //星期几不可选
					endDate: Infinity,        //日历结束日期
					forceParse: true,         //是否强制转换不符合格式的字符串
					format: 'yyyy-mm-dd',     //日期格式
					keyboardNavigation: true, //是否显示箭头导航
					language: 'cn',           //语言
					minViewMode: 0,
					orientation: "auto",      //方向
					rtl: false,
					startDate: -Infinity,     //日历开始日期
					startView: 0,             //开始显示
					todayBtn: false,          //今天按钮
					todayHighlight: false,    //今天高亮
					weekStart: 0              //星期几是开始
				});
			 
			 $('#endtime').datepicker({
					autoclose: true, //自动关闭
					beforeShowDay: $.noop,    //在显示日期之前调用的函数
					calendarWeeks: false,     //是否显示今年是第几周
					clearBtn: false,          //显示清除按钮
					daysOfWeekDisabled: [],   //星期几不可选
					endDate: Infinity,        //日历结束日期
					forceParse: true,         //是否强制转换不符合格式的字符串
					format: 'yyyy-mm-dd',     //日期格式
					keyboardNavigation: true, //是否显示箭头导航
					language: 'cn',           //语言
					minViewMode: 0,
					orientation: "auto",      //方向
					rtl: false,
					startDate: -Infinity,     //日历开始日期
					startView: 0,             //开始显示
					todayBtn: false,          //今天按钮
					todayHighlight: false,    //今天高亮
					weekStart: 0              //星期几是开始
				});
			 
			 function search() {
				 var url = "./cusInvoice/list.do";
				 $("#Form").attr("action", url);
				 $("#Form").submit();
			 }
			 
			 function btnDownloadInvoice() {
				 var url = "./cusInvoice/outExcel.do";
				 $("#Form").attr("action", url);
				 $("#Form").submit();
			 }
			
		</script>
	</body>
</html>

