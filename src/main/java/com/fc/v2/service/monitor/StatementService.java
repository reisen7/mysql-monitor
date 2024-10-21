package com.fc.v2.service.monitor;

/**
 * @ClassName: SqlExplainService
 * @Description:执行计划service
 * @author: reisen
 * @date: 2024-10-16 18:20:36
 * 
 */
public interface StatementService {
    Object getExplain(Long serverId, String schema, String sql);

    Object getTables(Long serverId, String statementExample);

    Object getShowCreateTable(Long serverId, String schema, String tableName);

    Object getShowIndexFromTable(Long serverId, String schema, String tableName);

    Object getStatementHistory(Long serverId, String orderBy);

    Object getShowTableStatus(Long serverId, String schema, String table);

    Object executeSql(Long serverId, String schema, String sql);

    Object getStatementSlow(Long serverId, String orderBy, Integer pageIndex, Integer pageSize);
}
