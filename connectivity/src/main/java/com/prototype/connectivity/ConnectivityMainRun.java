package com.prototype.connectivity;

import com.rabbitmq.client.ConnectionFactory ;

public class ConnectivityMainRun
{
	
	public static void main( String[ ] args ) {
		
		
		// rabbitmq 수신 connection
		
		ConnectionFactory factory = new ConnectionFactory( ) ;
		factory.setHost( "192.168.56.105" ) ;
		factory.setUsername( "admin" ) ;
		factory.setPassword( "admin" ) ;

		
		Data2Connectivity data2Connectivity = new Data2Connectivity( factory , "ESS01" ) ;
		Thread thread = new Thread( data2Connectivity , "data2Connectivity-Thread" ) ;
		thread.start( ); 
		
		
		
	}
	
}
