
package com.demo ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

public class ThreadTestMain
{

	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private static final Logger logger = LogManager.getLogger( ThreadTestMain.class ) ;
	// Logger logger = LogManager.getLogger( ) ;

	public static void main( String[ ] args )
	{
		logger.debug( "MainThread01 :: 시작!! :: " ) ;

		// implements Runnable
		MainThread01 imTh = new MainThread01( "MainThread01 실행" ) ;
		Thread th = new Thread( imTh ) ;
		th.start( ) ;

		// multi thread 실행
//
//		int threadCnt = 0 ;
//		MainThread01 imThMulti = new MainThread01( "MainThread01  Multi 실행" ) ;
//
//		threadCnt = 2 ;
//		Thread[ ] thd = new Thread[ threadCnt ] ;
//
//		for ( int i = 0 ; i < threadCnt ; i++ )
//		{
//			thd[ i ] = new Thread( imThMulti ) ;
//			thd[ i ].start( ) ;
//		}

		logger.debug( "MainThread01 :: 끝!! :: " ) ;

	}

}
