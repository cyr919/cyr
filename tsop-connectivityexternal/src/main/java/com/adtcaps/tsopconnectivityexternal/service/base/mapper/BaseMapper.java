package com.adtcaps.tsopconnectivityexternal.service.base.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * <ul>
 * <li>업무 그룹명 : tsop-connectivity-external-dev</li>
 * <li>서브 업무명 : com.adtcaps.tsop.common.mapper</li>
 * <li>설  명 : CommonMapper.java</li>
 * <li>작성일 : 2020. 9. 29.</li>
 * <li>작성자 : jeonyb4</li>
 * </ul>
 */
@Mapper
public interface BaseMapper {
	
	public String selectCurrentDate();

}
