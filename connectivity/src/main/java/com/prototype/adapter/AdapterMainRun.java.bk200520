package com.prototype.adapter ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.prototype.PropertyLoader ;

public class AdapterMainRun
{
	
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private static final Logger logger = LogManager.getLogger( AdapterMainRun.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
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
			
			
			
		} catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		} finally {
			
		}
		
	}
	
}
