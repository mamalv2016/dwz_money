package querygridtree.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import querygridtree.dao.QueryGridTreeDAO;
import querygridtree.data.GridTreeVO;
import querygridtree.data.QueryGridTreeVO;
import querygridtree.data.TaskVO;

import common.dao.PaginationSupport;
import common.util.CommonUtil;
import common.util.GridTreeUtil;

import dwz.framework.user.User;
import dwz.persistence.beans.SysUser;
import dwz.persistence.beans.UserVO;

public class QueryGridTreeBOImpl implements QueryGridTreeBO {
	public String userValidate(String logingId, String pass) {
		return queryGridTreeDAO.userValidate(logingId,pass);
	}

	private QueryGridTreeDAO queryGridTreeDAO; 

	public void add(QueryGridTreeVO queryGridTreeVO) {
		queryGridTreeDAO.add(queryGridTreeVO);
	}

	public QueryGridTreeDAO getQueryGridTreeDAO() {
		return queryGridTreeDAO;
	}

	public void setQueryGridTreeDAO(QueryGridTreeDAO queryGridTreeDAO) {
		this.queryGridTreeDAO = queryGridTreeDAO;
	}

	public void delete(QueryGridTreeVO queryGridTreeVO) {
		queryGridTreeDAO.del(queryGridTreeVO);
	}

	public String query(QueryGridTreeVO queryGridTreeVO) {
		return null;
	}

	public void update(QueryGridTreeVO queryGridTreeVO) {
		queryGridTreeDAO.upt(queryGridTreeVO);
	}

	public String queryPage(QueryGridTreeVO queryGridTreeVO, int pageSize,
			int page) {
		int startIndex = pageSize * (page - 1) + 1;
		PaginationSupport pg = queryGridTreeDAO.queryPage(queryGridTreeVO,
				pageSize, startIndex);
		return CommonUtil.getGridJsonStr(pg.getItems(), pg.getTotalCount(), pg
				.getCurrentPage());
	}

	public QueryGridTreeVO queryById(String id) {
		return queryGridTreeDAO.queryById(id);
	} 

	public List queryDaparts(UserVO loginUser) {
		if(loginUser!=null)
			return queryGridTreeDAO.queryDapartByUserId("" + loginUser.getUserId());
		else
			return queryGridTreeDAO.queryDapartByUserId("");
	}

	public List queryProjects(UserVO loginUser) {
		if(loginUser!=null)
			return queryGridTreeDAO.queryProjectsByUserId(""
					+ loginUser.getUserId(), 2);
		else
			return queryGridTreeDAO.queryProjectsByUserId("",3);
		
	}

	public List querySubUser(UserVO loginUser) {
		if(loginUser!=null)
			return queryGridTreeDAO
			.querySubUserByUserId("" + loginUser.getUserId());
		else
			return queryGridTreeDAO.querySubUserByUserId("");
		
	}

	public String queryDapartByAreaId(String areaId) {
		return changeListToString(queryGridTreeDAO.queryDapartByAreaId(areaId),true);
	}

	public String queryProjectsByDepartId(String departId) {
		return changeListToString(queryGridTreeDAO
				.queryProjectsByDepartId(departId),false);
	}

	public String queryProjectsByUserId(String userId, int way) {
		return changeListToString(queryGridTreeDAO.queryProjectsByUserId(userId,
				way),true);
	}

	public String querySubUserByUserId(String userId) {
		return changeListToString(queryGridTreeDAO.querySubUserByUserId(userId),false);
	}

	public String queryUsersByDepartAndProject(String departId, String projectId) {
		return changeListToString(queryGridTreeDAO.queryUsersByDepartAndProject(
				departId, projectId),true);
	}

	public String queryUsersByDepartId(String departId) {
		return changeListToString(queryGridTreeDAO.queryUsersByDepartId(departId),true);
	}

	public String queryUsersByProject(String projectId) {
		return changeListToString(queryGridTreeDAO.queryUsersByProject(projectId),true);
	}

	public List queryStatus() {
		return queryGridTreeDAO.queryStatus();
	}

	public String changeDepart(String departId, UserVO user) {
		// �������Ϊ�ǿ�,�Ͳ�ѯ��������������Ŀ����Ա
		if (!"-1".equals(departId))
			return queryProjectsByDepartId(departId) + ","
					+ queryUsersByDepartId(departId);
		// �������û��ѡ��,�Ͳ�ѯĬ�ϵ��û�����Ŀ.
		else {
			return changeListToString(queryProjects(user),false) + ","
					+ changeListToString(querySubUser(user),false);
		}
	}

	public String changeProject(String departId, String projectId, UserVO user) {
		// ������ź���Ŀ���ǿ�,��������������ѯ������������Ա
		if (!"-1".equals(departId) && !"-1".equals(projectId)) {
			return queryUsersByDepartAndProject(departId, projectId);
		}
		// �������Ϊ��,��Ŀ�ǿ�,�Ͳ�ѯ��Ŀ�������Ա
		else if ("-1".equals(departId) && !"-1".equals(projectId)) {
			return queryUsersByProject(projectId);
		}
		// ������ŷǿ�,��ĿΪ��,�Ͳ�ѯ���������ȫ����Ա
		else if (!"-1".equals(departId) && "-1".equals(projectId)) {
			return queryUsersByDepartId(departId);
		}
		// ������ź���Ŀ���ǿ�,�Ͳ�ѯ�¼�Ա��.
		else {
			return changeListToString(querySubUser(user),false);
		}
	} 

	public int countNewFirstLevel(GridTreeVO gridTreeVO) {
		List ans = queryGridTreeDAO.getFirstLevelParentPath(gridTreeVO);
		Set hashSet = new HashSet();
		if (ans != null && ans.size() > 0) {
			Iterator it = ans.iterator();
			// ����õ���һ��Ӧ����ʾ�����ڵ�.
			while (it.hasNext()) {
				String str = ((Object[]) it.next())[0].toString();
				// �õ��ڵڶ����Լ���һ�������.����",-1,1,"����",-1,"����������!���ݷָ����������õ����.
				// ����ֻ�����һ���ǰ׺��hashSet��,��Ϊ��ҳ�ĸ��ݾ��ǵ�һ��!�����ǵڶ���.
				String[] strs = str.split(",");
				hashSet.add(strs[1]);
			}
		}
		return hashSet.size();
	}

	/**
	 * �õ������ѯ������ȫ����parentPath
	 * 
	 * @param gridTreeVO
	 * @return
	 */
	private List getAllParentPath(GridTreeVO gridTreeVO) {
		return queryGridTreeDAO.getFirstLevelParentPath(gridTreeVO);
	}

	/**
	 * ����parent��taskId����ɵ�list,�õ�����taskId�Ƿ���parent 
	 * @param parentPathList
	 * @deprecated
	 */
	private Map getIsParentMap(List parentPathList) {
		// taskId�Ͷ�Ӧ���Ƿ��׽ڵ��ӳ���ϵ,���ؽ��.
		Map ans = new HashMap();
		// ȫ����parenPathȥ���ظ�����
		Set allParentPath = new HashSet();
		Iterator it = parentPathList.iterator();
		while (it.hasNext()) {
			Object[] objs = (Object[]) it.next();
			String _path = (String) objs[0];
			allParentPath.add(_path);
		}
		// ��ȫ���ļ�¼ѭ���õ��Ƿ��Ǹ���.
		it = parentPathList.iterator();
		while (it.hasNext()) {
			Object[] objs = (Object[]) it.next();
			String _path = (String) objs[0];
			String _id = ((BigDecimal) objs[1]).toString();
			ans.put(_id, isParentFilter(allParentPath, _path + _id + ","));
		}
		return ans;
	}

	/**
	 * �õ��Ƿ���.
	 * 
	 * @param allParentPath
	 * @param subParent
	 * @deprecated
	 * @return
	 */
	private String isParentFilter(Set allParentPath, String subParent) {
		Iterator it = allParentPath.iterator();
		while (it.hasNext()) {
			String str = (String) it.next();
			if (str.indexOf(subParent) != -1)
				return "1";
		}
		return "0";
	}

	public String getFirstLevelFinal(GridTreeVO gridTreeVO,
			HttpServletRequest request, int pageSize) {
		// �õ����������Ľ�����ϵ�ȫ����parentPath!!����Ҫ!���ݲ���ܶ�.
		List allParentPath = getAllParentPath(gridTreeVO);
		// ȫ��������Ҫ���taskVO
		List<TaskVO> allTask = new ArrayList<TaskVO>();
		Set firstLevelIds = new LinkedHashSet();
		if (allParentPath != null && allParentPath.size() > 0) {
			Iterator it = allParentPath.iterator();
			// ����õ���һ��Ӧ����ʾ�����ڵ�.��Ҫ��parentPath���д���.
			while (it.hasNext()) {
				Object[] temp = (Object[]) it.next();
				String parentPath = (String) temp[0];
				// �õ��ڵڶ����Լ���һ�������.����",-1,1,"����",-1,"����������!���ݷָ����������õ����.
				// ����ֻ�����һ���ǰ׺��hashSet��,��Ϊ��ҳ�ĸ��ݾ��ǵ�һ��!�����ǵڶ���.
				String[] strs = parentPath.split(",");
				firstLevelIds.add(strs[1]);
			}
		}
		Object[] firstLevelTaskIds = (Object[]) firstLevelIds.toArray();
		// ��һ��ڵ����Ŀ����������.
		int totalNum = firstLevelTaskIds.length;

		// ���ù�����ķ���������ʼ�к���ֹ�У�Ϊǰ���ͺ󿪵ģ���
		int[] rowStartEnd = GridTreeUtil.getStartAndEndInfo(request, totalNum,
				pageSize); 
		// �������ѭ����һ��ĸ����ڵ�,�õ��ӽڵ��з���Ҫ��Ľڵ�.
		for (int i = rowStartEnd[0]-1; i < rowStartEnd[1]-1; i++) {
			String taskId = (String) firstLevelTaskIds[i];
			// �õ���ǰ�ڵ�
			List theNode = queryGridTreeDAO.getTaskById(taskId);
			TaskVO vo = new TaskVO((Object[]) theNode.get(0));
			String parentPath = vo.getTaskParentPath();
			String childPath = parentPath + taskId + ",";

			// ����õ��ɱ��͹�ʱ
			String[] costAndHour = queryGridTreeDAO.getCostAndHour(taskId);
			// ��ʱ
			vo.setAttention(costAndHour[1]);
			// �ɱ�
			vo.setSubCost(costAndHour[0]);
			// ��һ������Ľڵ�϶��Ǹ��׽ڵ�!!��Ϊ�Ǵ�allParentPath�о��������˵�.
			if (isParent(allParentPath, childPath))
				vo.setIsParent("1");
			else
				vo.setIsParent("0");
			// ��ӵ�ǰ�ڵ�.
			allTask.add(vo);
			// �õ��ڶ��㺢�ӽڵ�.
			allTask.addAll(getSubTaskByParentFinal(taskId, gridTreeVO));
		}
		return "{total:" + request.getAttribute("gtcount") + ",page:"
				+ request.getAttribute("gtpage") + ",data:"
				+ GridTreeUtil.getTaskJsonStr(allTask) + "}";
	}

	/**
	 * ��ѯ�ӽڵ��vo����
	 * 
	 * @param pId
	 * @param gridTreeVO
	 * @return
	 */
	private List<TaskVO> getSubTaskByParentFinal(String pId,
			GridTreeVO gridTreeVO) {
		List<TaskVO> ans = new ArrayList<TaskVO>();
		// �õ����ӽڵ�,�Լ����ӽڵ�ȵ�.�������������ӽڵ��parentPath.list�ĳ�Ա��Object,����Obejct����!
		// list����Ķ�����һ��parentPath��taskId����ɵ�object[]
		List allSubParentPath = queryGridTreeDAO.getSubTaskByParent(pId,
				gridTreeVO);
		// �õ�ȫ���Ķ��ӽڵ�.ע��û�а��ղ�ѯ������ѯ!!
		List allSubTaskVOList = queryGridTreeDAO.getSubTaskByParent(pId);
		//Map isParentMap = getIsParentMap(allSubParentPath);
		Iterator it = allSubTaskVOList.iterator();
		while (it.hasNext()) {
			TaskVO vo = new TaskVO((Object[]) it.next());
			String taskId = vo.getTaskId();
			// �����ж�������ӽڵ��Ƿ�Ӧ���Ƿ��صĶ���.
			String parentPath = vo.getTaskParentPath();
			String childPath = parentPath + taskId + ",";
			// ����ͨ���洢���̵õ��ɱ��͹�ʱ
			String[] costAndHour = queryGridTreeDAO.getCostAndHour(taskId);
			// ��ʱ
			vo.setAttention(costAndHour[1]);
			// �ɱ�
			vo.setSubCost(costAndHour[0]);
			// �ж��ǲ���Ӧ�ó����ڶ��ӽڵ���!
			if (shouldReturn(allSubParentPath, vo)) {
				if (isParent(allSubParentPath, childPath)) {
					vo.setIsParent("1");
				} else {
					vo.setIsParent("0");
				}
				ans.add(vo);
			}
		}
		return ans;
	}

	private boolean isParent(List allSubParentPath, String parentPath) {
		Iterator it = allSubParentPath.iterator();
		// ѭ���ҵ���ֻҪ������������parentPath�ͷ���true!
		while (it.hasNext()) {
			Object[] temp = (Object[]) it.next();
			String _path = (String) temp[0];
			// �������·��������,����true.
			if (_path.indexOf(parentPath) != -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �õ��Ƿ�Ӧ�÷���.
	 * 
	 * @param allSubParentPath
	 * @param parentPath
	 * @return
	 */
	private boolean shouldReturn(List allSubParentPath, TaskVO vo) {
		Iterator it = allSubParentPath.iterator();
		String taskId = vo.getTaskId();
		String parentPath = vo.getTaskParentPath();
		String childInPath = parentPath + taskId + ",";
		// ѭ���ҵ���ֻҪ������������parentPath�ͷ���true!
		while (it.hasNext()) {
			Object[] temp = (Object[]) it.next();
			String _path = (String) temp[0];
			String _id = ((BigDecimal) temp[1]).toString();
			// ���taskId���,�Ϳ϶�����true
			if (taskId.equals(_id)) {
				return true;
			} else {
				// �������·��������,Ҳ�϶�����true.
				if (_path.indexOf(childInPath) != -1) {
					return true;
				}
			}
		}
		return false;
	}

	public String getSubTaskByParent(GridTreeVO gridTreeVO, String id) {
		List<TaskVO> ans = getSubTaskByParentFinal(id, gridTreeVO);
		return GridTreeUtil.getTaskJsonStr(ans);
	}
	
	/**
	 * 
	 * @param l Ҫת���ļ���.
	 * @param selectOption  �Ƿ�����ѡ��.
	 * @return
	 */
	private String changeListToString(List l,boolean selectOption){ 
		StringBuffer buf = new StringBuffer(100); 
		buf.append("[");
		if(selectOption){
			buf.append("[\"-1\",\"��ѡ��\"],");
		}
		if(l!=null){
			Iterator it  = l.iterator();
			while(it.hasNext()){
				Object[] o = (Object[])it.next();
				buf.append("[\"").append(o[0]).append("\",\"").append(o[1]).append("\"],");
			}
		}
		if(buf.lastIndexOf(",")!=-1)
		buf = buf.deleteCharAt(buf.lastIndexOf(","));
		buf.append("]"); 
		return buf.toString();
	}

	public String getPeopleByProject(String code) {
		List list = queryGridTreeDAO.getPeopleByProject(code); 
		return changeListToString(list,true);
	}

	public String getProjectByDepart(String code) {
		List list = queryGridTreeDAO.getProjectByDepart(code);
		return changeListToString(list,true);
	}

	public String getProjectAndUserByDepart(String departId) { 
		return "["+getProjectByDepart(departId)+","+queryUsersByDepartId(departId)+"]";
	}

	public User getUser(String userId) {
		SysUser user =  queryGridTreeDAO.getUser(userId);
		return null;
	}
}
