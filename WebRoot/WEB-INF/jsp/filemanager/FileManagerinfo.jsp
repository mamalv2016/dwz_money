
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/include.inc.jsp"%> 
<div class="pageContent">
	<form method="post" action="/money/filemanager!doAdd.do"
		class="pageForm required-validate"
		onsubmit="return myCallback(this, closeDialogWindow);">
		<div class="pageFormContent" layoutH="57">
					 <div class="unit">
						<label>
							文件id:
						</label>
									<input name="fileId" class="textInput required" size="30" type="text"   />
					</div>
					 <div class="unit">
						<label>
							文件名:
						</label>
									<input name="fileName" class="textInput required" size="30" type="text"   />
					</div>
					 <div class="unit">
						<label>
							文件长度:
						</label>
									<input name="fileLen" class="textInput required" size="30" type="text"   />
					</div>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								保存
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								取消
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>