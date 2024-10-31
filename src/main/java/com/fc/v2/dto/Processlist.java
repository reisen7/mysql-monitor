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
    @Override
    public String toString() {
        return "Processlist [id=" + id + ", user=" + user + ", host=" + host + ", db=" + db + ", command=" + command
                + ", time=" + time + ", state=" + state + ", info=" + info + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        result = prime * result + ((host == null) ? 0 : host.hashCode());
        result = prime * result + ((db == null) ? 0 : db.hashCode());
        result = prime * result + ((command == null) ? 0 : command.hashCode());
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        result = prime * result + ((info == null) ? 0 : info.hashCode());
        return result;
    }
    /***
     * 判断是否为空
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null){
            if (id==null&&user==null&&host==null&&db==null&&command==null&&time==null&&state==null&&info==null) {
                return true;
            }else if(id==""&&user==""&&host==""&&db==""&&command==""&&time==""&&state==""&&info==""){
                return true;
            }
            return false;
        }
            
        if (getClass() != obj.getClass())
            return false;
        Processlist other = (Processlist) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        if (host == null) {
            if (other.host != null)
                return false;
        } else if (!host.equals(other.host))
            return false;
        if (db == null) {
            if (other.db != null)
                return false;
        } else if (!db.equals(other.db))
            return false;
        if (command == null) {
            if (other.command != null)
                return false;
        } else if (!command.equals(other.command))
            return false;
        if (time == null) {
            if (other.time != null)
                return false;
        } else if (!time.equals(other.time))
            return false;
        if (state == null) {
            if (other.state != null)
                return false;
        } else if (!state.equals(other.state))
            return false;
        if (info == null) {
            if (other.info != null)
                return false;
        } else if (!info.equals(other.info))
            return false;
        return true;
    }

    
    
}
