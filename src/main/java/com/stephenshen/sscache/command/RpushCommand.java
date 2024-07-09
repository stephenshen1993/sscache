package com.stephenshen.sscache.command;

import com.stephenshen.sscache.core.Command;
import com.stephenshen.sscache.core.Reply;
import com.stephenshen.sscache.core.SSCache;

/**
 * Rpush command.
 * @author stephenshen
 * @date 2024/7/8 07:28:38
 */
public class RpushCommand implements Command {
    @Override
    public String name() {
        return "RPUSH";
    }

    @Override
    public Reply<?> exec(SSCache cache, String[] args) {
        String key = getKey(args);
        String[] vals = getParamsNoKey(args);
        return Reply.integer(cache.rpush(key, vals));
    }
}
