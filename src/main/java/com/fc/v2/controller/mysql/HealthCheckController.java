package com.fc.v2.controller.mysql;

import com.fc.v2.dto.RestResponse;
import com.fc.v2.service.mysql.HealthCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**   
 * @ClassName:  HealthCheckController   
 * @Description:健康检查Controller   
 * @author: reisen
 * @date: 2024-10-16 18:20:36
 */
@RestController
@RequestMapping("/health-check")
public class HealthCheckController
{
    @Autowired
    private HealthCheckService healthCheckService;
    
    /**
     * 检查是否有远程用户具有超级权限
     * @Title: superPriv   
     * @param serverId
     * @return        
     * @throws
     */
    @RequestMapping(value = "/super-priv/{serverId}", method = {RequestMethod.POST, RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public RestResponse<Object> superPriv(@PathVariable Long serverId)
    {
        return healthCheckService.superPriv(serverId);
    }
    
    /**
     * 检查是否有用户的密码是弱密码
     * @Title: weakPassword   
     * @param serverId
     * @return        
     * @throws
     */
    @RequestMapping(value = "/weak-password/{serverId}", method = {RequestMethod.POST, RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public RestResponse<Object> weakPassword(@PathVariable Long serverId)
    {
        return healthCheckService.weakPassword(serverId);
    }
    
    /**
     * 检查是否有锁等待
     * @Title: lockWait   
     * @param serverId
     * @return        
     * @throws
     */
    @RequestMapping(value = "/lock-wait/{serverId}", method = {RequestMethod.POST, RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public RestResponse<Object> lockWait(@PathVariable Long serverId)
    {
        return healthCheckService.lockWait(serverId);
    }
    
    /**
     * 检查是否有未完成的事务
     * @Title: transaction   
     * @param serverId
     * @return        
     * @throws
     */
    @RequestMapping(value = "/transaction/{serverId}", method = {RequestMethod.POST, RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public RestResponse<Object> transaction(@PathVariable Long serverId)
    {
        return healthCheckService.transaction(serverId);
    }
    
    /**
     * 检查是否有慢查询
     * @Title: slow   
     * @param serverId
     * @return        
     * @throws
     */
    @RequestMapping(value = "/slow/{serverId}", method = {RequestMethod.POST, RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public RestResponse<Object> slow(@PathVariable Long serverId)
    {
        return healthCheckService.slow(serverId);
    }
    
    /**
     * 检查是否存在非UTF8(UTF8MB4)的编码
     * @Title: charset   
     * @param serverId
     * @return        
     * @throws
     */
    @RequestMapping(value = "/charset/{serverId}", method = {RequestMethod.POST, RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public RestResponse<Object> charset(@PathVariable Long serverId)
    {
        return healthCheckService.charset(serverId);
    }
    
    /**
     * 检查复制状态是否正常
     * @Title: replication   
     * @param serverId
     * @return        
     * @throws
     */
    @RequestMapping(value = "/replication/{serverId}", method = {RequestMethod.POST, RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public RestResponse<Object> replication(@PathVariable Long serverId)
    {
        return healthCheckService.replication(serverId);
    }
    
    /**
     * 检查是否存在无用索引
     * @Title: unusedIndex   
     * @param serverId
     * @return        
     * @throws
     */
    @RequestMapping(value = "/unused-index/{serverId}", method = {RequestMethod.POST, RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public RestResponse<Object> unusedIndex(@PathVariable Long serverId)
    {
        return healthCheckService.unusedIndex(serverId);
    }
    
    /**
     * 检查是否存在没有设置主键的表
     * @Title: noPrimaryKey   
     * @param serverId
     * @return        
     * @throws
     */
    @RequestMapping(value = "/no-primary-key/{serverId}", method = {RequestMethod.POST, RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public RestResponse<Object> noPrimaryKey(@PathVariable Long serverId)
    {
        return healthCheckService.noPrimaryKey(serverId);
    }
    
    /**
     * 检查索引基数是否存在异常
     * @Title: indexCardinality   
     * @param serverId
     * @return        
     * @throws
     */
    @RequestMapping(value = "/index-cardinality/{serverId}", method = {RequestMethod.POST, RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public RestResponse<Object> indexCardinality(@PathVariable Long serverId)
    {
        return healthCheckService.indexCardinality(serverId);
    }
    
    /**
     * 检查是否存在冗余索引
     * @Title: redundantIndex   
     * @param serverId
     * @return        
     * @throws
     */
    @RequestMapping(value = "/redundant-index/{serverId}", method = {RequestMethod.POST, RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public RestResponse<Object> redundantIndex(@PathVariable Long serverId)
    {
        return healthCheckService.redundantIndex(serverId);
    }
}
