
package com.demo.rabbitmq ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.rabbitmq.client.Channel ;
import com.rabbitmq.client.Connection ;
import com.rabbitmq.client.ConnectionFactory ;

/**
 * com.demo.rabbitmq.RabbitmqWorkQueuesNewTask1.java
 * 
 * <pre>
 * http://previous.rabbitmq.com/v3_5_7/tutorials/tutorial-two-java.html
 * </pre>
 * 
 * @author cyr
 * @Date 2020. 2. 17.
 */
public class RabbitmqWorkQueuesNewTask1
{
	
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private static Logger logger = LogManager.getLogger( RabbitmqWorkQueuesNewTask1.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	private final static String QUEUE_NAME = "hello" ;
	
	public static void main( String[ ] args ) {
		ConnectionFactory factory = null ;
		Connection connection = null ;
		Channel channel = null ;
		try {
			factory = new ConnectionFactory( ) ;
			factory.setHost( "192.168.56.104" ) ;
			factory.setUsername( "admin" ) ;
			factory.setPassword( "admin" ) ;
			
			connection = factory.newConnection( ) ;
			channel = connection.createChannel( ) ;
			
			channel.queueDeclare( QUEUE_NAME , false , false , false , null ) ;
			
			// String message = "Hello World!" ;
			String message = getMessage( args ) ;
			channel.basicPublish( "" , QUEUE_NAME , null , message.getBytes( ) ) ;
			logger.info( " [x] Sent '" + message + "'" ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		
	}
	
	private static String getMessage( String[ ] strings ) {
		if( strings.length < 1 )
			return "Hello World!" ;
		return joinStrings( strings , " " ) ;
	}
	
	private static String joinStrings( String[ ] strings , String delimiter ) {
		int length = strings.length ;
		if( length == 0 )
			return "" ;
		StringBuilder words = new StringBuilder( strings[ 0 ] ) ;
		for( int i = 1 ; i < length ; i++ ) {
			words.append( delimiter ).append( strings[ i ] ) ;
		}
		return words.toString( ) ;
	}
}
