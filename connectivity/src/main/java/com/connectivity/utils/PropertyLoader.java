/**
 * 
 */
package com.connectivity.utils ;

import java.io.IOException ;
import java.io.InputStream ;
import java.util.Properties ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.common.CommonProperties ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-04-23
 */
public class PropertyLoader
{
	
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	Logger logger = LogManager.getLogger( PropertyLoader.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	public static void main( String[ ] args ) {
		PropertyLoader exe = new PropertyLoader( ) ;
		
		Boolean resultBool = exe.getCommonProperties( ) ;
		
	}
	
	public Boolean getCommonProperties( ) {
		
		Boolean resultBool = true ;
		
		InputStream propIs = null ;
		Properties prop = null ;
		String strPropFile = "" ;
		
		try {
			resultBool = false ;
			
			strPropFile = "connectivity.properties" ;
			logger.info( "strPropFile :: [" + strPropFile + "]" ) ;
			
			propIs = this.getClass( ).getClassLoader( ).getResourceAsStream( strPropFile ) ;
			
			if( propIs != null ) {
				
				prop = new Properties( ) ;
				
				prop.load( propIs ) ;
				
				CommonProperties.REDIS_IP = "" + prop.getProperty( "REDIS_IP" ) ;
				CommonProperties.REDIS_IP = CommonProperties.REDIS_IP.trim( ).replaceAll( " " , "" ) ;
				
				CommonProperties.REDIS_PORT = "" + prop.getProperty( "REDIS_PORT" ) ;
				CommonProperties.REDIS_PORT = CommonProperties.REDIS_PORT.trim( ).replaceAll( " " , "" ) ;
				
				CommonProperties.REDIS_PASSWORD = "" + prop.getProperty( "REDIS_PASSWORD" ) ;
				CommonProperties.REDIS_PASSWORD = CommonProperties.REDIS_PASSWORD.trim( ).replaceAll( " " , "" ) ;
				
				CommonProperties.RABBITMQ_IP = "" + prop.getProperty( "RABBITMQ_IP" ) ;
				CommonProperties.RABBITMQ_IP = CommonProperties.RABBITMQ_IP.trim( ).replaceAll( " " , "" ) ;
				
				CommonProperties.RABBITMQ_PORT = "" + prop.getProperty( "RABBITMQ_PORT" ) ;
				CommonProperties.RABBITMQ_PORT = CommonProperties.RABBITMQ_PORT.trim( ).replaceAll( " " , "" ) ;
				
				CommonProperties.RABBITMQ_USERNAME = "" + prop.getProperty( "RABBITMQ_USERNAME" ) ;
				CommonProperties.RABBITMQ_USERNAME = CommonProperties.RABBITMQ_USERNAME.trim( ).replaceAll( " " , "" ) ;
				
				CommonProperties.RABBITMQ_PASSWORD = "" + prop.getProperty( "RABBITMQ_PASSWORD" ) ;
				CommonProperties.RABBITMQ_PASSWORD = CommonProperties.RABBITMQ_PASSWORD.trim( ).replaceAll( " " , "" ) ;
				
				CommonProperties.MONGODB_IP = "" + prop.getProperty( "MONGODB_IP" ) ;
				CommonProperties.MONGODB_IP = CommonProperties.MONGODB_IP.trim( ).replaceAll( " " , "" ) ;
				
				CommonProperties.MONGODB_PORT = "" + prop.getProperty( "MONGODB_PORT" ) ;
				CommonProperties.MONGODB_PORT = CommonProperties.MONGODB_PORT.trim( ).replaceAll( " " , "" ) ;
				
				CommonProperties.MONGODB_DATABASE = "" + prop.getProperty( "MONGODB_DATABASE" ) ;
				CommonProperties.MONGODB_DATABASE = CommonProperties.MONGODB_DATABASE.trim( ).replaceAll( " " , "" ) ;
				
				CommonProperties.MONGODB_USERNAME = "" + prop.getProperty( "MONGODB_USERNAME" ) ;
				CommonProperties.MONGODB_USERNAME = CommonProperties.MONGODB_USERNAME.trim( ).replaceAll( " " , "" ) ;
				
				CommonProperties.MONGODB_PASSWORD = "" + prop.getProperty( "MONGODB_PASSWORD" ) ;
				CommonProperties.MONGODB_PASSWORD = CommonProperties.MONGODB_PASSWORD.trim( ).replaceAll( " " , "" ) ;
				
				logger.info( "CommonProperties.REDIS_IP :: [" + CommonProperties.REDIS_IP + "] " ) ;
				logger.info( "CommonProperties.REDIS_PORT :: [" + CommonProperties.REDIS_PORT + "] " ) ;
				logger.info( "CommonProperties.REDIS_PASSWORD :: [" + CommonProperties.REDIS_PASSWORD + "] " ) ;
				logger.info( "CommonProperties.RABBITMQ_IP :: [" + CommonProperties.RABBITMQ_IP + "] " ) ;
				logger.info( "CommonProperties.RABBITMQ_PORT :: [" + CommonProperties.RABBITMQ_PORT + "] " ) ;
				logger.info( "CommonProperties.RABBITMQ_USERNAME :: [" + CommonProperties.RABBITMQ_USERNAME + "] " ) ;
				logger.info( "CommonProperties.RABBITMQ_PASSWORD :: [" + CommonProperties.RABBITMQ_PASSWORD + "] " ) ;
				logger.info( "CommonProperties.MONGODB_IP :: [" + CommonProperties.MONGODB_IP + "] " ) ;
				logger.info( "CommonProperties.MONGODB_PORT :: [" + CommonProperties.MONGODB_PORT + "] " ) ;
				logger.info( "CommonProperties.MONGODB_DATABASE :: [" + CommonProperties.MONGODB_DATABASE + "] " ) ;
				logger.info( "CommonProperties.MONGODB_USERNAME :: [" + CommonProperties.MONGODB_USERNAME + "] " ) ;
				logger.info( "CommonProperties.MONGODB_PASSWORD :: [" + CommonProperties.MONGODB_PASSWORD + "] " ) ;
				
				resultBool = true ;
			}
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			
			if( propIs != null ) {
				try {
					propIs.close( ) ;
				}
				catch( IOException e ) {
					logger.error( e.getMessage( ) , e ) ;
				}
			}
			
			if( prop != null ) {
				prop.clear( ) ;
			}
			
			propIs = null ;
			prop = null ;
			strPropFile = null ;
			
			logger.info( "resultBool :: " + resultBool + "" ) ;
		}
		
		return resultBool ;
	}
	
}
