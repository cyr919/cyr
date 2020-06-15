package com.connectivity ;

import java.util.HashMap ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.common.CommonProperties ;
import com.connectivity.common.ConnectivityProperties ;
import com.connectivity.config.RabbitmqConnection ;
import com.connectivity.control.receiver.Control2ConnectivityReceiver ;
import com.connectivity.gather.receiver.Data2ConnectivityReceiver ;
import com.connectivity.manage.ConditionReport ;
import com.connectivity.manage.receiver.Command2ModuleReceiver ;
import com.rabbitmq.client.ConnectionFactory ;

public class ConnectivityMainRun
{
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	static final Logger logger = LogManager.getLogger( ConnectivityMainRun.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	// rabbitMQ sub 관련
	// 모듈 제어
	public static Command2ModuleReceiver command2ModuleReceiver = null ;
	// 디바이스 데이터 계측
	public static Data2ConnectivityReceiver[ ] data2ConnectivityReceiverArr = null ;
	// 디바이스 제어
	public static Control2ConnectivityReceiver control2ConnectivityReceiver = null ;
	
	// 상태 보고 관련
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
		
		CommonProperties commonProperties = new CommonProperties( ) ;
		ConnectivityProperties connectivityProperties = new ConnectivityProperties( ) ;
		
		Boolean resultBool01 = true ;
		Boolean resultBool02 = true ;
		long currentPid = 0L ;
		logger.info( "strEventID :: " + strEventID ) ;
		try {
			
			resultBool01 = false ;
			resultBool02 = false ;
			
			// db, redis, qeueu 연결 후
			// 기동 이벤트 전송
			// 받은 이벤트 이력 부모 아이디가 없으면 빈 값으로 처리
			
			// 공통 프로퍼티(프로퍼티 파일에 있는 설정) 읽어서 static 변수에 저장
			resultBool01 = commonProperties.setProperties( ) ;
			
			// connectivity 프로퍼티(db에 있는 mgp 설정) 읽어서 static 변수에 저장
			resultBool02 = connectivityProperties.setConnectivityProperties( ) ;
			
			if( resultBool01 && resultBool02 ) {
				
				logger.info( "ConnectivityProperties.STDV_INF :: " + ConnectivityProperties.STDV_INF ) ;
				
				// rabbitmqConnection 연결
				// 설치 디바이스 수 많큼 queue connection이 실행된다.
				this.rabbitmqConnectionOpen( ConnectivityProperties.STDV_INF ) ;
				
				logger.info( "ConnectivityProperties.STDV_INF :: " + ConnectivityProperties.STDV_INF ) ;
				
				//// 상태 보고 실행
				// PID 가져오기
				currentPid = ProcessHandle.current( ).pid( ) ;
				logger.info( "currentPid :: " + currentPid ) ;
				// TODO 프로세스 아이디 처리필요
				// 상태보고 thread 실행
				conditionReport = new ConditionReport( 10 , "Connectivity" , ( currentPid + "" ) ) ;
				// ctThread = new Thread( conditionReport , "conditionReport-Thread" ) ;
				ctThread = new Thread( conditionReport ) ;
				ctThread.start( ) ;
				
				// TODO 기동 이벤트 추가 필요
				logger.info( "connectivityRun 성공" ) ;
			}
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
			// 중지 이벤트
			
			this.rabbitmqConnectionClose( ) ;
			
			// 중간에 다른 처리 넣기
			
			// 상태 보고 중지
			conditionReport.setStopReport( ) ;
			ctThread.interrupt( ) ;
			
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
			
			// 처리 시작 처리
			this.rabbitmqConnectionOpen( ConnectivityProperties.STDV_INF ) ;
			
			// 리셋 완료 이벤트
			
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
	
	public void rabbitmqConnectionOpen( HashMap< String , HashMap< String , Object > > staticDiviceInfo ) throws Exception {
		
		RabbitmqConnection rabbitmqConnection = new RabbitmqConnection( ) ;
		
		Thread cmThread = null ;
		Thread ccThread = null ;
		Thread[ ] dcThread = null ;
		
		ConnectionFactory factory = null ;
		int threadCnt = 0 ;
		int i = 0 ;
		try {
			
			// rabbitmq 수신 connection
			logger.info( "================rabbitmqConnectionOpen================" ) ;
			
			logger.info( "staticDiviceInfo :: " + staticDiviceInfo ) ;
			
			factory = rabbitmqConnection.getConnectionFactory( ) ;
			
			// service hub 모듈 제어
			command2ModuleReceiver = new Command2ModuleReceiver( factory ) ;
			// cmThread = new Thread( command2ModuleReceiver , "command2ModuleReceiver-Thread" ) ;
			cmThread = new Thread( command2ModuleReceiver ) ;
			cmThread.start( ) ;
			
			// 디바이스 제어
			control2ConnectivityReceiver = new Control2ConnectivityReceiver( factory ) ;
			ccThread = new Thread( control2ConnectivityReceiver ) ;
			ccThread.start( ) ;
			
			// multi thread 실행(rabbitmq 수신 connection)
			// adaptor 디바이스 계측
			threadCnt = staticDiviceInfo.size( ) ;
			data2ConnectivityReceiverArr = new Data2ConnectivityReceiver[ threadCnt ] ;
			dcThread = new Thread[ threadCnt ] ;
			
			int cntMapKey = 0 ;
			
			for( String key : staticDiviceInfo.keySet( ) ) {
				logger.info( "cntMapKey :: " + cntMapKey ) ;
				
				System.out.println( String.format( "키 : %s, 값 : %s" , key , staticDiviceInfo.get( key ) ) ) ;
				
				data2ConnectivityReceiverArr[ cntMapKey ] = new Data2ConnectivityReceiver( factory , ( key ) ) ;
				
				// dcThread[ cntMapKey ] = new Thread( data2ConnectivityReceiverArr[ cntMapKey ] , ( "data2Connectivity-Thread-" + key ) ) ;
				dcThread[ cntMapKey ] = new Thread( data2ConnectivityReceiverArr[ cntMapKey ] ) ;
				dcThread[ cntMapKey ].start( ) ;
				cntMapKey = cntMapKey + 1 ;
			}
			
			logger.info( "staticDiviceInfo :: " + staticDiviceInfo ) ;
			
			// rabbitmq PUB connection
			
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
			
			// 모듈 제어 rabbitmq connection 종료
			command2ModuleReceiver.connectionClose( ) ;
			
			// 디바이스 제어 rabbitmq connection 종료
			control2ConnectivityReceiver.connectionClose( ) ;
			
			// 데이터 계측 rabbitmq connection 종료
			if( data2ConnectivityReceiverArr != null ) {
				
				logger.info( "data2ConnectivityArr.length :: " + data2ConnectivityReceiverArr.length ) ;
				
				for( i = 0 ; i < data2ConnectivityReceiverArr.length ; i++ ) {
					
					data2ConnectivityReceiverArr[ i ].connectionClose( ) ;
				}
			}
			ConnectivityMainRun.command2ModuleReceiver = null ;
			ConnectivityMainRun.control2ConnectivityReceiver = null ;
			ConnectivityMainRun.data2ConnectivityReceiverArr = null ;
			
		}
		finally {
			i = 0 ;
		}
		
		return ;
	}
	
}
