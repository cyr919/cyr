
package com.prototype ;

public class MainRun
{

	public static void main( String[ ] args )
	{
		PropertyLoader.getDemonProperties( ) ;

		TestDataSet testDataSet = new TestDataSet( ) ;
		Thread thread = new Thread( testDataSet ) ;
		thread.start( ) ;

	}

}
