package com.yk.application;

import javax.servlet.ServletContext;

/**
 * 该类对应Spring中的 WebApplicationInitializer
 */
public interface WebsiteInitializer {
    void onStartup(ServletContext servletContext);
}
