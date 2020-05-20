/**
 * 
 */
package com.demo.mongodb ;

import java.util.ArrayList ;
import java.util.HashMap ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;
import org.springframework.data.mongodb.core.MongoOperations ;
import org.springframework.data.mongodb.core.MongoTemplate ;
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
public class MongoTemplateTest
{
	Logger logger = LogManager.getLogger( MongoTemplateTest.class ) ;
	
	/**
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-05-19
	 * @param args
	 */
	public static void main( String[ ] args ) {
		
		MongoTemplateTest exe = new MongoTemplateTest( ) ;
		
		exe.springMongodbDataTest01( ) ;
		exe.springMongodbDataTest02( ) ;
	}
	
	public Boolean springMongodbDataTest01( ) {
		Boolean resultBool = true ;
		
		MongodbConnection mongodbConnection = new MongodbConnection( ) ;
		
		try {
			logger.info( "springMongodbDataTest01" );
			
			MongoOperations mongoOps = new MongoTemplate( mongodbConnection.getMongoClient( ) , "hsmgp" ) ;
			
			Query query = new Query( ) ;
			
			query.addCriteria( Criteria.where( "USE" ).is( "Y" ) ) ;
			
			ArrayList< HashMap > resultList = new ArrayList< HashMap >( ) ;
			
			resultList = ( ArrayList< HashMap > ) mongoOps.find( query , HashMap.class , "MGP_STDV" ) ;
			
			if( resultList == null ) {
				resultList = new ArrayList< HashMap >( ) ;
			}
			
			logger.trace( "mongoOps :: " + mongoOps ) ;
			logger.trace( "query :: " + query ) ;
			logger.trace( "resultList :: " + resultList ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}finally {
			logger.info( "springMongodbDataTest01 finally" );

		}
		
		return resultBool ;
	}
	
	

	public Boolean springMongodbDataTest02( ) {
		Boolean resultBool = true ;
		
		MongodbConnection mongodbConnection = new MongodbConnection( ) ;
		
		try {
			logger.info( "springMongodbDataTest01" );
			
			MongoOperations mongoOps = mongodbConnection.getMongoTemplate( ) ;
			
			Query query = new Query( ) ;
			query.addCriteria( Criteria.where( "USE" ).is( "Y" ) ) ;
			
			ArrayList< HashMap > resultList = new ArrayList< HashMap >( ) ;
			resultList = ( ArrayList< HashMap > ) mongoOps.find( query , HashMap.class , "MGP_STDV" ) ;
			
			if( resultList == null ) {
				resultList = new ArrayList< HashMap >( ) ;
			}
			
			logger.trace( "mongoOps :: " + mongoOps ) ;
			logger.trace( "query :: " + query ) ;
			logger.trace( "resultList :: " + resultList ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}finally {
			logger.info( "springMongodbDataTest01 finally" );

		}
		
		return resultBool ;
	}
}
