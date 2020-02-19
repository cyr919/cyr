
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

		SubThread01 subth = new SubThread01( "SubThread01 실행" ) ;
		Thread th = new Thread( subth ) ;
		
		
		for ( int i = 0 ; i < 10 ; i++ )
		{
			logger.debug( "MainThread01 :: i :: " + i ) ;
			logger.debug( "CheckParam01 :: " + CheckParam01 ) ;

			try
			{
				Thread.sleep( 1000 ) ;
			}
			catch ( InterruptedException e )
			{
				e.printStackTrace( ) ;
				logger.error( e ) ;
			}
			
			if(i== 3 ) {
				
				logger.debug( "새로운 thread 실행" ) ;
				
				th.start( ); 
				
			}
			
		}
		
		logger.debug( "MainThread01 :: 끝!! :: "  ) ;


	}

}
