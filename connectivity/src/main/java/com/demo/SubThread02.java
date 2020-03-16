
package com.demo ;

import java.util.HashMap ;

import org.apache.log4j.Logger ;

public class SubThread02 implements Runnable
{

	static Logger logger = Logger.getLogger( SubThread02.class ) ;

	private String subCheckParam01 = "" ;

	public SubThread02( String aCheckParam01 ) {
		subCheckParam01 = aCheckParam01 ;
	}

	@Override
	public void run( )
	{

		thrdSubThread01 thrdSub = null ;
		Thread thrdSubTh = null ;

		HashMap< String , Object > paramHashMap = new HashMap<>( ) ;
		logger.debug( "SubThread02 :: 시작!! :: " ) ;

		for ( int i = 0 ; i < 1 ; i++ )
		{

			logger.debug( "subCheckParam01 :: " + subCheckParam01 + "SubThread02 :: i :: " + i ) ;

			try
			{
				paramHashMap.put( "Key" , ( "Val" + i ) ) ;

				thrdSub = new thrdSubThread01( paramHashMap ) ;
				thrdSubTh = new Thread( thrdSub ) ;
				thrdSubTh.start( ) ;

				Thread.sleep( 3000 ) ;
			}
			catch ( InterruptedException e )
			{
				// TODO Auto-generated catch block
				e.printStackTrace( ) ;
			}

		}

		logger.debug( "SubThread01 :: 끝!! :: " ) ;

	}

}
