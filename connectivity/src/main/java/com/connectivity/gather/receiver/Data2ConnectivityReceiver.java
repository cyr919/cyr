package com.connectivity.gather.receiver ;

import java.io.IOException ;
import java.util.concurrent.TimeoutException ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.gather.DataGather ;
import com.rabbitmq.client.AMQP ;
import com.rabbitmq.client.Channel ;
import com.rabbitmq.client.Connection ;
import com.rabbitmq.client.ConnectionFactory ;
import com.rabbitmq.client.Consumer ;
import com.rabbitmq.client.DefaultConsumer ;
import com.rabbitmq.client.Envelope ;

/**
 * <pre>
 * Work Queues
 * connectivity가 queue의 데이터를 받는다.
 * device ID 별로 Queue가 생성한다.
 * </pre>
 *
 * @author cyr
 * @date 2020-04-06
 */
public class Data2ConnectivityReceiver implements Runnable
{
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	Logger logger = LogManager.getLogger( Data2ConnectivityReceiver.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	private String TASK_QUEUE_NAME = "Data2Connectivity" ;
	
	private ConnectionFactory connectionFactory = null ;
	private Connection connection = null ;
	private Channel channel = null ;
	
	public Data2ConnectivityReceiver( ConnectionFactory connectionFactory , String strDeviceName ) {
		
		this.connectionFactory = connectionFactory ;
		
		logger.info( "connectionFactory :: " + connectionFactory ) ;
		logger.info( "strDeviceName :: " + strDeviceName ) ;
		
		this.TASK_QUEUE_NAME = this.TASK_QUEUE_NAME + "_" + strDeviceName ;
		
		logger.info( "this.TASK_QUEUE_NAME :: " + this.TASK_QUEUE_NAME ) ;
		
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
		
		logger.info( "this.connectionFactory :: " + this.connectionFactory ) ;
		logger.info( "this.TASK_QUEUE_NAME :: " + this.TASK_QUEUE_NAME ) ;
		
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
					
					logger.info( " [x] Received :: " + message + "" ) ;
					
					try {
						doWork( message ) ;
					}
					catch( Exception e ) {
						logger.error( e.getMessage( ) , e ) ;
						
					}
					finally {
						channel.basicAck( envelope.getDeliveryTag( ) , false ) ;
						message = null ;
						logger.info( " [x] Done" ) ;
					}
				}
			} ;
			
			this.channel.basicConsume( TASK_QUEUE_NAME , false , consumer ) ;
			logger.info( "==========this.channel.basicConsume( TASK_QUEUE_NAME , false , consumer ) ;" ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
			
		}
		finally {
			logger.info( "run finally" ) ;
			factory = null ;
		}
		
		return ;
	}
	
	public Boolean doWork( String strSubMessage ) {
		
		Boolean resultBool = true ;
		DataGather dataGather = new DataGather( ) ;
		
		try {
			// logger.debug( "strSubMessage :: " + strSubMessage ) ;
			
			resultBool = dataGather.dataGathering( strSubMessage ) ;
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strSubMessage = null ;
			dataGather = null ;
		}
		
		return resultBool ;
	}
	
	public void connectionClose( ) {
		
		try {
			logger.info( "================connectionCut================" ) ;
			
			if( this.channel != null ) {
				try {
					this.channel.close( ) ;
				}
				catch( IOException e ) {
					logger.error( e.getMessage( ) , e ) ;
				}
				catch( TimeoutException e ) {
					logger.error( e.getMessage( ) , e ) ;
				}
			}
			if( this.connection != null ) {
				try {
					this.connection.close( ) ;
				}
				catch( IOException e ) {
					logger.error( e.getMessage( ) , e ) ;
				}
			}
			this.connectionFactory = null ;
			this.connection = null ;
			this.channel = null ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		
		return ;
	}
	
}
