
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

		logger.debug( "MainThread01 :: 끝!! :: " ) ;

	}

}
