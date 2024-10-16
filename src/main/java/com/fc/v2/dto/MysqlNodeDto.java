package com.fc.v2.dto;


import com.fc.v2.model.mysql.MysqlServer;

/**
 * @ClassName:  MysqlNodeDto   
 * @Description
 * @author: reisen
 * @date:  2024-10-16 18:20:36
 *     
 */
public class MysqlNodeDto
{
    private MysqlServer mysqlServer;
    
    private String clusterName = "";
    
    /**
     * @return the mysqlServer
     */
    public MysqlServer getMysqlServer()
    {
        return mysqlServer;
    }
    
    /**
     * @param mysqlServer the mysqlServer to set
     */
    public void setMysqlServer(MysqlServer mysqlServer)
    {
        this.mysqlServer = mysqlServer;
    }
    
    /**
     * @return the clusterName
     */
    public String getClusterName()
    {
        return clusterName;
    }
    
    /**
     * @param clusterName the clusterName to set
     */
    public void setClusterName(String clusterName)
    {
        this.clusterName = clusterName;
    }
    
}
