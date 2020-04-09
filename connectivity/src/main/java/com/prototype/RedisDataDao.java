/**
 * 
 */
package com.prototype ;

import org.apache.log4j.Logger ;

import redis.clients.jedis.Jedis ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-04-08
 */
public class RedisDataDao
{
	static Logger logger = Logger.getLogger( RedisDataDao.class ) ;
	
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
		
		RedisDataDao exe = new RedisDataDao( ) ;
		
		try {
			String resulString = exe.redisHget( "PCS01" , "BP" ) ;
			
			
			logger.info( resulString );
			
			Long resultLong = exe.redisHset( "PCS01" , "BP" , "100.23" ) ;
			 resulString = exe.redisHget( "PCS01" , "BP" ) ;
							
			logger.info( resultLong );
			logger.info( resulString );
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e );
		}
		
		
		
	}
	
	/**
	 * 
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
			jedis = JedisConnection.getJedisPool( ).getResource( ) ;
			
			logger.trace( "jedis.isConnected() :: " + jedis.isConnected( ) ) ;
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
	 * 
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
			jedis = JedisConnection.getJedisPool( ).getResource( ) ;
			
			logger.trace( "jedis.isConnected() :: " + jedis.isConnected( ) ) ;
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
	
}
