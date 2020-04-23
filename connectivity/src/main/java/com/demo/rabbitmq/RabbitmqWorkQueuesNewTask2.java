
package com.demo.rabbitmq ;

import java.io.IOException ;
import java.util.concurrent.TimeoutException ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.rabbitmq.client.Channel ;
import com.rabbitmq.client.Connection ;
import com.rabbitmq.client.ConnectionFactory ;
import com.rabbitmq.client.MessageProperties ;

/**
 * com.demo.rabbitmq.RabbitmqWorkQueuesNewTask1_2.java
 * 
 * <pre>
 * http://previous.rabbitmq.com/v3_5_7/tutorials/tutorial-two-java.html
 * </pre>
 * 
 * @author cyr
 * @Date 2020. 2. 17.
 */
public class RabbitmqWorkQueuesNewTask2
{

	
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private static final Logger logger = LogManager.getLogger( RabbitmqWorkQueuesNewTask2.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	

	private final static String QUEUE_NAME = "task_queue" ;

	public static void main( String[ ] args )
	{
		ConnectionFactory factory = null ;
		Connection connection = null ;
		Channel channel = null ;
		try
		{

			factory = new ConnectionFactory( ) ;
			factory.setHost( "192.168.56.104" ) ;
			factory.setUsername( "admin" ) ;
			factory.setPassword( "admin" ) ;

			connection = factory.newConnection( ) ;
			channel = connection.createChannel( ) ;

			channel.queueDeclare( QUEUE_NAME , true , false , false , null ) ;

			String message = "Hello World!" ;
			// String message = getMessage( args ) ;
			
			channel.basicPublish( "" , QUEUE_NAME , MessageProperties.PERSISTENT_TEXT_PLAIN , message.getBytes( ) ) ;
			logger.info( " [x] Sent '" + message + "'" ) ;

		}
		catch ( Exception e )
		{
			logger.error( e.getMessage( ) , e ) ;
		}
		finally
		{

			try
			{
				channel.close( ) ;
			}
			catch ( IOException e )
			{
				logger.error( e.getMessage( ) , e ) ;
				e.printStackTrace( ) ;
			}
			catch ( TimeoutException e )
			{
				logger.error( e.getMessage( ) , e ) ;
				e.printStackTrace( ) ;
			}

			try
			{
				connection.close( ) ;
			}
			catch ( IOException e )
			{
				logger.error( e.getMessage( ) , e ) ;
				e.printStackTrace( ) ;
			}
		}

	}

	private static String getMessage( String[ ] strings )
	{
		if ( strings.length < 1 ) return "Hello World!" ;
		return joinStrings( strings , " " ) ;
	}

	private static String joinStrings( String[ ] strings , String delimiter )
	{
		int length = strings.length ;
		if ( length == 0 ) return "" ;
		StringBuilder words = new StringBuilder( strings[ 0 ] ) ;
		for ( int i = 1 ; i < length ; i++ )
		{
			words.append( delimiter ).append( strings[ i ] ) ;
		}
		return words.toString( ) ;
	}
}
