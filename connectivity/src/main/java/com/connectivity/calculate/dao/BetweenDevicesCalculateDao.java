/**
 * 
 */
package com.connectivity.calculate.dao ;

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
	
	public HashMap< String , Map< String , String > > getAllDevicesGatherData( HashMap< String , HashMap< String , Object > > stdvInfMap ) {
		HashMap< String , Map< String , String > > resultMap = new HashMap< String , Map< String , String > >( ) ;
		
		Map< String , String > dvGthrDtMap = new HashMap< String , String >( ) ;
		
		RedisCommon redisCommon = new RedisCommon( ) ;
		String hashKeyStr = "" ;
		
		try {
			
			for( String keyStr : stdvInfMap.keySet( ) ) {
//				logger.debug( "keyStr :: " + keyStr ) ;
				hashKeyStr = "MGP_SVDT^" + keyStr ;
				
				dvGthrDtMap = redisCommon.redisHgetAll( hashKeyStr ) ;
//				logger.debug( "hashKeyStr :: " + hashKeyStr ) ;
//				logger.debug( "dvGthrDtMap :: " + dvGthrDtMap ) ;
				
				resultMap.put( keyStr , dvGthrDtMap ) ;
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
	
	public Boolean hmSetBtwnDvCalculData( String mgpKeyStr , String idStr , String valStr , String qcStr ) {
		Boolean resulBoolean = true ;
		
		String strKey = "" ;
		String resultStr = "" ;
		
		HashMap< String , String > redisSetDataMap = new HashMap< String , String >( ) ;
		
		RedisCommon redisCommon = new RedisCommon( ) ;
		CommUtil commUtil = new CommUtil( ) ;
		
		try {
//			logger.debug( "hmSetBtwnDvCalculData" ) ;
			
			strKey = "MGP_CLRT" + "^" + mgpKeyStr ;
//			logger.debug( "strKey :: " + strKey ) ;
			
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
			
//			logger.debug( "hmSetBtwnDvCalculData finally" ) ;
		}
		
		return resulBoolean ;
	}
	
	/**
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-11
	 * @param idStr 배치 시작시간
	 * @param resultRstDataMap
	 * @return
	 */
	public Boolean insertBtwnDvCalculData( String idStr , HashMap< String , Object > resultRstDataMap ) {
		
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
			// TODO 시뮬레이션 모드 정보 조회 및 추가
			mongodbSetDataMap.put( "SMLT" , "Y" ) ;
			mongodbSetDataMap.put( "SMLT_USR" , "사용자" ) ;
			
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
	
}
