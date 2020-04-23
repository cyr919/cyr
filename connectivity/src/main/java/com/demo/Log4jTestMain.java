
package com.demo ;

import java.util.Date ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

public class Log4jTestMain
{
	
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	Logger logger = LogManager.getLogger( Log4jTestMain.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	public static void main( String[ ] args ) {
		// TODO Auto-generated method stub
		
		Log4jTestMain exe = new Log4jTestMain( ) ;
		exe.runLoof( ) ;
		
	}
	
	public void runLoof( ) {
		
		for( int i = 0 ; i < 1500 ; i++ ) {
			
			logger.info( "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" ) ;
			logger.info( "i :: " + i ) ;
			System.out.println( "안녕" ) ;
			
			logger.trace( "로그 찍기 : trace" ) ;
			logger.debug( "로그 찍기 : debug" ) ;
			logger.info( "로그 찍기 : info" ) ;
			logger.warn( "로그 찍기 : warn" ) ;
			logger.error( "로그 찍기 : error" ) ;
			logger.fatal( "로그 찍기 : fatal" ) ;
			
			// this.getDalayTime(60);
			try {
				Thread.sleep( ( 60 * 1000 ) ) ;
			}
			catch( InterruptedException e ) {
				// TODO Auto-generated catch block
				e.printStackTrace( ) ;
			}
			logger.info( "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" ) ;
			
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
