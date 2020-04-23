
package com.demo.rabbitmq ;

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
 * com.demo.rabbitmq.RabbitmqWorkQueuesWorker1_2.java
 * 
 * <pre>
 * http://previous.rabbitmq.com/v3_5_7/tutorials/tutorial-two-java.html
 * </pre>
 * 
 * @author cyr
 * @Date 2020. 2. 17.
 */
public class RabbitmqWorkQueuesWorker2
{
	
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private static final Logger logger = LogManager.getLogger( RabbitmqWorkQueuesWorker2.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	private final static String QUEUE_NAME = "task_queue" ;
	
	public static void main( String[ ] args ) {
		// TODO Auto-generated method stub
		
		ConnectionFactory factory = new ConnectionFactory( ) ;
		
		try {
			factory.setHost( "192.168.56.104" ) ;
			factory.setUsername( "admin" ) ;
			factory.setPassword( "admin" ) ;
			
			final Connection connection = factory.newConnection( ) ;
			final Channel channel = connection.createChannel( ) ;
			
			channel.queueDeclare( QUEUE_NAME , true , false , false , null ) ;
			
			logger.info( " [*] Waiting for messages. To exit press CTRL+C" ) ;
			
			channel.basicQos( 1 ) ;
			
			final Consumer consumer = new DefaultConsumer( channel ) {
				
				@Override
				public void handleDelivery( String consumerTag , Envelope envelope , AMQP.BasicProperties properties , byte[ ] body ) throws IOException {
					String message = new String( body , "UTF-8" ) ;
					logger.info( " [x] Received '" + message + "'" ) ;
					
					try {
						doWork( message ) ;
					}
					finally {
						logger.info( " [x] Done" ) ;
						channel.basicAck( envelope.getDeliveryTag( ) , false ) ;
						
					}
				}
			} ;
			channel.basicConsume( QUEUE_NAME , false , consumer ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		
	}
	
	private static void doWork( String task ) {
		logger.info( task ) ;
		
		for( char ch : task.toCharArray( ) ) {
			if( ch == '.' ) {
				try {
					
					Thread.sleep( 1000 ) ;
					
				}
				catch( InterruptedException e ) {
					logger.error( e.getMessage( ) , e ) ;
					Thread.currentThread( ).interrupt( ) ;
				}
			}
		}
	}
	
}
