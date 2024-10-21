package com.fc.v2.service.monitor;

/**
 * @ClassName: SqlAdviserService
 * @Description:SQL建议Service
 * @author: reisen
 * @date: 2024-10-16 18:20:36
 * 
 */
public interface SqlAdviserService {
    /**
     * 获取SQL建议
     * @Title: getAdvice   
     * @param sql
     * @return        
     * @throws
     */
    String getAdvice(Long serverId, String schema, String sql);
}
