package com.stephenshen.sscache;

/**
 * ss cache plugin.
 * @author stephenshen
 * @date 2024/7/3 07:02:14
 */
public interface SSPlugin {

    void init();
    void startup();
    void shutdown();
}
