/**
 * 
 */
package com.connectivity.common;

import java.util.ArrayList ;
import java.util.HashMap ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.setting.SettingManage ;

/**
 *
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
	public static HashMap< String , HashMap< String , Object > > STDV_INF = new HashMap< String , HashMap<String,Object> >( ) ;
	// 저장(공통)데이터모델
	public static HashMap< String , ArrayList< HashMap< String , Object > > > STDV_DT_MDL = new HashMap< String , ArrayList<HashMap<String,Object>> >( );
	// 장치내 연산정보
	public static HashMap< String , ArrayList< HashMap< String , Object > > > STDV_CAL_INF = new HashMap< String , ArrayList<HashMap<String,Object>> >( );
	
	
	
	
	public Boolean setStdv () {
		Boolean resultBool = true ;
		
		logger.debug( "STDV_DT_MDL :: " + STDV_DT_MDL );
		logger.debug( "STDV_CAL_INF :: " + STDV_CAL_INF );
		logger.debug( "STDV_INF :: " + STDV_INF );

		SettingManage settingManage = new SettingManage( );
		resultBool = settingManage.devicePropertiesSetting( ) ;
		
		
		logger.debug( "STDV_DT_MDL :: " + STDV_DT_MDL );
		logger.debug( "STDV_CAL_INF :: " + STDV_CAL_INF );
		logger.debug( "STDV_INF :: " + STDV_INF );
		
		
		return resultBool ;
	}
	
	
}
