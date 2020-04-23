package com.prototype.connectivity ;

import java.io.IOException ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

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
 * Work Queues
 * connectivity가 queue의 데이터를 받는다.
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
	
	private final static String TASK_QUEUE_NAME = "task_queue" ;
	
	public static void main( String[ ] args ) {
		
		ConnectionFactory factory = new ConnectionFactory( ) ;
		factory.setHost( "192.168.56.105" ) ;
		factory.setUsername( "admin" ) ;
		factory.setPassword( "admin" ) ;

		try {
			final Connection connection = factory.newConnection( ) ;
			final Channel channel = connection.createChannel( ) ;
			
			channel.queueDeclare( TASK_QUEUE_NAME , true , false , false , null ) ;
			logger.info( " [*] Waiting for messages. To exit press CTRL+C" ) ;
			
			channel.basicQos( 1 ) ;
			
			final Consumer consumer = new DefaultConsumer( channel ) {
				@Override
				public void handleDelivery( String consumerTag , Envelope envelope , AMQP.BasicProperties properties , byte[ ] body ) throws IOException {
					String message = new String( body , "UTF-8" ) ;
					
					logger.info( " [x] Received '" + message + "'" ) ;
					try {
						doWork( message ) ;
					} finally {
						logger.info( " [x] Done" ) ;
						channel.basicAck( envelope.getDeliveryTag( ) , false ) ;
					}
				}
			} ;
			channel.basicConsume( TASK_QUEUE_NAME , false , consumer ) ;
			
		} catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
			
		} finally {
			logger.info( "run finally" );

		}
		
	}
	
	private static void doWork( String task ) {
		
		try {
			Thread.sleep( 1000 ) ;
		} catch( InterruptedException _ignored ) {
			Thread.currentThread( ).interrupt( ) ;
		}
		
	}
	
}
