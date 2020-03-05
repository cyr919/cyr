
package com.connectivity.utils ;

import java.util.HashMap ;
import java.util.List ;

import org.apache.log4j.Logger ;

public class CommUtil
{

	static Logger logger = Logger.getLogger( CommUtil.class ) ;

	public static void main( String[ ] args )
	{
		int intVal = 0 ;


		
		intVal = CommUtil.getRandomInt( 1 , 10 ) ;
		logger.debug( "" + intVal ) ;
		
		intVal = CommUtil.getRandomInt( 10 , 10 ) ;
		logger.debug( "" + intVal ) ;
		
		intVal = CommUtil.getRandomInt( 100 , 100 ) ;
		logger.debug( "" + intVal ) ;
		

	}

	public static int getRandomInt( int multyParam , int  plusParam ) {
		
		int intResult = 0 ;
		
		double dValue = 0D ;

		try
		{
			dValue = Math.random( ) ;			
			intResult = ( int ) ( dValue * multyParam ) + plusParam ;
		}
		finally
		{
			dValue = 0D ;
			multyParam = 0 ;
			plusParam = 0 ;
		}
		
		
		return intResult ;
		
	}
	
	
	/**
	 * <pre>
	 * String null 또는 빈 값 체크 , null 또는 빈 값이 아니면 false
	 * </pre>
	 * 
	 * @param
	 * @return Boolean
	 * @date 2019. 8. 6.
	 */
	public static Boolean checkNull( String param )
	{
		Boolean resultBoll = true ;

		try
		{
			if ( null != param && !"".equals( param ) && !param.isEmpty( ) )
			{
				resultBoll = false ;
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace( ) ;
		}
		finally
		{
			param = null ;
		}

		return resultBoll ;

	}

	/**
	 * <pre>
	 * List 가 null 혹은 0 < paramList.size( ) , 빈 값이 아닐 때 false 반환
	 * </pre>
	 * 
	 * @param
	 * @return Boolean
	 * @date 2019. 8. 5.
	 */
	public static Boolean checkNull( List< ? > paramList )
	{
		Boolean resultBoll = true ;

		try
		{
			if ( null != paramList && 0 < paramList.size( ) && !paramList.isEmpty( ) )
			{
				resultBoll = false ;
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace( ) ;
		}
		finally
		{
			paramList = null ;
		}

		return resultBoll ;

	}

	public static Boolean checkNull( String[ ][ ] paramStrtArray )
	{
		Boolean resultBoll = true ;

		try
		{
			if ( null != paramStrtArray && 0 < paramStrtArray.length )
			{
				resultBoll = false ;
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace( ) ;
		}
		finally
		{
			paramStrtArray = null ;
		}

		return resultBoll ;

	}

	public static Boolean checkNull( String[ ] paramStrtArray )
	{
		Boolean resultBoll = true ;

		try
		{
			if ( null != paramStrtArray && 0 < paramStrtArray.length )
			{
				resultBoll = false ;
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace( ) ;
		}
		finally
		{
			paramStrtArray = null ;
		}

		return resultBoll ;

	}

	
	
	/**
	 * <pre>
	 * HashMap 가 null 혹은 0 < HashMap.size( ) , 빈 값이 아닐 때 false 반환
	 * </pre>
	 * 
	 * @param
	 * @return Boolean
	 * @date 2019. 8. 5.
	 */
	public static Boolean checkNull( HashMap< String , Object > param )
	{
		Boolean resultBoll = true ;

		try
		{
			if ( null != param && 0 < param.size( ) && !param.isEmpty( ) )
			{
				resultBoll = false ;
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace( ) ;
		}
		finally
		{
			param = null ;
		}

		return resultBoll ;

	}

	public static Boolean checkNullHashMap( HashMap< String , ? > param )
	{
		Boolean resultBoll = true ;

		try
		{
			if ( null != param && 0 < param.size( ) && !param.isEmpty( ) )
			{
				resultBoll = false ;
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace( ) ;
		}
		finally
		{
			param = null ;
		}

		return resultBoll ;

	}
	
	/**
	 * <pre>
	 * Object 가 null 혹은 "" 값이 아닐 때 false
	 * </pre>
	 * 
	 * @param
	 * @return Boolean
	 * @date 2019. 8. 6.
	 */
	public static Boolean checkObjNull( Object paramObj )
	{
		Boolean resultBoll = true ;

		try
		{
			if ( null != paramObj && !"".equals( paramObj ) && !( paramObj + "" ).isEmpty( ) )
			{
				resultBoll = false ;
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace( ) ;
		}
		finally
		{
			paramObj = null ;
		}

		return resultBoll ;

	}

	/**
	 * <pre>
	 * </pre>
	 * 
	 * @param
	 * @return int
	 * @date 2019. 9. 8.
	 */
	public static int checkNumNull( Object param )
	{
		int resultInt = 0 ;

		try
		{
			if ( !CommUtil.checkObjNull( param ) )
			{
				resultInt = Integer.parseInt( ( param + "" ).trim( ).replaceAll( " " , "" ) ) ;
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace( ) ;
		}
		finally
		{
			param = null ;
		}

		return resultInt ;
	}

	/**
	 * <pre>
	 * </pre>
	 * 
	 * @param
	 * @return int
	 * @date 2019. 9. 8.
	 */
	public static int checkNumNull( Object param , int replaceParam )
	{
		int resultInt = 0 ;

		try
		{
			if ( !CommUtil.checkObjNull( param ) )
			{
				resultInt = Integer.parseInt( ( param + "" ).trim( ).replaceAll( " " , "" ) ) ;
			}
			else
			{
				param = replaceParam ;
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace( ) ;
		}
		finally
		{
			param = null ;
			replaceParam = 0 ;
		}

		return resultInt ;
	}

}
