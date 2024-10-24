package com.fc.v2.model.monitor;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.date.DateUtil;
import java.util.Date;

public class ServerStatusHistory implements Serializable {
    private static final long serialVersionUID = 1L;

	
	@ApiModelProperty(value = "主键")
	private Long id;
	
	@ApiModelProperty(value = "数据库")
	private Long serverId;
	
	@ApiModelProperty(value = "启动时间")
	private String uptime;
	
	@ApiModelProperty(value = "打开的表数量")
	private Integer openTables;
	
	@ApiModelProperty(value = "已连接的线程数")
	private Integer threadsConnected;
	
	@ApiModelProperty(value = "正在运行的线程数")
	private Integer threadsRunning;
	
	@ApiModelProperty(value = "已创建的线程数")
	private Integer threadsCreated;
	
	@ApiModelProperty(value = "已缓存的线程数")
	private Integer threadsCached;
	
	@ApiModelProperty(value = "连接数")
	private Integer connections;
	
	@ApiModelProperty(value = "中止的客户端数量")
	private Integer abortedClients;
	
	@ApiModelProperty(value = "中止的连接数")
	private Integer abortedConnects;
	
	@ApiModelProperty(value = "接受的数据量")
	private Long bytesReceived;
	
	@ApiModelProperty(value = "每秒接受的数据量")
	private String bytesReceivedPersecond;
	
	@ApiModelProperty(value = "发送的数据量")
	private Long bytesSent;
	
	@ApiModelProperty(value = "每秒发送的数据量")
	private String bytesSentPersecond;
	
	@ApiModelProperty(value = "select语句数量")
	private Long comSelect;
	
	@ApiModelProperty(value = "每秒select语句数量")
	private String comSelectPersecond;
	
	@ApiModelProperty(value = "insert语句数量")
	private Long comInsert;
	
	@ApiModelProperty(value = "每秒insert语句数量")
	private String comInsertPersecond;
	
	@ApiModelProperty(value = "update语句数量")
	private Long comUpdate;
	
	@ApiModelProperty(value = "每秒update语句数量")
	private String comUpdatePersecond;
	
	@ApiModelProperty(value = "delete语句数量")
	private Long comDelete;
	
	@ApiModelProperty(value = "每秒delete语句数量")
	private String comDeletePersecond;
	
	@ApiModelProperty(value = "事务提交数量")
	private Long comCommit;
	
	@ApiModelProperty(value = "每秒事务提交数量")
	private String comCommitPersecond;
	
	@ApiModelProperty(value = "事务回滚数量")
	private Long comRollback;
	
	@ApiModelProperty(value = "每秒事务回滚数量")
	private String comRollbackPersecond;
	
	@ApiModelProperty(value = "请求数")
	private Long questions;
	
	@ApiModelProperty(value = "每秒请求数")
	private String questionsPersecond;
	
	@ApiModelProperty(value = "事务数")
	private Long transactions;
	
	@ApiModelProperty(value = "每秒事务数")
	private String transactionsPersecond;
	
	@ApiModelProperty(value = "创建临时表的数量")
	private Long createdTmpTables;
	
	@ApiModelProperty(value = "每秒创建临时表的数量")
	private String createdTmpTablesPersecond;
	
	@ApiModelProperty(value = "创建临时磁盘表数量")
	private Long createdTmpDiskTables;
	
	@ApiModelProperty(value = "每秒创建临时磁盘表数量")
	private String createdTmpDiskTablesPersecond;
	
	@ApiModelProperty(value = "创建临时文件")
	private Long createdTmpFiles;
	
	@ApiModelProperty(value = "每秒创建临时文件")
	private String createdTmpFilesPersecond;
	
	@ApiModelProperty(value = "从文件中读取的次数")
	private Long innodbDataReads;
	
	@ApiModelProperty(value = "每秒从文件中读取的次数")
	private String innodbDataReadsPersecond;
	
	@ApiModelProperty(value = "从文件中写入的次数")
	private Long innodbDataWrites;
	
	@ApiModelProperty(value = "每秒从文件中写入的次数")
	private String innodbDataWritesPersecond;
	
	@ApiModelProperty(value = "进行fsync& #40;& #41;操作的次数")
	private Long innodbDataFsyncs;
	
	@ApiModelProperty(value = "每秒进行fsync& #40;& #41;操作的次数")
	private String innodbDataFsyncsPersecond;
	
	@ApiModelProperty(value = "读取的数据量，单位为KB")
	private Long innodbDataRead;
	
	@ApiModelProperty(value = "每秒读取的数据量，单位为KB")
	private String innodbDataReadPersecond;
	
	@ApiModelProperty(value = "写入的数据量，单位为KB")
	private Long innodbDataWritten;
	
	@ApiModelProperty(value = "每秒写入的数据量，单位为KB")
	private String innodbDataWrittenPersecond;
	
	@ApiModelProperty(value = "缓存池中脏页的数目-单位page")
	private Long innodbBufferPoolPagesDirty;
	
	@ApiModelProperty(value = "每秒缓存池中生成脏页的数目-单位page")
	private String innodbBufferPoolPagesDirtyPersecond;
	
	@ApiModelProperty(value = "缓存池中刷新页请求的数目-单位page")
	private Long innodbBufferPoolPagesFlushed;
	
	@ApiModelProperty(value = "每秒缓存池中刷新页请求的数目-单位page")
	private String innodbBufferPoolPagesFlushedPersecond;
	
	@ApiModelProperty(value = "缓冲池的读命中率: & #40; 1 - Innodb_buffer_pool_reads/Innodb_buffer_pool_read_requests& #41; * 100")
	private String innodbBufferReadHitRatio;
	
	@ApiModelProperty(value = "缓冲池的利用率 :  & #40; 1 - Innodb_buffer_pool_pages_free / Innodb_buffer_pool_pages_total& #41; * 100")
	private String innodbBufferUsage;
	
	@ApiModelProperty(value = "从innodb表插入的行数")
	private Long innodbRowsInserted;
	
	@ApiModelProperty(value = "每秒从innodb表插入的行数")
	private String innodbRowsInsertedPersecond;
	
	@ApiModelProperty(value = "从innodb表更新的行数")
	private Long innodbRowsUpdated;
	
	@ApiModelProperty(value = "每秒从innodb表更新的行数")
	private String innodbRowsUpdatedPersecond;
	
	@ApiModelProperty(value = "从innodb表删除的行数")
	private Long innodbRowsDeleted;
	
	@ApiModelProperty(value = "每秒从innodb表删除的行数")
	private String innodbRowsDeletedPersecond;
	
	@ApiModelProperty(value = "")
	private Long innodbBufferPoolPagesTotal;
	
	@ApiModelProperty(value = "")
	private Long innodbBufferPoolPagesFree;
	
	@ApiModelProperty(value = "")
	private Integer innodbPageSize;
	
	@ApiModelProperty(value = "慢查询数量")
	private Integer slowQueries;
	
	@ApiModelProperty(value = "备库延迟")
	private Long slaveDelay;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@ApiModelProperty(value = "更新时间")
	private Date updateDate;
	
	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id =  id;
	}
	@JsonProperty("serverId")
	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId =  serverId;
	}
	@JsonProperty("uptime")
	public String getUptime() {
		return uptime;
	}

	public void setUptime(String uptime) {
		this.uptime =  uptime;
	}
	@JsonProperty("openTables")
	public Integer getOpenTables() {
		return openTables;
	}

	public void setOpenTables(Integer openTables) {
		this.openTables =  openTables;
	}
	@JsonProperty("threadsConnected")
	public Integer getThreadsConnected() {
		return threadsConnected;
	}

	public void setThreadsConnected(Integer threadsConnected) {
		this.threadsConnected =  threadsConnected;
	}
	@JsonProperty("threadsRunning")
	public Integer getThreadsRunning() {
		return threadsRunning;
	}

	public void setThreadsRunning(Integer threadsRunning) {
		this.threadsRunning =  threadsRunning;
	}
	@JsonProperty("threadsCreated")
	public Integer getThreadsCreated() {
		return threadsCreated;
	}

	public void setThreadsCreated(Integer threadsCreated) {
		this.threadsCreated =  threadsCreated;
	}
	@JsonProperty("threadsCached")
	public Integer getThreadsCached() {
		return threadsCached;
	}

	public void setThreadsCached(Integer threadsCached) {
		this.threadsCached =  threadsCached;
	}
	@JsonProperty("connections")
	public Integer getConnections() {
		return connections;
	}

	public void setConnections(Integer connections) {
		this.connections =  connections;
	}
	@JsonProperty("abortedClients")
	public Integer getAbortedClients() {
		return abortedClients;
	}

	public void setAbortedClients(Integer abortedClients) {
		this.abortedClients =  abortedClients;
	}
	@JsonProperty("abortedConnects")
	public Integer getAbortedConnects() {
		return abortedConnects;
	}

	public void setAbortedConnects(Integer abortedConnects) {
		this.abortedConnects =  abortedConnects;
	}
	@JsonProperty("bytesReceived")
	public Long getBytesReceived() {
		return bytesReceived;
	}

	public void setBytesReceived(Long bytesReceived) {
		this.bytesReceived =  bytesReceived;
	}
	@JsonProperty("bytesReceivedPersecond")
	public String getBytesReceivedPersecond() {
		return bytesReceivedPersecond;
	}

	public void setBytesReceivedPersecond(String bytesReceivedPersecond) {
		this.bytesReceivedPersecond =  bytesReceivedPersecond;
	}
	@JsonProperty("bytesSent")
	public Long getBytesSent() {
		return bytesSent;
	}

	public void setBytesSent(Long bytesSent) {
		this.bytesSent =  bytesSent;
	}
	@JsonProperty("bytesSentPersecond")
	public String getBytesSentPersecond() {
		return bytesSentPersecond;
	}

	public void setBytesSentPersecond(String bytesSentPersecond) {
		this.bytesSentPersecond =  bytesSentPersecond;
	}
	@JsonProperty("comSelect")
	public Long getComSelect() {
		return comSelect;
	}

	public void setComSelect(Long comSelect) {
		this.comSelect =  comSelect;
	}
	@JsonProperty("comSelectPersecond")
	public String getComSelectPersecond() {
		return comSelectPersecond;
	}

	public void setComSelectPersecond(String comSelectPersecond) {
		this.comSelectPersecond =  comSelectPersecond;
	}
	@JsonProperty("comInsert")
	public Long getComInsert() {
		return comInsert;
	}

	public void setComInsert(Long comInsert) {
		this.comInsert =  comInsert;
	}
	@JsonProperty("comInsertPersecond")
	public String getComInsertPersecond() {
		return comInsertPersecond;
	}

	public void setComInsertPersecond(String comInsertPersecond) {
		this.comInsertPersecond =  comInsertPersecond;
	}
	@JsonProperty("comUpdate")
	public Long getComUpdate() {
		return comUpdate;
	}

	public void setComUpdate(Long comUpdate) {
		this.comUpdate =  comUpdate;
	}
	@JsonProperty("comUpdatePersecond")
	public String getComUpdatePersecond() {
		return comUpdatePersecond;
	}

	public void setComUpdatePersecond(String comUpdatePersecond) {
		this.comUpdatePersecond =  comUpdatePersecond;
	}
	@JsonProperty("comDelete")
	public Long getComDelete() {
		return comDelete;
	}

	public void setComDelete(Long comDelete) {
		this.comDelete =  comDelete;
	}
	@JsonProperty("comDeletePersecond")
	public String getComDeletePersecond() {
		return comDeletePersecond;
	}

	public void setComDeletePersecond(String comDeletePersecond) {
		this.comDeletePersecond =  comDeletePersecond;
	}
	@JsonProperty("comCommit")
	public Long getComCommit() {
		return comCommit;
	}

	public void setComCommit(Long comCommit) {
		this.comCommit =  comCommit;
	}
	@JsonProperty("comCommitPersecond")
	public String getComCommitPersecond() {
		return comCommitPersecond;
	}

	public void setComCommitPersecond(String comCommitPersecond) {
		this.comCommitPersecond =  comCommitPersecond;
	}
	@JsonProperty("comRollback")
	public Long getComRollback() {
		return comRollback;
	}

	public void setComRollback(Long comRollback) {
		this.comRollback =  comRollback;
	}
	@JsonProperty("comRollbackPersecond")
	public String getComRollbackPersecond() {
		return comRollbackPersecond;
	}

	public void setComRollbackPersecond(String comRollbackPersecond) {
		this.comRollbackPersecond =  comRollbackPersecond;
	}
	@JsonProperty("questions")
	public Long getQuestions() {
		return questions;
	}

	public void setQuestions(Long questions) {
		this.questions =  questions;
	}
	@JsonProperty("questionsPersecond")
	public String getQuestionsPersecond() {
		return questionsPersecond;
	}

	public void setQuestionsPersecond(String questionsPersecond) {
		this.questionsPersecond =  questionsPersecond;
	}
	@JsonProperty("transactions")
	public Long getTransactions() {
		return transactions;
	}

	public void setTransactions(Long transactions) {
		this.transactions =  transactions;
	}
	@JsonProperty("transactionsPersecond")
	public String getTransactionsPersecond() {
		return transactionsPersecond;
	}

	public void setTransactionsPersecond(String transactionsPersecond) {
		this.transactionsPersecond =  transactionsPersecond;
	}
	@JsonProperty("createdTmpTables")
	public Long getCreatedTmpTables() {
		return createdTmpTables;
	}

	public void setCreatedTmpTables(Long createdTmpTables) {
		this.createdTmpTables =  createdTmpTables;
	}
	@JsonProperty("createdTmpTablesPersecond")
	public String getCreatedTmpTablesPersecond() {
		return createdTmpTablesPersecond;
	}

	public void setCreatedTmpTablesPersecond(String createdTmpTablesPersecond) {
		this.createdTmpTablesPersecond =  createdTmpTablesPersecond;
	}
	@JsonProperty("createdTmpDiskTables")
	public Long getCreatedTmpDiskTables() {
		return createdTmpDiskTables;
	}

	public void setCreatedTmpDiskTables(Long createdTmpDiskTables) {
		this.createdTmpDiskTables =  createdTmpDiskTables;
	}
	@JsonProperty("createdTmpDiskTablesPersecond")
	public String getCreatedTmpDiskTablesPersecond() {
		return createdTmpDiskTablesPersecond;
	}

	public void setCreatedTmpDiskTablesPersecond(String createdTmpDiskTablesPersecond) {
		this.createdTmpDiskTablesPersecond =  createdTmpDiskTablesPersecond;
	}
	@JsonProperty("createdTmpFiles")
	public Long getCreatedTmpFiles() {
		return createdTmpFiles;
	}

	public void setCreatedTmpFiles(Long createdTmpFiles) {
		this.createdTmpFiles =  createdTmpFiles;
	}
	@JsonProperty("createdTmpFilesPersecond")
	public String getCreatedTmpFilesPersecond() {
		return createdTmpFilesPersecond;
	}

	public void setCreatedTmpFilesPersecond(String createdTmpFilesPersecond) {
		this.createdTmpFilesPersecond =  createdTmpFilesPersecond;
	}
	@JsonProperty("innodbDataReads")
	public Long getInnodbDataReads() {
		return innodbDataReads;
	}

	public void setInnodbDataReads(Long innodbDataReads) {
		this.innodbDataReads =  innodbDataReads;
	}
	@JsonProperty("innodbDataReadsPersecond")
	public String getInnodbDataReadsPersecond() {
		return innodbDataReadsPersecond;
	}

	public void setInnodbDataReadsPersecond(String innodbDataReadsPersecond) {
		this.innodbDataReadsPersecond =  innodbDataReadsPersecond;
	}
	@JsonProperty("innodbDataWrites")
	public Long getInnodbDataWrites() {
		return innodbDataWrites;
	}

	public void setInnodbDataWrites(Long innodbDataWrites) {
		this.innodbDataWrites =  innodbDataWrites;
	}
	@JsonProperty("innodbDataWritesPersecond")
	public String getInnodbDataWritesPersecond() {
		return innodbDataWritesPersecond;
	}

	public void setInnodbDataWritesPersecond(String innodbDataWritesPersecond) {
		this.innodbDataWritesPersecond =  innodbDataWritesPersecond;
	}
	@JsonProperty("innodbDataFsyncs")
	public Long getInnodbDataFsyncs() {
		return innodbDataFsyncs;
	}

	public void setInnodbDataFsyncs(Long innodbDataFsyncs) {
		this.innodbDataFsyncs =  innodbDataFsyncs;
	}
	@JsonProperty("innodbDataFsyncsPersecond")
	public String getInnodbDataFsyncsPersecond() {
		return innodbDataFsyncsPersecond;
	}

	public void setInnodbDataFsyncsPersecond(String innodbDataFsyncsPersecond) {
		this.innodbDataFsyncsPersecond =  innodbDataFsyncsPersecond;
	}
	@JsonProperty("innodbDataRead")
	public Long getInnodbDataRead() {
		return innodbDataRead;
	}

	public void setInnodbDataRead(Long innodbDataRead) {
		this.innodbDataRead =  innodbDataRead;
	}
	@JsonProperty("innodbDataReadPersecond")
	public String getInnodbDataReadPersecond() {
		return innodbDataReadPersecond;
	}

	public void setInnodbDataReadPersecond(String innodbDataReadPersecond) {
		this.innodbDataReadPersecond =  innodbDataReadPersecond;
	}
	@JsonProperty("innodbDataWritten")
	public Long getInnodbDataWritten() {
		return innodbDataWritten;
	}

	public void setInnodbDataWritten(Long innodbDataWritten) {
		this.innodbDataWritten =  innodbDataWritten;
	}
	@JsonProperty("innodbDataWrittenPersecond")
	public String getInnodbDataWrittenPersecond() {
		return innodbDataWrittenPersecond;
	}

	public void setInnodbDataWrittenPersecond(String innodbDataWrittenPersecond) {
		this.innodbDataWrittenPersecond =  innodbDataWrittenPersecond;
	}
	@JsonProperty("innodbBufferPoolPagesDirty")
	public Long getInnodbBufferPoolPagesDirty() {
		return innodbBufferPoolPagesDirty;
	}

	public void setInnodbBufferPoolPagesDirty(Long innodbBufferPoolPagesDirty) {
		this.innodbBufferPoolPagesDirty =  innodbBufferPoolPagesDirty;
	}
	@JsonProperty("innodbBufferPoolPagesDirtyPersecond")
	public String getInnodbBufferPoolPagesDirtyPersecond() {
		return innodbBufferPoolPagesDirtyPersecond;
	}

	public void setInnodbBufferPoolPagesDirtyPersecond(String innodbBufferPoolPagesDirtyPersecond) {
		this.innodbBufferPoolPagesDirtyPersecond =  innodbBufferPoolPagesDirtyPersecond;
	}
	@JsonProperty("innodbBufferPoolPagesFlushed")
	public Long getInnodbBufferPoolPagesFlushed() {
		return innodbBufferPoolPagesFlushed;
	}

	public void setInnodbBufferPoolPagesFlushed(Long innodbBufferPoolPagesFlushed) {
		this.innodbBufferPoolPagesFlushed =  innodbBufferPoolPagesFlushed;
	}
	@JsonProperty("innodbBufferPoolPagesFlushedPersecond")
	public String getInnodbBufferPoolPagesFlushedPersecond() {
		return innodbBufferPoolPagesFlushedPersecond;
	}

	public void setInnodbBufferPoolPagesFlushedPersecond(String innodbBufferPoolPagesFlushedPersecond) {
		this.innodbBufferPoolPagesFlushedPersecond =  innodbBufferPoolPagesFlushedPersecond;
	}
	@JsonProperty("innodbBufferReadHitRatio")
	public String getInnodbBufferReadHitRatio() {
		return innodbBufferReadHitRatio;
	}

	public void setInnodbBufferReadHitRatio(String innodbBufferReadHitRatio) {
		this.innodbBufferReadHitRatio =  innodbBufferReadHitRatio;
	}
	@JsonProperty("innodbBufferUsage")
	public String getInnodbBufferUsage() {
		return innodbBufferUsage;
	}

	public void setInnodbBufferUsage(String innodbBufferUsage) {
		this.innodbBufferUsage =  innodbBufferUsage;
	}
	@JsonProperty("innodbRowsInserted")
	public Long getInnodbRowsInserted() {
		return innodbRowsInserted;
	}

	public void setInnodbRowsInserted(Long innodbRowsInserted) {
		this.innodbRowsInserted =  innodbRowsInserted;
	}
	@JsonProperty("innodbRowsInsertedPersecond")
	public String getInnodbRowsInsertedPersecond() {
		return innodbRowsInsertedPersecond;
	}

	public void setInnodbRowsInsertedPersecond(String innodbRowsInsertedPersecond) {
		this.innodbRowsInsertedPersecond =  innodbRowsInsertedPersecond;
	}
	@JsonProperty("innodbRowsUpdated")
	public Long getInnodbRowsUpdated() {
		return innodbRowsUpdated;
	}

	public void setInnodbRowsUpdated(Long innodbRowsUpdated) {
		this.innodbRowsUpdated =  innodbRowsUpdated;
	}
	@JsonProperty("innodbRowsUpdatedPersecond")
	public String getInnodbRowsUpdatedPersecond() {
		return innodbRowsUpdatedPersecond;
	}

	public void setInnodbRowsUpdatedPersecond(String innodbRowsUpdatedPersecond) {
		this.innodbRowsUpdatedPersecond =  innodbRowsUpdatedPersecond;
	}
	@JsonProperty("innodbRowsDeleted")
	public Long getInnodbRowsDeleted() {
		return innodbRowsDeleted;
	}

	public void setInnodbRowsDeleted(Long innodbRowsDeleted) {
		this.innodbRowsDeleted =  innodbRowsDeleted;
	}
	@JsonProperty("innodbRowsDeletedPersecond")
	public String getInnodbRowsDeletedPersecond() {
		return innodbRowsDeletedPersecond;
	}

	public void setInnodbRowsDeletedPersecond(String innodbRowsDeletedPersecond) {
		this.innodbRowsDeletedPersecond =  innodbRowsDeletedPersecond;
	}
	@JsonProperty("innodbBufferPoolPagesTotal")
	public Long getInnodbBufferPoolPagesTotal() {
		return innodbBufferPoolPagesTotal;
	}

	public void setInnodbBufferPoolPagesTotal(Long innodbBufferPoolPagesTotal) {
		this.innodbBufferPoolPagesTotal =  innodbBufferPoolPagesTotal;
	}
	@JsonProperty("innodbBufferPoolPagesFree")
	public Long getInnodbBufferPoolPagesFree() {
		return innodbBufferPoolPagesFree;
	}

	public void setInnodbBufferPoolPagesFree(Long innodbBufferPoolPagesFree) {
		this.innodbBufferPoolPagesFree =  innodbBufferPoolPagesFree;
	}
	@JsonProperty("innodbPageSize")
	public Integer getInnodbPageSize() {
		return innodbPageSize;
	}

	public void setInnodbPageSize(Integer innodbPageSize) {
		this.innodbPageSize =  innodbPageSize;
	}
	@JsonProperty("slowQueries")
	public Integer getSlowQueries() {
		return slowQueries;
	}

	public void setSlowQueries(Integer slowQueries) {
		this.slowQueries =  slowQueries;
	}
	@JsonProperty("slaveDelay")
	public Long getSlaveDelay() {
		return slaveDelay;
	}

	public void setSlaveDelay(Long slaveDelay) {
		this.slaveDelay =  slaveDelay;
	}
	@JsonProperty("createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime =  createTime;
	}
	@JsonProperty("updateDate")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate =  updateDate;
	}


	public ServerStatusHistory(Long id,Long serverId,String uptime,Integer openTables,Integer threadsConnected,Integer threadsRunning,Integer threadsCreated,Integer threadsCached,Integer connections,Integer abortedClients,Integer abortedConnects,Long bytesReceived,String bytesReceivedPersecond,Long bytesSent,String bytesSentPersecond,Long comSelect,String comSelectPersecond,Long comInsert,String comInsertPersecond,Long comUpdate,String comUpdatePersecond,Long comDelete,String comDeletePersecond,Long comCommit,String comCommitPersecond,Long comRollback,String comRollbackPersecond,Long questions,String questionsPersecond,Long transactions,String transactionsPersecond,Long createdTmpTables,String createdTmpTablesPersecond,Long createdTmpDiskTables,String createdTmpDiskTablesPersecond,Long createdTmpFiles,String createdTmpFilesPersecond,Long innodbDataReads,String innodbDataReadsPersecond,Long innodbDataWrites,String innodbDataWritesPersecond,Long innodbDataFsyncs,String innodbDataFsyncsPersecond,Long innodbDataRead,String innodbDataReadPersecond,Long innodbDataWritten,String innodbDataWrittenPersecond,Long innodbBufferPoolPagesDirty,String innodbBufferPoolPagesDirtyPersecond,Long innodbBufferPoolPagesFlushed,String innodbBufferPoolPagesFlushedPersecond,String innodbBufferReadHitRatio,String innodbBufferUsage,Long innodbRowsInserted,String innodbRowsInsertedPersecond,Long innodbRowsUpdated,String innodbRowsUpdatedPersecond,Long innodbRowsDeleted,String innodbRowsDeletedPersecond,Long innodbBufferPoolPagesTotal,Long innodbBufferPoolPagesFree,Integer innodbPageSize,Integer slowQueries,Long slaveDelay,Date createTime,Date updateDate) {
		
		this.id = id;
		
		this.serverId = serverId;
		
		this.uptime = uptime;
		
		this.openTables = openTables;
		
		this.threadsConnected = threadsConnected;
		
		this.threadsRunning = threadsRunning;
		
		this.threadsCreated = threadsCreated;
		
		this.threadsCached = threadsCached;
		
		this.connections = connections;
		
		this.abortedClients = abortedClients;
		
		this.abortedConnects = abortedConnects;
		
		this.bytesReceived = bytesReceived;
		
		this.bytesReceivedPersecond = bytesReceivedPersecond;
		
		this.bytesSent = bytesSent;
		
		this.bytesSentPersecond = bytesSentPersecond;
		
		this.comSelect = comSelect;
		
		this.comSelectPersecond = comSelectPersecond;
		
		this.comInsert = comInsert;
		
		this.comInsertPersecond = comInsertPersecond;
		
		this.comUpdate = comUpdate;
		
		this.comUpdatePersecond = comUpdatePersecond;
		
		this.comDelete = comDelete;
		
		this.comDeletePersecond = comDeletePersecond;
		
		this.comCommit = comCommit;
		
		this.comCommitPersecond = comCommitPersecond;
		
		this.comRollback = comRollback;
		
		this.comRollbackPersecond = comRollbackPersecond;
		
		this.questions = questions;
		
		this.questionsPersecond = questionsPersecond;
		
		this.transactions = transactions;
		
		this.transactionsPersecond = transactionsPersecond;
		
		this.createdTmpTables = createdTmpTables;
		
		this.createdTmpTablesPersecond = createdTmpTablesPersecond;
		
		this.createdTmpDiskTables = createdTmpDiskTables;
		
		this.createdTmpDiskTablesPersecond = createdTmpDiskTablesPersecond;
		
		this.createdTmpFiles = createdTmpFiles;
		
		this.createdTmpFilesPersecond = createdTmpFilesPersecond;
		
		this.innodbDataReads = innodbDataReads;
		
		this.innodbDataReadsPersecond = innodbDataReadsPersecond;
		
		this.innodbDataWrites = innodbDataWrites;
		
		this.innodbDataWritesPersecond = innodbDataWritesPersecond;
		
		this.innodbDataFsyncs = innodbDataFsyncs;
		
		this.innodbDataFsyncsPersecond = innodbDataFsyncsPersecond;
		
		this.innodbDataRead = innodbDataRead;
		
		this.innodbDataReadPersecond = innodbDataReadPersecond;
		
		this.innodbDataWritten = innodbDataWritten;
		
		this.innodbDataWrittenPersecond = innodbDataWrittenPersecond;
		
		this.innodbBufferPoolPagesDirty = innodbBufferPoolPagesDirty;
		
		this.innodbBufferPoolPagesDirtyPersecond = innodbBufferPoolPagesDirtyPersecond;
		
		this.innodbBufferPoolPagesFlushed = innodbBufferPoolPagesFlushed;
		
		this.innodbBufferPoolPagesFlushedPersecond = innodbBufferPoolPagesFlushedPersecond;
		
		this.innodbBufferReadHitRatio = innodbBufferReadHitRatio;
		
		this.innodbBufferUsage = innodbBufferUsage;
		
		this.innodbRowsInserted = innodbRowsInserted;
		
		this.innodbRowsInsertedPersecond = innodbRowsInsertedPersecond;
		
		this.innodbRowsUpdated = innodbRowsUpdated;
		
		this.innodbRowsUpdatedPersecond = innodbRowsUpdatedPersecond;
		
		this.innodbRowsDeleted = innodbRowsDeleted;
		
		this.innodbRowsDeletedPersecond = innodbRowsDeletedPersecond;
		
		this.innodbBufferPoolPagesTotal = innodbBufferPoolPagesTotal;
		
		this.innodbBufferPoolPagesFree = innodbBufferPoolPagesFree;
		
		this.innodbPageSize = innodbPageSize;
		
		this.slowQueries = slowQueries;
		
		this.slaveDelay = slaveDelay;
		
		this.createTime = createTime;
		
		this.updateDate = updateDate;
		
	}

	public ServerStatusHistory() {
	    super();
	}

	public String dateToStringConvert(Date date) {
		if(date!=null) {
			return DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}
	

}