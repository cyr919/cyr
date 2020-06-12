/**
 * 
 */
package com.connectivity.manage.dao ;

import java.util.HashMap ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.common.RedisCommon ;
import com.connectivity.utils.CommUtil ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-05-22
 */
public class ConditionReportDao
{
	private Logger logger = LogManager.getLogger( this.getClass( ) ) ;
	
	public Boolean hmSetCondition( String redisSetKey , HashMap< String , String > redisSetDataMap ) {
		Boolean resulBoolean = true ;
		
		RedisCommon redisCommon = new RedisCommon( ) ;
		String resultStr = "" ;
		
		String strDateTime = "" ;
		CommUtil commUtil = new CommUtil( ) ;
		try {
			
			strDateTime = commUtil.getFormatingNowDateTime( ) ;
			redisSetDataMap.put( "LSDT" , strDateTime ) ;
			
			logger.debug( "redisSetKey :: " + redisSetKey ) ;
			resultStr = redisCommon.redisHmset( redisSetKey , redisSetDataMap ) ;
			logger.debug( "resultStr :: " + resultStr ) ;
		}
		catch( Exception e ) {
			resulBoolean = false ;
		}
		finally {
			redisSetKey = null ;
			redisSetDataMap = null ;
			redisCommon = null ;
			resultStr = null ;
			strDateTime = null ;
			commUtil = null ;
		}
		
		return resulBoolean ;
	}
}
