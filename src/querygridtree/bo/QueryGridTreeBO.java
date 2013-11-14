package  querygridtree.bo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import querygridtree.data.GridTreeVO;
import querygridtree.data.QueryGridTreeVO;
import dwz.framework.core.business.BusinessObjectManager;
import dwz.framework.user.User;
import dwz.persistence.beans.UserVO;

public interface QueryGridTreeBO extends BusinessObjectManager{ 
	/**
	 * ����Ԫ��.
	 * @param QueryGridTreeVO
	 * @return
	 */
	public void add(QueryGridTreeVO queryGridTreeVO);
	/**
	 * ɾ��Ԫ��.
	 * @param QueryGridTreeVO
	 * @return
	 */
	public void delete(QueryGridTreeVO queryGridTreeVO);
	
	/**
	 * �޸�Ԫ��.
	 * @param QueryGridTreeVO
	 * @return
	 */
	public void update(QueryGridTreeVO queryGridTreeVO);
	
	/**
	 * ���ز�ѯ�Ľ��json��.
	 * @param QueryGridTreeVO
	 * @return
	 */
	public String query(QueryGridTreeVO queryGridTreeVO);
	
	/**
	 * ������ϸ��Ϣ.
	 * @param id
	 * @return
	 */
	public QueryGridTreeVO queryById(String id);
	
	/**
	 * ���ط�ҳ��ѯsql.
	 * @param QueryGridTreeVO ��ѯ����
	 * @param pageSize ÿҳ��
	 * @param page ҳ��
	 * @return
	 */
	public String queryPage(QueryGridTreeVO queryGridTreeVO,int pageSize,int page);
	
	/**
	 * ������Աid��ѯ��Ŀ�б�.
	 * @param userId
	 * @param way 1:��ѯ�������ڲ����������Ŀ;2:��ѯ�����������������ȫ�����ŵ���Ŀ.
	 * @return
	 */
	public String queryProjectsByUserId(String userId,int way); 
	
	/**
	 * ��ѯ��¼��Ա������Ա��(������).
	 * @param userId 
	 * @return
	 */
	public String querySubUserByUserId(String userId); 
	
	/**
	 * ��������id����������Ĳ���.
	 * @param areaId
	 * @return
	 */
	public List queryDaparts(UserVO loginUser); 
	
	/**
	 * ������Աid��ѯ��Ŀ�б�.
	 * @param userId
	 * @param way 1:��ѯ�������ڲ����������Ŀ;2:��ѯ�����������������ȫ�����ŵ���Ŀ.
	 * @return
	 */
	public List queryProjects(UserVO loginUser); 
	
	/**
	 * ��ѯȫ����״̬.
	 * @return
	 */
	public List queryStatus(); 
	
	/**
	 * ��ѯ��¼��Ա������Ա��(������).
	 * @param userId 
	 * @return
	 */
	public List querySubUser(UserVO loginUser); 
	 
	/**
	 * ��ѯ�����������Ŀ����Ա.
	 * @param departId
	 * @return
	 */
	public String getProjectAndUserByDepart(String departId);   
	
	/**
	 * ��ѯ�����������Ա.
	 * @param departId
	 * @return
	 */
	public String queryUsersByDepartId(String departId); 
	
	/**
	 * ��ѯָ�����������ָ����Ŀ�Ĳ�����Ա.
	 * @param departId
	 * @param projectId
	 * @return
	 */
	public String queryUsersByDepartAndProject(String departId,String projectId); 
	
	 /**
	 * ���������ķ���.
	 * @param deaprtId
	 * @param user
	 * @return
	 */
	public String changeDepart(String departId,UserVO user) ;
	
	/**
	 * ��Ŀ�����ķ���.
	 * @param deaprtId
	 * @param projectId
	 * @param user
	 * @return
	 */
	public String changeProject(String departId,String projectId,UserVO user) ;
 
	public String getFirstLevelFinal(GridTreeVO gridTreeVO ,HttpServletRequest request,int pageSize) ;
	 
	
	/**
	 * �������Ĳ�ѯ������ĵ�һ�����ڵ�.
	 * @param gridTreeVO
	 * @return
	 */
	public int countNewFirstLevel(GridTreeVO gridTreeVO) ;
	public String getSubTaskByParent(GridTreeVO gridTreeVO,String pId);
	
	/**
	 * ������Ŀ������Ա
	 * @param code
	 * @return
	 */
	public String getProjectByDepart(String code);
	
	/**
	 * �������Ŵ�����Ŀ
	 * @param code
	 * @return
	 */
	public String getPeopleByProject(String code);
	
	/**
	 * ��֤��½,�����û�id.
	 * @param logingId
	 * @param pass
	 * @return
	 */
	public String userValidate(String logingId,String pass);
	
	/**
	 * �����û�����.
	 * @param userId
	 * @return
	 */
	public User getUser(String userId);
}
