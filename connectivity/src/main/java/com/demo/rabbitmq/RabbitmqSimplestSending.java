
package com.demo.rabbitmq ;

import org.apache.log4j.Logger ;

import com.rabbitmq.client.Channel ;
import com.rabbitmq.client.Connection ;
import com.rabbitmq.client.ConnectionFactory ;

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
		catch ( Exception e )
		{
			logger.error( e ) ;
		}

	}

}
