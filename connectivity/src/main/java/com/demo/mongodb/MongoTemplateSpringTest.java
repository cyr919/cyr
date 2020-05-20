/**
 * 
 */
package com.demo.mongodb ;

import java.util.ArrayList ;
import java.util.HashMap ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;
import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.data.mongodb.core.MongoOperations ;
import org.springframework.data.mongodb.core.MongoTemplate ;
import org.springframework.data.mongodb.core.query.Criteria ;
import org.springframework.data.mongodb.core.query.Query ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-05-19
 */
public class MongoTemplateSpringTest
{
	Logger logger = LogManager.getLogger( MongoTemplateSpringTest.class ) ;
	
	@Autowired
	MongoTemplate mongoTemplate ;
	
	/**
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-05-19
	 * @param args
	 */
	public static void main( String[ ] args ) {
		// TODO Auto-generated method stub
		
		MongoTemplateSpringTest exe = new MongoTemplateSpringTest( ) ;
		
		exe.springMongodbDataTest01( ) ;
		exe.springMongodbDataTest02( ) ;
		
		// 실행은 되나 템플릿 생성 안됨.
		
		
	}
	
	public Boolean springMongodbDataTest01( ) {
		Boolean resultBool = true ;
		
		try {
			logger.info( "springMongodbDataTest01" ) ;
			
			MongoOperations mongoOps = mongoTemplate ;
			
			Query query = new Query( ) ;
			
			query.addCriteria( Criteria.where( "USE" ).is( "Y" ) ) ;
			
			ArrayList< HashMap > resultList = new ArrayList< HashMap >( ) ;
			
			logger.trace( "mongoOps :: " + mongoOps ) ;
			logger.trace( "query :: " + query ) ;
			
			resultList = ( ArrayList< HashMap > ) mongoOps.find( query , HashMap.class , "MGP_STDV" ) ;
			
			if( resultList == null ) {
				resultList = new ArrayList< HashMap >( ) ;
			}
			
			logger.trace( "resultList :: " + resultList ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			logger.info( "springMongodbDataTest01 finally" ) ;
			
		}
		
		return resultBool ;
	}
	
	public Boolean springMongodbDataTest02( ) {
		Boolean resultBool = true ;
		
		try {
			logger.info( "springMongodbDataTest01" ) ;
			
			MongoOperations mongoOps = mongoTemplate ;
			
			Query query = new Query( ) ;
			query.addCriteria( Criteria.where( "USE" ).is( "Y" ) ) ;
			
			logger.trace( "mongoOps :: " + mongoOps ) ;
			logger.trace( "query :: " + query ) ;
			
			ArrayList< HashMap > resultList = new ArrayList< HashMap >( ) ;
			resultList = ( ArrayList< HashMap > ) mongoOps.find( query , HashMap.class , "MGP_STDV" ) ;
			
			if( resultList == null ) {
				resultList = new ArrayList< HashMap >( ) ;
			}
			
			logger.trace( "resultList :: " + resultList ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			logger.info( "springMongodbDataTest01 finally" ) ;
			
		}
		
		return resultBool ;
	}
}
