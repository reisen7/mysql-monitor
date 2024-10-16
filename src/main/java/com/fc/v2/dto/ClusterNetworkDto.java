package com.fc.v2.dto;

import com.fc.v2.model.mysql.MysqlServer;

import java.util.List;

/**   
 * @ClassName:  ClusterNetWorkDto   
 * @Description:集群拓扑节点数据对象
 * @author: reisen
 * @date:   2024-10-16 18:20:36
 *     
 */
public class ClusterNetworkDto
{
    private List<MysqlServer> mysqlServers;
    private List<Relation> relations;
    
    /**
     * @return the mysqlServers
     */
    public List<MysqlServer> getMysqlServers()
    {
        return mysqlServers;
    }

    /**
     * @param mysqlServers the mysqlServers to set
     */
    public void setMysqlServers(List<MysqlServer> mysqlServers)
    {
        this.mysqlServers = mysqlServers;
    }

    /**
     * @return the relations
     */
    public List<Relation> getRelations()
    {
        return relations;
    }

    /**
     * @param relations the relations to set
     */
    public void setRelations(List<Relation> relations)
    {
        this.relations = relations;
    }
}
