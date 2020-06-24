package com ;

import java.util.HashMap ;
import java.util.Map ;
import java.util.concurrent.ConcurrentHashMap ;

/**
 * 
 */

/**
 * <pre>
 * </pre>
 *
 * @author cyr
 * @date 2020-06-22
 */
public class HashMapTest01
{
	
	/**
	 * <pre>
	 * </pre>
	 * 
	 * @author cyr
	 * @date 2020-06-22
	 * @param args
	 */
	public static void main( String[ ] args ) {
		// TODO Auto-generated method stub
		
		HashMapTest01 exe = new HashMapTest01( ) ;
		
		// exe.hashmapCheckTest01( );
		// exe.hashmapCheckTest02( );
//		exe.hashmapCheckTest03( ) ;
		exe.hashmapCheckTest04( ) ;
		
	}
	
	public void hashmapCheckTest01( ) {
		
		HashMap< String , Object > map01 = new HashMap< String , Object >( ) ;
		HashMap< String , Object > map02 = new HashMap< String , Object >( ) ;
		
		try {
			System.out.println( "===========hashmapCheckTest01===========" ) ;
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			
			map01.put( "key1" , "value1" ) ;
			map01.put( "key2" , "value2" ) ;
			map01.put( "key3" , "value3" ) ;
			
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			
			map02 = map01 ;
			
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			
			map02.put( "key4" , "value4" ) ;
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			
			map01.put( "key3" , "value33" ) ;
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			
			map02 = null ;
			
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			
		}
		catch( Exception e ) {
			System.out.println( e ) ;
		}
		finally {
			System.out.println( "===========hashmapCheckTest01 finally===========" ) ;
			
		}
		
	}
	
	public void hashmapCheckTest02( ) {
		
		HashMap< String , Object > map01 = new HashMap< String , Object >( ) ;
		HashMap< String , Object > map02 = new HashMap< String , Object >( ) ;
		
		try {
			System.out.println( "===========hashmapCheckTest02===========" ) ;
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			
			map01.put( "key1" , "value1" ) ;
			map01.put( "key2" , "value2" ) ;
			map01.put( "key3" , "value3" ) ;
			
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			
			map02 = map01 ;
			
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			
			map02.put( "key4" , "value4" ) ;
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			
			map01.put( "key3" , "value33" ) ;
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			
			map01 = null ;
			
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			
		}
		catch( Exception e ) {
			System.out.println( e ) ;
		}
		finally {
			System.out.println( "===========hashmapCheckTest02 finally===========" ) ;
			
		}
		
	}
	
	public void hashmapCheckTest03( ) {
		
		Map< String , Object > map01 = new ConcurrentHashMap< String , Object >( ) ;
		Map< String , Object > map02 = new HashMap< String , Object >( ) ;
		Map< String , Object > map03 = new HashMap< String , Object >( ) ;
		
		try {
			System.out.println( "===========hashmapCheckTest03===========" ) ;
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			
			map01.put( "key1" , "value1" ) ;
			map01.put( "key2" , "value2" ) ;
			map01.put( "key3" , "value3" ) ;
			
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			
			map02 = map01 ;
			
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			
			map02.put( "key4" , "value4" ) ;
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			
			map01.put( "key3" , "value33" ) ;
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			
			map03.put( "key10" , "value10" ) ;
			map03.put( "key11" , "value11" ) ;
			map03.put( "key12" , "value12" ) ;
			map03.put( "key13" , "value13" ) ;
			
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			System.out.println( "map03 :: " + map03 ) ;
			
			map01 = map03 ;
			
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			System.out.println( "map03 :: " + map03 ) ;
			
		}
		catch( Exception e ) {
			System.out.println( e ) ;
		}
		finally {
			System.out.println( "===========hashmapCheckTest01 finally===========" ) ;
			
		}
		
	}

	public void hashmapCheckTest04( ) {
		
		Map< String , Object > map01 = new HashMap< String , Object >( ) ;
		Map< String , Object > map02 = new HashMap< String , Object >( ) ;
		Map< String , Object > map03 = new HashMap< String , Object >( ) ;
		
		try {
			System.out.println( "===========hashmapCheckTest04===========" ) ;
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			
			map01.put( "key1" , "value1" ) ;
			map01.put( "key2" , "value2" ) ;
			map01.put( "key3" , "value3" ) ;
			
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			
			map02 = map01 ;
			
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			
			map02.put( "key4" , "value4" ) ;
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			
			map01.put( "key3" , "value33" ) ;
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			
			map03.put( "key10" , "value10" ) ;
			map03.put( "key11" , "value11" ) ;
			map03.put( "key12" , "value12" ) ;
			map03.put( "key13" , "value13" ) ;
			
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			System.out.println( "map03 :: " + map03 ) ;
			
			map01 = map03 ;
			
			System.out.println( "======================" ) ;
			System.out.println( "map01 :: " + map01 ) ;
			System.out.println( "map02 :: " + map02 ) ;
			System.out.println( "map03 :: " + map03 ) ;
			
		}
		catch( Exception e ) {
			System.out.println( e ) ;
		}
		finally {
			System.out.println( "===========hashmapCheckTest01 finally===========" ) ;
			
		}
		
	}	
	
	
	
}
