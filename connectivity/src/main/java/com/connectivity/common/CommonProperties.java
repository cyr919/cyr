/**
 * 
 */
package com.connectivity.common ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.utils.PropertyLoader ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-04-23
 */
public final class CommonProperties
{
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	Logger logger = LogManager.getLogger( CommonProperties.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	public static String REDIS_IP = "" ;
	public static String REDIS_PORT = "" ;
	public static String REDIS_PASSWORD = "" ;
	public static String RABBITMQ_IP = "" ;
	public static String RABBITMQ_PORT = "" ;
	public static String RABBITMQ_USERNAME = "" ;
	public static String RABBITMQ_PASSWORD = "" ;
	public static String MONGODB_IP = "" ;
	public static String MONGODB_PORT = "" ;
	public static String MONGODB_DATABASE = "" ;
	public static String MONGODB_USERNAME = "" ;
	public static String MONGODB_PASSWORD = "" ;
	
	public Boolean setProperties( ) throws Exception {
		Boolean resultBool = true ;
		
		try {
			PropertyLoader propertyLoader = new PropertyLoader( ) ;
			resultBool = propertyLoader.getCommonProperties( ) ;
			
		}
		catch( Exception e ) {
			 resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			
		}
		
		return resultBool ;
	}
	
}
