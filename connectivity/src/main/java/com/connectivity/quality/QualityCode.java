/**
 * 
 */
package com.connectivity.quality ;

import java.math.BigDecimal ;
import java.util.HashMap ;
import java.util.Map ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.common.ConnectivityProperties ;
import com.connectivity.utils.CommUtil ;

/**
 * <pre>
 * QualityCode
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
	
	private Map< String , Map< String , Object > > fieldQcInf = new HashMap< String , Map< String , Object > >( ) ;
	private Map< String , Map< String , Object > > recordQcInf = new HashMap< String , Map< String , Object > >( ) ;
	
	private Map< String , Map< String , Integer > > fieldQcIdx = new HashMap< String , Map< String , Integer > >( ) ;
	private Map< String , Map< String , Integer > > recordQcIdx = new HashMap< String , Map< String , Integer > >( ) ;
	
	public String strSiteSmlt = "" ;
	public String strSiteSmltUsr = "" ;
	
	private CommUtil commUtil = new CommUtil( ) ;
	
	public QualityCode( ) {
		
		synchronized( ConnectivityProperties.RECORD_QC_INF ) {
			this.recordQcInf = ConnectivityProperties.RECORD_QC_INF ;
		}
		synchronized( ConnectivityProperties.RECORD_QC_IDX ) {
			this.recordQcIdx = ConnectivityProperties.RECORD_QC_IDX ;
		}
		synchronized( ConnectivityProperties.FIELD_QC_INF ) {
			this.fieldQcInf = ConnectivityProperties.FIELD_QC_INF ;
		}
		synchronized( ConnectivityProperties.FIELD_QC_IDX ) {
			this.fieldQcIdx = ConnectivityProperties.FIELD_QC_IDX ;
		}
		synchronized( ConnectivityProperties.SITE_SMLT ) {
			this.strSiteSmlt = ConnectivityProperties.SITE_SMLT ;
		}
		synchronized( ConnectivityProperties.SITE_SMLT_USR ) {
			this.strSiteSmltUsr = ConnectivityProperties.SITE_SMLT_USR ;
		}
		
		// logger.debug( "recordQcInf :: " + System.identityHashCode( recordQcInf ) ) ;
		// logger.debug( "fieldQcInf :: " + System.identityHashCode( fieldQcInf ) ) ;
		// logger.debug( "this.recordQcInf :: " + System.identityHashCode( this.recordQcInf ) ) ;
		// logger.debug( "this.fieldQcInf :: " + System.identityHashCode( this.fieldQcInf ) ) ;
	}
	
	// public QualityCode( HashMap< String , HashMap< String , Object > > recordQcInf , HashMap< String , HashMap< String , Integer > > recordQcIdx , HashMap< String , HashMap< String , Object > > fieldQcInf , HashMap< String , HashMap< String , Integer > > fieldQcIdx ) {
	// this.recordQcInf = recordQcInf ;
	// this.recordQcIdx = recordQcIdx ;
	// this.fieldQcInf = fieldQcInf ;
	// this.fieldQcIdx = fieldQcIdx ;
	//
	// // logger.debug( "recordQcInf :: " + System.identityHashCode( recordQcInf ) ) ;
	// // logger.debug( "fieldQcInf :: " + System.identityHashCode( fieldQcInf ) ) ;
	// // logger.debug( "this.recordQcInf :: " + System.identityHashCode( this.recordQcInf ) ) ;
	// // logger.debug( "this.fieldQcInf :: " + System.identityHashCode( this.fieldQcInf ) ) ;
	// }
	
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
	
	/**
	 * <pre>
	 * Record QC 초기 값
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-18
	 * @return
	 */
	public String initRecordQualityCode( ) {
		
		return "1" ;
	}
	
	/**
	 * <pre>
	 * Field QC 초기 값
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-18
	 * @return
	 */
	public String initFieldQualityCode( ) {
		
		return "99999999" ;
	}
	
	/**
	 * <pre>
	 * 설치 디바이스의 데이터 필드 QC의 스킵 여부를 리턴한다.
	 * true 면 해당 필드 QC를 스킵하고 false면 해당 필드 QC를 판단한다.
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-24
	 * @param strQcSkip 각 필드 QC의 스킵 여부
	 * @param strDviceSkip 설치 디바이스 스킵 여부
	 * @return
	 */
	public Boolean checkQualityCodeSkip( String strQcSkip , String strDviceSkip ) {
		Boolean resultBool = true ;
		
		try {
			if( "Y".equals( strQcSkip ) && "Y".equals( strDviceSkip ) ) {
				resultBool = true ;
			}
			else {
				resultBool = false ;
			}
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strQcSkip = null ;
			strDviceSkip = null ;
		}
		
		return resultBool ;
	}
	
	/**
	 * <pre>
	 * 대표 QC 처리
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-18
	 * @param tempRecordQc
	 * @param resultFieldQc
	 * @return
	 */
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
	
	/**
	 * <pre>
	 * old 데이터 qc 를 판단하고 그 결과값을 리턴한다.('0' or '1')
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-24
	 * @param strGatherDmt 계측 시간
	 * @param strDviceId 디바이스 아이디
	 * @param strDviceSkip 디바이스 qc 스킵 여부
	 * @return
	 */
	public String gatherOldDataQC( String strGatherDmt , String strDviceId , String strDviceSkip ) {
		String resultFieldQcStr = "" ;
		
		Integer checkInt = 0 ;
		ConnectivityProperties connectivityProperties = new ConnectivityProperties( ) ;
		
		try {
			
			// logger.debug( "fieldQcInf.get( \"oldData\" ) :: " + fieldQcInf.get( "oldData" ) ) ;
			// logger.debug( "strDviceSkip :: " + strDviceSkip ) ;
			
			if( checkQualityCodeSkip( ( fieldQcInf.get( "oldData" ).get( "SKIP" ) + "" ) , strDviceSkip ) ) {
				// 필드 QC 스킵 처리
				// skip(false)
				resultFieldQcStr = "0" ;
			}
			else {
				// 필드 QC 적용(판단) 처리
				
				// OldData 판단
				checkInt = connectivityProperties.CompareGatherDtmBeforeAndAfter( strGatherDmt , strDviceId ) ;
				// logger.debug( "checkInt :: " + checkInt ) ;
				if( checkInt > 2 ) {
					// checkInt 가 3일 때 처리
					// false
					resultFieldQcStr = "0" ;
				}
				else {
					// true
					resultFieldQcStr = "1" ;
				}
			}
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strGatherDmt = null ;
			strDviceId = null ;
			strDviceSkip = null ;
			checkInt = null ;
			connectivityProperties = null ;
		}
		
		return resultFieldQcStr ;
	}
	
	/**
	 * <pre>
	 * old data qc의 결과값을 필드 qc에 추가한다.(적용한다.)
	 * OldData QC는 이미 판단되어서 resultOldDataQc 값으로 받는다.
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-24
	 * @param strQc 해당 mgp 키의 필드 QC
	 * @param resultOldDataQc old data QC 결과 값
	 * @return
	 */
	public String gatherOldDataApplyField( String strQc , String resultOldDataQc ) {
		String resultStr = "" ;
		
		// String resultFieldQcStr = "" ;
		
		try {
			// logger.debug( "strQc :: " + strQc ) ;
			// logger.debug( "fieldQcIdx.get( \"oldData\" ) :: " + fieldQcIdx.get( "oldData" ) ) ;
			
			// OldData QC는 이미 판단되어서 resultOldDataQc 값으로 받는다.
			
			// 문자열 바꿔치키
			resultStr = commUtil.idxReplace( strQc , fieldQcIdx.get( "oldData" ).get( "startInt" ) , fieldQcIdx.get( "oldData" ).get( "endInt" ) , resultOldDataQc ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strQc = null ;
			resultOldDataQc = null ;
			// resultFieldQcStr = null ;
		}
		
		return resultStr ;
	}
	
	/**
	 * <pre>
	 * 계측 필드 QC 처리
	 * OverFlow QC 확인 및 결과 데이터로 변경, 레코드 QC 같이 확인, 이벤트 정보 추가
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-24
	 * @param strQc 해당 mgp 키의 필드 QC
	 * @param orgGatherValStr 계측 데이터
	 * @param stdvDtMdlInfo 해당 mgp 키의 디바이스 데이터 모델 정보
	 * @param tempRecordQc 임시 레코드 QC
	 * @param gatherDataEventMap 이벤트 정보 맵(여기에 데이터 추가해주면 gather에서도 같이 추가된다. ... 참조형?)
	 * @param strDviceSkip 디바이스 qc 스킵 여부
	 * @return 계측 데이터의 필드 QC 결과 및 임시 레코드 QC
	 */
	public HashMap< String , Object > gatherOverFlow( String strQc , String orgGatherValStr , Map< String , Object > stdvDtMdlInfo , String tempRecordQc , HashMap< String , Object > gatherDataEventMap , String strDviceSkip ) {
		HashMap< String , Object > resultMap = new HashMap< String , Object >( ) ;
		
		String resultStr = "" ;
		int resultTempRecordQc = 0 ;
		
		String resultFieldQcStr = "" ;
		BigDecimal minData = null ;
		BigDecimal maxData = null ;
		BigDecimal valData = null ;
		
		HashMap< String , Object > eventDataMap = new HashMap< String , Object >( ) ;
		
		try {
			// logger.debug( "strQc :: " + strQc ) ;
			// logger.debug( "orgGatherValStr :: " + orgGatherValStr ) ;
			// logger.debug( "stdvDtMdlInfo :: " + stdvDtMdlInfo ) ;
			// logger.debug( "fieldQcIdx.get( \"overFlow\" ) :: " + fieldQcIdx.get( "overFlow" ) ) ;
			// logger.debug( "fieldQcInf.get( \"overFlow\" ) :: " + fieldQcInf.get( "overFlow" ) ) ;
			
			if( checkQualityCodeSkip( ( fieldQcInf.get( "overFlow" ).get( "SKIP" ) + "" ) , strDviceSkip ) ) {
				// 필드 QC 스킵 처리
				// skip(false)
				resultFieldQcStr = "0" ;
			}
			else {
				// 필드 QC 적용(판단) 처리
				
				// OverFlow
				
				if( !commUtil.checkNull( stdvDtMdlInfo.get( "MIN" ) + "" ) && !commUtil.checkNull( stdvDtMdlInfo.get( "MAX" ) + "" ) ) {
					minData = new BigDecimal( stdvDtMdlInfo.get( "MIN" ) + "" ) ;
					maxData = new BigDecimal( stdvDtMdlInfo.get( "MAX" ) + "" ) ;
					valData = new BigDecimal( orgGatherValStr ) ;
					
					if( ( valData.compareTo( minData ) == -1 ) || ( valData.compareTo( maxData ) == 1 ) ) {
						// gatherValStr < MIN || gatherValStr > MAX
						// false
						resultFieldQcStr = "0" ;
						
						// 이벤트 데이터 추가
						eventDataMap.put( "minData" , minData ) ;
						eventDataMap.put( "maxData" , maxData ) ;
						eventDataMap.put( "valData" , valData ) ;
						
						gatherDataEventMap.put( "OverFlowEvent" , true ) ;
						gatherDataEventMap.put( "OverFlowEventDT" , eventDataMap ) ;
					}
					else {
						// MIN <= gatherValStr <= MAX
						// true
						resultFieldQcStr = "1" ;
					}
				}
				else {
					// min, max가 빈값이면 true
					resultFieldQcStr = "1" ;
				}
				
			}
			
			// 필드 QC 결과 적용 문자열 바꿔치키
			resultStr = commUtil.idxReplace( strQc , fieldQcIdx.get( "overFlow" ).get( "startInt" ) , fieldQcIdx.get( "overFlow" ).get( "endInt" ) , resultFieldQcStr ) ;
			
			// 레코드 QC 확인
			resultTempRecordQc = gatherRepresentData( tempRecordQc , resultFieldQcStr ) ;
			
			// 결과 셋팅
			resultMap.put( "resultFieldQc" , resultStr ) ;
			resultMap.put( "resultTempRecordQc" , resultTempRecordQc ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strQc = null ;
			orgGatherValStr = null ;
			stdvDtMdlInfo = null ;
			tempRecordQc = null ;
			gatherDataEventMap = null ;
			strDviceSkip = null ;
			resultStr = null ;
			resultFieldQcStr = null ;
			minData = null ;
			maxData = null ;
			valData = null ;
			eventDataMap = null ;
			resultTempRecordQc = 0 ;
		}
		
		return resultMap ;
	}
	
	/**
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-24
	 * @param strQc 해당 mgp 키의 필드 QC
	 * @param strDviceSkip 디바이스 qc 스킵 여부
	 * @return
	 */
	public String gatherMeasurementMode( String strQc , String strDviceSkip ) {
		String resultStr = "" ;
		
		String resultFieldQcStr = "" ;
		
		try {
			// logger.debug( "strQc :: " + strQc ) ;
			// logger.debug( "fieldQcIdx.get( \"measurementMode\" ) :: " + fieldQcIdx.get( "measurementMode" ) ) ;
			// logger.debug( "fieldQcInf.get( \"measurementMode\" ) :: " + fieldQcInf.get( "measurementMode" ) ) ;
			
			if( checkQualityCodeSkip( ( fieldQcInf.get( "measurementMode" ).get( "SKIP" ) + "" ) , strDviceSkip ) ) {
				// 필드 QC 스킵 처리
				// skip(false)
				resultFieldQcStr = "0" ;
			}
			else {
				// 필드 QC 적용(판단) 처리
				
				// 계측 모드 : true
				resultFieldQcStr = "1" ;
			}
			
			// 문자열 바꿔치키
			resultStr = commUtil.idxReplace( strQc , fieldQcIdx.get( "measurementMode" ).get( "startInt" ) , fieldQcIdx.get( "measurementMode" ).get( "endInt" ) , resultFieldQcStr ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strQc = null ;
			strDviceSkip = null ;
			resultFieldQcStr = null ;
		}
		
		return resultStr ;
	}
	
	/**
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-24
	 * @param strQc 해당 mgp 키의 필드 QC
	 * @param strDviceSkip 디바이스 qc 스킵 여부
	 * @return
	 */
	public String gatherCalculationMode( String strQc , String strDviceSkip ) {
		String resultStr = "" ;
		
		String resultFieldQcStr = "" ;
		
		try {
			// logger.debug( "strQc :: " + strQc ) ;
			// logger.debug( "fieldQcIdx.get( \"calculationMode\" ) :: " + fieldQcIdx.get( "calculationMode" ) ) ;
			// logger.debug( "fieldQcInf.get( \"calculationMode\" ) :: " + fieldQcInf.get( "calculationMode" ) ) ;
			
			if( checkQualityCodeSkip( ( fieldQcInf.get( "calculationMode" ).get( "SKIP" ) + "" ) , strDviceSkip ) ) {
				// 필드 QC 스킵 처리
				// skip(false)
				resultFieldQcStr = "0" ;
			}
			else {
				// 필드 QC 적용(판단) 처리
				
				// 연산 모드 : false
				resultFieldQcStr = "0" ;
			}
			
			// 문자열 바꿔치키
			resultStr = commUtil.idxReplace( strQc , fieldQcIdx.get( "calculationMode" ).get( "startInt" ) , fieldQcIdx.get( "calculationMode" ).get( "endInt" ) , resultFieldQcStr ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strQc = null ;
			resultFieldQcStr = null ;
			strDviceSkip = null ;
		}
		
		return resultStr ;
	}
	
	/**
	 * <pre>
	 * 계측시 시뮬레이션 모드 QC 처리
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-24
	 * @param strQc 해당 mgp 키의 필드 QC
	 * @param strDviceSkip 디바이스 qc 스킵 여부
	 * @return
	 */
	public String gatherSimulationMode( String strQc , String strDviceSkip ) {
		String resultStr = "" ;
		
		String resultFieldQcStr = "" ;
		
		try {
			// logger.debug( "strQc :: " + strQc ) ;
			// logger.debug( "fieldQcIdx.get( \"simulationMode\" ) :: " + fieldQcIdx.get( "simulationMode" ) ) ;
			// logger.debug( "fieldQcInf.get( \"simulationMode\" ) :: " + fieldQcInf.get( "simulationMode" ) ) ;
			// logger.debug( "this.strSiteSmlt :: " + this.strSiteSmlt ) ;
			
			if( checkQualityCodeSkip( ( fieldQcInf.get( "simulationMode" ).get( "SKIP" ) + "" ) , strDviceSkip ) ) {
				// 필드 QC 스킵 처리
				// skip(false)
				resultFieldQcStr = "0" ;
			}
			else {
				// 필드 QC 적용(판단) 처리
				
				// 시뮬레이션 모드
				if( "Y".equals( strSiteSmlt ) ) {
					// true
					resultFieldQcStr = "1" ;
				}
				else {
					// false
					resultFieldQcStr = "0" ;
				}
			}
			
			// 문자열 바꿔치키
			resultStr = commUtil.idxReplace( strQc , fieldQcIdx.get( "simulationMode" ).get( "startInt" ) , fieldQcIdx.get( "simulationMode" ).get( "endInt" ) , resultFieldQcStr ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strQc = null ;
			resultFieldQcStr = null ;
			strDviceSkip = null ;
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
		
		String resultFieldQcStr = "" ;
		
		try {
			// logger.debug( "strQc :: " + strQc ) ;
			// logger.debug( "fieldQcIdx.get( \"measurementMode\" ) :: " + fieldQcIdx.get( "measurementMode" ) ) ;
			
			// 계측 모드 : false
			resultFieldQcStr = "0" ;
			
			// 문자열 바꿔치키
			resultStr = commUtil.idxReplace( strQc , fieldQcIdx.get( "measurementMode" ).get( "startInt" ) , fieldQcIdx.get( "measurementMode" ).get( "endInt" ) , resultFieldQcStr ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strQc = null ;
			resultFieldQcStr = null ;
		}
		
		return resultStr ;
	}
	
	public String calculateCalculationMode( String strQc ) {
		String resultStr = "" ;
		
		String resultFieldQcStr = "" ;
		
		try {
			// logger.debug( "strQc :: " + strQc ) ;
			// logger.debug( "fieldQcIdx.get( \"calculationMode\" ) :: " + fieldQcIdx.get( "calculationMode" ) ) ;
			
			// 연산 모드 : true
			resultFieldQcStr = "1" ;
			
			// 문자열 바꿔치키
			resultStr = commUtil.idxReplace( strQc , fieldQcIdx.get( "calculationMode" ).get( "startInt" ) , fieldQcIdx.get( "calculationMode" ).get( "endInt" ) , resultFieldQcStr ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strQc = null ;
			resultFieldQcStr = null ;
		}
		
		return resultStr ;
	}
	
	public String calculateInDeviceSimulationMode( String strQc ) {
		String resultStr = "" ;
		
		String resultFieldQcStr = "" ;
		
		try {
			// logger.debug( "strQc :: " + strQc ) ;
			// logger.debug( "fieldQcIdx.get( \"simulationMode\" ) :: " + fieldQcIdx.get( "simulationMode" ) ) ;
			
			// 시뮬레이션 모드
			if( "Y".equals( strSiteSmlt ) ) {
				// true
				resultFieldQcStr = "1" ;
			}
			else {
				// false
				resultFieldQcStr = "0" ;
			}
			
			// 문자열 바꿔치키
			resultStr = commUtil.idxReplace( strQc , fieldQcIdx.get( "simulationMode" ).get( "startInt" ) , fieldQcIdx.get( "simulationMode" ).get( "endInt" ) , resultFieldQcStr ) ;
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			strQc = null ;
			resultFieldQcStr = null ;
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
			// logger.debug( "strQc :: " + strQc ) ;
			// logger.debug( "fieldQcIdx.get( \"simulationMode\" ) :: " + fieldQcIdx.get( "simulationMode" ) ) ;
			
			// 시뮬레이션 모드
			// 하나라도 "1(시뮬레이션 데이터)" 이면 해당 QC는 "1(시뮬레이션 데이터)" 모두 "0(일반 모드)" 이면 "0(일반 모드)"이다.
			strFieldQc = strQc.substring( fieldQcIdx.get( "simulationMode" ).get( "startInt" ) , fieldQcIdx.get( "simulationMode" ).get( "endInt" ) ) ;
			strGatherFieldQc = strGatherFieldQc.substring( fieldQcIdx.get( "simulationMode" ).get( "startInt" ) , fieldQcIdx.get( "simulationMode" ).get( "endInt" ) ) ;
			
			// logger.debug( "strFieldQc :: " + strFieldQc ) ;
			// logger.debug( "strGatherFieldQc :: " + strGatherFieldQc ) ;
			
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
