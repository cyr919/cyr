/**
 * 
 */
package com.connectivity.common ;

import java.util.ArrayList ;
import java.util.List ;
import java.util.Map ;
import java.util.concurrent.ConcurrentHashMap ;
import java.util.concurrent.ExecutorService ;
import java.util.concurrent.LinkedBlockingQueue ;
import java.util.concurrent.ThreadPoolExecutor ;
import java.util.concurrent.TimeUnit ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.setting.SettingManage ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-05-19
 */
public final class ConnectivityProperties
{
	private Logger logger = LogManager.getLogger( this.getClass( ) ) ;
	
	// 처리중인 쓰레드 수(계측, 장치간 연산, 이벤트 등)
	public static int PROCESS_THREAD_CNT = 0 ;
	
	public static ExecutorService executorService = null ;
	
	//// 사이트 정보
	// 장치간 연산 주기
	public static String SITE_TRM_CAL = "" ;
	// 시뮬레이션 모드 여부
	public static String SITE_SMLT = "" ;
	// 시뮬레이션 모드 설정 사용자 ID
	public static String SITE_SMLT_USR = "" ;
	
	//// 설치 디바이스 정보
	// 기본 정보(디바이스 아이디, 기본 정보)
	public static Map< String , Map< String , Object > > STDV_INF = new ConcurrentHashMap< String , Map< String , Object > >( ) ;
	// 저장(공통)데이터모델(디바이스 아이디, 저장(공통)데이터모델 리스트)
	public static Map< String , List< Map< String , Object > > > STDV_DT_MDL = new ConcurrentHashMap< String , List< Map< String , Object > > >( ) ;
	// 저장(공통)데이터모델 list -> map(디바이스 아이디, < mgp_key, 저장(공통)데이터모델 맵 >)
	// public static HashMap< String , HashMap< String , HashMap< String , Object > > > STDV_DT_MDL_MAP = new HashMap< String , HashMap< String , HashMap< String , Object > > >( ) ;
	// 장치내 연산정보(디바이스 아이디, 장치내 연산정보)
	public static Map< String , List< Map< String , Object > > > STDV_CAL_INF = new ConcurrentHashMap< String , List< Map< String , Object > > >( ) ;
	// 저장 주기 체크 설정(디바이스 아이디 , 저장주기 체크 횟수 )
	public static Map< String , Integer > DATA_SAVE_CYCLE_CHECK = new ConcurrentHashMap< String , Integer >( ) ;
	
	//// 퀄리티 코드 정보
	// 레코드 퀄리티 코드 정보(메소드, 레코드 퀄리티 코드 정보)
	public static Map< String , Map< String , Object > > RECORD_QC_INF = new ConcurrentHashMap< String , Map< String , Object > >( ) ;
	// 필드 퀄리티 코드 정보(메소드, 레코드 퀄리티 코드 정보)
	public static Map< String , Map< String , Object > > FIELD_QC_INF = new ConcurrentHashMap< String , Map< String , Object > >( ) ;
	// 레코드 퀄리티 코드 위치 정보(메소드, 퀄리티 코드 위치 정보)
	public static Map< String , Map< String , Integer > > RECORD_QC_IDX = new ConcurrentHashMap< String , Map< String , Integer > >( ) ;
	// 필드 퀄리티 코드 위치 정보(메소드, 퀄리티 코드 위치 정보)
	public static Map< String , Map< String , Integer > > FIELD_QC_IDX = new ConcurrentHashMap< String , Map< String , Integer > >( ) ;
	// Old Data QC 체크 저장 정보 - 최근 계측 시간(디바이스 아이디 , 가장 최근 계측 시간(lastGatherDtM))
	public static Map< String , String > LAST_GATHER_DTM = new ConcurrentHashMap< String , String >( ) ;
	// Old Data QC 체크 저장 정보 - old 데이터 중복 횟수(디바이스 아이디 , old data 중복 횟수(OldDataDuplCnt))
	public static Map< String , Integer > OLD_DATA_QC_CHECK = new ConcurrentHashMap< String , Integer >( ) ;
	
	//// 장치간 연산정보(장치간 연산정보 리스트)
	public static List< Map< String , Object > > BTWN_DV_CAL_INFO = new ArrayList< Map< String , Object > >( ) ;
	
	//// 맵퍼 정보
	// APPIO - 계측 데이터(+ 장치내 연산)(디바이스 아이디, <MGP_KEY, APPIO point index>)
	public static Map< String , List< Map< String , Object > > > APPIO_MAPPER_SDHS = new ConcurrentHashMap< String , List< Map< String , Object > > >( ) ;
	// APPIO - 장치간 연산 데이터(MGP_KEY, APPIO point index)
	public static List< Map< String , Object > > APPIO_MAPPER_CRHS = new ArrayList< Map< String , Object > >( ) ;
	// public static HashMap< String , String > APPIO_MAPPER_CRHS = new HashMap< String , String >( ) ;
	
	public void threadPoolExecute( ) {
		try {
			// ExecutorService 인터페이스 구현객체 Executors 정적메서드를 통해 코어 스레드와 최대 스레드 개수가 50인 스레드 풀 생성
			// ConnectivityProperties.executorService = Executors.newFixedThreadPool( 50 ) ;
			// 직접 ThreadPoolExecutor 객체 생성하기
			ConnectivityProperties.executorService = new ThreadPoolExecutor( 10 , 50 , 10L , TimeUnit.SECONDS , new LinkedBlockingQueue< Runnable >( ) ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
	}
	
	public void threadPoolShutdown( ) {
		try {
			// 남아있는 작업을 마무리하고 스레드풀 종료
			ConnectivityProperties.executorService.shutdown( ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
	}
	
	public void threadPoolShutdownNow( ) {
		try {
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
	}
	
	public Boolean setConnectivityProperties( ) {
		Boolean resultBool = true ;
		
		logger.debug( "STDV_DT_MDL :: " + STDV_DT_MDL ) ;
		logger.debug( "STDV_CAL_INF :: " + STDV_CAL_INF ) ;
		logger.debug( "STDV_INF :: " + STDV_INF ) ;
		
		SettingManage settingManage = new SettingManage( ) ;
		
		try {
			resultBool = false ;
			
			if( settingManage.siteInfoSetting( ) ) {
				if( settingManage.devicePropertiesSetting( ) ) {
					if( settingManage.qualityCodeInfoSetting( ) ) {
						if( settingManage.betweenDevicesCalculateInfoSetting( ) ) {
							if( settingManage.appioMappingInfoDeviceDataSetting( ) ) {
								if( settingManage.appioMappingInfoBetweenDevicesCalculatingDataSetting( ) ) {
									if( settingManage.deviceCheckPropertiesSetting( ) ) {
										
										resultBool = true ;
									}
								}
							}
						}
					}
				}
			}
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			settingManage = null ;
			logger.debug( "resultBool :: " + resultBool ) ;
		}
		
		logger.debug( "STDV_DT_MDL :: " + STDV_DT_MDL ) ;
		logger.debug( "STDV_CAL_INF :: " + STDV_CAL_INF ) ;
		logger.debug( "STDV_INF :: " + STDV_INF ) ;
		
		return resultBool ;
	}
	
	public Boolean setConnectivityPropertiesSimulModeOn( ) {
		Boolean resultBool = true ;
		
		SettingManage settingManage = new SettingManage( ) ;
		
		try {
			logger.debug( "setConnectivityPropertiesSimulModeOn :: " ) ;
			logger.debug( "SITE_SMLT :: " + SITE_SMLT ) ;
			
			resultBool = false ;
			
			if( settingManage.siteInfoSetting( ) ) {
				if( settingManage.devicePropertiesSetting( ) ) {
					
					resultBool = true ;
				}
			}
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			settingManage = null ;
			logger.debug( "resultBool :: " + resultBool ) ;
			logger.debug( "SITE_SMLT :: " + SITE_SMLT ) ;
			logger.debug( "setConnectivityPropertiesSimulModeOn finally :: " ) ;
			
		}
		
		return resultBool ;
	}
	
	public Boolean setConnectivityPropertiesSimulModeOff( ) {
		Boolean resultBool = true ;
		
		SettingManage settingManage = new SettingManage( ) ;
		
		try {
			logger.debug( "setConnectivityPropertiesSimulModeOff :: " ) ;
			logger.debug( "SITE_SMLT :: " + SITE_SMLT ) ;
			
			resultBool = false ;
			
			// if( settingManage.siteInfoSetting( ) ) {
			// resultBool = true ;
			// }
			
			synchronized( ConnectivityProperties.SITE_SMLT ) {
				ConnectivityProperties.SITE_SMLT = "N" ;
			}
			
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			settingManage = null ;
			logger.debug( "resultBool :: " + resultBool ) ;
			logger.debug( "SITE_SMLT :: " + SITE_SMLT ) ;
			logger.debug( "setConnectivityPropertiesSimulModeOff finally:: " ) ;
		}
		
		return resultBool ;
	}
	
	public Boolean setConnectivityPropertiesDeviceSimulDataReset( List< ? > deviceIdList ) {
		Boolean resultBool = true ;
		
		SettingManage settingManage = new SettingManage( ) ;
		
		try {
			logger.debug( "setConnectivityPropertiesSimulModeOff :: " ) ;
			logger.debug( "SITE_SMLT :: " + SITE_SMLT ) ;
			
			resultBool = false ;
			
			if( settingManage.devicePropertiesSetting( deviceIdList ) ) {
				resultBool = true ;
			}
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			settingManage = null ;
			deviceIdList = null ;
			logger.debug( "resultBool :: " + resultBool ) ;
			logger.debug( "SITE_SMLT :: " + SITE_SMLT ) ;
			logger.debug( "setConnectivityPropertiesSimulModeOff finally:: " ) ;
		}
		
		return resultBool ;
	}
	
	public synchronized void addOneProcessThreadCnt( ) {
		
		try {
			ConnectivityProperties.PROCESS_THREAD_CNT++ ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		return ;
	}
	
	public synchronized void subtractOneProcessThreadCnt( ) {
		try {
			ConnectivityProperties.PROCESS_THREAD_CNT-- ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		return ;
	}
	
	public synchronized Integer getProcessThreadCnt( ) {
		Integer resultInt = 0 ;
		
		try {
			resultInt = ConnectivityProperties.PROCESS_THREAD_CNT ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			
		}
		
		return resultInt ;
	}
	
	/**
	 * <pre>
	 * deviceId 의 old data Qc check 횟수를 리턴한다.
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-23
	 * @param deviceId
	 * @return
	 */
	public Integer getStdvOldDataQcCheckCnt( String deviceId ) {
		
		Integer resultInt = 0 ;
		try {
			resultInt = OLD_DATA_QC_CHECK.get( deviceId ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			deviceId = null ;
		}
		
		return resultInt ;
	}
	
	/**
	 * <pre>
	 * deviceId 의 old data Qc check 횟수에 1을 더한 후 그 결과값을 리턴한다.
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-23
	 * @param deviceId
	 * @return
	 */
	public Integer addOneAndGetStdvOldDataQcCheckCnt( String strDviceId ) {
		Integer resultInt = 0 ;
		
		try {
			OLD_DATA_QC_CHECK.put( strDviceId , ( OLD_DATA_QC_CHECK.get( strDviceId ) + 1 ) ) ;
			resultInt = OLD_DATA_QC_CHECK.get( strDviceId ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strDviceId = null ;
		}
		
		return resultInt ;
	}
	
	public Integer setZeroAndGetStdvOldDataQcCheckCnt( String strDviceId ) {
		Integer resultInt = 0 ;
		
		try {
			if( 0 != OLD_DATA_QC_CHECK.get( strDviceId ) ) {
				OLD_DATA_QC_CHECK.put( strDviceId , 0 ) ;
			}
			resultInt = OLD_DATA_QC_CHECK.get( strDviceId ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strDviceId = null ;
		}
		
		return resultInt ;
	}
	
	/**
	 * <pre>
	 * 최신 계측 데이터 계측시간과 바로 전 계측 시간을 비교한 후 
	 * 같을 경우 old data qc 체크 수에 1을 더하고 
	 * 다르면  old data qc 체크 수를 0으로 변경한 후 최근 계측 시간 정보에 최신 계측 시간을 put 한다.
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-24
	 * @param strGatherDmt 최신 계측 데이터 계측 시간
	 * @param strDviceId 설치디바이스 아이디
	 * @return
	 */
	public Integer CompareGatherDtmBeforeAndAfter( String strGatherDmt , String strDviceId ) {
		Integer resultInt = 0 ;
		
		String strLastGatherDmt = "" ;
		
		try {
			
			// logger.debug( "strGatherDmt :: " + strGatherDmt ) ;
			// logger.debug( "deviceId :: " + strDviceId ) ;
			
			strLastGatherDmt = LAST_GATHER_DTM.get( strDviceId ) ;
			// logger.debug( "strLastGatherDmt :: " + strLastGatherDmt ) ;
			
			if( strGatherDmt.equals( strLastGatherDmt ) ) {
				// 현재 계측 시간과 이전 계측 시간이 같을 경우
				resultInt = addOneAndGetStdvOldDataQcCheckCnt( strDviceId ) ;
			}
			else {
				// 현재 계측 시간과 이전 계측 시간이 다를
				resultInt = setZeroAndGetStdvOldDataQcCheckCnt( strDviceId ) ;
				LAST_GATHER_DTM.put( strDviceId , strGatherDmt ) ;
			}
			
			// logger.debug( "LAST_GATHER_DTM :: " + LAST_GATHER_DTM ) ;
			// logger.debug( "OLD_DATA_QC_CHECK :: " + OLD_DATA_QC_CHECK ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strGatherDmt = null ;
			strDviceId = null ;
			strLastGatherDmt = null ;
		}
		
		return resultInt ;
	}
	
	public Integer addOneAndGetStdvDataSaveCycleCheckCnt( String strDviceId ) {
		Integer resultInt = 0 ;
		
		try {
			DATA_SAVE_CYCLE_CHECK.put( strDviceId , ( DATA_SAVE_CYCLE_CHECK.get( strDviceId ) + 1 ) ) ;
			resultInt = DATA_SAVE_CYCLE_CHECK.get( strDviceId ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strDviceId = null ;
		}
		
		return resultInt ;
	}
	
	public void setZeroStdvDataSaveCycleCheckCnt( String strDviceId ) {
		
		try {
			DATA_SAVE_CYCLE_CHECK.put( strDviceId , 0 ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strDviceId = null ;
		}
		
		return ;
	}
	
}
