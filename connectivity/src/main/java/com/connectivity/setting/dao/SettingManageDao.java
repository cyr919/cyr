/**
 * 
 */
package com.connectivity.setting.dao ;

import java.util.ArrayList ;
import java.util.HashMap ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;
import org.springframework.data.mongodb.core.MongoOperations ;
import org.springframework.data.mongodb.core.aggregation.Aggregation ;
import org.springframework.data.mongodb.core.aggregation.MatchOperation ;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation ;
import org.springframework.data.mongodb.core.query.Criteria ;
import org.springframework.data.mongodb.core.query.Query ;

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
	
	public ArrayList< HashMap > selectInstallDeviceList( ) {
		
		ArrayList< HashMap > resultList = new ArrayList< HashMap >( ) ;
		
		MongodbConnection mongodbConnection = new MongodbConnection( ) ;
		MongoOperations mongoOps = null ;
		Query query = new Query( ) ;
		
		try {
			logger.debug( "springMongodbDataTest01" ) ;
			
			mongoOps = mongodbConnection.getMongoTemplate( ) ;
			
			MatchOperation MatchOperation01 = Aggregation.match( Criteria.where( "DT_MDL" ).exists( true ) ) ;
			MatchOperation MatchOperation02 = Aggregation.match( Criteria.where( "DT_MDL.USE" ).is( "Y" ) ) ;
			
			
			ProjectionOperation projectionOperation = Aggregation.project( "_id" , "SITE_ID" , "DVIF_ID"  , "TP", "SMLT", "ADPT_ID" , "TRM" , "QC" , "DT_MDL"  ) ; 
			
			
			// sql where
			query.addCriteria( Criteria.where( "USE" ).is( "Y" ) ) ;
			
			resultList = ( ArrayList< HashMap > ) mongoOps.find( query , HashMap.class , "MGP_STDV" ) ;
			
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
			
			logger.debug( "springMongodbDataTest01 finally" ) ;
		}
		
		return resultList ;
	}
	
	public ArrayList< HashMap > selectFieldQualityCodeList( ) {
		ArrayList< HashMap > resultList = new ArrayList< HashMap >( ) ;
		
		MongodbConnection mongodbConnection = new MongodbConnection( ) ;
		MongoOperations mongoOps = null ;
		Query query = new Query( ) ;
		
		try {
			logger.debug( "selectFieldQualityCodeList" ) ;
			
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
			
			logger.debug( "selectFieldQualityCodeList finally" ) ;
		}
		
		return resultList ;
	}
	
}
