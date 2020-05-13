
package com ;

import java.util.Date ;
import java.util.HashMap ;

import org.apache.logging.log4j.Level ;
import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

/**
 *
 * <pre>
 *  * 개발 완료 후 Log4j2Util 로 옮기기

 * </pre>
 *
 * @author cyr
 * @date 2020-05-13
 */
public class Log4jTestMain
{
	
	Logger logger = LogManager.getLogger( ) ;
	
	public static void main( String[ ] args ) {
		// TODO Auto-generated method stub
		
		Log4jTestMain exe = new Log4jTestMain( ) ;
		exe.runLoof( ) ;
		
	}
	
	public void runLoof( ) {
		
		HashMap< String , Object > paramHashmap = null ;
		
		LoggerLevelChange exe = new LoggerLevelChange( ) ;
		
		try {
			
			for( int i = 0 ; i < 10000 ; i++ ) {
				
				logger.info( "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" ) ;
				logger.info( "i :: " + i ) ;
				System.out.println( "안녕" ) ;
				
				logger.trace( "로그 찍기 : trace" ) ;
				logger.debug( "로그 찍기 : debug" ) ;
				logger.info( "로그 찍기 : info" ) ;
				logger.warn( "로그 찍기 : warn" ) ;
				logger.error( "로그 찍기 : error" ) ;
				logger.fatal( "로그 찍기 : fatal" ) ;
				
				if( i == 1000 ) {
					exe.changeLoggerSetting( Level.DEBUG , "500KB" ) ;
				}
				if( i == 2000 ) {
					exe.changeLoggerSetting( Level.INFO , "1MB" ) ;
				}
				if( i == 3000 ) {
					exe.changeLoggerSetting( Level.TRACE , "500KB" ) ;
				}
				
				// this.getDalayTime(60);
				try {
					// Thread.sleep( ( 10 * 1000 ) ) ;
					Thread.sleep( ( 1 * 1000 ) ) ;
				}
				catch( InterruptedException e ) {
					logger.error( e.getMessage( ) , e ) ;
					e.printStackTrace( ) ;
				}
				logger.info( "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" ) ;
				// paramHashmap.get( "dd" ) ;
				
			}
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
			e.printStackTrace( ) ;
			
		}
		finally {
			System.out.println( "finally" ) ;
			System.out.println( "System.out.println" ) ;
		}
		
		return ;
	}
	
	/**
	 * <pre>
	 * delayTimeSecond(초) 만큼 시간이 지난 후 리턴한다.
	 * </pre>
	 * 
	 * @param
	 * @return void
	 * @date 2019. 10. 23.
	 */
	public void getDalayTime( int delayTimeSecond ) {
		
		Date nowDate = new Date( ) ;
		
		long nowTimeMillis = 0L ;
		long EndTimeMillis = 0L ;
		
		try {
			logger.trace( ":::::::::: getDalayTime " ) ;
			
			logger.trace( "nowDate " + nowDate ) ;
			delayTimeSecond = delayTimeSecond * 1000 ;
			
			nowTimeMillis = nowDate.getTime( ) ;
			EndTimeMillis = nowTimeMillis + delayTimeSecond ;
			logger.trace( "nowTimeMillis " + nowTimeMillis ) ;
			logger.trace( "EndTimeMillis " + EndTimeMillis ) ;
			
			logger.trace( "------------------------------------------" ) ;
			while( nowTimeMillis < EndTimeMillis ) {
				nowDate = new Date( ) ;
				nowTimeMillis = nowDate.getTime( ) ;
			}
			logger.trace( "------------------------------------------" ) ;
			logger.trace( "nowTimeMillis " + nowTimeMillis ) ;
			
			logger.trace( ":::::::::: getDalayTime End " ) ;
		}
		catch( Exception e ) {
			e.printStackTrace( ) ;
		}
		finally {
			delayTimeSecond = 0 ;
			nowDate = null ;
			nowTimeMillis = 0L ;
			EndTimeMillis = 0L ;
		}
	}
	
}
