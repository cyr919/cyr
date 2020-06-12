/**
 * 
 */
package com.connectivity.calculate ;

import java.util.HashMap ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.calculate.dao.BetweenDevicesCalculateDao ;
import com.connectivity.common.ConnectivityProperties ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-06-12
 */
public class BetweenDevicesCalculateHistoryAndEvent implements Runnable
{
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private Logger logger = LogManager.getLogger( this.getClass( ) ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	private String strFirstDmt = "" ;
	private HashMap< String , Object > resultRstDataMap = new HashMap< String , Object >( ) ;
	
	public BetweenDevicesCalculateHistoryAndEvent( String strFirstDmt , HashMap< String , Object > resultRstDataMap ) {
		
		try {
			this.strFirstDmt = strFirstDmt ;
			this.resultRstDataMap = resultRstDataMap ;
		}
		finally {
			strFirstDmt = null ;
			resultRstDataMap = null ;
		}
	}
	
	@Override
	public void run( ) {
		// TODO Auto-generated method stub
		BetweenDevicesCalculateDao betweenDevicesCalculateDao = new BetweenDevicesCalculateDao( ) ;
		
		try {
			logger.debug( "BetweenDevicesCalculateHistoryAndEvent run" ) ;
			ConnectivityProperties.PROCESS_THREAD_CNT++ ;
			
			// mongodb 저장 - 표준 모델
			logger.debug( "strFirstDmt :: " + strFirstDmt ) ;
			betweenDevicesCalculateDao.insertBtwnDvCalculData( this.strFirstDmt , this.resultRstDataMap ) ;
			
			// TODO 이벤트 처리
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			betweenDevicesCalculateDao = null ;
			
			this.strFirstDmt = null ;
			this.resultRstDataMap = null ;
			
			ConnectivityProperties.PROCESS_THREAD_CNT-- ;
			logger.debug( "BetweenDevicesCalculateHistoryAndEvent run finally" ) ;
		}
	}
	
}
