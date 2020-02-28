
package com.prototype ;

import org.apache.log4j.Logger ;

public class PropertyLoader
{

	static Logger logger = Logger.getLogger( PropertyLoader.class ) ;

	public static String DEMON_LOGGING_LEVEL = "0" ;
	public static boolean IS_ALL_DEMON_LIVE = false ;

	public static void getDemonProperties( )
	{
		IS_ALL_DEMON_LIVE = true ;
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

		// getDemonProperties( ) ;
		logger.debug( "IS_ALL_DEMON_LIVE :: " + IS_ALL_DEMON_LIVE ) ;
		logger.debug( "IS_ALL_DEMON_LIVE :: " + IS_ALL_DEMON_LIVE ) ;

		setIsAllDemonLive( false ) ;
		logger.debug( "IS_ALL_DEMON_LIVE :: " + IS_ALL_DEMON_LIVE ) ;
		logger.debug( "IS_ALL_DEMON_LIVE :: " + IS_ALL_DEMON_LIVE ) ;

	}

}
