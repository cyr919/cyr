/**
 * 
 */
package com.demo ;

import java.util.ArrayList ;
import java.util.Arrays ;
import java.util.HashMap ;
import java.util.List ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-06-09
 */
public class StringTest01
{
	private Logger logger = LogManager.getLogger( MainThread01.class ) ;
	
	/**
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-09
	 * @param args
	 */
	public static void main( String[ ] args ) {
		StringTest01 exe = new StringTest01( ) ;
		// exe.test04( ) ;
		exe.test05( ) ;
	}
	
	public void test04( ) {
		logger.trace( "test04" ) ;
		
		String exStr = "" ;
		String[ ] strArr = { "" } ;
		String[ ] strSubArr = { "" } ;
		
		String resultOperator = "" ;
		String resultOperand = "" ;
		String delimitersStr = "" ;
		
		int i = 0 ;
		int checkOperator = 0 ;
		int checkOperand = 0 ;
		int arrLength = 0 ;
		
		try {
			exStr = "<mgp1> + <mgp2> - <mgp3> * <mgp4> / <mgp5>" ;
			logger.trace( "exStr :: " + exStr ) ;
			delimitersStr = "," ;
			exStr = exStr.replace( " " , "" ) ;
			
			strArr = exStr.split( ">" ) ;
			
			logger.trace( "exStr :: " + exStr ) ;
			logger.trace( "strArr :: " + Arrays.toString( strArr ) ) ;
			
			for( i = 0 ; i < strArr.length ; i++ ) {
				logger.trace( "strArr[ " + i + " ] :: " + strArr[ i ] ) ;
				strSubArr = strArr[ i ].split( "<" ) ;
				
				logger.trace( "strSubArr :: " + Arrays.toString( strSubArr ) ) ;
				
				// 피연산자의 MGP_KEY 추출하여 string에 추가. 구분자는 ','
				if( i == 0 ) {
					resultOperand = strSubArr[ 1 ] ;
				}
				else {
					resultOperand = resultOperand + delimitersStr + strSubArr[ 1 ] ;
				}
				if( strSubArr[ 1 ] != null && !"".equals( strSubArr[ 1 ] ) ) {
					checkOperand++ ;
				}
				
				// 연산자 추출하여 string에 추가. 구분자는 ','
				if( i == 1 ) {
					resultOperator = strSubArr[ 0 ] ;
				}
				else if( i > 1 ) {
					resultOperator = resultOperator + delimitersStr + strSubArr[ 0 ] ;
				}
				if( strSubArr[ 0 ] != null && !"".equals( strSubArr[ 0 ] ) && ( strSubArr[ 0 ] ).length( ) == 1 ) {
					checkOperator++ ;
				}
			} // for( i = 0 ; i < strArr.length ; i++ )
			arrLength = strArr.length ;
			logger.trace( "resultOperand :: " + resultOperand ) ;
			logger.trace( "resultOperator :: " + resultOperator ) ;
			logger.trace( "checkOperand :: " + checkOperand ) ;
			logger.trace( "checkOperator :: " + checkOperator ) ;
			logger.trace( "arrLength :: " + arrLength ) ;
			
			if( arrLength != checkOperand || ( arrLength - 1 ) != checkOperator ) {
				logger.trace( "형식에 맞지 않음." ) ;
			}
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		
		return ;
	}
	
	public void test05( ) {
		logger.trace( "test04" ) ;
		
		String exStr = "" ;
		String[ ] strArr = { "" } ;
		String[ ] strSubArr = { "" } ;
		String[ ] strOperandArr = { "" } ;
		
		String resultOperator = "" ;
		String delimitersStr = "" ;
		List< HashMap< String , String > > resultOperandList = new ArrayList< HashMap< String , String > >( ) ;
		
		HashMap< String , String > listSubMap = new HashMap< String , String >( ) ;
		
		int i = 0 ;
		int checkOperator = 0 ;
		int checkOperandDvID = 0 ;
		int checkOperandKey = 0 ;
		int arrLength = 0 ;
		
		try {
			 exStr = "<stdv0003.MGP023> + <stdv0004.MGP023> - <stdv0003.MGP024> - <stdv0004.MGP024> " ;
//			exStr = "<stdv0002.MGP021> + <stdv0006.MGP021> " ;
			logger.trace( "exStr :: " + exStr ) ;
			delimitersStr = "," ;
			exStr = exStr.replace( " " , "" ) ;
			
			strArr = exStr.split( ">" ) ;
			
			logger.trace( "exStr :: " + exStr ) ;
			logger.trace( "strArr :: " + Arrays.toString( strArr ) ) ;
			
			for( i = 0 ; i < strArr.length ; i++ ) {
				logger.trace( "strArr[ " + i + " ] :: " + strArr[ i ] ) ;
				strSubArr = strArr[ i ].split( "<" ) ;
				
				logger.trace( "strSubArr :: " + Arrays.toString( strSubArr ) ) ;
				
				// 피연산자의 디바이스 아이디, MGP_KEY 추출하여 string에 추가. 구분자는 ','
				
				strOperandArr = ( strSubArr[ 1 ] ).split( "\\." ) ;
				logger.trace( "strOperandArr :: " + Arrays.toString( strOperandArr ) ) ;
				
				listSubMap = new HashMap< String , String >( ) ;
				listSubMap.put( "STDV_ID" , strOperandArr[ 0 ] ) ;
				listSubMap.put( "MGP_LEY" , strOperandArr[ 1 ] ) ;
				
				resultOperandList.add( listSubMap ) ;
				
				if( strOperandArr[ 0 ] != null && !"".equals( strOperandArr[ 0 ] ) ) {
					checkOperandDvID++ ;
				}
				if( strOperandArr[ 1 ] != null && !"".equals( strOperandArr[ 1 ] ) ) {
					checkOperandKey++ ;
				}
				
				// 연산자 추출하여 string에 추가. 구분자는 ','
				if( i == 1 ) {
					resultOperator = strSubArr[ 0 ] ;
				}
				else if( i > 1 ) {
					resultOperator = resultOperator + delimitersStr + strSubArr[ 0 ] ;
				}
				if( strSubArr[ 0 ] != null && !"".equals( strSubArr[ 0 ] ) && ( strSubArr[ 0 ] ).length( ) == 1 ) {
					checkOperator++ ;
				}
			} // for( i = 0 ; i < strArr.length ; i++ )
			arrLength = strArr.length ;
			logger.trace( "resultOperandList :: " + resultOperandList ) ;
			logger.trace( "resultOperator :: " + resultOperator ) ;
			logger.trace( "checkOperandDvID :: " + checkOperandDvID ) ;
			logger.trace( "checkOperandKey :: " + checkOperandKey ) ;
			logger.trace( "checkOperator :: " + checkOperator ) ;
			logger.trace( "arrLength :: " + arrLength ) ;
			
			if( checkOperandDvID != checkOperandKey || arrLength != checkOperandDvID || arrLength != checkOperandKey || ( arrLength - 1 ) != checkOperator ) {
				logger.trace( "형식에 맞지 않음." ) ;
			}
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		
		return ;
	}
}
