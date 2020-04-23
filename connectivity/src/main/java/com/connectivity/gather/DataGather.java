package com.connectivity.gather ;

import java.math.BigDecimal ;
import java.util.Arrays ;
import java.util.HashMap ;
import java.util.List ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;

import com.connectivity.utils.CommUtil ;

public class DataGather
{
	
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private static final Logger logger = LogManager.getLogger( DataGather.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	public static void main( String[ ] args ) {
		// TODO Auto-generated method stub
		
	}
	
	public BigDecimal applyScaleFactor( Object gatherData , Object scaleFactor ) {
		
		BigDecimal resultBigDecimal = new BigDecimal( "0" ) ;
		
		BigDecimal scaleFactorBigDecimal = new BigDecimal( "0" ) ;
		
		try {
			
			resultBigDecimal = new BigDecimal( ( gatherData + "" ) ) ;
			logger.debug( "resultBigDecimal :: " + resultBigDecimal ) ;
			logger.debug( "scaleFactor :: " + scaleFactor ) ;
			logger.debug( "!CommUtil.checkObjNull( ( scaleFactor ) :: " + !CommUtil.checkObjNull( ( scaleFactor ) ) ) ;
			
			if( !CommUtil.checkObjNull( ( scaleFactor ) ) ) {
				
				scaleFactorBigDecimal = new BigDecimal( scaleFactor + "" ) ;
				logger.debug( "scaleFactorBigDecimal :: " + scaleFactorBigDecimal ) ;
				
				resultBigDecimal = resultBigDecimal.multiply( scaleFactorBigDecimal ) ;
				
			}
			
			logger.debug( "resultBigDecimal :: " + resultBigDecimal ) ;
			
		}
		finally {
			gatherData = null ;
			scaleFactor = null ;
			
			scaleFactorBigDecimal = null ;
			
		}
		
		return resultBigDecimal ;
	}
	
	public HashMap< String , Object > getCalculatingGatherData( HashMap< String , Object > gatherDataHashMap , List< HashMap< String , Object > > calculInfoList ) {
		
		HashMap< String , Object > resultHashMap = new HashMap< String , Object >( ) ;
		
		BigDecimal tempResultBigDecimal = new BigDecimal( "0" ) ;
		BigDecimal tempBigDecimal = new BigDecimal( "0" ) ;
		
		String[ ] stdIdxsArr = new String[ 0 ] ;
		
		int i = 0 ;
		int j = 0 ;
		
		try {
			
			for( i = 0 ; i < calculInfoList.size( ) ; i++ ) {
				logger.debug( "calculInfoList.get( i ).get( \"STD_IDX\" ) :: " + calculInfoList.get( i ).get( "STD_IDX" ) ) ;
				logger.debug( "calculInfoList.get( i ).get( \"STD_IDXS\" ) :: " + calculInfoList.get( i ).get( "STD_IDXS" ) ) ;
				logger.debug( "calculInfoList.get( i ).get( \"OPTR\" ) :: " + calculInfoList.get( i ).get( "OPTR" ) ) ;
				
				stdIdxsArr = ( calculInfoList.get( i ).get( "STD_IDXS" ) + "" ).split( "," ) ;
				logger.debug( "stdIdxsArr :: " + Arrays.toString( stdIdxsArr ) ) ;
				
				for( j = 0 ; j < stdIdxsArr.length ; j++ ) {
					
					tempBigDecimal = new BigDecimal( ( gatherDataHashMap.get( stdIdxsArr[ j ] ) + "" ) ) ;
					logger.debug( "tempBigDecimal :: " + j + " :: " + tempBigDecimal ) ;
					
					if( j == 0 ) {
						
						tempResultBigDecimal = tempBigDecimal ;
					}
					else {
						if( "*".equals( calculInfoList.get( i ).get( "OPTR" ) ) ) {
							
							tempResultBigDecimal = tempResultBigDecimal.multiply( tempBigDecimal ) ;
						}
						logger.debug( "tempResultBigDecimal :: " + j + " :: " + tempResultBigDecimal ) ;
					}
					
				}
				
				logger.debug( "tempResultBigDecimal :: " + tempResultBigDecimal ) ;
				resultHashMap.put( ( calculInfoList.get( i ).get( "STD_IDX" ) + "" ) , tempResultBigDecimal ) ;
			}
			
		}
		finally {
			i = 0 ;
			j = 0 ;
			gatherDataHashMap = null ;
			calculInfoList = null ;
			
			tempResultBigDecimal = null ;
			tempBigDecimal = null ;
			stdIdxsArr = null ;
		}
		
		return resultHashMap ;
		
	}
	
}
