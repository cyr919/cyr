/**
 * 
 */
package com.connectivity.calculate ;

import java.math.BigDecimal ;
import java.math.RoundingMode ;
import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.List ;
import java.util.Map ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.calculate.dao.BetweenDevicesCalculateDao ;
import com.connectivity.common.ConnectivityProperties ;
import com.connectivity.common.dao.CommonDao ;
import com.connectivity.quality.QualityCode ;
import com.connectivity.utils.CommUtil ;
import com.connectivity.utils.ExtndEgovStringUtil ;

/**
 * <pre>
 * 장치간 연산
 * </pre>
 *
 * @author cyr
 * @date 2020-06-10
 */
public class BetweenDevicesCalculate extends QualityCode implements Runnable
{
	
	private Logger logger = LogManager.getLogger( this.getClass( ) ) ;
	
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
	// BetweenDevicesCalculate exe = new BetweenDevicesCalculate( ) ;
	// // exe.calculateBetweenDevicesData( ) ;
	//
	// // Thread btwDvCalcuThread = new Thread( exe ) ;
	// // btwDvCalcuThread.start( ) ;
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
	
	@Override
	public void run( ) {
		
		ConnectivityProperties connectivityProperties = new ConnectivityProperties( ) ;
		try {
			logger.info( "BetweenDevicesCalculate run 시작" ) ;
			connectivityProperties.addOneProcessThreadCnt( ) ;
			
			// 장치간 연산 처리
			this.calculateBetweenDevicesData( ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			connectivityProperties.subtractOneProcessThreadCnt( ) ;
			connectivityProperties = null ;
			logger.info( "BetweenDevicesCalculate run 종료" ) ;
		}
	}
	
	/**
	 * <pre>
	 * 장치간 연산 처리
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-22
	 * @return
	 */
	public void calculateBetweenDevicesData( ) {
		
		logger.debug( "calculateBetweenDevicesData :: " ) ;
		Boolean resultBool = true ;
		
		BetweenDevicesCalculateDao betweenDevicesCalculateDao = new BetweenDevicesCalculateDao( ) ;
		Map< String , Map< String , Object > > stdvInfMap = new HashMap< String , Map< String , Object > >( ) ;
		// 장치 간 연산 정보
		List< Map< String , Object > > calculInfoList = new ArrayList< Map< String , Object > >( ) ;
		int i = 0 ;
		int j = 0 ;
		
		Map< String , Map< String , String > > allDvGthrDt = new HashMap< String , Map< String , String > >( ) ;
		
		String strFirstDmt = "" ;
		String strDmt = "" ;
		
		CommUtil commUtil = new CommUtil( ) ;
		ArrayList< HashMap< String , Object > > idxList = new ArrayList< HashMap< String , Object > >( ) ;
		BigDecimal resultBigDecimal = new BigDecimal( "0" ) ;
		BigDecimal paramBigDecimal = new BigDecimal( "0" ) ;
		BigDecimal scFctBigDecimal = new BigDecimal( "0" ) ;
		BigDecimal avgBigDecimal = new BigDecimal( "0" ) ;
		
		ArrayList< HashMap< String , String > > resultRedisDataList = new ArrayList< HashMap< String , String > >( ) ;
		HashMap< String , String > resultRedisDataMap = new HashMap< String , String >( ) ;
		
		HashMap< String , Object > resultRstDataMap = new HashMap< String , Object >( ) ;
		HashMap< String , Object > resultRstMgpKeyDataMap = new HashMap< String , Object >( ) ;
		// AppIO 저장 데이터
		HashMap< String , String > resultAppioDataMap = new HashMap< String , String >( ) ;
		
		String[ ] optrArr = new String[ 0 ] ;
		String strFuncOptr = "" ;
		int intPLen = 0 ;
		Boolean boolNullCheckPLen = true ;
		String resultQcStr = "" ;
		String strGatherFieldQc = "" ;
		
		String resultValStr = "" ;
		String resultKsStr = "" ;
		String resultVsStr = "" ;
		String paramValue = "" ;
		String paramKey = "" ;
		String delimitersStr = "" ;
		String resultMgpKey = "" ;
		String strMgpKey = "" ;
		String strPointIdx = "" ;
		
		List< Map< String , Object > > appioMapperList = new ArrayList< Map< String , Object > >( ) ;
		
		BetweenDevicesCalculateHistoryAndEvent historyAndEvent = null ;
		CommonDao commonDao = new CommonDao( ) ;
		ExtndEgovStringUtil extndEgovStringUtil = new ExtndEgovStringUtil( ) ;
		
		try {
			logger.debug( "calculateBetweenDevicesData :: try " ) ;
			
			// 장치간 연산 생성 시작 시간
			strFirstDmt = commUtil.getFormatingNowDateTime( "yyyyMMddHHmmssSSS" ) ;
			logger.debug( "strFirstDmt :: " + strFirstDmt ) ;
			
			delimitersStr = "," ;
			
			// 전체 설치 디바이스 정보 조회(redis)
			stdvInfMap = ConnectivityProperties.getStdvInf( ) ;
			
			// app io 맵 정보
			appioMapperList = ConnectivityProperties.getAppioMapperCrhs( ) ;
			
			// 장치간 연산 정보
			calculInfoList = ConnectivityProperties.getBtwnDvCalInfo( );
			
			allDvGthrDt = betweenDevicesCalculateDao.getAllDevicesGatherData( stdvInfMap ) ;
			
			logger.debug( "appioMapperList :: " + appioMapperList ) ;
			// logger.debug( "stdvInfMap :: " + stdvInfMap ) ;
			// logger.debug( "allDvGthrDt :: " + allDvGthrDt ) ;
			logger.debug( ":::::::::: 전체 디바이스 데이터 가져오기 ::::::::::" ) ;
			
			// TODO 시뮬레이션 여부 확인 및 변수 할당
			
			// 장치간 연산 데이터 생성
			for( i = 0 ; i < calculInfoList.size( ) ; i++ ) {
				logger.debug( "btwnDvCalInfoList.get( " + i + " ) :: " + calculInfoList.get( i ) ) ;
				
				idxList = new ArrayList< HashMap< String , Object > >( ) ;
				idxList = ( ArrayList< HashMap< String , Object > > ) calculInfoList.get( i ).get( "IDX_LIST" ) ;
				// logger.debug( "idxList :: " + idxList ) ;
				
				// 소수 점 길이 처리 확인
				if( commUtil.checkObjNull( calculInfoList.get( i ).get( "P_LEN" ) ) ) {
					boolNullCheckPLen = true ;
				}
				else {
					boolNullCheckPLen = false ;
					intPLen = Integer.parseInt( ( calculInfoList.get( i ).get( "P_LEN" ) + "" ) ) ;
				}
				
				// 피연산자 및 연산자 array 변환
				optrArr = ( calculInfoList.get( i ).get( "OPRT" ) + "" ).split( "," ) ;
				// logger.debug( "optrArr :: " + Arrays.toString( optrArr ) ) ;
				
				// 함수 연산시 함수명 소문자 변환
				if( "002".equals( calculInfoList.get( i ).get( "OPRT_TP" ) ) ) {
					// 함수 스트링 소문자 변환
					strFuncOptr = optrArr[ 0 ] ;
					strFuncOptr = strFuncOptr.toLowerCase( ) ;
					// logger.debug( "strFuncOptr :: " + strFuncOptr ) ;
				}
				
				// 연산 처리(사칙연산, sum, min, max)
				for( j = 0 ; j < idxList.size( ) ; j++ ) {
					// logger.debug( "idxList.get( " + j + " ) :: " + idxList.get( j ) ) ;
					
					// 피연산 Value
					paramValue = allDvGthrDt.get( idxList.get( j ).get( "STDV_ID" ) ).get( idxList.get( j ).get( "MGP_KEY" ) ) ;
					paramKey = idxList.get( j ).get( "STDV_ID" ) + "." + idxList.get( j ).get( "MGP_KEY" ) ;
					if( j == 0 ) {
						// 피연산 Keys
						resultKsStr = paramKey ;
						// 피연산 values
						resultVsStr = paramValue ;
					}
					else {
						// 피연산 Keys
						resultKsStr = resultKsStr + delimitersStr + paramKey ;
						// 피연산 values
						resultVsStr = resultVsStr + delimitersStr + paramValue ;
					}
					
					paramBigDecimal = new BigDecimal( paramValue ) ;
					// logger.debug( "tempBigDecimal :: " + j + " :: " + paramBigDecimal ) ;
					
					// logger.debug( "calculInfoList.get( i ).get( \"OPRT_TP\" ) :: " + calculInfoList.get( i ).get( "OPRT_TP" ) ) ;
					if( "001".equals( calculInfoList.get( i ).get( "OPRT_TP" ) ) ) {
						// 사칙 연산
						if( j == 0 ) {
							resultBigDecimal = paramBigDecimal ;
						}
						else {
							if( "*".equals( optrArr[ ( j - 1 ) ] ) ) {
								resultBigDecimal = resultBigDecimal.multiply( paramBigDecimal ) ;
							}
							else if( "+".equals( optrArr[ ( j - 1 ) ] ) ) {
								resultBigDecimal = resultBigDecimal.add( paramBigDecimal ) ;
							}
							else if( "-".equals( optrArr[ ( j - 1 ) ] ) ) {
								resultBigDecimal = resultBigDecimal.subtract( paramBigDecimal ) ;
							}
							else if( "/".equals( optrArr[ ( j - 1 ) ] ) ) {
								resultBigDecimal = resultBigDecimal.divide( paramBigDecimal , intPLen , RoundingMode.DOWN ) ;
							}
							// logger.debug( "tempResultBigDecimal :: " + j + " :: " + tempResultBigDecimal ) ;
						}
						
					} // 사칙 연산
					else {
						// 함수 연산
						if( j == 0 ) {
							resultBigDecimal = paramBigDecimal ;
						}
						else {
							if( "sum".equals( strFuncOptr ) || "avg".equals( strFuncOptr ) ) {
								resultBigDecimal = resultBigDecimal.add( paramBigDecimal ) ;
							}
							else if( "min".equals( strFuncOptr ) ) {
								if( paramBigDecimal.compareTo( resultBigDecimal ) == -1 ) {
									resultBigDecimal = paramBigDecimal ;
								}
							}
							else if( "max".equals( strFuncOptr ) ) {
								if( paramBigDecimal.compareTo( resultBigDecimal ) == 1 ) {
									resultBigDecimal = paramBigDecimal ;
								}
							}
							
							// logger.debug( "tempResultBigDecimal :: " + j + " :: " + tempResultBigDecimal ) ;
						}
					} // 함수 연산
					
					// 장치내 연산 데이터 QC 적용
					// TODO 연산 QC 적용 : OldData, OverFlow
					// logger.debug( "::::: 연산 QC 적용 OldData, OverFlow :::::" ) ;
					// logger.debug( "stdIdxsArr[ " + j + " ] :: " + stdIdxsArr[ j ] ) ;
					// logger.debug( "stdIdxsArr[ " + j + " ]_Q :: " + ( stdIdxsArr[ j ] + "_Q" ) ) ;
					// logger.debug( "gatherDataHashMap.get( stdIdxsArr[ " + j + " ]_Q ) :: " + gatherDataHashMap.get( ( stdIdxsArr[ j ] + "_Q" ) ) ) ;
					
					// strGatherFieldQc = gatherDataHashMap.get( ( stdIdxsArr[ j ] + "_Q" ) ) + "" ;
					strGatherFieldQc = allDvGthrDt.get( idxList.get( j ).get( "STDV_ID" ) ).get( ( idxList.get( j ).get( "MGP_KEY" ) + "_Q" ) ) ;
					// logger.debug( "strGatherFieldQc :: " + j + " :: " + strGatherFieldQc ) ;
					
					if( j == 0 ) {
						// 연산 필드 QC 맨 처음 피연산자의 QC 값을 가진다. 임시 값
						
						resultQcStr = strGatherFieldQc ;
					}
					else {
						resultQcStr = calculateOldData( resultQcStr , strGatherFieldQc ) ;
						resultQcStr = calculateOverFlow( resultQcStr , strGatherFieldQc ) ;
						resultQcStr = calculateBetweenDevicesSimulationMode( resultQcStr , strGatherFieldQc ) ;
					}
					
					// logger.debug( "resultQcStr :: " + resultQcStr ) ;
					// logger.debug( "::::: 연산 QC 적용 OldData, OverFlow end :::::" ) ;
					
				} // for( j = 0 ; j < stdIdxsArr.length ; j++ )
				
				// 함수 연산 - avg 시에 count 나누기
				if( "avg".equals( strFuncOptr ) ) {
					// stdIdxsArr.length
					avgBigDecimal = new BigDecimal( String.valueOf( idxList.size( ) ) ) ;
					resultBigDecimal = resultBigDecimal.divide( avgBigDecimal , intPLen , RoundingMode.DOWN ) ;
				}
				
				// logger.debug( "resultBigDecimal :: " + i + " :: " + resultBigDecimal ) ;
				
				// 연산식 스케일 팩터 적용 - 스케일 팩터가 있는 것만 처리
				if( !commUtil.checkObjNull( calculInfoList.get( i ).get( "SC_FCT" ) ) ) {
					// logger.debug( "calculInfoList.get( i ).get( \"SC_FCT\" ) :: " + i + " :: " + calculInfoList.get( i ).get( "SC_FCT" ) ) ;
					// 연산식 스케일 팩터 적용
					scFctBigDecimal = commUtil.getBigdeciNumValue( ( calculInfoList.get( i ).get( "SC_FCT" ) + "" ) ) ;
					resultBigDecimal = resultBigDecimal.multiply( scFctBigDecimal ) ;
				}
				// 연산식 데이터 소수점 처리 - 소수점 길이가 설정 된 것만 처리
				if( boolNullCheckPLen == false ) {
					// 연산식 데이터 소수점 처리
					resultBigDecimal = commUtil.setScaleStringToBigDecimal( ( resultBigDecimal + "" ) , intPLen , 1 ) ;
				}
				// 장치내 연산 데이터 QC 적용
				// TODO 연산 필드 QC 처리 : 계측 모드, 연산 모드
				// logger.debug( "::::: 연산 QC 적용 계측 모드, 연산 모드 :::::" ) ;
				resultQcStr = calculateMeasurementMode( resultQcStr ) ;
				resultQcStr = calculateCalculationMode( resultQcStr ) ;
				
				// logger.debug( "tempResultBigDecimal :: " + i + " :: " + resultBigDecimal ) ;
				// logger.debug( "resultQcStr :: " + i + " :: " + resultQcStr ) ;
				// logger.debug( "resultKsStr :: " + i + " :: " + resultKsStr ) ;
				// logger.debug( "resultVsStr :: " + i + " :: " + resultVsStr ) ;
				// logger.debug( "calculInfoList.get( i ).get( \"MGP_KEY\" ) :: " + i + " :: " + calculInfoList.get( i ).get( "MGP_KEY" ) ) ;
				
				// 연산 결과 MGP KEY 가져오기
				resultMgpKey = calculInfoList.get( i ).get( "MGP_KEY" ) + "" ;
				// logger.debug( "resultMgpKey :: " + i + " :: " + resultMgpKey ) ;
				
				// 데이터 생성 시간
				strDmt = commUtil.getFormatingNowDateTime( "yyyyMMddHHmmssSSS" ) ;
				
				// 연산 결과 string 변환
				resultValStr = extndEgovStringUtil.getStringFromNullAndObject( resultBigDecimal ) ;
				
				//// redis - 표준모델 데이터 생성
				resultRedisDataMap = new HashMap< String , String >( ) ;
				resultRedisDataMap.put( "ID" , strDmt ) ;
				resultRedisDataMap.put( "V" , resultValStr ) ;
				resultRedisDataMap.put( "Q" , resultQcStr ) ;
				resultRedisDataMap.put( "MGP_KEY" , resultMgpKey ) ;
				
				resultRedisDataList.add( resultRedisDataMap ) ;
				
				// //// redis 저장 - 표준모델
				// betweenDevicesCalculateDao.hmSetBtwnDvCalculData( resultMgpKey , strDmt , ( resultBigDecimal + "" ) , resultQcStr ) ;
				
				// mongodb 저장 map 생성
				resultRstMgpKeyDataMap = new HashMap< String , Object >( ) ;
				resultRstMgpKeyDataMap.put( "ID" , strDmt ) ;
				resultRstMgpKeyDataMap.put( "V" , resultBigDecimal ) ;
				resultRstMgpKeyDataMap.put( "Q" , resultQcStr ) ;
				resultRstMgpKeyDataMap.put( "KS" , resultKsStr ) ;
				resultRstMgpKeyDataMap.put( "VS" , resultVsStr ) ;
				// logger.debug( "resultRstMgpKeyDataMap :: " + i + " :: " + resultRstMgpKeyDataMap ) ;
				
				resultRstDataMap.put( resultMgpKey , resultRstMgpKeyDataMap ) ;
				
				// // Appio 모델 데이터 저장 - redis 저장
				// // logger.debug( "resultMgpKey :: " + i + " :: " + resultMgpKey ) ;
				// if( !commUtil.checkNull( appioMapperMap ) ) {
				// if( !commUtil.checkNull( appioMapperMap.get( resultMgpKey ) ) ) {
				// // logger.debug( "appioMapperMap.get( resultMgpKey ) :: " + i + " :: " + appioMapperMap.get( resultMgpKey ) ) ;
				// resultAppioDataMap.put( appioMapperMap.get( resultMgpKey ) , ( resultBigDecimal + "" ) ) ;
				// }
				// }
				
			} // for( i = 0 ; i < btwnDvCalInfoList.size( ) ; i++ )
			
			// logger.debug( "resultRstDataMap :: " + resultRstDataMap ) ;
			
			// redis 저장 - AppIO 모델
			// AppIO 모델 데이터 생성
			if( !commUtil.checkNull( appioMapperList ) ) {
				for( i = 0 ; i < appioMapperList.size( ) ; i++ ) {
					logger.debug( "appioMapperList.get( " + i + " ) :: " + appioMapperList.get( i ) ) ;
					// appIO Point Idx
					strPointIdx = ( appioMapperList.get( i ).get( "TG_ID" ) + "" ) ;
					// MGP_KEY
					strMgpKey = ( appioMapperList.get( i ).get( "SO_KEY" ) + "" ) ;
					
					resultRstMgpKeyDataMap = new HashMap< String , Object >( ) ;
					resultRstMgpKeyDataMap = ( HashMap< String , Object > ) resultRstDataMap.get( strMgpKey ) ;
					
					// map 형태인 mongodb 저장 데이터로 처리한다.
					resultAppioDataMap.put( strPointIdx , ( resultRstMgpKeyDataMap.get( "V" ) + "" ) ) ;
				}
			}
			// AppIO 모델 데이터 redis 저장
			logger.debug( "resultAppioDataMap :: " + resultAppioDataMap ) ;
			resultBool = commonDao.hmSetAppioData( resultAppioDataMap ) ;
			resultAppioDataMap = null ;
			
			//// redis 저장 - 표준모델
			resultBool = betweenDevicesCalculateDao.hmSetBtwnDvCalculData( resultRedisDataList ) ;
			
			logger.debug( "strSiteSmlt :: " + this.strSiteSmlt ) ;
			logger.debug( "strSiteSmltUsr :: " + this.strSiteSmltUsr ) ;
			
			// history 저장 및 이벤트 데이터 처리 thread 생성
			// BetweenDevicesCalculateHistoryAndEvent historyAndEvent = new BetweenDevicesCalculateHistoryAndEvent( strFirstDmt , resultRstDataMap ) ;
			historyAndEvent = new BetweenDevicesCalculateHistoryAndEvent( strFirstDmt , resultRstDataMap , this.strSiteSmlt , this.strSiteSmltUsr ) ;
			// historyAndEventThread = new Thread( historyAndEvent , "BetweenDevicesCalculateHistoryAndEvent" ) ;
			// historyAndEventThread = new Thread( historyAndEvent ) ;
			// historyAndEventThread.setPriority( 3 );
			// historyAndEventThread.start( ) ;
			ConnectivityProperties.executorService.execute( historyAndEvent ) ;
			
		}
		catch( Exception e ) {
			resultBool = false ;
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			stdvInfMap = null ;
			calculInfoList = null ;
			i = 0 ;
			j = 0 ;
			allDvGthrDt = null ;
			strFirstDmt = null ;
			strDmt = null ;
			commUtil = null ;
			idxList = null ;
			resultBigDecimal = null ;
			paramBigDecimal = null ;
			scFctBigDecimal = null ;
			avgBigDecimal = null ;
			resultRedisDataList = null ;
			resultRedisDataMap = null ;
			resultRstDataMap = null ;
			resultRstMgpKeyDataMap = null ;
			resultAppioDataMap = null ;
			optrArr = null ;
			strFuncOptr = null ;
			intPLen = 0 ;
			boolNullCheckPLen = null ;
			resultQcStr = null ;
			strGatherFieldQc = null ;
			resultValStr = null ;
			resultKsStr = null ;
			resultVsStr = null ;
			paramValue = null ;
			paramKey = null ;
			delimitersStr = null ;
			resultMgpKey = null ;
			strMgpKey = null ;
			strPointIdx = null ;
			appioMapperList = null ;
			historyAndEvent = null ;
			commonDao = null ;
			extndEgovStringUtil = null ;
			stdvInfMap = null ;
			betweenDevicesCalculateDao = null ;
			
			logger.debug( "calculateBetweenDevicesData finally :: " ) ;
		}
		
		return ;
	}
	
}
