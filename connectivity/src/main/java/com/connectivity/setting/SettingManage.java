/**
 * 
 */
package com.connectivity.setting ;

import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.List ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.common.ConnectivityProperties ;
import com.connectivity.setting.dao.SettingManageDao ;
import com.connectivity.utils.CommUtil ;

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
		SettingManage exe = new SettingManage( ) ;
		
		// exe.devicePropertiesSetting( ) ;
		// exe.qualityCodeInfoSetting( ) ;
		exe.betweenDevicesCalculateInfoSetting( ) ;
		// exe.siteInfoSetting( ) ;
		// exe.appioMappingInfoBetweenDevicesCalculatingDataSetting( ) ;
		// exe.appioMappingInfoDeviceDataSetting( ) ;
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
		ArrayList< HashMap< String , Object > > tempList = new ArrayList< HashMap< String , Object > >( ) ;
		HashMap< String , HashMap< String , Object > > deviceInfo = new HashMap< String , HashMap< String , Object > >( ) ;
		HashMap< String , ArrayList< HashMap< String , Object > > > deviceDataModel = new HashMap< String , ArrayList< HashMap< String , Object > > >( ) ;
		HashMap< String , ArrayList< HashMap< String , Object > > > deviceCalInfo = new HashMap< String , ArrayList< HashMap< String , Object > > >( ) ;
		
		CommUtil commUtil = new CommUtil( ) ;
		HashMap< String , HashMap< String , Object > > tempMapFromList = new HashMap< String , HashMap< String , Object > >( ) ;
		
		// HashMap< String , HashMap< String , HashMap< String , Object > > > deviceDataModelMap = new HashMap< String , HashMap< String , HashMap< String , Object > > >( ) ;
		
		String deviceId = "" ;
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
				
				for( int i = 0 ; i < resultList.size( ) ; i++ ) {
					
					logger.info( "resultList.get( " + i + " ) :: " + resultList.get( i ) ) ;
					logger.info( "resultList.get( " + i + " ).get( \"_id\" ) :: " + resultList.get( i ).get( "_id" ) ) ;
					logger.info( "resultList.get( " + i + " ).get( \"DT_MDL\" ) :: " + resultList.get( i ).get( "DT_MDL" ) ) ;
					logger.info( "resultList.get( " + i + " ).get( \"CAL_INF\" ) :: " + resultList.get( i ).get( "CAL_INF" ) ) ;
					
					deviceId = resultList.get( i ).get( "_id" ) + "" ;
					
					// 디바이스 기본정보 처리
					tempMap.put( "_id" , resultList.get( i ).get( "_id" ) ) ;
					tempMap.put( "TP" , resultList.get( i ).get( "TP" ) ) ;
					tempMap.put( "SMLT" , resultList.get( i ).get( "SMLT" ) ) ;
					tempMap.put( "QC" , resultList.get( i ).get( "QC" ) ) ;
					tempMap.put( "SCR" , resultList.get( i ).get( "SCR" ) ) ;
					tempMap.put( "DVIF_ID" , resultList.get( i ).get( "DVIF_ID" ) ) ;
					tempMap.put( "ADPT_ID" , resultList.get( i ).get( "ADPT_ID" ) ) ;
					
					deviceInfo.put( deviceId , tempMap ) ;
					
					// 데이터 모델 처리
					tempList = new ArrayList< HashMap< String , Object > >( ) ;
					tempList.addAll( ( ArrayList< HashMap< String , Object > > ) resultList.get( i ).get( "DT_MDL" ) ) ;
					
					deviceDataModel.put( deviceId , tempList ) ;
					
					// // 데이터 모델 list -> map
					// tempMapFromList = commUtil.getHashMapFromListHashMap( tempList , "MGP_KEY" ) ;
					// deviceDataModelMap.put( deviceId , tempMapFromList ) ;
					
					// 장치 내 연산 처리
					tempList = new ArrayList< HashMap< String , Object > >( ) ;
					tempList.addAll( ( ArrayList< HashMap< String , Object > > ) resultList.get( i ).get( "CAL_INF" ) ) ;
					
					deviceCalInfo.put( deviceId , tempList ) ;
					
				}
				logger.info( "deviceDataModel :: " + deviceDataModel ) ;
				// logger.info( "deviceDataModelMap :: " + deviceDataModelMap ) ;
				logger.info( "deviceCalInfo :: " + deviceCalInfo ) ;
				
				// static 변수에 데이터 넣기
				ConnectivityProperties.STDV_DT_MDL = deviceDataModel ;
				ConnectivityProperties.STDV_CAL_INF = deviceCalInfo ;
				ConnectivityProperties.STDV_INF = deviceInfo ;
				// ConnectivityProperties.STDV_DT_MDL_MAP = deviceDataModelMap ;
			}
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			settingManageDao = null ;
			
			deviceDataModel = null ;
			deviceCalInfo = null ;
			deviceInfo = null ;
			logger.info( "devicePropertiesSetting finally" ) ;
		}
		
		return resultBool ;
	}
	
	public Boolean qualityCodeInfoSetting( ) {
		
		Boolean resultBool = true ;
		logger.info( "qualityCodeInfoSetting" ) ;
		
		SettingManageDao settingManageDao = new SettingManageDao( ) ;
		
		ArrayList< HashMap > resultList = new ArrayList< HashMap >( ) ;
		HashMap< String , HashMap< String , Object > > fieldQcInf = new HashMap< String , HashMap< String , Object > >( ) ;
		HashMap< String , HashMap< String , Object > > recordQcInf = new HashMap< String , HashMap< String , Object > >( ) ;
		HashMap< String , HashMap< String , Integer > > fieldQcIdx = new HashMap< String , HashMap< String , Integer > >( ) ;
		HashMap< String , HashMap< String , Integer > > recordQcIdx = new HashMap< String , HashMap< String , Integer > >( ) ;
		
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
				ConnectivityProperties.FIELD_QC_INF = fieldQcInf ;
				ConnectivityProperties.RECORD_QC_INF = recordQcInf ;
				
				ConnectivityProperties.FIELD_QC_IDX = fieldQcIdx ;
				ConnectivityProperties.RECORD_QC_IDX = recordQcIdx ;
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
		
		ArrayList< HashMap< String , Object > > btwnDvCalInfoList = new ArrayList< HashMap< String , Object > >( ) ;
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
				ConnectivityProperties.BTWN_DV_CAL_INFO = btwnDvCalInfoList ;
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
			ConnectivityProperties.SITE_SMLT = siteInfoMap.get( "SMLT" ) + "" ;
			ConnectivityProperties.SITE_SMLT_USR = siteInfoMap.get( "SMLT_USR" ) + "" ;
			
			ConnectivityProperties.SITE_TRM_CAL = siteInfoTrmMap.get( "CAL" ) + "" ;
			
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
		
		HashMap< String , ArrayList< HashMap< String , Object > > > appioMapperSdhsMap = new HashMap< String , ArrayList< HashMap< String , Object > > >( ) ;
		ArrayList< HashMap< String , Object > > appioMapperList = new ArrayList< HashMap< String , Object > >( ) ;
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
						appioMapperSubMap.put( "MGP_KEY" , resultList.get( i ).get( "SO_KEY" ) ) ;
						appioMapperSubMap.put( "POINT_IDX" , resultList.get( i ).get( "TG_ID" ) ) ;
						
						appioMapperSdhsMap.get( ( resultList.get( i ).get( "SO_ID" ) + "" ) ).add( appioMapperSubMap ) ;
						
					}
					else {
						appioMapperSubMap = new HashMap< String , Object >( ) ;
						appioMapperSubMap.put( "MGP_KEY" , resultList.get( i ).get( "SO_KEY" ) ) ;
						appioMapperSubMap.put( "POINT_IDX" , resultList.get( i ).get( "TG_ID" ) ) ;
						
						appioMapperList = new ArrayList< HashMap< String , Object > >( ) ;
						appioMapperList.add( appioMapperSubMap ) ;
						
						appioMapperSdhsMap.put( ( resultList.get( i ).get( "SO_ID" ) + "" ) , appioMapperList ) ;
					}
					
				}
				ConnectivityProperties.APPIO_MAPPER_SDHS = appioMapperSdhsMap ;
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
	
	public Boolean appioMappingInfoBetweenDevicesCalculatingDataSetting( ) {
		Boolean resultBool = true ;
		
		SettingManageDao settingManageDao = new SettingManageDao( ) ;
		ArrayList< HashMap > resultList = new ArrayList< HashMap >( ) ;
		ArrayList< HashMap< String , Object > > appioMapperCrhsList = new ArrayList< HashMap< String , Object > >( ) ;
		HashMap< String , Object > appioMapperCrhsSubMap = new HashMap< String , Object >( ) ;
		HashMap< String , String > appioMapperCrhsMap = new HashMap< String , String >( ) ;
		
		int i = 0 ;
		
		try {
			resultList = settingManageDao.selectAppioMappingInfoBetweenDevicesCalculatingData( ) ;
			
			appioMapperCrhsMap = new HashMap< String , String >( ) ;
			if( resultList != null ) {
				for( i = 0 ; i < resultList.size( ) ; i++ ) {
					logger.debug( "resultList.get( " + i + " ) :: " + resultList.get( i ) ) ;
					appioMapperCrhsMap.put( ( resultList.get( i ).get( "SO_KEY" ) + "" ) , ( resultList.get( i ).get( "TG_ID" ) + "" ) ) ;
				}
				
				ConnectivityProperties.APPIO_MAPPER_CRHS = appioMapperCrhsMap ;
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
	
	// public Boolean appioMappingInfoBetweenDevicesCalculatingDataSetting( ) {
	// Boolean resultBool = true ;
	//
	// SettingManageDao settingManageDao = new SettingManageDao( ) ;
	// ArrayList< HashMap > resultList = new ArrayList< HashMap >( ) ;
	// ArrayList< HashMap< String , Object > > appioMapperCrhsList = new ArrayList< HashMap< String , Object > >( ) ;
	// HashMap< String , Object > appioMapperCrhsSubMap = new HashMap< String , Object >( ) ;
	// int i = 0 ;
	//
	// try {
	// resultList = settingManageDao.selectAppioMappingInfoBetweenDevicesCalculatingData( ) ;
	//
	// if( resultList != null ) {
	// for( i = 0 ; i < resultList.size( ) ; i++ ) {
	// logger.debug( "resultList.get( " + i + " ) :: " + resultList.get( i ) ) ;
	// appioMapperCrhsSubMap = new HashMap< String , Object >( ) ;
	// appioMapperCrhsSubMap.put( ( resultList.get( i ).get( "SO_KEY" ) + "" ) , resultList.get( i ).get( "TG_ID" ) ) ;
	////
	//// appioMapperCrhsSubMap.put( "MGP_KEY" , resultList.get( i ).get( "SO_KEY" ) ) ;
	//// appioMapperCrhsSubMap.put( "POINT_IDX" , resultList.get( i ).get( "TG_ID" ) ) ;
	// appioMapperCrhsList.add( appioMapperCrhsSubMap ) ;
	// }
	//
	// ConnectivityProperties.APPIO_MAPPER_CRHS = appioMapperCrhsList ;
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
	
}
