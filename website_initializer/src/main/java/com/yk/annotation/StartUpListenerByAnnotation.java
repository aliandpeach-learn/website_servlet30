package com.yk.annotation;

@WebsiteInitializerDeclare(StartUpListenerByAnnotation.class)
public class StartUpListenerByAnnotation {
    public StartUpListenerByAnnotation() {
        System.out.println("StartUpListenerByAnnotation");
    }
}
