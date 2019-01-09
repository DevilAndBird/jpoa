/** appUser_type类型 */
var APPUSER_TYPE = {
		/** 取派员 */
		DELIVERY_MAN : {
			value : 'DELIVERY_MAN',
			name  : '取派员'
		},
		/** 集散中心 */
		TRANSIT_CENTER : {
			value : 'TRANSIT_CENTER',
			name  : '集散中心'
		},
		/** 班车司机 */
		REGULAR_DRIVER : {
			value : 'REGULAR_DRIVER',
			name  : '班车司机'
		},
		/** 机场取件员 */
		AIRPORT_PICKER : {
			value : 'AIRPORT_PICKER',
			name  : '机场取件员'
		},
		/** 柜台服务中心 */
		SERVICE_CENTER : {
			value : 'SERVICE_CENTER',
			name  : '柜台服务中心'
		}
};

/** isvalid 类型 */
var ISVALID_TYPE = {
		/** 有效 */
		VALID : {
			value : 'Y',
			name  : '有效'
		},
		/** 无效 */
		INVALID : {
			value : 'N',
			name  : '无效'
		}
};

/** 柜台服务中心类型 */
var SERVICECENTER_TYPE = {
		/** 机场服务中心 */
		AIRPORT : {
			value : 'AIRPORT',
			name  : '机场服务中心'
		},
		/** 高铁服务中心 */
		HIGHSPEEDTRAIN : {
			value : 'HIGHSPEEDTRAIN',
			name  : '高铁服务中心'
		}
};

/** 司机任务类型枚举 */
var TASKMAINDRIVER_TYPE = {
		/** 定时任务 */
		TIMED_TASK : {
			value : 'TIMED_TASK',
			name  : '定时任务'
		},
		/** 临时任务 */
		TEMP_TASK : {
			value : 'TEMP_TASK',
			name  : '临时任务'
		}
};


/** 司机任务状态 */
var TASKMAINDRIVER_STATUS = {
		/** 未开始 */
		NOTSTARTED : {
			value : 'NOTSTARTED',
			name  : '未开始'
		},
		/** 已开始 */
		HASSTARTED : {
			value : 'HASSTARTED',
			name  : '已开始'
		},
		/** 已完成 */
		FINISHED : {
			value : 'FINISHED',
			name  : '已完成'
		},
		/** 已取消 */
		CANCEL : {
			value : 'CANCEL',
			name  : '已取消'
		}
};

/**  processStatus类型 */
var PROCESSSTATUS_TYPE = {
		/** 有效 */
		UNSOLVED : {
			value : 'UNSOLVED',
			name  : '未解决'
		},
		/** 无效 */
		SOLVED : {
			value : 'SOLVED',
			name  : '已解决'
		}
};
/**  processCode类型 */
var TROUBLECODE_TYPE = {
		/** 有效 */
		TROUBLECODE1 : {
			value : 'TROUBLECODE1',
			name  : '问题1'
		},
		TROUBLECODE2 : {
			value : 'TROUBLECODE2',
			name  : '问题2'
		}
};

/** status_type类型 */
var STATUS_TYPE = {
		ALLOTDELIVERY : {
			value : 'ALLOTDELIVERY',
			name  : '已派单'
		},
		ARRIVEAIRPORT : {
			value : 'ARRIVEAIRPORT',
			name  : '已达机场'
		},
		DELIVERYING : {
			value : 'DELIVERYING',
			name  : '派送中'
		},
		DELIVERYOVER : {
			value : 'DELIVERYOVER',
			name  : '已送达'
		},
		ORDERRECEIVINGOVER : {
			value : 'ORDERRECEIVINGOVER',
			name  : '已接单'
		},
		PREPAID : {
			value : 'PREPAID',
			name  : '已支付'
		},
		RELEASEOVER : {
			value : 'RELEASEOVER',
			name  : '已释放'
		},
		RELEASEOVER : {
			value : 'RELEASEOVER',
			name  : '已释放'
		},
		TAKEGOOGSING : {
			value : 'TAKEGOOGSING',
			name  : '取件中'
		},
		TAKEGOOGSOVER : {
			value : 'TAKEGOOGSOVER',
			name  : '已取件'
		},
		TRANSFEROVER : {
			value : 'TRANSFEROVER',
			name  : '已中转'
		},
		takeGoogsOver : {
			value : 'TAKEGOOGSOVER',
			name  : '已取件'
		},
		TRUCELOADINGOVER : {
			value : 'TRUCELOADINGOVER',
			name  : '已装车'
		},
		WAITORDERRECEIVING : {
			value : 'WAITORDERRECEIVING',
			name  : '待接单'
		},
		WAITPAY : {
			value : 'WAITPAY',
			name  : '待支付'
		},
		WAITTRUCELOADING : {
			value : 'WAITTRUCELOADING',
			name  : '待装车'
		},
		CANCELLED : {
			value : 'CANCELLED',
			name  : '已取消'
		},
	    WAITPICK :  {
        value : 'WAITPICK',
        name  : '待取件'
        }
};

/**  订单角色动作类型 */
var ROLE_TYPE = {
		ROLE_HOTEL_TASK : {
			value : 'ROLE_HOTEL_TASK',
			name  : '去酒/宅取件'
		},
		ROLE_HOTEL_SEND : {
			value : 'ROLE_HOTEL_SEND',
			name  : '去酒/宅送件'
		},
		ROLE_TRANSIT_TASK : {
			value : 'ROLE_TRANSIT_TASK',
			name  : '去集散中心取件'
		},
		ROLE_TRANSIT_SEND : {
			value : 'ROLE_TRANSIT_SEND',
			name  : '去集散中心送件'
		},
		ROLE_AIRPORT_TASK : {
			value : 'ROLE_AIRPORT_TASK',
			name  : '去机场取件'
		},
		ROLE_AIRPORT_SEND : {
			value : 'ROLE_AIRPORT_SEND',
			name  : '去机场送件'
		}
		/*,ROLE_ARRIVE_AIRPORT : {
			value : 'ROLE_ARRIVE_AIRPORT',
			name  : '抵达机场柜台'
		},
		ROLE_ARRIVE_TRANSIT : {
			value : 'ROLE_ARRIVE_TRANSIT',
			name  : '抵达集散中心'
		},
		ROLE_ARRIVE_HOTEL : {
			value : 'ROLE_ARRIVE_HOTEL',
			name  : '抵达酒店'
		}*/
};

/**  订单类型 */
var ORDER_TYPE = {
		AIRPORTCOUNTERTOHOTEL : {
			value : 'AIRPORTCOUNTERTOHOTEL',
			name  : '机场柜台到酒店'
		},
		AIRPORTTURNTABLETOHOTEL : {
			value : 'AIRPORTTURNTABLETOHOTEL',
			name  : '机场转盘到酒店'
		},
		HOTELTOAIRPORT : {
			value : 'HOTELTOAIRPORT',
			name  : '酒店到机场'
		}
};

/**  目标地址类型 */
var DESTINSATION_TYPE = {
		SERVICECERTER : {
			value : 'SERVICECERTER',
			name  : '机场/高铁'
		},
		AIRPORTCOUNTER : {
			value : 'AIRPORTCOUNTER',
			name  : '机场柜台'
		},
		TRANSITCERTER : {
			value : 'TRANSITCERTER',
			name  : '集'
		},
		HOTEL : {
			value : 'HOTEL',
			name  : '酒店'
		},
		RESIDENCE : {
			value : 'RESIDENCE',
			name  : '住宅'
		}
};

/**  目标地址类型 */
var MAILING_WAY = {
		AIRPOSTCOUNTER : {
			value : 'SERVICECERTER',
			name  : '柜台'
		},
		ONESELF : {
			value : 'ONESELF',
			name  : '本人'
		},
		FRONTDESK : {
			value : 'FRONTDESK',
			name  : '酒店前台'
		},
		OTHER : {
			value : 'OTHER',
			name  : '他人'
		}
};

/**  是否已经完成动作类型 */
var IS_FINISH = {
		UNFINISHED : {
			value : 'UNFINISHED',
			name  : '未开始'
		},
		ONGOING : {
			value : 'ONGOING',
			name  : '进行中'
		},
		FINISHED : {
			value : 'FINISHED',
			name  : '完成'
		}
};

/** 优惠卷类型 */
var COUPON_TYPE = {
		DISCOUNT : {
			value : 'UNFINISHED',
			name  : '折扣'
		},
		ONGOING : {
			value : 'DIRECT_REDUCE',
			name  : '直减'
		},
		FINISHED : {
			value : 'FULL_REDUCE',
			name  : '满减'
		}
};

/** 定时时间 */
var TIMING_TIME = {
		/** 待人工分配订单数量统计 */
		CONSOLE_WAIT_MANUAL_ALLOT_COUNT : {
			value : 30000,
			name  : '0.5分钟'
		},
		/** 控制台_待人工分配订单刷新 */
		CONSOLE_WAIT_MANUAL_ALLOT_MAP : {
			value : 30000,
			name  : '0.5分钟'
		},
		/** 控制台_取派员列表地图 */
		CONSOLE_DMANLIST_MAP : {
			value : 60000000,
			name  : '1分钟'
		},
		/** 控制台_取派员详情地图 */
		CONSOLE_DMANDETAILS_MAP : {
			value : 60000000,
			name  : '1分钟'
		},
};

/** 订单地址类型 */
var ORDER_ADDRESS_TYPE = {
		/** 机场柜台 */
		AIRPORTCOUNTER : {
			value : 'AIRPORTCOUNTER',
			name  : '机场柜台'
		},
		/** 酒店 */
		HOTEL : {
			value : 'HOTEL',
			name  : '酒店'
		},
		/** 住宅 */
		RESIDENCE : {
			value : 'RESIDENCE',
			name  : '住宅'
		}
};

/** 图片业务类型用于行李拍照展示 */
var IMGURL_BUSINESS_TYPE = {
		/** 收取行李拍照 */
		COOLECT : {
			value : 'COOLECT',
			name  : '收取行李拍照'
		},
		/** 释放行李拍照使用 */
		RELEASE : {
			value : 'RELEASE',
			name  : '释放行李拍照使用'
		}
};

/** 系统图片路径 */
var IMG_URL_PATH = {
		/** 飞机场图片地址 */
		AIRPORT_URL : {
			value : '/jpoa/static/img/airplane.png',
			name  : '飞机场图片地址'
		},
		/** 集散中心地址 */
		TRANSIT_URL : {
			value : '/jpoa/static/img/house.png',
			name  : '集散中心地址'
		},
		/**目的地坐标地址 */
		DESTPOINT_URL : {
			value : '/jpoa/static/img/destpoint.png',
			name  : '目的地坐标地址'
		},
		/** 出发地图片路径地址 */
		SRCPOINT_URL : {
			value : '/jpoa/static/img/srcpoint.png',
			name  : '出发地图片地址'
		},
		/** 取派员图片位置地址 */
		DMAN_CAR_URL : {
			value : '/jpoa/static/img/car.png',
			name  : '取派员图片位置地址'
		},
		/** 目的地地址标识 */
		DESTADDRESS_URL : {
			value : '/jpoa/static/img/dest.png',
			name  : '目的地地址标识'
		},
		/** 出发地地址标识 */
		SRCADDRESS_URL : {
			value : '/jpoa/static/img/src.png',
			name  : '出发地地址标识'
		},
		/** 出发地地址标识 */
		LUG_URL : {
			value : '/jpoa/static/img/lug.png',
			name  : '行李箱路径'
		},
		/** 显示下个动作路径_电灯图片路径 */
		LIGHT_URL : {
			value : '/jpoa/static/img/light.png',
			name  : '显示下个动作路径_电灯图片路径'
		},
		/** 出发地地址标识 */
		EDIT_URL : {
			value : '/jpoa/static/img/edit.png',
			name  : '编辑'
		},
		/** 取图片 */
		TASKLUG_URL : {
			value : '/jpoa/static/img/tasklug.png',
			name  : '取图片'
		},
		/** 送图片 */
		SENDLUG_URL : {
			value : '/jpoa/static/img/sendlug.png',
			name  : '送图片'
		},
		/** 送图片 */
		MAP_REFRESH_URL : {
			value : '/jpoa/static/img/map_refresh_32px.png',
			name  : '地图刷新'
		},
		/** 新增 */
		ADD_URL : {
			value : '/jpoa/static/img/add.png',
			name  : '送图片'
		},
		/** 删除 */
		CANCEL_URL : {
			value : '/jpoa/static/img/cancel.png',
			name  : '送图片'
		},
		/** 垃圾桶 */
		DELETE_URL : {
			value : '/jpoa/static/img/Delete_24px.png',
			name  : '删除'
		},
		/** 返回 */
		BACK_URL : {
			value : '/jpoa/static/img/back_48.png',
			name  : '返回'
		},
		/** 详情 */
		DETAILS_URL : {
			value : '/jpoa/static/img/details_24px.png',
			name  : '详情'
		},
		/** true */
		TRUE_URL : {
			value : '/jpoa/static/img/true_16px.png',
			name  : '通过or正确'
		},
		/** 倒计时 */
		BOMB_COUNTDOWN_URL : {
			value : '/jpoa/static/img/bomb_24px.png',
			name  : '倒计时'
		},
		/** 超时爆炸 */
		BOMB_OVERTIME_24_URL : {
			value : '/jpoa/static/img/pictograms_24px.png',
			name  : '超时爆炸'
		},
		/** 超时爆炸 */
		BOMB_OVERTIME_32_URL : {
			value : '/jpoa/static/img/pictograms_32px.png',
			name  : '超时爆炸'
		},
		/** 超时爆炸 */
		ORDER_SRC_URL : {
			value : '/jpoa/static/img/ordersrc.png',
			name  : '订单出发地显示'
		},
		/** 超时爆炸 */
		ORDER_DEST_URL : {
			value : '/jpoa/static/img/orderdest.png',
			name  : '订单目的地显示'
		},
		/** 取件动作图片 */
		FETCH_ACTIVE_URL : {
			value : '/jpoa/static/img/fetch_active.png',
			name  : '取件动作图片'
		},
		/** 超时爆炸 */
		DELIVERY_ACTIVE_URL : {
			value : '/jpoa/static/img/delivery_active.png',
			name  : '派件动作图片'
		},
		/** 订单路径显示 */
		ROUTE_URL : {
			value : '/jpoa/static/img/route_24px.png',
			name  : '订单路径显示'
		},
		/** 订单路径显示 */
		ROUTE_32px_URL : {
			value : '/jpoa/static/img/route_32px.png',
			name  : '订单路径显示_32px'
		},
		/** 返回 */
		BACK_URL : {
			value : '/jpoa/static/img/back.png',
			name  : '返回'
		},
		/** 专车 */
		SPECIAL_CAR_URL : {
			value : '/jpoa/static/img/special_car.png',
			name  : '专车'
		}
		
};

/** 订单渠道 */
var ORDER_CHANNEL = {
		/** 微信_金牌 */
		WEIXIN_GS : {
			value : 'weixin_gs',
			name  : '微信_金牌'
		},
		/** 微信_专车 */
		WEIXIN_SC : {
			value : 'weixin_sc',
			name  : '微信_专车'
		},
		 /** app_金牌 */
	    APP_GS : {
	    	value : "app_gs",
	    	name : "app_金牌"
		},
	    /** app_专车 */
	    APP_SC : {
	    	value : "app_sc",
	    	name : "app_专车"
	    }
};

/** 服务类型 */
var SERVICE_TYPE = {
		/** 金牌 */
		GS : {
			value : 'gs',
			name  : '金牌'
		},
		/** 专车 */
		SC : {
			value : 'sc',
			name  : '专车'
		}
};

/** 时间范围确定 */
var TIME_SCOPE = {
		/** 分派取派员最迟到达时间 */
		DMAM_ARRIVEDTIIME : {
			starttime : '09:00:00',
			endtime : '22:00:00',
			name  : '分派取派员最迟到达时间'
		}
};

