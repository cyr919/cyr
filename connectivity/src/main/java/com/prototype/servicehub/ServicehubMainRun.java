package com.prototype.servicehub ;

import org.apache.log4j.Logger ;
import org.json.simple.JSONObject ;

public class ServicehubMainRun
{
	static Logger logger = Logger.getLogger( ServicehubMainRun.class ) ;
	
	public static void main( String[ ] args ) {
		
		ServicehubMainRun exe = new ServicehubMainRun( ) ;
		exe.commandSend( "STop" ) ;
	}
	
	public void commandSend( String strCommand ) {
		
		Command2Module command2Module = new Command2Module( ) ;
		JSONObject resultJsonObject = new JSONObject( ) ;
		String strCommandLower = "" ;
		
		try {
			
			strCommandLower = strCommand.toLowerCase( ) ;
			
			logger.info( "strCommand :: " + strCommand ) ;
			logger.info( "strCommandLower :: " + strCommandLower ) ;
			
			resultJsonObject.put( "MODUL_ID" , "connectivity" ) ;
			
			if( "reset".equals( strCommandLower ) ) {
				
				resultJsonObject.put( "COMMAND" , "reset" ) ;
			} else if( "devicesimulationreset".equals( strCommandLower ) ) {
				
				resultJsonObject.put( "COMMAND" , "DeviceSimulationReset" ) ;
			} else {
				resultJsonObject.put( "COMMAND" , "stop" ) ;
			}
			
			logger.info( "resultJsonObject :: " + resultJsonObject ) ;
			
			command2Module.publishCommand2Module( resultJsonObject ) ;
			
		} catch( Exception e ) {
			logger.error( e.getMessage( ) , e ) ;
		} finally {
			resultJsonObject = null ;
			command2Module = null ;
			strCommand = null ;
			strCommandLower = null ;
		}
		
		return ;
	}
	
}
