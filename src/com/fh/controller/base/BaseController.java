package com.fh.controller.base;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fh.common.constant_enum.APP_RESPONSE_CODE;
import com.fh.entity.Page;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.AppResponseBean;
import com.fh.resolver.DoubleDefault0Adapter;
import com.fh.resolver.IntegerDefault0Adapter;
import com.fh.service.base.AirportInfoConfigService;
import com.fh.util.Logger;
import com.fh.util.MD5;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;
import com.fh.util.UuidUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.mysql.jdbc.StringUtils;
/**
 * 基础类(新增的controller需继承此类)
 * @author tqm
 * @date 2018年11月5日
 */
public class BaseController {

    
    @Autowired
    private AirportInfoConfigService airportInfoConfigService;

    protected Logger logger = Logger.getLogger(this.getClass());
    //用于如果字段为null，则将其转换成"" tangqm
	 public   Gson gson = new GsonBuilder().registerTypeAdapter(String.class, STRING).
			 registerTypeAdapter(Integer.class, new IntegerDefault0Adapter()).registerTypeAdapter(int.class, new IntegerDefault0Adapter()).//整型解析
			 registerTypeAdapter(double.class, new DoubleDefault0Adapter()).registerTypeAdapter(Double.class, new DoubleDefault0Adapter())//浮点型解析
			 .create();

	 
    /**
     * 自定义Strig适配器  
     * tangqm
     */
    @SuppressWarnings("all")
	private static final TypeAdapter STRING = new TypeAdapter()  
    {  
        public String read(JsonReader reader) throws IOException  
        {  
            if (reader.peek() == JsonToken.NULL)  
            {  
                reader.nextNull();  
                return "";  
            }  
            return reader.nextString();  
        }  
		@Override
		public void write(JsonWriter writer, Object value) throws IOException {
			  if (value == null)  
	            {  
	                // 在这里处理null改为空字符串  
	                writer.value("");  
	                return;  
	            }  
	            writer.value(value.toString());  
		}

    };  

    /**
	 * 校验接口的数据是否正确
	 * 
	 * sign = MD5( "jingpei"+user + password + timestamp )
	 * @param param
	 * @return
	 */
	public AppResponseBean doValidate( AppRequestBean param )
	{
		AppResponseBean rtBean = new AppResponseBean();
		rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
		rtBean.setMsg( "接口调用成功!" );
//		String sign = MD5.md5( "jingpei"+param.getUser()+param.getKey()+param.getTimestamp() );
//		if( sign.equalsIgnoreCase( param.getSign() ))
//		{
//			rtBean.setCode( APP_RESPONSE_CODE.SUCCESS.getValue() );
//			rtBean.setMsg( "接口调用成功!" );
//		}
		if( StringUtils.isEmptyOrWhitespaceOnly( param.getData() ) )
		{
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "参数体为空" );
		}
		// 生产使用
		Object object = RedisUtil.get(param.getUser());
		if(object==null){
			rtBean.setCode( APP_RESPONSE_CODE.LOGINERROR.getValue() );
			rtBean.setMsg( "登陆超时" );
			return rtBean;
		}
		String sign = (String) object;
		if( !sign.equalsIgnoreCase( param.getSign())){
			rtBean.setCode( APP_RESPONSE_CODE.LOGINERROR.getValue() );
	     	rtBean.setMsg( "登陆超时" );
	 	}
		return rtBean;
	}
	
	/**
	 * 校验H5接口的数据是否正确
	 * 
	 * sign = MD5( "jingpei"+user + password + timestamp )
	 * @param param
	 * @return
	 */
	public AppResponseBean doH5Validate( AppRequestBean param )
	{
		AppResponseBean rtBean = new AppResponseBean();
		rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
		rtBean.setMsg( "接口调用成功!" );
		String sign = MD5.md5( "jingpei"+param.getUser()+param.getKey()+param.getTimestamp() );
		if( sign.equalsIgnoreCase( param.getSign() ))
		{
			rtBean.setCode( APP_RESPONSE_CODE.SUCCESS.getValue() );
			rtBean.setMsg( "接口调用成功!" );
		}
		if( StringUtils.isEmptyOrWhitespaceOnly( param.getData() ) )
		{
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "参数体为空" );
		}
		return rtBean;
	}
	
	/**
	 * 校验接口的数据是否正确
	 * 
	 * sign = MD5( "jingpei"+user + password + timestamp )
	 * @param param
	 * @return
	 */
	public AppResponseBean doLoginValidate( AppRequestBean param )
	{
		AppResponseBean rtBean = new AppResponseBean();
		rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
		rtBean.setMsg( "成功!" );
		if( StringUtils.isEmptyOrWhitespaceOnly( param.getData() ) )
		{
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "参数体为空" );
		}
		return rtBean;
	}
	
    /**
     * 得到PageData
     */
    public PageData getPageData() {
        return new PageData(this.getRequest());
    }

    /**
     * 得到ModelAndView
     */
    public ModelAndView getModelAndView() {
        return new ModelAndView();
    }

    /**
     * 得到request对象
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        return request;
    }

    /**
     * 得到32位的uuid
     * 
     * @return
     */
    public String get32UUID() {
        return UuidUtil.get32UUID();
    }

    /**
     * 得到分页列表的信息
     */
    public Page getPage() {
        return new Page();
    }

    public static void logBefore(Logger logger, String interfaceName) {
        logger.info("");
        logger.info("start");
        logger.info(interfaceName);
    }

    public static void logAfter(Logger logger) {
        logger.info("end");
        logger.info("");
    }
}
