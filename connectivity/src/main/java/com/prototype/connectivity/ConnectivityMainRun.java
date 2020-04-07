package com.prototype.connectivity ;

import org.apache.log4j.Logger ;

import com.rabbitmq.client.ConnectionFactory ;

public class ConnectivityMainRun
{
	static Logger logger = Logger.getLogger( ConnectivityMainRun.class ) ;
	
	public static Command2Module command2Module = null ;
	public static Data2Connectivity[ ] data2ConnectivityArr = null ;
	
	public static void main( String[ ] args ) {
		
		connectivityRun( ) ;
		
		logger.info( "================================" ) ;
		logger.info( Thread.currentThread( ) ) ;
		logger.info( "================================" ) ;
		
		// try {
		// Thread.sleep( 10 * 1000 ) ;
		// } catch( InterruptedException e ) {
		// logger.error( e.getMessage( ) , e ) ;
		// }
		//
		// connectivityStop( );
		//
		// logger.info( "================================" ) ;
		// logger.info( Thread.currentThread( ) ) ;
		// logger.info( "================================" ) ;
		
	}
	
	public static void connectivityRun( ) {
		
		try {
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			
		}
		
		return ;
	}
	
	public static void connectivityStop( ) {
		try {
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			
		}
		
		return ;
		
	}
	
	public static void connectivityReset( ) {
		
		try {
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			
		}
		return ;
		
	}
	
	public static void connectivityDeviceSimulDataReset( ) {
		
		try {
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			
		}
		return ;
		
	}
	
	public static void rabbitmqConnectionOpen( ) {
		
		Thread cmThread = null ;
		Thread[ ] dcThread = null ;
		
		ConnectionFactory factory = null ;
		int threadCnt = 0 ;
		int i = 0 ;
		
		try {
			// rabbitmq 수신 connection
			
			logger.info( "================rabbitmqConnectionOpen================" ) ;
			factory = new ConnectionFactory( ) ;
			factory.setHost( "192.168.56.105" ) ;
			factory.setUsername( "admin" ) ;
			factory.setPassword( "admin" ) ;
			
			command2Module = new Command2Module( factory , "Command2Module" ) ;
			cmThread = new Thread( command2Module , "command2Module-Thread" ) ;
			cmThread.start( ) ;
			
			// multi thread 실행
			
			threadCnt = 5 ;
			
			data2ConnectivityArr = new Data2Connectivity[ threadCnt ] ;
			dcThread = new Thread[ threadCnt ] ;
			for( i = 0 ; i < threadCnt ; i++ ) {
				
				data2ConnectivityArr[ i ] = new Data2Connectivity( factory , ( "ESS01" + i ) ) ;
				
				dcThread[ i ] = new Thread( data2ConnectivityArr[ i ] , ( "data2Connectivity-Thread-" + i ) ) ;
				dcThread[ i ].start( ) ;
			}
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			
		}
		
	}
	
	public static void rabbitmqConnectionClose( ) {
		
		logger.info( "================rabbitmqConnectionClose================" ) ;
		
		int i = 0 ;
		try {
			
			// 플랫폼 제어 rabbitmq connection 종료
			command2Module.connectionCut( ) ;
			
			// 데이터 계측 rabbitmq connection 종료
			if( data2ConnectivityArr != null ) {
				
				logger.info( "data2ConnectivityArr.length :: " + data2ConnectivityArr.length ) ;
				
				for( i = 0 ; i < data2ConnectivityArr.length ; i++ ) {
					
					data2ConnectivityArr[ i ].connectionCut( ) ;
				}
			}
			
			// data2Connectivity.connectionCut( );
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			i = 0 ;
		}
		
		return ;
	}
	
}
