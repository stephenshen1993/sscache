package com.stephenshen.sscache.command.zset;

import com.stephenshen.sscache.core.Command;
import com.stephenshen.sscache.core.Reply;
import com.stephenshen.sscache.core.SSCache;

import java.util.Arrays;

/**
 * zrem command.
 * @author stephenshen
 * @date 2024/7/8 07:28:38
 */
public class ZremCommand implements Command {
    @Override
    public String name() {
        return "ZREM";
    }

    @Override
    public Reply<?> exec(SSCache cache, String[] args) {
        String key = getKey(args);
        String[] vals = getParamsNoKey(args);
        return Reply.integer(cache.zrem(key, vals));
    }
}
