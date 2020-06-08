/**
 * 
 */
package com.connectivity.common ;

import java.util.HashMap ;

import org.bson.Document ;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression ;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-06-08
 */
public class FilterAggregationExpression implements AggregationExpression
{
	private HashMap< String , Object > filterCondMap = new HashMap< String , Object >( ) ;
	private String inputStr = "" ;
	
	public FilterAggregationExpression( String inputStr , HashMap< String , Object > filterCondMap ) {
		this.inputStr = inputStr ;
		this.filterCondMap = filterCondMap ;
	}
	
	@Override
	public Document toDocument( AggregationOperationContext context ) {
		HashMap< String , Object > filterMap = new HashMap< String , Object >( ) ;
		filterMap.put( "input" , this.inputStr ) ;
		filterMap.put( "cond" , this.filterCondMap ) ;
		
		return new Document( "$filter" , filterMap ) ;
	}
	
}
