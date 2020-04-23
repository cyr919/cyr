
package com.connectivity.utils ;

import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.Iterator ;
import java.util.List ;
import java.util.Map ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;
import org.json.simple.JSONArray ;
import org.json.simple.JSONObject ;
import org.json.simple.parser.JSONParser ;
import org.json.simple.parser.ParseException ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-04-06
 */
public class JsonUtil
{
	
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	Logger logger = LogManager.getLogger( JsonUtil.class ) ;
	
	/**
	 * <pre>
	 * json string to json object
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-04-06
	 * @return
	 */
	public JSONObject getJSONObjectFromString( String strJson ) {
		
		JSONObject resultJsonObject = new JSONObject( ) ;
		
		JSONParser jsonParser = new JSONParser( ) ;
		
		try {
			resultJsonObject = ( JSONObject ) jsonParser.parse( ( strJson ) ) ;
			
		}
		catch( ParseException e ) {
			logger.error( "변환에 실패" ) ;
			logger.error( e.getMessage( ) , e ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			jsonParser = null ;
			strJson = null ;
		}
		
		return resultJsonObject ;
		
	}
	
	/**
	 * <pre>
	 * Map을 json으로 변환한다.
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-04-07
	 * @param map
	 * @return
	 */
	public JSONObject getJsonStringFromMap( Map< String , Object > map ) {
		
		JSONObject jsonObject = new JSONObject( ) ;
		String key = "" ;
		Object value = null ;
		try {
			for( Map.Entry< String , Object > entry : map.entrySet( ) ) {
				key = entry.getKey( ) ;
				value = entry.getValue( ) ;
				
				jsonObject.put( key , value ) ;
			}
			
		}
		catch( Exception e ) {
			jsonObject = new JSONObject( ) ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			map = null ;
			key = null ;
			value = null ;
		}
		
		return jsonObject ;
	}
	
	/**
	 * List<Map>을 jsonArray로 변환한다.
	 *
	 * @param list List<Map<String, Object>>.
	 * @return JSONArray.
	 */
	public JSONArray getJsonArrayFromList( List< Map< String , Object > > list ) {
		JSONArray jsonArray = new JSONArray( ) ;
		for( Map< String , Object > map : list ) {
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
	public String getJsonStringFromList( List< Map< String , Object > > list ) {
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
	public HashMap< String , Object > getHashMapFromJsonObject( JSONObject jsonObj ) {
		HashMap< String , Object > resultHashMap = new HashMap<>( ) ;
		
		Iterator< ? > iteratorJSONObjectKeys = null ;
		String strJsonKey = "" ;
		// String strJsonVal = "" ;
		
		try {
			if( jsonObj != null ) {
				iteratorJSONObjectKeys = jsonObj.keySet( ).iterator( ) ;
				while( iteratorJSONObjectKeys.hasNext( ) ) {
					strJsonKey = ( String ) iteratorJSONObjectKeys.next( ) ;
					// strJsonVal = ( String ) jsonObj.get( strJsonKey ) ;
					
					resultHashMap.put( strJsonKey , jsonObj.get( strJsonKey ) ) ;
				}
			}
			logger.debug( "resultHashMap :: " + resultHashMap ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
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
	public List< HashMap< String , Object > > getListHashMapFromJsonArray( JSONArray jsonArray ) {
		List< HashMap< String , Object > > resultList = new ArrayList<>( ) ;
		
		HashMap< String , Object > resultHashMap = new HashMap<>( ) ;
		JSONObject JSONObject = new JSONObject( ) ;
		int i = 0 ;
		
		try {
			if( jsonArray != null ) {
				for( i = 0 ; i < jsonArray.size( ) ; i++ ) {
					JSONObject = ( JSONObject ) jsonArray.get( i ) ;
					
					resultHashMap = new HashMap<>( ) ;
					resultHashMap = this.getHashMapFromJsonObject( JSONObject ) ;
					
					resultList.add( resultHashMap ) ;
				}
			}
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			i = 0 ;
			JSONObject = null ;
			resultHashMap = null ;
			jsonArray = null ;
		}
		
		return resultList ;
	}
}
