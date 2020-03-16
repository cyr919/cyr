
package com.demo ;

import org.apache.log4j.Logger ;

public class MainThread01 implements Runnable
{

	static Logger logger = Logger.getLogger( MainThread01.class ) ;

	private String CheckParam01 = "" ;

	public MainThread01( String aCheckParam01 ) {
		CheckParam01 = aCheckParam01 ;
	}

	@Override
	public void run( )
	{

		SubThread01 subth = null ;
		Thread th = null ;

		SubThread02 subth02 = null ;
		Thread th02 = null ;

		try
		{
			logger.debug( "MainThread01 :: 시작!! :: " ) ;

			for ( int i = 0 ; i < 1 ; i++ )
			{
				logger.debug( "CheckParam01 :: " + CheckParam01 + "/MainThread01 :: i :: " + i ) ;

				try
				{

					subth02 = new SubThread02( "SubThread02 실행 :: " + i ) ;
					th02 = new Thread( subth02 ) ;
					th02.start( ) ;
					
					Thread.sleep( 1000 ) ;
				}
				catch ( InterruptedException e )
				{
					e.printStackTrace( ) ;
					logger.error( e ) ;
				}

				if ( i == 3 )
				{

					logger.debug( "새로운 thread 실행 :: " + i ) ;
					subth = new SubThread01( "SubThread01 실행" ) ;
					th = new Thread( subth ) ;

					th.start( ) ;

				}

			}

		}
		finally
		{
			subth = null ;
			th = null ;

			subth02 = null ;
			th02 = null ;

			logger.debug( "MainThread01 :: 끝!! :: " ) ;
		}

	}

}
