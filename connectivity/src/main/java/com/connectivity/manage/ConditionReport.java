/**
 * 
 */
package com.connectivity.manage ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.common.RedisCommon ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-04-08
 */
public class ConditionReport implements Runnable
{
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	Logger logger = LogManager.getLogger( ConditionReport.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	private boolean isDemonLive = true ;
	private String strState = "" ;
	private int intReportInterval = 0 ;
	
	/**
	 * @param intReportInterval 초 단위로 입력
	 */
	public ConditionReport( int intReportInterval ) {
		this.intReportInterval = ( intReportInterval * 1000 ) ;
		this.strState = "run" ;
		this.isDemonLive = true ;
		return ;
	}
	
	@Override
	public void run( ) {
		
		try {
			
			logger.info( "ConditionReport run 시작" ) ;
			
			while( isDemonLive ) {
				
				this.runConditionSet( ) ;
				
				Thread.sleep( intReportInterval ) ;
			}
			
		}
		catch( InterruptedException e ) {
			logger.info( "ConditionReport run 종료 요청" ) ;
			this.stopConditionSet( ) ;
			
			logger.error( e.getMessage( ) , e ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			logger.info( "ConditionReport run 종료" ) ;
		}
		
		return ;
	}
	
	/**
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-04-08
	 */
	public void runConditionSet( ) throws Exception {
		
		RedisCommon redisCommon = new RedisCommon( ) ;
		long resultLong = 0L ;
		
		try {
			resultLong = redisCommon.redisHset( "conditions" , "connectivity" , "run" ) ;
		}
		finally {
			redisCommon = null ;
			resultLong = 0L ;
		}
		
		return ;
	}
	
	public void stopConditionSet( ) {
		RedisCommon redisCommon = new RedisCommon( ) ;
		long resultLong = 0L ;
		
		try {
			logger.debug( "runConditionSet :: stop" ) ;
			resultLong = redisCommon.redisHset( "conditions" , "connectivity" , "stop" ) ;
			
			// TODO 이벤트 처리 추가 필요
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			redisCommon = null ;
			resultLong = 0L ;
		}
		
		return ;
	}
	
	/**
	 * @return the strState
	 */
	public String getStrState( ) {
		return strState ;
	}
	
	public void setStopReport( ) throws Exception {
		
		this.strState = "stop" ;
		this.isDemonLive = false ;
		
		return ;
	}
	
}
