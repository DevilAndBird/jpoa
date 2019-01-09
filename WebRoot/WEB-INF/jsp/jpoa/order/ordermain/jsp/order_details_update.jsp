<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
         <div class="modal-dialog">
             <div class="modal-content">
                 <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                     <h4 class="modal-title">编辑</h4>
                 </div>
                 <div class="modal-body">
                 	<div id="divSenderreceiver">
                 		<label for="form-field-8">寄件人</label>
                 		<div class="row-fluid">
							<input type="text" id="sendername_modal" placeholder="寄件人姓名"/> 
						</div>
						<div class="row-fluid">
							<input type="text" id="senderphone_modal" placeholder="寄件人手机号"/> 
						</div>
						<div class="row-fluid">
							<input type="text" id="senderidno_modal" placeholder="寄件人身份证号"/> 
						</div>
						<label for="form-field-8">收件人</label>
						<div class="row-fluid">
							<input type="text" id="receivername_modal" placeholder="收件人姓名"/> 
						</div>
						<div class="row-fluid">
							<input type="text" id="receiverphone_modal" placeholder="收件人手机号"/> 
						</div>
						<div class="row-fluid">
							<input type="text" id="receiveridno_modal" placeholder="收件人身份证号"/> 
						</div>
					</div>
					<div id="divUpdateNotes">
						<div class="row-fluid">
							<label for="form-field-8">备注信息：<input type="text" id="update_notes_modal" placeholder="备注信息"/> </label>
						</div>
					</div>
					<div id="divSaveNotes">
						<div class="row-fluid">
							<label for="form-field-8">备注信息：<input type="text" id="save_notes_modal" placeholder="备注信息"/> </label>
						</div>
					</div>
                 </div>
                 <div class="modal-footer">
                     <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                     <button id="btnUpdateSenderreceiver" type="button" class="btn btn-primary">更改</button>
                     <button id="btnUpdateNotes" type="button" class="btn btn-primary">更改</button>
                     <button id="btnSaveNotes" type="button" class="btn btn-primary">增加</button>
                 </div>
             </div><!-- /.modal-content -->
         </div><!-- /.modal -->
</div>
