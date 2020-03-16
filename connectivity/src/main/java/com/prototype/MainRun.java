
package com.prototype ;

public class MainRun
{

	public static void main( String[ ] args )
	{

		try
		{
			PropertyLoader.getDemonProperties( ) ;

			TestDataSet testDataSet = new TestDataSet( ) ;
			Thread thread = new Thread( testDataSet ) ;
			thread.start( ) ;
			
		}
		catch ( Exception e )
		{
			e.printStackTrace( );
		}
		
	}

}
