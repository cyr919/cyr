package com.prototype.adapter ;

import org.apache.log4j.Logger ;

import com.prototype.PropertyLoader ;

public class AdapterDataSimulTest implements Runnable
{
	
	static Logger logger = Logger.getLogger( AdapterDataSimulTest.class ) ;
	
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
				
				Thread.sleep( 10000 ) ;
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
