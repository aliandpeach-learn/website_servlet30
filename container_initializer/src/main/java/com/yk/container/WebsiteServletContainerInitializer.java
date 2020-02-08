package com.yk.container;

import com.yk.annotation.WebsiteHandlesTypes;
import com.yk.application.WebsiteInitializer;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

//该类作为实现Servlet3.0的启动类，配置在META-INF/services/javax.servlet.ServletContainerInitializer
//然后被tomcat实现的spi技术(java 原版的spi技术主要类是ServiceLoader, tomcat重新实现了ServiceLoader类，名字是WebappServiceLader，用法基本一致)所启动
@HandlesTypes({WebsiteHandlesTypes.class, WebsiteInitializer.class})
public class WebsiteServletContainerInitializer implements ServletContainerInitializer {
    public void onStartup(Set<Class<?>> webAppInitializerClasses, ServletContext ctx) throws ServletException {
        //该集合中包含了实现WebsiteInitializer接口的类,在这里统一初始化
        List<WebsiteInitializer> initializers = new LinkedList<WebsiteInitializer>();
        if (webAppInitializerClasses != null) {
            for (Class<?> waiClass : webAppInitializerClasses) {
                if (!waiClass.isInterface() && !Modifier.isAbstract(waiClass.getModifiers()) &&
                        WebsiteInitializer.class.isAssignableFrom(waiClass)) {
                    try {
                        initializers.add((WebsiteInitializer) waiClass.newInstance());
                    } catch (Throwable ex) {
                        throw new ServletException("Failed to instantiate WebsiteInitializer class", ex);
                    }
                }
            }
        }
        //这里还可以从webAppInitializerClasses中获取注释了@WebsiteHandlesTypes的类

        if (initializers.isEmpty()) {
            ctx.log("No Spring WebsiteInitializer types detected on classpath");
            return;
        }

        ctx.log(initializers.size() + " Spring WebApplicationInitializers detected on classpath");
        for (WebsiteInitializer initializer : initializers) {
            initializer.onStartup(ctx);
        }
    }
}
