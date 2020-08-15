package com.yk.container;

import com.yk.annotation.WebsiteInitializerDeclare;
import com.yk.application.WebsiteInitializer;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 该类对应Spring中的 SpringServletContainerInitializer
 */
//该类作为实现Servlet3.0的启动类，配置在META-INF/services/javax.servlet.ServletContainerInitializer
//然后被tomcat实现的spi技术
// (java 原版的spi技术主要类是ServiceLoader, tomcat重新实现了ServiceLoader类，名字是WebappServiceLoader，用法基本一致)所启动
// 主要过程是：先根据services下的配置文件读取ServletContainerInitializer的实现类，
// 紧接着扫描@HandlesTypes中配置的接口或者注解，把接口（WebsiteInitializer）实现类的搜集起来,
// 然后调用onStartUp方法，传入刚才搜集的实现类
//
@HandlesTypes({WebsiteInitializerDeclare.class, WebsiteInitializer.class})
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
                        continue;
                    } catch (Throwable ex) {
                        throw new ServletException("Failed to instantiate WebsiteInitializer class", ex);
                    }
                }
                WebsiteInitializerDeclare websiteHandlesTypes = waiClass.getAnnotation(WebsiteInitializerDeclare.class);
                if(!waiClass.isInterface() && !Modifier.isAbstract(waiClass.getModifiers())){
                    try {
                        Object object = waiClass.newInstance();
                    } catch (InstantiationException e) {
                        throw new ServletException("Failed to instantiate WebsiteInitializerDeclare class", e);
                    } catch (IllegalAccessException e) {
                        throw new ServletException("Failed to instantiate WebsiteInitializerDeclare class", e);
                    }
                }
            }
        }
        //这里还可以从webAppInitializerClasses中获取注释了@WebsiteHandlesTypes的类

        if (initializers.isEmpty()) {
            ctx.log("No Spring WebsiteInitializer types detected on classpath");
            return;
        }

        ctx.log(initializers.size() + " WebsiteServletContainerInitializer detected on classpath");
        for (WebsiteInitializer initializer : initializers) {
            initializer.onStartup(ctx);
        }
    }
}
