package com.prototype.adapter ;

import java.util.HashMap ;
import java.util.Map ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.common.ConnectivityProperties ;
import com.prototype.PropertyLoader ;

public class AdapterDataSimulTest implements Runnable
{
	
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private Logger logger = LogManager.getLogger( AdapterDataSimulTest.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	private boolean isDemonLive = false ;
	
	@Override
	public void run( ) {
		
		Map< String , Map< String , Object > > staticDiviceInfo = new HashMap< String , Map<String,Object> >( );
		AdapterDataGenerator adapterDataGenerator = null ;
		try {
			PropertyLoader.PROCESS_THREAD_CNT++ ;
			
			setDemonLive( true ) ;
			
			logger.debug( "isDemonLive :: " + isDemonLive ) ;
			
			while( isDemonLive ) {
				
				staticDiviceInfo = ConnectivityProperties.STDV_INF ;
				
				logger.debug( "staticDiviceInfo :: " + staticDiviceInfo ) ;
				
				for( String key : staticDiviceInfo.keySet( ) ) {
					adapterDataGenerator = new AdapterDataGenerator( key ) ;
					Thread dgThread = new Thread( adapterDataGenerator , "adapterDataGeneratorThread" ) ;
					
					dgThread.start( ) ;
					
					adapterDataGenerator = null ;
					
				}
				
				Thread.sleep( 100 ) ;
			}
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			setDemonLive( false ) ;
			PropertyLoader.PROCESS_THREAD_CNT-- ;
			staticDiviceInfo = null ;
			adapterDataGenerator = null ;
		}
		
	}
	
	public boolean isDemonLive( ) {
		return isDemonLive ;
	}
	
	public void setDemonLive( boolean isDemonLive ) {
		this.isDemonLive = isDemonLive ;
	}
	
}
