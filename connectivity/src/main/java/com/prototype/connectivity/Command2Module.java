package com.prototype.connectivity ;

import java.io.IOException ;

import org.apache.log4j.Logger ;

import com.connectivity.utils.JsonUtil ;
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
 * connectivity가 queue의 데이터를 받는다.(from ServiceHUB)
 * </pre>
 *
 * @author cyr
 * @date 2020-04-06
 */
public class Command2Module implements Runnable
{
	static Logger logger = Logger.getLogger( Command2Module.class ) ;
	
	private final static String TASK_QUEUE_NAME = "Command2Module" ;
	
	private ConnectionFactory connectionFactory = null ;
	private String strDeviceName = "" ;
	
	private void doWork( String strSubMessage ) throws Exception {
		
		JsonUtil jsonUtil = new JsonUtil( ) ;
		
		try {
			
			
			
			Thread.sleep( 1000 ) ;
		} catch( InterruptedException _ignored ) {
			Thread.currentThread( ).interrupt( ) ;
		} finally {
			strSubMessage = null ;
		}
		
	}
	
	public Command2Module( ConnectionFactory connectionFactory , String param ) {
		
		this.connectionFactory = connectionFactory ;
		this.strDeviceName = param ;
		
		logger.info( "connectionFactory :: " + connectionFactory ) ;
		logger.info( "param :: " + param ) ;
		
	}
	
	@Override
	public void run( ) {
		// TODO Auto-generated method stub
		
		// ConnectionFactory factory = new ConnectionFactory( ) ;
		// factory.setHost( "192.168.56.105" ) ;
		// factory.setUsername( "admin" ) ;
		// factory.setPassword( "admin" ) ;
		
		ConnectionFactory factory = this.connectionFactory ;
		
		logger.info( "this.connectionFactory :: " + this.connectionFactory ) ;
		logger.info( "this.strDeviceName :: " + this.strDeviceName ) ;
		
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
						channel.basicAck( envelope.getDeliveryTag( ) , false ) ;
					
					} catch( Exception e ) {
						logger.error( e.getMessage( ) , e ) ;
					}
					finally {
						logger.info( " [x] Done" ) ;
						message = null ;
					}
				}
			} ;
			channel.basicConsume( TASK_QUEUE_NAME , false , consumer ) ;
			
		} catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
			
		} finally {
			logger.info( "run finally" ) ;
		}
		
	}
	
}
