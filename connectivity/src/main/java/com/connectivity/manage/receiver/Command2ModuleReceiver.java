package com.connectivity.manage.receiver ;

import java.io.IOException ;
import java.util.concurrent.TimeoutException ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;
import org.json.simple.JSONObject ;

import com.connectivity.common.ConnectivityProperties ;
import com.connectivity.manage.ModuleCommand ;
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
public class Command2ModuleReceiver implements Runnable
{
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private Logger logger = LogManager.getLogger( this.getClass( ) ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	private String TASK_QUEUE_NAME = "Command2Module" ;
	
	private ConnectionFactory connectionFactory = null ;
	private Connection connection = null ;
	private Channel channel = null ;
	
	public Command2ModuleReceiver( ConnectionFactory connectionFactory ) {
		
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
					
					String message = "" ;
					ConnectivityProperties connectivityProperties = new ConnectivityProperties( ) ;
					
					try {
						logger.info( "Command2ModuleReceiver handleDelivery" ) ;
						connectivityProperties.addOneProcessThreadCnt( ) ;
						
						message = new String( body , "UTF-8" ) ;
						logger.info( " [x] Received '" + message + "'" ) ;
						
						doWork( message , channel , envelope ) ;
						
					}
					catch( Exception e ) {
						logger.error( e.getMessage( ) , e ) ;
					}
					finally {
						connectivityProperties.subtractOneProcessThreadCnt( ) ;
						message = null ;
						connectivityProperties = null ;
						consumerTag = null ;
						envelope = null ;
						properties = null ;
						body = null ;
						logger.info( "Command2ModuleReceiver handleDelivery finally" ) ;
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
	
	// private static void doWork( String strSubMessage , Channel channel , Envelope envelope ) throws Exception {
	public void doWork( String strSubMessage , Channel channel , Envelope envelope ) {
		
		ModuleCommand moduleCommand = new ModuleCommand( ) ;
		
		JsonUtil jsonUtil = new JsonUtil( ) ;
		JSONObject jsonObject = new JSONObject( ) ;
		
		String strModuleId = "" ;
		String strCommand = "" ;
		
		try {
			// 수집된 json data 파싱
			jsonObject = jsonUtil.getJSONObjectFromString( ( strSubMessage + "" ) ) ;
			
			// 제어 명령 확인
			strCommand = jsonObject.get( "COMMAND" ) + "" ;
			logger.debug( "strCommand :: " + strCommand ) ;
			
			// 모듈 아이디 확인
			strModuleId = jsonObject.get( "MFIF_ID" ) + "" ;
			logger.debug( "strModuleId :: " + strModuleId ) ;
			
			if( strModuleId.equals( "connectivity" ) ) {
				// 모듈 아이디가 connectivity 인것만 처리한다. 그 외는 무시한다.
				if( "M02".equals( strCommand ) ) {
					// 정지
					// connectivity 정지 명령은 ack 처리 후 정지 처리한다. 안그러면 mq에 계속 남아있음.
					channel.basicAck( envelope.getDeliveryTag( ) , false ) ;
					moduleCommand.exeModuleCommandStop( strSubMessage ) ;
				}
				else {
					moduleCommand.exeModuleCommand( strSubMessage ) ;
					// connectivity 정지 외 설정 변경 명령 처리 후 ack 처리
					channel.basicAck( envelope.getDeliveryTag( ) , false ) ;
				}
			}
			else {
				// connectivity 대상 명령이 아니면 처리하지 않고 ack 처리
				channel.basicAck( envelope.getDeliveryTag( ) , false ) ;
			}
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strSubMessage = null ;
			channel = null ;
			envelope = null ;
			moduleCommand = null ;
			jsonUtil = null ;
			jsonObject = null ;
			strModuleId = null ;
			strCommand = null ;
		}
		
		return ;
	}
	
	// public void doWork( String strSubMessage ) {
	//
	// ModuleCommand moduleCommand = new ModuleCommand( ) ;
	//
	// try {
	// moduleCommand.exeModuleCommand( strSubMessage ) ;
	// }
	// catch( Exception e ) {
	// logger.error( e.getMessage( ) , e ) ;
	// }
	// finally {
	// strSubMessage = null ;
	// moduleCommand = null ;
	// }
	//
	// return ;
	// }
	
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
