package com.fc.v2.service.monitor;


import com.fc.v2.dto.RestResponse;

/**   
 * @ClassName:  HealthCheckService   
 * @Description:健康检查Service
 * @author: reisen
 * @date:   2024-10-16 18:20:36
 *     
 */
public interface HealthCheckService
{
    /**
     * 查询指定节点是否存在远程用户具备超级权限
     * @Title: superPriv   
     * @param serverId
     * @return        
     * @throws
     */
    public RestResponse<Object> superPriv(Long serverId);
    /**
     * 检查是否有用户的密码是弱密码
     * @Title: weakPassword   
     * @param serverId
     * @return        
     * @throws
     */
    public RestResponse<Object> weakPassword(Long serverId);
    
    /**
     * 检查是否有锁等待
     * @Title: lockWait   
     * @param serverId
     * @return        
     * @throws
     */
    public RestResponse<Object> lockWait(Long serverId);
    
    /**
     * 检查是否有未完成的事务
     * @Title: transaction   
     * @param serverId
     * @return        
     * @throws
     */
    public RestResponse<Object> transaction(Long serverId);
    
    /**
     * 检查是否有慢查询
     * @Title: slow   
     * @param serverId
     * @return        
     * @throws
     */
    public RestResponse<Object> slow(Long serverId);
    
    /**
     * 检查是否存在非UTF8(UTF8MB4)的编码
     * @Title: charset   
     * @param serverId
     * @return        
     * @throws
     */
    public RestResponse<Object> charset(Long serverId);
    
    /**
     * 检查连接数设置在当前环境下是否合理
     * @Title: connections   
     * @param serverId
     * @return        
     * @throws
     */
    public RestResponse<Object> connections(Long serverId);
    
    /**
     * 检查复制状态是否正常
     * @Title: replication   
     * @param serverId
     * @return        
     * @throws
     */
    public RestResponse<Object> replication(Long serverId);
    
    /**
     * 检查是否存在无用索引
     * @Title: unusedIndex   
     * @param serverId
     * @return        
     * @throws
     */
    public RestResponse<Object> unusedIndex(Long serverId);
    
    /**
     * 检查是否存在没有设置主键的表
     * @Title: noPrimaryKey   
     * @param serverId
     * @return        
     * @throws
     */
    public RestResponse<Object> noPrimaryKey(Long serverId);
    
    /**
     * 检查索引基数是否存在异常
     * @Title: indexCardinality   
     * @param serverId
     * @return        
     * @throws
     */
    public RestResponse<Object> indexCardinality(Long serverId);
    
    /**
     * 检查是否存在冗余索引
     * @Title: redundantIndex   
     * @param serverId
     * @return        
     * @throws
     */
    public RestResponse<Object> redundantIndex(Long serverId);
}
