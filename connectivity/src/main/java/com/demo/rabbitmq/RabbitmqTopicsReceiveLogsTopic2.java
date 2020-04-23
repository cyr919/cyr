
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
 * com.demo.rabbitmq.RabbitmqTopicsReceiveLogsTopic.java
 * 
 * <pre>
 * http://previous.rabbitmq.com/v3_5_7/tutorials/tutorial-five-java.html
 * </pre>
 * 
 * @author cyr
 * @Date 2020. 2. 21.
 */
public class RabbitmqTopicsReceiveLogsTopic2
{
	
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private static final Logger logger = LogManager.getLogger( RabbitmqTopicsReceiveLogsTopic2.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	private static final String EXCHANGE_NAME = "topic_logs" ;
	
	public static void main( String[ ] args ) {
		// TODO Auto-generated method stub
		
		ConnectionFactory factory = null ;
		Connection connection = null ;
		Channel channel = null ;
		String queueName = "" ;
		Consumer consumer = null ;
		
		String[ ] bindingKeyArr = { "*.erorr" } ;
		int i = 0 ;
		
		try {
			factory = new ConnectionFactory( ) ;
			factory.setHost( "192.168.56.104" ) ;
			factory.setUsername( "admin" ) ;
			factory.setPassword( "admin" ) ;
			
			connection = factory.newConnection( ) ;
			channel = connection.createChannel( ) ;
			
			channel.exchangeDeclare( EXCHANGE_NAME , "topic" ) ;
			
			queueName = channel.queueDeclare( ).getQueue( ) ;
			
			for( i = 0 ; i < bindingKeyArr.length ; i++ ) {
				channel.queueBind( queueName , EXCHANGE_NAME , bindingKeyArr[ i ] ) ;
				logger.info( "queueBind :: routingKeyArr[ " + i + " ] :: " + bindingKeyArr[ i ] ) ;
			}
			
			logger.info( " [*] Waiting for messages. To exit press CTRL+C" ) ;
			
			// channel.basicQos( 1 ) ;
			
			consumer = new DefaultConsumer( channel ) {
				
				@Override
				public void handleDelivery( String consumerTag , Envelope envelope , AMQP.BasicProperties properties , byte[ ] body ) throws IOException {
					String message = new String( body , "UTF-8" ) ;
					logger.info( " [x] Received '" + envelope.getRoutingKey( ) + "':'" + message + "'" ) ;
					
					try {
						doWork( message ) ;
					} finally {
						logger.info( " [x] Done" ) ;
						// channel.basicAck( envelope.getDeliveryTag( ) , false ) ;
						
					}
				}
			} ;
			channel.basicConsume( queueName , true , consumer ) ;
		} catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		
	}
	
	private static void doWork( String task ) {
		logger.info( "doWork :: [x] start" ) ;
		logger.info( task ) ;
		
		try {
			
			Thread.sleep( 10000 ) ;
			// logger.info( "Thread.sleep :: " + ch ) ;
			
		} catch( InterruptedException e ) {
			logger.error( e.getMessage( ) , e ) ;
			Thread.currentThread( ).interrupt( ) ;
		}
		
		logger.info( "doWork :: [x] Done" ) ;
		
	}
	
}
