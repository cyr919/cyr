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
 * 장치간 연산 히스토리 데이터 저장 및 이벤트 처리
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
	
	private String strSiteSmlt = "" ;
	private String strSiteSmltUsr = "" ;
	
	public BetweenDevicesCalculateHistoryAndEvent( String strFirstDmt , HashMap< String , Object > resultRstDataMap , String strSiteSmlt , String strSiteSmltUsr ) {
		
		try {
			this.strFirstDmt = strFirstDmt ;
			this.resultRstDataMap = resultRstDataMap ;
			this.strSiteSmlt = strSiteSmlt ;
			this.strSiteSmltUsr = strSiteSmltUsr ;
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
		
		HashMap< String , Object > resultMongodbDataMap = new HashMap< String , Object >( ) ;

		try {
			logger.debug( "BetweenDevicesCalculateHistoryAndEvent run" ) ;
			ConnectivityProperties.PROCESS_THREAD_CNT++ ;
			
			// mongodb 저장 - 표준 모델
			logger.debug( "strFirstDmt :: " + strFirstDmt ) ;
			
			resultMongodbDataMap.put( "_id" , this.strFirstDmt ) ;
			resultMongodbDataMap.put( "RST" , this.resultRstDataMap ) ;
			
			// 시뮬레이션 모드 정보 추가
			if( "Y".equals( this.strSiteSmlt ) ) {
				resultMongodbDataMap.put( "SMLT" , "Y" ) ;
				resultMongodbDataMap.put( "SMLT_USR" , this.strSiteSmltUsr ) ;
			}
			else {
				resultMongodbDataMap.put( "SMLT" , "N" ) ;
			}
			
			betweenDevicesCalculateDao.insertBtwnDvCalculData( resultMongodbDataMap ) ;
			
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
