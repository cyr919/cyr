
package com.demo.rabbitmq ;

import java.io.IOException ;

import org.apache.log4j.Logger ;

import com.rabbitmq.client.Channel ;
import com.rabbitmq.client.Connection ;
import com.rabbitmq.client.ConnectionFactory ;

/**
 *
 * <pre>
 * http://previous.rabbitmq.com/v3_5_7/tutorials
 * http://previous.rabbitmq.com/v3_5_7/tutorials/tutorial-one-java.html
 * amqp-client 4.1.0
 * </pre>
 *
 * @author cyr
 * @date 2020-03-25
 */
public class RabbitmqSimplestSending
{

	static Logger logger = Logger.getLogger( RabbitmqSimplestSending.class ) ;

	private final static String QUEUE_NAME = "hello" ;

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

			channel.queueDeclare( QUEUE_NAME , false , false , false , null ) ;
			String message = "Hello World!" ;
			channel.basicPublish( "" , QUEUE_NAME , null , message.getBytes( ) ) ;
			logger.info( " [x] Sent '" + message + "'" ) ;

		}
		catch ( IOException e )
		{
			logger.error( e.getMessage( ) , e ) ;
		}
		catch ( Exception e )
		{
			logger.error( e.getMessage( ) , e ) ;
		}

	}

}
