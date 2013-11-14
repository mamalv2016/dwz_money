package common.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * ����ҳ��.
 * @author renjie120
 * connect my:(QQ)1246910068
 *
 */
public class PageBarTag extends BodyTagSupport {
	private String name;
	private int totalCount;
	private int numPerPage;
	private int pageNumShown;
	private int  currentPage;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public int getPageNumShown() {
		return pageNumShown;
	}

	public void setPageNumShown(int pageNumShown) {
		this.pageNumShown = pageNumShown;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * ���캯��.
	 * @coustructor method
	 */
	public PageBarTag() {
		super();
	}

	/**
	 * ��ǩ��������.
	 * @return
	 * @throws JspException
	 */
	public int doEndTag() throws JspException {
		pageContext.setAttribute("pagebar", this);
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
}
