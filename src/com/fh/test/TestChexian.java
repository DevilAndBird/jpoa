package com.fh.test;

import java.math.BigInteger;

public class TestChexian {

	/**
	 * 33赋值，
	 * @param args
	 */
	public static void main(String[] args )
	{
		getTotal();
		for( int i = 2; i <=16; i++ )
		{
			process(i);
		}
	}
	
	public static void getTotal()
	{
		BigInteger[] money = { new BigInteger("580"),new BigInteger("1380"),
				new BigInteger("3960"),new BigInteger("11340"),new BigInteger("32400"),
				new BigInteger("92340"),new BigInteger("262440"),new BigInteger("743580"),
				new BigInteger("2099520"),new BigInteger("5904900"),new BigInteger("16533720"),
				new BigInteger("46058220"),new BigInteger("127545840"),
				new BigInteger("350751060"),new BigInteger("956593800"),
				new BigInteger("2582803260"),};
		BigInteger total = new BigInteger("0");
		for( int i = 0; i <16; i++ )
		{
			total = total.add( money[i] );
		}
		System.out.println( "剩余总金额"+total );
	}
	public static void process( int level )
	{
		long[] number = {1,3,9,27,81,243,729,2187,6561,19683,59049,177147,531441,1594323,4782969,14348907};
		int money = 580;
		int leftMoney = 580-120;
		//System.out.println( "第"+(level-1)+"级获取"+120+"元" );
		for( int i = level-2 ; i >=1; i-- )
		{
			leftMoney -=20;
		//	System.out.println( "第"+i+"级获取20元" );
		}
		//System.out.println( "最终剩余"+leftMoney+"元" );
		long total = number[level-1]*leftMoney;
		//System.out.println("第"+level+"级别会员数"+number[level-1]+"*剩余金额"+leftMoney+"=剩余总金额"+total);
		System.out.print( "new BigInteger(\""+total+"\"),");
	}
	
	public static void getPeopleNum()
	{
		long cur = 1;
		for( int i = 2 ; i <= 16; i++ )
		{
			cur = cur*3; 
			System.out.print( "第"+i+"级人数为："+cur +";  " );
		}
	}


}
