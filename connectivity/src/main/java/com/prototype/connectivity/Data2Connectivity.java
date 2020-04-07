package com.prototype.connectivity ;

import java.io.IOException ;
import java.util.concurrent.TimeoutException ;

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
 * Work Queues
 * connectivity가 queue의 데이터를 받는다.
 * </pre>
 *
 * @author cyr
 * @date 2020-04-06
 */
public class Data2Connectivity implements Runnable
{
	static Logger logger = Logger.getLogger( Data2Connectivity.class ) ;
	
	private final static String TASK_QUEUE_NAME = "Data2Connectivity" ;
	
	private String strDeviceName = "" ;
	
	private ConnectionFactory connectionFactory = null ;
	private Connection connection = null ;
	private Channel channel = null ;
	
	
	private static void doWork( String task ) {
		
		try {
			Thread.sleep( 1000 ) ;
		} catch( InterruptedException _ignored ) {
			Thread.currentThread( ).interrupt( ) ;
		}
		
		return ;
	}
	
	public Data2Connectivity(  ConnectionFactory connectionFactory , String param ) {
		
		this.connectionFactory = connectionFactory ;
		this.strDeviceName = param ;
		
		logger.info( "connectionFactory :: " + connectionFactory );
		logger.info( "param :: " + param );
		
		return ;
	}
	
	@Override
	public void run( ) {
		// TODO Auto-generated method stub
		
		// ConnectionFactory factory = new ConnectionFactory( ) ;
		// factory.setHost( "192.168.56.105" ) ;
		// factory.setUsername( "admin" ) ;
		// factory.setPassword( "admin" ) ;
		
		ConnectionFactory factory = this.connectionFactory ; 
		
		logger.info( "this.connectionFactory :: " + this.connectionFactory );
		logger.info( "this.strDeviceName :: " + this.strDeviceName );
		
		
		try {
			this.connection = factory.newConnection( ) ;
			this.channel = this.connection.createChannel( ) ;
			
			this.channel.queueDeclare( TASK_QUEUE_NAME , true , false , false , null ) ;
			logger.info( " [*] Waiting for messages. To exit press CTRL+C" ) ;
			
			this.channel.basicQos( 1 ) ;
			
			final Consumer consumer = new DefaultConsumer( this.channel ) {
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
			
			logger.info( "==========this.channel.basicConsume( TASK_QUEUE_NAME , false , consumer ) ;" ) ;

			this.channel.basicConsume( TASK_QUEUE_NAME , false , consumer ) ;
			
		} catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
			
		} finally {
			logger.info( "run finally" );
		}
		
		return ;
	}
	
	
	public void connectionCut( ) {
		
		try {
			logger.info( "================connectionCut================" ) ;
			
			if( this.channel != null ) {
				try {
					this.channel.close( ) ;
				} catch( IOException e ) {
					logger.error( e.getMessage( ) , e ) ;
				} catch( TimeoutException e ) {
					logger.error( e.getMessage( ) , e ) ;
				}
			}
			if( this.connection != null ) {
				try {
					this.connection.close( ) ;
				} catch( IOException e ) {
					logger.error( e.getMessage( ) , e ) ;
				}
			}
			this.connectionFactory = null ;
			this.connection = null ;
			this.channel = null ;
			
		} catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		
		return ;
	}
	
}
