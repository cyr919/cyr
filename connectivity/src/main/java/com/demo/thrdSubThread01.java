
package com.demo ;

import java.util.HashMap ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

public class thrdSubThread01 implements Runnable
{

	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private static final Logger logger = LogManager.getLogger( thrdSubThread01.class ) ;
	// Logger logger = LogManager.getLogger( ) ;

	private HashMap< String , Object > paramPrivateHashMap = new HashMap<>( ) ;

	public thrdSubThread01( HashMap< String , Object > paramHashMap ) {
		paramPrivateHashMap = paramHashMap ;
	}

	@Override
	public void run( )
	{

		logger.debug( ":: thrdSubThread01 :: 시작" ) ;
		logger.debug( "paramPrivateHashMap :: " + paramPrivateHashMap ) ;
		logger.debug( ":: thrdSubThread01 :: 종료" ) ;

	}

	/**
	 * @return the paramPrivateHashMap
	 */
	public HashMap< String , Object > getParamPrivateHashMap( )
	{
		return paramPrivateHashMap ;
	}

}
