<#include "/com.renjie120.codegenerate.common.ftl">package ${model.packageName};

import dwz.framework.core.business.BusinessObject;

public class ${model.className}Impl implements ${model.className} {
	private ${vo} <@arg nm="${model.className}"/>VO = null;
	private static final long serialVersionUID = 1L;

	public ${model.className}Impl(${vo} orgVO) {
		this.<@arg nm="${model.className}"/>VO = <@arg nm="${model.className}"/>VO;
	}

	public ${model.className}Impl(<@allfield nm=model.attributes />) {
		this.<@arg nm="${model.className}"/>VO = new ${vo}(<@allfield2 nm=model.attributes />);
	} 

	public ${vo} get${model.className}VO() {
		return this.<@arg nm="${model.className}"/>VO;
	}

	public void copyProperties(BusinessObject orig) {

	}

	/**
	 * 返回主键.
	 */
	public ${model.keyType} getId() {
		return this.<@arg nm="${model.className}"/>VO.get${model.keyName?cap_first}();
	} 
	
	<#list model.attributes as attr>
 	/**
 	 * 获取${attr.desc}的属性值.
 	 */
 	public  <@datatype nm="${attr.type}" key="${attr.iskey}"/>   get${attr.name?cap_first}(){
 		return this.<@arg nm="${model.className}"/>VO.get${attr.name?cap_first}();
 	}
 </#list>
 
}