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
	private String strSiteSmlt = "" ;
	private String strSiteSmltUsr = "" ;
	private List< Map< String , Object > > gatherDataEventList = new ArrayList< Map< String , Object > >( ) ;
	private Map< String , Object > stdvInfMap = new HashMap< String , Object >( ) ;
	
	/**
	 * @param strDviceId 설치 디바이스 아이디
	 * @param strDmt 계측 시간
	 * @param strRecordQc 레코드 QC 정보
	 * @param strSiteSmlt 사이트 시뮬레이션 모드
	 * @param strSiteSmltUsr 사이트 시뮬레이션 모드 변경자
	 * @param stdvInfMap 설치 디바이스 기본 정보
	 * @param resultHistoryDataMap 계측 데이터 중 이력 저장 데이터
	 * @param resultCalCulDataMap 장치내 연산 데이터
	 * @param gatherDataEventList 계측시 발생한 이벤트 정보
	 */
	public DataGatherHistoryAndEvent( String strDviceId , String strDmt , String strRecordQc , String strSiteSmlt , String strSiteSmltUsr , Map< String , Object > stdvInfMap , HashMap< String , String > resultHistoryDataMap , HashMap< String , String > resultCalCulDataMap , List< Map< String , Object > > gatherDataEventList ) {
		
		try {
			this.strDviceId = strDviceId ;
			this.strDmt = strDmt ;
			this.strRecordQc = strRecordQc ;
			this.strSiteSmlt = strSiteSmlt ;
			this.strSiteSmltUsr = strSiteSmltUsr ;
			this.stdvInfMap = stdvInfMap ;
			this.resultHistoryDataMap = resultHistoryDataMap ;
			this.resultCalCulDataMap = resultCalCulDataMap ;
			this.gatherDataEventList = gatherDataEventList ;
		}
		finally {
			strDviceId = null ;
			strDmt = null ;
			strRecordQc = null ;
			strSiteSmlt = null ;
			strSiteSmltUsr = null ;
			stdvInfMap = null ;
			resultHistoryDataMap = null ;
			resultCalCulDataMap = null ;
			gatherDataEventList = null ;
		}
	}
	
	@Override
	public void run( ) {
		
		HashMap< String , Object > resultMongodbDataMap = new HashMap< String , Object >( ) ;
		String strTemp = "" ;
		
		DataGatherDao dataGatherDao = new DataGatherDao( ) ;
		CommUtil commUtil = new CommUtil( ) ;
		ConnectivityProperties connectivityProperties = new ConnectivityProperties( ) ;
		
		int i = 0 ;
		int intStdvDataSaveCycleCheckCnt = 0 ;
		
		String strStdvTp = "" ;
		String strStdvTrmSv = "" ;
		
		try {
			logger.debug( "DataGatherHistoryAndEvent run :: strDmt :: " + strDmt ) ;
			connectivityProperties.addOneProcessThreadCnt( );
			
			logger.debug( "stdvInfMap :: " + stdvInfMap ) ;
			
			////// TODO : 이력 저장 주기 처리
			
			strStdvTrmSv = ( stdvInfMap.get( "TRM_SV" ) + "" ) ;
			logger.debug( "strStdvTrmSv :: " + strStdvTrmSv ) ;
			
			// 데이터 저장 주기 체크 cnt + 1
			intStdvDataSaveCycleCheckCnt = connectivityProperties.addOneAndGetStdvDataSaveCycleCheckCnt( strDviceId ) ;
			logger.debug( "intStdvDataSaveCycleCheckCnt :: " + intStdvDataSaveCycleCheckCnt ) ;
			
			if( "1".equals( intStdvDataSaveCycleCheckCnt + "" ) ) {
				// 데이터 저장 주기 체크 cnt = 1 이면 저장처리한다.
				logger.debug( "이력 데이터 저장" ) ;
				
				// mongodb 저장
				
				////// old 데이터의 존재로 인해 _id는 '기존 디바이스아이디_계측시간' 이 아닌 uuid로 처리한다.
				// strTemp = strDmt.replaceAll( " " , "" ).replaceAll( "-" , "" ).replaceAll( ":" , "" ).replaceAll( "\\." , "" ) ;
				// logger.debug( "strTemp :: " + strTemp ) ;
				// resultMongodbDataMap.put( "_id" , ( strDviceId + "_" + strTemp ) ) ;
				
				strTemp = strDmt.split( " " )[ 0 ] ;
				// logger.debug( "strTemp :: " + strTemp ) ;
				resultMongodbDataMap.put( "YMD" , strTemp ) ;
				
				resultMongodbDataMap.put( "DTM" , strDmt ) ;
				resultMongodbDataMap.put( "STDV_ID" , strDviceId ) ;
				strStdvTp = stdvInfMap.get( "TP" ) + "" ;
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
				
			} // if( "1".equals( intStdvDataSaveCycleCheckCnt + "" ) )
			if( strStdvTrmSv.equals( ( intStdvDataSaveCycleCheckCnt + "" ) ) ) {
				// 데이터 저장 주기 체크 cnt 와 디바이스에 설정된 저장 주기가 같으면 데이터 저장 주기 체크 cnt를 0으로 변경한다.
				logger.debug( "데이터 저장 주기 체크 cnt 초기화" ) ;
				connectivityProperties.setZeroStdvDataSaveCycleCheckCnt( strDviceId ) ;
				logger.debug( "intStdvDataSaveCycleCheckCnt :: " + intStdvDataSaveCycleCheckCnt ) ;
			}
			
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
			connectivityProperties.subtractOneProcessThreadCnt( );
			
			resultMongodbDataMap = null ;
			strTemp = null ;
			dataGatherDao = null ;
			commUtil = null ;
			connectivityProperties = null ;
			strStdvTp = null ;
			strStdvTrmSv = null ;
			
			i = 0 ;
			intStdvDataSaveCycleCheckCnt = 0 ;
			
			this.strDviceId = null ;
			this.strDmt = null ;
			this.strRecordQc = null ;
			this.strSiteSmlt = null ;
			this.strSiteSmltUsr = null ;
			this.stdvInfMap = null ;
			this.resultHistoryDataMap = null ;
			this.resultCalCulDataMap = null ;
			this.gatherDataEventList = null ;
			
			logger.debug( "DataGatherHistoryAndEvent run finally" ) ;
		}
		
	}
	
}
