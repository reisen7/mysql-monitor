package com.fc.v2.model.auto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.date.DateUtil;
import java.util.Date;

public class MonConnect implements Serializable {
    private static final long serialVersionUID = 1L;

	
	@ApiModelProperty(value = "主键")
	private Integer id;
	
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
	private String tag;
	
	@ApiModelProperty(value = "集群")
	private Integer clusterId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;
	
	@JsonProperty("id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
	@JsonProperty("tag")
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag =  tag;
	}
	@JsonProperty("clusterId")
	public Integer getClusterId() {
		return clusterId;
	}

	public void setClusterId(Integer clusterId) {
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


	public MonConnect(Integer id,String host,Integer port,String username,String password,String version,String tag,Integer clusterId,Date createTime,Date updateTime) {
		
		this.id = id;
		
		this.host = host;
		
		this.port = port;
		
		this.username = username;
		
		this.password = password;
		
		this.version = version;
		
		this.tag = tag;
		
		this.clusterId = clusterId;
		
		this.createTime = createTime;
		
		this.updateTime = updateTime;
		
	}

	public MonConnect() {
	    super();
	}

	public String dateToStringConvert(Date date) {
		if(date!=null) {
			return DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}
	

}