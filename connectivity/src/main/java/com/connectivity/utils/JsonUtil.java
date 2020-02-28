
package com.connectivity.utils ;

import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.Iterator ;
import java.util.List ;
import java.util.Map ;

import org.apache.log4j.Logger ;
import org.json.simple.JSONArray ;
import org.json.simple.JSONObject ;

public class JsonUtil
{

	static Logger logger = Logger.getLogger( JsonUtil.class ) ;

	public static void main( String[ ] args )
	{
		// TODO Auto-generated method stub
	}

	/**
	 * Map을 json으로 변환한다.
	 *
	 * @param map Map<String, Object>.
	 * @return JSONObject.
	 */
	public JSONObject getJsonStringFromMap( Map< String , Object > map )
	{
		JSONObject jsonObject = new JSONObject( ) ;
		for ( Map.Entry< String , Object > entry : map.entrySet( ) )
		{
			String key = entry.getKey( ) ;
			Object value = entry.getValue( ) ;
			jsonObject.put( key , value ) ;
		}

		return jsonObject ;
	}

	/**
	 * List<Map>을 jsonArray로 변환한다.
	 *
	 * @param list List<Map<String, Object>>.
	 * @return JSONArray.
	 */
	public JSONArray getJsonArrayFromList( List< Map< String , Object > > list )
	{
		JSONArray jsonArray = new JSONArray( ) ;
		for ( Map< String , Object > map : list )
		{
			jsonArray.add( getJsonStringFromMap( map ) ) ;
		}

		return jsonArray ;
	}

	/**
	 * List<Map>을 jsonString으로 변환한다.
	 *
	 * @param list List<Map<String, Object>>.
	 * @return String.
	 */
	public String getJsonStringFromList( List< Map< String , Object > > list )
	{
		JSONArray jsonArray = getJsonArrayFromList( list ) ;
		return jsonArray.toJSONString( ) ;
	}

	/**
	 * <pre>
	 * JsonObject를 HashMap<String,Object>으로 변환한다.
	 * </pre>
	 * 
	 * @param
	 * @return HashMap<String,Object>
	 * @date 2020. 2. 28.
	 */
	public HashMap< String , Object > getHashMapFromJsonObject( JSONObject jsonObj )
	{
		HashMap< String , Object > resultHashMap = new HashMap<>( ) ;

		Iterator< ? > iteratorJSONObjectKeys = null ;
		String strJsonKey = "" ;
		// String strJsonVal = "" ;

		try
		{
			if ( jsonObj != null )
			{
				iteratorJSONObjectKeys = jsonObj.keySet( ).iterator( ) ;
				while ( iteratorJSONObjectKeys.hasNext( ) )
				{
					strJsonKey = ( String ) iteratorJSONObjectKeys.next( ) ;
					// strJsonVal = ( String ) jsonObj.get( strJsonKey ) ;

					resultHashMap.put( strJsonKey , jsonObj.get( strJsonKey ) ) ;
				}
			}
			logger.debug( "resultHashMap :: " + resultHashMap ) ;

		}
		finally
		{
			jsonObj = null ;

			iteratorJSONObjectKeys = null ;
			strJsonKey = null ;
			// strJsonVal = null ;
		}

		return resultHashMap ;
	}

	/**
	 * <pre>
	 * JsonArray를 List<HashMap<String,Object>>으로 변환한다.
	 * </pre>
	 * 
	 * @param
	 * @return List<HashMap<String,Object>>
	 * @date 2020. 2. 28.
	 */
	public List< HashMap< String , Object > > getListHashMapFromJsonArray( JSONArray jsonArray )
	{
		List< HashMap< String , Object > > resultList = new ArrayList<>( ) ;

		HashMap< String , Object > resultHashMap = new HashMap<>( ) ;
		JSONObject JSONObject = new JSONObject( ) ;
		int i = 0 ;

		try
		{
			if ( jsonArray != null )
			{
				for ( i = 0 ; i < jsonArray.size( ) ; i++ )
				{
					JSONObject = ( JSONObject ) jsonArray.get( i ) ;

					resultHashMap = new HashMap<>( ) ;
					resultHashMap = this.getHashMapFromJsonObject( JSONObject ) ;

					resultList.add( resultHashMap ) ;
				}
			}
		}
		finally
		{
			i = 0 ;
			JSONObject = null ;
			resultHashMap = null ;
			jsonArray = null ;
		}

		return resultList ;
	}
}
