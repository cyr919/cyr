/**
 * 
 */
package com.connectivity.setting ;

import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.List ;
import java.util.Map ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.common.CommonProperties ;
import com.connectivity.common.ConnectivityProperties ;
import com.connectivity.setting.dao.SettingManageDao ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-05-19
 */
public class SettingManage
{
	private Logger logger = LogManager.getLogger( this.getClass( ) ) ;
	
	/**
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-05-19
	 * @param args
	 */
	public static void main( String[ ] args ) {
		
		CommonProperties commonProperties = new CommonProperties( ) ;
		try {
			commonProperties.setProperties( ) ;
		}
		catch( Exception e ) {
			e.printStackTrace( ) ;
		}
		
		SettingManage exe = new SettingManage( ) ;
		
		exe.devicePropertiesSetting( ) ;
		// exe.qualityCodeInfoSetting( ) ;
		// exe.betweenDevicesCalculateInfoSetting( ) ;
		// exe.siteInfoSetting( ) ;
		// exe.appioMappingInfoBetweenDevicesCalculatingDataSetting( ) ;
		// exe.appioMappingInfoDeviceDataSetting( ) ;
		// exe.deviceCheckPropertiesSetting( ) ;
		
	}
	
	/**
	 * <pre>
	 * 디바이스 정보 셋팅
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-18
	 * @return
	 */
	public Boolean devicePropertiesSetting( ) {
		
		Boolean resultBool = true ;
		
		SettingManageDao settingManageDao = new SettingManageDao( ) ;
		
		List< HashMap > resultList = new ArrayList< HashMap >( ) ;
		HashMap< String , Object > tempMap = new HashMap< String , Object >( ) ;
		List< Map< String , Object > > tempList = new ArrayList< Map< String , Object > >( ) ;
		Map< String , Map< String , Object > > deviceInfo = new HashMap< String , Map< String , Object > >( ) ;
		Map< String , List< Map< String , Object > > > deviceDataModel = new HashMap< String , List< Map< String , Object > > >( ) ;
		Map< String , List< Map< String , Object > > > deviceCalInfo = new HashMap< String , List< Map< String , Object > > >( ) ;
		
		// HashMap< String , HashMap< String , Object > > tempMapFromList = new HashMap< String , HashMap< String , Object > >( ) ;
		
		// HashMap< String , HashMap< String , HashMap< String , Object > > > deviceDataModelMap = new HashMap< String , HashMap< String , HashMap< String , Object > > >( ) ;
		
		String deviceId = "" ;
		int i = 0 ;
		try {
			logger.info( "devicePropertiesSetting" ) ;
			
			// 데이터 조회
			resultList = settingManageDao.selectInstallDeviceList( ) ;
			logger.info( "resultList :: " + resultList ) ;
			
			// 조회한 데이터를 변수에 저장한다.
			// 기본 정보
			// 저장(공통) 데이터 모델
			// 장치내 연산정보
			
			if( resultList != null ) {
				
				for( i = 0 ; i < resultList.size( ) ; i++ ) {
					
					logger.info( "resultList.get( " + i + " ) :: " + resultList.get( i ) ) ;
					logger.info( "resultList.get( " + i + " ).get( \"_id\" ) :: " + resultList.get( i ).get( "_id" ) ) ;
					logger.info( "resultList.get( " + i + " ).get( \"DT_MDL\" ) :: " + resultList.get( i ).get( "DT_MDL" ) ) ;
					logger.info( "resultList.get( " + i + " ).get( \"CAL_INF\" ) :: " + resultList.get( i ).get( "CAL_INF" ) ) ;
					
					deviceId = resultList.get( i ).get( "_id" ) + "" ;
					
					// 디바이스 기본정보 처리
					tempMap = new HashMap< String , Object >( );
					tempMap.put( "_id" , deviceId ) ;
					tempMap.put( "TP" , resultList.get( i ).get( "TP" ) ) ;
					tempMap.put( "SMLT" , resultList.get( i ).get( "SMLT" ) ) ;
					tempMap.put( "SKIP" , resultList.get( i ).get( "SKIP" ) ) ;
					tempMap.put( "SCR" , resultList.get( i ).get( "SCR" ) ) ;
					tempMap.put( "DVIF_ID" , resultList.get( i ).get( "DVIF_ID" ) ) ;
					tempMap.put( "ADPT_ID" , resultList.get( i ).get( "ADPT_ID" ) ) ;
					
					deviceInfo.put( deviceId , tempMap ) ;
					
					// 데이터 모델 처리
					// TODO unit converter facter 추가 기능 개발
					tempList = new ArrayList< Map< String , Object > >( ) ;
					tempList.addAll( ( ArrayList< HashMap< String , Object > > ) resultList.get( i ).get( "DT_MDL" ) ) ;
					
					deviceDataModel.put( deviceId , tempList ) ;
					
					// // 데이터 모델 list -> map
					// tempMapFromList = commUtil.getHashMapFromListHashMap( tempList , "MGP_KEY" ) ;
					// deviceDataModelMap.put( deviceId , tempMapFromList ) ;
					
					// 장치 내 연산 처리
					tempList = new ArrayList< Map< String , Object > >( ) ;
					tempList.addAll( ( ArrayList< HashMap< String , Object > > ) resultList.get( i ).get( "CAL_INF" ) ) ;
					
					deviceCalInfo.put( deviceId , tempList ) ;
					
				}
				logger.info( "deviceDataModel :: " + deviceDataModel ) ;
				// logger.info( "deviceDataModelMap :: " + deviceDataModelMap ) ;
				logger.info( "deviceCalInfo :: " + deviceCalInfo ) ;
				logger.info( "deviceInfo :: " + deviceInfo ) ;
				
				// static 변수에 데이터 넣기
				synchronized( ConnectivityProperties.STDV_DT_MDL ) {
					ConnectivityProperties.STDV_DT_MDL = deviceDataModel ;
				}
				synchronized( ConnectivityProperties.STDV_CAL_INF ) {
					ConnectivityProperties.STDV_CAL_INF = deviceCalInfo ;
				}
				synchronized( ConnectivityProperties.STDV_INF ) {
					ConnectivityProperties.STDV_INF = deviceInfo ;
				}
				// ConnectivityProperties.STDV_DT_MDL_MAP = deviceDataModelMap ;
			}
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			settingManageDao = null ;
			resultList = null ;
			tempMap = null ;
			tempList = null ;
			deviceInfo = null ;
			deviceDataModel = null ;
			deviceCalInfo = null ;
			deviceId = null ;
			
			i = 0 ;
			logger.info( "devicePropertiesSetting finally" ) ;
		}
		
		return resultBool ;
	}
	
	public Boolean qualityCodeInfoSetting( ) {
		
		Boolean resultBool = true ;
		logger.info( "qualityCodeInfoSetting" ) ;
		
		SettingManageDao settingManageDao = new SettingManageDao( ) ;
		
		ArrayList< HashMap > resultList = new ArrayList< HashMap >( ) ;
		Map< String , Map< String , Object > > fieldQcInf = new HashMap< String , Map< String , Object > >( ) ;
		Map< String , Map< String , Object > > recordQcInf = new HashMap< String , Map< String , Object > >( ) ;
		Map< String , Map< String , Integer > > fieldQcIdx = new HashMap< String , Map< String , Integer > >( ) ;
		Map< String , Map< String , Integer > > recordQcIdx = new HashMap< String , Map< String , Integer > >( ) ;
		
		int i = 0 ;
		
		HashMap< String , Integer > tempMap = new HashMap< String , Integer >( ) ;
		int startInt = 0 ;
		int endInt = 0 ;
		
		try {
			resultList = settingManageDao.selectQualityCodeList( ) ;
			if( resultList != null ) {
				
				for( i = 0 ; i < resultList.size( ) ; i++ ) {
					logger.debug( "resultList.get( " + i + " ) :: " + resultList.get( i ) ) ;
					
					startInt = Integer.parseInt( resultList.get( i ).get( "ADR" ) + "" ) ;
					endInt = Integer.parseInt( resultList.get( i ).get( "LEN" ) + "" ) + startInt ;
					
					tempMap = new HashMap< String , Integer >( ) ;
					tempMap.put( "startInt" , startInt ) ;
					tempMap.put( "endInt" , endInt ) ;
					
					if( "QTC01".equals( resultList.get( i ).get( "TP" ) + "" ) ) {
						// 레코드 QC
						recordQcInf.put( ( resultList.get( i ).get( "MTHD" ) + "" ) , resultList.get( i ) ) ;
						
						// 레코드 index QC
						recordQcIdx.put( ( resultList.get( i ).get( "MTHD" ) + "" ) , tempMap ) ;
					}
					else if( "QTC02".equals( resultList.get( i ).get( "TP" ) + "" ) ) {
						// 필드 QC
						fieldQcInf.put( ( resultList.get( i ).get( "MTHD" ) + "" ) , resultList.get( i ) ) ;
						
						// 필드 index QC
						fieldQcIdx.put( ( resultList.get( i ).get( "MTHD" ) + "" ) , tempMap ) ;
					}
				}
				logger.info( "fieldQcInf :: " + fieldQcInf ) ;
				logger.info( "recordQcInf :: " + recordQcInf ) ;
				logger.info( "fieldQcIdx :: " + fieldQcIdx ) ;
				logger.info( "recordQcIdx :: " + recordQcIdx ) ;
				
				// static 변수에 데이터 넣기
				synchronized( ConnectivityProperties.FIELD_QC_INF ) {
					ConnectivityProperties.FIELD_QC_INF = fieldQcInf ;
				}
				synchronized( ConnectivityProperties.RECORD_QC_INF ) {
					ConnectivityProperties.RECORD_QC_INF = recordQcInf ;
				}
				
				synchronized( ConnectivityProperties.FIELD_QC_IDX ) {
					ConnectivityProperties.FIELD_QC_IDX = fieldQcIdx ;
				}
				synchronized( ConnectivityProperties.RECORD_QC_IDX ) {
					ConnectivityProperties.RECORD_QC_IDX = recordQcIdx ;
				}
			}
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			settingManageDao = null ;
			resultList = null ;
			fieldQcInf = null ;
			recordQcInf = null ;
			fieldQcIdx = null ;
			tempMap = null ;
			i = 0 ;
			startInt = 0 ;
			endInt = 0 ;
		}
		
		return resultBool ;
	}
	
	public Boolean betweenDevicesCalculateInfoSetting( ) {
		Boolean resultBool = true ;
		
		SettingManageDao settingManageDao = new SettingManageDao( ) ;
		ArrayList< HashMap > resultList = new ArrayList< HashMap >( ) ;
		
		List< Map< String , Object > > btwnDvCalInfoList = new ArrayList< Map< String , Object > >( ) ;
		HashMap< String , Object > btwnDvCalInfoMap = new HashMap< String , Object >( ) ;
		int i = 0 ;
		
		try {
			resultList = settingManageDao.selectBetweenDevicesCalculateInfo( ) ;
			
			// logger.info( "resultList :: " + resultList ) ;
			if( resultList != null ) {
				
				for( i = 0 ; i < resultList.size( ) ; i++ ) {
					logger.info( "resultList.get( " + i + " ) :: " + resultList.get( i ) ) ;
					
					btwnDvCalInfoMap = resultList.get( i ) ;
					btwnDvCalInfoList.add( btwnDvCalInfoMap ) ;
				}
				logger.info( "btwnDvCalInfoList :: " + btwnDvCalInfoList ) ;
				
				// static 변수에 데이터 넣기
				synchronized( ConnectivityProperties.BTWN_DV_CAL_INFO ) {
					ConnectivityProperties.BTWN_DV_CAL_INFO = btwnDvCalInfoList ;
				}
			}
			
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			settingManageDao = null ;
			resultList = null ;
			btwnDvCalInfoList = null ;
			btwnDvCalInfoMap = null ;
			i = 0 ;
			
			logger.info( "ConnectivityProperties.BTWN_DV_CAL_INFO :: " + ConnectivityProperties.BTWN_DV_CAL_INFO ) ;
			// logger.info( "btwnDvCalInfoList :: " + btwnDvCalInfoList ) ;
		}
		return resultBool ;
	}
	
	public Boolean siteInfoSetting( ) {
		Boolean resultBool = true ;
		
		SettingManageDao settingManageDao = new SettingManageDao( ) ;
		HashMap< String , Object > siteInfoMap = new HashMap< String , Object >( ) ;
		HashMap< String , Object > siteInfoTrmMap = new HashMap< String , Object >( ) ;
		
		try {
			// 사이트 정보 조회
			siteInfoMap = settingManageDao.selectSiteInfo( ) ;
			// 사이트 장치간연산 정보 조회
			siteInfoTrmMap = ( HashMap< String , Object > ) siteInfoMap.get( "TRM" ) ;
			logger.debug( "siteInfoTrmMap :: " + siteInfoTrmMap ) ;
			
			// static 변수에 데이터 넣기
			synchronized( ConnectivityProperties.SITE_SMLT ) {
				ConnectivityProperties.SITE_SMLT = siteInfoMap.get( "SMLT" ) + "" ;
			}
			synchronized( ConnectivityProperties.SITE_SMLT_USR ) {
				ConnectivityProperties.SITE_SMLT_USR = siteInfoMap.get( "SMLT_USR" ) + "" ;
			}
			synchronized( ConnectivityProperties.SITE_TRM_CAL ) {
				ConnectivityProperties.SITE_TRM_CAL = siteInfoTrmMap.get( "CAL" ) + "" ;
			}
			
			logger.debug( "SITE_SMLT :: " + ConnectivityProperties.SITE_SMLT ) ;
			logger.debug( "SITE_SMLT_USR :: " + ConnectivityProperties.SITE_SMLT_USR ) ;
			logger.debug( "SITE_TRM_CAL :: " + ConnectivityProperties.SITE_TRM_CAL ) ;
			
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			settingManageDao = null ;
			siteInfoMap = null ;
			siteInfoTrmMap = null ;
		}
		
		return resultBool ;
	}
	
	public Boolean appioMappingInfoDeviceDataSetting( ) {
		Boolean resultBool = true ;
		
		SettingManageDao settingManageDao = new SettingManageDao( ) ;
		ArrayList< HashMap > resultList = new ArrayList< HashMap >( ) ;
		
		Map< String , List< Map< String , Object > > > appioMapperSdhsMap = new HashMap< String , List< Map< String , Object > > >( ) ;
		List< Map< String , Object > > appioMapperList = new ArrayList< Map< String , Object > >( ) ;
		HashMap< String , Object > appioMapperSubMap = new HashMap< String , Object >( ) ;
		int i = 0 ;
		
		try {
			resultList = settingManageDao.selectAppioMappingInfoDeviceData( ) ;
			
			if( resultList != null ) {
				for( i = 0 ; i < resultList.size( ) ; i++ ) {
					logger.debug( "resultList.get( " + i + " ) :: " + resultList.get( i ) ) ;
					// SO_ID 가 디바이스 아이디가 된다.
					if( appioMapperSdhsMap.get( ( resultList.get( i ).get( "SO_ID" ) + "" ) ) != null ) {
						// appioMapperSdhsMap.get( ( resultList.get( i ).get( "SO_ID" ) + "" ) ).put( ( resultList.get( i ).get( "SO_KEY" ) + "" ) , resultList.get( i ).get( "TG_ID" ) ) ;
						
						appioMapperSubMap = new HashMap< String , Object >( ) ;
						appioMapperSubMap.put( "SO_KEY" , resultList.get( i ).get( "SO_KEY" ) ) ;
						appioMapperSubMap.put( "TG_ID" , resultList.get( i ).get( "TG_ID" ) ) ;
						
						appioMapperSdhsMap.get( ( resultList.get( i ).get( "SO_ID" ) + "" ) ).add( appioMapperSubMap ) ;
						
					}
					else {
						appioMapperSubMap = new HashMap< String , Object >( ) ;
						appioMapperSubMap.put( "SO_KEY" , resultList.get( i ).get( "SO_KEY" ) ) ;
						appioMapperSubMap.put( "TG_ID" , resultList.get( i ).get( "TG_ID" ) ) ;
						
						appioMapperList = new ArrayList< Map< String , Object > >( ) ;
						appioMapperList.add( appioMapperSubMap ) ;
						
						appioMapperSdhsMap.put( ( resultList.get( i ).get( "SO_ID" ) + "" ) , appioMapperList ) ;
					}
					
				}
				synchronized( ConnectivityProperties.APPIO_MAPPER_SDHS ) {
					ConnectivityProperties.APPIO_MAPPER_SDHS = appioMapperSdhsMap ;
				}
				
			}
			logger.debug( "appioMapperSdhsMap :: " + appioMapperSdhsMap ) ;
			
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			settingManageDao = null ;
			i = 0 ;
			resultList = null ;
			appioMapperSdhsMap = null ;
			appioMapperList = null ;
			appioMapperSubMap = null ;
		}
		
		return resultBool ;
	}
	
	// public Boolean appioMappingInfoBetweenDevicesCalculatingDataSetting( ) {
	// Boolean resultBool = true ;
	//
	// SettingManageDao settingManageDao = new SettingManageDao( ) ;
	// ArrayList< HashMap > resultList = new ArrayList< HashMap >( ) ;
	// ArrayList< HashMap< String , Object > > appioMapperCrhsList = new ArrayList< HashMap< String , Object > >( ) ;
	// HashMap< String , Object > appioMapperCrhsSubMap = new HashMap< String , Object >( ) ;
	// HashMap< String , String > appioMapperCrhsMap = new HashMap< String , String >( ) ;
	//
	// int i = 0 ;
	//
	// try {
	// resultList = settingManageDao.selectAppioMappingInfoBetweenDevicesCalculatingData( ) ;
	//
	// appioMapperCrhsMap = new HashMap< String , String >( ) ;
	// if( resultList != null ) {
	// for( i = 0 ; i < resultList.size( ) ; i++ ) {
	// logger.debug( "resultList.get( " + i + " ) :: " + resultList.get( i ) ) ;
	// appioMapperCrhsMap.put( ( resultList.get( i ).get( "SO_KEY" ) + "" ) , ( resultList.get( i ).get( "TG_ID" ) + "" ) ) ;
	// }
	//
	// ConnectivityProperties.APPIO_MAPPER_CRHS = appioMapperCrhsMap ;
	// }
	// logger.debug( "appioMapperCrhsList :: " + appioMapperCrhsList ) ;
	//
	// }
	// catch( Exception e ) {
	// resultBool = false ;
	// logger.error( e.getMessage( ) , e ) ;
	// }
	// finally {
	// settingManageDao = null ;
	// resultList = null ;
	// appioMapperCrhsList = null ;
	// appioMapperCrhsSubMap = null ;
	// i = 0 ;
	//
	// }
	//
	// return resultBool ;
	// }
	
	public Boolean appioMappingInfoBetweenDevicesCalculatingDataSetting( ) {
		Boolean resultBool = true ;
		
		SettingManageDao settingManageDao = new SettingManageDao( ) ;
		ArrayList< HashMap > resultList = new ArrayList< HashMap >( ) ;
		List< Map< String , Object > > appioMapperCrhsList = new ArrayList< Map< String , Object > >( ) ;
		HashMap< String , Object > appioMapperCrhsSubMap = new HashMap< String , Object >( ) ;
		int i = 0 ;
		
		try {
			resultList = settingManageDao.selectAppioMappingInfoBetweenDevicesCalculatingData( ) ;
			
			if( resultList != null ) {
				for( i = 0 ; i < resultList.size( ) ; i++ ) {
					logger.debug( "resultList.get( " + i + " ) :: " + resultList.get( i ) ) ;
					appioMapperCrhsSubMap = new HashMap< String , Object >( ) ;
					
					// appioMapperCrhsSubMap.put( ( resultList.get( i ).get( "SO_KEY" ) + "" ) , resultList.get( i ).get( "TG_ID" ) ) ;
					
					appioMapperCrhsSubMap.put( "SO_KEY" , resultList.get( i ).get( "SO_KEY" ) ) ;
					appioMapperCrhsSubMap.put( "TG_ID" , resultList.get( i ).get( "TG_ID" ) ) ;
					appioMapperCrhsList.add( appioMapperCrhsSubMap ) ;
				}
				
				synchronized( ConnectivityProperties.APPIO_MAPPER_CRHS ) {
					ConnectivityProperties.APPIO_MAPPER_CRHS = appioMapperCrhsList ;
				}
			}
			logger.debug( "appioMapperCrhsList :: " + appioMapperCrhsList ) ;
			
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			settingManageDao = null ;
			resultList = null ;
			appioMapperCrhsList = null ;
			appioMapperCrhsSubMap = null ;
			i = 0 ;
			
		}
		
		return resultBool ;
	}
	
	public Boolean deviceCheckPropertiesSetting( ) {
		Boolean resultBool = true ;
		
		SettingManageDao settingManageDao = new SettingManageDao( ) ;
		List< HashMap > resultList = new ArrayList< HashMap >( ) ;
		HashMap< String , Integer > dataSaveCycleCheckMap = new HashMap< String , Integer >( ) ;
		HashMap< String , String > lastGatherDtm = new HashMap< String , String >( ) ;
		HashMap< String , Integer > oldDataQcCheck = new HashMap< String , Integer >( ) ;
		String deviceId = "" ;
		int i = 0 ;
		try {
			logger.info( "devicePropertiesSetting" ) ;
			
			// 데이터 조회
			resultList = settingManageDao.selectInstallDeviceList( ) ;
			logger.info( "resultList :: " + resultList ) ;
			
			if( resultList != null ) {
				for( i = 0 ; i < resultList.size( ) ; i++ ) {
					logger.info( "resultList.get( " + i + " ).get( \"_id\" ) :: " + resultList.get( i ).get( "_id" ) ) ;
					deviceId = resultList.get( i ).get( "_id" ) + "" ;
					
					dataSaveCycleCheckMap.put( deviceId , 0 ) ;
					oldDataQcCheck.put( deviceId , 0 ) ;
					lastGatherDtm.put( deviceId , "" ) ;
				}
			}
			logger.info( "dataSaveCycleCheckMap :: " + dataSaveCycleCheckMap ) ;
			logger.info( "oldDataQcCheck :: " + oldDataQcCheck ) ;
			logger.info( "lastGatherDtm :: " + lastGatherDtm ) ;
			
			synchronized (ConnectivityProperties.LAST_GATHER_DTM) {
				ConnectivityProperties.LAST_GATHER_DTM = lastGatherDtm ;
			}
			synchronized (ConnectivityProperties.OLD_DATA_QC_CHECK) {
				ConnectivityProperties.OLD_DATA_QC_CHECK = oldDataQcCheck ;
			}
			synchronized (ConnectivityProperties.DATA_SAVE_CYCLE_CHECK) {
				ConnectivityProperties.DATA_SAVE_CYCLE_CHECK = dataSaveCycleCheckMap ;
			}
			
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			settingManageDao = null ;
			resultList = null ;
			dataSaveCycleCheckMap = null ;
			lastGatherDtm = null ;
			oldDataQcCheck = null ;
			deviceId = null ;
			i = 0 ;
		}
		return resultBool ;
	}
	
}
