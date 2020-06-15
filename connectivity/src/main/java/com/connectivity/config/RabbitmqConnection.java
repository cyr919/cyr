/**
 * 
 */
package com.connectivity.config ;

import java.io.IOException ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.common.CommonProperties ;
import com.rabbitmq.client.Connection ;
import com.rabbitmq.client.ConnectionFactory ;

/**
 * <pre>
 * connection을 공유한다.
 * 다만 하나의 connection으로 하게되면 모든 처리가 하나의 connection에서 이루어지기 때문에 성능상의 이유로
 * 각 기능에 따라서 connection을 각 1개씩 열어 놓는다.
 * </pre>
 *
 * @author cyr
 * @date 2020-04-23
 */
public class RabbitmqConnection
{
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private static Logger logger = LogManager.getLogger( RabbitmqConnection.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	private static Connection eventPubConnection ;
	private static Connection deviceControlPubConnection ;
	
	public ConnectionFactory getConnectionFactory( ) {
		
		ConnectionFactory connectionFactory = null ;
		try {
			
			connectionFactory = new ConnectionFactory( ) ;
			connectionFactory.setHost( CommonProperties.RABBITMQ_IP ) ;
			connectionFactory.setPort( Integer.parseInt( CommonProperties.RABBITMQ_PORT ) ) ;
			connectionFactory.setUsername( CommonProperties.RABBITMQ_USERNAME ) ;
			connectionFactory.setPassword( CommonProperties.RABBITMQ_PASSWORD ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
		}
		
		return connectionFactory ;
	}
	
	public Connection setConnection( ) {
		
		ConnectionFactory connectionFactory = null ;
		Connection connection = null ;
		
		try {
			connectionFactory = getConnectionFactory( ) ;
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
	
	public Connection getEventConnection( ) {
		if( null == eventPubConnection || !eventPubConnection.isOpen( ) ) {
			eventPubConnection = setConnection( ) ;
		}
		return eventPubConnection ;
		
	}
	
	public Connection getDeviceControlConnection( ) {
		if( null == deviceControlPubConnection || !deviceControlPubConnection.isOpen( ) ) {
			deviceControlPubConnection = setConnection( ) ;
		}
		return deviceControlPubConnection ;
		
	}
	
	/**
	 * <pre>
	 * rabbitmq Connection을 확인한다.
	 * 연결이 정상적으로 되어있으면 true, 연결이 null 이거나 없으면 false를 반환한다.
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-04-23
	 * @return
	 */
	public Boolean connectionCheck( ) {
		
		Boolean resutlBool = true ;
		
		try {
			resutlBool = false ;
			
			resutlBool = ( ( null != eventPubConnection && eventPubConnection.isOpen( ) )
							&& ( null != deviceControlPubConnection && deviceControlPubConnection.isOpen( ) ) ) ;
			
			logger.info( "( null == eventPubConnection ) :: " + ( null == eventPubConnection ) ) ;
			logger.info( "( null == deviceControlPubConnection ) :: " + ( null == deviceControlPubConnection ) ) ;
			
			logger.info( "( eventPubConnection.isOpen( ) ) :: " + ( eventPubConnection.isOpen( ) ) ) ;
			logger.info( "( deviceControlPubConnection.isOpen( ) ) :: " + ( deviceControlPubConnection.isOpen( ) ) ) ;
			
		}
		catch( Exception e ) {
			resutlBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
		}
		
		return resutlBool ;
	}
	
	public void closePubConnection( ) {
		
		try {
			if( eventPubConnection != null && eventPubConnection.isOpen( ) ) {
				try {
					eventPubConnection.close( ) ;
				}
				catch( IOException e ) {
					logger.error( e.getMessage( ) , e ) ;
				}
			}
			
			if( deviceControlPubConnection != null && deviceControlPubConnection.isOpen( ) ) {
				try {
					deviceControlPubConnection.close( ) ;
				}
				catch( IOException e ) {
					logger.error( e.getMessage( ) , e ) ;
				}
			}
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			
			eventPubConnection = null ;
			deviceControlPubConnection = null ;
			
		}
		
	}
	
	/**
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-04-23
	 * @param args
	 */
	public static void main( String[ ] args ) {
		
		CommonProperties commonProperties = new CommonProperties( ) ;
		
		Boolean resultBool = true ;
		
		try {
			// resultBool = commonProperties.setProperties( ) ;
			
			if( resultBool ) {
				
				RabbitmqConnection exe = new RabbitmqConnection( ) ;
				
				exe.connectionCheck( ) ;
				
				exe.getEventConnection( ) ;
				
				exe.getDeviceControlConnection( ) ;
				
				logger.info( "연결을 확인한다." ) ;
				
				if( exe.connectionCheck( ) ) {
					logger.info( "연결을 종료한다. 한다." ) ;
					
					exe.closePubConnection( ) ;
					
					exe.connectionCheck( ) ;
					
				}
				
			}
		}
		// catch( Exception e ) {
		// logger.error( e.getMessage( ) , e ) ;
		// }
		finally {
			
		}
		
	}
	
}
