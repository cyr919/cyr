/**
 * 
 */
package com.connectivity.config ;

import java.io.UnsupportedEncodingException ;
import java.net.URLEncoder ;

import org.springframework.context.annotation.Bean ;
import org.springframework.context.annotation.Configuration ;
import org.springframework.data.mongodb.core.MongoTemplate ;

import com.mongodb.ConnectionString ;
import com.mongodb.client.MongoClient ;
import com.mongodb.client.MongoClients ;

/**
 * <pre>
 * mongotemplate 테스트
 * </pre>
 *
 * @author cyr
 * @date 2020-05-19
 */
@Configuration
public class MongoConfig
{
	/*
	 * Use the standard Mongo driver API to create a com.mongodb.client.MongoClient instance.
	 */
	public @Bean MongoClient mongoClient( ) {
		
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
		}
		strMongoClientURI = "mongodb://" + dbUser + ":" + encodedPwd + "@" + host + ":" + port + "/" + dbName + "?authSource=" + dbName + "&authMechanism=SCRAM-SHA-256" ;
		
		connectionString = new ConnectionString( strMongoClientURI ) ;
		
		return MongoClients.create( connectionString ) ;
	}
	
	public @Bean MongoTemplate mongoTemplate( ) {
		return new MongoTemplate( mongoClient( ) , "hsmgp" ) ;
	}
}
