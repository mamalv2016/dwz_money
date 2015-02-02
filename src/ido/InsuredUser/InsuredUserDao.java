
package ido.InsuredUser;

import java.util.Collection;

import dwz.dal.BaseDao; 

/**
 * 关于投保用户的数据库操作接口
 * @author www(水清)
 * 任何人和公司可以传播并且修改本程序，但是不得去掉本段声明以及作者署名.
 * http://www.iteye.com
 */ 
public interface InsuredUserDao extends BaseDao<InsuredUserVO, Integer> {
	/**
	 * 根据主键查询全部集合.
	 */
	public Collection<InsuredUserVO> findRecordById(int sno); 
	
	/**
	 * 查询全部集合.
	 */
	public Collection<InsuredUserVO> findAll(); 
	
	/**
	 * 根据条件查询全部集合.
	 */
	public Collection<InsuredUserVO> findParmByType(int paramType);  

	/**
	 * 根据主键删除数据.
	 */
	public void deleteAllById(int sno);

	/**
	 * 根据主键更新数据.
	 */
	public void updateAllaccessInsuredUserVO(InsuredUserVO vo, int sno);
}
