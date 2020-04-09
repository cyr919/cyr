/**
 * 
 */
package com.prototype.connectivity ;

import org.apache.log4j.Logger ;

import com.prototype.RedisDataDao ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-04-08
 */
public class ConditionReport implements Runnable
{
	static Logger logger = Logger.getLogger( ConditionReport.class ) ;
	
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
		
		RedisDataDao redisDataDao = new RedisDataDao( ) ;
		long resultLong = 0L ;
		
		try {
			resultLong = redisDataDao.redisHset( "conditions" , "connectivity" , "run" ) ;
		}
		finally {
			redisDataDao = null ;
			resultLong = 0L ;
		}
		
		return ;
	}
	
	public void stopConditionSet( ) {
		RedisDataDao redisDataDao = new RedisDataDao( ) ;
		long resultLong = 0L ;
		
		try {
			logger.debug( "runConditionSet :: stop" ) ;
			resultLong = redisDataDao.redisHset( "conditions" , "connectivity" , "stop" ) ;
			
			// TODO 이벤트 처리 추가 필요
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			redisDataDao = null ;
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
