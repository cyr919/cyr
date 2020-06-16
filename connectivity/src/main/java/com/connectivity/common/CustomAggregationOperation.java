/**
 * 
 */
package com.connectivity.common ;

import org.bson.Document ;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation ;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext ;

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-06-16
 */
public class CustomAggregationOperation implements AggregationOperation
{
	
	private String jsonOperation ;
	
	public CustomAggregationOperation( String jsonOperation ) {
		this.jsonOperation = jsonOperation ;
	}
	
	@Override
	public Document toDocument( AggregationOperationContext aggregationOperationContext ) {
		return aggregationOperationContext.getMappedObject( Document.parse( jsonOperation ) ) ;
	}
	
}
