package com.prototype.servicehub ;

import java.io.IOException ;
import java.util.concurrent.TimeoutException ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;
import org.json.simple.JSONObject ;

import com.rabbitmq.client.Channel ;
import com.rabbitmq.client.Connection ;
import com.rabbitmq.client.ConnectionFactory ;
import com.rabbitmq.client.MessageProperties ;

/**
 * <pre>
 * Work Queues
 * servicehub에서 queue로 데이터를 전송한다.
 * </pre>
 *
 * @author cyr
 * @date 2020-04-06
 */
public class Command2Module
{
	
	Logger logger = LogManager.getLogger( ) ;
	
	private String TASK_QUEUE_NAME = "Command2Module" ;
	
	public void publishCommand2Module( JSONObject jsonObjectMessage ) {
		
		ConnectionFactory factory = new ConnectionFactory( ) ;
		Connection connection = null ;
		Channel channel = null ;
		
		String message = "" ;
		
		try {
			
			factory.setHost( "192.168.56.105" ) ;
			factory.setUsername( "admin" ) ;
			factory.setPassword( "admin" ) ;
			
			connection = factory.newConnection( ) ;
			channel = connection.createChannel( ) ;
			
			channel.queueDeclare( TASK_QUEUE_NAME , true , false , false , null ) ;
			
			message = jsonObjectMessage.toJSONString( ) ;
			
			channel.basicPublish( "" , TASK_QUEUE_NAME , MessageProperties.PERSISTENT_TEXT_PLAIN , message.getBytes( ) ) ;
			logger.info( " [x] Sent '" + message + "'" ) ;
			
		}
		catch( IOException e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		catch( TimeoutException e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			
			if( channel != null ) {
				try {
					channel.close( ) ;
				}
				catch( IOException e ) {
					logger.error( e.getMessage( ) , e ) ;
				}
				catch( TimeoutException e ) {
					logger.error( e.getMessage( ) , e ) ;
				}
			}
			if( connection != null ) {
				try {
					connection.close( ) ;
				}
				catch( IOException e ) {
					logger.error( e.getMessage( ) , e ) ;
				}
			}
			factory = null ;
			channel = null ;
			connection = null ;
			jsonObjectMessage = null ;
			message = null ;
		}
		
		return ;
	}
	
}
