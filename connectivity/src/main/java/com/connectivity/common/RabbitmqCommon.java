/**
 * 
 */
package com.connectivity.common ;

import java.io.IOException ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;
import org.json.simple.JSONObject ;

import com.rabbitmq.client.Channel ;
import com.rabbitmq.client.Connection ;
import com.rabbitmq.client.MessageProperties ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-06-15
 */
public class RabbitmqCommon
{
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	// private Logger logger = LogManager.getLogger( RedisCommon.class ) ;
	private Logger logger = LogManager.getLogger( this.getClass( ) ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	/**
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-15
	 * @param args
	 */
	public static void main( String[ ] args ) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-15
	 * @param connection rabbitmq connection
	 * @param strTaskQueueName queue 명
	 * @param jsonObjectMessage pub 할 메시지
	 * @return
	 */
	public Boolean rabbitmqCommonSender( Connection connection , String strTaskQueueName , JSONObject jsonObjectMessage ) {
		
		Boolean resultBool = true ;
		Channel channel = null ;
		String message = "" ;
		
		try {
			logger.debug( "rabbitmqCommonSender start" ) ;
			logger.debug( "rabbitmqCommonSender connection :: " + connection ) ;
			
			
			channel = connection.createChannel( ) ;
			
			channel.queueDeclare( strTaskQueueName , true , false , false , null ) ;
			
			message = jsonObjectMessage.toJSONString( ) ;
			
			channel.basicPublish( "" , strTaskQueueName , MessageProperties.PERSISTENT_TEXT_PLAIN , message.getBytes( ) ) ;
			
			logger.debug( "rabbitmqCommonSender MSG :: [" ) ;
			logger.debug( message ) ;
			logger.debug( "] :: rabbitmqCommonSender MSG :: " ) ;
			
		}
		catch( IOException e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strTaskQueueName = null ;
			jsonObjectMessage = null ;
			// connection = null;
			resultBool = null ;
			channel = null ;
			message = null ;
			logger.debug( "rabbitmqCommonSender finally" ) ;
		}
		
		return resultBool ;
	}
	
}
