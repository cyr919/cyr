/**
 * 
 */
package com.connectivity.calculate.dao ;

import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.Map ;

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
 * @date 2020-06-11
 */
public class BetweenDevicesCalculateDao
{
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private Logger logger = LogManager.getLogger( this.getClass( ) ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	/**
	 * <pre>
	 * 설치디바이스의 모든 데이터 가져오기
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-22
	 * @param stdvInfMap
	 * @return
	 */
	public HashMap< String , Map< String , String > > getAllDevicesGatherData( Map< String , Map< String , Object > > stdvInfMap ) {
		HashMap< String , Map< String , String > > resultMap = new HashMap< String , Map< String , String > >( ) ;
		
		Map< String , String > dvGthrDtMap = new HashMap< String , String >( ) ;
		
		RedisCommon redisCommon = new RedisCommon( ) ;
		String hashKeyStr = "" ;
		CommUtil commUtil = new CommUtil( ) ;
		
		try {
			if( !commUtil.checkNull( stdvInfMap ) ) {
				
				for( String keyStr : stdvInfMap.keySet( ) ) {
					// logger.debug( "keyStr :: " + keyStr ) ;
					hashKeyStr = "MGP_SVDT^" + keyStr ;
					
					dvGthrDtMap = redisCommon.redisHgetAll( hashKeyStr ) ;
					// logger.debug( "hashKeyStr :: " + hashKeyStr ) ;
					// logger.debug( "dvGthrDtMap :: " + dvGthrDtMap ) ;
					
					resultMap.put( keyStr , dvGthrDtMap ) ;
				}
			}
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			stdvInfMap = null ;
			dvGthrDtMap = null ;
			redisCommon = null ;
			hashKeyStr = null ;
		}
		
		return resultMap ;
	}
	
	/**
	 * <pre>
	 * 장치간 연산 데이터 redis 저장
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-18
	 * @param mgpKeyStr
	 * @param idStr
	 * @param valStr
	 * @param qcStr
	 * @return
	 */
	public Boolean hmSetBtwnDvCalculData( String mgpKeyStr , String idStr , String valStr , String qcStr ) {
		Boolean resulBoolean = true ;
		
		String strKey = "" ;
		String resultStr = "" ;
		
		HashMap< String , String > redisSetDataMap = new HashMap< String , String >( ) ;
		
		RedisCommon redisCommon = new RedisCommon( ) ;
		CommUtil commUtil = new CommUtil( ) ;
		
		try {
			// logger.debug( "hmSetBtwnDvCalculData" ) ;
			
			strKey = "MGP_CLRT" + "^" + mgpKeyStr ;
			// logger.debug( "strKey :: " + strKey ) ;
			
			redisSetDataMap.put( "ID" , idStr ) ;
			redisSetDataMap.put( "V" , valStr ) ;
			redisSetDataMap.put( "Q" , qcStr ) ;
			redisSetDataMap.put( "INS_DT" , commUtil.getFormatingNowDateTime( ) ) ;
			
			resultStr = redisCommon.redisHmset( strKey , redisSetDataMap ) ;
		}
		catch( Exception e ) {
			resulBoolean = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			mgpKeyStr = null ;
			idStr = null ;
			valStr = null ;
			qcStr = null ;
			
			strKey = null ;
			resultStr = null ;
			redisSetDataMap = null ;
			redisCommon = null ;
			commUtil = null ;
			
			// logger.debug( "hmSetBtwnDvCalculData finally" ) ;
		}
		
		return resulBoolean ;
	}
	
	/**
	 * <pre>
	 * 장치간 연산 데이터 redis 저장
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-22
	 * @param redisDataList
	 * @return
	 */
	public Boolean hmSetBtwnDvCalculData( ArrayList< HashMap< String , String > > redisDataList ) {
		Boolean resulBoolean = true ;
		
		String strKey = "" ;
		String resultStr = "" ;
		
		HashMap< String , String > redisSetDataMap = new HashMap< String , String >( ) ;
		
		RedisCommon redisCommon = new RedisCommon( ) ;
		CommUtil commUtil = new CommUtil( ) ;
		
		try {
			logger.debug( "hmSetBtwnDvCalculData" ) ;
			
			if( !commUtil.checkNull( redisDataList ) ) {
				for( int i = 0 ; i < redisDataList.size( ) ; i++ ) {
					
					// logger.debug( "hmSetBtwnDvCalculData" ) ;
					
					strKey = "MGP_CLRT" + "^" + redisDataList.get( i ).get( "MGP_KEY" ) ;
					// logger.debug( "strKey :: " + strKey ) ;
					
					redisSetDataMap = new HashMap< String , String >( ) ;
					redisSetDataMap.put( "ID" , redisDataList.get( i ).get( "ID" ) ) ;
					redisSetDataMap.put( "V" , redisDataList.get( i ).get( "V" ) ) ;
					redisSetDataMap.put( "Q" , redisDataList.get( i ).get( "Q" ) ) ;
					redisSetDataMap.put( "INS_DT" , commUtil.getFormatingNowDateTime( ) ) ;
					
					resultStr = redisCommon.redisHmset( strKey , redisSetDataMap ) ;
				}
			}
			
		}
		catch( Exception e ) {
			resulBoolean = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			redisDataList = null ;
			
			strKey = null ;
			resultStr = null ;
			redisSetDataMap = null ;
			redisCommon = null ;
			commUtil = null ;
			
			// logger.debug( "hmSetBtwnDvCalculData finally" ) ;
		}
		
		return resulBoolean ;
	}
	
	/**
	 * <pre>
	 * 장치간연산 mongodb 저장
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-11
	 * @param idStr 배치 시작시간
	 * @param resultRstDataMap
	 * @return
	 */
	public Boolean insertBtwnDvCalculData( String idStr , HashMap< String , Object > resultRstDataMap , String strSiteSmlt , String strSiteSmltUsr ) {
		
		Boolean resulBoolean = true ;
		
		HashMap< String , Object > mongodbSetDataMap = new HashMap< String , Object >( ) ;
		
		MongodbConnection mongodbConnection = new MongodbConnection( ) ;
		MongoOperations mongoOps = null ;
		
		CommUtil commUtil = new CommUtil( ) ;
		String saveDtmStr = "" ;
		
		try {
			logger.debug( "insertBtwnDvCalculData" ) ;
			
			mongodbSetDataMap.put( "_id" , idStr ) ;
			mongodbSetDataMap.put( "RST" , resultRstDataMap ) ;
			
			// 시뮬레이션 모드 정보 추가
			if( "Y".equals( strSiteSmlt ) ) {
				mongodbSetDataMap.put( "SMLT" , "Y" ) ;
				mongodbSetDataMap.put( "SMLT_USR" , strSiteSmltUsr ) ;
			}
			else {
				mongodbSetDataMap.put( "SMLT" , "N" ) ;
			}
			
			saveDtmStr = commUtil.getFormatingNowDateTime( ) ;
			mongodbSetDataMap.put( "INS_DT" , saveDtmStr ) ;
			mongodbSetDataMap.put( "INS_USR" , "Connectivity" ) ;
			mongodbSetDataMap.put( "UPD_DT" , saveDtmStr ) ;
			mongodbSetDataMap.put( "UPD_USR" , "Connectivity" ) ;
			
			mongoOps = mongodbConnection.getMongoTemplate( ) ;
			mongoOps.insert( mongodbSetDataMap , "MGP_CRHS" ) ;
			
		}
		catch( Exception e ) {
			resulBoolean = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			resultRstDataMap = null ;
			mongodbSetDataMap = null ;
			
			mongodbConnection = null ;
			mongoOps = null ;
			commUtil = null ;
			saveDtmStr = null ;
			
			logger.debug( "insertBtwnDvCalculData finally" ) ;
		}
		
		return resulBoolean ;
		
	}
	
	public Boolean insertBtwnDvCalculData( HashMap< String , Object > mongodbSetDataMap ) {
		
		Boolean resulBoolean = true ;
		
		// HashMap< String , Object > mongodbSetDataMap = new HashMap< String , Object >( ) ;
		
		MongodbConnection mongodbConnection = new MongodbConnection( ) ;
		MongoOperations mongoOps = null ;
		
		CommUtil commUtil = new CommUtil( ) ;
		String saveDtmStr = "" ;
		
		try {
			logger.debug( "insertBtwnDvCalculData" ) ;
			if( !commUtil.checkNull( mongodbSetDataMap ) ) {
				
				saveDtmStr = commUtil.getFormatingNowDateTime( ) ;
				mongodbSetDataMap.put( "INS_DT" , saveDtmStr ) ;
				mongodbSetDataMap.put( "INS_USR" , "Connectivity" ) ;
				mongodbSetDataMap.put( "UPD_DT" , saveDtmStr ) ;
				mongodbSetDataMap.put( "UPD_USR" , "Connectivity" ) ;
				
				mongoOps = mongodbConnection.getMongoTemplate( ) ;
				mongoOps.insert( mongodbSetDataMap , "MGP_CRHS" ) ;
			}
			
		}
		catch( Exception e ) {
			resulBoolean = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			mongodbSetDataMap = null ;
			
			mongodbConnection = null ;
			mongoOps = null ;
			commUtil = null ;
			saveDtmStr = null ;
			
			logger.debug( "insertBtwnDvCalculData finally" ) ;
		}
		
		return resulBoolean ;
		
	}
	
}
