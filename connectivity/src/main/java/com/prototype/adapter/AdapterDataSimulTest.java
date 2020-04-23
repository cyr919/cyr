package com.prototype.adapter ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.prototype.PropertyLoader ;

public class AdapterDataSimulTest implements Runnable
{
	
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private static final Logger logger = LogManager.getLogger( AdapterDataSimulTest.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	private boolean isDemonLive = false ;
	
	@Override
	public void run( ) {
		
		try {
			PropertyLoader.PROCESS_THREAD_CNT++ ;
			
			setDemonLive( true ) ;
			
			logger.debug( "isDemonLive :: " + isDemonLive ) ;
			logger.debug( "PropertyLoader.IS_ALL_DEMON_LIVE :: " + PropertyLoader.IS_ALL_DEMON_LIVE ) ;
			
			while( isDemonLive && PropertyLoader.IS_ALL_DEMON_LIVE ) {
				
				AdapterDataGenerator adapterDataGenerator = new AdapterDataGenerator( ) ;
				Thread dgThread = new Thread( adapterDataGenerator , "adapterDataGeneratorThread" ) ;
				
				dgThread.start( ) ;
				
				adapterDataGenerator = null ;
				
				Thread.sleep( 10 * 1000 ) ;
			}
			
		} catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		} finally {
			setDemonLive( false ) ;
			PropertyLoader.PROCESS_THREAD_CNT-- ;
			
		}
		
	}
	
	public boolean isDemonLive( ) {
		return isDemonLive ;
	}
	
	public void setDemonLive( boolean isDemonLive ) {
		this.isDemonLive = isDemonLive ;
	}
	
}
