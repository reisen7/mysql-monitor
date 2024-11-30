package com.fc.v2.service.monitor;

import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.dto.PagedDto;
import com.fc.v2.model.custom.Tablepar;
import com.fc.v2.model.mysql.InnodbLockWaits;
import com.fc.v2.model.mysql.InnodbTrx;
import com.github.pagehelper.PageInfo;

public interface InnodbService {
	/**
	 * 获取innodb状态
	 * @param serverId
	 * @return
	 */
	AjaxResult getStatus(Long serverId);
	
	/**
	 * 获取innodb事务信息
	 * @param serverId
	 * @return
	 */
	PageInfo<InnodbTrx> getInnodbTrxs(Long serverId, Tablepar tablepar);
	
	/**
	 * 获取innodb锁等待信息
	 * @param serverId
	 * @return
	 */
	PageInfo<InnodbLockWaits> getInnodbLockWaits(Long serverId, Tablepar tablepar);
	
	
}
