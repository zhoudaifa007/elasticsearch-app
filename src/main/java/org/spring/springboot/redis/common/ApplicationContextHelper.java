package org.spring.springboot.redis.common;

/**
 * Created by zhoudf2 on 2017-11-8.
 */
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextHelper implements ApplicationContextAware{

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static Object getBean( String beanName ) {
        return applicationContext.getBean( beanName );
    }
}