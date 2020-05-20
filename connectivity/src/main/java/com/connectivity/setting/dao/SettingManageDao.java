/**
 * 
 */
package com.connectivity.setting.dao ;

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
public class SettingManageDao
{
	private Logger logger = LogManager.getLogger( this.getClass( ) ) ;
	
	public ArrayList< HashMap > selectInstallDeviceList( ) {
		
		ArrayList< HashMap > resultList = new ArrayList< HashMap >( ) ;
		
		MongodbConnection mongodbConnection = new MongodbConnection( ) ;
		MongoOperations mongoOps = null ;
		Query query = new Query( ) ;
		
		try {
			
			logger.info( "springMongodbDataTest01" ) ;
			
			mongoOps = new MongoTemplate( mongodbConnection.getMongoClient( ) , "hsmgp" ) ;
			
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
			
			logger.info( "springMongodbDataTest01 finally" ) ;
		}
		
		return resultList ;
	}
	
}
