
package com.prototype ;

import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.List ;

import org.apache.log4j.Logger ;
import org.json.simple.JSONArray ;
import org.json.simple.JSONObject ;
import org.json.simple.parser.JSONParser ;
import org.json.simple.parser.ParseException ;

import com.connectivity.utils.FileUtil ;
import com.connectivity.utils.JsonUtil ;

public class JsonDeviceProperties
{
	
	static Logger logger = Logger.getLogger( JsonDeviceProperties.class ) ;
	
	private String jsonPropertiesFile = "C:\\\\_git\\\\local_repositories\\\\cyr\\\\connectivity\\\\env\\\\settingParam.json" ;
	
	public HashMap< String , List< HashMap< String , Object > > > getDevicePropertiesDtMdl( ) {
		
		HashMap< String , List< HashMap< String , Object > > > resultHashMap = new HashMap<>( ) ;
		
		FileUtil fileUtil = new FileUtil( ) ;
		JsonUtil jsonUtil = new JsonUtil( ) ;
		
		StringBuffer fileCntStringBuffer = null ;
		JSONObject jsonObject = new JSONObject( ) ;
		JSONArray dvSetJSONArray = new JSONArray( ) ;
		JSONObject dvSetJSONObject = new JSONObject( ) ;
		
		JSONArray tempJSONArray = new JSONArray( ) ;
		
		List< HashMap< String , Object > > tempList = new ArrayList<>( ) ;
		
		int i = 0 ;
		
		try {
			logger.debug( "getDevicePropertiesDtMdl 시작 :: " ) ;
			
			fileCntStringBuffer = new StringBuffer( ) ;
			fileCntStringBuffer = fileUtil.fileContentRead( jsonPropertiesFile ) ;
			
			logger.debug( "fileCntStringBuffer :: " ) ;
			logger.debug( fileCntStringBuffer ) ;
			logger.debug( "fileCntStringBuffer :: " ) ;
			
			jsonObject = jsonUtil.getJSONObjectFromString( ( fileCntStringBuffer + "" ) ) ;
			
			dvSetJSONArray = ( JSONArray ) jsonObject.get( "DVIF_SETTINGS" ) ;
			
			logger.debug( "dvSetJSONArray :: " + dvSetJSONArray ) ;
			logger.debug( "dvSetJSONArray.size( ) :: " + dvSetJSONArray.size( ) ) ;
			
			for( i = 0 ; i < dvSetJSONArray.size( ) ; i++ ) {
				dvSetJSONObject = ( JSONObject ) dvSetJSONArray.get( i ) ;
				
				logger.debug( "dvSetJSONObject :: " + dvSetJSONObject ) ;
				
				tempJSONArray = ( JSONArray ) dvSetJSONObject.get( "DT_MDL" ) ;
				tempList = jsonUtil.getListHashMapFromJsonArray( tempJSONArray ) ;
				logger.debug( "tempList :: " + tempList ) ;
				
				resultHashMap.put( ( dvSetJSONObject.get( "DVIF_ID" ) + "" ) , tempList ) ;
			}
			
			logger.debug( "resultHashMap :: " ) ;
			logger.debug( resultHashMap ) ;
			logger.debug( "resultHashMap :: " ) ;
			
		} catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		} finally {
			
			fileUtil = null ;
			jsonUtil = null ;
			
			fileCntStringBuffer = null ;
			jsonObject = null ;
			dvSetJSONArray = null ;
			dvSetJSONObject = null ;
			
			tempJSONArray = null ;
			
			tempList = null ;
			
			i = 0 ;
			logger.debug( "getDevicePropertiesDtMdl 종료 :: " ) ;
		}
		
		return resultHashMap ;
	}
	
	public HashMap< String , List< HashMap< String , Object > > > getDevicePropertiesCalInf( ) {
		
		HashMap< String , List< HashMap< String , Object > > > resultHashMap = new HashMap<>( ) ;
		
		FileUtil fileUtil = new FileUtil( ) ;
		JsonUtil jsonUtil = new JsonUtil( ) ;
		
		StringBuffer fileCntStringBuffer = null ;
		JSONParser jsonParser = new JSONParser( ) ;
		JSONObject jsonObject = new JSONObject( ) ;
		JSONArray dvSetJSONArray = new JSONArray( ) ;
		JSONObject dvSetJSONObject = new JSONObject( ) ;
		
		JSONArray tempJSONArray = new JSONArray( ) ;
		
		List< HashMap< String , Object > > tempList = new ArrayList<>( ) ;
		
		int i = 0 ;
		
		try {
			logger.debug( "getDevicePropertiesCalInf 시작 :: " ) ;
			
			fileCntStringBuffer = new StringBuffer( ) ;
			fileCntStringBuffer = fileUtil.fileContentRead( jsonPropertiesFile ) ;
			
			logger.debug( "fileCntStringBuffer :: " ) ;
			logger.debug( fileCntStringBuffer ) ;
			logger.debug( "fileCntStringBuffer :: " ) ;
			
			jsonObject = ( JSONObject ) jsonParser.parse( ( fileCntStringBuffer + "" ) ) ;
			dvSetJSONArray = ( JSONArray ) jsonObject.get( "DVIF_SETTINGS" ) ;
			
			logger.debug( "dvSetJSONArray :: " + dvSetJSONArray ) ;
			logger.debug( "dvSetJSONArray.size( ) :: " + dvSetJSONArray.size( ) ) ;
			
			for( i = 0 ; i < dvSetJSONArray.size( ) ; i++ ) {
				dvSetJSONObject = ( JSONObject ) dvSetJSONArray.get( i ) ;
				
				logger.debug( "dvSetJSONObject :: " + dvSetJSONObject ) ;
				
				tempJSONArray = ( JSONArray ) dvSetJSONObject.get( "CAL_INF" ) ;
				tempList = jsonUtil.getListHashMapFromJsonArray( tempJSONArray ) ;
				logger.debug( "tempList :: " + tempList ) ;
				
				resultHashMap.put( ( dvSetJSONObject.get( "DVIF_ID" ) + "" ) , tempList ) ;
			}
			
			logger.debug( "resultHashMap :: " ) ;
			logger.debug( resultHashMap ) ;
			logger.debug( "resultHashMap :: " ) ;
			
		} catch( ParseException e ) {
			logger.error( "변환에 실패" ) ;
			logger.error( e.getMessage( ) , e ) ;
		} catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		} finally {
			
			fileUtil = null ;
			jsonUtil = null ;
			
			fileCntStringBuffer = null ;
			jsonParser = null ;
			jsonObject = null ;
			dvSetJSONArray = null ;
			dvSetJSONObject = null ;
			
			tempJSONArray = null ;
			
			tempList = null ;
			
			i = 0 ;
			logger.debug( "getDevicePropertiesCalInf 종료 :: " ) ;
		}
		
		return resultHashMap ;
	}
	
}
