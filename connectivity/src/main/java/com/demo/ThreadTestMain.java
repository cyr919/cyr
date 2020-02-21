
package com.demo ;

import org.apache.log4j.Logger ;

public class ThreadTestMain
{

	static Logger logger = Logger.getLogger( ThreadTestMain.class ) ;

	public static void main( String[ ] args )
	{

		// implements Runnable
		MainThread01 imTh = new MainThread01( "MainThread01 실행" ) ;
		Thread th = new Thread( imTh ) ;
		th.start( ) ;

		// multi thread 실행

		int threadCnt = 0 ;
		MainThread01 imThMulti = new MainThread01( "MainThread01  Multi 실행" ) ;

		threadCnt = 3 ;
		Thread[ ] thd = new Thread[ threadCnt ] ;

		for ( int i = 0 ; i < threadCnt ; i++ )
		{
			thd[ i ] = new Thread( imThMulti ) ;
			thd[ i ].start( ) ;
		}

		logger.debug( "MainThread01 :: 끝!! :: " ) ;

	}

}
