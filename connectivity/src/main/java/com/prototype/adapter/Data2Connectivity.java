package com.prototype.adapter ;

import java.io.IOException ;
import java.util.concurrent.TimeoutException ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;
import org.json.simple.JSONObject ;

import com.rabbitmq.client.Channel ;
import com.rabbitmq.client.Connection ;
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
	private Logger logger = LogManager.getLogger( Data2Connectivity.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	private String TASK_QUEUE_NAME = "Data2Connectivity" ;
	
	public void publishData2Connectivity( JSONObject jsonObjectMessage , String strDeviceID ) {
		
		Connection connection = null ;
		Channel channel = null ;
		
		String message = "" ;
		
		String queueName = "" ;
		
		AdapterMainRun adapterMainRun = new AdapterMainRun( ) ;
		
		try {
			queueName = TASK_QUEUE_NAME + "_" + strDeviceID ;
			connection = adapterMainRun.getPubConnection( ) ;
			channel = connection.createChannel( ) ;
			
			channel.queueDeclare( queueName , true , false , false , null ) ;
			
			message = jsonObjectMessage.toJSONString( ) ;
			
			channel.basicPublish( "" , queueName , MessageProperties.PERSISTENT_TEXT_PLAIN , message.getBytes( ) ) ;
			logger.info( " [x] Sent :: " + message + "" ) ;
			
		}
		catch( IOException e ) {
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
			channel = null ;
			jsonObjectMessage = null ;
			message = null ;
		}
		
		return ;
	}
	
}
