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
				<form action="wxpublicnumconfig/publicNumConfiglistPage" method="post" name="Form" id="Form">
					<table>
						<tr>
							<td> 
								<span class="input-icon">
									<input autocomplete="off" type="text" name="publicnum" value="${pd.publicnum}" placeholder="公众号" />
								</span>
							</td>
							<td> 
								<span class="input-icon">
									<input autocomplete="off" type="text" name="businesskey" value="${pd.businesskey}" placeholder="业务key" />
								</span>
							</td>
							<td style="vertical-align:top;">
								<button style="height: 30px;" class="btn btn-mini btn-light" title="检 索">
									<i id="nav-search-icon" class="icon-search" onclick="search();">检索</i>
								</button>
							</td>
						</tr>
					</table>
					<table id="table_report"
						class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th class="center">序号</th>
								<th class="center">公众号</th>
								<th class="center">业务key</th>
								<th class="center">业务value</th>
								<th class="center">操作</th>
							</tr>
						</thead>
						<tbody>
							<!-- 开始循环 -->
							<c:choose>
								<c:when test="${not empty publicNumConfigList}">
									<c:forEach items="${publicNumConfigList}" var="obj" varStatus="vs">
										<tr>
											<td class="center">${vs.index+1}</td>
											<td class="center publicnum">${obj.publicnum}</td>
											<td class="center businesskey">${obj.businesskey}</td>
											<td class="center businessvalue">${obj.businessvalue}</td>
											<td class="center">
												<input type='hidden' class='publicnumconfigid' value='${obj.id}'>
												<a title="编辑" class='btn btn-mini btn-info' onclick="edit_yes(this);" ><i class='icon-edit'>编辑</i></a>
												<a title="删除" class='btn btn-mini btn-info' onclick="delete_yes(this);" ><i class='icon-edit'>删除</i></a>
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center">没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
					<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td >
									<a title="新增" class="btn btn-small btn-success" onclick="save_yes(this);" >新增</a>
									<a title="刷新缓存" class="btn btn-small btn-success" onclick="refash_publicnum();" >刷新缓存</a>
								</td>
								<td style="vertical-align:top;">
									<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
								</td>
							</tr>
						</table>
					</div>
					
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
         <div class="modal-dialog">
             <div class="modal-content">
                 <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                     <h4 id="h4CancelOrder" class="modal-title">公众号配置信息</h4>
      			</div>
                 <div class="modal-body">
                 	<div id="divmodify">
	                   	<div class="row-fluid">
	                   		<label>公众号</label>
	                   		 <span class="input-icon">
								<input autocomplete="off"  type="text" id='model_publicnum'  placeholder="公众号" />
							 </span>
						</div>
						<div class="row-fluid">
	                   		<label>业务key</label>
	                   		 <span class="input-icon">
								<input autocomplete="off" type="text" id='model_businesskey'  placeholder="业务key" />
							 </span>
						</div>
						<div class="row-fluid">
	                   		<label>业务value</label>
	                   		 <span class="input-icon">
								<input autocomplete="off"  type="text" id='model_businessvalue'  placeholder="业务value" />
							 </span>
						</div>
					</div>
					<div id="divsave">
	                   	<div class="row-fluid">
	                   		<label>公众号</label>
	                   		 <span class="input-icon">
								<input autocomplete="off"  type="text" id='model_publicnum_save'  placeholder="公众号" />
							 </span>
						</div>
						<div class="row-fluid">
	                   		<label>业务key</label>
	                   		 <span class="input-icon">
								<input autocomplete="off" type="text" id='model_businesskey_save'  placeholder="业务key" />
							 </span>
						</div>
						<div class="row-fluid">
	                   		<label>业务value</label>
	                   		 <span class="input-icon">
								<input autocomplete="off"  type="text" id='model_businessvalue_save'  placeholder="业务value" />
							 </span>
						</div>
					</div>
                 </div>
                 <div class="modal-footer">
                      <button id="butmodifyconfig" type="button" class="btn btn-primary">确认修改</button>
                      <button id="butsaveconfig" type="button" class="btn btn-primary">确认保存</button>
                 </div>
        	 </div>
		</div>
		</div>		
		
					
				</form>
			</div>
		</div>
	</div>
	
	<!-- 返回顶部  -->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i
		class="icon-double-angle-up icon-only"></i>
	</a>
	
	
	<script type="text/javascript" src="${ctx}/jpoa/winxin/publicnumconfig/js/publicnumconfig_list.js?r=<%=new Random().nextInt() %>"></script>
</body>
</html>

