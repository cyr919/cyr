
package com.demo.rabbitmq ;

import java.io.IOException ;

import org.apache.log4j.Logger ;

import com.rabbitmq.client.AMQP ;
import com.rabbitmq.client.Channel ;
import com.rabbitmq.client.Connection ;
import com.rabbitmq.client.ConnectionFactory ;
import com.rabbitmq.client.Consumer ;
import com.rabbitmq.client.DefaultConsumer ;
import com.rabbitmq.client.Envelope ;

public class RabbitmqWorkQueuesWorker1
{

	static Logger logger = Logger.getLogger( RabbitmqWorkQueuesWorker1.class ) ;

	private final static String QUEUE_NAME = "hello" ;

	public static void main( String[ ] args )
	{
		// TODO Auto-generated method stub

		ConnectionFactory factory = null ;
		Connection connection = null ;
		Channel channel = null ;
		Consumer consumer = null ;
		try
		{
			factory = new ConnectionFactory( ) ;
			factory.setHost( "192.168.56.104" ) ;
			factory.setUsername( "admin" ) ;
			factory.setPassword( "admin" ) ;

			connection = factory.newConnection( ) ;
			channel = connection.createChannel( ) ;

			channel.queueDeclare( QUEUE_NAME , false , false , false , null ) ;
			logger.info( " [*] Waiting for messages. To exit press CTRL+C" ) ;

			consumer = new DefaultConsumer( channel ) {

				@Override
				public void handleDelivery( String consumerTag , Envelope envelope , AMQP.BasicProperties properties , byte[ ] body ) throws IOException
				{
					String message = new String( body , "UTF-8" ) ;
					logger.info( " [x] Received '" + message + "'" ) ;

					try
					{
						doWork( message ) ;
					}
					catch ( InterruptedException e )
					{
						logger.error( e ) ;
					}
					finally
					{
						logger.info( " [x] Done" ) ;
					}
				}
			} ;
			channel.basicConsume( QUEUE_NAME , true , consumer ) ;
		}
		catch ( Exception e )
		{
			logger.error( e ) ;
		}

	}

	@SuppressWarnings( "unused" )
	private static void doWork( String task ) throws InterruptedException
	{
		logger.info( task ) ;

		for ( char ch : task.toCharArray( ) )
		{
			if ( ch == '.' ) Thread.sleep( 1000 ) ;
		}
	}

}
