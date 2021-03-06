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
	
	public ArrayList< HashMap > selectStandardDataModelList( ) {
		
		ArrayList< HashMap > resultList = new ArrayList< HashMap >( ) ;
		
		MongodbConnection mongodbConnection = new MongodbConnection( ) ;
		MongoOperations mongoOps = null ;
		Query query = new Query( ) ;
		
		try {
			logger.debug( "selectStandardDataModelList" ) ;
			
			mongoOps = mongodbConnection.getMongoTemplate( ) ;
			
			// sql where
			query.addCriteria( Criteria.where( "USE" ).is( "Y" ) ) ;
			
			resultList = ( ArrayList< HashMap > ) mongoOps.find( query , HashMap.class , "MGP_STDM" ) ;
			
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
			
			logger.debug( "selectStandardDataModelList finally" ) ;
		}
		
		return resultList ;
	}
	
	/**
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
		
		MatchOperation matchOperation01 = null ;
		MatchOperation matchOperation02 = null ;
		ProjectionOperation projectionOperation01 = null ;
		
		try {
			logger.debug( "selectInstallDeviceList" ) ;
			
			mongoOps = mongodbConnection.getMongoTemplate( ) ;
			
			// sql where
			matchOperation01 = selectInstallDeviceListMatchOperation01( ) ;
			matchOperation02 = selectInstallDeviceListMatchOperation02( ) ;
			
			projectionOperation01 = selectInstallDeviceListProjectionOperation01( ) ;
			
			aggregation = Aggregation.newAggregation( matchOperation01 , matchOperation02 , projectionOperation01 ) ;
			
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
			matchOperation01 = null ;
			matchOperation02 = null ;
			projectionOperation01 = null ;
			
			logger.debug( "selectInstallDeviceList finally" ) ;
		}
		
		return resultList ;
	}
	
	public List< HashMap > selectInstallDeviceList( List< ? > deviceIdList ) {
		
		List< HashMap > resultList = new ArrayList< HashMap >( ) ;
		
		MongodbConnection mongodbConnection = new MongodbConnection( ) ;
		MongoOperations mongoOps = null ;
		
		Aggregation aggregation = null ;
		AggregationResults< HashMap > aggregationResults = null ;
		
		MatchOperation matchOperation01 = null ;
		MatchOperation matchOperation02 = null ;
		MatchOperation matchOperation03 = null ;
		ProjectionOperation projectionOperation01 = null ;
		
		try {
			logger.debug( "selectInstallDeviceList" ) ;
			
			mongoOps = mongodbConnection.getMongoTemplate( ) ;
			
			// sql where
			matchOperation01 = selectInstallDeviceListMatchOperation01( ) ;
			matchOperation02 = selectInstallDeviceListMatchOperation02( ) ;
			// in 조건 추가
			matchOperation03 = selectInstallDeviceListMatchOperation03( deviceIdList ) ;
			
			projectionOperation01 = selectInstallDeviceListProjectionOperation01( ) ;
			
			aggregation = Aggregation.newAggregation( matchOperation01 , matchOperation02 , matchOperation03 , projectionOperation01 ) ;
			
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
			matchOperation01 = null ;
			matchOperation02 = null ;
			matchOperation03 = null ;
			projectionOperation01 = null ;
			deviceIdList = null ;
			logger.debug( "selectInstallDeviceList finally" ) ;
		}
		
		return resultList ;
	}
	
	public MatchOperation selectInstallDeviceListMatchOperation01( ) {
		return Aggregation.match( Criteria.where( "USE" ).is( "Y" ) ) ;
	}
	
	public MatchOperation selectInstallDeviceListMatchOperation02( ) {
		return Aggregation.match( Criteria.where( "DT_MDL.USE" ).is( "Y" ) ) ;
	}
	
	public MatchOperation selectInstallDeviceListMatchOperation03( List< ? > deviceIdList ) {
		MatchOperation matchOperation = null ;
		try {
			matchOperation = Aggregation.match( Criteria.where( "_id" ).in( deviceIdList ) ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			deviceIdList = null ;
		}
		return matchOperation ;
	}
	
	public ProjectionOperation selectInstallDeviceListProjectionOperation01( ) {
		
		ProjectionOperation projectionOperation01 = null ;
		FilterAggregationExpression filterAggregationExpression = null ;
		HashMap< String , Object > filterCondMap = new HashMap< String , Object >( ) ;
		List< Object > condList = new ArrayList< Object >( ) ;
		
		try {
			condList.add( "$$this.USE" ) ;
			condList.add( "Y" ) ;
			filterCondMap.put( "$eq" , condList ) ;
			filterAggregationExpression = new FilterAggregationExpression( "$DT_MDL" , filterCondMap ) ;
			projectionOperation01 = Aggregation.project( "_id" , "SITE_ID" , "DVIF_ID" , "NM" , "TP" , "SMLT" , "ADPT_ID" , "USE" , "TRM" , "SKIP" , "SCR" , "CAL_INF" ).and( filterAggregationExpression ).as( "DT_MDL" ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			filterAggregationExpression = null ;
			filterCondMap = null ;
			condList = null ;
		}
		
		return projectionOperation01 ;
	}
	
	/**
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
	
	public HashMap< String , Object > selectLogConfigInfo( ) {
		
		HashMap< String , Object > resultMap = new HashMap< String , Object >( ) ;
		
		MongodbConnection mongodbConnection = new MongodbConnection( ) ;
		MongoOperations mongoOps = null ;
		Query query = new Query( ) ;
		
		try {
			
			logger.debug( "selectLogConfigInfo" ) ;
			
			mongoOps = mongodbConnection.getMongoTemplate( ) ;
			
			// sql where
			query.addCriteria( Criteria.where( "TP" ).is( "CNT" ) ) ;
			
			// 추후 변경
			query.addCriteria( Criteria.where( "_id" ).is( "connectivity" ) ) ;
			
			resultMap = mongoOps.findOne( query , HashMap.class , "MGP_LGCF" ) ;
			
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
			logger.debug( "selectLogConfigInfo finally" ) ;
		}
		
		return resultMap ;
	}
	
}
