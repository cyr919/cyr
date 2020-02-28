
package com.prototype ;

import java.io.IOException ;
import java.io.InputStream ;
import java.io.OutputStream ;
import java.net.InetAddress ;
import java.net.ServerSocket ;
import java.net.Socket ;
import java.security.AccessControlException ;
import java.util.Date ;
import java.util.Random ;
import java.util.Timer ;
import java.util.TimerTask ;

/**
 * com.prototype.Server.java
 * <pre>
 * 실행/종료 : java Server start / java Server shutdown
 * 출처: https://openbee.kr/234 [오픈비 : 삽질은없다]
 * 
 * 
 * eclipse test 
 * run as > run configurations > Arguments 탭 > 추가항 명령어 넣기 > shutdown/start > run 
 * 
 * </pre>
 * @author cyr
 * @Date 2020. 2. 24.
 */
public class Server
{

	private static final String COMMAND_SHUTDOWN = "shutdown" ;
	private static final int COMMAND_PORT = 8099 ;

	private boolean started ;
	private Random random ;
	private Timer timer ;

	private Server( ) {
		this.started = false ;
		this.random = null ;
		this.timer = null ;
	}

	private void start( )
	{
		// 여기서 서버가 수행하는 쓰레드를 start시킨다(반드시 쓰레드이어야 한다).

		// 에로서 Timer를 사용하여 1초마다 메시지 출력...
		this.timer = new Timer( ) ;

		timer.scheduleAtFixedRate( new TimerTask( ) {

			public void run( )
			{
				if ( started ) System.out.println( "1초마다 메시지" ) ;
			}
		} , new Date( ) , 1000 ) ;

		await( ) ;
	}

	private void stop( )
	{
		// 서버가 수행하던 쓰레드를 중지시킨다.
		// 예로써 Timer 중지...
		this.timer.cancel( ) ;
		this.timer = null ;

		System.out.println( "중지되었습니다." ) ;
	}

	/**
	 * 서버소켓을 열고 shutdown 요청이 있을때까지 대기...
	 */
	protected void await( )
	{
		try
		{
			ServerSocket serverSocket = null ;
			try
			{
				serverSocket = new ServerSocket( COMMAND_PORT , 1 , InetAddress.getByName( "127.0.0.1" ) ) ;
				started = true ;
			}
			catch ( IOException e )
			{
				e.printStackTrace( ) ;
			}
			while ( started )
			{
				Socket socket = null ;
				InputStream stream = null ;
				try
				{
					socket = serverSocket.accept( ) ;
					socket.setSoTimeout( 10 * 1000 ) ; // 타임아웃 10초...
					stream = socket.getInputStream( ) ;
				}
				catch ( AccessControlException e )
				{
					continue ;
				}
				catch ( IOException e )
				{
					System.exit( 1 ) ;
				}

				StringBuffer command = new StringBuffer( ) ;

				// Cut off to avoid DoS attack
				int expected = 1024 ;
				while ( expected < COMMAND_SHUTDOWN.length( ) )
				{
					if ( random == null ) random = new Random( System.currentTimeMillis( ) ) ;

					expected += ( random.nextInt( ) % 1024 ) ;
				}

				while ( expected > 0 )
				{
					int ch = -1 ;
					try
					{
						ch = stream.read( ) ;
					}
					catch ( IOException e )
					{
						ch = -1 ;
					}
					// EOF
					if ( ch < 32 ) break ;

					command.append( ( char ) ch ) ;
					expected-- ;
				}

				// 소켓을 닫는다.
				try
				{
					socket.close( ) ;
				}
				catch ( IOException ignore )
				{
				}

				String cmd = command.toString( ) ;

				// shutdown 명령시 루프 중지
				if ( COMMAND_SHUTDOWN.equals( cmd ) )
				{
					break ;
				}
			}
			// 서버 소켓을 닫는다.
			try
			{
				serverSocket.close( ) ;
			}
			catch ( IOException ignore )
			{
			}
		}
		catch ( Throwable t )
		{
			t.printStackTrace( ) ;
		}
		finally
		{
			// 서버 종료 처리...
			try
			{
				stop( ) ;
			}
			catch ( Throwable ignore )
			{
			}
		}
	}

	/**
	 * 소켓으로 shutdown 커맨드를 보낸다.
	 */
	private static void shutdown( )
	{
		Socket socket = null ;
		OutputStream os = null ;
		try
		{
			socket = new Socket( "127.0.0.1" , COMMAND_PORT ) ;
			os = socket.getOutputStream( ) ;
			for ( int i = 0 ; i < COMMAND_SHUTDOWN.length( ) ; i++ )
			{
				os.write( COMMAND_SHUTDOWN.charAt( i ) ) ;
			}
			os.flush( ) ;
		}
		catch ( Throwable t )
		{
			t.printStackTrace( ) ;
			System.exit( 1 ) ;
		}
		finally
		{
			try
			{
				os.close( ) ;
			}
			catch ( Throwable t )
			{
			}
			try
			{
				socket.close( ) ;
			}
			catch ( Throwable t )
			{
			}
		}
	}

	public static void main( String[ ] args )
	{
		try
		{
			if ( COMMAND_SHUTDOWN.equals( args[ 0 ] ) )
			{
				shutdown( ) ;
			}
			else
			{
				Server server = new Server( ) ;
				server.start( ) ;
			}
		}
		catch ( Throwable t )
		{
			t.printStackTrace( ) ;
		}
	}

}
