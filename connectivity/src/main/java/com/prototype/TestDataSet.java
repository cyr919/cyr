
package com.prototype ;

import java.math.BigDecimal ;
import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.Iterator ;
import java.util.List ;

import org.apache.log4j.Logger ;

import com.connectivity.utils.CommUtil ;

import redis.clients.jedis.Jedis ;

public class TestDataSet implements Runnable
{
	
	static Logger logger = Logger.getLogger( TestDataSet.class ) ;
	
	private boolean isDemonLive = false ;
	
	public TestDataSet( ) {
	}
	
	@Override
	public void run( ) {
		setDemonLive( true ) ;
		
		String[ ] dvStrArr = { "PCS01" , "PCS02" , "ESS" } ;
		
		try {
			logger.debug( "isDemonLive :: " + isDemonLive ) ;
			logger.debug( "PropertyLoader.IS_ALL_DEMON_LIVE :: " + PropertyLoader.IS_ALL_DEMON_LIVE ) ;
			
			while( isDemonLive && PropertyLoader.IS_ALL_DEMON_LIVE ) {
				logger.debug( "================================================================" ) ;
				logger.debug( "TestDataSet :: while :: start" ) ;
				// logger.debug( "isDemonLive :: " + isDemonLive ) ;
				// logger.debug( "PropertyLoader.IS_ALL_DEMON_LIVE :: " + PropertyLoader.IS_ALL_DEMON_LIVE ) ;
				
				/////////////////////////////////////////////
				
				Jedis jedis = null ;
				int intVal = 0 ;
				int intCalVal = 0 ;
				
				BigDecimal tempBDVal = new BigDecimal( "0" ) ;
				
				HashMap< String , Object > tempHashMap = new HashMap<>( ) ;
				int i = 0 ;
				Iterator< ? > iteratorHashMapKeys = null ;
				String strHashMapKey = "" ;
				
				HashMap< String , Object > dataHashMap = new HashMap<>( ) ;
				HashMap< String , Object > dataCalculHashMap = new HashMap<>( ) ;
				
				List< HashMap< String , Object > > dtList = new ArrayList<>( ) ;
				List< HashMap< String , Object > > calculList = new ArrayList<>( ) ;
				int j = 0 ;
				
				DataGather dataGather = new DataGather( ) ;
				
				try {
					jedis = JedisConnection.getJedisPool( ).getResource( ) ;
					
					logger.info( "jedis.isConnected() :: " + jedis.isConnected( ) ) ;
					if( jedis.isConnected( ) ) {
						//////////////////////////
						// if ( !CommUtil.checkNull( PropertyLoader.DEVICE_PROPERTIES_HASHMAP ) )
						// {
						
						iteratorHashMapKeys = PropertyLoader.DEVICE_PROPERTIES_HASHMAP.keySet( ).iterator( ) ;
						while( iteratorHashMapKeys.hasNext( ) ) {
							
							logger.debug( "PropertyLoader.DEVICE_PROPERTIES_HASHMAP :: " ) ;
							logger.debug( PropertyLoader.DEVICE_PROPERTIES_HASHMAP ) ;
							logger.debug( "PropertyLoader.DEVICE_PROPERTIES_HASHMAP :: " ) ;
							
							strHashMapKey = ( String ) iteratorHashMapKeys.next( ) ;
							
							logger.debug( "PropertyLoader.DEVICE_PROPERTIES_HASHMAP.get( " + strHashMapKey + " ) :: " ) ;
							logger.debug( PropertyLoader.DEVICE_PROPERTIES_HASHMAP.get( strHashMapKey ) ) ;
							logger.debug( ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::" ) ;
							
							dataHashMap = new HashMap< String , Object >( ) ;
							
							////////////////////////////////////////////////////
							// 표준인덱스 처리
							
							// 값 참고로 변경
							dtList = ( List< HashMap< String , Object > > ) PropertyLoader.DEVICE_PROPERTIES_HASHMAP.get( strHashMapKey ).get( "DT_MDL" ) ;
							
							logger.debug( "dtList :: " + dtList ) ;
							
							for( j = 0 ; j < dtList.size( ) ; j++ ) {
								
								intVal = CommUtil.getRandomInt( 100 , 10 ) ;
								logger.debug( "dtList.get( j ).get( \"STD_IDX\" ) :: " + dtList.get( j ).get( "STD_IDX" ) ) ;
								tempBDVal = dataGather.applyScaleFactor( intVal , dtList.get( j ).get( "SC_FCT" ) ) ;
								
								dataHashMap.put( ( dtList.get( j ).get( "STD_IDX" ) + "" ) , tempBDVal ) ;
								
							}
							
							logger.debug( ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::" ) ;
							logger.debug( "표준 인덱스 처리 후 dataHashMap :: " + dataHashMap ) ;
							
							//////////////////////////
							// 장치 내 연산 처리
							calculList = ( List< HashMap< String , Object > > ) PropertyLoader.DEVICE_PROPERTIES_HASHMAP.get( strHashMapKey ).get( "CAL_INF" ) ;
							
							logger.debug( "calculList :: " + calculList ) ;
							
							dataCalculHashMap = dataGather.getCalculatingGatherData( dataHashMap , calculList ) ;
							
							logger.debug( "장치 내 연산 처리 후 dataCalculHashMap :: " + dataCalculHashMap ) ;
							
							logger.debug( ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::" ) ;

							
							//////////////////////////
							// 메모리(redis) 저장
							
							
							
							//////////////////////////
							// DB(mongodb) 저장
							
							
							////////////////////////////////////////////////////
						}
						
						// }
						//////////////////////////
					}
					
				} finally {
					intVal = 0 ;
					intCalVal = 0 ;
					tempHashMap = null ;
					i = 0 ;
					
					dtList = null ;
					calculList = null ;
					
					if( jedis != null ) {
						jedis.close( ) ;
					}
					jedis = null ;
				}
				
				/////////////////////////////////////////////
				logger.debug( ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::" ) ;
				logger.debug( "PropertyLoader.DEVICE_PROPERTIES_HASHMAP :: " ) ;
				logger.debug( PropertyLoader.DEVICE_PROPERTIES_HASHMAP ) ;
				logger.debug( "PropertyLoader.DEVICE_PROPERTIES_HASHMAP :: " ) ;
				
				// Thread.sleep( 10 ) ;
				Thread.sleep( 10000 ) ;
				
				logger.debug( JedisConnection.getPoolCurrentUsage( ) ) ;
				
				logger.debug( "TestDataSet :: while :: end" ) ;
			}
			
		} catch( Exception e ) {
			e.printStackTrace( ) ;
			logger.error( e.getMessage( ) , e ) ;
		} finally {
			setDemonLive( false ) ;
			dvStrArr = null ;
		}
		
	}
	
	public boolean isDemonLive( ) {
		return isDemonLive ;
	}
	
	public void setDemonLive( boolean isDemonLive ) {
		this.isDemonLive = isDemonLive ;
	}
}
