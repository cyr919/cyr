/**
 * 
 */
package com.connectivity.manage ;

import java.util.HashMap ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.manage.dao.ConditionReportDao ;
import com.connectivity.utils.CommUtil ;

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
	private String strPID = "" ;
	private String strProcessID = "" ;
	private int intReportInterval = 0 ;
	
	/**
	 * @param intReportInterval 초 단위로 입력
	 * @param strProcessID ProcessID(디비에서 읽어옴??)
	 * @param strPID PID
	 */
	public ConditionReport( int intReportInterval , String strProcessID , String strPID ) {
		this.intReportInterval = ( intReportInterval * 1000 ) ;
		this.strState = "run" ;
		this.isDemonLive = true ;
		this.strPID = strPID ;
		this.strProcessID = strProcessID ;
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
			
			logger.info( "while 종료 :: isDemonLive :: "  + isDemonLive ) ;
		}
		catch( InterruptedException e ) {
			logger.info( "ConditionReport run 종료 요청 :: interrupt" ) ;
			
			logger.error( e.getMessage( ) , e ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			this.stopConditionSet( ) ;
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
		
		ConditionReportDao ConditionReportDao = new ConditionReportDao( ) ;
		CommUtil commUtil = new CommUtil( ) ;
		
		String redisSetKey = "" ;
		String strDateTime = "" ;
		HashMap< String , String > redisSetDataMap = new HashMap< String , String >( ) ;
		try {
			redisSetKey = "MGP_PSST" + "^" + strProcessID ;
			
			strDateTime = commUtil.getFormatingNowDateTime( ) ;
			
			redisSetDataMap.put( "PID" , strPID ) ;
			redisSetDataMap.put( "STAT" , "RUN" ) ;
			redisSetDataMap.put( "LSDT" , strDateTime ) ;
			
			ConditionReportDao.hmSetCondition( redisSetKey , redisSetDataMap ) ;
		}
		finally {
			ConditionReportDao = null ;
			commUtil = null ;
			redisSetKey = null ;
			strDateTime = null ;
			redisSetDataMap = null ;
		}
		
		return ;
	}
	
	public void stopConditionSet( ) {
		
		ConditionReportDao ConditionReportDao = new ConditionReportDao( ) ;
		CommUtil commUtil = new CommUtil( ) ;
		
		String redisSetKey = "" ;
		String strDateTime = "" ;
		HashMap< String , String > redisSetDataMap = new HashMap< String , String >( ) ;
		try {
			redisSetKey = "MGP_PSST" + "^" + strProcessID ;
			
			strDateTime = commUtil.getFormatingNowDateTime( ) ;
			
			redisSetDataMap.put( "PID" , strPID ) ;
			redisSetDataMap.put( "STAT" , "STOP" ) ;
			redisSetDataMap.put( "LSDT" , strDateTime ) ;
			
			ConditionReportDao.hmSetCondition( redisSetKey , redisSetDataMap ) ;
		}
		finally {
			ConditionReportDao = null ;
			commUtil = null ;
			redisSetKey = null ;
			strDateTime = null ;
			redisSetDataMap = null ;
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
