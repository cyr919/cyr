/**
 * 
 */
package com.connectivity.common ;

import java.util.ArrayList ;
import java.util.HashMap ;

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
public class ConnectivityProperties
{
	private Logger logger = LogManager.getLogger( this.getClass( ) ) ;
	
	// 설치 디바이스 정보
	// 기본 정보
	public static HashMap< String , HashMap< String , Object > > STDV_INF = new HashMap< String , HashMap< String , Object > >( ) ;
	// 저장(공통)데이터모델
	public static HashMap< String , ArrayList< HashMap< String , Object > > > STDV_DT_MDL = new HashMap< String , ArrayList< HashMap< String , Object > > >( ) ;
	// 저장(공통)데이터모델 list -> map
	public static HashMap< String , HashMap< String , HashMap< String , Object > > > STDV_DT_MDL_MAP = new HashMap< String , HashMap< String , HashMap< String , Object > > >( ) ;
	// 장치내 연산정보
	public static HashMap< String , ArrayList< HashMap< String , Object > > > STDV_CAL_INF = new HashMap< String , ArrayList< HashMap< String , Object > > >( ) ;
	// 레코드 퀄리티 코드 정보
	public static HashMap< String , HashMap< String , Object > > RECORD_QC_INF = new HashMap< String , HashMap< String , Object > >( ) ;
	// 필드 퀄리티 코드 정보
	public static HashMap< String , HashMap< String , Object > > FIELD_QC_INF = new HashMap< String , HashMap< String , Object > >( ) ;
	// 레코드 퀄리티 코드 정보
	public static HashMap< String , HashMap< String , Integer > > RECORD_QC_IDX = new HashMap< String , HashMap< String , Integer > >( ) ;
	// 필드 퀄리티 코드 정보
	public static HashMap< String , HashMap< String , Integer > > FIELD_QC_IDX = new HashMap< String , HashMap< String , Integer > >( ) ;

	public Boolean setConnectivityProperties( ) {
		Boolean resultBool = true ;
		
		logger.debug( "STDV_DT_MDL :: " + STDV_DT_MDL ) ;
		logger.debug( "STDV_CAL_INF :: " + STDV_CAL_INF ) ;
		logger.debug( "STDV_INF :: " + STDV_INF ) ;
		
		SettingManage settingManage = new SettingManage( ) ;
		
		try {
			resultBool = false ;
			
			if( settingManage.devicePropertiesSetting( ) ) {
				if( settingManage.qualityCodeInfoSetting( ) ) {
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
		}
		
		logger.debug( "STDV_DT_MDL :: " + STDV_DT_MDL ) ;
		logger.debug( "STDV_CAL_INF :: " + STDV_CAL_INF ) ;
		logger.debug( "STDV_INF :: " + STDV_INF ) ;
		
		return resultBool ;
	}
	
}
