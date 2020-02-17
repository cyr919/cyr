
package com.demo.rabbitmq ;

import org.apache.log4j.Logger ;

import com.rabbitmq.client.Channel ;
import com.rabbitmq.client.Connection ;
import com.rabbitmq.client.ConnectionFactory ;

public class RabbitmqWorkQueuesNewTask1_2
{

	static Logger logger = Logger.getLogger( RabbitmqWorkQueuesNewTask1_2.class ) ;

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
			// String message = getMessage( args ) ;
			channel.basicPublish( "" , QUEUE_NAME , null , message.getBytes( ) ) ;
			logger.info( " [x] Sent '" + message + "'" ) ;


		}
		catch ( Exception e )
		{
			logger.error( e ) ;
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
