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
			<form action="appUser/appUserlistPage" method="post" name="Form" id="Form">
			   <table>
					<tr>
						<td>
							<span class="input-icon">
								<input type="text" name="name" value="${pd.name}" autocomplete="off" placeholder="请输入登陆用户名" />
							</span>
						</td>
						<td>
							<span class="input-icon">
								<input type="text" name="mobile" value="${pd.mobile}" autocomplete="off"  placeholder="请输入手机号" />
							</span>
						</td>
						<td>
							<span class="input-icon">
								<select name="type" id="seleType" data-placeholder="人员类型" style="vertical-align:top;width: 120px;">
									<option value="-1" selected="selected">--人员类型--</option>
								</select>
							</span>
						</td>
					    <td style="vertical-align:top;">
					    	<button class="btn btn-mini btn-light" onclick="search()" title="检 索">
					    		<i id="nav-search-icon" class="icon-search">检 索</i>
					    	</button>
					    </td>
					</tr>
				</table>
			
				<table id="table_report" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="center">序号</th>
							<th class="center">登陆用户名</th>
							<th class="center">密码</th>
							<th class="center">登陆手机号码</th>
							<th class="center">类型</th>
							<th class="center">最近登陆ip</th>
							<th class="center">最近登陆时间</th>
							<th class="center">状态</th>
							<th class="center">添加时间</th>
							<th class="center">修改时间</th>
							<th class="center">操作</th>
						</tr>
					</thead>
					<tbody>
					<!-- 开始循环 -->
					<c:choose>
						<c:when test="${not empty appUserlistPage}">
							<c:forEach items="${appUserlistPage}" var="appUser" varStatus="vs">
								<tr>
									<td class="center">
										${vs.index+1}
										<input type="hidden" value="${appUser.id}"/>
									</td>
									<td class="center">${appUser.name}</td>
									<td class="center">${appUser.password}</td>
									<td class="center">${appUser.mobile}</td>
									<td class="center">
										${appUser.typeDesc}
										<input type="hidden" value="${appUser.type}"/>
									</td>
									<td class="center">${appUser.lastloginip}</td>
									<td class="center">${appUser.lastlogintime}</td>
									<td class="center">
										${appUser.isvalidDesc}
										<input type="hidden" value="${appUser.isvalid}"/>
									</td>
									<td class="center">${appUser.addtime}</td>
									<td class="center">${appUser.modifytime}</td>
									<td class="center">
										<button onclick="fillAppUser_modal(this)" class="btn btn-link" data-toggle="modal" data-target="#myModal" title="编辑登陆用户信息" class="btn btn-link" value="${appUser.id}">编辑登陆用户信息</button>
										<button onclick="lookUserRole(this)" class="btn btn-link">查看角色信息</button>
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
							<td style="vertical-align:top;">
								<a onclick="resetAppUser(this)" class="btn btn-small btn-success" data-toggle="modal" data-target="#myModal" >新增角色登陆信息</a>
							</td>
							<td style="vertical-align:top;">
								<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
							</td>
						</tr>
					</table>
				</div>
				
				<c:import url="./addappuser.jsp"/>
				
			</form>
		</div>
	  </div>
	</div>
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<script type="text/javascript" src="${ctx}/jpoa/common/js/common.js?r=<%=new Random().nextInt() %>"></script>
		 <script type="text/javascript" src="${ctx}/jpoa/common/js/ENUM.js?r=<%=new Random().nextInt() %>"></script>
		 <script type="text/javascript" src="${ctx}/jpoa/delivery/appuser/js/appuser_list.js?r=<%=new Random().nextInt() %>"></script>
		 
		 <script type="text/javascript">
			$(function() {
				appuser_list.init();
			});
			
			/* 查看角色 */
			function lookUserRole(param) {
				appuser_list.lookUserRole(param);
			}
		</script>
		
	</body>
</html>

