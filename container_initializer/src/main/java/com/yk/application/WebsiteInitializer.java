package com.yk.application;

import javax.servlet.ServletContext;

public interface WebsiteInitializer {
    void onStartup(ServletContext servletContext);
}
