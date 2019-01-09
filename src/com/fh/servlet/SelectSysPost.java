package com.fh.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fh.entity.system.SysPost;
import com.fh.service.system.syspost.SysPostService;

public class SelectSysPost extends TagSupport{

	private String postname;
	private String fid;
	private String id;
	private String name;

	private static final long serialVersionUID = 1L;
	
	@Override
	 public int doEndTag() throws JspException {
	  try {
		  JspWriter out = pageContext.getOut();
		  ServletContext servletContext = this.pageContext.getServletContext();
		  WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(servletContext);  
		  SysPostService service = (SysPostService)wac.getBean("sysPostService");  
		  
		  StringBuffer sb = new StringBuffer();
		  try {
			List<SysPost> posts = service.syspostlistPage(null);
			
			sb.append( "<select id='");
			sb.append( id );
			sb.append( "' name='" );
			sb.append( name );
			sb.append( "' >" );
			for( SysPost post : posts )
			{
				sb.append("<option ");
				sb.append("value='");
				sb.append(post.getId());
				sb.append( "'>" );
				sb.append( post.getPostname());
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

	public String getPostname() {
		return postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
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

}
