package com.fc.v2.dto;


import com.fc.v2.model.monitor.MonitorServer;

/**
 * @ClassName:  Relation   
 * @Description:MySQL节点的复制关系
 * @author: reisen
 * @date:   2024-10-16 18:20:36
 */
public class Relation
{
    private MonitorServer from;
    private MonitorServer to;
    private SlaveStatus slaveStatus;
    
    public Relation(MonitorServer from, MonitorServer to, SlaveStatus slaveStatus){
        this.from=from;
        this.to=to;
        this.slaveStatus=slaveStatus;
    }
    /**
     * @return the from
     */
    public MonitorServer getFrom()
    {
        return from;
    }
    /**
     * @param from the from to set
     */
    public void setFrom(MonitorServer from)
    {
        this.from = from;
    }
    /**
     * @return the to
     */
    public MonitorServer getTo()
    {
        return to;
    }
    /**
     * @param to the to to set
     */
    public void setTo(MonitorServer to)
    {
        this.to = to;
    }
    /**
     * @return the slaveStatus
     */
    public SlaveStatus getSlaveStatus()
    {
        return slaveStatus;
    }
    /**
     * @param slaveStatus the slaveStatus to set
     */
    public void setSlaveStatus(SlaveStatus slaveStatus)
    {
        this.slaveStatus = slaveStatus;
    }
}
