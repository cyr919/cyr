package com.prototype.adapter ;

import java.math.BigDecimal ;
import java.time.LocalDateTime ;
import java.time.format.DateTimeFormatter ;
import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.Iterator ;
import java.util.List ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;
import org.json.simple.JSONObject ;

import com.connectivity.gather.DataGather ;
import com.connectivity.utils.CommUtil ;
import com.prototype.PropertyLoader ;

public class AdapterDataGenerator implements Runnable
{
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private static final Logger logger = LogManager.getLogger( AdapterDataGenerator.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	private boolean isDemonLive = false ;
	
	public AdapterDataGenerator( ) {
	}
	
	@Override
	public void run( ) {
		
		int intVal = 0 ;
		
		BigDecimal tempBDVal = new BigDecimal( "0" ) ;
		
		HashMap< String , Object > tempHashMap = new HashMap<>( ) ;
		int i = 0 ;
		Iterator< ? > iteratorHashMapKeys = null ;
		String strHashMapKey = "" ;
		
		HashMap< String , Object > dataHashMap = new HashMap<>( ) ;
		
		List< HashMap< String , Object > > dtList = new ArrayList<>( ) ;
		int j = 0 ;
		
		DataGather dataGather = new DataGather( ) ;
		
		LocalDateTime nowLocalDateTime = null ;
		String strNowLocalDateTime = "" ;
		
		JSONObject resultJsonObject = null ;
		JSONObject dataJsonObject = null ;
		Data2Connectivity data2Connectivity = new Data2Connectivity( ) ;
		
		try {
			PropertyLoader.PROCESS_THREAD_CNT++ ;
			
			setDemonLive( true ) ;
			
			logger.debug( "PropertyLoader.DEVICE_PROPERTIES_DT_MDL :: " ) ;
			logger.debug( PropertyLoader.DEVICE_PROPERTIES_DT_MDL ) ;
			logger.debug( "PropertyLoader.DEVICE_PROPERTIES_DT_MDL :: " ) ;
			
			iteratorHashMapKeys = PropertyLoader.DEVICE_PROPERTIES_DT_MDL.keySet( ).iterator( ) ;
			while( iteratorHashMapKeys.hasNext( ) ) {
				
				strHashMapKey = ( String ) iteratorHashMapKeys.next( ) ;
				logger.debug( "strHashMapKey :: " + strHashMapKey ) ;
				
				logger.debug( "PropertyLoader.DEVICE_PROPERTIES_DT_MDL.get( " + strHashMapKey + " ) :: " ) ;
				logger.debug( PropertyLoader.DEVICE_PROPERTIES_DT_MDL.get( strHashMapKey ) ) ;
				logger.debug( ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::" ) ;
				
				dataHashMap = new HashMap< String , Object >( ) ;
				
				////////////////////////////////////////////////////
				// 표준인덱스 처리
				
				// 값 참고로 변경
				dtList = PropertyLoader.DEVICE_PROPERTIES_DT_MDL.get( strHashMapKey ) ;
				
				logger.debug( "dtList :: " + dtList ) ;
				
				for( j = 0 ; j < dtList.size( ) ; j++ ) {
					
					intVal = CommUtil.getRandomInt( 100 , 10 ) ;
					logger.debug( "dtList.get( " + j + " ).get( \"STD_IDX\" ) :: " + dtList.get( j ).get( "STD_IDX" ) ) ;
					tempBDVal = dataGather.applyScaleFactor( intVal , dtList.get( j ).get( "SC_FCT" ) ) ;
					
					dataHashMap.put( ( dtList.get( j ).get( "STD_IDX" ) + "" ) , tempBDVal ) ;
					
				}
				
				logger.debug( ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::" ) ;
				logger.debug( "표준 인덱스 처리 후 dataHashMap :: " + dataHashMap ) ;
				
				nowLocalDateTime = LocalDateTime.now( ) ;
				strNowLocalDateTime = nowLocalDateTime.format( DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss.SSS" ) ) ;
				
				logger.info( "nowLocalDateTime :: " + nowLocalDateTime ) ;
				logger.info( "strNowLocalDateTime :: " + strNowLocalDateTime ) ;
				
				////////////////////////////////////////////////////
				// jsonData 만들기
				resultJsonObject = new JSONObject( ) ;
				dataJsonObject = new JSONObject( dataHashMap ) ;
				
				resultJsonObject.put( "DVIF_ID" , strHashMapKey ) ;
				resultJsonObject.put( "DATA_DT" , strNowLocalDateTime ) ;
				resultJsonObject.put( "DATA" , dataJsonObject ) ;
				
				logger.info( "resultJsonObject :: " + resultJsonObject ) ;
				////////////////////////////////////////////////////
				// rabbitmq pub
				data2Connectivity.publishData2Connectivity( resultJsonObject ) ;
				
				////////////////////////////////////////////////////
			}
			
		} catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		} finally {
			intVal = 0 ;
			i = 0 ;
			j = 0 ;
			tempBDVal = null ;
			tempHashMap = null ;
			iteratorHashMapKeys = null ;
			strHashMapKey = null ;
			dataHashMap = null ;
			dtList = null ;
			dataGather = null ;
			
			nowLocalDateTime = null ;
			strNowLocalDateTime = null ;
			resultJsonObject = null ;
			dataJsonObject = null ;
			data2Connectivity = null ;
			
			PropertyLoader.PROCESS_THREAD_CNT-- ;
			setDemonLive( false ) ;
			
		}
		
	}
	
	public boolean isDemonLive( ) {
		return isDemonLive ;
	}
	
	public void setDemonLive( boolean isDemonLive ) {
		this.isDemonLive = isDemonLive ;
	}
	
}
