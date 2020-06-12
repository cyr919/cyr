/**
 * 
 */
package com.connectivity.config ;

import java.io.UnsupportedEncodingException ;
import java.net.URLEncoder ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;
import org.bson.Document ;
import org.springframework.data.mongodb.core.MongoTemplate ;

import com.mongodb.ConnectionString ;
import com.mongodb.client.MongoClient ;
import com.mongodb.client.MongoClients ;
import com.mongodb.client.MongoCollection ;
import com.mongodb.client.MongoDatabase ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-04-23
 */
public class MongodbConnection
{
	
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	Logger logger = LogManager.getLogger( MongodbConnection.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	private static MongoClient mongoClient ;
	
	public MongoClient getMongoClient( ) {
		
		if( mongoClient == null ) {
			logger.debug( "setMongoClient" );
			mongoClient = setMongoClient( ) ;
		}
		
		return mongoClient ;
	}
	
	public MongoClient setMongoClient( ) {
		MongoClient mongoClientLocal = null ;
		
		ConnectionString connectionString = null ;
		String strMongoClientURI = "" ;
		String host = "192.168.56.105" ;
		String port = "27017" ;
		
		String dbName = "hsmgp" ;
		String dbUser = "hsmgp" ;
		String dbPwd = "1q2w3e4r5t!@#$%" ;
		String encodedPwd = "" ;
		
		/*
		 * Imp. Note - 1. Developers will need to encode the 'auth_user' or the 'auth_pwd' string if it contains the <code>:</code> or the <code>@</code> symbol. If not, the code will throw the <code>java.lang.IllegalArgumentException</code>. 2. If the 'auth_user' or the 'auth_pwd' string does not contain the <code>:</code> or the <code>@</code> symbol, we can skip the encoding step.
		 */
		try {
			encodedPwd = URLEncoder.encode( dbPwd , "UTF-8" ) ;
		}
		catch( UnsupportedEncodingException e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		
		try {
			
			strMongoClientURI = "mongodb://" + dbUser + ":" + encodedPwd + "@" + host + ":" + port + "/" + dbName + "?authSource=" + dbName + "&authMechanism=SCRAM-SHA-256" ;
			
			connectionString = new ConnectionString( strMongoClientURI ) ;
			logger.info( "strMongoClientURI :: [" + strMongoClientURI + "]" ) ;
			
			mongoClientLocal = MongoClients.create( connectionString ) ;
			
		}
		catch( IllegalArgumentException e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			
			connectionString = null ;
			strMongoClientURI = null ;
			host = null ;
			port = null ;
			
			dbName = null ;
			dbUser = null ;
			dbPwd = null ;
			encodedPwd = null ;
		}
		
		return mongoClientLocal ;
	}
	
	public void closeMongoClient( ) {
		
		try {
			if( mongoClient != null ) {
				
				mongoClient.close( ) ;
			}
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		
		return ;
	}
	
	public MongoDatabase getDatabase( ) {
		
		MongoDatabase database = null ;
		
		try {
			
			database = getMongoClient( ).getDatabase( "hsmgp" ) ;
			logger.info( "database :: [" + database + "]" ) ;
			
		}
		catch( IllegalArgumentException e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			
		}
		
		return database ;
	}
	
	public Boolean checkMongoDatabase( ) {
		
		Boolean resultBool = true ;
		Document document = null ;
		try {
			resultBool = false ;
			
			document = getDatabase( ).runCommand( new Document( "dbStats" , 1 ) ) ;
			logger.info( "dbStats document :: [" + document + "]" ) ;
			
			resultBool = true ;
		}
		catch( Exception e ) {
			resultBool = false ;
			
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			document = null ;
		}
		
		return resultBool ;
	}
	
	public MongoDatabase getDatabase( String dbName ) {
		
		MongoDatabase database = null ;
		
		try {
			
			database = getMongoClient( ).getDatabase( dbName ) ;
			logger.info( "database :: [" + database + "]" ) ;
			
		}
		catch( IllegalArgumentException e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			dbName = null ;
		}
		
		return database ;
	}
	
	public Boolean checkMongoDatabase( String dbName ) {
		
		Boolean resultBool = true ;
		Document document = null ;
		try {
			resultBool = false ;
			
			document = getDatabase( dbName ).runCommand( new Document( "dbStats" , 1 ) ) ;
			logger.info( "dbStats document :: [" + document + "]" ) ;
			
			resultBool = true ;
		}
		catch( Exception e ) {
			resultBool = false ;
			
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			document = null ;
			dbName = null ;
		}
		
		return resultBool ;
	}
	
	public MongoTemplate getMongoTemplate( ) throws Exception {
		return new MongoTemplate( getMongoClient( ) , "hsmgp" ) ;
	}
	
	public MongoTemplate getMongoTemplate( String dbName ) throws Exception {
		return new MongoTemplate( getMongoClient( ) , dbName ) ;
	}
	
	public void mongodbConnectionTest( ) {
		
		logger.info( "mongodbConnectionTest start" ) ;
		
		ConnectionString connectionString = null ;
		MongoClient mongoClient = null ;
		MongoDatabase database = null ;
		MongoCollection< Document > coll = null ;
		
		String strMongoClientURI = "" ;
		String host = "192.168.56.105" ;
		String port = "27017" ;
		
		String dbName = "hsmgp" ;
		String dbUser = "hsmgp" ;
		String dbPwd = "1q2w3e4r5t!@#$%" ;
		String encodedPwd = "" ;
		
		/*
		 * Imp. Note - 1. Developers will need to encode the 'auth_user' or the 'auth_pwd' string if it contains the <code>:</code> or the <code>@</code> symbol. If not, the code will throw the <code>java.lang.IllegalArgumentException</code>. 2. If the 'auth_user' or the 'auth_pwd' string does not contain the <code>:</code> or the <code>@</code> symbol, we can skip the encoding step.
		 */
		try {
			encodedPwd = URLEncoder.encode( dbPwd , "UTF-8" ) ;
		}
		catch( UnsupportedEncodingException e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		
		try {
			
			strMongoClientURI = "mongodb://" + dbUser + ":" + encodedPwd + "@" + host + ":" + port + "/" + dbName + "?authSource=" + dbName + "&authMechanism=SCRAM-SHA-256" ;
			
			// strMongoClientURI = "mongodb://" + "192.168.56.105" + ":" + "27017" ;
			
			connectionString = new ConnectionString( strMongoClientURI ) ;
			logger.info( "strMongoClientURI :: [" + strMongoClientURI + "]" ) ;
			
			mongoClient = MongoClients.create( connectionString ) ;
			database = mongoClient.getDatabase( "hsmgp" ) ;
			logger.info( "database :: [" + database + "]" ) ;
			
			logger.info( "database.listCollectionNames( ) :: [" + database.listCollectionNames( ) + "]" ) ;
			logger.info( "database.getName( ) :: [" + database.getName( ) + "]" ) ;
			
			Document document = database.runCommand( new Document( "dbStats" , 1 ) ) ;
			
			logger.info( "document :: [" + document + "]" ) ;
			
			coll = database.getCollection( "memoCom" ) ;
			
			logger.info( "coll :: [" + coll + "]" ) ;
			logger.info( "coll.countDocuments( ) :: [" + coll.countDocuments( ) + "]" ) ;
			
		}
		catch( Exception e ) {
			
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			
			if( mongoClient != null ) {
				mongoClient.close( ) ;
			}
			
			connectionString = null ;
			mongoClient = null ;
			database = null ;
			coll = null ;
			
			strMongoClientURI = null ;
			host = null ;
			port = null ;
			
			dbName = null ;
			dbUser = null ;
			dbPwd = null ;
			encodedPwd = null ;
			
		}
		
		logger.info( "mongodbConnectionTest end" ) ;
		return ;
	}
	
	public void mongodbConnectionTest2( ) {
		
		logger.info( "mongodbConnectionTest2 start" ) ;
		MongoCollection< Document > coll = null ;
		
		try {
			
			logger.info( "checkMongoDatabase( ) :: " + checkMongoDatabase( ) ) ;
			logger.info( "checkMongoDatabase( \"hsmgp\" ) :: " + checkMongoDatabase( "hsmgp" ) ) ;
			
			coll = getDatabase( ).getCollection( "memoCom" ) ;
			
			logger.info( "coll :: [" + coll + "]" ) ;
			logger.info( "coll.countDocuments( ) :: [" + coll.countDocuments( ) + "]" ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		
		logger.info( "mongodbConnectionTest2 end" ) ;
		return ;
	}
	
	/**
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-04-23
	 * @param args
	 */
	public static void main( String[ ] args ) {
		// TODO Auto-generated method stub
		
		MongodbConnection exe = new MongodbConnection( ) ;
		
		// exe.mongodbConnectionTest( ) ;
		
		exe.mongodbConnectionTest2( ) ;
		
	}
	
}
