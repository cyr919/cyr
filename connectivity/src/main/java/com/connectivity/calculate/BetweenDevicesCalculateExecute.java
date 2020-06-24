/**
 * 
 */
package com.connectivity.calculate ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

/**
 * <pre>
 * 장치간 연산
 * </pre>
 *
 * @author cyr
 * @date 2020-06-10
 */
public class BetweenDevicesCalculateExecute implements Runnable
{
	
	private Logger logger = LogManager.getLogger( this.getClass( ) ) ;
	
	private boolean isDemonLive = true ;
	private int intExeInterval = 0 ;
	
	/**
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-10
	 * @param args
	 */
	// public static void main( String[ ] args ) {
	// // TODO Auto-generated method stub
	//
	// CommonProperties commonProperties = new CommonProperties( ) ;
	// JedisConnection jedisConnection = new JedisConnection( ) ;
	// MongodbConnection mongodbConnection = new MongodbConnection( ) ;
	// ConnectivityProperties connectivityProperties = new ConnectivityProperties( ) ;
	//
	// try {
	// commonProperties.setProperties( ) ;
	// jedisConnection.getJedisPool( ) ;
	// mongodbConnection.getMongoClient( ) ;
	//
	// connectivityProperties.setConnectivityProperties( ) ;
	// }
	// catch( Exception e ) {
	// // TODO Auto-generated catch block
	// e.printStackTrace( ) ;
	// }
	//
	// try {
	// // BetweenDevicesCalculateExecute exe = new BetweenDevicesCalculateExecute( 100 ) ;
	// //
	// // Thread btwDvCalcuExeThread = new Thread( exe ) ;
	// // btwDvCalcuExeThread.start( ) ;
	// //
	// // Thread.sleep( 1 * 1000 ) ;
	// // exe.setStopExeThread( ) ;
	// // btwDvCalcuExeThread.interrupt( );
	//
	// }
	// // catch( InterruptedException e ) {
	// // // TODO Auto-generated catch block
	// // e.printStackTrace( ) ;
	// // }
	// catch( Exception e ) {
	// e.printStackTrace( ) ;
	// }
	//
	// }
	
	public BetweenDevicesCalculateExecute( int intExeInterval ) {
		
		try {
			this.isDemonLive = true ;
			// this.intExeInterval = ( intExeInterval * 1000 ) ;
			this.intExeInterval = intExeInterval ;
		}
		catch( Exception e ) {
			logger.debug( e.getMessage( ) , e ) ;
		}
		finally {
			intExeInterval = 0 ;
		}
		
		return ;
	}
	
	@Override
	public void run( ) {
		
		BetweenDevicesCalculate betweenDevicesCalculate = null ;
		// Thread btwDvCalcuThread = null ;
		
		int checkInt = 0 ;
		
		try {
			
			logger.info( "BetweenDevicesCalculateExecute run 시작" ) ;
			
			while( this.isDemonLive ) {
				checkInt ++ ;
				logger.info( "BetweenDevicesCalculateExecute BetweenDevicesCalculate run 시작 :: checkInt :: " +checkInt ) ;
				
				betweenDevicesCalculate = new BetweenDevicesCalculate( ) ;
				Thread btwDvCalcuThread = new Thread( betweenDevicesCalculate , ( "btwDvCalcuThread" + checkInt) ) ;
				btwDvCalcuThread.setPriority( 6 );
				btwDvCalcuThread.start( ) ;
				
				// betweenDevicesCalculate = null ;
				
				Thread.sleep( this.intExeInterval ) ;
			}
			
			logger.info( "while 종료 :: isDemonLive :: " + isDemonLive ) ;
		}
		catch( InterruptedException e ) {
			logger.info( "BetweenDevicesCalculateExecute run 종료 요청 :: interrupt" ) ;
			logger.error( e.getMessage( ) , e ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			betweenDevicesCalculate = null ;
			
			logger.info( "BetweenDevicesCalculateExecute run 종료" ) ;
		}
	}
	
	public void setStopExeThread( ) throws Exception {
		
		logger.info( "BetweenDevicesCalculateExecute run 종료 요청" ) ;
		this.isDemonLive = false ;
		
		return ;
	}
	
}
