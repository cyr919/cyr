/**
 * 
 */
package com.connectivity.manage ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;
import org.json.simple.JSONObject ;

import com.connectivity.ConnectivityMainRun ;
import com.connectivity.utils.JsonUtil ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-06-24
 */
public class ModuleCommand
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
	 * @date 2020-06-24
	 * @param args
	 */
	public static void main( String[ ] args ) {
		// TODO Auto-generated method stub
		
	}
	
	public Boolean exeModuleCommand( String strJsonData ) {
		Boolean resultBool = true ;
		
		JsonUtil jsonUtil = new JsonUtil( ) ;
		JSONObject jsonObject = new JSONObject( ) ;
		
		String strEventHisId = "" ;
		String strModuleId = "" ;
		String strCommand = "" ;
		
		ConnectivityMainRun exe = new ConnectivityMainRun( ) ;
		
		try {
			logger.debug( "exeModuleCommand" ) ;
			
			logger.debug( "strJsonData :: " + strJsonData ) ;
			// 수집된 json data 파싱
			jsonObject = jsonUtil.getJSONObjectFromString( ( strJsonData + "" ) ) ;
			
			// 모듈 아이디 확인
			strModuleId = jsonObject.get( "MFIF_ID" ) + "" ;
			logger.debug( "strModuleId :: " + strModuleId ) ;
			
			// 모듈 아이디가 connectivity 인것만 처리한다. 그 외는 무시한다.
			if( strModuleId.equals( "connectivity" ) ) {
				// 제어 명령 확인
				strCommand = jsonObject.get( "COMMAND" ) + "" ;
				logger.debug( "strCommand :: " + strCommand ) ;
				
				// 이벤트 아이디 확인
				strEventHisId = jsonObject.get( "EVHS_ID" ) + "" ;
				logger.debug( "strEventHisId :: " + strEventHisId ) ;
				
				// 제어 명령 처리
				if( "S01".equals( strCommand ) ) {
					// 시뮬레이션 모드 On
					logger.info( "::::::::::::::::::connectivity connectivitySiteSimulModeOn ::::::::::::::::" ) ;
					logger.info( "::::::::::::::::::connectivity connectivitySiteSimulModeOn ::::::::::::::::" ) ;
					logger.info( "::::::::::::::::::connectivity connectivitySiteSimulModeOn ::::::::::::::::" ) ;
					exe.connectivitySiteSimulModeOn( strEventHisId ) ;
					
				}
				else if( "S02".equals( strCommand ) ) {
					// 시뮬레이션 모드 Off
					logger.info( "::::::::::::::::::connectivity connectivitySiteSimulModeOff ::::::::::::::::" ) ;
					logger.info( "::::::::::::::::::connectivity connectivitySiteSimulModeOff ::::::::::::::::" ) ;
					logger.info( "::::::::::::::::::connectivity connectivitySiteSimulModeOff ::::::::::::::::" ) ;
					exe.connectivitySiteSimulModeOff( strEventHisId ) ;
					
				}
				else if( "S03".equals( strCommand ) ) {
					// 시뮬레이션 모드 리셋
					logger.info( "::::::::::::::::::connectivity connectivityDeviceSimulDataReset ::::::::::::::::" ) ;
					logger.info( "::::::::::::::::::connectivity connectivityDeviceSimulDataReset ::::::::::::::::" ) ;
					logger.info( "::::::::::::::::::connectivity connectivityDeviceSimulDataReset ::::::::::::::::" ) ;
					
					// 디바이스 별로 올 때
					// exe.connectivityDeviceSimulDataReset( strEventHisId , List< ? > deviceIdList) ;
					
					// 디바이스 정보가 all 일때 - 실질적으로 On 해야함.
					exe.connectivitySiteSimulModeOn( strEventHisId ) ;
					
				}
				else if( "M03".equals( strCommand ) ) {
					// reset
					
					logger.info( "::::::::::::::::::connectivity Reset ::::::::::::::::" ) ;
					logger.info( "::::::::::::::::::connectivity Reset ::::::::::::::::" ) ;
					logger.info( "::::::::::::::::::connectivity Reset ::::::::::::::::" ) ;
					
					exe.connectivityReset( strEventHisId ) ;
				}
				
			} // if( strModuleId.equals( "connectivity" ) )
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strJsonData = null ;
		}
		
		return resultBool ;
	}
	
	public Boolean exeModuleCommandStop( String strJsonData ) {
		Boolean resultBool = true ;
		
		JsonUtil jsonUtil = new JsonUtil( ) ;
		JSONObject jsonObject = new JSONObject( ) ;
		
		String strEventHisId = "" ;
		String strModuleId = "" ;
		String strCommand = "" ;
		
		ConnectivityMainRun exe = new ConnectivityMainRun( ) ;
		
		try {
			logger.debug( "exeModuleCommand" ) ;
			
			logger.debug( "strJsonData :: " + strJsonData ) ;
			// 수집된 json data 파싱
			jsonObject = jsonUtil.getJSONObjectFromString( ( strJsonData + "" ) ) ;
			
			// 모듈 아이디 확인
			strModuleId = jsonObject.get( "MFIF_ID" ) + "" ;
			logger.debug( "strModuleId :: " + strModuleId ) ;
			
			// 모듈 아이디가 connectivity 인것만 처리한다. 그 외는 무시한다.
			if( strModuleId.equals( "connectivity" ) ) {
				// 제어 명령 확인
				strCommand = jsonObject.get( "COMMAND" ) + "" ;
				logger.debug( "strCommand :: " + strCommand ) ;
				
				// 이벤트 아이디 확인
				strEventHisId = jsonObject.get( "EVHS_ID" ) + "" ;
				logger.debug( "strEventHisId :: " + strEventHisId ) ;
				
				// 제어 명령 처리
				if( "M02".equals( strCommand ) ) {
					// 정지
					
					logger.info( "::::::::::::::::::connectivity Stop ::::::::::::::::" ) ;
					logger.info( "::::::::::::::::::connectivity Stop ::::::::::::::::" ) ;
					logger.info( "::::::::::::::::::connectivity Stop ::::::::::::::::" ) ;
					
					exe.connectivityStop( strEventHisId ) ;
				}
				
			} // if( strModuleId.equals( "connectivity" ) )
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strJsonData = null ;
		}
		
		return resultBool ;
	}
	
}
