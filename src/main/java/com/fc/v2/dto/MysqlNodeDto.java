package com.fc.v2.dto;


import com.fc.v2.model.monitor.MonitorServer;

/**
 * @ClassName:  MysqlNodeDto   
 * @Description
 * @author: reisen
 * @date:  2024-10-16 18:20:36
 *     
 */
public class MysqlNodeDto
{
    private MonitorServer monitorServer;
    
    private String clusterName = "";
    
    /**
     * @return the monitorServer
     */
    public MonitorServer getMonitorServer()
    {
        return monitorServer;
    }
    
    /**
     * @param monitorServer the monitorServer to set
     */
    public void setMonitorServer(MonitorServer monitorServer)
    {
        this.monitorServer = monitorServer;
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
