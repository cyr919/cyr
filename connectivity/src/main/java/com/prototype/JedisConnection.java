
package com.prototype ;

import java.util.HashMap ;

import org.apache.log4j.Logger ;

import redis.clients.jedis.Jedis ;
import redis.clients.jedis.JedisPool ;
import redis.clients.jedis.JedisPoolConfig ;

public class JedisConnection
{
	
	static Logger logger = Logger.getLogger( JedisConnection.class ) ;
	
	private static JedisPool jedisPool ;
	private static JedisPoolConfig jedisPoolConfig ;
	
	public static JedisPool getJedisPool( ) {
		
		if( null == jedisPool ) {
			setJedisPool( ) ;
		}
		
		return jedisPool ;
	}
	
	public static JedisPoolConfig getPoolConfig( ) {
		if( jedisPoolConfig == null ) {
			JedisPoolConfig poolConfig = new JedisPoolConfig( ) ;
			
			// Each thread trying to access Redis needs its own Jedis instance from the pool.
			// Using too small a value here can lead to performance problems, too big and you have wasted resources.
			int maxConnections = 200 ;
			poolConfig.setMaxTotal( maxConnections ) ;
			poolConfig.setMaxIdle( maxConnections ) ;
			
			// Using "false" here will make it easier to debug when your maxTotal/minIdle/etc settings need adjusting.
			// Setting it to "true" will result better behavior when unexpected load hits in production
			poolConfig.setBlockWhenExhausted( true ) ;
			
			// How long to wait before throwing when pool is exhausted
			// poolConfig.setMaxWaitMillis(operationTimeout);
			
			// This controls the number of connections that should be maintained for bursts of load.
			// Increase this value when you see pool.getResource() taking a long time to complete under burst scenarios
			poolConfig.setMinIdle( 50 ) ;
			
			jedisPoolConfig = poolConfig ;
		}
		
		return jedisPoolConfig ;
	}
	
	public static JedisPool setJedisPool( ) {
		
		JedisPoolConfig jedisPoolConfig = null ;
		
		try {
			jedisPoolConfig = getPoolConfig( ) ;
			// jedisPool = new JedisPool( jedisPoolConfig , "192.168.43.62" , 6379 , 3000 ) ;
			// jedisPool = new JedisPool( jedisPoolConfig , "192.168.43.62" , 6379 , 3000 , "1q2w3e4r5t!@#$%" ) ;
			// jedisPool = new JedisPool( jedisPoolConfig , "192.168.56.105" , 6379 , 3000 , "1q2w3e4r5t!@#$%" ) ;
			jedisPool = new JedisPool( jedisPoolConfig , "192.168.56.105" , 6379 , 3000 ) ;
			
			// jedisPool = new JedisPool( jedisPoolConfig , "192.168.56.104" , 6379 , 3000 , "1q2w3e4r5t!@#$%" ) ;
			// jedisPool = new JedisPool( jedisPoolConfig , "127.0.0.1" , 6379 , 3000 , "1q2w3e4r5t!@#$%" ) ;
			// Jedis풀 생성(JedisPoolConfig, host, port, timeout, password)
			
		} finally {
			jedisPoolConfig = null ;
		}
		
		return jedisPool ;
	}
	
	public static String getPoolCurrentUsage( ) {
		JedisPool jedisPool = getJedisPool( ) ;
		JedisPoolConfig poolConfig = getPoolConfig( ) ;
		
		int active = jedisPool.getNumActive( ) ;
		int idle = jedisPool.getNumIdle( ) ;
		int total = active + idle ;
		String log = String.format( "JedisPool: Active=%d, Idle=%d, Waiters=%d, total=%d, maxTotal=%d, minIdle=%d, maxIdle=%d" , active , idle , jedisPool.getNumWaiters( ) , total , poolConfig.getMaxTotal( ) , poolConfig.getMinIdle( ) , poolConfig.getMaxIdle( ) ) ;
		
		return log ;
	}
	
	public static void main( String[ ] args ) {
		logger.info( "start :: " ) ;
		
		Jedis jedis = null ;
		
		int intVal = 0 ;
		int intCalVal = 0 ;
		HashMap< String , Object > tempHashMap = new HashMap<>( ) ;
		
		try {
			jedis = getJedisPool( ).getResource( ) ;
			
			logger.info( "jedis.isConnected() :: " + jedis.isConnected( ) ) ;
			if( jedis.isConnected( ) ) {
				// jedis.set( "deviceKey01" , "ESS01" ) ;
				// jedis.set( "deviceKey02" , "ESS02" ) ;
				// jedis.set( "deviceKey03" , "ESS03" ) ;
				// jedis.set( "deviceKey04" , "ESS04" ) ;
				
				logger.debug( jedis.get( "deviceKey01" ) ) ;
				logger.debug( jedis.get( "deviceKey02" ) ) ;
				logger.debug( jedis.get( "deviceKey03" ) ) ;
				logger.debug( jedis.get( "deviceKey04" ) ) ;
				//
				// intVal = CommUtil.getRandomInt( 100 , 0 ) ;
				// tempHashMap.put( "BV" , intVal ) ;
				// jedis.hset( "PCS01" , "BV" , ( tempHashMap.get( "BV" ) + "" ) ) ;
				//
				// intVal = CommUtil.getRandomInt( 10 , 20 ) ;
				// tempHashMap.put( "BC" , intVal ) ;
				// jedis.hset( "PCS01" , "BC" , ( tempHashMap.get( "BC" ) + "" ) ) ;
				//
				// intVal = CommUtil.getRandomInt( 10 , 50 ) ;
				// tempHashMap.put( "BF" , intVal ) ;
				// jedis.hset( "PCS01" , "BF" , ( tempHashMap.get( "BF" ) + "" ) ) ;
				//
				// intVal = CommUtil.getRandomInt( 10 , 150 ) ;
				// tempHashMap.put( "DCBA" , intVal ) ;
				// jedis.hset( "PCS01" , "DCBA" , ( tempHashMap.get( "DCBA" ) + "" ) ) ;
				//
				// logger.debug( "tempHashMap :: " + tempHashMap ) ;
				//
				// intCalVal = Integer.parseInt( tempHashMap.get( "BV" ) + "" ) * Integer.parseInt( tempHashMap.get( "BC" ) + "" ) ;
				//
				// tempHashMap.put( "BP" , intCalVal ) ;
				// jedis.hset( "PCS01" , "BP" , ( tempHashMap.get( "BP" ) + "" ) ) ;
				
				logger.debug( jedis.hget( "PCS01" , "BV" ) ) ;
				logger.debug( jedis.hget( "PCS01" , "BC" ) ) ;
				logger.debug( jedis.hget( "PCS01" , "BF" ) ) ;
				logger.debug( jedis.hget( "PCS01" , "DCBA" ) ) ;
				logger.debug( jedis.hget( "PCS01" , "BP" ) ) ;
				
			}
			
			logger.debug( getPoolCurrentUsage( ) ) ;
			
		} finally {
			
			intVal = 0 ;
			intCalVal = 0 ;
			tempHashMap = null ;
			
			if( jedis != null ) {
				jedis.close( ) ;
			}
			jedis = null ;
		}
		logger.info( "end :: " ) ;
		
		logger.debug( getPoolCurrentUsage( ) ) ;
		
	}
	
}
