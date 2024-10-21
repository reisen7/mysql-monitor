package com.fc.v2.controller.mysql;

import com.fc.v2.dto.ClusterNetworkDto;
import com.fc.v2.dto.RestResponse;
import com.fc.v2.model.mysql.MysqlCluster;
import com.fc.v2.service.monitor.MysqlClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**   
 * @ClassName:  MysqlClusterController   
 * @Description:MySQL集群Controller
 * @author: reisen
 * @date:   2024-10-16 18:20:36
 *     
 */
@RestController
public class MysqlClusterController
{
    @Autowired
    private MysqlClusterService mysqlClusterService;

    /**
     * 获取所有集群信息
     * @Title: getAllClusters   
     * @return        
     * @throws
     */
    @RequestMapping("/mysql/cluster/all")
    @CrossOrigin(origins = "*")
    public List<MysqlCluster> getAllClusters() {
        return mysqlClusterService.getAllClusters();
    }
    /**
     * 获取指定集群的拓扑节点信息
     * @Title: getClusterNetWorkById   
     * @return        
     * @throws
     */
    @RequestMapping("/mysql/cluster/network")
    @CrossOrigin(origins = "*")
    public ClusterNetworkDto getClusterNetworkById(HttpServletRequest request) {
        //请求数据
        Long clusterId = Long.valueOf(request.getParameter("clusterId"));
        return mysqlClusterService.getClusterNetworkById(clusterId);
    }
    /**
     * 新增集群
     * @Title: addCluster   
     * @param request
     * @return        
     * @throws
     */
    @RequestMapping("/mysql/cluster/save")
    @CrossOrigin(origins = "*")
    public RestResponse<String> saveCluster(HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("id"));
        String name = request.getParameter("name");
        return mysqlClusterService.saveCluster(id,name);
    }
    /**
     * 删除集群
     * @Title: deleteCluster   
     * @param request
     * @return        
     * @throws
     */
    @RequestMapping("/mysql/cluster/delete")
    @CrossOrigin(origins = "*")
    public RestResponse<String> deleteCluster(HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("id"));
        return mysqlClusterService.deleteCluster(id);
    }
}
