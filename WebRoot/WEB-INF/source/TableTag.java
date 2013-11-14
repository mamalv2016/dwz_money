package common.taglib;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.BeanUtils;

import common.util.CommonUtil;

/**
 * ��������ǩ.
 * 
 * @author renjie120 connect my:(QQ)1246910068
 * 
 */
public class TableTag extends TagSupport {
	private String width;
	private String layoutH;
	private String id;
	private List list;
	private String toolBar;
	private String tableContent;
	private String pageBar;

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getLayoutH() {
		return layoutH;
	}

	public void setLayoutH(String layoutH) {
		this.layoutH = layoutH;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	/**
	 * ���캯��.
	 * 
	 * @coustructor method
	 */
	public TableTag() {
		super();
	}

	/**
	 * ��ǩ��������.
	 * 
	 * @return
	 * @throws JspException
	 */
	public int doEndTag() throws JspException {
		try {
			System.out.println(end());
			pageContext.getOut().write(end());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}

	private synchronized String end() {
		StringBuffer buf = new StringBuffer(200);
		buf.append(getHeader());
		buf.append(getBody());		
		buf.append("</table>"); 
		buf.append(getPagebar());
		buf.append("</div>");
		return buf.toString();
	}

	private String getHeader() {
		StringBuffer buf = new StringBuffer(200);
		buf.append("<thead><tr>");
		List columns = (List) pageContext.getAttribute("columns");
		if (columns != null) {
			Iterator it = columns.iterator();
			while (it.hasNext()) {
				Column c = (Column) it.next();
				buf.append("<th");
				if (!CommonUtil.isBlank(c.getWidth())) {
					buf.append(" width=\"" + c.getWidth() + "\"");
				}
				buf.append(">");
				buf.append(c.getHeader());
				buf.append("</th>");
			}
		}
		buf.append("</tr></thead>");
		return buf.toString();
	}

	private String getPagebar() {
		StringBuffer buf = new StringBuffer(200);
		PageBarTag pagebar = (PageBarTag) pageContext.getAttribute("pagebar");
		if(pagebar.getCurrentPage()==0)
			pagebar.setCurrentPage(1);
		if(pagebar.getNumPerPage()==0)
			pagebar.setNumPerPage(20);
		if(pagebar.getCurrentPage()==0)
			pagebar.setCurrentPage(1);
		if(pagebar.getPageNumShown()==0)
			pagebar.setPageNumShown(20);
		buf.append("<div class=\"panelBar\"><DIV CLASS='pages'><span>��ʾ</span>");
		buf.append("<select class=\"combox\" name=\"numPerPage\" onchange=\"navTabPageBreak({numPerPage:this.value})\">");
		buf.append("<option value=\"20\">20</option>");
		buf.append("<option value=\"50\">50</option>");
		buf.append("<option value=\"100\">100</option>");
		buf.append("<option value=\"200\">200</option></select>");
		buf.append("<span>������${totalCount}��</span></div>");
		buf.append("<div class=\"pagination\" targetType=\"navTab\" totalCount=\""+pagebar.getCurrentPage()+"\" numPerPage=\""+pagebar.getNumPerPage()+"\" pageNumShown=\""+pagebar.getPageNumShown()+"\" currentPage=\""+pagebar.getCurrentPage()+"\"></div>");
		buf.append("</div>");
		return buf.toString();
	}
	
	private String getBody() {
		StringBuffer buf = new StringBuffer(200);
		buf.append("<tbody>");
		List values = new ArrayList();
		List columns = (List) pageContext.getAttribute("columns");
		if (columns != null) {
			Iterator it = columns.iterator();
			while (it.hasNext()) {
				Column c = (Column) it.next();
				values.add(c.getValue());
			}
		}

		try {
			if (list != null) {  
				Iterator it = list.iterator();
				while (it.hasNext()) {
					buf.append("<tr>");
					Object o = it.next();
					for (int i = 0; i < values.size(); i++) {
						buf.append("<td>").append(BeanUtils.getProperty(o,(String) values.get(i))).append("</td>");
					}
					buf.append("</tr>");
				}
			}
		} catch (IllegalAccessException e) { 
			e.printStackTrace();
		} catch (InvocationTargetException e) { 
			e.printStackTrace();
		} catch (NoSuchMethodException e) { 
			e.printStackTrace();
		} 
		buf.append("</tbody>");
		return buf.toString();
	}

	/**
	 * ��ǩ��ʼ����.
	 * 
	 * @return
	 * @throws JspException
	 */
	public int doStartTag() throws JspException {
		try {
			if (CommonUtil.isBlank(layoutH)) {
				layoutH = "300";
			}
			if (CommonUtil.isBlank(width)) {
				width = "100%";
			}
			pageContext.getOut().write(
					"<div class=\"pageContent\"><table width=\"" + width
							+ "\" layoutH=\"" + layoutH
							+ "\"  class=\"table\">");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ��ʾ�м��Ԫ�ػ�Ҫ������ʾ
		return 1;
	}

	public String getToolBar() {
		return toolBar;
	}

	public void setToolBar(String toolBar) {
		this.toolBar = toolBar;
	}

	public String getTableContent() {
		return tableContent;
	}

	public void setTableContent(String tableContent) {
		this.tableContent = tableContent;
	}

	public String getPageBar() {
		return pageBar;
	}

	public void setPageBar(String pageBar) {
		this.pageBar = pageBar;
	}

}
