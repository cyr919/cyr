/**
 * 
 */
package com.connectivity.utils ;

import org.apache.logging.log4j.Level ;
import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;
import org.apache.logging.log4j.core.Appender ;
import org.apache.logging.log4j.core.LoggerContext ;
import org.apache.logging.log4j.core.appender.RollingFileAppender ;
import org.apache.logging.log4j.core.appender.rolling.CompositeTriggeringPolicy ;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy ;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy ;
import org.apache.logging.log4j.core.appender.rolling.TimeBasedTriggeringPolicy ;
import org.apache.logging.log4j.core.config.Configuration ;
import org.apache.logging.log4j.core.config.LoggerConfig ;
import org.apache.logging.log4j.core.layout.PatternLayout ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-06-26
 */
public class LoggerUtil
{
	private Logger logger = LogManager.getLogger( ) ;
	
	/**
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-26
	 * @param strFileSize
	 * @return
	 */
	public Appender setAppender( String strFileSize ) {
		Appender appender = null ;
		
		String strProjecNm = "" ;
		String strPattern = "" ;
		PatternLayout layout = null ;
		TimeBasedTriggeringPolicy timeBasedTriggeringPolicy = null ;
		SizeBasedTriggeringPolicy sizeBasedTriggeringPolicy = null ;
		CompositeTriggeringPolicy policy = null ;
		DefaultRolloverStrategy strategy = null ;
		
		try {
			strPattern = "[%d{yyyy-MM-dd HH:mm:ss.SSS}] [${hostName}] [%t] %c{1} [%-5level] - %msg%n" ;
			strProjecNm = "log4j-test" ;
			
			// Layout< ? > layout = PatternLayout.createLayout( strPattern , null , config , null , null , false , false , null , null ) ;
			layout = PatternLayout.newBuilder( ).withPattern( strPattern ).build( ) ;
			logger.fatal( "layout :: " + layout ) ;
			
			// TimeBasedTriggeringPolicy timeBasedTriggeringPolicy = TimeBasedTriggeringPolicy.createPolicy( "1" , "false" ) ;
			timeBasedTriggeringPolicy = TimeBasedTriggeringPolicy.newBuilder( ).withInterval( 1 ).build( ) ;
			logger.fatal( "timeBasedTriggeringPolicy :: " + timeBasedTriggeringPolicy ) ;
			
			// SizeBasedTriggeringPolicy sizeBasedTriggeringPolicy = SizeBasedTriggeringPolicy.createPolicy( "1KB" ) ;
			sizeBasedTriggeringPolicy = SizeBasedTriggeringPolicy.createPolicy( strFileSize ) ;
			logger.fatal( "sizeBasedTriggeringPolicy :: " + sizeBasedTriggeringPolicy ) ;
			
			policy = CompositeTriggeringPolicy.createPolicy( timeBasedTriggeringPolicy , sizeBasedTriggeringPolicy ) ;
			logger.fatal( "policy :: " + policy ) ;
			
			// DefaultRolloverStrategy strategy = DefaultRolloverStrategy.createStrategy( null , null , "nomax" , null , null , false , config ) ;
			strategy = DefaultRolloverStrategy.newBuilder( ).withFileIndex( "nomax" ).build( ) ;
			
			logger.fatal( "strategy :: " + strategy ) ;
			// Appender appender = RollingFileAppender.createAppender( "./log/" + strProjecNm + ".log" , "./log/" + strProjecNm + ".log.%d{yyyy-MM-dd}.%i" , "true" , "rollingFile" , "true" , "1024" , null , policy , strategy , layout , null , null , null , null , config ) ;
			appender = RollingFileAppender.newBuilder( ).setName( "rollingFile" )
							.withFileName( "./log/" + strProjecNm + ".log" )
							.withFilePattern( "./log/" + strProjecNm + ".%d{yyyy-MM-dd}.%i.log" )
							.withAppend( true ).withBufferedIo( true ).withBufferSize( 1024 )
							.setLayout( layout ).withPolicy( policy ).withStrategy( strategy ).build( ) ;
			
		}
		catch( Exception e ) {
			logger.fatal( e.getMessage( ) , e ) ;
		}
		finally {
			strFileSize = null ;
			strProjecNm = null ;
			strPattern = null ;
			layout = null ;
			timeBasedTriggeringPolicy = null ;
			sizeBasedTriggeringPolicy = null ;
			policy = null ;
			strategy = null ;
		}
		
		return appender ;
	}
	
	/**
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-26
	 * @param logName
	 * @param paramLevel
	 * @param strFileSize
	 * @return
	 */
	public Boolean changeLoggerSetting( String logName , Level paramLevel , String strFileSize ) {
		Boolean resultBool = true ;
		
		LoggerContext ctx = null ;
		Configuration config = null ;
		LoggerConfig loggerConfig = null ;
		Appender appender = null ;
		
		try {
			
			logger.fatal( "::::: changeLoggerSetting :::::" ) ;
			logger.trace( "로그" ) ;
			logger.debug( "로그" ) ;
			logger.info( "로그" ) ;
			logger.warn( "로그" ) ;
			logger.error( "로그" ) ;
			logger.fatal( "로그" ) ;
			logger.fatal( "::::: 변경 전 :::::" ) ;
			logger.fatal( "---------------------------" ) ;
			logger.fatal( "logName :: " + logName ) ;
			logger.fatal( "paramLevel :: " + paramLevel ) ;
			logger.fatal( "strFileSize :: " + strFileSize ) ;
			logger.fatal( "---------------------------" ) ;
			
			resultBool = false ;
			
			ctx = ( LoggerContext ) LogManager.getContext( false ) ;
			config = ctx.getConfiguration( ) ;
			loggerConfig = config.getLoggerConfig( logName ) ;
			
			appender = setAppender( strFileSize ) ;
			
			appender.start( ) ;
			config.addAppender( appender ) ;
			
			logger.fatal( "appender :: " + appender ) ;
			logger.fatal( "appender.getLayout( ) :: " + appender.getLayout( ) ) ;
			
			logger.fatal( "appender.getHandler( ) :: " + appender.getHandler( ) ) ;
			logger.fatal( "appender.getClass( ) :: " + appender.getClass( ) ) ;
			
			// loggerConfig.addAppender( appender , Level.DEBUG , null ) ;
			// loggerConfig.setLevel( Level.DEBUG ) ;
			loggerConfig.addAppender( appender , paramLevel , null ) ;
			loggerConfig.setLevel( paramLevel ) ;
			
			logger.fatal( "config :: " + config ) ;
			logger.fatal( "loggerConfig :: " + loggerConfig ) ;
			logger.fatal( "loggerConfig.getAppenders( ) :: " + loggerConfig.getAppenders( ) ) ;
			logger.fatal( "loggerConfig.getAppenderRefs( ) :: " + loggerConfig.getAppenderRefs( ) ) ;
			logger.fatal( "loggerConfig.getLevel( ) :: " + loggerConfig.getLevel( ) ) ;
			logger.fatal( "loggerConfig.getName( ) :: " + loggerConfig.getName( ) ) ;
			logger.fatal( "loggerConfig.getState( ) :: " + loggerConfig.getState( ) ) ;
			
			ctx.updateLoggers( ) ;
			resultBool = true ;
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.fatal( e.getMessage( ) , e ) ;
		}
		finally {
			
			appender = null ;
			loggerConfig = null ;
			config = null ;
			ctx = null ;
			
			logName = null ;
			paramLevel = null ;
			strFileSize = null ;
			logger.fatal( "::::: 변경 후 :::::" ) ;
			logger.trace( "로그" ) ;
			logger.debug( "로그" ) ;
			logger.info( "로그" ) ;
			logger.warn( "로그" ) ;
			logger.error( "로그" ) ;
			logger.fatal( "로그" ) ;
			logger.fatal( "::::: changeLoggerSetting finally:::::" ) ;
		}
		
		return resultBool ;
		
	}
	
	/**
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-26
	 * @param paramLevel
	 * @param strFileSize
	 * @return
	 */
	public Boolean changeLoggerRootSetting( Level paramLevel , String strFileSize ) {
		Boolean resultBool = true ;
		
		LoggerContext ctx = null ;
		Configuration config = null ;
		LoggerConfig loggerConfig = null ;
		Appender appender = null ;
		
		try {
			
			logger.fatal( "::::: changeLoggerSetting :::::" ) ;
			logger.trace( "로그" ) ;
			logger.debug( "로그" ) ;
			logger.info( "로그" ) ;
			logger.warn( "로그" ) ;
			logger.error( "로그" ) ;
			logger.fatal( "로그" ) ;
			logger.fatal( "::::: 변경 전 :::::" ) ;
			logger.fatal( "---------------------------" ) ;
			logger.fatal( "paramLevel :: " + paramLevel ) ;
			logger.fatal( "strFileSize :: " + strFileSize ) ;
			logger.fatal( "---------------------------" ) ;
			
			resultBool = false ;
			
			ctx = ( LoggerContext ) LogManager.getContext( false ) ;
			config = ctx.getConfiguration( ) ;
			loggerConfig = config.getLoggerConfig( LogManager.ROOT_LOGGER_NAME ) ;
			
			appender = setAppender( strFileSize ) ;
			
			appender.start( ) ;
			config.addAppender( appender ) ;
			
			logger.fatal( "appender :: " + appender ) ;
			logger.fatal( "appender.getLayout( ) :: " + appender.getLayout( ) ) ;
			logger.fatal( "appender.getHandler( ) :: " + appender.getHandler( ) ) ;
			logger.fatal( "appender.getClass( ) :: " + appender.getClass( ) ) ;
			
			// loggerConfig.addAppender( appender , Level.DEBUG , null ) ;
			// loggerConfig.setLevel( Level.DEBUG ) ;
			loggerConfig.addAppender( appender , paramLevel , null ) ;
			loggerConfig.setLevel( paramLevel ) ;
			
			logger.fatal( "config :: " + config ) ;
			logger.fatal( "loggerConfig :: " + loggerConfig ) ;
			logger.fatal( "loggerConfig.getAppenders( ) :: " + loggerConfig.getAppenders( ) ) ;
			logger.fatal( "loggerConfig.getAppenderRefs( ) :: " + loggerConfig.getAppenderRefs( ) ) ;
			logger.fatal( "loggerConfig.getLevel( ) :: " + loggerConfig.getLevel( ) ) ;
			logger.fatal( "loggerConfig.getName( ) :: " + loggerConfig.getName( ) ) ;
			logger.fatal( "loggerConfig.getState( ) :: " + loggerConfig.getState( ) ) ;
			
			ctx.updateLoggers( ) ;
			resultBool = true ;
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.fatal( e.getMessage( ) , e ) ;
		}
		finally {
			
			appender = null ;
			loggerConfig = null ;
			config = null ;
			ctx = null ;
			
			paramLevel = null ;
			strFileSize = null ;
			
			logger.fatal( "::::: 변경 후 :::::" ) ;
			logger.trace( "로그" ) ;
			logger.debug( "로그" ) ;
			logger.info( "로그" ) ;
			logger.warn( "로그" ) ;
			logger.error( "로그" ) ;
			logger.fatal( "로그" ) ;
			logger.fatal( "::::: changeLoggerSetting finally:::::" ) ;
		}
		
		return resultBool ;
		
	}
	
}
