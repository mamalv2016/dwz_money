<#include "/com.renjie120.codegenerate.common.ftl">
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/include.inc.jsp"%>
<%@ page import="${model.packageName}.${model.className}"%>
<% 
	${model.className} vo = (${model.className}) request.getAttribute("vo"); 
%>
<div class="pageContent">
	<form method="post" action="/money/${nm}!doUpdate.do"
		class="pageForm required-validate"
		onsubmit="return myCallback(this, closeDialogWindow);">
		<input type='hidden' name="${model.keyName}"
			value="<s:property value="vo.${model.keyName}"/>">
		<div class="pageFormContent" layoutH="57"> 
			<#list model.attributes as attr> 
				<#if '${attr.name}'!='${model.keyName}'>
					 <div class="unit">
						<label>
							${attr.desc}:
						</label>
						<#if '${attr.type}'='date'>
							<input type="text" name="${attr.name}" class="date <#if "${attr.notnull}"='true'>required</#if>" <#if '${attr.size}'!=''>size="${attr.size}"<#else>size="30"</#if> readOnly="true"  value="<s:property value="vo.${attr.name}"/>" />
							<a class="inputDateButton" href="javascript:;">选择</a>
						<#else>
							<#if '${attr.showType}'='textarea'>
							<textarea  <#if '${attr.size}'!=''>size="${attr.size}"<#else>size="30"</#if>  <#if "${attr.noedit}"='true'>readonly='true'</#if> class="<#if "${attr.notnull}"='true'&&"${attr.noedit}"!='true'>required</#if>" name="${attr.name}" cols="30" rows="2"><s:property value="vo.${attr.name}"/></textarea>
							<#else>
								<#if '${attr.showType}'='select'>
							<my:newselect tagName="${attr.name}"  paraType="${attr.selectCode}" width="100" <#if "${attr.allSelect}"='true'>allSelected="true"</#if> selectedValue="<%=vo.get${attr.name?cap_first}() %>"/>									
								<#else>
									<#if '${attr.showType}'='email'>
							<input name="${attr.name}" <#if '${attr.size}'!=''>size="${attr.size}"<#else>size="30"</#if>  class="email  <#if "${attr.notnull}"='true'&&"${attr.noedit}"!='true'>required</#if>" <#if "${attr.noedit}"='true'>readonly='true'</#if>   type="text"  value="<s:property value="vo.${attr.name}"/>" />
									<#else>
										<#if '${attr.showType}'='digits'>
							<input name="${attr.name}"<#if '${attr.size}'!=''>size="${attr.size}"<#else>size="30"</#if>   class="digits  <#if "${attr.notnull}"='true'&&"${attr.noedit}"!='true'>required</#if>" <#if "${attr.noedit}"='true'>readonly='true'</#if>  type="text"  value="<s:property value="vo.${attr.name}"/>" />
										<#else>
											<#if '${attr.showType}'='number'>
							<input name="${attr.name}" <#if '${attr.size}'!=''>size="${attr.size}"<#else>size="30"</#if>  class="number  <#if "${attr.notnull}"='true'&&"${attr.noedit}"!='true'>required</#if>" <#if "${attr.noedit}"='true'>readonly='true'</#if>   type="text"  value="<s:property value="vo.${attr.name}"/>" />
											<#else>
												<#if '${attr.showType}'='dict'>
							<my:newselect tagName="${attr.name}"   tableName="${attr.fromTable}" nameColumn="${attr.nameColumn}" idColumn ="${attr.idCoulmn}"  width="100"  <#if "${attr.allSelect}"='true'>allSelected="true"</#if> selectedValue="<%=vo.get${attr.name?cap_first}() %>"/>									
												<#else>
							<input name="${attr.name}" <#if '${attr.size}'!=''>size="${attr.size}"<#else>size="30"</#if>  class="textInput  <#if "${attr.notnull}"='true'&&"${attr.noedit}"!='true'>required</#if>" <#if "${attr.noedit}"='true'>readonly='true'</#if>   type="text"  value="<s:property value="vo.${attr.name}"/>" />
												</#if>
											</#if>
										</#if>
									</#if>
								</#if>
							</#if>							
						</#if>
					</div>
				</#if>
			</#list>  
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

