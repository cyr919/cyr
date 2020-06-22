/**
 * 
 */
package com.connectivity.gather ;

import java.util.HashMap ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.common.ConnectivityProperties ;
import com.connectivity.gather.dao.DataGatherDao ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-06-12
 */
public class DataGatherHistoryAndEvent implements Runnable
{
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private Logger logger = LogManager.getLogger( this.getClass( ) ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	private HashMap< String , String > resultDataMap = new HashMap< String , String >( ) ;
	private HashMap< String , String > resultCalCulDataMap = new HashMap< String , String >( ) ;
	private String strDviceId = "" ;
	private String strDmt = "" ;
	private String strRecordQc = "" ;
	private String strStdvTp = "" ;
	private String strSiteSmlt = "" ;
	private String strSiteSmltUsr = "" ;

	/**
	 * @param strDviceId 설치 디바이스 아이디
	 * @param strDmt 계측 시간
	 * @param strRecordQc 레코드 QC 정보
	 * @param strStdvTp 설치 디바이스 유형
	 * @param resultDataMap 계측 데이터
	 * @param resultCalCulDataMap 장치내 연산 데이터
	 */
	/**
	 * @param strDviceId 설치 디바이스 아이디
	 * @param strDmt 계측 시간
	 * @param strRecordQc 레코드 QC 정보
	 * @param strStdvTp 설치 디바이스 유형
	 * @param strSiteSmlt 사이트 시뮬레이션 모드
	 * @param strSiteSmltUsr 사이트 시뮬레이션 모드 변경자
	 * @param resultDataMap 계측 데이터
	 * @param resultCalCulDataMap 장치내 연산 데이터
	 */
	public DataGatherHistoryAndEvent( String strDviceId , String strDmt , String strRecordQc , String strStdvTp , String strSiteSmlt, String strSiteSmltUsr , HashMap< String , String > resultDataMap , HashMap< String , String > resultCalCulDataMap ) {
		
		try {
			this.resultDataMap = resultDataMap ;
			this.resultCalCulDataMap = resultCalCulDataMap ;
			this.strDviceId = strDviceId ;
			this.strDmt = strDmt ;
			this.strRecordQc = strRecordQc ;
			this.strStdvTp = strStdvTp ;
			this.strSiteSmlt = strSiteSmlt ;
			this.strSiteSmltUsr = strSiteSmltUsr ;
		}
		finally {
			resultDataMap = null ;
			resultCalCulDataMap = null ;
			strDviceId = null ;
			strDmt = null ;
			strRecordQc = null ;
			strStdvTp = null ;
		}
	}
	
	@Override
	public void run( ) {
		// TODO Auto-generated method stub
		HashMap< String , Object > resultMongodbDataMap = new HashMap< String , Object >( ) ;
		String strTemp = "" ;
		DataGatherDao dataGatherDao = new DataGatherDao( ) ;
		
		try {
			
			logger.debug( "DataGatherHistoryAndEvent run :: strDmt :: " + strDmt ) ;
			ConnectivityProperties.PROCESS_THREAD_CNT++ ;
			
			// mongodb 저장
			
			strTemp = strDmt.replaceAll( " " , "" ).replaceAll( "-" , "" ).replaceAll( ":" , "" ).replaceAll( "\\." , "" ) ;
			// logger.debug( "strTemp :: " + strTemp ) ;
			
//			resultMongodbDataMap.put( "_id" , ( strDviceId + "_" + strTemp ) ) ;
			
			// strStdvTp = stdvInfMap.get( "TP" ) ;
			
			strTemp = strDmt.split( " " )[ 0 ] ;
			resultMongodbDataMap.put( "YMD" , strTemp ) ;
			resultMongodbDataMap.put( "DTM" , strDmt ) ;
			resultMongodbDataMap.put( "STDV_ID" , strDviceId ) ;
			resultMongodbDataMap.put( "STDV_TP" , strStdvTp ) ;
			resultMongodbDataMap.put( "Q" , strRecordQc ) ;
			// 시뮬레이션 모드 정보 추가
			if( "Y".equals( this.strSiteSmlt ) ) {
				resultMongodbDataMap.put( "SMLT" , "Y" ) ;
				resultMongodbDataMap.put( "SMLT_USR" , this.strSiteSmltUsr ) ;
			}
			else {
				resultMongodbDataMap.put( "SMLT" , "N" ) ;
			}
			
			// logger.debug( "strTemp :: [" + strTemp +"]" ) ;
			
			// 장치내 연산 데이터 및 계측 데이터
			resultCalCulDataMap.putAll( resultDataMap ) ;
			resultMongodbDataMap.put( "DT" , resultCalCulDataMap ) ;
			
			logger.debug( "mongodb 저장 데이터 :: resultMongodbDataMap :: " + resultMongodbDataMap ) ;
			// logger.debug( "resultCalCulDataMap :: " + resultCalCulDataMap ) ;
			// logger.debug( "resultDataMap :: " + resultDataMap ) ;
			
			// 계측 데이터 mongodb 저장처리
			dataGatherDao.insertGatherData( resultMongodbDataMap ) ;
			// TODO 이벤트 처리
			
			
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			
			resultMongodbDataMap = null ;
			strTemp = null ;
			dataGatherDao = null ;
			
			this.resultDataMap = null ;
			this.resultCalCulDataMap = null ;
			this.strDviceId = null ;
			this.strDmt = null ;
			this.strRecordQc = null ;
			this.strStdvTp = null ;
			
			ConnectivityProperties.PROCESS_THREAD_CNT-- ;
			logger.debug( "DataGatherHistoryAndEvent run finally" ) ;
		}
		
	}
	
}
