
package com.prototype ;

import org.apache.log4j.Logger ;

public class MainRun
{
	static Logger logger = Logger.getLogger( MainRun.class ) ;
	
	public static void main( String[ ] args ) {
		
		try {
			PropertyLoader.getDemonProperties( ) ;
			
			TestDataSet testDataSet = new TestDataSet( ) ;
			Thread thread = new Thread( testDataSet ) ;
			thread.start( ) ;
			
		} catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
			
		}
		
	}
	
}
