package com.fc.v2.service.monitor.impl;

import com.fc.v2.dto.PagedDto;
import com.fc.v2.dto.QueryResult;
import com.fc.v2.model.mysql.InnodbLockWaits;
import com.fc.v2.model.mysql.InnodbTrx;
import com.fc.v2.service.monitor.InnodbService;
import com.fc.v2.model.mysql.MysqlServer;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class InnodbServiceImpl extends AbstractService implements InnodbService {

	@Override
	public String getStatus(Long serverId) {
		String sql="show engine innodb status";
		QueryResult<List<Map<Object,Object>>> queryResult = getQueryResult(serverId, sql);
		if (queryResult.isSuccess()) {
			return queryResult.getData().toString().replaceAll("\n", "<br/>");
		}
		else {
			return null;
		}
	}

	@Override
	public PagedDto<InnodbTrx> getInnodbTrxs(Long serverId) {
		String sql="select * from information_schema.INNODB_TRX";
		QueryResult<List<Map<Object,Object>>> queryResult = getQueryResult(serverId, sql);
		if (queryResult.isSuccess()) {
			List<InnodbTrx> innodbTrxs=new ArrayList<>();
			List<Map<Object,Object>> data = queryResult.getData();
			PagedDto<InnodbTrx> pagedDto=new PagedDto<>();
			for (Map<Object, Object> map : data) {
				InnodbTrx trx=new InnodbTrx();
				trx.setTrxAdaptiveHashLatched((Integer)map.get("trx_adaptive_hash_latched"));
				trx.setTrxAdaptiveHashTimeout((BigInteger)map.get("trx_adaptive_hash_timeout"));
				trx.setTrxAutocommitNonLocking((Integer)map.get("trx_autocommit_non_locking"));
				trx.setTrxConcurrencyTickets((BigInteger)map.get("trx_concurrency_tickets"));
				trx.setTrxForeignKeyChecks((Integer)map.get("trx_foreign_key_checks"));
				trx.setTrxId((String)map.get("trx_id"));
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
				trx.setTrxStarted((Date)map.get("trx_started"));
				trx.setTrxState((String)map.get("trx_state"));
				trx.setTrxTablesInUse((BigInteger)map.get("trx_tables_in_use"));
				trx.setTrxTablesLocked((BigInteger)map.get("trx_tables_locked"));
				trx.setTrxUniqueChecks((Integer)map.get("trx_unique_checks"));
				trx.setTrxWaitStarted((Date)map.get("trx_wait_started"));
				trx.setTrxWeight((BigInteger)map.get("trx_weight"));
				
				innodbTrxs.add(trx);
			}
			pagedDto.setData(innodbTrxs);
			return pagedDto;
		}
		else {
			return null;
		}
	}

	@Override
	public PagedDto<InnodbLockWaits> getInnodbLockWaits(Long serverId) {
		MysqlServer mysqlServer = mysqlServerMapper.selectByPrimaryKey(serverId);
		String sql = mysqlServer.getVer().equals("8")?"select * from performance_schema.DATA_LOCKS":"select * from information_schema.INNODB_LOCK_WAITS";
		QueryResult<List<Map<Object,Object>>> queryResult = getQueryResult(serverId, sql);
		if (queryResult.isSuccess()) {
			List<InnodbLockWaits> innodbLockWaits=new ArrayList<>();
			List<Map<Object,Object>> data = queryResult.getData();
			PagedDto<InnodbLockWaits> pagedDto=new PagedDto<>();
			for (Map<Object, Object> map : data) {
				InnodbLockWaits lockWaits=new InnodbLockWaits();
				lockWaits.setBlockingLockId((String)map.get("blocking_lock_id"));
				lockWaits.setBlockingTrxId((String)map.get("blocking_trx_id"));
				lockWaits.setRequestedLockId((String)map.get("requested_lock_id"));
				lockWaits.setRequestingTrxId((String)map.get("requesting_trx_id"));
				innodbLockWaits.add(lockWaits);
			}
			pagedDto.setData(innodbLockWaits);
			return pagedDto;
		}
		else {
			return null;
		}
	}
}
