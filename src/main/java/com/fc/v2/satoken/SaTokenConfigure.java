package com.fc.v2.satoken;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.hutool.core.util.ArrayUtil;
import com.fc.v2.common.conf.V2Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.alibaba.fastjson.JSON;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.satoken.dialect.SaTokenDialect;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import java.util.List;

/**
 * Sa-Token 配置 
 * @author kong
 *
 */
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {

	@Autowired
	private V2Config v2Config;
	//开放权限的url
	private  final String[] excludePaths = {
			"/favicon.ico", "/static/**",
			// 对所有用户认证
			"/admin/login",
			//手机登录
			"/admin/API/login",
			// 放验证码
			"/captcha/**",
			// 释放 druid 监控画面
			"/druid/**",
			// 释放websocket请求
			"/websocket",
			// 前端
			"/", "/index",
			// 任务调度暂时放开
			"/quartz/**",
			"/ServerStatusHistoryController/**",
			// 开放APicontroller
			"/ApiController/**",
			"/oss/**", "/druid/**"};

	// Sa-Token 参数配置，参考文档：https://sa-token.cc
	// 此配置会与 application.yml 中的配置合并 （代码配置优先）
	@Autowired
	public void configSaToken(SaTokenConfig config) {
		config.setTokenName("satoken");             // token 名称（同时也是 cookie 名称）
		config.setTimeout(30 * 24 * 60 * 60);       // token 有效期（单位：秒），默认30天，-1代表永不过期
		config.setActivityTimeout(60 * 60 * 24);         // token 最低活跃频率（单位：秒），如果 token 超过此时间没有访问系统就会被冻结，默认-1 代表不限制，永不冻结
		config.setIsConcurrent(true);               // 是否允许同一账号多地同时登录（为 true 时允许一起登录，为 false 时新登录挤掉旧登录）
		config.setIsShare(true);                    // 在多人登录同一账号时，是否共用一个 token （为 true 时所有登录共用一个 token，为 false 时每次登录新建一个 token）
		config.setTokenStyle("uuid");               // token 风格
		config.setIsLog(false);                     // 是否输出操作日志
	}

	/**
	 * 注册 Sa-Token 的注解拦截器，打开注解式鉴权功能 
	 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaAnnotationInterceptor()).addPathPatterns("/**");    
    }

    /**
     * 注册 [Sa-Token全局过滤器] 
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
		List<String> satoken_not_urls=v2Config.getSaTokenNotFilterUrl();
		String [] str=satoken_not_urls.toArray(new String[satoken_not_urls.size()]);
		//集合转成,字符串
		str=ArrayUtil.addAll(excludePaths, str);
        return new SaServletFilter()

                // 指定 拦截路由
                .addInclude("/**")
                
                // 指定 放行路由
                .addExclude(str)

                // 认证函数: 每次请求执行 
                .setAuth(r -> {
                	SaRouter.match("/**", () -> StpUtil.checkLogin());
                })

                // 异常处理函数：每次认证函数发生异常时执行此函数 
                .setError(e -> {
                	// e.printStackTrace();
                    if(e instanceof NotLoginException) {
                    	for (String nourl : satoken_not_urls) {
							if(nourl.contains(SaHolder.getRequest().getUrl())) {
								 return JSON.toJSONString(AjaxResult.error(886,e.getMessage()));
							}
						}
                    	//这儿如果是tomcat发布用内部跳转比较好
                    	//SaHolder.getResponse().redirect("/admin/login");
                    	SaHolder.getRequest().forward("/admin/login");
                    }
                    return JSON.toJSONString(AjaxResult.error(e.getMessage()));
                })

                // 前置函数：在每次认证函数之前执行
                .setBeforeAuth(r -> {
                	// ---------- 设置跨域响应头 ----------
        			SaHolder.getResponse()
        			// 允许指定域访问跨域资源
        			.setHeader("Access-Control-Allow-Origin", "*")
        			// 允许所有请求方式
        			.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE")
        			// 有效时间
        			.setHeader("Access-Control-Max-Age", "3600")
        			// 允许的header参数
        			.setHeader("Access-Control-Allow-Headers", "x-requested-with,satoken");
        			
        			// 如果是预检请求，直接返回
        			if ("OPTIONS".equals(SaHolder.getRequest().getMethod())) {
        				System.out.println("=======================浏览器发来了OPTIONS预检请求==========");
        				SaRouter.back();
        			}
                })
                ;
    }
    
    /**
     * 注册 Sa-Token 标签方言 
     */
    @Bean
    public SaTokenDialect saTokenDialect() {
        return new SaTokenDialect();
    }
    
}
