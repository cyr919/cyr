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
		exe.qualityCodeInfoSetting( ) ;
		
	}
	
	public Boolean devicePropertiesSetting( ) {
		
		Boolean result = true ;
		logger.info( "devicePropertiesSetting" ) ;
		
		SettingManageDao settingManageDao = new SettingManageDao( ) ;
		
		List< HashMap > resultList = new ArrayList< HashMap >( ) ;
		HashMap< String , Object > tempMap = new HashMap< String , Object >( ) ;
		ArrayList< HashMap< String , Object > > tempList = new ArrayList< HashMap< String , Object > >( ) ;
		HashMap< String , HashMap< String , Object > > deviceInfo = new HashMap< String , HashMap< String , Object > >( ) ;
		HashMap< String , ArrayList< HashMap< String , Object > > > deviceDataModel = new HashMap< String , ArrayList< HashMap< String , Object > > >( ) ;
		HashMap< String , ArrayList< HashMap< String , Object > > > deviceCalInfo = new HashMap< String , ArrayList< HashMap< String , Object > > >( ) ;
		
		CommUtil commUtil = new CommUtil( ) ;
		HashMap< String , HashMap< String , Object > > tempMapFromList = new HashMap< String , HashMap< String , Object > >( ) ;
		
		HashMap< String , HashMap< String , HashMap< String , Object > > > deviceDataModelMap = new HashMap< String , HashMap< String , HashMap< String , Object > > >( ) ;
		
		String deviceId = "" ;
		try {
			result = false ;
			
			// 데이터 조회
			resultList = settingManageDao.selectInstallDeviceList( ) ;
			logger.info( "resultList :: " + resultList ) ;
			
			// 조회한 데이터를 변수에 저장한다.
			// 기본 정보
			// 저장(공통) 데이터 모델
			// 장치내 연산정보
			
			for( int i = 0 ; i < resultList.size( ) ; i++ ) {
				
				logger.info( "resultList.get( " + i + " ) :: " + resultList.get( i ) ) ;
				logger.info( "resultList.get( " + i + " ).get( \"_id\" ) :: " + resultList.get( i ).get( "_id" ) ) ;
				logger.info( "resultList.get( " + i + " ).get( \"DT_MDL\" ) :: " + resultList.get( i ).get( "DT_MDL" ) ) ;
				logger.info( "resultList.get( " + i + " ).get( \"CAL_INF\" ) :: " + resultList.get( i ).get( "CAL_INF" ) ) ;
				
				deviceId = resultList.get( i ).get( "_id" ) + "" ;
				
				// 디바이스 기본정보 처리
				tempMap.put( "_id" , resultList.get( i ).get( "_id" ) ) ;
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
				
				// 데이터 모델 list -> map
				tempMapFromList = commUtil.getHashMapFromListHashMap( tempList , "MGP_KEY" ) ;
				deviceDataModelMap.put( deviceId , tempMapFromList ) ;
				
				// 장치 내 연산 처리
				tempList = new ArrayList< HashMap< String , Object > >( ) ;
				tempList.addAll( ( ArrayList< HashMap< String , Object > > ) resultList.get( i ).get( "CAL_INF" ) ) ;
				
				deviceCalInfo.put( deviceId , tempList ) ;
				
			}
			logger.info( "deviceDataModel :: " + deviceDataModel ) ;
			logger.info( "deviceDataModelMap :: " + deviceDataModelMap ) ;
			logger.info( "deviceCalInfo :: " + deviceCalInfo ) ;
			
			ConnectivityProperties.STDV_DT_MDL = deviceDataModel ;
			ConnectivityProperties.STDV_CAL_INF = deviceCalInfo ;
			ConnectivityProperties.STDV_INF = deviceInfo ;
			ConnectivityProperties.STDV_DT_MDL_MAP = deviceDataModelMap ;
			
			result = true ;
		}
		catch( Exception e ) {
			result = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			settingManageDao = null ;
			logger.info( "devicePropertiesSetting finally" ) ;
			
			deviceDataModel = null ;
			deviceCalInfo = null ;
			deviceInfo = null ;
		}
		
		return result ;
	}
	
	public Boolean qualityCodeInfoSetting( ) {
		
		Boolean result = true ;
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
			resultList = settingManageDao.selectFieldQualityCodeList( ) ;
			
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
			
			ConnectivityProperties.FIELD_QC_INF = fieldQcInf ;
			ConnectivityProperties.RECORD_QC_INF = recordQcInf ;
			
			ConnectivityProperties.FIELD_QC_IDX = fieldQcIdx ;
			ConnectivityProperties.RECORD_QC_IDX = recordQcIdx ;
			
		}
		catch( Exception e ) {
			result = false ;
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
		
		return result ;
	}
	
}
