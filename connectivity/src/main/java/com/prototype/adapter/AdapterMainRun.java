package com.prototype.adapter ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.common.ConnectivityProperties ;
import com.rabbitmq.client.Connection ;
import com.rabbitmq.client.ConnectionFactory ;

public class AdapterMainRun
{
	
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private static final Logger logger = LogManager.getLogger( AdapterMainRun.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	private static Connection pubConnection ;
	
	/**
	 * adapter 데이터 생성기...
	 * 
	 * @param args
	 */
	public static void main( String[ ] args ) {
		
		try {
			
			ConnectivityProperties connectivityProperties = new ConnectivityProperties( ) ;
			connectivityProperties.setStdv( ) ;
			
			AdapterMainRun exe = new AdapterMainRun( ) ;
			
			exe.getPubConnection( ) ;
			
			AdapterDataSimulTest adapterDataSimulTest = new AdapterDataSimulTest( ) ;
			Thread dsThread = new Thread( adapterDataSimulTest , "adapterDataSimulTestThread" ) ;
			
			dsThread.start( ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			
		}
		
	}
	
	public Connection setConnection( ) {
		
		ConnectionFactory connectionFactory = null ;
		Connection connection = null ;
		
		try {
			connectionFactory = new ConnectionFactory( ) ;
			
			connectionFactory.setHost( "192.168.56.105" ) ;
			connectionFactory.setUsername( "admin" ) ;
			connectionFactory.setPassword( "admin" ) ;
			
			connection = connectionFactory.newConnection( ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			connectionFactory = null ;
		}
		
		return connection ;
	}
	
	public Connection getPubConnection( ) {
		if( null == pubConnection || !pubConnection.isOpen( ) ) {
			pubConnection = setConnection( ) ;
		}
		return pubConnection ;
		
	}
}
