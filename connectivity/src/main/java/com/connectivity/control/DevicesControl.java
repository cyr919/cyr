/**
 * 
 */
package com.connectivity.control ;

import java.util.HashMap ;
import java.util.List ;
import java.util.Map ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;
import org.json.simple.JSONObject ;

import com.connectivity.common.CommonProperties ;
import com.connectivity.common.ConnectivityProperties ;
import com.connectivity.common.RabbitmqCommon ;
import com.connectivity.config.JedisConnection ;
import com.connectivity.config.MongodbConnection ;
import com.connectivity.config.RabbitmqConnection ;
import com.connectivity.control.dao.DevicesControlDao ;
import com.connectivity.utils.JsonUtil ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-06-15
 */
public class DevicesControl
{
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private Logger logger = LogManager.getLogger( this.getClass( ) ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	/**
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-15
	 * @param args
	 */
	public static void main( String[ ] args ) {
		// TODO Auto-generated method stub
		
		ConnectivityProperties connectivityProperties = new ConnectivityProperties( ) ;
		CommonProperties commonProperties = new CommonProperties( ) ;
		JedisConnection jedisConnection = new JedisConnection( ) ;
		MongodbConnection mongodbConnection = new MongodbConnection( ) ;
		RabbitmqConnection rabbitmqConnection = new RabbitmqConnection( ) ;
		
		String testData = "" ;
		
		DevicesControl exe = new DevicesControl( ) ;
		try {
			commonProperties.setProperties( ) ;
			connectivityProperties.setConnectivityProperties( ) ;
			jedisConnection.getJedisPool( ) ;
			mongodbConnection.getMongoClient( ) ;
			
			testData = "{\"APP_ID\":\"APP01\",\"EVHS_ID\":\"event01\",\"PNT_IDX\":\"ctrl001\"}" ;
			exe.devicesControlExe( testData ) ;
		}
		catch( Exception e ) {
			e.printStackTrace( ) ;
		}
		finally {
			testData = null ;
			rabbitmqConnection.closePubConnection( ) ;
		}
		
	}
	
	public Boolean devicesControlExe( String strJsonData ) {
		Boolean resultBool = true ;
		
		JsonUtil jsonUtil = new JsonUtil( ) ;
		JSONObject jsonObject = new JSONObject( ) ;
		DevicesControlDao devicesControlDao = new DevicesControlDao( ) ;
		
		String strPointIndex = "" ;
		String strEventHisId = "" ;
		String strSiteSmlt = "" ;
		String strSiteSmltUsr = "" ;
		
		HashMap< String , Object > deviceCtrlInfo = new HashMap< String , Object >( ) ;
		
		try {
			logger.debug( "devicesControlExe" ) ;
			
			strSiteSmlt = ConnectivityProperties.SITE_SMLT ;
			strSiteSmltUsr = ConnectivityProperties.SITE_SMLT_USR ;
			
			logger.debug( "strJsonData :: " + strJsonData ) ;
			// 수집된 json data 파싱
			jsonObject = jsonUtil.getJSONObjectFromString( ( strJsonData + "" ) ) ;
			
			strPointIndex = ( String ) jsonObject.get( "PNT_IDX" ) ;
			strEventHisId = ( String ) jsonObject.get( "EVHS_ID" ) ;
			
			logger.debug( "strPointIndex :: " + strPointIndex ) ;
			logger.debug( "strEventHisId :: " + strEventHisId ) ;
			
			// 디바이스 제어 명령 수신 이벤트 생성/전송
			
			// 디바이스 제어 명령 상세 정보 조회
			deviceCtrlInfo = devicesControlDao.selectDeviceContolInfoFromPointIndex( strPointIndex ) ;
			logger.debug( "deviceCtrlInfo :: " + deviceCtrlInfo ) ;
			
			// 사이트의 시뮬레이션 모드 조건 확인
			if( "Y".equals( strSiteSmlt ) ) {
				// case 1 : 시뮬레이션 디바이스 제어 이벤트 생성, 전송
				simulationDevicesControl( strEventHisId , deviceCtrlInfo , strSiteSmlt , strSiteSmltUsr ) ;
			}
			else {
				// case 2 : 디바이스 제어 명령을 adaptor에 전달
				transmissionDevicesControl( strEventHisId , deviceCtrlInfo ) ;
			}
			
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strJsonData = null ;
			jsonUtil = null ;
			jsonObject = null ;
			devicesControlDao = null ;
			strPointIndex = null ;
			strEventHisId = null ;
			strSiteSmlt = null ;
			strSiteSmltUsr = null ;
			logger.debug( "devicesControlExe finally" ) ;
		}
		return resultBool ;
	}
	
	public Boolean simulationDevicesControl( String strEventHisId , HashMap< String , Object > deviceCtrlInfo , String strSiteSmlt , String strSiteSmltUsr ) {
		
		Boolean resultBool = true ;
		
		List< HashMap< String , Object > > ctrlList = null ;
		int i = 0 ;
		try {
			logger.debug( "simulationDevicesControl" ) ;
			
			logger.debug( "strSiteSmlt :: " + strSiteSmlt ) ;
			logger.debug( "strSiteSmltUsr :: " + strSiteSmltUsr ) ;
			
			// 시뮬레이션 시작 이벤트 처리
			
			ctrlList = ( List< HashMap< String , Object > > ) deviceCtrlInfo.get( "CTRL_LIST" ) ;
			
			for( i = 0 ; i < ctrlList.size( ) ; i++ ) {
				logger.debug( "ctrlList.get( " + i + " ) :: " + ctrlList.get( i ) ) ;
				
				// 시뮬레이션 이벤트 처리
			}
			// 시뮬레이션 완료 이벤트 처리
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			deviceCtrlInfo = null ;
			strSiteSmlt = null ;
			strSiteSmltUsr = null ;
			ctrlList = null ;
			i = 0 ;
			logger.debug( "simulationDevicesControl finally" ) ;
		}
		
		return resultBool ;
	}
	
	public Boolean transmissionDevicesControl( String strEventHisId , HashMap< String , Object > deviceCtrlInfo ) {
		Boolean resultBool = true ;
		
		HashMap< String , Object > msgMap = new HashMap< String , Object >( ) ;
		RabbitmqConnection rabbitmqConnection = new RabbitmqConnection( ) ;
		RabbitmqCommon rabbitmqCommon = new RabbitmqCommon( ) ;
		JSONObject msgJsonObject = null ;
		Map< String , Object > deviceInfoMap = new HashMap< String , Object >( ) ;
		
		String strStdvId = "" ;
		String strAdptId = "" ;
		try {
			logger.debug( "transmissionDevicesControl" ) ;
			
			strStdvId = deviceCtrlInfo.get( "STDV_ID" ) + "" ;
			deviceInfoMap = ConnectivityProperties.STDV_INF.get( strStdvId ) ;
			
			strAdptId = deviceInfoMap.get( "ADPT_ID" ) + "" ;
			
			msgMap.put( "EVHS_ID" , strEventHisId ) ;
			msgMap.put( "STDV_ID" , strStdvId ) ;
			msgMap.put( "ADPT_ID" , strAdptId ) ;
			msgMap.put( "CTRL_LIST" , deviceCtrlInfo.get( "CTRL_LIST" ) ) ;
			msgMap.put( "CD" , deviceCtrlInfo.get( "CD" ) ) ;
			msgMap.put( "PORT" , deviceCtrlInfo.get( "PORT" ) ) ;
			msgMap.put( "SQNC" , deviceCtrlInfo.get( "SQNC" ) ) ;
			msgMap.put( "TRNS_ITV" , deviceCtrlInfo.get( "TRNS_ITV" ) ) ;
			msgMap.put( "PTC" , deviceCtrlInfo.get( "PTC" ) ) ;
			msgMap.put( "TRNS_UNIT" , deviceCtrlInfo.get( "TRNS_UNIT" ) ) ;
			
			logger.debug( "msgMap :: " + msgMap ) ;
			msgJsonObject = new JSONObject( msgMap ) ;
			logger.debug( "msgJsonObject :: " + msgJsonObject ) ;
			
			rabbitmqCommon.rabbitmqCommonSender( rabbitmqConnection.getDeviceControlConnection( ) , "Control2Adapter" , msgJsonObject ) ;
			
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strEventHisId = null ;
			deviceCtrlInfo = null ;
			msgMap = null ;
			rabbitmqConnection = null ;
			rabbitmqCommon = null ;
			msgJsonObject = null ;
			deviceInfoMap = null ;
			strStdvId = null ;
			strAdptId = null ;
			logger.debug( "transmissionDevicesControl finally" ) ;
		}
		
		return resultBool ;
	}
	
}
