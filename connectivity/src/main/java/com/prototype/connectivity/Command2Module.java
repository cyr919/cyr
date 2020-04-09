package com.prototype.connectivity ;

import java.io.IOException ;
import java.util.concurrent.TimeoutException ;

import org.apache.log4j.Logger ;
import org.json.simple.JSONObject ;

import com.connectivity.utils.JsonUtil ;
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
 * connectivity가 queue의 데이터를 받는다.(from ServiceHUB)
 * </pre>
 *
 * @author cyr
 * @date 2020-04-06
 */
public class Command2Module implements Runnable
{
	static Logger logger = Logger.getLogger( Command2Module.class ) ;
	
	private String TASK_QUEUE_NAME = "Command2Module" ;
	
	private ConnectionFactory connectionFactory = null ;
	private Connection connection = null ;
	private Channel channel = null ;
	
	// private static void doWork( String strSubMessage , Channel channel , Envelope envelope ) throws Exception {
	public void doWork( String strSubMessage , Channel channel , Envelope envelope ) {
		
		JsonUtil jsonUtil = new JsonUtil( ) ;
		ConnectivityMainRun exe = new ConnectivityMainRun( ) ;
		
		JSONObject msgJSONObject = new JSONObject( ) ;
		String strCommandLower = "" ;
//		String strEventID = "" ;
		
		try {
			
			logger.info( "strSubMessage :: " + strSubMessage ) ;
			
			Thread.sleep( 5 * 1000 ) ;
			
			msgJSONObject = jsonUtil.getJSONObjectFromString( strSubMessage ) ;
			
			logger.info( "msgJSONObject.get( \"COMMAND\" ) :: " + msgJSONObject.get( "COMMAND" ) ) ;
			
			strCommandLower = msgJSONObject.get( "COMMAND" ) + "" ;
			strCommandLower = strCommandLower.toLowerCase( ) ;
			
			// ack 날리기, channel 이 끊어 진 후에는 기존 채널이 아니게 되므로 수신 되면 ack를 날리고 실패시 이벤트 처리(성공/실패시) 이벤트 처리해야함.
			channel.basicAck( envelope.getDeliveryTag( ) , false ) ;
			
			if( "reset".equals( strCommandLower ) ) {
				////////////////////////////////////////////
				// reset
				logger.info( "::::::::::::::::::connectivityReset ::::::::::::::::" ) ;
				logger.info( "::::::::::::::::::connectivityReset ::::::::::::::::" ) ;
				logger.info( "::::::::::::::::::connectivityReset ::::::::::::::::" ) ;
				
				exe.connectivityReset( "event-reset" ) ;
				
			}
			else if( "DeviceSimulationReset".equals( strCommandLower ) ) {
				////////////////////////////////////////////
				// device simulation data reset
				
			}
			else {
				////////////////////////////////////////////
				// stop
				logger.info( "::::::::::::::::::connectivityStop ::::::::::::::::" ) ;
				logger.info( "::::::::::::::::::connectivityStop ::::::::::::::::" ) ;
				logger.info( "::::::::::::::::::connectivityStop ::::::::::::::::" ) ;
				
				exe.connectivityStop( "event-stop" ) ;
				
			}
			
		}
		catch( InterruptedException _ignored ) {
			Thread.currentThread( ).interrupt( ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			jsonUtil = null ;
			strSubMessage = null ;
			exe = null ;
		}
		
		return ;
	}
	
	public Command2Module( ConnectionFactory connectionFactory , String param ) {
		
		this.connectionFactory = connectionFactory ;
		
		logger.info( "connectionFactory :: " + connectionFactory ) ;
		
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
						
						doWork( message , channel , envelope ) ;
						
					}
					catch( Exception e ) {
						logger.error( e.getMessage( ) , e ) ;
					}
					finally {
						logger.info( " [x] Done" ) ;
						message = null ;
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
		
		return ;
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
