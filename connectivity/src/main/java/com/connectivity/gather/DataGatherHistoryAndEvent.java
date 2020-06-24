/**
 * 
 */
package com.connectivity.gather ;

import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.List ;
import java.util.Map ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.common.ConnectivityProperties ;
import com.connectivity.gather.dao.DataGatherDao ;
import com.connectivity.utils.CommUtil ;

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
	
	private HashMap< String , String > resultHistoryDataMap = new HashMap< String , String >( ) ;
	private HashMap< String , String > resultCalCulDataMap = new HashMap< String , String >( ) ;
	private String strDviceId = "" ;
	private String strDmt = "" ;
	private String strRecordQc = "" ;
	private String strStdvTp = "" ;
	private String strSiteSmlt = "" ;
	private String strSiteSmltUsr = "" ;
	private List< Map< String , Object > > gatherDataEventList = new ArrayList< Map< String , Object > >( ) ;
	
	/**
	 * @param strDviceId 설치 디바이스 아이디
	 * @param strDmt 계측 시간
	 * @param strRecordQc 레코드 QC 정보
	 * @param strStdvTp 설치 디바이스 유형
	 * @param strSiteSmlt 사이트 시뮬레이션 모드
	 * @param strSiteSmltUsr 사이트 시뮬레이션 모드 변경자
	 * @param resultHistoryDataMap 계측 데이터 중 이력 저장 데이터
	 * @param resultCalCulDataMap 장치내 연산 데이터
	 */
	public DataGatherHistoryAndEvent( String strDviceId , String strDmt , String strRecordQc , String strStdvTp , String strSiteSmlt , String strSiteSmltUsr , HashMap< String , String > resultHistoryDataMap , HashMap< String , String > resultCalCulDataMap , List< Map< String , Object > > gatherDataEventList ) {
		
		try {
			this.gatherDataEventList = gatherDataEventList ;
			this.resultHistoryDataMap = resultHistoryDataMap ;
			this.resultCalCulDataMap = resultCalCulDataMap ;
			this.strDviceId = strDviceId ;
			this.strDmt = strDmt ;
			this.strRecordQc = strRecordQc ;
			this.strStdvTp = strStdvTp ;
			this.strSiteSmlt = strSiteSmlt ;
			this.strSiteSmltUsr = strSiteSmltUsr ;
		}
		finally {
			resultHistoryDataMap = null ;
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
		
		CommUtil commUtil = new CommUtil( ) ;
		int i = 0 ;
		try {
			
			logger.debug( "DataGatherHistoryAndEvent run :: strDmt :: " + strDmt ) ;
			ConnectivityProperties.PROCESS_THREAD_CNT++ ;
			
			// mongodb 저장
			
			strTemp = strDmt.replaceAll( " " , "" ).replaceAll( "-" , "" ).replaceAll( ":" , "" ).replaceAll( "\\." , "" ) ;
			// logger.debug( "strTemp :: " + strTemp ) ;
			
			// resultMongodbDataMap.put( "_id" , ( strDviceId + "_" + strTemp ) ) ;
			
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
			resultCalCulDataMap.putAll( resultHistoryDataMap ) ;
			resultMongodbDataMap.put( "DT" , resultCalCulDataMap ) ;
			
			logger.debug( "mongodb 저장 데이터 :: resultMongodbDataMap :: " + resultMongodbDataMap ) ;
			// logger.debug( "resultCalCulDataMap :: " + resultCalCulDataMap ) ;
			// logger.debug( "resultDataMap :: " + resultDataMap ) ;
			
			// 계측 데이터 mongodb 저장처리
			dataGatherDao.insertGatherData( resultMongodbDataMap ) ;
			
			// TODO 이벤트 처리
			if( !commUtil.checkNull( gatherDataEventList ) ) {
				for( i = 0 ; i < gatherDataEventList.size( ) ; i++ ) {
					logger.debug( "gatherDataEventList.get( " + i + " ) :: " + gatherDataEventList.get( i ) ) ;
					
				}
			}
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			
			resultMongodbDataMap = null ;
			strTemp = null ;
			dataGatherDao = null ;
			
			this.gatherDataEventList = null ;
			this.resultHistoryDataMap = null ;
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
