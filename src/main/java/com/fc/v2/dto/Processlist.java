package com.fc.v2.dto;

/**   
 * @ClassName:  Processlist   
 * @Description
 * @author: reisen
 * @date:   2024-10-16 18:20:36
 *     
 */
public class Processlist
{
    private String id;
    private String user;
    private String host;
    private String db;
    private String command;
    private String time;
    private String state;
    private String info;
    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }
    /**
     * @return the user
     */
    public String getUser()
    {
        return user;
    }
    /**
     * @param user the user to set
     */
    public void setUser(String user)
    {
        this.user = user;
    }
    /**
     * @return the host
     */
    public String getHost()
    {
        return host;
    }
    /**
     * @param host the host to set
     */
    public void setHost(String host)
    {
        this.host = host;
    }
    /**
     * @return the db
     */
    public String getDb()
    {
        return db;
    }
    /**
     * @param db the db to set
     */
    public void setDb(String db)
    {
        this.db = db;
    }
    /**
     * @return the command
     */
    public String getCommand()
    {
        return command;
    }
    /**
     * @param command the command to set
     */
    public void setCommand(String command)
    {
        this.command = command;
    }
    /**
     * @return the time
     */
    public String getTime()
    {
        return time;
    }
    /**
     * @param time the time to set
     */
    public void setTime(String time)
    {
        this.time = time;
    }
    /**
     * @return the state
     */
    public String getState()
    {
        return state;
    }
    /**
     * @param state the state to set
     */
    public void setState(String state)
    {
        this.state = state;
    }
    /**
     * @return the info
     */
    public String getInfo()
    {
        return info;
    }
    /**
     * @param info the info to set
     */
    public void setInfo(String info)
    {
        this.info = info;
    }
    
}
