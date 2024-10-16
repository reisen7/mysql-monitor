package com.fc.v2.dto;

import com.fc.v2.model.mysql.MysqlServer;

/**   
 * @ClassName:  Relation   
 * @Description:MySQL节点的复制关系
 * @author: reisen
 * @date:   2024-10-16 18:20:36
 */
public class Relation
{
    private MysqlServer from;
    private MysqlServer to;
    private SlaveStatus slaveStatus;
    
    public Relation(MysqlServer from, MysqlServer to, SlaveStatus slaveStatus){
        this.from=from;
        this.to=to;
        this.slaveStatus=slaveStatus;
    }
    /**
     * @return the from
     */
    public MysqlServer getFrom()
    {
        return from;
    }
    /**
     * @param from the from to set
     */
    public void setFrom(MysqlServer from)
    {
        this.from = from;
    }
    /**
     * @return the to
     */
    public MysqlServer getTo()
    {
        return to;
    }
    /**
     * @param to the to to set
     */
    public void setTo(MysqlServer to)
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
