
package com.prototype ;

import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.Iterator ;
import java.util.List ;

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
				Iterator< ? > iteratorHashMapKeys = null ;
				String strHashMapKey = "" ;

				HashMap< String , Object > dataHashMap = new HashMap<>( ) ;

				try
				{
					jedisPool = jedisConfig.setJedisPool( ) ;
					jedis = jedisPool.getResource( ) ;

					logger.info( "jedis.isConnected() :: " + jedis.isConnected( ) ) ;
					if ( jedis.isConnected( ) )
					{
						//////////////////////////
//						if ( !CommUtil.checkNull( PropertyLoader.DEVICE_PROPERTIES_HASHMAP ) )
//						{

							iteratorHashMapKeys = PropertyLoader.DEVICE_PROPERTIES_HASHMAP.keySet( ).iterator( ) ;
							while ( iteratorHashMapKeys.hasNext( ) )
							{
								strHashMapKey = ( String ) iteratorHashMapKeys.next( ) ;

								// PropertyLoader.DEVICE_PROPERTIES_HASHMAP.get( strHashMapKey );
								logger.debug( "PropertyLoader.DEVICE_PROPERTIES_HASHMAP.get( " + strHashMapKey + " ) :: " ) ;
								logger.debug( PropertyLoader.DEVICE_PROPERTIES_HASHMAP.get( strHashMapKey ) ) ;
								logger.debug( ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::" ) ;
								////////////////////////////////////////////////////
								// 표준인덱스 처리

								List< HashMap< String , Object > > dtHashmap = new ArrayList<>( ) ;

								//////////////////////////
								// 장치 내 연산 처리

								////////////////////////////////////////////////////
							}

//						}
						//////////////////////////
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

				// Thread.sleep( 10 ) ;
				Thread.sleep( 100000 ) ;
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
