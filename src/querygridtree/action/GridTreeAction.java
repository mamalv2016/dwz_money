package querygridtree.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import querygridtree.bo.QueryGridTreeBO;
import querygridtree.data.GridTreeVO;
import testGridTree.GridTreeUtil;

import com.opensymphony.xwork2.ModelDriven;

import dwz.constants.BeanManagerKey;
import dwz.framework.core.factory.BusinessFactory;
import dwz.present.BaseAction;

/**
 * ��ѯ�����ʾ��.
 * @author lsq
 * 
 */
public class GridTreeAction extends BaseAction implements
		ModelDriven<GridTreeVO> {
	
	// ÿҳ����
	private final Log logger = LogFactory.getLog(getClass());
	private static int DEFAULT_PAGE_SIZE = 1;
	private GridTreeVO gridTreeVO = new GridTreeVO();
	private QueryGridTreeBO queryGridTreeBO;

	public GridTreeVO getModel() {
		return gridTreeVO;
	}
 
	/**
	 * ��ʼ����ѯ�˵���������.
	 * @return
	 */
	public String initGridTree() {
		List list = new ArrayList();
		// �������
		int totalNum = 0;
		int[] rowStartEnd;
		int len = 0;
		try {
			/*
			totalNum = dao.getFirstLevelCount(); 
			// ���ù�����ķ���������ʼ�к���ֹ�У�Ϊǰ���ͺ󿪵ģ���
			rowStartEnd = GridTreeUtil.getStartAndEndInfo(request, totalNum,
					DEFAULT_PAGE_SIZE);
			list = dao.getList(rowStartEnd[0], rowStartEnd[1]);

			// ���ù�����ķ����õ�json�ַ�����
			String jsonStr = GridTreeUtil.getJsonStr(list, request);
			*/
			
			
			StringBuffer buf  =new StringBuffer();
			//��һ��:��һ���������.
			totalNum = 10;
			//�ڶ���:��ʾ����ʼ��,�ͽ�����.
			//�ڵ����ҳ��ť��ʱ��,�ᴫ��gtstart��������̨.
			rowStartEnd = GridTreeUtil.getStartAndEndInfo(request, totalNum,
					DEFAULT_PAGE_SIZE);	
			//������:��ѯlist--demoʡ��.
			
			//���Ĳ�:�γ�json��.------������ģ���json
			int n = 1;
			if (request.getParameter("gtpage") != null)  
				n = Integer.parseInt(request.getParameter("gtpage"));
			String page  ="--���Ե�"+n+"ҳ";	
			buf.append("{total:"+ request.getAttribute("gtcount") + ",page:"+ request.getAttribute("gtpage")+",");
			buf.append(" data:[{\"disid\":\"10\",\"disparentId\":\"\",\"disname\":\"����"+page+"\",\"isLeaf\":\"1\"},"); 
			buf.append(" {\"disid\":\"11\",\"disparentId\":\"\",\"disname\":\"��ǿ���ʲ"+page+"\",\"isLeaf\":\"0\"},      ");	
			buf.append(" {\"disid\":\"12\",\"disparentId\":\"\",\"disname\":\"���"+page+"\",\"isLeaf\":\"1\"}]} ");

			String jsonStr = buf.toString();
			response.setContentType("text/html; charset=UTF-8");
			System.out.println("json��:" + jsonStr);
			PrintWriter out = response.getWriter();
			out.println(jsonStr); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��������ʾ�ӽڵ�.
	 * @return
	 */
	public String lazyLoad() {   
		String parentId = request.getParameter("pId");
    	//GridTreeDao dao = new GridTreeDao();
		List list = new ArrayList();		
		try {
			/*
			//��ѯ�ӽڵ㼯��
			list = dao.getListByParent(parentId);
			// ���ù�����ķ����õ�json�ַ�����
			String jsonStr = GridTreeUtil.getJsonStr(list);
			*/
			StringBuffer buf  =new StringBuffer();
			String page  ="--��"+parentId+"�ĺ���...";	
			buf.append("[");
			buf.append("{\"disid\":\"10"+parentId+"\",\"disparentId\":\""+parentId+"\",\"disname\":\"����10"+page+"\",\"isLeaf\":\"1\"},");
			buf.append(" {\"disid\":\"12"+parentId+"\",\"disparentId\":\""+parentId+"\",\"disname\":\"����12"+page+"\",\"isLeaf\":\"0\"},  ");	
			buf.append(" {\"disid\":\"13"+parentId+"\",\"disparentId\":\""+parentId+"\",\"disname\":\"����13"+page+"\",\"isLeaf\":\"0\"}, ");
			buf.append("{\"disid\":\"14"+parentId+"\",\"disparentId\":\""+parentId+"\",\"disname\":\"����14"+page+"\",\"isLeaf\":\"1\"},");
			buf.append(" {\"disid\":\"15"+parentId+"\",\"disparentId\":\""+parentId+"\",\"disname\":\"����15"+page+"\",\"isLeaf\":\"0\"} ");	
			buf.append("]");
			
			String jsonStr = buf.toString();
			response.setContentType("text/html; charset=UTF-8");
			System.out.println("�������Ӵ�:"+jsonStr);
			PrintWriter out = response.getWriter();
			out.println(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return null;
	}

	public QueryGridTreeBO getQueryGridTreeBO() {
		return queryGridTreeBO;
	}

	public void setQueryGridTreeBO(QueryGridTreeBO queryGridTreeBO) {
		this.queryGridTreeBO = queryGridTreeBO;
	}
}
