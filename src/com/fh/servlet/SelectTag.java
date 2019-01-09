package com.fh.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fh.entity.system.SysItem;
import com.fh.service.system.sysitem.SysItemService;

public class SelectTag extends TagSupport{

	private String itemcode;
	private String fid;
	private String id;
	private String name;
	private String onchange;
	private String nextid;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	 public int doEndTag() throws JspException {
	  try {
		  JspWriter out = pageContext.getOut();
		  ServletContext servletContext = this.pageContext.getServletContext();
		  WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(servletContext);  
		  SysItemService service = (SysItemService)wac.getBean("sysItemService");  
		  
		  StringBuffer sb = new StringBuffer();
		  try {
			List<SysItem> items = service.listItemsByItemCode( itemcode );
			
			sb.append( "<select id='");
			sb.append( id );
			sb.append( "' name='" );
			sb.append( name );
			sb.append( "' onchage='" );
			sb.append( onchange );
			sb.append( "' >" );
//			if( !StringUtils.isEmpty( nextid) )
//			{
//				
//			}
			sb.append( "<option  value ='' selected>所有</option>");
			for( SysItem item : items )
			{
				sb.append("<option value='");
				sb.append( item.getItemname());
				sb.append( "'>" );
				sb.append( item.getItemname() );
				sb.append( "</option>" );
			}
			sb.append( "</select>" );
		} catch (Exception e) {
			e.printStackTrace();
		}
		  out.write( sb.toString() );
	  } catch (IOException e) {
		  e.printStackTrace();
	  }
	  	return TagSupport.EVAL_BODY_INCLUDE;
	 }
	
	 @Override
	 public int doStartTag() throws JspException {
	  return TagSupport.EVAL_PAGE;
	 }

	public String getItemcode() {
		return itemcode;
	}

	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	 
	public String getNextid() {
		return nextid;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public void setNextid(String nextid) {
		this.nextid = nextid;
	} 
}
