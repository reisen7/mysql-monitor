package com.fc.v2.model.monitor;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.date.DateUtil;
import java.util.Date;

public class MonitorServer implements Serializable {
    private static final long serialVersionUID = 1L;

	
	@ApiModelProperty(value = "主键")
	private Long id;
	
	@ApiModelProperty(value = "主机")
	private String host;
	
	@ApiModelProperty(value = "端口")
	private Integer port;
	
	@ApiModelProperty(value = "用户名")
	private String username;
	
	@ApiModelProperty(value = "密码")
	private String password;
	
	@ApiModelProperty(value = "版本")
	private String version;
	
	@ApiModelProperty(value = "标签")
	private String tags;
	
	@ApiModelProperty(value = "集群")
	private Long clusterId;


	/**
	 * 非数据库字段
	 */
	private MonitorCluster monitorCluster;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;
	
	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id =  id;
	}
	@JsonProperty("host")
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host =  host;
	}
	@JsonProperty("port")
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port =  port;
	}
	@JsonProperty("username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username =  username;
	}
	@JsonProperty("password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password =  password;
	}
	@JsonProperty("version")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version =  version;
	}
	@JsonProperty("tags")
	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags =  tags;
	}
	@JsonProperty("clusterId")
	public Long getClusterId() {
		return clusterId;
	}

	public void setClusterId(Long clusterId) {
		this.clusterId =  clusterId;
	}
	@JsonProperty("createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime =  createTime;
	}
	@JsonProperty("updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime =  updateTime;
	}

	public MonitorCluster getMonitorCluster() {
		return monitorCluster;
	}

	public void setMonitorCluster(MonitorCluster monitorCluster) {
		this.monitorCluster = monitorCluster;
	}

	public MonitorServer(Long id, String host, Integer port, String username, String password, String version, String tags, Long clusterId, Date createTime, Date updateTime) {
		
		this.id = id;
		
		this.host = host;
		
		this.port = port;
		
		this.username = username;
		
		this.password = password;
		
		this.version = version;
		
		this.tags = tags;
		
		this.clusterId = clusterId;
		
		this.createTime = createTime;
		
		this.updateTime = updateTime;
		
	}

	public MonitorServer() {
	    super();
	}

	public String dateToStringConvert(Date date) {
		if(date!=null) {
			return DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}
	

}