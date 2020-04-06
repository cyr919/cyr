
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


	public static void main( String[ ] args )
	{


	}

	public HashMap< String , HashMap< String , Object > > getDeviceProperties( )
	{

		HashMap< String , HashMap< String , Object > > resultHashMap = new HashMap<>( ) ;
		HashMap< String , Object > resultsubHashMap = new HashMap<>( ) ;

		FileUtil fileUtil = new FileUtil( ) ;
		JsonUtil jsonUtil = new JsonUtil( ) ;

		StringBuffer fileCntStringBuffer = null ;
		JSONParser jsonParser = new JSONParser( ) ;
		JSONObject jsonObject = new JSONObject( ) ;
		JSONArray dvSetJSONArray = new JSONArray( ) ;
		JSONObject dvSetJSONObject = new JSONObject( ) ;

		JSONArray tempJSONArray = new JSONArray( ) ;
		JSONObject tempJSONObject = new JSONObject( ) ;

		HashMap< String , Object > tempHashMap = new HashMap<>( ) ;
		List< HashMap< String , Object > > tempList = new ArrayList<>( ) ;
		String deviceIdStr = "" ;

		int i = 0 ;
		int j = 0 ;

		try
		{
			logger.debug( "getDeviceProperties 시작 :: " ) ;

			fileCntStringBuffer = new StringBuffer( ) ;
			fileCntStringBuffer = fileUtil.fileContentRead( "C:\\_git\\local_repositories\\cyr\\connectivity\\env\\settingParam.json" ) ;

			logger.debug( "fileCntStringBuffer :: " ) ;
			logger.debug( fileCntStringBuffer ) ;
			logger.debug( "fileCntStringBuffer :: " ) ;

			jsonObject = ( JSONObject ) jsonParser.parse( ( fileCntStringBuffer + "" ) ) ;
			dvSetJSONArray = ( JSONArray ) jsonObject.get( "DVIF_SETTINGS" ) ;

			logger.debug( "dvSetJSONArray :: " + dvSetJSONArray ) ;
			logger.debug( "dvSetJSONArray.size( ) :: " + dvSetJSONArray.size( ) ) ;

			for ( i = 0 ; i < dvSetJSONArray.size( ) ; i++ )
			{
				dvSetJSONObject = ( JSONObject ) dvSetJSONArray.get( i ) ;
				resultsubHashMap = new HashMap<>( ) ;

				logger.debug( "dvSetJSONObject :: " + dvSetJSONObject ) ;

				tempJSONArray = ( JSONArray ) dvSetJSONObject.get( "DT_MDL" ) ;
				tempList = jsonUtil.getListHashMapFromJsonArray( tempJSONArray ) ;
				logger.debug( "tempList :: " + tempList ) ;
				resultsubHashMap.put( "DT_MDL" , tempList ) ;

				tempJSONArray = ( JSONArray ) dvSetJSONObject.get( "CAL_INF" ) ;
				tempList = jsonUtil.getListHashMapFromJsonArray( tempJSONArray ) ;
				logger.debug( "tempList :: " + tempList ) ;
				resultsubHashMap.put( "CAL_INF" , tempList ) ;

				resultHashMap.put( ( dvSetJSONObject.get( "DVIF_ID" ) + "" ) , resultsubHashMap ) ;
			}

			logger.debug( "resultHashMap :: " ) ;
			logger.debug( resultHashMap ) ;
			logger.debug( "resultHashMap :: " ) ;

		}
		catch ( ParseException e )
		{
			System.out.println( "변환에 실패" ) ;
			e.printStackTrace( ) ;
		}
		finally
		{
			logger.debug( "getDeviceProperties 종료 :: " ) ;
		}

		return resultHashMap ;
	}

}
