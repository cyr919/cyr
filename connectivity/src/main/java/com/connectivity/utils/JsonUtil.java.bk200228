
package com.connectivity.utils ;

import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.List ;
import java.util.Map ;

import org.apache.log4j.Logger ;
import org.json.simple.JSONArray ;
import org.json.simple.JSONObject ;

import com.mongodb.util.JSON ;

public class JsonUtil
{

	static Logger logger = Logger.getLogger( JsonUtil.class ) ;

	public static void main( String[ ] args )
	{
		// TODO Auto-generated method stub
		
		JsonUtil exe = new JsonUtil( ) ;
		HashMap< String , Object > paramHashMap = new HashMap<>( ) ;
		JSONObject jsonObject = new JSONObject( ) ;
		
		jsonObject = exe.getJsonStringFromMap( paramHashMap ) ;


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
	 * JsonObject를 Map<String, String>으로 변환한다.
	 *
	 * @param jsonObj JSONObject.
	 * @return Map<String, Object>.
	 */
	@SuppressWarnings( "unchecked" )
	public Map< String , Object > getMapFromJsonObject( JSONObject jsonObj )
	{
		Map< String , Object > map = null ;

		// try
		// {
		//
		// map = new ObjectMapper( ).readValue( jsonObj.toJSONString( ) , Map.class ) ;
		//
		// }
		// catch ( JsonParseException e )
		// {
		// e.printStackTrace( ) ;
		// }
		// catch ( JsonMappingException e )
		// {
		// e.printStackTrace( ) ;
		// }
		// catch ( IOException e )
		// {
		// e.printStackTrace( ) ;
		// }

		return map ;
	}

	/**
	 * JsonArray를 List<Map<String, String>>으로 변환한다.
	 *
	 * @param jsonArray JSONArray.
	 * @return List<Map<String, Object>>.
	 */
	public List< Map< String , Object > > getListMapFromJsonArray( JSONArray jsonArray )
	{
		List< Map< String , Object > > list = new ArrayList< Map< String , Object > >( ) ;

		if ( jsonArray != null )
		{
			int jsonSize = jsonArray.size( ) ;
			for ( int i = 0 ; i < jsonSize ; i++ )
			{
				Map< String , Object > map = this.getMapFromJsonObject( ( JSONObject ) jsonArray.get( i ) ) ;
				list.add( map ) ;
			}
		}

		return list ;
	}
}
