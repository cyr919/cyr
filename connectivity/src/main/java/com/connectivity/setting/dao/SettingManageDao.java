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
	
	public List< HashMap > selectInstallDeviceList( ) {
		
		List< HashMap > resultList = new ArrayList< HashMap >( ) ;
		
		MongodbConnection mongodbConnection = new MongodbConnection( ) ;
		MongoOperations mongoOps = null ;
		
		Aggregation aggregation = null ;
		AggregationResults< HashMap > aggregationResults = null ;
		
		MatchOperation MatchOperation01 = null ;
		MatchOperation MatchOperation02 = null ;
		MatchOperation MatchOperation03 = null ;
		ProjectionOperation projectionOperation01 = null ;
		FilterAggregationExpression filterAggregationExpression = null ;
		HashMap< String , Object > filterCondMap = new HashMap< String , Object >( ) ;
		List< Object > condList = new ArrayList< Object >( ) ;
		
		try {
			logger.debug( "springMongodbDataTest01" ) ;
			
			mongoOps = mongodbConnection.getMongoTemplate( ) ;
			
			// sql where
			MatchOperation01 = Aggregation.match( Criteria.where( "USE" ).is( "Y" ) ) ;
			MatchOperation02 = Aggregation.match( Criteria.where( "DT_MDL" ).exists( true ) ) ;
			MatchOperation03 = Aggregation.match( Criteria.where( "DT_MDL.USE" ).is( "Y" ) ) ;
			
			condList.add( "$$this.USE" ) ;
			condList.add( "Y" ) ;
			filterCondMap.put( "$eq" , condList ) ;
			filterAggregationExpression = new FilterAggregationExpression( "$DT_MDL" , filterCondMap ) ;
			projectionOperation01 = Aggregation.project( "_id" , "SITE_ID" , "DVIF_ID" , "NM" , "TP" , "SMLT" , "ADPT_ID" , "USE" , "TRM" , "QC" , "CAL_INF" ).and( filterAggregationExpression ).as( "DT_MDL" ) ;
			
			aggregation = Aggregation.newAggregation( MatchOperation01 , MatchOperation02 , MatchOperation03 , projectionOperation01 ) ;
			
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
			MatchOperation03 = null ;
			projectionOperation01 = null ;
			filterAggregationExpression = null ;
			filterCondMap = null ;
			condList = null ;
			
			logger.debug( "springMongodbDataTest01 finally" ) ;
		}
		
		return resultList ;
	}
	
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
}
