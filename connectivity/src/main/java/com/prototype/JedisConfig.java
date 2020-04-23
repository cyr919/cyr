
package com.prototype ;

import java.util.HashMap ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import redis.clients.jedis.Jedis ;
import redis.clients.jedis.JedisPool ;
import redis.clients.jedis.JedisPoolConfig ;

public class JedisConfig
{

	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private static Logger logger = LogManager.getLogger( JedisConfig.class ) ;
	// Logger logger = LogManager.getLogger( ) ;

	public static void main( String[ ] args )
	{
		logger.info( "start :: " ) ;
		JedisConfig exe = new JedisConfig( ) ;

		JedisPool jedisPool = null ;
		Jedis jedis = null ;

		int intVal = 0 ;
		int intCalVal = 0 ;
		HashMap< String , Object > tempHashMap = new HashMap<>( ) ;

		try
		{
			jedisPool = exe.setJedisPool( ) ;
			jedis = jedisPool.getResource( ) ;

			logger.info( "jedis.isConnected() :: " + jedis.isConnected( ) ) ;
			if ( jedis.isConnected( ) )
			{
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

		}
		finally
		{

			intVal = 0 ;
			intCalVal = 0 ;
			tempHashMap = null ;

			if ( jedis != null )
			{
				jedis.close( ) ;
			}
			jedisPool.close( ) ;
			exe = null ;
			jedis = null ;
			jedisPool = null ;
		}
		logger.info( "end :: " ) ;

	}

	public JedisPool setJedisPool( )
	{

		JedisPoolConfig jedisPoolConfig = null ;
		JedisPool jedisPool = null ;

		try
		{
			jedisPoolConfig = new JedisPoolConfig( ) ;
			jedisPool = new JedisPool( jedisPoolConfig , "192.168.43.62" , 6379 , 3000 ) ;
			// jedisPool = new JedisPool( jedisPoolConfig , "192.168.43.62" , 6379 , 3000 , "1q2w3e4r5t!@#$%" ) ;
			// jedisPool = new JedisPool( jedisPoolConfig , "192.168.56.105" , 6379 , 3000 , "1q2w3e4r5t!@#$%" ) ;
			// jedisPool = new JedisPool( jedisPoolConfig , "192.168.56.105" , 6379 , 3000 ) ;

			// jedisPool = new JedisPool( jedisPoolConfig , "192.168.56.104" , 6379 , 3000 , "1q2w3e4r5t!@#$%" ) ;
			// jedisPool = new JedisPool( jedisPoolConfig , "127.0.0.1" , 6379 , 3000 , "1q2w3e4r5t!@#$%" ) ;
			// Jedis풀 생성(JedisPoolConfig, host, port, timeout, password)

		}
		finally
		{
			jedisPoolConfig = null ;
		}

		return jedisPool ;
	}

}
