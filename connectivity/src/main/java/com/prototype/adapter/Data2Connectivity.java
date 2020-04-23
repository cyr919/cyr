package com.prototype.adapter ;

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
 * adatper에서 queue로 데이터를 전송한다.
 * </pre>
 *
 * @author cyr
 * @date 2020-04-06
 */
public class Data2Connectivity
{
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private static final Logger logger = LogManager.getLogger( Data2Connectivity.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	private String TASK_QUEUE_NAME = "Data2Connectivity" ;
	
	public void publishData2Connectivity( JSONObject jsonObjectMessage ) {
		
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
