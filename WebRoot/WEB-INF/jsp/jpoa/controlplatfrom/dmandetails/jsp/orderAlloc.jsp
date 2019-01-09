<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 模态框（Modal） -->
		<div class="modal fade" id="orderAlloc" tabindex="-1" data-backdrop="true" style="width: 1000px;margin-right: 500px; ">
	         <div class="modal-dialog">
	             <div class="modal-content" >
	                 <div class="modal-header">
	                 	 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                 	 <div id= "divshow">
	                 	 	<h4 class="modal-title">订单分配</h4>
	                    	<div id = "divdmanAllocDetails"></div>
	                 	 </div>
	                 </div>
	                 <div class="modal-body" style=" max-height:700px;overflow-y:auto;overflow-x:hidden;">
	                 	
	                 	<div id = "divchangeallocdman" ></div>
	                    </div>
	                    
	                    <button  id="ichangedman" class="layui-btn layui-btn-radius">确认更改取派员</button>
	                    
	                 <div class="modal-footer">
	                 	<input type ='hidden' id= 'inp_Alloc_orderid'/>
	                 	<input type ='hidden' id= 'changedmanuserid'/>
	                 	<input type ='hidden' id= 'taskid'/>
	                 	<input type ='hidden' id= 'sendid'/>
	                 </div>
	             </div><!-- /.modal-content -->	
	         </div><!-- /.modal -->
		</div>
	

		