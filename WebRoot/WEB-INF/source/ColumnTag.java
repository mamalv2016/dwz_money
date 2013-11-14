package common.taglib;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * ����б�ǩ.
 * @author renjie120
 * connect my:(QQ)1246910068
 *
 */
public class ColumnTag extends BodyTagSupport {
	private String name;
	private String header;
	private String width;
	private String style;
	private String value;
	private String tableId;
	/**
	 * ���캯��.
	 * @coustructor method
	 */
	public ColumnTag() {
		super();
	}

	/**
	 * ��ǩ��������.
	 * @return
	 * @throws JspException
	 */
	public int doEndTag() throws JspException {  
		//�ҵ������ǩ�ĸ��ױ�ǩ��
		TableTag table = (TableTag)findAncestorWithClass(this, TableTag.class);
		List columns = (List)pageContext.getAttribute("columns");
		if(columns==null){
			columns = new ArrayList(); 
		}
		Column c = new Column();
		c.setHeader(getHeader());
		c.setName(getName());
		c.setStyle(getStyle());
		c.setValue(getValue());
		c.setWidth(getWidth());
		columns.add(c);
		pageContext.setAttribute("columns", columns);
		return super.doEndTag();
	} 
	/**
	 * ��ǩ��ʼ����.
	 * @return
	 * @throws JspException
	 */
	public int doStartTag() throws JspException { 
		return super.doStartTag();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
