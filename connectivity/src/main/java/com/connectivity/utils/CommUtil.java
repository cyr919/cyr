
package com.connectivity.utils ;

import java.math.BigDecimal ;
import java.math.RoundingMode ;
import java.time.LocalDateTime ;
import java.time.format.DateTimeFormatter ;
import java.util.HashMap ;
import java.util.List ;
import java.util.Map ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

public class CommUtil
{
	
	// Define a logger variable so that it references the
	// Logger instance named "MyApp".
	private Logger logger = LogManager.getLogger( CommUtil.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	public void main( String[ ] args ) {
	}
	
	/**
	 * <pre>
	 * 현재 일시를 "yyyy-MM-dd HH:mm:ss.SSS" 형식으로 formating 하여 String 으로 리턴한다.
	 * getNowDateTime( ) 메서드 사용.
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-05-22
	 * @return
	 */
	public String getFormatingNowDateTime( ) {
		String resultStr = "" ;
		
		LocalDateTime nowLocalDateTime = null ;
		
		try {
			
			nowLocalDateTime = LocalDateTime.now( ) ;
			resultStr = nowLocalDateTime.format( DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss.SSS" ) ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			
		}
		
		return resultStr ;
		
	}
	
	/**
	 * <pre>
	 * 현재 일시를 formating 하여 String 으로 리턴한다.
	 * getNowDateTime( ) 메서드 사용.
	 * format 형식 예시 : yyyy-MM-dd HH:mm:ss.SSS / yyyy-MM-dd HH:mm:ss / yyyyMMdd / yyyyMMddHHmmss
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-05-22
	 * @param strFormat
	 * @return
	 */
	public String getFormatingNowDateTime( String strFormat ) {
		String resultStr = "" ;
		
		LocalDateTime nowLocalDateTime = null ;
		
		try {
			
			nowLocalDateTime = LocalDateTime.now( ) ;
			resultStr = nowLocalDateTime.format( DateTimeFormatter.ofPattern( strFormat ) ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strFormat = null ;
			nowLocalDateTime = null ;
		}
		
		return resultStr ;
	}
	
	/**
	 * <pre>
	 * string 을 받아 BigDecimal 변환한다. Exception 이 발생하면 null을 반환한다.
	 * </pre>
	 * 
	 * @param
	 * @return BigDecimal
	 * @date 2019. 8. 20.
	 */
	public BigDecimal getBigdeciNumValue( String paramVal ) {
		
		BigDecimal returnVal = new BigDecimal( "0" ) ;
		
		try {
			returnVal = null ;
			
			returnVal = new BigDecimal( paramVal.trim( ) ) ;
			
		}
		catch( NumberFormatException e ) {
			returnVal = null ;
		}
		catch( Exception e ) {
			returnVal = null ;
			e.printStackTrace( ) ;
		}
		finally {
			paramVal = null ;
		}
		
		return returnVal ;
		
	}
	
	/**
	 * string 을 받아 BigDecimal 변환한다. Exception 이 발생하면 null을 반환한다.
	 * 
	 * @param String paramVal
	 * @return BigDecimal
	 */
	public BigDecimal getBigdeciNumValue( String paramVal , int newScale , int roundingMode ) {
		
		BigDecimal returnVal = new BigDecimal( "0" ) ;
		
		try {
			returnVal = null ;
			
			returnVal = new BigDecimal( paramVal.trim( ) ) ;
			
			if( null != returnVal ) {
				returnVal = returnVal.setScale( newScale , roundingMode ) ;
			}
		}
		catch( NumberFormatException e ) {
			returnVal = null ;
		}
		catch( Exception e ) {
			returnVal = null ;
			e.printStackTrace( ) ;
		}
		finally {
			paramVal = null ;
			newScale = 0 ;
			roundingMode = 0 ;
		}
		
		return returnVal ;
		
	}
	
	/**
	 * <pre>
	 * String 으로 된 숫자를 받아 소수점 처리를 한다.
	 * 
	 * strNum : 처리할 숫자를 String 형태로 입력한다.
	 * intScale : 소수점 자릿수
	 * intScaleMode : 소수점 처리 방법 ( 소수점 올림 - 0 / 소수점 버림 - 1 /소수점 5이상 올림(반올림) - 2 / 소수점 5이하 내림(반내림) - 3 )
	 * </pre>
	 * 
	 * @param
	 * @return BigDecimal
	 * @date 2019. 8. 22.
	 */
	public BigDecimal setScaleStringToBigDecimal( String strNum , int intScale , int intScaleMode ) {
		BigDecimal bgdcVal = new BigDecimal( "0" ) ;
		
		try {
			bgdcVal = new BigDecimal( strNum ) ;
			
			if( "0".equals( ( intScaleMode + "" ) ) ) {
				bgdcVal = bgdcVal.setScale( intScale , RoundingMode.UP ) ;
			}
			else if( "1".equals( ( intScaleMode + "" ) ) ) {
				bgdcVal = bgdcVal.setScale( intScale , RoundingMode.DOWN ) ;
			}
			else if( "2".equals( ( intScaleMode + "" ) ) ) {
				bgdcVal = bgdcVal.setScale( intScale , RoundingMode.HALF_UP ) ;
			}
			else if( "3".equals( ( intScaleMode + "" ) ) ) {
				bgdcVal = bgdcVal.setScale( intScale , RoundingMode.HALF_DOWN ) ;
			}
			
		}
		catch( Exception e ) {
			e.printStackTrace( ) ;
		}
		finally {
			strNum = null ;
			intScale = 0 ;
			intScaleMode = 0 ;
		}
		
		return bgdcVal ;
		
	}
	
	/**
	 * <pre>
	 * list to map
	 * list를 strKey의 값을 key 로한 map으로 만든다.
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-05-21
	 * @param tempList
	 * @param strKey hashmap의 key
	 * @return
	 */
	public Map< String , Map< String , Object > > getHashMapFromListHashMap( List< Map< String , Object > > tempList , String strKey ) {
		Map< String , Map< String , Object > > resultHashMap = new HashMap< String , Map<String,Object> >( );
		
		HashMap< String , Object > tempHashMap = new HashMap<>( ) ;
		int i = 0 ;
		
		try {
			if( !checkNull( tempList ) ) {
				for( i = 0 ; i < tempList.size( ) ; i++ ) {
					// logger.info( "listHashMap.get( " + i + " ).get( " + strKey + " ) :: " + listHashMap.get( i ).get( strKey ) ) ;
					// logger.info( "listHashMap.get( " + i + " ) :: " + listHashMap.get( i ) ) ;
					
					tempHashMap = new HashMap< String , Object >( ) ;
					tempHashMap = ( HashMap< String , Object > ) tempList.get( i ) ;
					
					resultHashMap.put( ( tempList.get( i ).get( strKey ) + "" ) , tempHashMap ) ;
				}
			}
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			tempHashMap = null ;
			tempList = null ;
			strKey = null ;
			i = 0 ;
		}
		
		return resultHashMap ;
	}
	
	public HashMap< String , HashMap< String , Object > > getHashMapFromMongodbListHashMap( List< HashMap > listHashMap , String strKey ) {
		HashMap< String , HashMap< String , Object > > resultHashMap = new HashMap<>( ) ;
		
		HashMap< String , Object > tempHashMap = new HashMap<>( ) ;
		int i = 0 ;
		
		try {
			if( !checkNull( listHashMap ) ) {
				for( i = 0 ; i < listHashMap.size( ) ; i++ ) {
					// logger.info( "listHashMap.get( " + i + " ).get( " + strKey + " ) :: " + listHashMap.get( i ).get( strKey ) ) ;
					// logger.info( "listHashMap.get( " + i + " ) :: " + listHashMap.get( i ) ) ;
					
					tempHashMap = new HashMap< String , Object >( ) ;
					tempHashMap = ( HashMap< String , Object > ) listHashMap.get( i ) ;
					
					resultHashMap.put( ( listHashMap.get( i ).get( strKey ) + "" ) , tempHashMap ) ;
				}
			}
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			tempHashMap = null ;
			listHashMap = null ;
			strKey = null ;
			i = 0 ;
		}
		
		return resultHashMap ;
	}
	
	public int getRandomInt( int multyParam , int plusParam ) {
		
		int intResult = 0 ;
		
		double dValue = 0D ;
		
		try {
			dValue = Math.random( ) ;
			intResult = ( int ) ( dValue * multyParam ) + plusParam ;
		}
		finally {
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
	public Boolean checkNull( String param ) {
		Boolean resultBoll = true ;
		
		try {
			if( null != param && !"".equals( param ) && !param.isEmpty( ) ) {
				resultBoll = false ;
			}
		}
		catch( Exception e ) {
			e.printStackTrace( ) ;
		}
		finally {
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
	public Boolean checkNull( List< ? > paramList ) {
		Boolean resultBoll = true ;
		
		try {
			if( null != paramList && 0 < paramList.size( ) && !paramList.isEmpty( ) ) {
				resultBoll = false ;
			}
		}
		catch( Exception e ) {
			e.printStackTrace( ) ;
		}
		finally {
			paramList = null ;
		}
		
		return resultBoll ;
		
	}
	
	public Boolean checkNull( String[ ][ ] paramStrtArray ) {
		Boolean resultBoll = true ;
		
		try {
			if( null != paramStrtArray && 0 < paramStrtArray.length ) {
				resultBoll = false ;
			}
		}
		catch( Exception e ) {
			e.printStackTrace( ) ;
		}
		finally {
			paramStrtArray = null ;
		}
		
		return resultBoll ;
		
	}
	
	public Boolean checkNull( String[ ] paramStrtArray ) {
		Boolean resultBoll = true ;
		
		try {
			if( null != paramStrtArray && 0 < paramStrtArray.length ) {
				resultBoll = false ;
			}
		}
		catch( Exception e ) {
			e.printStackTrace( ) ;
		}
		finally {
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
	public Boolean checkNull( Map< ? , ? > param ) {
		Boolean resultBoll = true ;
		
		try {
			if( null != param && 0 < param.size( ) && !param.isEmpty( ) ) {
				resultBoll = false ;
			}
		}
		catch( Exception e ) {
			e.printStackTrace( ) ;
		}
		finally {
			param = null ;
		}
		
		return resultBoll ;
		
	}
	
	public Boolean checkNullHashMap( Map< String , ? > param ) {
		Boolean resultBoll = true ;
		
		try {
			if( null != param && 0 < param.size( ) && !param.isEmpty( ) ) {
				resultBoll = false ;
			}
		}
		catch( Exception e ) {
			e.printStackTrace( ) ;
		}
		finally {
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
	public Boolean checkObjNull( Object paramObj ) {
		Boolean resultBoll = true ;
		
		try {
			if( null != paramObj && !"".equals( paramObj ) && !( paramObj + "" ).isEmpty( ) ) {
				resultBoll = false ;
			}
		}
		catch( Exception e ) {
			e.printStackTrace( ) ;
		}
		finally {
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
	public int checkNumNull( Object param ) {
		int resultInt = 0 ;
		
		try {
			if( !checkObjNull( param ) ) {
				resultInt = Integer.parseInt( ( param + "" ).trim( ).replaceAll( " " , "" ) ) ;
			}
		}
		catch( Exception e ) {
			e.printStackTrace( ) ;
		}
		finally {
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
	public int checkNumNull( Object param , int replaceParam ) {
		int resultInt = 0 ;
		
		try {
			if( !checkObjNull( param ) ) {
				resultInt = Integer.parseInt( ( param + "" ).trim( ).replaceAll( " " , "" ) ) ;
			}
			else {
				param = replaceParam ;
			}
		}
		catch( Exception e ) {
			e.printStackTrace( ) ;
		}
		finally {
			param = null ;
			replaceParam = 0 ;
		}
		
		return resultInt ;
	}
	
	/**
	 * <pre>
	 * 문자열의 위치값으로 replace 한다.
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-09
	 * @param source 원본 문자열
	 * @param startInt object가 변환될 시작 위치
	 * @param endInt object가 변환될 종료 위치
	 * @param object 변환할 문자열
	 * @return
	 */
	public String idxReplace( String source , int startInt , int endInt , String object ) {
		StringBuffer resultStringBuffer = null ;
		
		try {
			resultStringBuffer = new StringBuffer( source ) ;
			
			// logger.debug( "resultStringBuffer.length( ) :: " + resultStringBuffer.length( ) ) ;
			
			resultStringBuffer = resultStringBuffer.replace( startInt , endInt , object ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			source = null ;
			object = null ;
			startInt = 0 ;
			endInt = 0 ;
		}
		
		return( resultStringBuffer + "" ) ;
	}
	
	public int multiplyData( String operandStr01 , String operandStr02 ) {
		
		int resultInt = 0 ;
		
		int operandInt01 = 0 ;
		int operandInt02 = 0 ;
		
		try {
			operandInt01 = Integer.parseInt( operandStr01 ) ;
			operandInt02 = Integer.parseInt( operandStr02 ) ;
			
			resultInt = operandInt01 * operandInt02 ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			operandStr01 = null ;
			operandStr02 = null ;
			operandInt01 = 0 ;
			operandInt02 = 0 ;
		}
		
		return resultInt ;
	}
	
	public int addData( String operandStr01 , String operandStr02 ) {
		
		int resultInt = 0 ;
		
		int operandInt01 = 0 ;
		int operandInt02 = 0 ;
		
		try {
			operandInt01 = Integer.parseInt( operandStr01 ) ;
			operandInt02 = Integer.parseInt( operandStr02 ) ;
			
			resultInt = operandInt01 + operandInt02 ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			operandStr01 = null ;
			operandStr02 = null ;
			operandInt01 = 0 ;
			operandInt02 = 0 ;
		}
		
		return resultInt ;
	}
}
