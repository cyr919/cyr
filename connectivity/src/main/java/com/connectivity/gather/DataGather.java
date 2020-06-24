package com.connectivity.gather ;

import java.math.BigDecimal ;
import java.math.RoundingMode ;
import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.List ;
import java.util.Map ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;
import org.json.simple.JSONObject ;

import com.connectivity.common.CommonProperties ;
import com.connectivity.common.ConnectivityProperties ;
import com.connectivity.common.dao.CommonDao ;
import com.connectivity.config.JedisConnection ;
import com.connectivity.config.MongodbConnection ;
import com.connectivity.gather.dao.DataGatherDao ;
import com.connectivity.quality.QualityCode ;
import com.connectivity.utils.CommUtil ;
import com.connectivity.utils.ExtndEgovStringUtil ;
import com.connectivity.utils.JsonUtil ;

public class DataGather extends QualityCode
{
	
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private Logger logger = LogManager.getLogger( this.getClass( ) ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	// private QualityCode qualityCode = new QualityCode( ConnectivityProperties.RECORD_QC_INF , ConnectivityProperties.RECORD_QC_IDX , ConnectivityProperties.FIELD_QC_INF , ConnectivityProperties.FIELD_QC_IDX ) ;
	private CommUtil commUtil = new CommUtil( ) ;
	private ExtndEgovStringUtil extndEgovStringUtil = new ExtndEgovStringUtil( ) ;
	private DataGatherDao dataGatherDao = new DataGatherDao( ) ;
	
	public static void main( String[ ] args ) {
		
		CommonProperties commonProperties = new CommonProperties( ) ;
		JedisConnection jedisConnection = new JedisConnection( ) ;
		MongodbConnection mongodbConnection = new MongodbConnection( ) ;
		ConnectivityProperties connectivityProperties = new ConnectivityProperties( ) ;
		
		try {
			commonProperties.setProperties( ) ;
			connectivityProperties.setConnectivityProperties( ) ;
			jedisConnection.getJedisPool( ) ;
			mongodbConnection.getMongoClient( ) ;
		}
		catch( Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace( ) ;
		}
		
		DataGather exe = new DataGather( ) ;
		String testData = "" ;
		
		// testData = "{\"STDV_ID\":\"stdv0004\",\"DATA\":{\"MGP040\":16,\"MGP041\":10,\"MGP044\":105,\"MGP023\":70,\"MGP045\":34,\"MGP042\":47,\"MGP043\":38,\"MGP026\":19,\"MGP048\":48,\"MGP027\":17,\"MGP049\":67,\"MGP024\":69,\"MGP046\":45,\"MGP025\":80,\"MGP047\":28,\"MGP039\":90,\"MGP051\":24,\"MGP030\":82,\"MGP052\":73,\"MGP050\":42,\"MGP033\":88,\"MGP055\":28,\"MGP034\":66,\"MGP056\":57,\"MGP031\":86,\"MGP053\":87,\"MGP032\":25,\"MGP054\":38,\"MGP037\":22,\"MGP038\":70,\"MGP035\":23,\"MGP057\":109,\"MGP036\":99,\"MGP028\":88,\"MGP029\":52},\"DMT\":\"2020-05-21 09:12:57.396\"}" ;
		// testData = "{\"STDV_ID\":\"stdv0002\",\"DATA\":{\"MGP022\":85,\"MGP021\":50},\"DMT\":\"2020-05-21 13:00:34.747\"}" ;
		// testData = "{\"STDV_ID\":\"stdv0001\",\"DATA\":{\"MGP011\":17,\"MGP001\":36,\"MGP012\":81,\"MGP020\":33,\"MGP010\":93,\"MGP004\":27,\"MGP015\":91,\"MGP005\":30,\"MGP016\":58,\"MGP002\":109,\"MGP013\":25,\"MGP003\":48,\"MGP014\":98,\"MGP008\":85,\"MGP019\":78,\"MGP009\":34,\"MGP006\":31,\"MGP017\":42,\"MGP007\":24,\"MGP018\":98},\"DMT\":\"2020-05-21 17:51:29.044\"}" ;
		// exe.dataGathering( testData ) ;
		
		// testData = "{\"STDV_ID\":\"stdv0004\",\"DATA\":{\"MGP040\":16,\"MGP041\":10,\"MGP044\":105,\"MGP023\":70,\"MGP045\":34,\"MGP042\":47,\"MGP043\":38,\"MGP026\":19,\"MGP048\":48,\"MGP027\":17,\"MGP049\":67,\"MGP024\":69,\"MGP046\":45,\"MGP025\":80,\"MGP047\":28,\"MGP039\":90,\"MGP051\":24,\"MGP030\":82,\"MGP052\":73,\"MGP050\":42,\"MGP033\":88,\"MGP055\":28,\"MGP034\":66,\"MGP056\":57,\"MGP031\":86,\"MGP053\":87,\"MGP032\":25,\"MGP054\":38,\"MGP037\":22,\"MGP038\":70,\"MGP035\":23,\"MGP057\":109,\"MGP036\":99,\"MGP028\":88,\"MGP029\":52},\"DMT\":\"2020-05-21 09:12:57.396\"}" ;
		testData = "{\"STDV_ID\":\"stdv0002\",\"DATA\":{\"MGP022\":85,\"MGP021\":5000000},\"DMT\":\"2020-05-21 13:00:34.747\"}" ;
		// testData = "{\"STDV_ID\":\"stdv0001\",\"DATA\":{\"MGP011\":17,\"MGP001\":36,\"MGP012\":81,\"MGP020\":33,\"MGP010\":93,\"MGP004\":27,\"MGP015\":91,\"MGP005\":30,\"MGP016\":58,\"MGP002\":109,\"MGP013\":25,\"MGP003\":48,\"MGP014\":98,\"MGP008\":85,\"MGP019\":78,\"MGP009\":34,\"MGP006\":31,\"MGP017\":42,\"MGP007\":24,\"MGP018\":98},\"DMT\":\"2020-05-21 17:51:29.044\"}" ;
		exe.dataGathering( testData ) ;
		//
		// testData = "{\"STDV_ID\":\"stdv0001\",\"DATA\":{\"MGP011\":17,\"MGP001\":36,\"MGP012\":81,\"MGP020\":33,\"MGP010\":93,\"MGP004\":27,\"MGP015\":91,\"MGP005\":30,\"MGP016\":58,\"MGP002\":109,\"MGP013\":25,\"MGP003\":48,\"MGP014\":98,\"MGP008\":85,\"MGP019\":78,\"MGP009\":34,\"MGP006\":31,\"MGP017\":42,\"MGP007\":24,\"MGP018\":98},\"DMT\":\"2020-05-21 17:51:29.044\"}" ;
		// testData = "{\"STDV_ID\":\"stdv0002\",\"DATA\":{\"MGP022\":85,\"MGP021\":5000000},\"DMT\":\"2020-05-21 13:00:34.747\"}" ;
		// exe.dataGathering( testData ) ;
		// testData = "{\"STDV_ID\":\"stdv0002\",\"DATA\":{\"MGP022\":85,\"MGP021\":5000000},\"DMT\":\"2020-05-21 13:00:34.747\"}" ;
		// exe.dataGathering( testData ) ;
		// testData = "{\"STDV_ID\":\"stdv0002\",\"DATA\":{\"MGP022\":85,\"MGP021\":5000000},\"DMT\":\"2020-05-21 13:00:34.747\"}" ;
		// exe.dataGathering( testData ) ;
		//
		// testData = "{\"STDV_ID\":\"stdv0004\",\"DATA\":{\"MGP040\":16,\"MGP041\":10,\"MGP044\":105,\"MGP023\":70,\"MGP045\":34,\"MGP042\":47,\"MGP043\":38,\"MGP026\":19,\"MGP048\":48,\"MGP027\":17,\"MGP049\":67,\"MGP024\":69,\"MGP046\":45,\"MGP025\":80,\"MGP047\":28,\"MGP039\":90,\"MGP051\":24,\"MGP030\":82,\"MGP052\":73,\"MGP050\":42,\"MGP033\":88,\"MGP055\":28,\"MGP034\":66,\"MGP056\":57,\"MGP031\":86,\"MGP053\":87,\"MGP032\":25,\"MGP054\":38,\"MGP037\":22,\"MGP038\":70,\"MGP035\":23,\"MGP057\":109,\"MGP036\":99,\"MGP028\":88,\"MGP029\":52},\"DMT\":\"2020-05-21 09:12:57.396\"}" ;
		// exe.dataGathering( testData ) ;
	}
	
	/**
	 * <pre>
	 * 계측 처리
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-18
	 * @param strJsonData 수신된 계측 데이터
	 * @return
	 */
	public Boolean dataGathering( String strJsonData ) {
		Boolean resultBool = true ;
		
		String strDviceId = "" ;
		String strDmt = "" ;
		JsonUtil jsonUtil = new JsonUtil( ) ;
		JSONObject jsonObject = new JSONObject( ) ;
		JSONObject subDataJSONObject = new JSONObject( ) ;
		// TODO 일단 hashmap으로 해보고 object 변환시 좀 오래 걸리는거같으면 바꾸기
		// 계측 처리 데이터
		HashMap< String , String > resultDataMap = new HashMap< String , String >( ) ;
		HashMap< String , String > resultHistoryDataMap = new HashMap< String , String >( ) ;
		// 장치간 연산 처리 데이터
		HashMap< String , String > resultCalCulDataMap = new HashMap< String , String >( ) ;
		// 레디스 저장 데이터
		HashMap< String , String > resultRedisDataMap = new HashMap< String , String >( ) ;
		// AppIO 저장 데이터
		HashMap< String , String > resultAppioDataMap = new HashMap< String , String >( ) ;
		// 장치내 연산 정보
		List< Map< String , Object > > stdvCalInfList = new ArrayList< Map< String , Object > >( ) ;
		List< Map< String , Object > > stdvDtMdlList = new ArrayList< Map< String , Object > >( ) ;
		// HashMap< String , HashMap< String , Object > > stdvDtMdlMap = new HashMap< String , HashMap< String , Object > >( ) ;
		List< Map< String , Object > > appioMapperList = new ArrayList< Map< String , Object > >( ) ;
		Map< String , Object > stdvInfMap = new HashMap< String , Object >( ) ;
		BigDecimal tempBigDecimal = new BigDecimal( "0" ) ;
		int i = 0 ;
		String tempQcStr = "" ;
		String gatherValStr = "" ;
		String strMgpKey = "" ;
		String strPointIdx = "" ;
		
		String resultRecordQc = "" ;
		String resultOldDataQc = "" ;
		HashMap< String , Object > tempFildQcMap = new HashMap< String , Object >( ) ;
		
		List< Map< String , Object > > gatherDataEventList = new ArrayList< Map< String , Object > >( ) ;
		HashMap< String , Object > gatherDataEventMap = new HashMap< String , Object >( ) ;
		DataGatherHistoryAndEvent historyAndEvent = null ;
		Thread historyAndEventThread = null ;
		
		CommonDao commonDao = new CommonDao( ) ;
		
		String strDviceSkip = "" ;
		
		try {
			logger.debug( "dataGathering ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: " ) ;
			logger.debug( "수신된 데이터 :: " + strJsonData ) ;
			
			// 수집된 json data 파싱
			jsonObject = jsonUtil.getJSONObjectFromString( ( strJsonData + "" ) ) ;
			
			// 디바이스 아이디와 계측 시간 추출
			strDviceId = ( String ) jsonObject.get( "STDV_ID" ) ;
			strDmt = ( String ) jsonObject.get( "DMT" ) ;
			subDataJSONObject = ( JSONObject ) jsonObject.get( "DATA" ) ;
			logger.debug( "dataGathering :: strDmt :: " + strDmt ) ;
			
			logger.debug( "strDviceId :: " + strDviceId ) ;
			// logger.debug( "strDmt :: " + strDmt ) ;
			// logger.debug( "subDataJSONObject :: " + subDataJSONObject ) ;
			
			// 데이터 맵 정보 map static 변수에서 가지고 오기
			stdvDtMdlList = ConnectivityProperties.STDV_DT_MDL.get( strDviceId ) ;
			// stdvDtMdlMap = ConnectivityProperties.STDV_DT_MDL_MAP.get( strDviceId ) ;
			// 디바이스 기본 정보 map static 변수에서 가지고 오기
			stdvInfMap = ConnectivityProperties.STDV_INF.get( strDviceId ) ;
			// 계측 Appio 정보
			appioMapperList = ConnectivityProperties.APPIO_MAPPER_SDHS.get( strDviceId ) ;
			// logger.debug( "stdvDtMdlMap :: " + stdvDtMdlMap ) ;
			logger.debug( "stdvInfMap :: " + stdvInfMap ) ;
			// logger.debug( "ConnectivityProperties.STDV_DT_MDL.get( " + strDviceId + " ) :: " + ConnectivityProperties.STDV_DT_MDL.get( strDviceId ) ) ;
			
			stdvCalInfList = ConnectivityProperties.STDV_CAL_INF.get( strDviceId ) ;
			strDviceSkip = stdvInfMap.get( "SKIP" ) + "" ;
			
			logger.debug( "strDviceSkip :: " + strDviceSkip ) ;
			logger.debug( "ConnectivityProperties.STDV_INF :: " + ConnectivityProperties.STDV_INF ) ;
			
			// 레코드 QC 처리 관련 true 부터 시작한다.
			resultRecordQc = initRecordQualityCode( ) ;
			
			// Old Data 필드 QC 판단 - 계측 시간 관련 된 내용이기에 각 필드마다 데이터를 판단할 필요 없음.
			resultOldDataQc = gatherOldDataQC( strDmt , strDviceId , strDviceSkip ) ;
			// OldData 필드 QC 레코드 QC 확인
			resultRecordQc = gatherRepresentData( resultRecordQc , resultOldDataQc ) + "" ;
			logger.debug( "resultOldDataQc :: " + resultOldDataQc ) ;
			logger.debug( "resultRecordQc :: " + resultRecordQc ) ;
			
			for( i = 0 ; i < stdvDtMdlList.size( ) ; i++ ) {
				logger.debug( "stdvDtMdlList.get( " + i + " ) :: " + stdvDtMdlList.get( i ) ) ;
				// logger.debug( "stdvDtMdlList.get( " + i + " ).get( \"MGP_KEY\" ) :: " + stdvDtMdlList.get( i ).get( "MGP_KEY" ) ) ;
				
				strMgpKey = stdvDtMdlList.get( i ).get( "MGP_KEY" ) + "" ;
				
				// 시뮬레이션 값 치환 또는 스케일 팩터 적용
				if( "Y".equals( strSiteSmlt ) && "Y".equals( stdvDtMdlList.get( i ).get( "SMLT" ) ) ) {
					// 시뮬레이션 값 적용
					gatherValStr = ( stdvDtMdlList.get( i ).get( "SMLT" ) + "" ) ;
				}
				else {
					// 스케일 팩터 값 적용
					tempBigDecimal = applyScaleFactor( subDataJSONObject.get( strMgpKey ) , stdvDtMdlList.get( i ).get( "SC_FCT" ) ) ;
					gatherValStr = extndEgovStringUtil.getStringFromNullAndObject( tempBigDecimal ) ;
				}
				
				// TODO unit convert factor 적용
				
				// 계측 처리맵에 추가
				resultDataMap.put( ( strMgpKey + "" ) , gatherValStr ) ;
				
				//// 계측 필드 데이터 QC 적용
				// 초기값
				tempQcStr = initFieldQualityCode( ) ;
				
				// 이벤트 정보 저장 map
				gatherDataEventMap = new HashMap< String , Object >( ) ;
				
				//// 계측 필드 QC 적용
				// OldData 필드 QC 적용 , 위에서 판단한 old qc를 필드 qc에 추가 한다.
				tempQcStr = gatherOldDataApplyField( tempQcStr , resultOldDataQc ) ;
				
				// OverFlow 필드 QC 레코드 QC 같이 확인
				tempFildQcMap = gatherOverFlow( tempQcStr , gatherValStr , stdvDtMdlList.get( i ) , resultRecordQc , gatherDataEventMap , strDviceSkip ) ;
				tempQcStr = tempFildQcMap.get( "resultFieldQc" ) + "" ;
				resultRecordQc = tempFildQcMap.get( "resultTempRecordQc" ) + "" ;
				
				// 필드 QC 처리하면서 이벤트 정보도 받는다.
				if( gatherDataEventMap.size( ) > 0 ) {
					logger.debug( "gatherDataEventMap :: " + gatherDataEventMap ) ;
					gatherDataEventMap.put( "MGP_KEY" , strMgpKey ) ;
					gatherDataEventList.add( gatherDataEventMap ) ;
				}
				
				tempQcStr = gatherMeasurementMode( tempQcStr , strDviceSkip ) ;
				tempQcStr = gatherCalculationMode( tempQcStr , strDviceSkip ) ;
				tempQcStr = gatherSimulationMode( tempQcStr , strDviceSkip ) ;
				
				// 계측 처리맵에 필드 QC 추가
				resultDataMap.put( ( strMgpKey + "_Q" ) , tempQcStr ) ;
				
				if( "1".equals( stdvDtMdlList.get( i ).get( "HST" ) + "" ) ) {
					// 이력 저장 데이터 생성
					resultHistoryDataMap.put( ( strMgpKey + "" ) , gatherValStr ) ;
					resultHistoryDataMap.put( ( strMgpKey + "_Q" ) , tempQcStr ) ;
				}
				
				// logger.debug( "tempQcStr ::" + tempQcStr ) ;
			}
			
			logger.debug( "계측 처리 후 ::" ) ;
			logger.debug( "resultDataMap :: " + resultDataMap ) ;
			logger.debug( "resultHistoryDataMap :: " + resultHistoryDataMap ) ;
			logger.debug( "계측 처리 후 끝 ::" ) ;
			
			logger.debug( "계측 이벤트 처리 gatherDataEventList :: " + gatherDataEventList ) ;
			
			// TODO 계측 레코드 데이터 QC 확인
			logger.debug( "계측 레코드 데이터 QC :: resultRecordQc :: " + resultRecordQc ) ;
			
			// 장치내 연산 데이터 생성 + 장치내 연산 데이터 필드 QC 적용
			// 연산 정보 가지고 오기
			resultCalCulDataMap = getCalculatingGatherData( resultDataMap , stdvCalInfList ) ;
			
			logger.debug( "연산 처리 후 ::" ) ;
			logger.debug( "resultCalCulDataMap :: " + resultCalCulDataMap ) ;
			logger.debug( "연산 처리 후 끝 ::" ) ;
			
			//// redis 저장
			
			// MGP 데이터 모델 저장 데이터 만들기
			// 아이디/계측시간 추가
			resultRedisDataMap.put( "ID" , strDmt ) ;
			// 레코드 QC 추가
			resultRedisDataMap.put( "Q" , resultRecordQc ) ;
			// 시뮬레이션 모드 정보 추가
			resultRedisDataMap.put( "SMLT" , strSiteSmlt ) ;
			// 계측 데이터 추가
			resultRedisDataMap.putAll( resultDataMap ) ;
			// 연산 데이터 추가
			resultRedisDataMap.putAll( resultCalCulDataMap ) ;
			
			logger.debug( "redis 저장 데이터 :: resultRedisDataMap :: " + resultRedisDataMap ) ;
			
			// redis 데이터 생성 - Appio 데이터 모델
			if( !commUtil.checkNull( appioMapperList ) ) {
				for( i = 0 ; i < appioMapperList.size( ) ; i++ ) {
					logger.debug( "appioMapperList.get( " + i + " ) :: " + appioMapperList.get( i ) ) ;
					// appIO Point Idx
					strPointIdx = ( appioMapperList.get( i ).get( "TG_ID" ) + "" ) ;
					// MGP_KEY
					strMgpKey = ( appioMapperList.get( i ).get( "SO_KEY" ) + "" ) ;
					// map 형태인 redis 저장 데이터로 처리한다.
					resultAppioDataMap.put( strPointIdx , resultRedisDataMap.get( strMgpKey ) ) ;
				}
			}
			logger.debug( "appoi 저장  데이터 :: resultAppioDataMap :: " + resultAppioDataMap ) ;
			
			// redis 데이터 저장 처리 - Appio 데이터 모델
			resultBool = commonDao.hmSetAppioData( resultAppioDataMap ) ;
			resultAppioDataMap = null ;
			
			// redis 데이터 저장 처리 - MGP 표준 모델
			resultBool = dataGatherDao.hmSetGatherData( strDviceId , resultRedisDataMap ) ;
			resultRedisDataMap = null ;
			
			// logger.debug( "resultCalCulDataMap :: " + resultCalCulDataMap ) ;
			// logger.debug( "resultDataMap :: " + resultDataMap ) ;
			
			//// history 저장 및 이벤트 데이터 처리 thread 생성
			logger.debug( "history 저장 및 이벤트 데이터 처리 thread 생성 :: " ) ;
			
			historyAndEvent = new DataGatherHistoryAndEvent( strDviceId , strDmt , resultRecordQc , ( stdvInfMap.get( "TP" ) + "" ) , strSiteSmlt , strSiteSmltUsr , resultHistoryDataMap , resultCalCulDataMap , gatherDataEventList ) ;
			// historyAndEventThread = new Thread( historyAndEvent , "DataGatherHistoryAndEvent" ) ;
			historyAndEventThread = new Thread( historyAndEvent ) ;
			historyAndEventThread.start( ) ;
			logger.debug( "history 저장 및 이벤트 데이터 처리 thread 생성 :: 시작 완료 " ) ;
			
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			
			strJsonData = null ;
			
			strDviceId = null ;
			strDmt = null ;
			jsonUtil = null ;
			jsonObject = null ;
			subDataJSONObject = null ;
			resultDataMap = null ;
			resultCalCulDataMap = null ;
			resultRedisDataMap = null ;
			resultAppioDataMap = null ;
			stdvDtMdlList = null ;
			appioMapperList = null ;
			stdvInfMap = null ;
			tempBigDecimal = null ;
			tempQcStr = null ;
			gatherValStr = null ;
			strMgpKey = null ;
			strPointIdx = null ;
			resultRecordQc = null ;
			tempFildQcMap = null ;
			gatherDataEventList = null ;
			gatherDataEventMap = null ;
			historyAndEvent = null ;
			historyAndEventThread = null ;
			commonDao = null ;
			i = 0 ;
			logger.debug( "dataGathering finally ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: " ) ;
			
		}
		
		return resultBool ;
	}
	
	// public Boolean dataGathering_1( String strJsonData ) {
	// Boolean resultBool = true ;
	//
	// String strDviceId = "" ;
	// String strDmt = "" ;
	// String strTemp = "" ;
	// JsonUtil jsonUtil = new JsonUtil( ) ;
	// JSONObject jsonObject = new JSONObject( ) ;
	// JSONObject subDataJSONObject = new JSONObject( ) ;
	// // TODO 일단 hashmap으로 해보고 object 변환시 좀 오래 걸리는거같으면 바꾸기
	// HashMap< String , String > resultDataMap = new HashMap< String , String >( ) ;
	// HashMap< String , String > resultCalCulDataMap = new HashMap< String , String >( ) ;
	// HashMap< String , String > resultRedisDataMap = new HashMap< String , String >( ) ;
	//
	// HashMap< String , HashMap< String , Object > > stdvDtMdlMap = new HashMap< String , HashMap< String , Object > >( ) ;
	// HashMap< String , Object > stdvInfMap = new HashMap< String , Object >( ) ;
	// BigDecimal tempBigDecimal = new BigDecimal( "0" ) ;
	//
	// String tempQcStr = "" ;
	// String gatherValStr = "" ;
	//
	// String resultRecordQc = "" ;
	// HashMap< String , Object > tempFildQcMap = new HashMap< String , Object >( ) ;
	//
	// DataGatherHistoryAndEvent historyAndEvent = null ;
	// Thread historyAndEventThread = null ;
	//
	// try {
	// logger.debug( "dataGathering :: " ) ;
	// logger.debug( "수신된 데이터 :: " + strJsonData ) ;
	//
	// // 수집된 json data 파싱
	// jsonObject = jsonUtil.getJSONObjectFromString( ( strJsonData + "" ) ) ;
	//
	// // 디바이스 아이디와 계측 시간 추출
	// strDviceId = ( String ) jsonObject.get( "STDV_ID" ) ;
	// strDmt = ( String ) jsonObject.get( "DMT" ) ;
	// subDataJSONObject = ( JSONObject ) jsonObject.get( "DATA" ) ;
	// logger.debug( "dataGathering :: strDmt :: " + strDmt ) ;
	//
	// // logger.debug( "strDviceId :: " + strDviceId ) ;
	// // logger.debug( "strDmt :: " + strDmt ) ;
	// // logger.debug( "subDataJSONObject :: " + subDataJSONObject ) ;
	//
	// // 데이터 맵 정보 map 가지고 오기
	// stdvDtMdlMap = ConnectivityProperties.STDV_DT_MDL_MAP.get( strDviceId ) ;
	// stdvInfMap = ConnectivityProperties.STDV_INF.get( strDviceId ) ;
	//
	// logger.debug( "stdvDtMdlMap :: " + stdvDtMdlMap ) ;
	// logger.debug( "stdvInfMap :: " + stdvInfMap ) ;
	// // logger.debug( "ConnectivityProperties.STDV_DT_MDL.get( " + strDviceId + " ) :: " + ConnectivityProperties.STDV_DT_MDL.get( strDviceId ) ) ;
	//
	// // 레코드 QC 처리 관련 true 부터 시작한다.
	// resultRecordQc = initRecordQualityCode( ) ;
	//
	// // 수집된 계측 데이터 처리
	// for( Object key : subDataJSONObject.keySet( ) ) {
	// // logger.debug( "key :: " + key ) ;
	// // logger.debug( "subDataJSONObject.get( key ) :: " + subDataJSONObject.get( key ) ) ;
	// // logger.debug( "stdvDtMdlMap.get( key ) :: " + stdvDtMdlMap.get( key ) ) ;
	//
	// // 시뮬레이션 값 치환 또는 스케일 팩터 적용
	// if( "Y".equals( stdvDtMdlMap.get( key ).get( "SMLT" ) ) ) {
	//
	// // 시뮬레이션 값 적용
	// gatherValStr = ( stdvDtMdlMap.get( key ).get( "SMLT_V" ) + "" ) ;
	// }
	// else {
	// // 스케일 팩터 값 적용
	// tempBigDecimal = applyScaleFactor( subDataJSONObject.get( key ) , stdvDtMdlMap.get( key ).get( "SC_FCT" ) ) ;
	// gatherValStr = extndEgovStringUtil.getStringFromNullAndObject( tempBigDecimal ) ;
	// }
	// resultDataMap.put( ( key + "" ) , gatherValStr ) ;
	//
	// //// TODO 계측 필드 데이터 QC 적용
	// // 초기값
	// tempQcStr = initFieldQualityCode( ) ;
	// //// 계측 필드 QC 적용
	// // OldData 필드 QC 레코드 QC 같이 확인
	// tempQcStr = gatherOldData( tempQcStr ) ;
	//
	// // OverFlow 필드 QC 레코드 QC 같이 확인
	// tempFildQcMap = gatherOverFlow( tempQcStr , gatherValStr , stdvDtMdlMap.get( key ) , resultRecordQc ) ;
	// tempQcStr = tempFildQcMap.get( "resultFieldQc" ) + "" ;
	// resultRecordQc = tempFildQcMap.get( "resultTempRecordQc" ) + "" ;
	//
	// tempQcStr = gatherMeasurementMode( tempQcStr ) ;
	// tempQcStr = gatherCalculationMode( tempQcStr ) ;
	// tempQcStr = gatherSimulationMode( tempQcStr ) ;
	//
	// resultDataMap.put( ( key + "_Q" ) , tempQcStr ) ;
	// //// 계측 필드 데이터 QC 적용
	//
	// // logger.debug( "tempQcStr ::" + tempQcStr ) ;
	//
	// } // subDataJSONObject.keySet( )
	// logger.debug( "계측 처리 후 ::" ) ;
	// logger.debug( "resultDataMap :: " + resultDataMap ) ;
	// logger.debug( "계측 처리 후 끝 ::" ) ;
	//
	// // TODO 계측 레코드 데이터 QC 확인
	// logger.debug( "계측 레코드 데이터 QC :: resultRecordQc :: " + resultRecordQc ) ;
	//
	// // 장치내 연산 데이터 생성 + 장치내 연산 데이터 필드 QC 적용
	// // 연산 정보 가지고 오기
	// resultCalCulDataMap = getCalculatingGatherData( resultDataMap , ConnectivityProperties.STDV_CAL_INF.get( strDviceId ) ) ;
	//
	// logger.debug( "연산 처리 후 ::" ) ;
	// logger.debug( "resultCalCulDataMap :: " + resultCalCulDataMap ) ;
	// logger.debug( "연산 처리 후 끝 ::" ) ;
	//
	// // redis 저장
	//
	// // redis 저장 데이터 만들기
	// // 계측 데이터 확인
	// // 설정 된 데이터 맵을 기준으로 app io 저장 여부를 확인한다.
	// for( Object key : stdvDtMdlMap.keySet( ) ) {
	//
	// // logger.debug( "stdvDtMdlMap.get( " + ( key + "" ) + " ).get( \"APP_IO\" ) :: [" + stdvDtMdlMap.get( ( key + "" ) ).get( "APP_IO" ) + "]" ) ;
	// // logger.debug( "checkObjNull :: " + ( !commUtil.checkObjNull( stdvDtMdlMap.get( ( key + "" ) ).get( "APP_IO" ) ) ) ) ;
	// // logger.debug( "equals :: " + ( "1".equals( stdvDtMdlMap.get( ( key + "" ) ).get( "APP_IO" ) + "" ) ) ) ;
	//
	// if( !commUtil.checkObjNull( stdvDtMdlMap.get( ( key + "" ) ).get( "APP_IO" ) ) && "1".equals( stdvDtMdlMap.get( ( key + "" ) ).get( "APP_IO" ) + "" ) ) {
	// // app io 저장 여부가 1 이면 redis에 저장될 데이터이다. 해당 되는 계측 데이터와 필드 QC를 redis 저장 map에 추가한다.
	//
	// // logger.debug( "APP_IO 저장 대상 :: " + ( key + "" ) ) ;
	// resultRedisDataMap.put( ( key + "" ) , extndEgovStringUtil.getStringFromNullAndObject( resultDataMap.get( ( key + "" ) ) ) ) ;
	// resultRedisDataMap.put( ( key + "_Q" ) , extndEgovStringUtil.getStringFromNullAndObject( resultDataMap.get( ( key + "_Q" ) ) ) ) ;
	// }
	// } // stdvDtMdlMap.keySet( )
	// // 아이디/계측시간 추가
	// resultRedisDataMap.put( "ID" , strDmt ) ;
	// // 레코드 QC 추가
	// resultRedisDataMap.put( "Q" , resultRecordQc ) ;
	// // 연산 데이터 추가
	// resultRedisDataMap.putAll( resultCalCulDataMap ) ;
	//
	// logger.debug( "redis 저장 데이터 :: resultRedisDataMap :: " + resultRedisDataMap ) ;
	//
	// // TODO redis 데이터 저장 처리 - appoi
	//
	// // redis 데이터 저장 처리 - 표준 모델
	// resultBool = dataGatherDao.hmSetGatherData( strDviceId , resultRedisDataMap ) ;
	// resultRedisDataMap = null ;
	//
	// // logger.debug( "resultCalCulDataMap :: " + resultCalCulDataMap ) ;
	// // logger.debug( "resultDataMap :: " + resultDataMap ) ;
	//
	// // history 저장 및 이벤트 데이터 처리 thread 생성
	// logger.debug( "history 저장 및 이벤트 데이터 처리 thread 생성 :: " ) ;
	//
	// historyAndEvent = new DataGatherHistoryAndEvent( strDviceId , strDmt , resultRecordQc , ( stdvInfMap.get( "TP" ) + "" ) , strSiteSmlt , strSiteSmltUsr , resultDataMap , resultCalCulDataMap ) ;
	// // historyAndEventThread = new Thread( historyAndEvent , "DataGatherHistoryAndEvent" ) ;
	// historyAndEventThread = new Thread( historyAndEvent ) ;
	// historyAndEventThread.start( ) ;
	// logger.debug( "history 저장 및 이벤트 데이터 처리 thread 생성 :: 시작 완료 " ) ;
	//
	// }
	// catch( Exception e ) {
	// resultBool = false ;
	// logger.error( e.getMessage( ) , e ) ;
	// }
	// finally {
	//
	// strJsonData = null ;
	//
	// strDviceId = null ;
	// strDmt = null ;
	// strTemp = null ;
	// jsonUtil = null ;
	// jsonObject = null ;
	// subDataJSONObject = null ;
	// // TODO 일단 hashmap으로 해보고 object 변환시 좀 오래 걸리는거같으면 바꾸기
	// resultDataMap = null ;
	// resultCalCulDataMap = null ;
	// resultRedisDataMap = null ;
	//
	// stdvDtMdlMap = null ;
	// stdvInfMap = null ;
	// tempBigDecimal = null ;
	// tempQcStr = null ;
	// gatherValStr = null ;
	//
	// resultRecordQc = null ;
	// tempFildQcMap = null ;
	//
	// historyAndEvent = null ;
	// historyAndEventThread = null ;
	//
	// logger.debug( "dataGathering finally :: " ) ;
	// }
	//
	// return resultBool ;
	// }
	
	/**
	 * <pre>
	 * 스케일 팩처 적용 함수.
	 * gatherData * scaleFactor 처리
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-05-21
	 * @param gatherData
	 * @param scaleFactor
	 * @return
	 */
	public BigDecimal applyScaleFactor( Object gatherData , Object scaleFactor ) {
		
		BigDecimal resultBigDecimal = new BigDecimal( "0" ) ;
		
		BigDecimal scaleFactorBigDecimal = new BigDecimal( "0" ) ;
		
		try {
			if( !commUtil.checkObjNull( ( gatherData ) ) ) {
				resultBigDecimal = new BigDecimal( ( gatherData + "" ) ) ;
				// logger.debug( "resultBigDecimal :: " + resultBigDecimal ) ;
				// logger.debug( "scaleFactor :: " + scaleFactor ) ;
				// logger.debug( "commUtil :: " + commUtil ) ;
				// logger.debug( "!commUtil.checkObjNull( ( scaleFactor ) :: " + !commUtil.checkObjNull( ( scaleFactor ) ) ) ;
				
				if( !commUtil.checkObjNull( ( scaleFactor ) ) ) {
					
					scaleFactorBigDecimal = new BigDecimal( scaleFactor + "" ) ;
					// logger.debug( "scaleFactorBigDecimal :: " + scaleFactorBigDecimal ) ;
					
					resultBigDecimal = resultBigDecimal.multiply( scaleFactorBigDecimal ) ;
				}
			}
			else {
				resultBigDecimal = null ;
			}
			// logger.debug( "resultBigDecimal :: " + resultBigDecimal ) ;
		}
		catch( Exception e ) {
			resultBigDecimal = null ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			gatherData = null ;
			scaleFactor = null ;
			scaleFactorBigDecimal = null ;
		}
		
		return resultBigDecimal ;
	}
	
	/**
	 * <pre>
	 * 장치내 연산 처리 function
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-05-21
	 * @param gatherDataHashMap 계측 데이터
	 * @param calculInfoList 장치 내 연산 설정 정보
	 * @return
	 */
	public HashMap< String , String > getCalculatingGatherData( Map< String , String > gatherDataHashMap , List< Map< String , Object > > calculInfoList ) {
		
		HashMap< String , String > resultHashMap = new HashMap< String , String >( ) ;
		
		BigDecimal resultBigDecimal = new BigDecimal( "0" ) ;
		BigDecimal paramBigDecimal = new BigDecimal( "0" ) ;
		BigDecimal scFctBigDecimal = new BigDecimal( "0" ) ;
		BigDecimal avgBigDecimal = new BigDecimal( "0" ) ;
		
		String[ ] stdIdxsArr = new String[ 0 ] ;
		String[ ] optrArr = new String[ 0 ] ;
		String strFuncOptr = "" ;
		int intPLen = 0 ;
		Boolean boolNullCheckPLen = true ;
		
		int i = 0 ;
		int j = 0 ;
		
		String resultQcStr = "" ;
		String strGatherFieldQc = "" ;
		
		try {
			
			if( !commUtil.checkNull( gatherDataHashMap ) && !commUtil.checkNull( calculInfoList ) ) {
				
				// logger.info( "getCalculatingGatherData :: " ) ;
				for( i = 0 ; i < calculInfoList.size( ) ; i++ ) {
					
					try {
						
						// logger.debug( "calculInfoList.get( " + i + " ) :: " + calculInfoList.get( i ) ) ;
						// logger.debug( "calculInfoList.get( " + i + " ).get( \"MGP_KEY\" ) :: " + calculInfoList.get( i ).get( "MGP_KEY" ) ) ;
						// logger.debug( "calculInfoList.get( " + i + " ).get( \"MGP_KEYS\" ) :: " + calculInfoList.get( i ).get( "MGP_KEYS" ) ) ;
						// logger.debug( "calculInfoList.get( " + i + " ).get( \"OPRT\" ) :: " + calculInfoList.get( i ).get( "OPRT" ) ) ;
						
						// 소수 점 길이 처리 확인
						if( commUtil.checkObjNull( calculInfoList.get( i ).get( "P_LEN" ) ) ) {
							boolNullCheckPLen = true ;
						}
						else {
							boolNullCheckPLen = false ;
							intPLen = Integer.parseInt( ( calculInfoList.get( i ).get( "P_LEN" ) + "" ) ) ;
						}
						
						// 피연산자 및 연산자 array 변환
						stdIdxsArr = ( calculInfoList.get( i ).get( "MGP_KEYS" ) + "" ).split( "," ) ;
						// TODO db 키명 변경 필요 -> OPRT
						optrArr = ( calculInfoList.get( i ).get( "OPRT" ) + "" ).split( "," ) ;
						// logger.debug( "stdIdxsArr :: " + Arrays.toString( stdIdxsArr ) ) ;
						// logger.debug( "optrArr :: " + Arrays.toString( optrArr ) ) ;
						
						// 함수 연산시 함수명 소문자 변환
						// TODO db 키명 변경 필요 -> OPRT_TP
						if( "002".equals( calculInfoList.get( i ).get( "OPRT_TP" ) ) ) {
							// 함수 스트링 소문자 변환
							strFuncOptr = optrArr[ 0 ] ;
							strFuncOptr = strFuncOptr.toLowerCase( ) ;
							// logger.debug( "strFuncOptr :: " + strFuncOptr ) ;
						}
						
						// 연산 처리(사칙연산, sum, min, max)
						for( j = 0 ; j < stdIdxsArr.length ; j++ ) {
							
							// logger.debug( "stdIdxsArr[ " + j + " ] :: " + stdIdxsArr[ j ] ) ;
							// logger.debug( "gatherDataHashMap.get( stdIdxsArr[ " + j + " ] ) :: " + gatherDataHashMap.get( stdIdxsArr[ j ] ) ) ;
							
							paramBigDecimal = new BigDecimal( gatherDataHashMap.get( stdIdxsArr[ j ] ) ) ;
							// logger.debug( "tempBigDecimal :: " + j + " :: " + tempBigDecimal ) ;
							
							// logger.debug( "calculInfoList.get( i ).get( \"OPRT_TP\" ) :: " + calculInfoList.get( i ).get( "OPRT_TP" ) ) ;
							if( "001".equals( calculInfoList.get( i ).get( "OPRT_TP" ) ) ) {
								// 사칙 연산
								if( j == 0 ) {
									resultBigDecimal = paramBigDecimal ;
								}
								else {
									if( "*".equals( optrArr[ ( j - 1 ) ] ) ) {
										resultBigDecimal = resultBigDecimal.multiply( paramBigDecimal ) ;
									}
									else if( "+".equals( optrArr[ ( j - 1 ) ] ) ) {
										resultBigDecimal = resultBigDecimal.add( paramBigDecimal ) ;
									}
									else if( "-".equals( optrArr[ ( j - 1 ) ] ) ) {
										resultBigDecimal = resultBigDecimal.subtract( paramBigDecimal ) ;
									}
									else if( "/".equals( optrArr[ ( j - 1 ) ] ) ) {
										resultBigDecimal = resultBigDecimal.divide( paramBigDecimal , intPLen , RoundingMode.DOWN ) ;
									}
									// logger.debug( "tempResultBigDecimal :: " + j + " :: " + tempResultBigDecimal ) ;
								}
								
							} // 사칙 연산
							else {
								// 함수 연산
								if( j == 0 ) {
									resultBigDecimal = paramBigDecimal ;
								}
								else {
									if( "sum".equals( strFuncOptr ) || "avg".equals( strFuncOptr ) ) {
										resultBigDecimal = resultBigDecimal.add( paramBigDecimal ) ;
									}
									else if( "min".equals( strFuncOptr ) ) {
										if( paramBigDecimal.compareTo( resultBigDecimal ) == -1 ) {
											resultBigDecimal = paramBigDecimal ;
										}
									}
									else if( "max".equals( strFuncOptr ) ) {
										if( paramBigDecimal.compareTo( resultBigDecimal ) == 1 ) {
											resultBigDecimal = paramBigDecimal ;
										}
									}
									
									// logger.debug( "tempResultBigDecimal :: " + j + " :: " + tempResultBigDecimal ) ;
								}
							} // 함수 연산
							
							// 장치내 연산 데이터 QC 적용
							// TODO 연산 QC 적용 : OldData, OverFlow
							// logger.debug( "::::: 연산 QC 적용 OldData, OverFlow :::::" ) ;
							// logger.debug( "stdIdxsArr[ " + j + " ] :: " + stdIdxsArr[ j ] ) ;
							// logger.debug( "stdIdxsArr[ " + j + " ]_Q :: " + ( stdIdxsArr[ j ] + "_Q" ) ) ;
							// logger.debug( "gatherDataHashMap.get( stdIdxsArr[ " + j + " ]_Q ) :: " + gatherDataHashMap.get( ( stdIdxsArr[ j ] + "_Q" ) ) ) ;
							
							strGatherFieldQc = gatherDataHashMap.get( ( stdIdxsArr[ j ] + "_Q" ) ) + "" ;
							if( j == 0 ) {
								// 연산 필드 QC 맨 처음 피연산자의 QC 값을 가진다. 임시 값
								
								resultQcStr = strGatherFieldQc ;
							}
							else {
								resultQcStr = calculateOldData( resultQcStr , strGatherFieldQc ) ;
								resultQcStr = calculateOverFlow( resultQcStr , strGatherFieldQc ) ;
							}
							
							// logger.debug( "resultQcStr :: " + resultQcStr ) ;
							// logger.debug( "::::: 연산 QC 적용 OldData, OverFlow end :::::" ) ;
							
						} // for( j = 0 ; j < stdIdxsArr.length ; j++ )
						
						// 함수 연산 - avg 시에 count 나누기
						if( "avg".equals( strFuncOptr ) ) {
							// stdIdxsArr.length
							avgBigDecimal = new BigDecimal( String.valueOf( stdIdxsArr.length ) ) ;
							resultBigDecimal = resultBigDecimal.divide( avgBigDecimal , intPLen , RoundingMode.DOWN ) ;
						}
						
						// 연산식 스케일 팩터 적용 - 스케일 팩터가 있는 것만 처리
						if( !commUtil.checkObjNull( calculInfoList.get( i ).get( "SC_FCT" ) ) ) {
							// 연산식 스케일 팩터 적용
							scFctBigDecimal = commUtil.getBigdeciNumValue( ( calculInfoList.get( i ).get( "SC_FCT" ) + "" ) ) ;
							resultBigDecimal = resultBigDecimal.multiply( scFctBigDecimal ) ;
						}
						// 연산식 데이터 소수점 처리 - 소수점 길이가 설정 된 것만 처리
						if( boolNullCheckPLen == false ) {
							// 연산식 데이터 소수점 처리
							resultBigDecimal = commUtil.setScaleStringToBigDecimal( ( resultBigDecimal + "" ) , intPLen , 1 ) ;
						}
						
						// 장치내 연산 데이터 QC 적용
						// TODO 연산 필드 QC 처리 : 계측 모드, 연산 모드, calculateInDeviceSimulationMode
						// logger.debug( "::::: 연산 QC 적용 계측 모드, 연산 모드, calculateInDeviceSimulationMode :::::" ) ;
						
						resultQcStr = calculateMeasurementMode( resultQcStr ) ;
						resultQcStr = calculateCalculationMode( resultQcStr ) ;
						resultQcStr = calculateInDeviceSimulationMode( resultQcStr ) ;
						
						// logger.debug( "resultQcStr :: " + resultQcStr ) ;
						// logger.debug( "::::: 연산 QC 적용 계측 모드, 연산 모드, calculateInDeviceSimulationMode end :::::" ) ;
						
						// 연산 결과 값 추가
						// logger.debug( "tempResultBigDecimal :: " + tempResultBigDecimal ) ;
						resultHashMap.put( ( calculInfoList.get( i ).get( "MGP_KEY" ) + "" ) , extndEgovStringUtil.getStringFromNullAndObject( resultBigDecimal ) ) ;
						// TODO 연산 필드 QC 값 추가
						resultHashMap.put( ( calculInfoList.get( i ).get( "MGP_KEY" ) + "_Q" ) , resultQcStr ) ;
					}
					catch( Exception e ) {
						logger.error( e.getMessage( ) , e ) ;
					}
					
				} // for( i = 0 ; i < calculInfoList.size( ) ; i++ )
			}
			
			// logger.debug( "resultHashMap :: " + resultHashMap ) ;
		} // try
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			i = 0 ;
			j = 0 ;
			gatherDataHashMap = null ;
			calculInfoList = null ;
			resultBigDecimal = null ;
			paramBigDecimal = null ;
			scFctBigDecimal = null ;
			avgBigDecimal = null ;
			stdIdxsArr = null ;
			optrArr = null ;
			strFuncOptr = null ;
			intPLen = 0 ;
			resultQcStr = null ;
			strGatherFieldQc = null ;
			logger.info( "getCalculatingGatherData finally:: " ) ;
		} // finally
		
		return resultHashMap ;
		
	}
	
}
