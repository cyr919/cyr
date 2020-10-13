package com.adtcaps.tsopconnectivityexternal.service.base.service.impl ;

import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.stereotype.Service ;

import com.adtcaps.tsopconnectivityexternal.service.base.mapper.BaseMapper ;
import com.adtcaps.tsopconnectivityexternal.service.base.service.BaseService ;

/**
 * <ul>
 * <li>업무 그룹명 : tsop-connectivity-external-dev</li>
 * <li>서브 업무명 : com.adtcaps.tsop.common.service.impl</li>
 * <li>설 명 : CommonServiceImpl.java</li>
 * <li>작성일 : 2020. 9. 29.</li>
 * <li>작성자 : jeonyb4</li>
 * </ul>
 */
@Service
public class BaseServiceImpl implements BaseService
{
	
	@Autowired
	private BaseMapper baseMapper ;
	
	/**
	 * findCurrentDate
	 *
	 * @return String
	 */
	@Override
	public String findCurrentDate( ) {
		return baseMapper.selectCurrentDate( ) ;
	}
	
}
