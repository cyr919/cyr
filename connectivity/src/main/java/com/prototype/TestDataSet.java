
package com.prototype ;

import java.util.HashMap ;

import org.apache.log4j.Logger ;

import com.connectivity.utils.CommUtil ;

import redis.clients.jedis.Jedis ;
import redis.clients.jedis.JedisPool ;

public class TestDataSet implements Runnable
{

	static Logger logger = Logger.getLogger( TestDataSet.class ) ;

	private boolean isDemonLive = false ;

	public TestDataSet( ) {
	}

	@Override
	public void run( )
	{
		setDemonLive( true ) ;

		JedisConfig jedisConfig = new JedisConfig( ) ;
		String[ ] dvStrArr = { "PCS01" , "PCS02" , "ESS" } ;

		try
		{
			logger.debug( "isDemonLive :: " + isDemonLive ) ;
			logger.debug( "PropertyLoader.IS_ALL_DEMON_LIVE :: " + PropertyLoader.IS_ALL_DEMON_LIVE ) ;

			while ( isDemonLive && PropertyLoader.IS_ALL_DEMON_LIVE )
			{
				logger.debug( "================================================================" ) ;
				logger.debug( "TestDataSet :: while :: start" ) ;
				// logger.debug( "isDemonLive :: " + isDemonLive ) ;
				// logger.debug( "PropertyLoader.IS_ALL_DEMON_LIVE :: " + PropertyLoader.IS_ALL_DEMON_LIVE ) ;

				/////////////////////////////////////////////

				JedisPool jedisPool = null ;
				Jedis jedis = null ;
				int intVal = 0 ;
				int intCalVal = 0 ;
				HashMap< String , Object > tempHashMap = new HashMap<>( ) ;
				int i = 0 ;
				try
				{
					jedisPool = jedisConfig.setJedisPool( ) ;
					jedis = jedisPool.getResource( ) ;

					logger.info( "jedis.isConnected() :: " + jedis.isConnected( ) ) ;
					if ( jedis.isConnected( ) )
					{

						for ( i = 0 ; i < dvStrArr.length ; i++ )
						{
							logger.debug( "===============================" ) ;

							tempHashMap = new HashMap<>( ) ;

							intVal = CommUtil.getRandomInt( 100 , 10 ) ;
							tempHashMap.put( "BV" , intVal ) ;
							jedis.hset( dvStrArr[ i ] , "BV" , ( tempHashMap.get( "BV" ) + "" ) ) ;

							intVal = CommUtil.getRandomInt( 10 , 20 ) ;
							tempHashMap.put( "BC" , intVal ) ;
							jedis.hset( dvStrArr[ i ] , "BC" , ( tempHashMap.get( "BC" ) + "" ) ) ;

							intVal = CommUtil.getRandomInt( 10 , 50 ) ;
							tempHashMap.put( "BF" , intVal ) ;
							jedis.hset( dvStrArr[ i ] , "BF" , ( tempHashMap.get( "BF" ) + "" ) ) ;

							intVal = CommUtil.getRandomInt( 100 , 50 ) ;
							tempHashMap.put( "DCBA" , intVal ) ;
							jedis.hset( dvStrArr[ i ] , "DCBA" , ( tempHashMap.get( "DCBA" ) + "" ) ) ;

							intCalVal = Integer.parseInt( tempHashMap.get( "BV" ) + "" ) * Integer.parseInt( tempHashMap.get( "BC" ) + "" ) ;

							tempHashMap.put( "BP" , intCalVal ) ;
							jedis.hset( dvStrArr[ i ] , "BP" , ( tempHashMap.get( "BP" ) + "" ) ) ;

							logger.debug( "tempHashMap :: " + tempHashMap ) ;

							logger.debug( "jedis.hget( " + dvStrArr[ i ] + " , \"BV\" ) :: " + jedis.hget( dvStrArr[ i ] , "BV" ) ) ;
							logger.debug( "jedis.hget( " + dvStrArr[ i ] + " , \"BC\" ) :: " + jedis.hget( dvStrArr[ i ] , "BC" ) ) ;
							logger.debug( "jedis.hget( " + dvStrArr[ i ] + " , \"BF\" ) :: " + jedis.hget( dvStrArr[ i ] , "BF" ) ) ;
							logger.debug( "jedis.hget( " + dvStrArr[ i ] + " , \"DCBA\" ) :: " + jedis.hget( dvStrArr[ i ] , "DCBA" ) ) ;
							logger.debug( "jedis.hget( " + dvStrArr[ i ] + " , \"BP\" ) :: " + jedis.hget( dvStrArr[ i ] , "BP" ) ) ;

						}

					}

				}
				finally
				{
					intVal = 0 ;
					intCalVal = 0 ;
					tempHashMap = null ;
					i = 0 ;

					if ( jedis != null )
					{
						jedis.close( ) ;
					}
					jedisPool.close( ) ;
					jedis = null ;
					jedisPool = null ;
				}

				/////////////////////////////////////////////

				Thread.sleep( 10 ) ;

				logger.debug( "TestDataSet :: while :: end" ) ;
			}

		}
		catch ( Exception e )
		{
			e.printStackTrace( ) ;
			logger.error( e ) ;
		}
		finally
		{
			setDemonLive( false ) ;
			jedisConfig = null ;
			dvStrArr = null ;
		}

	}

	public boolean isDemonLive( )
	{
		return isDemonLive ;
	}

	public void setDemonLive( boolean isDemonLive )
	{
		this.isDemonLive = isDemonLive ;
	}
}
