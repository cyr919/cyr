package com.prototype.adapter ;

import java.io.IOException ;
import java.util.concurrent.TimeoutException ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.rabbitmq.client.Channel ;
import com.rabbitmq.client.Connection ;
import com.rabbitmq.client.ConnectionFactory ;

/**
 *
 * <pre>
 * adatper에서 queue로 데이터를 전송한다.
 * 
 * 
 * </pre>
 *
 * @author cyr
 * @date 2020-03-25
 */
public class Data2ConnectivityTest01
{
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private static final Logger logger = LogManager.getLogger( Data2ConnectivityTest01.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	
	private final static String QUEUE_NAME = "hello" ;
	
	public static void main( String[ ] args ) {
		
		ConnectionFactory factory = new ConnectionFactory( ) ;
		factory.setHost( "192.168.56.105" ) ;
		factory.setUsername( "admin" ) ;
		factory.setPassword( "admin" ) ;
		
		try {
			Connection connection = factory.newConnection( ) ;
			Channel channel = connection.createChannel( ) ;
			
			channel.queueDeclare( QUEUE_NAME , false , false , false , null ) ;
			
			String message = "Hello World!" ;
			
			channel.basicPublish( "" , QUEUE_NAME , null , message.getBytes( ) ) ;
			
			logger.debug( " [x] Sent '" + message + "'" ) ;
			
		} catch( IOException e ) {
			logger.error( e.getMessage( ) , e ) ;
		} catch( TimeoutException e ) {
			logger.error( e.getMessage( ) , e ) ;
		} catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		} finally {
			factory = null ;
		}
		
	}
	
}
