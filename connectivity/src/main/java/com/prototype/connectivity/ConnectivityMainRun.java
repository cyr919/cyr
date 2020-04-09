package com.prototype.connectivity ;

import org.apache.log4j.Logger ;

import com.rabbitmq.client.ConnectionFactory ;

public class ConnectivityMainRun
{
	static Logger logger = Logger.getLogger( ConnectivityMainRun.class ) ;
	
	public static Command2Module command2Module = null ;
	public static Data2Connectivity[ ] data2ConnectivityArr = null ;
	
	public static ConditionReport conditionReport = null ;
	public static Thread ctThread = null ;
	
	public static void main( String[ ] args ) {
		
		ConnectivityMainRun exe = new ConnectivityMainRun( ) ;
		
		exe.connectivityRun( "eventId01" ) ;
		
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
	
	public void connectivityRun( String strEventID ) {
		
		logger.info( "strEventID :: " + strEventID ) ;
		try {
			
			// rabbitmaConnection 연결
			this.rabbitmqConnectionOpen( ) ;
			
			// 상태 보고 실행
			conditionReport = new ConditionReport( 30 ) ;
			ctThread = new Thread( conditionReport , "conditionReport-Thread" ) ;
			ctThread.start( );
			
			// TODO 기동 이벤트 추가 필요
			logger.info( "connectivityRun 성공" ) ;
		}
		catch( Exception e ) {
			logger.error( "connectivityRun 실패" ) ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strEventID = null ;
			
		}
		
		return ;
	}
	
	public void connectivityStop( String strEventID ) {
		logger.info( "strEventID :: " + strEventID ) ;
		try {
			this.rabbitmqConnectionClose( ) ;
			
			// 중간에 다른 처리 넣기
			
			
			// 상태 보고 중지
			conditionReport.setStopReport( );
			ctThread.interrupt( ); 
			
			logger.info( "connectivityStop 성공" ) ;
		}
		catch( Exception e ) {
			logger.error( "connectivityStop 실패" ) ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strEventID = null ;
			
		}
		
		return ;
		
	}
	
	public void connectivityReset( String strEventID ) {
		
		logger.info( "strEventID :: " + strEventID ) ;
		try {
			// 처리 정지 처리
			this.rabbitmqConnectionClose( ) ;
			
			// 처리 정지 시작 처리
			this.rabbitmqConnectionOpen( ) ;
			
			logger.info( "connectivityReset 성공" ) ;
		}
		catch( Exception e ) {
			logger.error( "connectivityReset 실패" ) ;
			logger.error( e.getMessage( ) , e ) ;
			
		}
		finally {
			strEventID = null ;
			
		}
		
		return ;
	}
	
	public void connectivityDeviceSimulDataReset( String strEventID ) {
		
		logger.info( "strEventID :: " + strEventID ) ;
		try {
			
			logger.info( "connectivityDeviceSimulDataReset 성공" ) ;
		}
		catch( Exception e ) {
			logger.error( "connectivityDeviceSimulDataReset 실패" ) ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strEventID = null ;
		}
		return ;
		
	}
	
	public void rabbitmqConnectionOpen( ) throws Exception {
		
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
			
			// multi thread 실행(rabbitmq 수신 connection)
			
			threadCnt = 5 ;
			data2ConnectivityArr = new Data2Connectivity[ threadCnt ] ;
			dcThread = new Thread[ threadCnt ] ;
			for( i = 0 ; i < threadCnt ; i++ ) {
				
				data2ConnectivityArr[ i ] = new Data2Connectivity( factory , ( "ESS-" + i ) ) ;
				
				dcThread[ i ] = new Thread( data2ConnectivityArr[ i ] , ( "data2Connectivity-Thread-" + i ) ) ;
				dcThread[ i ].start( ) ;
			}
			
		}
		finally {
			factory = null ;
			threadCnt = 0 ;
			i = 0 ;
		}
		
	}
	
	public void rabbitmqConnectionClose( ) throws Exception {
		
		logger.info( "================rabbitmqConnectionClose================" ) ;
		
		int i = 0 ;
		try {
			
			// 플랫폼 제어 rabbitmq connection 종료
			command2Module.connectionClose( ) ;
			
			// 데이터 계측 rabbitmq connection 종료
			if( data2ConnectivityArr != null ) {
				
				logger.info( "data2ConnectivityArr.length :: " + data2ConnectivityArr.length ) ;
				
				for( i = 0 ; i < data2ConnectivityArr.length ; i++ ) {
					
					data2ConnectivityArr[ i ].connectionClose( ) ;
				}
			}
			ConnectivityMainRun.command2Module = null ;
			ConnectivityMainRun.data2ConnectivityArr = null ;
			
		}
		finally {
			i = 0 ;
		}
		
		return ;
	}
	
}
