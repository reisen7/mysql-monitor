package com.fc.v2.service.mysql;

/**
 * @ClassName: SuggestionService
 * @Description
 * @author: reisen
 * @date: 2024-10-16 18:20:36
 * 
 */
public interface SuggestionService {
    /**
     * 根据指定MySQL节点状态，获取优化建议
     * @Title: getSuggestion   
     * @param serverId
     * @return        
     * @throws
     */
    Object getSuggestion(Long serverId);
}
