
package com.prototype ;

import java.util.HashMap ;

import org.apache.log4j.Logger ;

public class PropertyLoader
{

	static Logger logger = Logger.getLogger( PropertyLoader.class ) ;

	public static String DEMON_LOGGING_LEVEL = "0" ;
	public static boolean IS_ALL_DEMON_LIVE = false ;
	public static HashMap< String , HashMap< String , Object > > DEVICE_PROPERTIES_HASHMAP = new HashMap<>( ) ;
	

	public static void getDemonProperties( )
	{

		JsonDeviceProperties jsonDeviceProperties = new JsonDeviceProperties( ) ;
		try
		{
			IS_ALL_DEMON_LIVE = true ;

//			DEVICE_PROPERTIES_HASHMAP = jsonDeviceProperties.getDeviceProperties( ) ;
			logger.debug( "DEVICE_PROPERTIES_HASHMAP :: " ) ;
			logger.debug( DEVICE_PROPERTIES_HASHMAP ) ;
			logger.debug( "DEVICE_PROPERTIES_HASHMAP :: " ) ;

		}
		finally
		{
			jsonDeviceProperties = null ;
		}

	}

	public static void setIsAllDemonLive( boolean setParam )
	{
		IS_ALL_DEMON_LIVE = setParam ;

	}

	public static void main( String[ ] args )
	{
		// TODO Auto-generated method stub

		logger.debug( "IS_ALL_DEMON_LIVE :: " + IS_ALL_DEMON_LIVE ) ;
		logger.debug( "IS_ALL_DEMON_LIVE :: " + IS_ALL_DEMON_LIVE ) ;

		getDemonProperties( ) ;
		logger.debug( "IS_ALL_DEMON_LIVE :: " + IS_ALL_DEMON_LIVE ) ;
		logger.debug( "IS_ALL_DEMON_LIVE :: " + IS_ALL_DEMON_LIVE ) ;

		setIsAllDemonLive( false ) ;
		logger.debug( "IS_ALL_DEMON_LIVE :: " + IS_ALL_DEMON_LIVE ) ;
		logger.debug( "IS_ALL_DEMON_LIVE :: " + IS_ALL_DEMON_LIVE ) ;

		logger.debug( "DEVICE_PROPERTIES_HASHMAP :: " ) ;
		logger.debug( DEVICE_PROPERTIES_HASHMAP ) ;
		logger.debug( "DEVICE_PROPERTIES_HASHMAP :: " ) ;

	}

}
