/**
 * 
 */
package com.connectivity.setting ;

import java.util.ArrayList ;
import java.util.HashMap ;

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
		
		exe.devicePropertiesSetting( ) ;
		
	}
	
	public Boolean devicePropertiesSetting( ) {
		
		Boolean result = true ;
		logger.info( "devicePropertiesSetting" ) ;
		
		SettingManageDao settingManageDao = new SettingManageDao( ) ;
		
		ArrayList< HashMap > resultList = new ArrayList< HashMap >( ) ;
		HashMap< String , Object > tempMap = new HashMap< String , Object >( ) ;
		ArrayList< HashMap< String , Object > > tempList = new ArrayList< HashMap< String , Object > >( ) ;
		HashMap< String , HashMap< String , Object > > deviceInfo = new HashMap< String , HashMap< String , Object > >( ) ;
		HashMap< String , ArrayList< HashMap< String , Object > > > deviceDataModel = new HashMap< String , ArrayList< HashMap< String , Object > > >( ) ;
		HashMap< String , ArrayList< HashMap< String , Object > > > deviceCalInfo = new HashMap< String , ArrayList< HashMap< String , Object > > >( ) ;
		
		CommUtil commUtil = new CommUtil( ) ;
		HashMap< String , HashMap< String , Object > > tempMapFromList = new HashMap< String , HashMap<String,Object> >( );
		
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
		
		
		try {
			
		}
		catch( Exception e ) {
			result = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			settingManageDao = null ;
		}
		
		return result ;
	}

	
	
}


