
package com.prototype ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

public class MainRun
{
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private static final Logger logger = LogManager.getLogger( PropertyLoader.class ) ;
	
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
