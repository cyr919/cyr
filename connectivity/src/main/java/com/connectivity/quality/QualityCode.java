/**
 * 
 */
package com.connectivity.quality ;

import java.math.BigDecimal ;
import java.util.HashMap ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.common.ConnectivityProperties ;
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
	
	public QualityCode( ) {
		this.recordQcInf = ConnectivityProperties.RECORD_QC_INF ;
		this.recordQcIdx = ConnectivityProperties.RECORD_QC_IDX ;
		this.fieldQcInf = ConnectivityProperties.FIELD_QC_INF ;
		this.fieldQcIdx = ConnectivityProperties.FIELD_QC_IDX ;
		// logger.debug( "recordQcInf :: " + System.identityHashCode( recordQcInf ) ) ;
		// logger.debug( "fieldQcInf :: " + System.identityHashCode( fieldQcInf ) ) ;
		// logger.debug( "this.recordQcInf :: " + System.identityHashCode( this.recordQcInf ) ) ;
		// logger.debug( "this.fieldQcInf :: " + System.identityHashCode( this.fieldQcInf ) ) ;
	}
	
	public QualityCode( HashMap< String , HashMap< String , Object > > recordQcInf , HashMap< String , HashMap< String , Integer > > recordQcIdx , HashMap< String , HashMap< String , Object > > fieldQcInf , HashMap< String , HashMap< String , Integer > > fieldQcIdx ) {
		this.recordQcInf = recordQcInf ;
		this.recordQcIdx = recordQcIdx ;
		this.fieldQcInf = fieldQcInf ;
		this.fieldQcIdx = fieldQcIdx ;
		
		// logger.debug( "recordQcInf :: " + System.identityHashCode( recordQcInf ) ) ;
		// logger.debug( "fieldQcInf :: " + System.identityHashCode( fieldQcInf ) ) ;
		// logger.debug( "this.recordQcInf :: " + System.identityHashCode( this.recordQcInf ) ) ;
		// logger.debug( "this.fieldQcInf :: " + System.identityHashCode( this.fieldQcInf ) ) ;
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
	
	public String initRecordQualityCode( ) {
		
		return "1" ;
	}
	
	public String initFieldQualityCode( ) {
		
		return "99999999" ;
	}
	
	public int gatherRepresentData( String tempRecordQc , String resultFieldQc ) {
		int resultInt = 0 ;
		
		try {
			if( !commUtil.checkNull( tempRecordQc ) && !commUtil.checkNull( resultFieldQc ) ) {
				
				resultInt = commUtil.multiplyData( tempRecordQc , resultFieldQc ) ;
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
		}
		
		return resultInt ;
	}
	
	public String gatherOldData( String strQc ) {
		String resultStr = "" ;
		
		String resultQcStr = "" ;
		
		try {
			// logger.debug( "strQc :: " + strQc ) ;
			// logger.debug( "fieldQcIdx.get( \"oldData\" ) :: " + fieldQcIdx.get( "oldData" ) ) ;
			
			// TODO OldData
			resultQcStr = "1" ;
			
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
			// logger.debug( "strQc :: " + strQc ) ;
			// logger.debug( "gatherValStr :: " + gatherValStr ) ;
			// logger.debug( "stdvDtMdlInfo :: " + stdvDtMdlInfo ) ;
			// logger.debug( "fieldQcIdx.get( \"overFlow\" ) :: " + fieldQcIdx.get( "overFlow" ) ) ;
			
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
			gatherValStr = null ;
			stdvDtMdlInfo = null ;
			tempRecordQc = null ;
			resultStr = null ;
			resultTempRecordQc = 0 ;
			resultQcStr = null ;
			minData = null ;
			maxData = null ;
			valData = null ;
		}
		
		return resultMap ;
	}
	
	public String gatherMeasurementMode( String strQc ) {
		String resultStr = "" ;
		
		String resultQcStr = "" ;
		
		try {
			// logger.debug( "strQc :: " + strQc ) ;
			// logger.debug( "fieldQcIdx.get( \"measurementMode\" ) :: " + fieldQcIdx.get( "measurementMode" ) ) ;
			
			// 계측 모드 : true
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
			// logger.debug( "strQc :: " + strQc ) ;
			// logger.debug( "fieldQcIdx.get( \"calculationMode\" ) :: " + fieldQcIdx.get( "calculationMode" ) ) ;
			
			// 연산 모드 : false
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
			// logger.debug( "strQc :: " + strQc ) ;
			// logger.debug( "fieldQcIdx.get( \"simulationMode\" ) :: " + fieldQcIdx.get( "simulationMode" ) ) ;
			
			// TODO 시뮬레이션 모드
			resultQcStr = "0" ;
			
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
	
	/**
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-09
	 * @param strQc 연산 필드 QC 데이터
	 * @param strGatherFieldQc 계측 데이터의 필드 QC 데이터
	 * @return
	 */
	public String calculateOldData( String strQc , String strGatherFieldQc ) {
		String resultStr = "" ;
		
		int resultInt = 0 ;
		String strFieldQc = "" ;
		
		try {
			// logger.debug( "fieldQcIdx.get( \"oldData\" ) :: " + fieldQcIdx.get( "oldData" ) ) ;
			// logger.debug( "strQc :: " + strQc ) ;
			// logger.debug( "strGatherFieldQc :: " + strGatherFieldQc ) ;
			//
			// logger.debug( "substring test :: " + "12345678".substring( fieldQcIdx.get( "oldData" ).get( "startInt" ) , fieldQcIdx.get( "oldData" ).get( "endInt" ) ) ) ;
			
			// OldData
			strFieldQc = strQc.substring( fieldQcIdx.get( "oldData" ).get( "startInt" ) , fieldQcIdx.get( "oldData" ).get( "endInt" ) ) ;
			strGatherFieldQc = strGatherFieldQc.substring( fieldQcIdx.get( "oldData" ).get( "startInt" ) , fieldQcIdx.get( "oldData" ).get( "endInt" ) ) ;
			
			// logger.debug( "strFieldQc :: " + strFieldQc ) ;
			// logger.debug( "strGatherFieldQc :: " + strGatherFieldQc ) ;
			
			resultInt = commUtil.multiplyData( strFieldQc , strGatherFieldQc ) ;
			
			// 문자열 바꿔치키
			resultStr = commUtil.idxReplace( strQc , fieldQcIdx.get( "oldData" ).get( "startInt" ) , fieldQcIdx.get( "oldData" ).get( "endInt" ) , String.valueOf( resultInt ) ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strQc = null ;
			strGatherFieldQc = null ;
			strFieldQc = null ;
			resultInt = 0 ;
		}
		
		return resultStr ;
	}
	
	/**
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-09
	 * @param strQc 연산 필드 QC 데이터
	 * @param strGatherFieldQc 계측 데이터의 필드 QC 데이터
	 * @return
	 */
	public String calculateOverFlow( String strQc , String strGatherFieldQc ) {
		String resultStr = "" ;
		
		int resultInt = 0 ;
		String strFieldQc = "" ;
		
		try {
			// logger.debug( "fieldQcIdx.get( \"overFlow\" ) :: " + fieldQcIdx.get( "overFlow" ) ) ;
			// logger.debug( "strQc :: " + strQc ) ;
			// logger.debug( "strGatherFieldQc :: " + strGatherFieldQc ) ;
			//
			// logger.debug( "substring test :: " + "12345678".substring( fieldQcIdx.get( "overFlow" ).get( "startInt" ) , fieldQcIdx.get( "overFlow" ).get( "endInt" ) ) ) ;
			
			// OverFlow
			strFieldQc = strQc.substring( fieldQcIdx.get( "overFlow" ).get( "startInt" ) , fieldQcIdx.get( "overFlow" ).get( "endInt" ) ) ;
			strGatherFieldQc = strGatherFieldQc.substring( fieldQcIdx.get( "overFlow" ).get( "startInt" ) , fieldQcIdx.get( "overFlow" ).get( "endInt" ) ) ;
			
			// logger.debug( "strFieldQc :: " + strFieldQc ) ;
			// logger.debug( "strGatherFieldQc :: " + strGatherFieldQc ) ;
			
			resultInt = commUtil.multiplyData( strFieldQc , strGatherFieldQc ) ;
			
			// 문자열 바꿔치키
			resultStr = commUtil.idxReplace( strQc , fieldQcIdx.get( "overFlow" ).get( "startInt" ) , fieldQcIdx.get( "overFlow" ).get( "endInt" ) , String.valueOf( resultInt ) ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strQc = null ;
			strGatherFieldQc = null ;
			strFieldQc = null ;
			resultInt = 0 ;
		}
		
		return resultStr ;
	}
	
	public String calculateMeasurementMode( String strQc ) {
		String resultStr = "" ;
		
		String resultQcStr = "" ;
		
		try {
			// logger.debug( "strQc :: " + strQc ) ;
			// logger.debug( "fieldQcIdx.get( \"measurementMode\" ) :: " + fieldQcIdx.get( "measurementMode" ) ) ;
			
			// 계측 모드 : false
			resultQcStr = "0" ;
			
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
	
	public String calculateCalculationMode( String strQc ) {
		String resultStr = "" ;
		
		String resultQcStr = "" ;
		
		try {
			// logger.debug( "strQc :: " + strQc ) ;
			// logger.debug( "fieldQcIdx.get( \"calculationMode\" ) :: " + fieldQcIdx.get( "calculationMode" ) ) ;
			
			// 연산 모드 : true
			resultQcStr = "1" ;
			
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
	
	public String calculateInDeviceSimulationMode( String strQc ) {
		String resultStr = "" ;
		
		String resultQcStr = "" ;
		
		try {
			// logger.debug( "strQc :: " + strQc ) ;
			// logger.debug( "fieldQcIdx.get( \"simulationMode\" ) :: " + fieldQcIdx.get( "simulationMode" ) ) ;
			
			// TODO 시뮬레이션 모드
			resultQcStr = "1" ;
			
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
	
	/**
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-11
	 * @param strQc 연산 필드 QC 데이터
	 * @param strGatherFieldQc 계측 데이터의 필드 QC 데이터
	 * @return
	 */
	public String calculateBetweenDevicesSimulationMode( String strQc , String strGatherFieldQc ) {
		String resultStr = "" ;
		
		int resultInt = 0 ;
		String strFieldQc = "" ;
		
		try {
//			logger.debug( "strQc :: " + strQc ) ;
//			logger.debug( "fieldQcIdx.get( \"simulationMode\" ) :: " + fieldQcIdx.get( "simulationMode" ) ) ;
			
			// 시뮬레이션 모드
			// 하나라도 "1(시뮬레이션 데이터)" 이면 해당 QC는 "1(시뮬레이션 데이터)" 모두 "0(일반 모드)" 이면 "0(일반 모드)"이다.
			strFieldQc = strQc.substring( fieldQcIdx.get( "simulationMode" ).get( "startInt" ) , fieldQcIdx.get( "simulationMode" ).get( "endInt" ) ) ;
			strGatherFieldQc = strGatherFieldQc.substring( fieldQcIdx.get( "simulationMode" ).get( "startInt" ) , fieldQcIdx.get( "simulationMode" ).get( "endInt" ) ) ;
			
//			logger.debug( "strFieldQc :: " + strFieldQc ) ;
//			logger.debug( "strGatherFieldQc :: " + strGatherFieldQc ) ;
			
			resultInt = commUtil.addData( strFieldQc , strGatherFieldQc ) ;
			
			if( resultInt > 1 ) {
				resultInt = 1 ;
			}
			
			// 문자열 바꿔치키
			resultStr = commUtil.idxReplace( strQc , fieldQcIdx.get( "simulationMode" ).get( "startInt" ) , fieldQcIdx.get( "simulationMode" ).get( "endInt" ) , String.valueOf( resultInt ) ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strQc = null ;
			strGatherFieldQc = null ;
			resultInt = 0 ;
			strFieldQc = null ;
		}
		
		return resultStr ;
	}
	
}
