/**
 * 
 */
package com.connectivity.common.dao ;

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
 * @date 2020-06-18
 */
public class CommonDao
{
	private Logger logger = LogManager.getLogger( this.getClass( ) ) ;
	
	/**
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-18
	 * @param redisSetAppioDataMap
	 * @return
	 */
	public Boolean hmSetAppioData( HashMap< String , String > redisSetAppioDataMap ) {
		Boolean resulBoolean = true ;
		
		RedisCommon redisCommon = new RedisCommon( ) ;
		
		String strKey = "" ;
		String resultStr = "" ;
		
		CommUtil commUtil = new CommUtil( ) ;
		
		try {
			logger.debug( "hmSetAppioData" ) ;
			
			if( !commUtil.checkNull( redisSetAppioDataMap ) ) {
				logger.debug( "hmSetAppioData 저장" ) ;
				strKey = "MGP_APMD" ;
				
				// TODO redis now 같은거 있는지, db 저장되는 시간으로 처리
				redisSetAppioDataMap.put( "UPD_DT" , commUtil.getFormatingNowDateTime( ) ) ;
				
				// logger.debug( "redisSetAppioDataMap :: " + redisSetAppioDataMap ) ;
				resultStr = redisCommon.redisHmset( strKey , redisSetAppioDataMap ) ;
				
			}
		}
		catch( Exception e ) {
			resulBoolean = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			redisSetAppioDataMap = null ;
			strKey = null ;
			resultStr = null ;
			commUtil = null ;
			redisCommon = null ;
			logger.debug( "hmSetAppioData finally" ) ;
			
		}
		
		return resulBoolean ;
	}
	
}
