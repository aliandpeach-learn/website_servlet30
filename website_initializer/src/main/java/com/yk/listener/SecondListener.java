package com.yk.listener;

import com.yk.servlet.SecondServlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

@WebListener//该注释可以直接使得SecondListener成为一个listener
public class SecondListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //和ServletInitializer一样也是可以的，因为@WebListener已经被定义为了listener，所以就可以在启动时定义servlet了
        ServletRegistration.Dynamic servlet = sce.getServletContext().addServlet("secondServlet", new SecondServlet());
        servlet.addMapping("/second");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
