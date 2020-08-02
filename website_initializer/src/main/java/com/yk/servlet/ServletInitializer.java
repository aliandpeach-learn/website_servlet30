package com.yk.servlet;

import com.yk.application.WebsiteInitializer;
import com.yk.filter.UserFilter;
import com.yk.listener.SecondListener;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.EnumSet;

/**
 * 继承了WebsiteInitializer就会被WebsiteServletContainerInitializer类感知到，然后被初始化
 */
public class ServletInitializer implements WebsiteInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {
        ServletRegistration.Dynamic servlet = servletContext.addServlet("userServlet", new IndexServlet());
        servlet.addMapping("/index");

        FilterRegistration.Dynamic filter = servletContext.addFilter("userFilter", new UserFilter());
        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.ASYNC), false, "/*");

//        servletContext.addListener(new SecondListener());
    }
}