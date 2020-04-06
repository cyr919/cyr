package com.prototype.adapter ;

import org.apache.log4j.Logger ;

import com.prototype.PropertyLoader ;

public class AdapterMainRun
{
	
	static Logger logger = Logger.getLogger( AdapterMainRun.class ) ;
	
	/**
	 * adapter 데이터 생성기...
	 * 
	 * @param args
	 */
	public static void main( String[ ] args ) {
		
		try {
			PropertyLoader.getDemonProperties( ) ;
			
			AdapterDataSimulTest adapterDataSimulTest = new AdapterDataSimulTest( ) ;
			Thread dsThread = new Thread( adapterDataSimulTest , "adapterDataSimulTestThread" ) ;
			
			dsThread.start( ); 
			
			logger.info( "PropertyLoader.PROCESS_THREAD_CNT :: " + PropertyLoader.PROCESS_THREAD_CNT );
			
			Thread.sleep( 5 * 1000 );
			
			logger.info( "PropertyLoader.PROCESS_THREAD_CNT :: " + PropertyLoader.PROCESS_THREAD_CNT );
			
			
		} catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		} finally {
			
		}
		
	}
	
}
