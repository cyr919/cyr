package com.prototype.adapter ;

import java.io.IOException ;
import java.util.concurrent.TimeoutException ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.rabbitmq.client.Channel ;
import com.rabbitmq.client.Connection ;
import com.rabbitmq.client.ConnectionFactory ;
import com.rabbitmq.client.MessageProperties ;

/**
 *
 * <pre>
 * Work Queues
 * adatper에서 queue로 데이터를 전송한다.
 * </pre>
 *
 * @author cyr
 * @date 2020-04-06
 */
public class Data2ConnectivityTest02
{
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private static final Logger logger = LogManager.getLogger( Data2ConnectivityTest02.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	private static final String TASK_QUEUE_NAME = "task_queue" ;
	
	public static void main( String[ ] args ) {
		
		ConnectionFactory factory = new ConnectionFactory( ) ;
		factory.setHost( "192.168.56.105" ) ;
		factory.setUsername( "admin" ) ;
		factory.setPassword( "admin" ) ;
		
		try {
			Connection connection = factory.newConnection( ) ;
			Channel channel = connection.createChannel( ) ;
			
			channel.queueDeclare( TASK_QUEUE_NAME , true , false , false , null ) ;
			
			String message = "hellow mesage" ;
			
			channel.basicPublish( "" , TASK_QUEUE_NAME , MessageProperties.PERSISTENT_TEXT_PLAIN , message.getBytes( ) ) ;
			logger.info( " [x] Sent '" + message + "'" ) ;
			
			channel.close( ) ;
			connection.close( ) ;
			
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
