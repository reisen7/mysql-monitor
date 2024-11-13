package com.fc.v2.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;
/**
 * @ClassName SpringUtil
 * @Author reisen
 * @Description
 * @date 2024年11月05日
 **/

@Component
public final class SpringUtil implements ApplicationContextAware {


    private static ApplicationContext applicationContext = null;

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }

    public static <T> Collection<T> getBeansOfType(Class<T> clazz) {
        return applicationContext.getBeansOfType(clazz).values();
    }
}
