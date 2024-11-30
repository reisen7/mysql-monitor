package com.fc.v2.service.monitor.impl;

import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.dto.PagedDto;
import com.fc.v2.dto.Processlist;
import com.fc.v2.dto.QueryResult;
import com.fc.v2.model.custom.Tablepar;
import com.fc.v2.model.monitor.MonitorServer;
import com.fc.v2.model.mysql.InnodbLockWaits;
import com.fc.v2.model.mysql.InnodbTrx;
import com.fc.v2.service.monitor.InnodbService;
import com.fc.v2.util.DateUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InnodbServiceImpl extends AbstractService implements InnodbService {

	@Override
	public AjaxResult getStatus(Long serverId) {
		String sql="show engine innodb status";
		QueryResult<List<Map<Object,Object>>> queryResult = getQueryResult(serverId, sql);
		if (queryResult.isSuccess()) {
			return AjaxResult.success(queryResult.getData().toString().replaceAll("\n", "<br/>"));
		}
		else {
			return AjaxResult.error("加载失败");
		}
	}

	@Override
	public PageInfo<InnodbTrx> getInnodbTrxs(Long serverId, Tablepar tablepar) {
		String sql="select * from information_schema.INNODB_TRX";
		QueryResult<List<Map<Object,Object>>> queryResult = getQueryResult(serverId, sql);
		if (queryResult.isSuccess()) {
			List<InnodbTrx> innodbTrxs=new ArrayList<>();
			List<Map<Object,Object>> data = queryResult.getData();
			int size = data.size(); // 获取分页的总大小
			List<Map<Object, Object>> pageData = data.stream()
					.skip((tablepar.getPage() - 1) * tablepar.getLimit()) // 跳过前面的数据
					.limit(tablepar.getLimit()) // 限制当前页的数据量
					.collect(Collectors.toList()); // 收集结果

			for (Map<Object, Object> map : pageData) {
				InnodbTrx trx=new InnodbTrx();
				trx.setTrxAdaptiveHashLatched((Integer)map.get("trx_adaptive_hash_latched"));
				trx.setTrxAdaptiveHashTimeout((BigInteger)map.get("trx_adaptive_hash_timeout"));
				trx.setTrxAutocommitNonLocking((Integer)map.get("trx_autocommit_non_locking"));
				trx.setTrxConcurrencyTickets((BigInteger)map.get("trx_concurrency_tickets"));
				trx.setTrxForeignKeyChecks((Integer)map.get("trx_foreign_key_checks"));
				trx.setTrxId(map.get("trx_id").toString());
				trx.setTrxIsolationLevel((String)map.get("trx_isolation_level"));
				trx.setTrxIsReadOnly((Integer)map.get("trx_is_read_only"));
				trx.setTrxLastForeignKeyError((String)map.get("trx_last_foreign_key_error"));
				trx.setTrxLockMemoryBytes((BigInteger)map.get("trx_lock_memory_bytes"));
				trx.setTrxLockStructs((BigInteger)map.get("trx_lock_structs"));
				trx.setTrxMysqlThreadId((BigInteger)map.get("trx_mysql_thread_id"));
				trx.setTrxOperationState((String)map.get("trx_operation_state"));
				trx.setTrxQuery((String)map.get("trx_query"));
				trx.setTrxRequestedLockId((String)map.get("trx_requested_lock_id"));
				trx.setTrxRowsLocked((BigInteger)map.get("trx_rows_locked"));
				trx.setTrxRowsModified((BigInteger)map.get("trx_rows_modified"));
				trx.setTrxStarted(DateUtils.localDateTimeToDate((LocalDateTime)map.get("trx_started"))); // LocalDateTime 转 Date
				trx.setTrxState((String)map.get("trx_state"));
				trx.setTrxTablesInUse((BigInteger)map.get("trx_tables_in_use"));
				trx.setTrxTablesLocked((BigInteger)map.get("trx_tables_locked"));
				trx.setTrxUniqueChecks((Integer)map.get("trx_unique_checks"));
				trx.setTrxWaitStarted((Date)map.get("trx_wait_started"));
				trx.setTrxWeight((BigInteger)map.get("trx_weight"));
				innodbTrxs.add(trx);
			}
			PageInfo<InnodbTrx> pageInfo = new PageInfo<>(innodbTrxs);
			pageInfo.setTotal(size);
			return pageInfo;
		}
		else {
			return null;
		}
	}

	@Override
	public PageInfo<InnodbLockWaits> getInnodbLockWaits(Long serverId, Tablepar tablepar) {
		MonitorServer monitorServe = monitorServerMapper.selectByPrimaryKey(serverId);
		String sql = monitorServe.getVersion().equals("8")?"select * from performance_schema.DATA_LOCKS":"select * from information_schema.INNODB_LOCK_WAITS";
		QueryResult<List<Map<Object,Object>>> queryResult = getQueryResult(serverId, sql);
		if (queryResult.isSuccess()) {
			List<InnodbLockWaits> innodbLockWaits=new ArrayList<>();
			List<Map<Object,Object>> data = queryResult.getData();
			int size = data.size(); // 获取分页的总大小
			List<Map<Object, Object>> pageData = data.stream()
					.skip((tablepar.getPage() - 1) * tablepar.getLimit()) // 跳过前面的数据
					.limit(tablepar.getLimit()) // 限制当前页的数据量
					.collect(Collectors.toList()); // 收集结果
			for (Map<Object, Object> map : pageData) {
				InnodbLockWaits lockWaits=new InnodbLockWaits();
				lockWaits.setBlockingLockId((String)map.get("blocking_lock_id"));
				lockWaits.setBlockingTrxId((String)map.get("blocking_trx_id"));
				lockWaits.setRequestedLockId((String)map.get("requested_lock_id"));
				lockWaits.setRequestingTrxId((String)map.get("requesting_trx_id"));
				innodbLockWaits.add(lockWaits);
			}
			PageInfo<InnodbLockWaits> pageInfo = new PageInfo<>(innodbLockWaits);
			pageInfo.setTotal(size);
			return pageInfo;
		}
		else {
			return null;
		}
	}
}
