package com.fc;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
* @ClassName: SpringbootWebInitializer
* @Description: web容器中进行部署
* @author fuce
* @date: 2024年10月16日
*
*/
public class V2WebInitializer  extends SpringBootServletInitializer{
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(V2Application.class);
    }
}
