package com.fc.v2.common.base;

import com.fc.v2.service.MonClusterService;
import com.fc.v2.service.MonConnectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 数据库连接Controller
 * @ClassName: MonConnectController
 * @author fuce
 * @date 2024-08-16 17:43:36
 */
@Api(value = "业务基础")
@Controller
public class BusinessBaseController {

    @Autowired
    protected MonClusterService monClusterService;

    @Autowired
    protected MonConnectService monConnectService;
}
