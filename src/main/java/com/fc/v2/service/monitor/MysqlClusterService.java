package com.fc.v2.service.monitor;

import com.fc.v2.dto.ClusterNetworkDto;
import com.fc.v2.dto.RestResponse;
import com.fc.v2.model.mysql.MysqlCluster;


import java.util.List;

/**   
 * @ClassName:  MysqlClusterService   
 * @Description:MySQL集群 Service
 * @author: reisen
 * @date:   2024-10-16 18:20:36
 *     
 */
public interface MysqlClusterService
{
    /**
     * 获取所有MySQL集群
     * @Title: getAllClusters   
     * @return        
     * @throws
     */
    public List<MysqlCluster> getAllClusters();
    
    /**
     * 获取指定集群的拓扑节点信息
     * @Title: getClusterNetworkById   
     * @param clusterId
     * @return        
     * @throws
     */
    public ClusterNetworkDto getClusterNetworkById(Long clusterId);

    /**
     * 保存集群
     * @Title: saveCluster   
     * @param id
     * @param name
     * @return        
     * @throws
     */
    public RestResponse<String> saveCluster(Long id, String name);

    /**
     * 删除集群
     * @Title: deleteCluster   
     * @param id
     * @return        
     * @throws
     */
    public RestResponse<String> deleteCluster(Long id);
}
