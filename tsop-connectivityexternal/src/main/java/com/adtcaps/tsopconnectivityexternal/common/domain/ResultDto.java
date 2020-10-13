package com.adtcaps.tsopconnectivityexternal.common.domain ;

import java.io.Serializable ;

/**
 * <ul>
 * <li>업무 그룹명 : tsop-connectivity-external-dev</li>
 * <li>서브 업무명 : com.adtcaps.tsop.common.domain</li>
 * <li>설 명 : ResultDto.java</li>
 * <li>작성일 : 2020. 9. 29.</li>
 * <li>작성자 : jeonyb4</li>
 * </ul>
 */
public class ResultDto implements Serializable
{
	
	private static final long serialVersionUID = 1L ;
	
	/**
	 * 코드
	 */
	protected String resultCode ;
	
	/**
	 * 메시지
	 */
	protected String resultMessage ;
	
	/**
	 * 데이터
	 */
	protected Object resultData ;
	
	public ResultDto( ) {
	}
	
	public ResultDto( String resultCode , String resultMessage ) {
		this( resultCode , resultMessage , null ) ;
	}
	
	public ResultDto( String resultCode , Object resultData ) {
		this( resultCode , "" , resultData ) ;
	}
	
	public ResultDto( String resultCode , String resultMessage , Object resultData ) {
		this.resultCode = resultCode ;
		this.resultMessage = resultMessage ;
		this.resultData = resultData ;
	}
	
	public String getResultCode( ) {
		return resultCode ;
	}
	
	public void setResultCode( String resultCode ) {
		this.resultCode = resultCode ;
	}
	
	public String getResultMessage( ) {
		return resultMessage ;
	}
	
	public void setResultMessage( String resultMessage ) {
		this.resultMessage = resultMessage ;
	}
	
	public Object getResultData( ) {
		return resultData ;
	}
	
	public void setResultData( Object resultData ) {
		this.resultData = resultData ;
	}
}
