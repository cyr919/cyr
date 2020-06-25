package com.connectivity ;

import java.util.List ;
import java.util.Map ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.calculate.BetweenDevicesCalculateExecute ;
import com.connectivity.common.CommonProperties ;
import com.connectivity.common.ConnectivityProperties ;
import com.connectivity.config.JedisConnection ;
import com.connectivity.config.MongodbConnection ;
import com.connectivity.config.RabbitmqConnection ;
import com.connectivity.control.receiver.Control2ConnectivityReceiver ;
import com.connectivity.gather.receiver.Data2ConnectivityReceiver ;
import com.connectivity.manage.ConditionReport ;
import com.connectivity.manage.receiver.Command2ModuleReceiver ;
import com.rabbitmq.client.ConnectionFactory ;

/**
 * <pre>
 * connectivity 기동
 * </pre>
 *
 * @author cyr
 * @date 2020-06-18
 */
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
	public static Thread crThread = null ;
	
	// 장치간 연산
	public static BetweenDevicesCalculateExecute betweenDevicesCalculateExecute = null ;
	public static Thread btwDvCalcuExeThread = null ;
	
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
	
	/**
	 * <pre>
	 * connectivity 기동
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-18
	 * @param strEventID 이벤트 히스토리 id
	 */
	public void connectivityRun( String strEventID ) {
		
		CommonProperties commonProperties = new CommonProperties( ) ;
		ConnectivityProperties connectivityProperties = new ConnectivityProperties( ) ;
		JedisConnection jedisConnection = new JedisConnection( ) ;
		MongodbConnection mongodbConnection = new MongodbConnection( ) ;
		
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
			if( commonProperties.setProperties( ) ) {
				jedisConnection.getJedisPool( ) ;
				mongodbConnection.getMongoClient( ) ;
				
				connectivityProperties.threadPoolExecute( ) ;
				
				// connectivity 프로퍼티(db에 있는 mgp 설정) 읽어서 static 변수에 저장
				if( connectivityProperties.setConnectivityProperties( ) ) {
					
					resultBool01 = true ;
					resultBool02 = true ;
					
					// db에 있는 로그 설정정보 조회하여 로그 정보 변경.
				}
			}
			
			if( resultBool01 && resultBool02 ) {
				
				logger.info( "ConnectivityProperties.STDV_INF :: " + ConnectivityProperties.STDV_INF ) ;
				
				//// rabbitmqConnection 연결
				// 설치 디바이스 수 많큼 queue connection이 실행된다.
				this.rabbitmqSubConnectionOpen( ConnectivityProperties.STDV_INF ) ;
				this.rabbitmqCommandModuleSubConnectionOpen( ) ;
				this.rabbitmqPubConnectionOpen( ) ;
				
				logger.info( "ConnectivityProperties.STDV_INF :: " + ConnectivityProperties.STDV_INF ) ;
				
				//// 상태 보고 실행
				// PID 가져오기
				currentPid = ProcessHandle.current( ).pid( ) ;
				logger.info( "currentPid :: " + currentPid ) ;
				// TODO 프로세스 아이디 처리필요
				// 상태보고 thread 실행
				conditionReport = new ConditionReport( 10 , "Connectivity" , ( currentPid + "" ) ) ;
				// crThread = new Thread( conditionReport , "conditionReport-Thread" ) ;
				crThread = new Thread( conditionReport ) ;
				crThread.setPriority( 5 ) ;
				crThread.start( ) ;
				
				// 장치간 연산 시작
				this.betweenDevicesCalculateStart( ) ;
				
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
			commonProperties = null ;
			connectivityProperties = null ;
			jedisConnection = null ;
			mongodbConnection = null ;
			resultBool01 = null ;
			resultBool02 = null ;
			currentPid = 0L ;
		}
		
		return ;
	}
	
	/**
	 * <pre>
	 * connectivity 중지
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-18
	 * @param strEventID 이벤트 히스토리 id
	 */
	public void connectivityStop( String strEventID ) {
		logger.info( "strEventID :: " + strEventID ) ;
		
		ConnectivityProperties connectivityProperties = new ConnectivityProperties( ) ;
		
		try {
			// TODO 중지 이벤트
			
			// 장치간 연산 중지
			this.betweenDevicesCalculateStop( ) ;
			
			// rabbitmq Sub connection 종료
			this.rabbitmqSubConnectionClose( ) ;
			this.rabbitmqCommandModuleSubConnectionClose( ) ;
			// 중간에 다른 처리 넣기
			
			// 실행 스레드 수 체크
			logger.info( "connectivityProperties.getProcessThreadCnt( ) :: " + connectivityProperties.getProcessThreadCnt( ) ) ;
			// 1은 Command2ModuleReceiver 의 처리 중인 thread이다. 정지 처리가 종료된 후 삭제된다.
			while( 1 < connectivityProperties.getProcessThreadCnt( ) ) {
				logger.info( "connectivityProperties.getProcessThreadCnt( ) :while: " + connectivityProperties.getProcessThreadCnt( ) ) ;
				
				Thread.sleep( 100 ) ;
			}
			
			logger.info( "connectivityProperties.getProcessThreadCnt( ) :end: " + connectivityProperties.getProcessThreadCnt( ) ) ;
			
			// Thread pool 종료
			connectivityProperties.threadPoolShutdown( ) ;
			
			// rabbitmq Pub connection 종료
			this.rabbitmqPubConnectionClose( ) ;
			
			logger.info( "connectivityStop 성공" ) ;
		}
		catch( Exception e ) {
			logger.error( "connectivityStop 실패" ) ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			
			try {
				// 상태 보고 중지
				conditionReport.setStopReport( ) ;
				crThread.interrupt( ) ;
			}
			catch( Exception e ) {
				logger.error( e.getMessage( ) , e ) ;
			}
			
			strEventID = null ;
			
		}
		
		return ;
		
	}
	
	/**
	 * <pre>
	 * connectivity reset
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-18
	 * @param strEventID 이벤트 히스토리 id
	 */
	public void connectivityReset( String strEventID ) {
		
		logger.info( "strEventID :: " + strEventID ) ;
		
		ConnectivityProperties connectivityProperties = new ConnectivityProperties( ) ;
		
		try {
			// 처리 정지 처리
			
			// 장치간 연산 중지
			this.betweenDevicesCalculateStop( ) ;
			
			// rabbitmq sub connectino 연결 종료
			this.rabbitmqSubConnectionClose( ) ;
			
			// 실행 스레드 수 체크
			logger.info( "connectivityProperties.getProcessThreadCnt( ) :: " + connectivityProperties.getProcessThreadCnt( ) ) ;
			// 1은 Command2ModuleReceiver 의 처리 중인 thread이다. 정지 처리가 종료된 후 삭제된다.
			while( 1 < connectivityProperties.getProcessThreadCnt( ) ) {
				logger.info( "connectivityProperties.getProcessThreadCnt( ) :while: " + connectivityProperties.getProcessThreadCnt( ) ) ;
				
				Thread.sleep( 100 ) ;
			}
			
			logger.info( "connectivityProperties.getProcessThreadCnt( ) :end: " + connectivityProperties.getProcessThreadCnt( ) ) ;
			
			// 설정 재설정
			connectivityProperties.setConnectivityProperties( ) ;
			
			// rabbitmq sub connectino 연결
			this.rabbitmqSubConnectionOpen( ConnectivityProperties.STDV_INF ) ;
			
			// 장치간 연산 시작
			this.betweenDevicesCalculateStart( ) ;
			
			// 리셋 완료 이벤트
			
			logger.info( "connectivityReset 성공" ) ;
		}
		catch( Exception e ) {
			logger.error( "connectivityReset 실패" ) ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strEventID = null ;
			connectivityProperties = null ;
		}
		
		return ;
	}
	
	public void connectivityDeviceSimulDataReset( String strEventID , List< ? > deviceIdList ) {
		
		logger.info( "strEventID :: " + strEventID ) ;
		ConnectivityProperties connectivityProperties = new ConnectivityProperties( ) ;
		
		try {
			// 처리 정지 처리
			
			// 장치간 연산 중지
			this.betweenDevicesCalculateStop( ) ;
			
			// rabbitmq sub connectino 연결 종료
			this.rabbitmqSubConnectionClose( ) ;
			
			// 실행 스레드 수 체크
			logger.info( "connectivityProperties.getProcessThreadCnt( ) :: " + connectivityProperties.getProcessThreadCnt( ) ) ;
			// 1은 Command2ModuleReceiver 의 처리 중인 thread이다. 정지 처리가 종료된 후 삭제된다.
			while( 1 < connectivityProperties.getProcessThreadCnt( ) ) {
				logger.info( "connectivityProperties.getProcessThreadCnt( ) :while: " + connectivityProperties.getProcessThreadCnt( ) ) ;
				
				Thread.sleep( 100 ) ;
			}
			
			logger.info( "connectivityProperties.getProcessThreadCnt( ) :end: " + connectivityProperties.getProcessThreadCnt( ) ) ;
			
			// 설정 재설정
			connectivityProperties.setConnectivityPropertiesDeviceSimulDataReset( deviceIdList ) ;
			
			// rabbitmq sub connectino 연결
			this.rabbitmqSubConnectionOpen( ConnectivityProperties.STDV_INF ) ;
			
			// 장치간 연산 시작
			this.betweenDevicesCalculateStart( ) ;
			
			// 리셋 완료 이벤트
			
			logger.info( "connectivityDeviceSimulDataReset 성공" ) ;
		}
		catch( Exception e ) {
			logger.error( "connectivityDeviceSimulDataReset 실패" ) ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strEventID = null ;
			deviceIdList = null ;
			connectivityProperties = null ;
		}
		return ;
		
	}
	
	public void connectivitySiteSimulModeOn( String strEventID ) {
		
		logger.info( "strEventID :: " + strEventID ) ;
		ConnectivityProperties connectivityProperties = new ConnectivityProperties( ) ;
		
		try {
			// 처리 정지 처리
			
			// 장치간 연산 중지
			this.betweenDevicesCalculateStop( ) ;
			
			// rabbitmq sub connectino 연결 종료
			this.rabbitmqSubConnectionClose( ) ;
			
			// 실행 스레드 수 체크
			logger.info( "connectivityProperties.getProcessThreadCnt( ) :: " + connectivityProperties.getProcessThreadCnt( ) ) ;
			// 1은 Command2ModuleReceiver 의 처리 중인 thread이다. 정지 처리가 종료된 후 삭제된다.
			while( 1 < connectivityProperties.getProcessThreadCnt( ) ) {
				logger.info( "connectivityProperties.getProcessThreadCnt( ) :while: " + connectivityProperties.getProcessThreadCnt( ) ) ;
				
				Thread.sleep( 100 ) ;
			}
			
			logger.info( "connectivityProperties.getProcessThreadCnt( ) :end: " + connectivityProperties.getProcessThreadCnt( ) ) ;
			
			// 설정 재설정
			connectivityProperties.setConnectivityPropertiesSimulModeOn( ) ;
			
			// rabbitmq sub connectino 연결
			this.rabbitmqSubConnectionOpen( ConnectivityProperties.STDV_INF ) ;
			
			// 장치간 연산 시작
			this.betweenDevicesCalculateStart( ) ;
			
			// 리셋 완료 이벤트
			
			logger.info( "connectivitySiteSimulModeOn 성공" ) ;
		}
		catch( Exception e ) {
			logger.error( "connectivitySiteSimulModeOn 실패" ) ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strEventID = null ;
			connectivityProperties = null ;
			
		}
		return ;
		
	}
	
	public void connectivitySiteSimulModeOff( String strEventID ) {
		
		logger.info( "strEventID :: " + strEventID ) ;
		ConnectivityProperties connectivityProperties = new ConnectivityProperties( ) ;
		
		try {
			// 처리 정지 처리
			
			// 장치간 연산 중지
			this.betweenDevicesCalculateStop( ) ;
			
			// rabbitmq sub connectino 연결 종료
			this.rabbitmqSubConnectionClose( ) ;
			
			// 실행 스레드 수 체크
			logger.info( "connectivityProperties.getProcessThreadCnt( ) :: " + connectivityProperties.getProcessThreadCnt( ) ) ;
			// 1은 Command2ModuleReceiver 의 처리 중인 thread이다. 정지 처리가 종료된 후 삭제된다.
			while( 1 < connectivityProperties.getProcessThreadCnt( ) ) {
				logger.info( "connectivityProperties.getProcessThreadCnt( ) :while: " + connectivityProperties.getProcessThreadCnt( ) ) ;
				
				Thread.sleep( 100 ) ;
			}
			
			logger.info( "connectivityProperties.getProcessThreadCnt( ) :end: " + connectivityProperties.getProcessThreadCnt( ) ) ;
			
			// 설정 재설정
			connectivityProperties.setConnectivityPropertiesSimulModeOff( ) ;
			
			// rabbitmq sub connectino 연결
			this.rabbitmqSubConnectionOpen( ConnectivityProperties.STDV_INF ) ;
			
			// 장치간 연산 시작
			this.betweenDevicesCalculateStart( ) ;
			
			// 리셋 완료 이벤트
			
			logger.info( "connectivitySiteSimulModeOff 성공" ) ;
		}
		catch( Exception e ) {
			logger.error( "connectivitySiteSimulModeOff 실패" ) ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strEventID = null ;
			connectivityProperties = null ;
			
		}
		return ;
		
	}
	
	/**
	 * <pre>
	 * connectivity의 모든
	 * rabbitMQ connection 연결
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-18
	 * @param staticDiviceInfo 설치 디바이스 정보
	 * @throws Exception
	 */
	public void rabbitmqSubConnectionOpen( Map< String , Map< String , Object > > staticDiviceInfo ) throws Exception {
		
		RabbitmqConnection rabbitmqConnection = new RabbitmqConnection( ) ;
		
		Thread c2cThread = null ;
		Thread[ ] d2cThread = null ;
		
		ConnectionFactory factory = null ;
		int threadCnt = 0 ;
		
		try {
			
			// rabbitmq 수신 connection
			logger.info( "================rabbitmqSubConnectionOpen================" ) ;
			
			logger.info( "staticDiviceInfo :: " + staticDiviceInfo ) ;
			
			factory = rabbitmqConnection.getConnectionFactory( ) ;
			
			// 디바이스 제어
			control2ConnectivityReceiver = new Control2ConnectivityReceiver( factory ) ;
			c2cThread = new Thread( control2ConnectivityReceiver ) ;
			c2cThread.start( ) ;
			
			// multi thread 실행(rabbitmq 수신 connection)
			// adaptor 디바이스 계측
			threadCnt = staticDiviceInfo.size( ) ;
			data2ConnectivityReceiverArr = new Data2ConnectivityReceiver[ threadCnt ] ;
			d2cThread = new Thread[ threadCnt ] ;
			
			int cntMapKey = 0 ;
			
			for( String key : staticDiviceInfo.keySet( ) ) {
				logger.info( "cntMapKey :: " + cntMapKey ) ;
				
				System.out.println( String.format( "키 : %s, 값 : %s" , key , staticDiviceInfo.get( key ) ) ) ;
				
				data2ConnectivityReceiverArr[ cntMapKey ] = new Data2ConnectivityReceiver( factory , ( key ) ) ;
				
				// dcThread[ cntMapKey ] = new Thread( data2ConnectivityReceiverArr[ cntMapKey ] , ( "data2Connectivity-Thread-" + key ) ) ;
				d2cThread[ cntMapKey ] = new Thread( data2ConnectivityReceiverArr[ cntMapKey ] ) ;
				d2cThread[ cntMapKey ].start( ) ;
				cntMapKey = cntMapKey + 1 ;
			}
			
			logger.info( "staticDiviceInfo :: " + staticDiviceInfo ) ;
			
		}
		finally {
			staticDiviceInfo = null ;
			rabbitmqConnection = null ;
			factory = null ;
			threadCnt = 0 ;
			
			// c2mThread = null ;
			// c2cThread = null ;
			// d2cThread = null ;
		}
		
	}
	
	public void rabbitmqCommandModuleSubConnectionOpen( ) throws Exception {
		
		RabbitmqConnection rabbitmqConnection = new RabbitmqConnection( ) ;
		ConnectionFactory factory = null ;
		
		Thread c2mThread = null ;
		try {
			// rabbitmq 수신 connection
			logger.info( "================rabbitmqSubConnectionOpen================" ) ;
			factory = rabbitmqConnection.getConnectionFactory( ) ;
			
			// service hub 모듈 제어
			command2ModuleReceiver = new Command2ModuleReceiver( factory ) ;
			// cmThread = new Thread( command2ModuleReceiver , "command2ModuleReceiver-Thread" ) ;
			c2mThread = new Thread( command2ModuleReceiver ) ;
			c2mThread.start( ) ;
			
		}
		finally {
			rabbitmqConnection = null ;
			factory = null ;
			
			// c2mThread = null ;
		}
	}
	
	public void rabbitmqPubConnectionOpen( ) throws Exception {
		
		RabbitmqConnection rabbitmqConnection = new RabbitmqConnection( ) ;
		
		try {
			logger.info( "================rabbitmqPubConnectionOpen================" ) ;
			
			// rabbitmq PUB connection
			rabbitmqConnection.getDeviceControlConnection( ) ;
			rabbitmqConnection.getEventConnection( ) ;
		}
		finally {
			rabbitmqConnection = null ;
			
		}
		
	}
	
	/**
	 * <pre>
	 * connectivity의 모든
	 * rabbitMQ connection 종료
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-18
	 * @throws Exception
	 */
	public void rabbitmqSubConnectionClose( ) throws Exception {
		
		logger.info( "================rabbitmqSubConnectionClose================" ) ;
		
		int i = 0 ;
		try {
			// 디바이스 제어 rabbitmq connection 종료
			control2ConnectivityReceiver.connectionClose( ) ;
			
			// 데이터 계측 rabbitmq connection 종료
			if( data2ConnectivityReceiverArr != null ) {
				
				logger.info( "data2ConnectivityArr.length :: " + data2ConnectivityReceiverArr.length ) ;
				
				for( i = 0 ; i < data2ConnectivityReceiverArr.length ; i++ ) {
					
					data2ConnectivityReceiverArr[ i ].connectionClose( ) ;
				}
			}
			ConnectivityMainRun.control2ConnectivityReceiver = null ;
			ConnectivityMainRun.data2ConnectivityReceiverArr = null ;
			
		}
		finally {
			i = 0 ;
		}
		
		return ;
	}
	
	public void rabbitmqCommandModuleSubConnectionClose( ) throws Exception {
		
		logger.info( "================rabbitmqCommandModuleSubConnectionClose================" ) ;
		try {
			// 모듈 제어 rabbitmq connection 종료
			command2ModuleReceiver.connectionClose( ) ;
			ConnectivityMainRun.command2ModuleReceiver = null ;
		}
		finally {
		}
		
		return ;
	}
	
	public void rabbitmqPubConnectionClose( ) throws Exception {
		
		logger.info( "================rabbitmqPubConnectionClose================" ) ;
		
		RabbitmqConnection rabbitmqConnection = new RabbitmqConnection( ) ;
		try {
			
			// rabbitmq PUB connection 종료
			rabbitmqConnection.closePubConnection( ) ;
		}
		finally {
			rabbitmqConnection = null ;
		}
		
		return ;
	}
	
	public void betweenDevicesCalculateStart( ) throws Exception {
		//// 장치간 연산 10ms 단위는 안됨.
		// betweenDevicesCalculateExecute = new BetweenDevicesCalculateExecute( 10 ) ;
		betweenDevicesCalculateExecute = new BetweenDevicesCalculateExecute( 20 * 1000 ) ;
		btwDvCalcuExeThread = new Thread( betweenDevicesCalculateExecute ) ;
		btwDvCalcuExeThread.setPriority( 5 ) ;
		btwDvCalcuExeThread.start( ) ;
		
	}
	
	public void betweenDevicesCalculateStop( ) throws Exception {
		
		betweenDevicesCalculateExecute.setStopExeThread( ) ;
		btwDvCalcuExeThread.interrupt( ) ;
		
	}
	
}
