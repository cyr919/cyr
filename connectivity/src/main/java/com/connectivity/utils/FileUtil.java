
package com.connectivity.utils ;

import java.io.BufferedReader ;
import java.io.File ;
import java.io.FileInputStream ;
import java.io.FileNotFoundException ;
import java.io.IOException ;
import java.io.InputStreamReader ;

import org.apache.log4j.Logger ;

public class FileUtil
{

	static Logger logger = Logger.getLogger( FileUtil.class ) ;

	public static void main( String[ ] args )
	{
		// TODO Auto-generated method stub

	}

	/**
	 * <pre>
	 * 디렉토리 생성 매서드
	 * 최하위 디렉토리 생성에 실패하면 상위 디렉토리까지 생성.
	 * </pre>
	 * 
	 * @param
	 * @return void
	 * @date 2020. 1. 9.
	 */
	public void makeDir( String strFilePath )
	{

		File folder = null ;

		Boolean resultBool = true ;
		try
		{
			folder = new File( strFilePath ) ;

			// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
			if ( !folder.exists( ) )
			{
				// folder.mkdir( ) ; // 폴더 생성합니다.

				// 최 하위 디렉토리에 대해서만 생성을 함. 최 하위 디렉토리의 바루 상위 디렉토리가 존재하지 않을 경우, 디렉토리가 생성되지 못하고, false를 리턴함
				resultBool = folder.mkdir( ) ;

				if ( resultBool )
				{
					logger.trace( "폴더가 생성되었습니다." ) ;
				}
				else
				{
					logger.trace( "상위 폴더가 존재하지 않습니다." ) ;
					// 폴더 생성합니다.
					// 상위 디렉토리가 존재하지 않을 경우, 상위 디렉토리까지 생성함
					resultBool = folder.mkdirs( ) ;

					if ( resultBool )
					{
						logger.trace( "상위 폴더 포함하여 폴더가 생성되었습니다." ) ;
					}
					else
					{
						logger.trace( "폴더 생성에 실패하였습니다." ) ;
					}
				}
			}
			else
			{
				logger.trace( "이미 폴더가 생성되어 있습니다." ) ;
			}

		}
		catch ( Exception e )
		{
			logger.error( e.getMessage( ) , e ) ;
		}
		finally
		{
			strFilePath = null ;
			folder = null ;
			resultBool = null ;
		}

		return ;
	}

	/**
	 * <pre>
	 * 파일 읽기
	 * 인코딩 - UTF-8
	 * </pre>
	 * 
	 * @param fileName 파일의 전체 이름(경로+파일명)
	 * @return StringBuffer
	 * @date 2020. 2. 27.
	 */
	public StringBuffer fileContentRead( String fileName )
	{
		logger.trace( ":::::::::: fileContentRead " ) ;

		// File 경로
		File file = null ;
		// File Input 스트림 생성
		FileInputStream fileInputStream = null ;

		// Input 스트림 생성
		InputStreamReader inputStreamReader = null ;
		// 버퍼 생성
		BufferedReader bufferedReader = null ;
		// 버퍼로 읽어들일 임시 변수
		String strTempLine = "" ;
		// 최종 내용 출력을 위한 변수
		StringBuffer resultStrBuffer = new StringBuffer( ) ;

		int intBuffer = 1024 ;

		try
		{
			logger.trace( "fileName :: " + fileName ) ;

			if ( !CommUtil.checkNull( fileName ) )
			{
				// File 경로
				file = new File( fileName ) ;
				// 파일을 읽어들여 File Input 스트림 객체 생성
				fileInputStream = new FileInputStream( file ) ;
				// File Input 스트림 객체를 이용해 Input 스트림 객체를 생성하는데 인코딩을 EUC-KR(혹은 UTF-8) 로 지정
				// inputStreamReader = new InputStreamReader( fileInputStream , "EUC-KR" ) ;
				inputStreamReader = new InputStreamReader( fileInputStream , "UTF-8" ) ;
				// Input 스트림 객체를 이용하여 버퍼를 생성
				bufferedReader = new BufferedReader( inputStreamReader , intBuffer ) ;
				// 버퍼를 한줄한줄 읽어들여 내용 추출
				while ( ( strTempLine = bufferedReader.readLine( ) ) != null )
				{
					resultStrBuffer.append( strTempLine ) ;
				}
				// logger.trace( "================== 파일 내용 출력 ==================" ) ;
				// logger.trace( "resultStrBuffer :: " + ( resultStrBuffer + "" ) ) ;
			}

		}
		catch ( FileNotFoundException e )
		{
			logger.error( e.getMessage( ) , e ) ;
		}
		catch ( Exception e )
		{
			logger.error( e.getMessage( ) , e ) ;
		}
		finally
		{
			if ( fileInputStream != null )
			{
				try
				{
					fileInputStream.close( ) ;
				}
				catch ( IOException e )
				{
					logger.error( e.getMessage( ) , e ) ;
				}
			}
			if ( inputStreamReader != null )
			{
				try
				{
					inputStreamReader.close( ) ;
				}
				catch ( IOException e )
				{
					logger.error( e.getMessage( ) , e ) ;
				}
			}
			if ( bufferedReader != null )
			{
				try
				{
					bufferedReader.close( ) ;
				}
				catch ( IOException e )
				{
					logger.error( e.getMessage( ) , e ) ;
				}
			}
			fileName = null ;
			file = null ;
			fileInputStream = null ;

			inputStreamReader = null ;
			bufferedReader = null ;
			strTempLine = null ;

			intBuffer = 0 ;

		}

		logger.trace( ":::::::::: fileContentRead End" ) ;

		return resultStrBuffer ;

	}

}
