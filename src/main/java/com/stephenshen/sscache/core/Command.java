package com.stephenshen.sscache.core;

/**
 * Command interface.
 *
 * @author stephenshen
 * @date 2024/7/8 07:18:08
 */
public interface Command {

    String CRLF = "\r\n";
    String OK = "OK";

    String name();

    Reply<?> exec(SSCache cache, String[] args);

    // add default args operator
    default String getKey(String[] args){
        return args[4];
    }

    default String getVal(String[] args){
        return args[6];
    }

    default String[] getParams(String[] args) {
        int len = (args.length - 3) / 2;
        String[] keys = new String[len];
        for (int i = 0; i < len; i++) {
            keys[i] = args[4 + i * 2];
        }
        return keys;
    }

    default String[] getParamsNoKey(String[] args) {
        int len = (args.length - 5) / 2;
        String[] keys = new String[len];
        for (int i = 0; i < len; i++) {
            keys[i] = args[6 + i * 2];
        }
        return keys;
    }

    default String[] getKeys(String[] args) {
        int len = (args.length - 3) / 4;
        String[] keys = new String[len];
        for (int i = 0; i < len; i++) {
            keys[i] = args[4 + i * 2];
        }
        return keys;
    }

    default String[] getVals(String[] args) {
        int len = (args.length - 3) / 4;
        String[] vals = new String[len];
        for (int i = 0; i < len; i++) {
            vals[i] = args[6 + i * 2];
        }
        return vals;
    }
}
