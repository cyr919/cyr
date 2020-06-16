/**
 * 
 */
package com.prototype.application ;

import java.io.IOException ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;
import org.json.simple.JSONObject ;

import com.connectivity.common.CommonProperties ;
import com.connectivity.common.RabbitmqCommon ;
import com.connectivity.config.RabbitmqConnection ;
import com.prototype.servicehub.ServicehubMainRun ;
import com.rabbitmq.client.Connection ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-06-16
 */
public class ApplicationMainRun
{
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private static Logger logger = LogManager.getLogger( ServicehubMainRun.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	/**
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-16
	 * @param args
	 */
	public static void main( String[ ] args ) {
		// TODO Auto-generated method stub
		
		CommonProperties commonProperties = new CommonProperties( ) ;
		RabbitmqConnection rabbitmqConnection = new RabbitmqConnection( ) ;
		Connection connection = null ;
		try {
			
			commonProperties.setProperties( ) ;
			
			connection = rabbitmqConnection.setConnection( ) ;
			
			ApplicationMainRun exe = new ApplicationMainRun( ) ;
			exe.deviceControlCommand( connection ) ;
			
		}
		catch( Exception e ) {
			e.printStackTrace( ) ;
		}
		finally {
			
			if( connection.isOpen( ) ) {
				try {
					connection.close( );
				}
				catch( IOException e ) {
					logger.error( e.getMessage( ) , e ) ;
				}
			}
			
			commonProperties = null ;
			rabbitmqConnection = null ;
			connection = null ;
			
			logger.debug( "main :: connection :: " + connection ) ;
			
		}
		
	}
	
	public Boolean deviceControlCommand( Connection connection ) {
		
		Boolean resultBool = true ;
		
		RabbitmqCommon rabbitmqCommon = new RabbitmqCommon( ) ;
		
		JSONObject resultJsonObject = new JSONObject( ) ;
		
		try {
			
			resultJsonObject.put( "APP_ID" , "APP01" ) ;
			resultJsonObject.put( "EVHS_ID" , "event01" ) ;
			resultJsonObject.put( "PNT_IDX" , "ctrl001" ) ;
			
			rabbitmqCommon.rabbitmqCommonSender( connection , "Control2Connectivity" , resultJsonObject ) ;
			rabbitmqCommon.rabbitmqCommonSender( connection , "Control2Connectivity" , resultJsonObject ) ;
			rabbitmqCommon.rabbitmqCommonSender( connection , "Control2Connectivity" , resultJsonObject ) ;
			rabbitmqCommon.rabbitmqCommonSender( connection , "Control2Connectivity" , resultJsonObject ) ;
			
		}
		catch( Exception e ) {
			resultBool = true ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			
			if( connection.isOpen( ) ) {
				try {
					connection.close( );
				}
				catch( IOException e ) {
					logger.error( e.getMessage( ) , e ) ;
				}
			}
			
			
			connection = null ;
			rabbitmqCommon = null ;
			resultJsonObject = null ;
			
			logger.debug( "deviceControlCommand :: connection :: " + connection ) ;
		}
		
		return resultBool ;
	}
	
}
