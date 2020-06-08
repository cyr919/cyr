/**
 * 
 */
package com.connectivity.quality ;

import java.math.BigDecimal ;
import java.util.HashMap ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.utils.CommUtil ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-05-21
 */
public class QualityCode
{
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private Logger logger = LogManager.getLogger( this.getClass( ) ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	private HashMap< String , HashMap< String , Object > > fieldQcInf = new HashMap< String , HashMap< String , Object > >( ) ;
	private HashMap< String , HashMap< String , Object > > recordQcInf = new HashMap< String , HashMap< String , Object > >( ) ;
	
	private HashMap< String , HashMap< String , Integer > > fieldQcIdx = new HashMap< String , HashMap< String , Integer > >( ) ;
	private HashMap< String , HashMap< String , Integer > > recordQcIdx = new HashMap< String , HashMap< String , Integer > >( ) ;
	
	private CommUtil commUtil = new CommUtil( ) ;
	
	public QualityCode( HashMap< String , HashMap< String , Object > > recordQcInf , HashMap< String , HashMap< String , Integer > > recordQcIdx , HashMap< String , HashMap< String , Object > > fieldQcInf , HashMap< String , HashMap< String , Integer > > fieldQcIdx ) {
		this.recordQcInf = recordQcInf ;
		this.fieldQcInf = fieldQcInf ;
		this.fieldQcIdx = fieldQcIdx ;
		this.recordQcIdx = recordQcIdx ;
		
		logger.debug( "recordQcInf :: " + System.identityHashCode( recordQcInf ) ) ;
		logger.debug( "fieldQcInf :: " + System.identityHashCode( fieldQcInf ) ) ;
		logger.debug( "this.recordQcInf :: " + System.identityHashCode( this.recordQcInf ) ) ;
		logger.debug( "this.fieldQcInf :: " + System.identityHashCode( this.fieldQcInf ) ) ;
	}
	
	/**
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-05-21
	 * @param args
	 */
	public static void main( String[ ] args ) {
		// TODO Auto-generated method stub
		
	}
	
	// public HashMap< String , HashMap< String , Integer > > setFieldQcIdx( HashMap< String , HashMap< String , Object > > fieldQcInf ) {
	//
	// HashMap< String , HashMap< String , Integer > > resultFieldQcIdx = new HashMap< String , HashMap< String , Integer > >( ) ;
	// HashMap< String , Integer > tempMap = new HashMap< String , Integer >( ) ;
	// int startInt = 0 ;
	// int endInt = 0 ;
	//
	// try {
	// for( String keyStr : fieldQcInf.keySet( ) ) {
	// logger.debug( "keyStr :: " + keyStr ) ;
	// logger.debug( "fieldQcInf.get( " + keyStr + " ) :: " + fieldQcInf.get( keyStr ) ) ;
	//
	// startInt = Integer.parseInt( fieldQcInf.get( keyStr ).get( "ADR" ) + "" ) ;
	// endInt = Integer.parseInt( fieldQcInf.get( keyStr ).get( "LEN" ) + "" ) + startInt ;
	//
	// logger.debug( "startInt :: " + startInt ) ;
	// logger.debug( "endInt :: " + endInt ) ;
	//
	// tempMap.put( "startInt" , startInt ) ;
	// tempMap.put( "endInt" , endInt ) ;
	//
	// resultFieldQcIdx.put( keyStr , tempMap ) ;
	// }
	//
	// logger.debug( "resultFieldQcIdx :: " + resultFieldQcIdx ) ;
	// }
	// catch( Exception e ) {
	// logger.error( e.getMessage( ) , e ) ;
	// }
	// finally {
	// fieldQcInf = null ;
	// }
	//
	// return resultFieldQcIdx ;
	// }
	
	public String initRecordQualityCode( ) {
		
		return "9" ;
	}
	
	public String initFieldQualityCode( ) {
		
		return "98765432" ;
	}
	
	public int gatherRepresentData( String tempRecordQc , String resultFieldQc ) {
		int resultInt = 0 ;
		
		int tempRecordQcInt = 0 ;
		int resultFieldQcInt = 0 ;
		
		try {
			
			if( !commUtil.checkNull( tempRecordQc ) && !commUtil.checkNull( resultFieldQc ) ) {
				tempRecordQcInt = Integer.parseInt( tempRecordQc ) ;
				resultFieldQcInt = Integer.parseInt( resultFieldQc ) ;
				
				resultInt = tempRecordQcInt * resultFieldQcInt ;
			}
			else {
				// false
				resultInt = 0 ;
			}
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			tempRecordQc = null ;
			resultFieldQc = null ;
			tempRecordQcInt = 0 ;
			resultFieldQcInt = 0 ;
		}
		
		return resultInt ;
	}
	
	public String gatherOldData( String strQc ) {
		String resultStr = "" ;
		
		String resultQcStr = "" ;
		
		try {
			logger.debug( "strQc :: " + strQc ) ;
			logger.debug( "fieldQcIdx.get( \"oldData\" ) :: " + fieldQcIdx.get( "oldData" ) ) ;
			
			// 계측 모드
			resultQcStr = "!" ;
			
			// 문자열 바꿔치키
			resultStr = commUtil.idxReplace( strQc , fieldQcIdx.get( "oldData" ).get( "startInt" ) , fieldQcIdx.get( "oldData" ).get( "endInt" ) , resultQcStr ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strQc = null ;
			resultQcStr = null ;
		}
		
		return resultStr ;
	}
	
	public HashMap< String , Object > gatherOverFlow( String strQc , String gatherValStr , HashMap< String , Object > stdvDtMdlInfo , String tempRecordQc ) {
		
		HashMap< String , Object > resultMap = new HashMap< String , Object >( ) ;
		
		String resultStr = "" ;
		int resultTempRecordQc = 0 ;
		
		String resultQcStr = "" ;
		BigDecimal minData = null ;
		BigDecimal maxData = null ;
		BigDecimal valData = null ;
		
		try {
			logger.debug( "strQc :: " + strQc ) ;
			logger.debug( "gatherValStr :: " + gatherValStr ) ;
			logger.debug( "stdvDtMdlInfo :: " + stdvDtMdlInfo ) ;
			logger.debug( "fieldQcIdx.get( \"overFlow\" ) :: " + fieldQcIdx.get( "overFlow" ) ) ;
			
			// OverFlow
			
			if( !commUtil.checkNull( stdvDtMdlInfo.get( "MIN" ) + "" ) && !commUtil.checkNull( stdvDtMdlInfo.get( "MAX" ) + "" ) ) {
				minData = new BigDecimal( stdvDtMdlInfo.get( "MIN" ) + "" ) ;
				maxData = new BigDecimal( stdvDtMdlInfo.get( "MAX" ) + "" ) ;
				valData = new BigDecimal( gatherValStr ) ;
				
				if( ( valData.compareTo( minData ) == -1 ) || ( valData.compareTo( maxData ) == 1 ) ) {
					// gatherValStr < MIN || gatherValStr > MAX
					// false
					resultQcStr = "0" ;
					
				}
				else {
					// MIN <= gatherValStr <= MAX
					// true
					resultQcStr = "1" ;
				}
			}
			else {
				// min, max가 빈값이면 true
				resultQcStr = "1" ;
			}
			
			// 필드 QC 결과 적용 문자열 바꿔치키
			resultStr = commUtil.idxReplace( strQc , fieldQcIdx.get( "overFlow" ).get( "startInt" ) , fieldQcIdx.get( "overFlow" ).get( "endInt" ) , resultQcStr ) ;
			
			// 레코드 QC 확인
			resultTempRecordQc = gatherRepresentData( tempRecordQc , resultQcStr ) ;
			
			// 결과 셋팅
			resultMap.put( "resultFieldQc" , resultStr ) ;
			resultMap.put( "resultTempRecordQc" , resultTempRecordQc ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strQc = null ;
			resultQcStr = null ;
		}
		
		return resultMap ;
	}
	
	public String gatherMeasurementMode( String strQc ) {
		String resultStr = "" ;
		
		String resultQcStr = "" ;
		
		try {
			logger.debug( "strQc :: " + strQc ) ;
			logger.debug( "fieldQcIdx.get( \"measurementMode\" ) :: " + fieldQcIdx.get( "measurementMode" ) ) ;
			
			// 계측 모드
			resultQcStr = "1" ;
			
			// 문자열 바꿔치키
			resultStr = commUtil.idxReplace( strQc , fieldQcIdx.get( "measurementMode" ).get( "startInt" ) , fieldQcIdx.get( "measurementMode" ).get( "endInt" ) , resultQcStr ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strQc = null ;
			resultQcStr = null ;
		}
		
		return resultStr ;
	}
	
	public String gatherCalculationMode( String strQc ) {
		String resultStr = "" ;
		
		String resultQcStr = "" ;
		
		try {
			logger.debug( "strQc :: " + strQc ) ;
			logger.debug( "fieldQcIdx.get( \"calculationMode\" ) :: " + fieldQcIdx.get( "calculationMode" ) ) ;
			
			// 연산 모드
			resultQcStr = "0" ;
			
			// 문자열 바꿔치키
			resultStr = commUtil.idxReplace( strQc , fieldQcIdx.get( "calculationMode" ).get( "startInt" ) , fieldQcIdx.get( "calculationMode" ).get( "endInt" ) , resultQcStr ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strQc = null ;
			resultQcStr = null ;
		}
		
		return resultStr ;
	}
	
	public String gatherSimulationMode( String strQc ) {
		String resultStr = "" ;
		
		String resultQcStr = "" ;
		
		try {
			logger.debug( "strQc :: " + strQc ) ;
			logger.debug( "fieldQcIdx.get( \"simulationMode\" ) :: " + fieldQcIdx.get( "simulationMode" ) ) ;
			
			// 계측 모드
			resultQcStr = "&" ;
			
			// 문자열 바꿔치키
			resultStr = commUtil.idxReplace( strQc , fieldQcIdx.get( "simulationMode" ).get( "startInt" ) , fieldQcIdx.get( "simulationMode" ).get( "endInt" ) , resultQcStr ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strQc = null ;
			resultQcStr = null ;
		}
		
		return resultStr ;
	}
	
}
