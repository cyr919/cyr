
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

/**
 *
 * <pre>
 * http://previous.rabbitmq.com/v3_5_7/tutorials
 * amqp-client 4.1.0
 * </pre>
 *
 * @author cyr
 * @date 2020-03-25
 */
public class RabbitmqSimplestReceiving
{

	static Logger logger = Logger.getLogger( RabbitmqSimplestReceiving.class ) ;

	private final static String QUEUE_NAME = "hello" ;

	public static void main( String[ ] args ) throws InterruptedException
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
				}
			} ;
			channel.basicConsume( QUEUE_NAME , true , consumer ) ;
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
