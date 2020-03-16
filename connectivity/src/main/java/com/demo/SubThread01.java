package com.demo ;
import org.apache.log4j.Logger ;

public class SubThread01 implements Runnable
{
	static Logger logger = Logger.getLogger( SubThread01.class ) ;

	private String subCheckParam01 = "" ;

	public SubThread01( String aCheckParam01 ) {
		subCheckParam01 = aCheckParam01 ;
	}

	@Override
	public void run( )
	{
		for ( int i = 0 ; i < 2 ; i++ )
		{
			
			logger.debug( "subCheckParam01 :: " + subCheckParam01 + "/SubThread01 :: i :: " + i ) ;

			try
			{
				
				Thread.sleep( 3000 );
			}
			catch ( InterruptedException e )
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		logger.debug( "SubThread01 :: 끝!! :: "  ) ;

	}

}
