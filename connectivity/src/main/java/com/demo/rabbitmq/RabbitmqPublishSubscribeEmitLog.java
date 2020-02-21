
package com.demo.rabbitmq ;

import java.io.IOException ;
import java.util.concurrent.TimeoutException ;

import org.apache.log4j.Logger ;

import com.rabbitmq.client.Channel ;
import com.rabbitmq.client.Connection ;
import com.rabbitmq.client.ConnectionFactory ;


/**
 * com.demo.rabbitmq.RabbitmqPublishSubscribeEmitLog.java
 * <pre>
 * http://previous.rabbitmq.com/v3_5_7/tutorials/tutorial-three-java.html
 * </pre>
 * @author cyr
 * @Date 2020. 2. 21.
 */
public class RabbitmqPublishSubscribeEmitLog
{

	static Logger logger = Logger.getLogger( RabbitmqPublishSubscribeEmitLog.class ) ;

	// private final static String QUEUE_NAME = "task_queue" ;
	private final static String EXCHANGE_NAME = "logs" ;

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

			channel.exchangeDeclare( EXCHANGE_NAME , "fanout" ) ;
			// channel.queueDeclare( QUEUE_NAME , true , false , false , null ) ;

			String message = "Hello World!" ;
			// String message = getMessage( args ) ;

			channel.basicPublish( EXCHANGE_NAME , "" , null , message.getBytes( ) ) ;
			// channel.basicPublish( "" , QUEUE_NAME , MessageProperties.PERSISTENT_TEXT_PLAIN , message.getBytes( ) ) ;
			logger.info( " [x] Sent '" + message + "'" ) ;

		}
		catch ( Exception e )
		{
			logger.error( e ) ;
		}
		finally
		{

			try
			{
				channel.close( ) ;
			}
			catch ( IOException e )
			{
				logger.error( e ) ;
				e.printStackTrace( ) ;
			}
			catch ( TimeoutException e )
			{
				logger.error( e ) ;
				e.printStackTrace( ) ;
			}

			try
			{
				connection.close( ) ;
			}
			catch ( IOException e )
			{
				logger.error( e ) ;
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
