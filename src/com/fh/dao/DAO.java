package com.fh.dao;

public interface DAO {
	
	/**
	 * 保存对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object save(String str, Object obj) throws Exception; 
	
	/**
	 * 修改对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object update(String str, Object obj) throws Exception;
	
	/**
	 * 删除对象 
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object delete(String str, Object obj) throws Exception;

	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForObject(String str, Object obj) throws Exception;

	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForList(String str, Object obj) throws Exception;
	
	/**
	 * 查找对象封装成Map
	 * @param s
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForMap(String sql, Object obj, String key , String value) throws Exception;
	/**
	 * 用于自动分配，查询对象
	 * @param s
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForObjectByAutoAllot(String sql, Object obj);
	/**
	 * 用于自动分配，查询集合
	 * @param s
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForListByAutoAllot(String sql, Object obj);
	/**
	 * 用于自动分配，保存
	 * @param s
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object saveByAutoAllot(String sql, Object obj);
	
	
	
}
