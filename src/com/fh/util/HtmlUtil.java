package com.fh.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import org.apache.commons.io.IOUtils;

public class HtmlUtil {

	
	public static String doGet( HttpURLConnection conn,String cond ,String encode ) throws IOException
	{
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt");
		conn.setConnectTimeout(1000);
		conn.setReadTimeout(1000);
		IOUtils.write( cond ,conn.getOutputStream());
		InputStream openStream = conn.getInputStream();
		return IOUtils.toString( openStream , encode );
	}
	
	public static String doPost( HttpURLConnection conn,String cond,String encode ) throws IOException
	{
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt");
		conn.setConnectTimeout(30000);
		conn.setReadTimeout(30000);
		IOUtils.write( cond ,conn.getOutputStream());
		InputStream openStream = conn.getInputStream();
		return IOUtils.toString( openStream , encode );
	}
}
