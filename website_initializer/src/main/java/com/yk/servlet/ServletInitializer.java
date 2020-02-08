package com.yk.servlet;

import com.yk.application.WebsiteInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * 继承了WebsiteInitializer就会被WebsiteServletContainerInitializer类感知到，然后被初始化
 */
public class ServletInitializer implements WebsiteInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {
        ServletRegistration.Dynamic servlet = servletContext.addServlet("userServlet", new IndexServlet());
        servlet.addMapping("/index");
    }
}
