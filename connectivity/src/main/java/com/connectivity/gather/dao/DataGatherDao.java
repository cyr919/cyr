/**
 * 
 */
package com.connectivity.gather.dao ;

import java.util.HashMap ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;
import org.springframework.data.mongodb.core.MongoOperations ;

import com.connectivity.common.RedisCommon ;
import com.connectivity.config.MongodbConnection ;
import com.connectivity.utils.CommUtil ;

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
		
		RedisCommon redisCommon = new RedisCommon( ) ;
		
		String strKey = "" ;
		String resultStr = "" ;
		
		CommUtil commUtil = new CommUtil( ) ;
		
		try {
//			logger.debug( "hmSetGatherData" ) ;
			
			if( !commUtil.checkNull( redisSetDataMap ) ) {
				strKey = "MGP_SVDT" + "^" + dviceId ;
				// logger.debug( "strKey :: " + strKey ) ;
				
				// TODO redis now 같은거 있는지, db 저장되는 시간으로 처리
				redisSetDataMap.put( "INS_DT" , commUtil.getFormatingNowDateTime( ) ) ;
				
				// logger.debug( "redisSetDataMap :: " + redisSetDataMap ) ;
				resultStr = redisCommon.redisHmset( strKey , redisSetDataMap ) ;
				
			}
			
		}
		catch( Exception e ) {
			resulBoolean = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			dviceId = null ;
			redisSetDataMap = null ;
			strKey = null ;
			resultStr = null ;
			commUtil = null ;
			redisCommon = null ;
//			logger.debug( "hmSetGatherData finally" ) ;
			
		}
		
		return resulBoolean ;
	}
	
	// public Boolean hmSetGatherData( String dviceId , HashMap< String , String > redisSetDataMap ) {
	// Boolean resulBoolean = true ;
	//
	// Jedis jedis = null ;
	// JedisConnection jedisConnection = new JedisConnection( ) ;
	// // RedisCommon redisCommon = new RedisCommon( ) ;
	//
	// String strKey = "" ;
	// String resultStr = "" ;
	//
	// CommUtil commUtil = new CommUtil( ) ;
	//
	// try {
	// logger.debug( "hmSetGatherData" ) ;
	//
	// strKey = "MGP_SVDT" + "^" + dviceId ;
	// logger.debug( "strKey :: " + strKey ) ;
	//
	// // TODO redis now 같은거 있는지, db 저장되는 시간으로 처리
	// redisSetDataMap.put( "INS_DT" , commUtil.getFormatingNowDateTime( ) ) ;
	//
	// logger.debug( "redisSetDataMap :: " + redisSetDataMap ) ;
	// // resultStr = redisCommon.redisHmset( strKey , redisSetDataMap ) ;
	//
	// jedis = jedisConnection.getJedisPool( ).getResource( ) ;
	// // logger.trace( "jedis.isConnected() :: " + jedis.isConnected( ) ) ;
	// if( jedis.isConnected( ) ) {
	// resulBoolean = false ;
	// resultStr = jedis.hmset( strKey , redisSetDataMap ) ;
	// logger.debug( "resultStr :: " + resultStr ) ;
	//
	// }
	// }
	// catch( Exception e ) {
	// resulBoolean = false ;
	// logger.error( e.getMessage( ) , e ) ;
	// }
	// finally {
	// strKey = null ;
	// dviceId = null ;
	// redisSetDataMap = null ;
	// resultStr = null ;
	// commUtil = null ;
	// if( jedis != null ) {
	// jedis.close( ) ;
	// }
	// jedis = null ;
	// jedisConnection = null ;
	// logger.debug( "hmSetGatherData finally" ) ;
	//
	// }
	//
	// return resulBoolean ;
	// }
	
	public Boolean insertGatherData( HashMap< String , Object > mongodbSetDataMap ) {
		
		Boolean resulBoolean = true ;
		
		MongodbConnection mongodbConnection = new MongodbConnection( ) ;
		MongoOperations mongoOps = null ;
		
		CommUtil commUtil = new CommUtil( ) ;
		String saveDtmStr = "" ;
		
		try {
			logger.debug( "insertGatherData" ) ;
			
			if( !commUtil.checkNull( mongodbSetDataMap ) ) {
				
				saveDtmStr = commUtil.getFormatingNowDateTime( ) ;
				mongodbSetDataMap.put( "INS_DT" , saveDtmStr ) ;
				mongodbSetDataMap.put( "INS_USR" , "Connectivity" ) ;
				mongodbSetDataMap.put( "UPD_DT" , saveDtmStr ) ;
				mongodbSetDataMap.put( "UPD_USR" , "Connectivity" ) ;
				
				mongoOps = mongodbConnection.getMongoTemplate( ) ;
				mongoOps.insert( mongodbSetDataMap , "MGP_SDHS" ) ;
			}
			
		}
		catch( Exception e ) {
			resulBoolean = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			mongodbConnection = null ;
			mongoOps = null ;
			mongodbSetDataMap = null ;
			
			commUtil = null ;
			saveDtmStr = null ;
			
			logger.debug( "insertGatherData finally" ) ;
		}
		
		return resulBoolean ;
		
	}
	
}
