/**
 * 
 */
package com.connectivity.gather.dao ;

import java.util.HashMap ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.common.RedisCommon ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-05-21
 */
public class DataGatherDao
{
	private Logger logger = LogManager.getLogger( this.getClass( ) ) ;
	
	/**
	 * <pre>
	 * 계측 처리한 데이터를 redis에 저장한다.
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-05-21
	 * @param dviceId
	 * @param redisSetDataMap
	 * @return
	 */
	public Boolean hmSetGatherData( String dviceId , HashMap< String , String > redisSetDataMap ) {
		Boolean resulBoolean = true ;
		
		String strKey = "" ;
		String resultStr = "" ;
		
		RedisCommon redisCommon = new RedisCommon( ) ;
		try {
			strKey = "MGP_STDV" + "^" + dviceId ;
			logger.debug( "strKey :: " + strKey ) ;
			
			resultStr = redisCommon.redisHmset( strKey , redisSetDataMap ) ;
			
		}
		catch( Exception e ) {
			resulBoolean = false ;
		}
		finally {
			strKey = null ;
			dviceId = null ;
			redisSetDataMap = null ;
			resultStr = null ;
			redisCommon = null ;
		}
		
		return resulBoolean ;
	}
	
}
