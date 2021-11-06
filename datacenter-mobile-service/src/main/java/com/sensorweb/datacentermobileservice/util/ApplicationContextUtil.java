package com.sensorweb.datacentermobileservice.util;


import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

        private static ApplicationContext applicationContext;

        public void setApplicationContext(ApplicationContext context) {
            ApplicationContextUtil.applicationContext = context;
        }

        public static Object getBean(String name) {
            return applicationContext.getBean(name);
        }

        public static ApplicationContext getApplicationContext() {
            return applicationContext;
        }
}
