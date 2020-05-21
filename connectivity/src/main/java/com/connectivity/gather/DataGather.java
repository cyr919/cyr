package com.connectivity.gather ;

import java.math.BigDecimal ;
import java.util.ArrayList ;
import java.util.Arrays ;
import java.util.HashMap ;
import java.util.List ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;
import org.json.simple.JSONObject ;

import com.connectivity.common.ConnectivityProperties ;
import com.connectivity.utils.CommUtil ;
import com.connectivity.utils.ExtndEgovStringUtil ;
import com.connectivity.utils.JsonUtil ;

public class DataGather
{
	
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private Logger logger = LogManager.getLogger( this.getClass( ) ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	public static void main( String[ ] args ) {
		ConnectivityProperties connectivityProperties = new ConnectivityProperties( ) ;
		connectivityProperties.setStdv( ) ;
		
		DataGather exe = new DataGather( ) ;
		String testData = "" ;
		
		// testData = "{\"STDV_ID\":\"stdv0004\",\"DATA\":{\"MGP040\":16,\"MGP041\":10,\"MGP044\":105,\"MGP023\":70,\"MGP045\":34,\"MGP042\":47,\"MGP043\":38,\"MGP026\":19,\"MGP048\":48,\"MGP027\":17,\"MGP049\":67,\"MGP024\":69,\"MGP046\":45,\"MGP025\":80,\"MGP047\":28,\"MGP039\":90,\"MGP051\":24,\"MGP030\":82,\"MGP052\":73,\"MGP050\":42,\"MGP033\":88,\"MGP055\":28,\"MGP034\":66,\"MGP056\":57,\"MGP031\":86,\"MGP053\":87,\"MGP032\":25,\"MGP054\":38,\"MGP037\":22,\"MGP038\":70,\"MGP035\":23,\"MGP057\":109,\"MGP036\":99,\"MGP028\":88,\"MGP029\":52},\"DMT\":\"2020-05-21 09:12:57.396\"}" ;
		testData = "{\"STDV_ID\":\"stdv0002\",\"DATA\":{\"MGP022\":85,\"MGP021\":50},\"DMT\":\"2020-05-21 13:00:34.747\"}" ;
		
		exe.dataGathering( testData ) ;
		
	}
	
	public Boolean dataGathering( String strJsonData ) {
		Boolean resultBool = true ;
		
		String strDviceId = "" ;
		String strDmt = "" ;
		
		JsonUtil jsonUtil = new JsonUtil( ) ;
		JSONObject jsonObject = new JSONObject( ) ;
		JSONObject subDataJSONObject = new JSONObject( ) ;
		JSONObject resultDataJSONObject = new JSONObject( ) ;
		// TODO 일단 hashmap으로 해보고 object 변환시 좀 오래 걸리는거같으면 바꾸기
		HashMap< String , Object > resultDataMap = new HashMap< String , Object >( ) ;
		
		HashMap< String , HashMap< String , Object > > stdvDtMdlMap = new HashMap< String , HashMap< String , Object > >( ) ;
		ArrayList< HashMap< String , Object > > stdvCalInf = new ArrayList< HashMap< String , Object > >( ) ;
		
		BigDecimal tempBigDecimal = new BigDecimal( "0" ) ;
		
		ExtndEgovStringUtil extndEgovStringUtil = new ExtndEgovStringUtil( ) ;
		try {
			logger.debug( "dataGathering :: " ) ;
			logger.debug( "수신된 데이터 :: " + strJsonData ) ;
			
			// 수집된 json data 파싱
			jsonObject = jsonUtil.getJSONObjectFromString( ( strJsonData + "" ) ) ;
			
			// 디바이스 아이디와 계측 시간 추출
			strDviceId = ( String ) jsonObject.get( "STDV_ID" ) ;
			strDmt = ( String ) jsonObject.get( "DMT" ) ;
			subDataJSONObject = ( JSONObject ) jsonObject.get( "DATA" ) ;
			
			logger.debug( "strDviceId :: " + strDviceId ) ;
			logger.debug( "strDmt :: " + strDmt ) ;
			logger.debug( "subDataJSONObject :: " + subDataJSONObject ) ;
			
			// 데이터 맵 정보 map 가지고 오기
			stdvDtMdlMap = ConnectivityProperties.STDV_DT_MDL_MAP.get( strDviceId ) ;
			
			logger.debug( "stdvDtMdlMap :: " + stdvDtMdlMap ) ;
			logger.debug( "ConnectivityProperties.STDV_DT_MDL.get( " + strDviceId + " ) :: " + ConnectivityProperties.STDV_DT_MDL.get( strDviceId ) ) ;
			
			// 수집된 계측 데이터 처리
			for( Object key : subDataJSONObject.keySet( ) ) {
				logger.debug( "key :: " + key ) ;
				logger.debug( "subDataJSONObject.get( key ) :: " + subDataJSONObject.get( key ) ) ;
				logger.debug( "stdvDtMdlMap.get( key ) :: " + stdvDtMdlMap.get( key ) ) ;
				
				// 시뮬레이션 값 치환 또는 스케일 팩터 적용
				
				if( "Y".equals( stdvDtMdlMap.get( key ).get( "SMLT" ) ) ) {
					// 시뮬레이션 값 적용
					resultDataMap.put( ( key + "" ) , stdvDtMdlMap.get( key ).get( "SMLT_V" ) ) ;
				}
				else {
					// 스케일 팩터 값 적용
					tempBigDecimal = applyScaleFactor( subDataJSONObject.get( key ) , stdvDtMdlMap.get( key ).get( "SC_FCT" ) ) ;
					resultDataMap.put( ( key + "" ) , extndEgovStringUtil.getStringFromNullAndObject( tempBigDecimal ) ) ;
				}
				
				// 계측 필드 데이터 QC 적용
				
			}
			logger.debug( "resultDataMap :: " + resultDataMap ) ;
			
			// 계측 레코드 데이터 QC 적용
			
			// 연산 정보 가지고 오기
			stdvCalInf = ConnectivityProperties.STDV_CAL_INF.get( strDviceId ) ;
			
			// 장치내 연산 데이터 생성
			
			
			
			// 장치내 연산 데이터 QC 적용
			
			// redis 저장
			
			// mongodb 저장
			
			// 이벤트 처리(thread 생성 후 거기서 처리하는 방안으로)
			
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			logger.debug( "finally :: " ) ;
		}
		
		return resultBool ;
	}
	
	public BigDecimal applyScaleFactor( Object gatherData , Object scaleFactor ) {
		
		BigDecimal resultBigDecimal = new BigDecimal( "0" ) ;
		
		BigDecimal scaleFactorBigDecimal = new BigDecimal( "0" ) ;
		CommUtil commUtil = new CommUtil( ) ;
		
		try {
			
			resultBigDecimal = new BigDecimal( ( gatherData + "" ) ) ;
			logger.debug( "resultBigDecimal :: " + resultBigDecimal ) ;
			logger.debug( "scaleFactor :: " + scaleFactor ) ;
			logger.debug( "!commUtil.checkObjNull( ( scaleFactor ) :: " + !commUtil.checkObjNull( ( scaleFactor ) ) ) ;
			
			if( !commUtil.checkObjNull( ( scaleFactor ) ) ) {
				
				scaleFactorBigDecimal = new BigDecimal( scaleFactor + "" ) ;
				logger.debug( "scaleFactorBigDecimal :: " + scaleFactorBigDecimal ) ;
				
				resultBigDecimal = resultBigDecimal.multiply( scaleFactorBigDecimal ) ;
			}
			
			logger.debug( "resultBigDecimal :: " + resultBigDecimal ) ;
		}
		finally {
			gatherData = null ;
			scaleFactor = null ;
			scaleFactorBigDecimal = null ;
		}
		
		return resultBigDecimal ;
	}
	
	public HashMap< String , Object > getCalculatingGatherData( HashMap< String , Object > gatherDataHashMap , List< HashMap< String , Object > > calculInfoList ) {
		
		HashMap< String , Object > resultHashMap = new HashMap< String , Object >( ) ;
		
		BigDecimal tempResultBigDecimal = new BigDecimal( "0" ) ;
		BigDecimal tempBigDecimal = new BigDecimal( "0" ) ;
		
		String[ ] stdIdxsArr = new String[ 0 ] ;
		String[ ] optrArr = new String[ 0 ] ;
		
		int i = 0 ;
		int j = 0 ;
		
		try {
			
			for( i = 0 ; i < calculInfoList.size( ) ; i++ ) {
				logger.debug( "calculInfoList.get( i ).get( \"STD_IDX\" ) :: " + calculInfoList.get( i ).get( "STD_IDX" ) ) ;
				logger.debug( "calculInfoList.get( i ).get( \"STD_IDXS\" ) :: " + calculInfoList.get( i ).get( "STD_IDXS" ) ) ;
				logger.debug( "calculInfoList.get( i ).get( \"OPTR\" ) :: " + calculInfoList.get( i ).get( "OPTR" ) ) ;
				
				stdIdxsArr = ( calculInfoList.get( i ).get( "STD_IDXS" ) + "" ).split( "," ) ;
				logger.debug( "stdIdxsArr :: " + Arrays.toString( stdIdxsArr ) ) ;
				
				for( j = 0 ; j < stdIdxsArr.length ; j++ ) {
					
					tempBigDecimal = new BigDecimal( ( gatherDataHashMap.get( stdIdxsArr[ j ] ) + "" ) ) ;
					logger.debug( "tempBigDecimal :: " + j + " :: " + tempBigDecimal ) ;
					
					if( j == 0 ) {
						
						tempResultBigDecimal = tempBigDecimal ;
					}
					else {
						if( "*".equals( calculInfoList.get( i ).get( "OPTR" ) ) ) {
							
							tempResultBigDecimal = tempResultBigDecimal.multiply( tempBigDecimal ) ;
						}
						logger.debug( "tempResultBigDecimal :: " + j + " :: " + tempResultBigDecimal ) ;
					}
					
				}
				
				logger.debug( "tempResultBigDecimal :: " + tempResultBigDecimal ) ;
				resultHashMap.put( ( calculInfoList.get( i ).get( "STD_IDX" ) + "" ) , tempResultBigDecimal ) ;
			}
			
		}
		finally {
			i = 0 ;
			j = 0 ;
			gatherDataHashMap = null ;
			calculInfoList = null ;
			
			tempResultBigDecimal = null ;
			tempBigDecimal = null ;
			stdIdxsArr = null ;
		}
		
		return resultHashMap ;
		
	}
	
}
