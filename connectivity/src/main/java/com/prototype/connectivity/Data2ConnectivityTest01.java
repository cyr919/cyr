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
 * connectivity가 queue의 데이터를 받는다.
 * </pre>
 *
 * @author cyr
 * @date 2020-03-25
 */
public class Data2ConnectivityTest01
{
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private static final Logger logger = LogManager.getLogger( Data2ConnectivityTest01.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	private final static String QUEUE_NAME = "hello" ;
	
	public static void main( String[ ] args ) {
		
		ConnectionFactory factory = new ConnectionFactory( ) ;
		factory.setHost( "192.168.56.105" ) ;
		factory.setUsername( "admin" ) ;
		factory.setPassword( "admin" ) ;
		
		try {
			Connection connection = factory.newConnection( ) ;
			Channel channel = connection.createChannel( ) ;
			
			channel.queueDeclare( QUEUE_NAME , false , false , false , null ) ;
			
			logger.debug( " [*] Waiting for messages. To exit press CTRL+C" ) ;
			
			Consumer consumer = new DefaultConsumer( channel ) {
				@Override
				public void handleDelivery( String consumerTag , Envelope envelope , AMQP.BasicProperties properties , byte[ ] body ) throws IOException {
					String message = new String( body , "UTF-8" ) ;
					logger.debug( " [x] Received '" + message + "'" ) ;
				}
			} ;
			channel.basicConsume( QUEUE_NAME , true , consumer ) ;
			
		} catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		
	}
	
}
