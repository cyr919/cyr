package com.prototype.servicehub ;

import org.apache.logging.log4j.LogManager ;
import org.apache.logging.log4j.Logger ;
import org.json.simple.JSONObject ;

public class ServicehubMainRun
{
	// Define a static logger variable so that it references the
	// Logger instance named "MyApp".
	private static final Logger logger = LogManager.getLogger( ServicehubMainRun.class ) ;
	// Logger logger = LogManager.getLogger( ) ;
	
	public static void main( String[ ] args ) {
		
		ServicehubMainRun exe = new ServicehubMainRun( ) ;
		exe.commandSend( "stop" ) ;
		// exe.commandSend( "reset" ) ;
		// exe.commandSend( "SiteSimulModOn" ) ;
		// exe.commandSend( "SiteSimulModOff" ) ;
	}
	
	public void commandSend( String strCommand ) {
		
		Command2Module command2Module = new Command2Module( ) ;
		
		JSONObject resultJsonObject = new JSONObject( ) ;
		String strCommandLower = "" ;
		
		try {
			
			strCommandLower = strCommand.toLowerCase( ) ;
			
			logger.info( "strCommand :: " + strCommand ) ;
			logger.info( "strCommandLower :: " + strCommandLower ) ;
			
			resultJsonObject.put( "MFIF_ID" , "connectivity" ) ;
			resultJsonObject.put( "EVHS_ID" , "connectivity-event-his-id" ) ;
			
			if( "reset".equals( strCommandLower ) ) {
				
				resultJsonObject.put( "COMMAND" , "M03" ) ;
			}
			else if( "devicesimulationreset".equals( strCommandLower ) ) {
				
				resultJsonObject.put( "COMMAND" , "S03" ) ;
			}
			else if( "sitesimulmodon".equals( strCommandLower ) ) {
				
				resultJsonObject.put( "COMMAND" , "S01" ) ;
			}
			else if( "sitesimulmodoff".equals( strCommandLower ) ) {
				
				resultJsonObject.put( "COMMAND" , "S02" ) ;
			}
			else {
				// stop
				resultJsonObject.put( "COMMAND" , "M02" ) ;
			}
			
			logger.info( "resultJsonObject :: " + resultJsonObject ) ;
			
			command2Module.publishCommand2Module( resultJsonObject ) ;
			
		}
		catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		}
		finally {
			resultJsonObject = null ;
			command2Module = null ;
			strCommand = null ;
			strCommandLower = null ;
		}
		
		return ;
	}
	
}
