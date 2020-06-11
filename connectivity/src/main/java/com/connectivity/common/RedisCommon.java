/**
 * 
 */
package com.connectivity.common ;

import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.List ;
import java.util.Map ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.config.JedisConnection ;

import redis.clients.jedis.Jedis ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-04-08
 */
public class RedisCommon extends JedisConnection
{
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	// private Logger logger = LogManager.getLogger( RedisCommon.class ) ;
	private static Logger logger = LogManager.getLogger( RedisCommon.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	/**
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-04-08
	 * @param args
	 */
	public static void main( String[ ] args ) {
		// TODO Auto-generated method stub
		
		CommonProperties commonProperties = new CommonProperties( ) ;
		RedisCommon exe = new RedisCommon( ) ;
		
		try {
			commonProperties.setProperties( ) ;
			// String resulString = exe.redisHget( "PCS01" , "BP" ) ;
			
			// logger.info( resulString ) ;
			
			// Long resultLong = exe.redisHset( "PCS01" , "BP" , "100.23" ) ;
			// resulString = exe.redisHget( "PCS01" , "BP" ) ;
			
			// logger.info( resultLong ) ;
			// logger.info( resulString ) ;
			
			Map< String , String > resultMap = exe.redisHgetAll( "MGP_SVDT^stdv0002" ) ;
			logger.info( "resultMap :: " + resultMap ) ;
			
		}
		catch( Exception e ) {
			e.printStackTrace( ) ;
			// logger.error( e.getMessage( ) , e ) ;
		}
		
	}
	
	/**
	 * <pre>
	 * Set the specified hash field to the specified value. 
	 * If key does not exist, a new key holding a hash is created.
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-04-08
	 * @param key
	 * @param field
	 * @param value
	 * @return If the field already exists, and the HSET just produced an update of the value, 0 isreturned, otherwise if a new field is created 1 is returned.
	 * @throws Exception
	 */
	public Long redisHset( String key , String field , String value ) throws Exception {
		Long resultLong = 0L ;
		Jedis jedis = null ;
		
		try {
			jedis = getJedisPool( ).getResource( ) ;
			
			// logger.trace( "jedis.isConnected() :: " + jedis.isConnected( ) ) ;
			if( jedis.isConnected( ) ) {
				resultLong = jedis.hset( key , field , value ) ;
			}
			
		}
		finally {
			
			key = null ;
			field = null ;
			value = null ;
			
			if( jedis != null ) {
				jedis.close( ) ;
			}
			jedis = null ;
			
		}
		
		return resultLong ;
	}
	
	/**
	 * <pre>
	 * If key holds a hash, retrieve the value associated to the specified field. 
	 * If the field is not found or the key does not exist, a special 'nil' value is returned.
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-04-08
	 * @param key
	 * @param field
	 * @return
	 * @throws Exception
	 */
	public String redisHget( String key , String field ) throws Exception {
		
		String resultStr = "" ;
		Jedis jedis = null ;
		
		try {
			jedis = getJedisPool( ).getResource( ) ;
			
			// logger.trace( "jedis.isConnected() :: " + jedis.isConnected( ) ) ;
			if( jedis.isConnected( ) ) {
				resultStr = jedis.hget( key , field ) ;
			}
		}
		finally {
			key = null ;
			field = null ;
			
			if( jedis != null ) {
				jedis.close( ) ;
			}
			jedis = null ;
			
		}
		
		return resultStr ;
		
	}
	
	/**
	 * <pre>
	 * Set the respective fields to the respective values. HMSET replaces old values with new values.
	 * If key does not exist, a new key holding a hash is created.
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-04-09
	 * @param key
	 * @param hash
	 * @return Return OK or Exception if hash is empty
	 * @throws Exception
	 */
	public String redisHmset( String key , Map< String , String > hash ) throws Exception {
		
		String resultStr = "" ;
		Jedis jedis = null ;
		
		try {
			jedis = getJedisPool( ).getResource( ) ;
			
			// logger.trace( "jedis.isConnected() :: " + jedis.isConnected( ) ) ;
			if( jedis.isConnected( ) ) {
				resultStr = jedis.hmset( key , hash ) ;
				logger.trace( "resultStr :: " + resultStr ) ;
				
			}
		}
		finally {
			key = null ;
			hash = null ;
			
			if( jedis != null ) {
				jedis.close( ) ;
			}
			jedis = null ;
			
		}
		
		return resultStr ;
		
	}
	
	public List< String > redisHmget( String key , String ... fields ) throws Exception {
		
		List< String > resultList = new ArrayList< String >( ) ;
		Jedis jedis = null ;
		
		try {
			jedis = getJedisPool( ).getResource( ) ;
			
			// logger.trace( "jedis.isConnected() :: " + jedis.isConnected( ) ) ;
			if( jedis.isConnected( ) ) {
				resultList = jedis.hmget( key , fields ) ;
			}
		}
		finally {
			key = null ;
			fields = null ;
			
			if( jedis != null ) {
				jedis.close( ) ;
			}
			jedis = null ;
		}
		
		return resultList ;
		
	}
	
	public Map< String , String > redisHgetAll( String key ) throws Exception {
		
		// List< String > resultList = new ArrayList< String >( ) ;
		Map< String , String > resultMap = new HashMap< String , String >( ) ;
		
		Jedis jedis = null ;
		
		try {
			jedis = getJedisPool( ).getResource( ) ;
			
			// logger.trace( "jedis.isConnected() :: " + jedis.isConnected( ) ) ;
			if( jedis.isConnected( ) ) {
				resultMap = jedis.hgetAll( key ) ;
			}
		}
		finally {
			key = null ;
			
			if( jedis != null ) {
				jedis.close( ) ;
			}
			jedis = null ;
		}
		
		return resultMap ;
		
	}
}
