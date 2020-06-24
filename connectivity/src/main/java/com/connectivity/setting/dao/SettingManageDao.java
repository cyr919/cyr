/**
 * 
 */
package com.connectivity.setting.dao ;

import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.List ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;
import org.springframework.data.mongodb.core.MongoOperations ;
import org.springframework.data.mongodb.core.aggregation.Aggregation ;
import org.springframework.data.mongodb.core.aggregation.AggregationResults ;
import org.springframework.data.mongodb.core.aggregation.MatchOperation ;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation ;
import org.springframework.data.mongodb.core.query.Criteria ;
import org.springframework.data.mongodb.core.query.Query ;

import com.connectivity.common.FilterAggregationExpression ;
import com.connectivity.config.MongodbConnection ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-05-19
 */
public class SettingManageDao
{
	private Logger logger = LogManager.getLogger( this.getClass( ) ) ;
	
	/**
	 * 
	 * <pre>
	 * 설치 디바이스 정보 조회
	 * 사용여부 Y, 디바이스 데이터 모델 중 사용여부 Y인 데이터
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-18
	 * @return
	 */
	public List< HashMap > selectInstallDeviceList( ) {
		
		List< HashMap > resultList = new ArrayList< HashMap >( ) ;
		
		MongodbConnection mongodbConnection = new MongodbConnection( ) ;
		MongoOperations mongoOps = null ;
		
		Aggregation aggregation = null ;
		AggregationResults< HashMap > aggregationResults = null ;
		
		MatchOperation MatchOperation01 = null ;
		MatchOperation MatchOperation02 = null ;
		ProjectionOperation projectionOperation01 = null ;
		FilterAggregationExpression filterAggregationExpression = null ;
		HashMap< String , Object > filterCondMap = new HashMap< String , Object >( ) ;
		List< Object > condList = new ArrayList< Object >( ) ;
		
		try {
			logger.debug( "selectInstallDeviceList" ) ;
			
			mongoOps = mongodbConnection.getMongoTemplate( ) ;
			
			// sql where
			MatchOperation01 = Aggregation.match( Criteria.where( "USE" ).is( "Y" ) ) ;
			MatchOperation02 = Aggregation.match( Criteria.where( "DT_MDL.USE" ).is( "Y" ) ) ;
			
			condList.add( "$$this.USE" ) ;
			condList.add( "Y" ) ;
			filterCondMap.put( "$eq" , condList ) ;
			filterAggregationExpression = new FilterAggregationExpression( "$DT_MDL" , filterCondMap ) ;
			projectionOperation01 = Aggregation.project( "_id" , "SITE_ID" , "DVIF_ID" , "NM" , "TP" , "SMLT" , "ADPT_ID" , "USE" , "TRM" , "SKIP" , "SCR" , "CAL_INF" ).and( filterAggregationExpression ).as( "DT_MDL" ) ;
			
			aggregation = Aggregation.newAggregation( MatchOperation01 , MatchOperation02  , projectionOperation01 ) ;
			
			aggregationResults = mongoOps.aggregate( aggregation , "MGP_STDV" , HashMap.class ) ;
			resultList = aggregationResults.getMappedResults( ) ;
			
			logger.trace( "mongoOps :: " + mongoOps ) ;
			logger.trace( "aggregation :: " + aggregation ) ;
			logger.trace( "resultList :: " + resultList ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			mongodbConnection = null ;
			mongoOps = null ;
			
			aggregation = null ;
			aggregationResults = null ;
			MatchOperation01 = null ;
			MatchOperation02 = null ;
			projectionOperation01 = null ;
			filterAggregationExpression = null ;
			filterCondMap = null ;
			condList = null ;
			
			logger.debug( "selectInstallDeviceList finally" ) ;
		}
		
		return resultList ;
	}
	
	/**
	 * 
	 * <pre>
	 * 퀄리티 코드 정보 조회
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-18
	 * @return
	 */
	public ArrayList< HashMap > selectQualityCodeList( ) {
		
		ArrayList< HashMap > resultList = new ArrayList< HashMap >( ) ;
		
		MongodbConnection mongodbConnection = new MongodbConnection( ) ;
		MongoOperations mongoOps = null ;
		Query query = new Query( ) ;
		
		try {
			logger.debug( "selectQualityCodeList" ) ;
			
			mongoOps = mongodbConnection.getMongoTemplate( ) ;
			
			// sql where
			query.addCriteria( Criteria.where( "USE" ).is( "Y" ) ) ;
			
			resultList = ( ArrayList< HashMap > ) mongoOps.find( query , HashMap.class , "MGP_QLCD" ) ;
			
			logger.trace( "mongoOps :: " + mongoOps ) ;
			logger.trace( "query :: " + query ) ;
			logger.trace( "resultList :: " + resultList ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			mongodbConnection = null ;
			mongoOps = null ;
			query = null ;
			
			logger.debug( "selectQualityCodeList finally" ) ;
		}
		
		return resultList ;
	}
	
	/**
	 * 
	 * <pre>
	 * 장치간 연산 정보 조회
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-18
	 * @return
	 */
	public ArrayList< HashMap > selectBetweenDevicesCalculateInfo( ) {
		
		ArrayList< HashMap > resultList = new ArrayList< HashMap >( ) ;
		
		MongodbConnection mongodbConnection = new MongodbConnection( ) ;
		MongoOperations mongoOps = null ;
		Query query = new Query( ) ;
		
		try {
			logger.debug( "selectBetweenDevicesCalculateInfo" ) ;
			
			mongoOps = mongodbConnection.getMongoTemplate( ) ;
			
			// sql where
			query.addCriteria( Criteria.where( "USE" ).is( "Y" ) ) ;
			
			resultList = ( ArrayList< HashMap > ) mongoOps.find( query , HashMap.class , "MGP_CLIF" ) ;
			
			logger.trace( "mongoOps :: " + mongoOps ) ;
			logger.trace( "query :: " + query ) ;
			logger.trace( "resultList :: " + resultList ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			mongodbConnection = null ;
			mongoOps = null ;
			query = null ;
			
			logger.debug( "selectBetweenDevicesCalculateInfo finally" ) ;
		}
		
		return resultList ;
	}
	
	/**
	 * 
	 * <pre>
	 * 사이트 정보 조회
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-18
	 * @return
	 */
	public HashMap< String , Object > selectSiteInfo( ) {
		
		HashMap< String , Object > resultMap = new HashMap< String , Object >( ) ;
		
		MongodbConnection mongodbConnection = new MongodbConnection( ) ;
		MongoOperations mongoOps = null ;
		Query query = new Query( ) ;
		
		try {
			
			logger.debug( "selectSiteInfo" ) ;
			
			mongoOps = mongodbConnection.getMongoTemplate( ) ;
			
			resultMap = mongoOps.findOne( query , HashMap.class , "MGP_SITE" ) ;
			
			logger.trace( "mongoOps :: " + mongoOps ) ;
			logger.trace( "resultMap :: " + resultMap ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			mongodbConnection = null ;
			mongoOps = null ;
			query = null ;
			logger.debug( "selectSiteInfo finally" ) ;
		}
		
		return resultMap ;
	}


	/**
	 * 
	 * <pre>
	 * app io 맵핑 정보 조회 - 계측 데이터 관련
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-18
	 * @return
	 */
	public ArrayList< HashMap > selectAppioMappingInfoDeviceData( ) {
		
		ArrayList< HashMap > resultList = new ArrayList< HashMap >( ) ;
		
		MongodbConnection mongodbConnection = new MongodbConnection( ) ;
		MongoOperations mongoOps = null ;
		Query query = new Query( ) ;
		
		try {
			logger.debug( "selectAppioMappingInfoDeviceData" ) ;
			
			mongoOps = mongodbConnection.getMongoTemplate( ) ;
			
			// sql where
			query.addCriteria( Criteria.where( "USE" ).is( "Y" ) ) ;
			query.addCriteria( Criteria.where( "MP_TP" ).is( "APPIO" ) ) ;
			query.addCriteria( Criteria.where( "DT_TP" ).is( "meter" ) ) ;
			query.addCriteria( Criteria.where( "SO_COL" ).is( "MGP_SDHS" ) ) ;
			
			resultList = ( ArrayList< HashMap > ) mongoOps.find( query , HashMap.class , "MGP_MPPR" ) ;
			
			logger.trace( "mongoOps :: " + mongoOps ) ;
			logger.trace( "query :: " + query ) ;
			logger.trace( "resultList :: " + resultList ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			mongodbConnection = null ;
			mongoOps = null ;
			query = null ;
			
			logger.debug( "selectAppioMappingInfoDeviceData finally" ) ;
		}
		
		return resultList ;
	}	
	
public ArrayList< HashMap > selectAppioMappingInfoBetweenDevicesCalculatingData( ) {
		
		ArrayList< HashMap > resultList = new ArrayList< HashMap >( ) ;
		
		MongodbConnection mongodbConnection = new MongodbConnection( ) ;
		MongoOperations mongoOps = null ;
		Query query = new Query( ) ;
		
		try {
			logger.debug( "selectAppioMappingInfoBetweenDevicesCalculatingData" ) ;
			
			mongoOps = mongodbConnection.getMongoTemplate( ) ;
			
			// sql where
			query.addCriteria( Criteria.where( "USE" ).is( "Y" ) ) ;
			query.addCriteria( Criteria.where( "MP_TP" ).is( "APPIO" ) ) ;
			query.addCriteria( Criteria.where( "DT_TP" ).is( "meter" ) ) ;
			query.addCriteria( Criteria.where( "SO_COL" ).is( "MGP_CRHS" ) ) ;
			
			resultList = ( ArrayList< HashMap > ) mongoOps.find( query , HashMap.class , "MGP_MPPR" ) ;
			
			logger.trace( "mongoOps :: " + mongoOps ) ;
			logger.trace( "query :: " + query ) ;
			logger.trace( "resultList :: " + resultList ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			mongodbConnection = null ;
			mongoOps = null ;
			query = null ;
			
			logger.debug( "selectAppioMappingInfoBetweenDevicesCalculatingData finally" ) ;
		}
		
		return resultList ;
	}	
}
