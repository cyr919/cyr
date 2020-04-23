package com ;

import java.util.Enumeration ;
import java.util.LinkedList ;
import java.util.List ;

import org.apache.log4j.Category ;
import org.apache.log4j.Level ;
import org.apache.log4j.Logger ;

/**
 * <pre>
 * log4j 1.2 실시간 로그 레벨 변경
 * </pre>
 *
 * @author cyr
 * @date 2020-03-25
 */
public class LoggerLevel
{
	static Logger logger = Logger.getLogger( LoggerLevel.class ) ;
	
	public static List< Category > getLoggerAllCategories( ) {
		
		List< Category > results = new LinkedList< Category >( ) ;
		
		Logger root = null ;
		Enumeration< ? > currentCategories = null ;
		Category category = null ;
		
		try {
			
			root = Logger.getRootLogger( ) ;
			
			currentCategories = root.getLoggerRepository( ).getCurrentLoggers( ) ;
			
			while( currentCategories.hasMoreElements( ) ) {
				category = ( Category ) currentCategories.nextElement( ) ;
				
				if( category.getLevel( ) != null ) {
					results.add( category ) ;
				}
				
			}
			
			// TODO Loggers Category 정렬인거같은데 필요 할까???
			// Collections.sort( results , new Comparator< Category >( ) {
			// public int compare( Category o1 , Category o2 ) {
			// if( o1 == null || o2 == null )
			// return 0 ;
			// if( o1.getName( ) == null || o2.getName( ) == null )
			// return 0 ;
			// return o1.getName( ).compareTo( o2.getName( ) ) ;
			// }
			// } ) ;
			//
			
			results.add( 0 , root ) ;
			
		}
		finally {
			root = null ;
			currentCategories = null ;
			category = null ;
			
		}
		
		return results ;
	}
	
	private static Category findCategory( String categoryName ) {
		if( categoryName == null )
			return null ;
		
		Logger root = Logger.getRootLogger( ) ;
		if( "root".equals( categoryName ) )
			return root ;
		
		Enumeration categories = root.getLoggerRepository( ).getCurrentCategories( ) ;
		while( categories.hasMoreElements( ) ) {
			Category category = ( Category ) categories.nextElement( ) ;
			if( categoryName.equals( category.getName( ) ) )
				return category ;
		}
		return null ;
	}
	
	public static void setLogLevel( String categoryName , Level level ) {
		Category category = findCategory( categoryName ) ;
		if( category == null ) {
			System.out.println( "[Log4JUtil] Can not find category: " + categoryName ) ;
			return ;
		}
		
		category.setLevel( level ) ;
	}
	
	public static void setLogLevelAllLogger( Level level ) {
		
		List< Category > categoryList = getLoggerAllCategories( ) ;
		int i = 0 ;
		
		try {
			
			for( i = 0 ; i < categoryList.size( ) ; i++ ) {
				if( null != categoryList.get( i ) ) {
					categoryList.get( i ).setLevel( level ) ;
				}
			}
			
		}
		finally {
			level = null ;
			categoryList = null ;
			i = 0 ;
		}
		
		return ;
	}
	
	public static void main( String[ ] args ) {
		
		logger.trace( "로그" ) ;
		logger.debug( "로그" ) ;
		logger.info( "로그" ) ;
		logger.warn( "로그" ) ;
		logger.error( "로그" ) ;
		logger.fatal( "로그" ) ;
		
		List< Category > results = getLoggerAllCategories( ) ;
		for( int i = 0 ; i < results.size( ) ; i++ ) {
			System.out.println( results.get( i ).getName( ) + " / " + results.get( i ).getLevel( ) ) ;
		}
		
		setLogLevelAllLogger( Level.toLevel( "ERROR" ) ) ;
		
		logger.trace( "로그" ) ;
		logger.debug( "로그" ) ;
		logger.info( "로그" ) ;
		logger.warn( "로그" ) ;
		logger.error( "로그" ) ;
		logger.fatal( "로그" ) ;
		
	}
	
}
