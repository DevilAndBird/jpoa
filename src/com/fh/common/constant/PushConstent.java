package com.fh.common.constant;
/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：极光推送常量
 * 类名称：com.fh.common.constant.PushConstent     
 * 创建人：zjj
 * 创建时间：2018年9月26日 下午2:57:39   
 * 修改人：
 * 修改时间：2018年9月26日 下午2:57:39   
 * 修改备注：
 */
public class PushConstent {
	// 话术key的key
	public static final String KEY = "KEY";
	
	/** =====================分配订单给取派员 start========================= */
	// 话术key
	public static final String ALLOC_NEWORDER_DMAN = "ALLOC_NEWORDER_DMAN";
	// 取派员名称
	public static final String ORDERNO_SUFFIX = "ORDERNO_SUFFIX";
	// 业务参数
	public static final String ORDERNO_EXTRAS = "orderno"; 
	/** =====================分配订单给取派员 end========================= */
	
	/** =====================取消订单给取派员 start========================= */
	// 话术key
	public static final String CANCEL_ORDER = "CANCELORDER";
	/** =====================分配订单给取派员 end========================= */


	/** =====================订单反馈给取派员 start========================= */
	// 话术key
	public static final String FEEDBACK = "FEEDBACK";
	/** =====================分配订单给取派员 end========================= */
	
	
	/** =====================改派订单给取派员 start========================= */
	// 话术key
	public static final String UPDATE_ROLE = "UPDATE_ROLE";
	/** =====================分配订单给取派员 end========================= */
		
	/** =====================取派员即将到达 start ========================= */
	/** 取派员即将到达Key的Value值 */
	public static final String DMAN_ARRIVING = "DMAN_ARRIVING";
	// 取派员名称
	public static final String DMAN_NAME = "DMAN_NAME";
	// 取派员车牌号
	public static final String DMAN_PLATNUM = "DMAN_PLATNUM";
	// 订单总数
	public static final String ORDER_NUM = "ORDER_NUM";
	// 行李数总数
	public static final String LUG_NUM = "LUG_NUM";
		
	public static final String DMAN_USERID_EXTRAS = "dmanuserid";
	
	public static final String INFO_TYPE = "voice";
	/** ======================取派员即将到达 end ======================== */
	
	
	/** =====================取派员已到达 start ========================= */
	/** 取派员即将到达Key的Value值 */
	public static final String DMAN_ARRIVED = "DMAN_ARRIVED";
	/** ======================取派员即将到达 end ======================== */
	
	
	/** ===================== 更改取派员 start ========================= */
	// 取派员即将到达Key的Value值 
	public static final String CHANGE_DMAN = "CHANGE_DMAN";
	// 更改取派员_老
	public static final String OLD_DMAN_NAME = "OLD_DMAN_NAME";
	// 更改取派员_新
	public static final String NEW_DMAN_NAME = "NEW_DMAN_NAME";
	/** ======================更改取派员 end ======================== */

	/** 跳转页面 start  */
	//跳转页面key
	public static final String PAGE = "PAGE";
	//跳转页面key 额外参数
	public static final String PAGE_PARAMETER = "PAGE_PARAMETER";
	//订单列表
	public static final String ORDERPAGE = "ORDERPAGE";
	//反馈页面
	public static final String FEEDBACKPAGE = "FEEDBACKPAGE";
	//默认首页
	public static final String DEFAULTPAGE = "DEFAULTPAGE";
	/** 跳转页面 end  */
}
