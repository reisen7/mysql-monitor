package com.fc.v2.controller.mysql;

import com.fc.v2.service.monitor.MonitorServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/mysql")
public class MysqlController
{
    
    @Autowired
    private MonitorServerService monitorServerService;
    
    /**
     * 获取二进制文件列表
     * @Title: getBinaryLogs   
     * @param serverId
     * @return        
     * @throws
     */
    @RequestMapping(value = "/{serverId}/binaryLogs", method = {RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public Object getBinaryLogs(@PathVariable Long serverId)
    {
        return monitorServerService.getBinaryLogs(serverId);
    }
    
    /**
     * 显示二进制文件内容
     * @Title: getBinaryLogData   
     * @param serverId
     * @param logName
     * @param begin
     * @param end
     * @return        
     * @throws
     */
    @RequestMapping(value = "/{serverId}/binaryLogs/{logName}/{begin}/{end}", method = {RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public Object getBinaryLogData(@PathVariable Long serverId, @PathVariable String logName, @PathVariable Long begin,
        @PathVariable Long end)
    {
        return monitorServerService.getBinaryLogData(serverId, logName, begin, end);
    }
    
    /**
     * 根据语句显示二进制文件内容
     * @Title: getShowBinlogEvents   
     * @param serverId
     * @param request
     * @return        
     * @throws
     */
    @RequestMapping(value = "/{serverId}/showBinlogEvents", method = {RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public Object getShowBinlogEvents(@PathVariable Long serverId, HttpServletRequest request)
    {
        String showBinlogEvents = request.getParameter("showBinlogEvents");
        return monitorServerService.getShowBinlogEvents(serverId, showBinlogEvents);
    }
    
    /**
     * 获取全局变量
     * @Title: getGlobalVariables   
     * @param serverId
     * @param request
     * @return        
     * @throws
     */
    @RequestMapping(value = "/{serverId}/globalVariables", method = {RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public Object getGlobalVariables(@PathVariable Long serverId, HttpServletRequest request)
    {
        String filter = request.getParameter("filter");
        return monitorServerService.getGlobalVariables(serverId, filter);
    }
    
    /**
     * 获取会话变量
     * @Title: getSessionVariables   
     * @param serverId
     * @return        
     * @throws
     */
    @RequestMapping(value = "/{serverId}/sessionVariables", method = {RequestMethod.GET})
    public Object getSessionVariables(@PathVariable Long serverId)
    {
        return monitorServerService.getSessionVariables(serverId);
    }
    
    /**
     * 设置全局变量值
     * @Title: setGlobalVariables   
     * @param serverId
     * @param name
     * @param value
     * @return        
     * @throws
     */
    @RequestMapping(value = "/{serverId}/globalVariables/{name}/{value}/")
    @CrossOrigin(origins = "*")
    public Object setGlobalVariables(@PathVariable Long serverId, @PathVariable String name, @PathVariable String value)
    {
        return monitorServerService.setGlobalVariables(serverId, name, value);
    }
    
    /**
     * 设置会话变量值
     * @Title: setSessionVariables   
     * @param serverId
     * @param name
     * @param value
     * @return        
     * @throws
     */
    @RequestMapping(value = "/{serverId}/sessionVariables/{name}/{value}/", method = {RequestMethod.POST})
    public Object setSessionVariables(@PathVariable Long serverId, @PathVariable String name,
        @PathVariable String value)
    {
        return monitorServerService.setSessionVariables(serverId, name, value);
    }
    
    /**
     * MySQL登录身份验证
     * @Title: mysqlVerify   
     * @param host
     * @param port
     * @param username
     * @param password
     * @return        
     * @throws
     */
    @RequestMapping(value = "/verify/{host}/{port}/{username}/{password}", method = {RequestMethod.POST,
        RequestMethod.GET})
    public Object mysqlVerify(@PathVariable String host, @PathVariable Integer port, @PathVariable String username,
        @PathVariable String password)
    {
        // 需要提供数据的版本
        String version = "";

        return monitorServerService.mysqlVerify(host, port, username, password, version);
    }
    
    /**
     * 获取数据库列表
     * @Title: getDatabases   
     * @param serverId
     * @return        
     * @throws
     */
    @RequestMapping(value = "/{serverId}/databases", method = {RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public Object getDatabases(@PathVariable Long serverId)
    {
        return monitorServerService.getDatabases(serverId);
    }
    
    /**
     * 获取数据库列表
     * @Title: getDatabases   
     * @param serverId
     * @param schema
     * @return        
     * @throws
     */
    @RequestMapping(value = "/{serverId}/{schema}/tables", method = {RequestMethod.GET})
    public Object getDatabases(@PathVariable Long serverId, @PathVariable String schema)
    {
        return monitorServerService.getTables(serverId, schema);
    }
    
    /**
     * 根据Schema获取对应用户权限信息
     * @Title: getUserPriv   
     * @param serverId
     * @param schema
     * @return        
     * @throws
     */
    @RequestMapping(value = "/{serverId}/{schema}/priv", method = {RequestMethod.GET})
    public Object getUserPriv(@PathVariable Long serverId, @PathVariable String schema)
    {
        return monitorServerService.getUserPriv(serverId, schema);
    }
    
    /**
     * 获取状态
     * @Title: getStatus   
     * @param serverId
     * @param request
     * @return        
     * @throws
     */
    @RequestMapping(value = "/{serverId}/status", method = {RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public Object getStatus(@PathVariable Long serverId, HttpServletRequest request)
    {
        String filter = request.getParameter("filter");
        return monitorServerService.getStatus(serverId, filter);
    }
    
    /**
     * 获取节点概要信息
     * @Title: getOverview   
     * @param serverId
     * @return        
     * @throws
     */
    @RequestMapping(value = "/get/overview/{serverId}", method = {RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public Object getOverview(@PathVariable Long serverId)
    {
        return monitorServerService.getOverview(serverId);
    }
    
    /**
     * 获取主库相关信息
     * @Title: getMasterInfo   
     * @param serverId
     * @return        
     * @throws
     */
    @RequestMapping(value = "/{serverId}/master-info", method = {RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public Object getMasterInfo(@PathVariable Long serverId)
    {
        return monitorServerService.getMasterInfo(serverId);
    }
    
    /**
     * 获取从库相关信息
     * @Title: getSlaveInfo   
     * @param serverId
     * @return        
     * @throws
     */
    @RequestMapping(value = "/{serverId}/slave-info", method = {RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public Object getSlaveInfo(@PathVariable Long serverId)
    {
        return monitorServerService.getSlaveInfo(serverId);
    }
    
    /**
     * 获取节点列表
     * @Title: getNodeList   
     * @param request
     * @return        
     * @throws
     */
    @RequestMapping(value = "/node/list", method = {RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public Object getNodeList(HttpServletRequest request)
    {
        int draw = Integer.parseInt(request.getParameter("draw"));
        int startIndex = Integer.parseInt(request.getParameter("start"));
        int pageSize = Integer.parseInt(request.getParameter("length"));
        return monitorServerService.getNodeList(draw, startIndex, pageSize);
    }
    
    /**
     * 保存节点信息
     * @Title: saveNode   
     * @param request
     * @return        
     * @throws
     */
    @RequestMapping(value = "/node/save", method = {RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public Object saveNode(HttpServletRequest request)
    {
        Long id = Long.parseLong(request.getParameter("id"));
        String host = request.getParameter("host");
        Integer port = Integer.parseInt(request.getParameter("port"));
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String tags = request.getParameter("tags");
        Long clusterId = Long.parseLong(request.getParameter("clusterId"));
        String version = request.getParameter("version");
        return monitorServerService.saveNode(username, id, host, port, password, tags, clusterId, version);
    }
    
    /**
     * 删除节点信息
     * @Title: deleteNode   
     * @param request
     * @return        
     * @throws
     */
    @RequestMapping(value = "/node/delete", method = {RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public Object deleteNode(HttpServletRequest request)
    {
        Long id = Long.parseLong(request.getParameter("id"));
        return monitorServerService.deleteNode(id);
    }
    
    /**
     * 获取节点信息
     * @Title: getNodeInfo   
     * @param request
     * @return        
     * @throws
     */
    @RequestMapping(value = "/node/info", method = {RequestMethod.GET})
    @CrossOrigin(origins = "*")
    public Object getNodeInfo(HttpServletRequest request)
    {
        Long id = Long.parseLong(request.getParameter("id"));
        return monitorServerService.getNodeInfo(id);
    }
}
