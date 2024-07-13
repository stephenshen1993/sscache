package com.stephenshen.sscache.command.hash;

import com.stephenshen.sscache.core.Command;
import com.stephenshen.sscache.core.Reply;
import com.stephenshen.sscache.core.SSCache;

/**
 * hset command.
 * @author stephenshen
 * @date 2024/7/8 07:28:38
 */
public class HsetCommand implements Command {
    @Override
    public String name() {
        return "HSET";
    }

    @Override
    public Reply<?> exec(SSCache cache, String[] args) {
        String key = getKey(args);
        String[] hkeys = getHKeys(args);
        String[] hvals = getHVals(args);
        return Reply.integer(cache.hset(key, hkeys, hvals));
    }
}
