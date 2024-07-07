package com.stephenshen.sscache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * plugin entrypoint.
 * @author stephenshen
 * @date 2024/7/3 07:04:16
 */
@Component
public class SSApplicationListener implements ApplicationListener<ApplicationEvent> {

    @Autowired
    private List<SSPlugin> plugins;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationReadyEvent are) {
            for (SSPlugin plugin : plugins) {
                plugin.init();
                plugin.startup();
            }
        } else if (event instanceof ContextClosedEvent cce) {
            for (SSPlugin plugin : plugins) {
                plugin.shutdown();
            }
        }
    }
}
