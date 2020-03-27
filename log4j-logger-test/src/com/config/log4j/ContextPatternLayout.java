
package com.config.log4j ;

import java.net.InetAddress ;
import java.net.UnknownHostException ;

import org.apache.log4j.PatternLayout ;
import org.apache.log4j.helpers.PatternConverter ;
import org.apache.log4j.helpers.PatternParser ;
import org.apache.log4j.spi.LoggingEvent ;

public class ContextPatternLayout extends PatternLayout
{

	public static void main( String[ ] args )
	{
		// TODO Auto-generated method stub

		System.out.println( "ContextPatternLayout" ) ;
		try
		{
			System.out.println( "InetAddress.getLocalHost :: " + InetAddress.getLocalHost( ) ) ;
			System.out.println( "getHostname :: " + getHostname( ) ) ;
		}
		catch ( UnknownHostException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace( ) ;
		}

	}

	public static final char HOSTNAME_PATTERN_CHAR = 'h' ;

	protected static String getHostname( )
	{
		try
		{
			InetAddress addr = InetAddress.getLocalHost( ) ;
			return addr.getHostName( ) ;
		}
		catch ( UnknownHostException ex )
		{
			return "unknown" ;
		}
	}

	@Override
	protected PatternParser createPatternParser( String pattern )
	{
		return new PatternParser( pattern ) {

			@Override
			protected void finalizeConverter( char c )
			{
				PatternConverter pc = null ;

				switch (c)
				{
					case HOSTNAME_PATTERN_CHAR:
						pc = new PatternConverter( ) {

							@Override
							protected String convert( LoggingEvent event )
							{
								return getHostname( ) ;
							}
						} ;
						break ;
				}

				if ( pc == null )
				{
					super.finalizeConverter( c ) ;
				}
				else
				{
					addConverter( pc ) ;
				}

			}
		} ;
	}

}
