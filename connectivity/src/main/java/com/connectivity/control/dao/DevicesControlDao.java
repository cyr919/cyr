/**
 * 
 */
package com.connectivity.control.dao ;

import java.util.HashMap ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;
import org.springframework.data.mongodb.core.MongoOperations ;
import org.springframework.data.mongodb.core.query.Criteria ;
import org.springframework.data.mongodb.core.query.Query ;

import com.connectivity.config.MongodbConnection ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-06-15
 */
public class DevicesControlDao
{
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private Logger logger = LogManager.getLogger( this.getClass( ) ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	public HashMap< String , Object > selectDeviceContolInfoFromPointIndex( String strPointIndex ) {
		
		HashMap< String , Object > resultMap = new HashMap< String , Object >( ) ;
		
		HashMap< String , Object > resultMapperMap = new HashMap< String , Object >( ) ;
		MongodbConnection mongodbConnection = new MongodbConnection( ) ;
		MongoOperations mongoOps = null ;
		Query query = new Query( ) ;
		
		try {
			logger.debug( "selectDeviceContolInfoFromPointIndex" ) ;
			
			mongoOps = mongodbConnection.getMongoTemplate( ) ;
			
			// sql where
			query = new Query( ) ;
			query.addCriteria( Criteria.where( "USE" ).is( "Y" ) ) ;
			query.addCriteria( Criteria.where( "TG_ID" ).is( strPointIndex ) ) ;
			logger.debug( "query :: " + query ) ;
			
			resultMapperMap = mongoOps.findOne( query , HashMap.class , "MGP_MPPR" ) ;
			logger.debug( "resultMapperMap :: " + resultMapperMap ) ;
			
			query = new Query( ) ;
			query.addCriteria( Criteria.where( "STDV_ID" ).is( ( resultMapperMap.get( "SO_ID" ) + "" ) ) ) ;
			query.addCriteria( Criteria.where( "CD" ).is( ( resultMapperMap.get( "SO_CTRL_CD" ) + "" ) ) ) ;
			logger.debug( "query :: " + query ) ;
			
			resultMap = mongoOps.findOne( query , HashMap.class , "MGP_DVCT" ) ;
			logger.debug( "resultMap :: " + resultMap ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strPointIndex = null ;
			resultMapperMap = null ;
			mongodbConnection = null ;
			mongoOps = null ;
			query = null ;
			logger.debug( "selectDeviceContolInfoFromPointIndex finally" ) ;
		}
		return resultMap ;
	}
	
	// public HashMap< String , Object > selectDeviceContolInfoLookupFromPointIndex( String strPointIndex ) {
	//
	// HashMap< String , Object > resultMap = new HashMap< String , Object >( ) ;
	// List< HashMap > resultList = new ArrayList< HashMap >( ) ;
	//
	// MongodbConnection mongodbConnection = new MongodbConnection( ) ;
	// MongoOperations mongoOps = null ;
	// String strQuery = "" ;
	//
	// Aggregation aggregation = null ;
	// AggregationResults< HashMap > aggregationResults = null ;
	//
	// MatchOperation MatchOperation01 = null ;
	// MatchOperation MatchOperation02 = null ;
	// MatchOperation MatchOperation03 = null ;
	// CustomAggregationOperation customAggregationOperation = null ;
	//
	// try {
	// logger.debug( "selectDeviceContolInfoLookupFromPointIndex" ) ;
	// logger.debug( "strPointIndex :: " + strPointIndex ) ;
	//
	// mongoOps = mongodbConnection.getMongoTemplate( ) ;
	//
	// strQuery = "" ;
	// strQuery = strQuery + "{ $lookup : {" ;
	// strQuery = strQuery + "from : 'MGP_DVCT'" ;
	// strQuery = strQuery + ", let : { mppr_so_id : '$SO_ID' , mppr_so_ctrl_cd : '$SO_CTRL_CD' }" ;
	// strQuery = strQuery + ", pipeline : [" ;
	// strQuery = strQuery + "{ $match : { $expr :" ;
	// strQuery = strQuery + "{ $and : [" ;
	// strQuery = strQuery + "{ $eq : [ '$STDV_ID' , '$$mppr_so_id' ] }" ;
	// strQuery = strQuery + ", { $eq : [ '$CD' , '$$mppr_so_ctrl_cd' ] }" ;
	// strQuery = strQuery + ", { $eq : [ '$USE' , 'Y' ] }" ;
	// strQuery = strQuery + "] }" ;
	// strQuery = strQuery + "} }" ;
	// strQuery = strQuery + "]" ;
	// strQuery = strQuery + ", as : 'DVCT_OBJ'" ;
	// strQuery = strQuery + "}" ;
	// strQuery = strQuery + "}" ;
	//
	// customAggregationOperation = new CustomAggregationOperation( strQuery ) ;
	// // logger.debug( "strQuery :: " + strQuery ) ;
	// // logger.debug( "customAggregationOperation :: " + customAggregationOperation ) ;
	//
	// // logger.debug( "Document.parse( strQuery ) :: " + Document.parse( strQuery ) ) ;
	//
	// // sql where
	// MatchOperation01 = Aggregation.match( Criteria.where( "USE" ).is( "Y" ) ) ;
	// MatchOperation02 = Aggregation.match( Criteria.where( "MP_TP" ).is( "WRSQNS" ) ) ;
	// MatchOperation03 = Aggregation.match( Criteria.where( "TG_ID" ).is( strPointIndex ) ) ;
	//
	// // logger.debug( "MatchOperation01 :: " + MatchOperation01 ) ;
	// // logger.debug( "MatchOperation02 :: " + MatchOperation02 ) ;
	// // logger.debug( "MatchOperation03 :: " + MatchOperation03 ) ;
	//
	// aggregation = Aggregation.newAggregation( MatchOperation01 , MatchOperation02 , MatchOperation03 , customAggregationOperation ) ;
	// logger.debug( "aggregation :: " + aggregation ) ;
	// aggregationResults = mongoOps.aggregate( aggregation , "MGP_MPPR" , HashMap.class ) ;
	// resultList = aggregationResults.getMappedResults( ) ;
	//
	// logger.debug( "resultList :: " + resultList ) ;
	//
	// if( resultList != null ) {
	// // resultMap = ( HashMap< String , Object > ) resultList.get( 0 ).get( "DVCT_OBJ" ) ;
	// resultMap = resultList.get( 0 ) ;
	// }
	//
	// logger.debug( "resultMap :: " + resultMap ) ;
	// }
	// catch( Exception e ) {
	// logger.error( e.getMessage( ) , e ) ;
	// }
	// finally {
	// strPointIndex = null ;
	// resultList = null ;
	// mongodbConnection = null ;
	// mongoOps = null ;
	// strQuery = null ;
	// aggregation = null ;
	// aggregationResults = null ;
	// MatchOperation01 = null ;
	// MatchOperation02 = null ;
	// MatchOperation03 = null ;
	// customAggregationOperation = null ;
	// }
	// return resultMap ;
	// }
	
}
