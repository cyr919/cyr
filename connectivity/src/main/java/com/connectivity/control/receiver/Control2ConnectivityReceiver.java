/**
 * 
 */
package com.connectivity.control.receiver ;

import java.io.IOException ;
import java.util.concurrent.TimeoutException ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.common.ConnectivityProperties ;
import com.rabbitmq.client.AMQP ;
import com.rabbitmq.client.Channel ;
import com.rabbitmq.client.Connection ;
import com.rabbitmq.client.ConnectionFactory ;
import com.rabbitmq.client.Consumer ;
import com.rabbitmq.client.DefaultConsumer ;
import com.rabbitmq.client.Envelope ;

/**
 * <pre>
 * rabbitMQ Receiver
 * Control2Connectivity queue
 * </pre>
 *
 * @author cyr
 * @date 2020-06-18
 */
public class Control2ConnectivityReceiver implements Runnable
{
	
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private Logger logger = LogManager.getLogger( this.getClass( ) ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	private String TASK_QUEUE_NAME = "Control2Connectivity" ;
	
	private ConnectionFactory connectionFactory = null ;
	private Connection connection = null ;
	private Channel channel = null ;
	
	public Control2ConnectivityReceiver( ConnectionFactory connectionFactory ) {
		
		this.connectionFactory = connectionFactory ;
		
		logger.info( "connectionFactory :: " + connectionFactory ) ;
	}
	
	@Override
	public void run( ) {
		
		ConnectionFactory factory = this.connectionFactory ;
		
		logger.info( "this.connectionFactory :: " + this.connectionFactory ) ;
		
		try {
			this.connection = factory.newConnection( ) ;
			this.channel = this.connection.createChannel( ) ;
			
			this.channel.queueDeclare( TASK_QUEUE_NAME , true , false , false , null ) ;
			logger.info( " [*] Waiting for messages. To exit press CTRL+C" ) ;
			
			this.channel.basicQos( 1 ) ;
			
			final Consumer consumer = new DefaultConsumer( this.channel ) {
				@Override
				public void handleDelivery( String consumerTag , Envelope envelope , AMQP.BasicProperties properties , byte[ ] body ) throws IOException {
					
					String message = "" ;
					ConnectivityProperties connectivityProperties = new ConnectivityProperties( ) ;
					
					try {
						logger.info( "Control2ConnectivityReceiver handleDelivery" ) ;
						connectivityProperties.addOneProcessThreadCnt( ) ;
						
						message = new String( body , "UTF-8" ) ;
						logger.info( " [x] Received '" + message + "'" ) ;
						
						doWork( message ) ;
						
					}
					catch( Exception e ) {
						logger.error( e.getMessage( ) , e ) ;
					}
					finally {
						// ack 날리기, channel 이 끊어 진 후에는 기존 채널이 아니게 되므로 수신 되면 ack를 날리고 실패시 이벤트 처리(성공/실패시) 이벤트 처리해야함.
						channel.basicAck( envelope.getDeliveryTag( ) , false ) ;
						connectivityProperties.subtractOneProcessThreadCnt( ) ;
						message = null ;
						connectivityProperties = null ;
						consumerTag = null ;
						envelope = null ;
						properties = null ;
						body = null ;
						logger.info( "Control2ConnectivityReceiver handleDelivery finally" ) ;
					}
				}
			} ;
			
			logger.info( "==========this.channel.basicConsume( TASK_QUEUE_NAME , false , consumer ) ;" ) ;
			this.channel.basicConsume( TASK_QUEUE_NAME , false , consumer ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
			
		}
		finally {
			factory = null ;
			logger.info( "run finally" ) ;
		}
		
	}
	
	// private static void doWork( String strSubMessage , Channel channel , Envelope envelope ) throws Exception {
	public Boolean doWork( String strSubMessage ) {
		
		Boolean resultBool = true ;
		try {
			
			logger.info( "doWork :: strSubMessage :: " + strSubMessage ) ;
			
			Thread.sleep( 5 * 1000 ) ;
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strSubMessage = null ;
		}
		
		return resultBool ;
	}
	
	/**
	 * <pre>
	 * 해당 Receiver의 connection 종료
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-18
	 */
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
