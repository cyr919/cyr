/**
 * 
 */
package com.connectivity.gather.dao ;

import java.util.HashMap ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;
import org.springframework.data.mongodb.core.MongoOperations ;

import com.connectivity.config.JedisConnection ;
import com.connectivity.config.MongodbConnection ;

import redis.clients.jedis.Jedis ;

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
		
		Jedis jedis = null ;
		JedisConnection jedisConnection = new JedisConnection( ) ;
		
		String strKey = "" ;
		String resultStr = "" ;
		
		try {
			logger.debug( "hmSetGatherData" ) ;
			
			strKey = "MGP_SVDT" + "^" + dviceId ;
			logger.debug( "strKey :: " + strKey ) ;
			
			// resultStr = redisCommon.redisHmset( strKey , redisSetDataMap ) ;
			
			jedis = jedisConnection.getJedisPool( ).getResource( ) ;
			// logger.trace( "jedis.isConnected() :: " + jedis.isConnected( ) ) ;
			if( jedis.isConnected( ) ) {
				resulBoolean = false ;
				resultStr = jedis.hmset( strKey , redisSetDataMap ) ;
				logger.debug( "resultStr :: " + resultStr ) ;
				
			}
		}
		catch( Exception e ) {
			resulBoolean = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strKey = null ;
			dviceId = null ;
			redisSetDataMap = null ;
			resultStr = null ;
			
			if( jedis != null ) {
				jedis.close( ) ;
			}
			jedis = null ;
			jedisConnection = null ;
			logger.debug( "hmSetGatherData finally" ) ;
			
		}
		
		return resulBoolean ;
	}
	
	public Boolean insertGatherData( HashMap< String , Object > mongodbSetDataMap ) {
		
		Boolean resulBoolean = true ;
		
		MongodbConnection mongodbConnection = new MongodbConnection( ) ;
		MongoOperations mongoOps = null ;
		
		try {
			logger.debug( "insertGatherData" ) ;
			
			mongoOps = mongodbConnection.getMongoTemplate( ) ;
			mongoOps.insert( mongodbSetDataMap , "MGP_SDHS" ) ;
			
		}
		catch( Exception e ) {
			resulBoolean = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			mongodbConnection = null ;
			mongoOps = null ;
			mongodbSetDataMap = null ;
			logger.debug( "insertGatherData finally" ) ;
			
		}
		
		return resulBoolean ;
		
	}
	
}
