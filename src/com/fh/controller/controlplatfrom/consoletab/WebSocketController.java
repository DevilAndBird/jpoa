package com.fh.controller.controlplatfrom.consoletab;

import com.fh.controller.base.BaseController;
import com.fh.controller.config.HttpSessionConfigurator;
import com.fh.entity.system.User;
import com.fh.service.controlplatfrom.websocket.WebSocketService;
import com.fh.util.Const;
import com.fh.util.PageData;
import org.springframework.web.context.ContextLoader;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tangqm
 * websocket服务层
 * 2018年12月12日
 */
@ServerEndpoint(value = "/websocket",configurator = HttpSessionConfigurator.class)
public class WebSocketController extends BaseController {

	private WebSocketService webSocketService = (WebSocketService) ContextLoader.getCurrentWebApplicationContext().getBean("webSocketService");
    PageData pd = new PageData();

	@OnMessage
    public void onMessage(String message, Session session)
    	throws Exception {
		logger.info(session.getId());
		logger.info(session.getMaxBinaryMessageBufferSize());
		logger.info(session.getMaxIdleTimeout());
		logger.info(session);
		logger.info("接收: " + message);
        Integer cacheProblemNum = 0;
        Integer cacheAutoAllocNum = 0;
        Integer cacheManualAllocNum = 0;
        boolean flag = false;
        Map<String,Integer> mesData = new HashMap<String, Integer>(16);
        while(true){
            //获取当前反馈数量
            Integer newProblemNum = webSocketService.findUntreatedNum();
            if(!newProblemNum.equals(cacheProblemNum)){
                mesData.put("proNum",newProblemNum);
                flag = true;
                cacheProblemNum = newProblemNum;
            }
            //获取当前自动分配数量
            Integer newAutoAllocNum = webSocketService.findAutoAllotNum(pd);
            if(!newAutoAllocNum.equals(cacheAutoAllocNum)){
                mesData.put("autoNum",newAutoAllocNum);
                flag = true;
                cacheAutoAllocNum = newAutoAllocNum;
            }
            //获取当前带分配分配数量
            Integer newManualAllocNum = webSocketService.findWaitmanualAllocNum(pd);
            if(!newManualAllocNum.equals(cacheManualAllocNum)){
                mesData.put("manualNum",newManualAllocNum);
                flag = true;
                cacheManualAllocNum = newManualAllocNum;
            }
            if(flag){
                session.getBasicRemote().sendText(gson.toJson(mesData));
                //归原
                flag = false;
            }
            Thread.sleep(45000);
        }
    }


    /**
     *客户端连接触发
     */
	@OnOpen
    public void onOpen (Session session, EndpointConfig config) {
        logger.info("客户端--已连接");
        //获取用户数据 tangqm
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        User user = (User) httpSession.getAttribute(Const.SESSION_USER);
        if(user != null) {
            pd.put("loginperson_provid", user.getProvid());
            pd.put("loginperson_cityid", user.getCityid());
        }else{
            pd.put("loginperson_provid", "310000");
            pd.put("loginperson_cityid", "310000");
        }
    }

    /**
     *客户端断开触发
     */
    @OnClose
    public void onClose () {
		logger.info("客户端--已关闭");
    }
    
}
/*@ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端。注解的值将被用于监听用户连接的终端访问URL地址。 

onOpen 和 onClose 方法分别被@OnOpen和@OnClose 所注解。这两个注解的作用不言自明：他们定义了当一个新用户连接和断开的时候所调用的方法。 

onMessage 方法被@OnMessage所注解。这个注解定义了当服务器接收到客户端发送的消息时所调用的方法。注意：这个方法可能包含一个javax.websocket.Session可选参数（在我们的例子里就是session参数）。如果有这个参数，容器将会把当前发送消息客户端的连接Session注入进去。 
*/
