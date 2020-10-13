package com.adtcaps.tsopconnectivityexternal.service.base.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.web.bind.annotation.GetMapping ;
import org.springframework.web.bind.annotation.RestController;

import com.adtcaps.tsopconnectivityexternal.service.base.service.BaseService ;

/**
 * 
 * <ul>
 * <li>업무 그룹명 : tsop-connectivity-external-dev</li>
 * <li>서브 업무명 : com.adtcaps.tsop.service.vps.controller</li>
 * <li>설  명 : BaseController.java</li>
 * <li>작성일 : 2020. 9. 29.</li>
 * <li>작성자 : jeonyb4</li>
 * </ul>
 */
@RestController
public class BaseController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	 
    @Autowired    
    private BaseService baseService;
    
    
	@SuppressWarnings("rawtypes")
	@GetMapping( "/" )
	public String index( ) {
		// 화면에 모니터링 로그 남기기
		logger.info( "index" ) ;
		try {
		}
		catch( Exception e ) {
			// 화면에 모니터링 로그 남기기
			logger.error( e.getMessage( ) ) ;
			logger.error( "{}", e ) ;
		}
		return "helloworld!";
		
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping( "/base/findCurrentDate" )
	public String findCurrentDate( ) {
		// 화면에 모니터링 로그 남기기
		logger.info( "findCurrentDate" ) ;
		
		String returnString = "";		
		try {
			returnString = baseService.findCurrentDate( ) ;
		}
		catch( Exception e ) {
			// 화면에 모니터링 로그 남기기
			returnString = e.getMessage( ) ;
			logger.error( e.getMessage( ) ) ;
			logger.error( "{}", e ) ;
		}
		return returnString ;
		
	}
	
	
    /**
     * 
     * listBuilding
     *
     * @return ResponseEntity
     */
//    @SuppressWarnings("rawtypes")
//    @GetMapping(value = CONTEXT_URL + "/buildings")
//    public ResponseEntity listBuilding() {
//        
//    	ResponseEntity<ResultDto> resEntity = null;
//		String returnString = "";
//    	
//    	try {
//    		
//    		List<SiteResultDto> siteResultDtoList = new ArrayList<SiteResultDto>();
//    		
//    		SiteResultDto siteResultDto = new SiteResultDto();
//    		siteResultDto.setSiteId("0001");
//    		siteResultDto.setSiteName("SKT T-타워");
//    		siteResultDto.setSiteAddress("서울특별시 중구 을지로 65");
//    		siteResultDtoList.add(siteResultDto);
//    		
//    		returnString = Const.Common.RESULT_CODE.SUCCESS;
//			resEntity = ResponseEntity.ok(new ResultDto(returnString, "", siteResultDtoList));
//    		
//    	} catch(Exception ex) {
//            logger.error("listBuilding.Exception: {}", ex);
//    		returnString = Const.Common.RESULT_CODE.FAIL;
//    		resEntity = ResponseEntity.ok(new ResultDto(returnString, ex));
//        }
//    	return resEntity;
//    }
    
    


}
