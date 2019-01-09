package com.fh.test;

import java.util.Date;

import com.fh.util.DateUtil;


public class TestDateTime {

	public static void main( String[] args )
	{
		Date date = new Date();
		System.out.println( DateUtil.getDefaultDateStr( date ) );
		Date date1 = DateUtil.addDate( date , -3 );
		System.out.println( DateUtil.getDefaultDateStr( date1 ) );
		Date date2 = DateUtil.addDate( date , 3 );
		System.out.println( DateUtil.getDefaultDateStr( date2 ) );
	}
}
