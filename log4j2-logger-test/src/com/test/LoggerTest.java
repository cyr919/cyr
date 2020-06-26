/**
 * 
 */
package com.test ;

import org.apache.logging.log4j.Level ;
import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.Log4jTestMain ;
import com.LoggerChange ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-05-15
 */
public class LoggerTest
{
	private Logger logger = LogManager.getLogger( ) ;
	
	/**
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-05-15
	 * @param args
	 */
	public static void main( String[ ] args ) {
		// TODO Auto-generated method stub
		
		LoggerTest exe = new LoggerTest( ) ;
		Log4jTestMain main = new Log4jTestMain( ) ;
		LoggerChange change = new LoggerChange( ) ;
		
		exe.testLog( ) ;
		main.testLog( ) ;
		change.changeLoggerSetting( "com.test" , Level.INFO , "2KB" ) ;
		
//		change.changeLoggerSettingRollingFileAppender( "3KB" ) ;
//		change.changeLoggerLevel(  "com.test" , Level.INFO ) ;
		exe.testLog( ) ;
		main.testLog( ) ;
//		change.changeLoggerLevel(  Level.ERROR ) ;

//		change.changeLoggerRootSetting( Level.ERROR , "4KB" ) ;
		
		exe.testLog( ) ;
		main.testLog( ) ;

	}
	
	public void testLog( ) {
		
		logger.fatal( "::::: testLog :::::" ) ;
		logger.trace( "로그" ) ;
		logger.debug( "로그" ) ;
		logger.info( "로그" ) ;
		logger.warn( "로그" ) ;
		logger.error( "로그" ) ;
		logger.fatal( "로그" ) ;
		logger.trace( "로그" ) ;
		logger.debug( "로그" ) ;
		logger.info( "로그" ) ;
		logger.warn( "로그" ) ;
		logger.error( "로그" ) ;
		logger.fatal( "로그" ) ;
		logger.trace( "로그" ) ;
		logger.debug( "로그" ) ;
		logger.info( "로그" ) ;
		logger.warn( "로그" ) ;
		logger.error( "로그" ) ;
		logger.fatal( "로그" ) ;
		logger.trace( "로그" ) ;
		logger.debug( "로그" ) ;
		logger.info( "로그" ) ;
		logger.warn( "로그" ) ;
		logger.error( "로그" ) ;
		logger.fatal( "로그" ) ;
		logger.fatal( "::::: testLog :::::" ) ;
		
	}
	
}
