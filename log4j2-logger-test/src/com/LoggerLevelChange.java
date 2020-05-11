/**
 * 
 */
package com ;

import java.util.zip.Deflater ;

import org.apache.logging.log4j.Level ;
import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;
import org.apache.logging.log4j.core.Appender ;
import org.apache.logging.log4j.core.Layout ;
import org.apache.logging.log4j.core.LoggerContext ;
import org.apache.logging.log4j.core.appender.ConsoleAppender ;
import org.apache.logging.log4j.core.appender.RollingFileAppender ;
import org.apache.logging.log4j.core.appender.rolling.CompositeTriggeringPolicy ;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy ;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy ;
import org.apache.logging.log4j.core.appender.rolling.TimeBasedTriggeringPolicy ;
import org.apache.logging.log4j.core.config.Configuration ;
import org.apache.logging.log4j.core.config.Configurator ;
import org.apache.logging.log4j.core.config.LoggerConfig ;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder ;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder ;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder ;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory ;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder ;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder ;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration ;
import org.apache.logging.log4j.core.layout.PatternLayout ;

/**
 * <pre>
 * 
 * https://stackoverflow.com/questions/23434252/programmatically-change-log-level-in-log4j2
 * https://logging.apache.org/log4j/log4j-2.4/faq.html
 * https://logging.apache.org/log4j/2.x/manual/customconfig.html
 * https://www.baeldung.com/log4j2-programmatic-config
 * https://github.com/eugenp/tutorials/tree/master/logging-modules/log4j2
 * </pre>
 *
 * @author cyr
 * @date 2020-05-11
 */
public class LoggerLevelChange
{
	private Logger logger = LogManager.getLogger( ) ;
	
	/**
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-05-11
	 * @param args
	 */
	public static void main( String[ ] args ) {
		// TODO Auto-generated method stub
		
		//////////////////////////////////////////
		// changeLoggerSetting test .. 로그 레벨 적용 불가
		//////////////////////////////////////////
		LoggerLevelChange exe = new LoggerLevelChange( ) ;
		exe.changeLoggerLevel( Level.ERROR ) ;
		
		// 설정 변경
		exe.changeLoggerSetting( ) ;
		
		LoggerLevelChange exe1 = new LoggerLevelChange( ) ;
		exe1.changeLoggerLevel( Level.INFO ) ;
		
		LoggerLevelChange exe2 = new LoggerLevelChange( ) ;
		exe2.changeLoggerLevel( Level.WARN ) ;
		
		//////////////////////////////////////////
		
	}
	
	public Boolean changeLoggerLevel( Level paramLevel ) {
		
		Boolean resultBool = true ;
		
		try {
			System.out.println( "=================================" ) ;
			
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
			
			resultBool = false ;
			
			// Level level = Level.valueOf( "error" ) ;
			// logger.fatal( "---------------------------" ) ;
			// logger.fatal( "level :: " + level ) ;
			// logger.fatal( "---------------------------" ) ;
			
			LoggerContext ctx = ( LoggerContext ) LogManager.getContext( false ) ;
			Configuration config = ctx.getConfiguration( ) ;
			LoggerConfig loggerConfig = config.getLoggerConfig( LogManager.ROOT_LOGGER_NAME ) ;
			loggerConfig.setLevel( paramLevel ) ;
			// This causes all Loggers to refetch information from their LoggerConfig.
			ctx.updateLoggers( ) ;
			
			resultBool = true ;
			
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.fatal( e.getMessage( ) , e ) ;
		}
		finally {
			System.out.println( "=====finally============================" ) ;
			logger.trace( "로그" ) ;
			logger.debug( "로그" ) ;
			logger.info( "로그" ) ;
			logger.warn( "로그" ) ;
			logger.error( "로그" ) ;
			logger.fatal( "로그" ) ;
			System.out.println( "=================================" ) ;
			
		}
		
		return resultBool ;
	}
	
	/**
	 * <pre>
	 * https://logging.apache.org/log4j/2.x/manual/customconfig.html - Programmatically Modifying the Current Configuration after Initialization
	 * 
	 * https://stackify.com/log4j2-java/
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-05-11
	 * @return
	 */
	public Boolean changeLoggerSetting( ) {
		
		Boolean resultBool = true ;
		
		final String strPattern = "[%d{yyyy-MM-dd HH:mm:ss.SSS}] [${hostName}] [%t] %c{1} [%-5level] - %msg%n" ;
		final String strProjecNm = "log4j-test" ;
		
		try {
			
			LoggerContext ctx = ( LoggerContext ) LogManager.getContext( false ) ;
			Configuration config = ctx.getConfiguration( ) ;
			LoggerConfig loggerConfig = config.getLoggerConfig( LogManager.ROOT_LOGGER_NAME ) ;
			
			// Layout< ? > layout = PatternLayout.createLayout( strPattern , null , config , null , null , false , false , null , null ) ;
			PatternLayout layout = PatternLayout.createDefaultLayout( config ) ;
			
			// TimeBasedTriggeringPolicy timeBasedTriggeringPolicy = TimeBasedTriggeringPolicy.createPolicy( "1" , "false" ) ;
			TimeBasedTriggeringPolicy timeBasedTriggeringPolicy = TimeBasedTriggeringPolicy.newBuilder( ).withInterval( 1 ).build( ) ;
			
			SizeBasedTriggeringPolicy sizeBasedTriggeringPolicy = SizeBasedTriggeringPolicy.createPolicy( "1KB" ) ;
			
			CompositeTriggeringPolicy policy = CompositeTriggeringPolicy.createPolicy( timeBasedTriggeringPolicy , sizeBasedTriggeringPolicy ) ;
			// DefaultRolloverStrategy strategy = DefaultRolloverStrategy.createStrategy( null , null , "nomax" , null , null , false , config ) ;
			DefaultRolloverStrategy strategy = DefaultRolloverStrategy.newBuilder( ).withFileIndex( "nomax" ).build( ) ;
			
			// Appender appender = RollingFileAppender.createAppender( "./log/" + strProjecNm + ".log" , "./log/" + strProjecNm + ".log.%d{yyyy-MM-dd}.%i" , "true" , "rollingFile" , "true" , "1024" , null , policy , strategy , layout , null , null , null , null , config ) ;
			Appender appender = RollingFileAppender.newBuilder( ).setName( "rollingFile" )
							.withFileName( "./log/" + strProjecNm + ".log" )
							.withFilePattern( "./log/" + strProjecNm + ".log.%d{yyyy-MM-dd}.%i" )
							.withAppend( true ).withBufferedIo( true ).withBufferSize( 1024 )
							.setLayout( layout ).withPolicy( policy ).withStrategy( strategy ).build( ) ;
			
			appender.start( ) ;
			config.addAppender( appender ) ;
			
			loggerConfig.addAppender( appender , Level.DEBUG , null ) ;
			
			loggerConfig.setLevel( Level.DEBUG ) ;
			
			ctx.updateLoggers( ) ;
			
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.fatal( e.getMessage( ) , e ) ;
		}
		finally {
			
			System.out.println( "=================================" ) ;
		}
		
		return resultBool ;
		
	}
	
	/**
	 * <pre>
	 * https://logging.apache.org/log4j/2.x/manual/customconfig.html
	 * Reconfigure Log4j Using ConfigurationBuilder with the Configurator
	 * 
	 * https://www.baeldung.com/log4j2-programmatic-config
	 * 설정 후 사용 클래스를 다시 생성해줘야한다.
	 * After this is invoked, future calls to Log4j 2 will use our configuration.
	 * Note that this means that we need to invoke Configurator.initialize before we make any calls to LogManager.getLogger.
	 * 이것이 호출 된 후, Log4j 2에 대한 향후 호출은 구성을 사용합니다.
	 * 이것은 LogManager.getLogger 를 호출  하기 전에  Configurator.initialize 를 호출해야 함을 의미합니다.
	 * 
	 * 해당 클래스 실행 후 LogManager.getLogger를 재 호출해야 작동됨. 로그 레벨 적용 안됨.
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-05-11
	 * @return
	 */
	public Boolean changeLoggerSetting_2( ) {
		
		Boolean resultBool = true ;
		
		final String strPattern = "[%d{yyyy-MM-dd HH:mm:ss.SSS}] [${hostName}] [%t] %c{1} [%-5level] - %msg%n" ;
		final String strProjecNm = "log4j-test" ;
		
		try {
			
			ConfigurationBuilder< BuiltConfiguration > builder = ConfigurationBuilderFactory.newConfigurationBuilder( ) ;
			
			builder.setStatusLevel( Level.DEBUG ) ;
			builder.setConfigurationName( "RollingBuilder" ) ;
			// create a console appender
			AppenderComponentBuilder appenderBuilder = builder.newAppender( "console" , "Console" ).addAttribute( "target" ,
							ConsoleAppender.Target.SYSTEM_OUT ) ;
			appenderBuilder.add( builder.newLayout( "PatternLayout" ).addAttribute( "pattern" , strPattern ) ) ;
			builder.add( appenderBuilder ) ;
			
			// create a rolling file appender
			LayoutComponentBuilder layoutBuilder = builder.newLayout( "PatternLayout" ).addAttribute( "pattern" , strPattern ) ;
			
			ComponentBuilder< ? > triggeringPolicy = builder.newComponent( "Policies" )
							.addComponent( builder.newComponent( "TimeBasedTriggeringPolicy" ).addAttribute( "interval" , "1" ) )
							.addComponent( builder.newComponent( "SizeBasedTriggeringPolicy" ).addAttribute( "size" , "1KB" ) ) ;
			
			ComponentBuilder< ? > triggeringRolloverStrategy = builder.newComponent( "DefaultRolloverStrategy" )
							.addAttribute( "fileIndex" , "nomax" ) ;
			
			appenderBuilder = builder.newAppender( "rollingFile" , "RollingFile" )
							.addAttribute( "fileName" , "./log/" + strProjecNm + ".log" )
							.addAttribute( "filePattern" , "./log/" + strProjecNm + ".log.%d{yyyy-MM-dd}.%i" )
							.addAttribute( "append" , "true" )
							.addAttribute( "bufferedIO" , "true" )
							.addAttribute( "bufferSize" , "1024" )
							.add( layoutBuilder )
							.addComponent( triggeringPolicy )
							.addComponent( triggeringRolloverStrategy ) ;
			builder.add( appenderBuilder ) ;
			
			builder.add( builder.newRootLogger( Level.DEBUG )
							.add( builder.newAppenderRef( "console" ) )
							.add( builder.newAppenderRef( "rollingFile" ) ) ) ;
			LoggerContext ctx = Configurator.initialize( builder.build( ) ) ;
			
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.fatal( e.getMessage( ) , e ) ;
			System.out.println( e ) ;
		}
		finally {
			
			System.out.println( "=================================" ) ;
		}
		
		return resultBool ;
		
	}
	
	public Boolean changeLoggerSetting_1( ) {
		
		Boolean resultBool = true ;
		
		final String strPattern = "[%d{yyyy-MM-dd HH:mm:ss.SSS}] [${hostName}] [%t] %c{1} [%-5level] - %msg%n" ;
		final String strProjecNm = "log4j-test" ;
		
		try {
			
			ConfigurationBuilder< BuiltConfiguration > builder = ConfigurationBuilderFactory.newConfigurationBuilder( ) ;
			
			// Console
			
			AppenderComponentBuilder console = builder.newAppender( "Console" , "Console" ).addAttribute(
							"target" , ConsoleAppender.Target.SYSTEM_OUT ) ;
			
			// RollingFile
			AppenderComponentBuilder rollingFile = builder.newAppender( "rollingFile" , "RollingFile" ) ;
			rollingFile.addAttribute( "fileName" , "./log/" + strProjecNm + ".log" ) ;
			rollingFile.addAttribute( "filePattern" , "./log/" + strProjecNm + ".log.%d{yyyy-MM-dd}.%i" ) ;
			rollingFile.addAttribute( "append" , "true" ) ;
			rollingFile.addAttribute( "bufferedIO" , "true" ) ;
			rollingFile.addAttribute( "bufferSize" , "1024" ) ;
			
			ComponentBuilder< ? > triggeringPolicies = builder.newComponent( "Policies" ) ;
			triggeringPolicies.addComponent( builder.newComponent( "TimeBasedTriggeringPolicy" ).addAttribute( "interval" , "1" ) ) ;
			triggeringPolicies.addComponent( builder.newComponent( "SizeBasedTriggeringPolicy" ).addAttribute( "size" , "4KB" ) ) ;
			
			rollingFile.addComponent( triggeringPolicies ) ;
			
			ComponentBuilder< ? > triggeringRolloverStrategy = builder.newComponent( "DefaultRolloverStrategy" ) ;
			triggeringRolloverStrategy.addAttribute( "fileIndex" , "nomax" ) ;
			
			rollingFile.addComponent( triggeringRolloverStrategy ) ;
			
			// PatternLayout
			LayoutComponentBuilder standard = builder.newLayout( "PatternLayout" ) ;
			standard.addAttribute( "pattern" , strPattern ) ;
			console.add( standard ) ;
			rollingFile.add( standard ) ;
			
			// Loggers
			RootLoggerComponentBuilder rootLogger = builder.newRootLogger( Level.DEBUG ) ;
			rootLogger.add( builder.newAppenderRef( "Console" ) ) ;
			rootLogger.add( builder.newAppenderRef( "rollingFile" ) ) ;
			
			// builder에 구성 추가
			builder.add( console ) ;
			builder.add( rollingFile ) ;
			builder.add( rootLogger ) ;
			
			logger.fatal( "----------------------" ) ;
			logger.fatal( "로거 설정 변경 후" ) ;
			
			// 개발 시 확인 용.
			builder.writeXmlConfiguration( System.out ) ;
			
			// 적용
			LoggerContext ctx = Configurator.initialize( builder.build( ) ) ;
			
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.fatal( e.getMessage( ) , e ) ;
			System.out.println( e ) ;
		}
		finally {
			
			System.out.println( "=================================" ) ;
		}
		
		return resultBool ;
		
	}
	
}
