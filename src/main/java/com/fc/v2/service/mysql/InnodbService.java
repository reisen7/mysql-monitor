package com.fc.v2.service.mysql;

import com.fc.v2.dto.PagedDto;
import com.fc.v2.model.mysql.InnodbLockWaits;
import com.fc.v2.model.mysql.InnodbTrx;

public interface InnodbService {
	/**
	 * 获取innodb状态
	 * @param serverId
	 * @return
	 */
	String getStatus(Long serverId);
	
	/**
	 * 获取innodb事务信息
	 * @param serverId
	 * @return
	 */
	PagedDto<InnodbTrx> getInnodbTrxs(Long serverId);
	
	/**
	 * 获取innodb锁等待信息
	 * @param serverId
	 * @return
	 */
	PagedDto<InnodbLockWaits> getInnodbLockWaits(Long serverId);
	
	
}
