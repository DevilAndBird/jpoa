package com.fh.util;

import java.util.Date;

/**
 * 获取ID的程序，注意只能通过单例模式获取
 * 	即 
 * 		IdWorker worker = IdWorker.getInstance();
 * 	否则会产生重复ID
 * 
 * 陈玉石
 * 2017年12月19日13:54:49
 */
public class IdWorker 
{
	public static IdWorker worker;
	
	public static IdWorker getInstance()
	{
		if( worker == null )
			worker = new IdWorker();
		return worker;
	}
	
	public String getDefaultFormatId()
    {
		synchronized( worker )
		{
			try {
				Thread.sleep( 1 );
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long id = System.currentTimeMillis();
	        Date date = new Date( id );
	        return DateUtil.format( date , "yyyyMMddHHmmssSSS");
		}
    }

	public static void main(String[] args) 
	{
		for( int i = 0 ; i < 10000; i++ )
		{
			IdWorker idWorker = IdWorker.getInstance();
			System.out.println( idWorker.getDefaultFormatId() );
		}
	}
}
