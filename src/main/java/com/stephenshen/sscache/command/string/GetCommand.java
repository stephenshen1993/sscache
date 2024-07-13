package com.stephenshen.sscache.command.string;

import com.stephenshen.sscache.core.Command;
import com.stephenshen.sscache.core.Reply;
import com.stephenshen.sscache.core.SSCache;

/**
 * Get command.
 * @author stephenshen
 * @date 2024/7/8 07:28:38
 */
public class GetCommand implements Command {
    @Override
    public String name() {
        return "GET";
    }

    @Override
    public Reply<?> exec(SSCache cache, String[] args) {
        String key = getKey(args);
        return Reply.bulkString(cache.get(key));
    }
}
