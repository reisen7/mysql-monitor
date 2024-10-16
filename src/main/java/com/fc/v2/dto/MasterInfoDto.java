package com.fc.v2.dto;

/**   
 * @ClassName:  MasterInfoDto   
 * @Description
 * @author: reisen
 * @date:  2024-10-16 18:20:36
 *     
 */
public class MasterInfoDto
{
    private Object masterStatus;
    private Object slaveHosts;
    /**
     * @return the masterStatus
     */
    public Object getMasterStatus()
    {
        return masterStatus;
    }
    /**
     * @param masterStatus the masterStatus to set
     */
    public void setMasterStatus(Object masterStatus)
    {
        this.masterStatus = masterStatus;
    }
    /**
     * @return the slaveHosts
     */
    public Object getSlaveHosts()
    {
        return slaveHosts;
    }
    /**
     * @param slaveHosts the slaveHosts to set
     */
    public void setSlaveHosts(Object slaveHosts)
    {
        this.slaveHosts = slaveHosts;
    }
    
}
