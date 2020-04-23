
package com.prototype ;

import java.util.HashMap ;
import java.util.List ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

public class PropertyLoader
{
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private static final Logger logger = LogManager.getLogger( PropertyLoader.class ) ;
	
	public static String DEMON_LOGGING_LEVEL = "0" ;
	public static boolean IS_ALL_DEMON_LIVE = false ;
	public static int PROCESS_THREAD_CNT = 0 ;
	
	public static HashMap< String , List< HashMap< String , Object > > > DEVICE_PROPERTIES_DT_MDL = new HashMap<>( ) ;
	public static HashMap< String , List< HashMap< String , Object > > > DEVICE_PROPERTIES_CAL_INF = new HashMap<>( ) ;
	
	public static void getDemonProperties( ) {
		
		JsonDeviceProperties jsonDeviceProperties = new JsonDeviceProperties( ) ;
		try {
			
			logger.info( "getDemonProperties start" ) ;
			
			IS_ALL_DEMON_LIVE = true ;
			
			DEVICE_PROPERTIES_DT_MDL = jsonDeviceProperties.getDevicePropertiesDtMdl( ) ;
			DEVICE_PROPERTIES_CAL_INF = jsonDeviceProperties.getDevicePropertiesCalInf( ) ;
			
			logger.debug( "DEVICE_PROPERTIES_DT_MDL :: " ) ;
			logger.debug( DEVICE_PROPERTIES_DT_MDL ) ;
			logger.debug( "DEVICE_PROPERTIES_DT_MDL :: " ) ;
			
			logger.debug( "DEVICE_PROPERTIES_CAL_INF :: " ) ;
			logger.debug( DEVICE_PROPERTIES_CAL_INF ) ;
			logger.debug( "DEVICE_PROPERTIES_CAL_INF :: " ) ;
		}
		finally {
			jsonDeviceProperties = null ;
			logger.info( "getDemonProperties end" ) ;
		}
		
	}
	
	public static void setIsAllDemonLive( boolean setParam ) {
		IS_ALL_DEMON_LIVE = setParam ;
		
	}
	
	public static void main( String[ ] args ) {
		// TODO Auto-generated method stub
		
		logger.debug( "IS_ALL_DEMON_LIVE :: " + IS_ALL_DEMON_LIVE ) ;
		logger.debug( "IS_ALL_DEMON_LIVE :: " + IS_ALL_DEMON_LIVE ) ;
		
		getDemonProperties( ) ;
		logger.debug( "IS_ALL_DEMON_LIVE :: " + IS_ALL_DEMON_LIVE ) ;
		logger.debug( "IS_ALL_DEMON_LIVE :: " + IS_ALL_DEMON_LIVE ) ;
		
		setIsAllDemonLive( false ) ;
		logger.debug( "IS_ALL_DEMON_LIVE :: " + IS_ALL_DEMON_LIVE ) ;
		logger.debug( "IS_ALL_DEMON_LIVE :: " + IS_ALL_DEMON_LIVE ) ;
		
		logger.debug( "DEVICE_PROPERTIES_DT_MDL :: " ) ;
		logger.debug( DEVICE_PROPERTIES_DT_MDL ) ;
		logger.debug( "DEVICE_PROPERTIES_DT_MDL :: " ) ;
		
		logger.debug( "DEVICE_PROPERTIES_CAL_INF :: " ) ;
		logger.debug( DEVICE_PROPERTIES_CAL_INF ) ;
		logger.debug( "DEVICE_PROPERTIES_CAL_INF :: " ) ;
		
	}
	
}
