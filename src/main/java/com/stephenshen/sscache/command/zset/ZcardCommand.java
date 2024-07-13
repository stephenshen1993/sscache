package com.stephenshen.sscache.command.zset;

import com.stephenshen.sscache.core.Command;
import com.stephenshen.sscache.core.Reply;
import com.stephenshen.sscache.core.SSCache;

/**
 * zcard command.
 * @author stephenshen
 * @date 2024/7/8 07:28:38
 */
public class ZcardCommand implements Command {
    @Override
    public String name() {
        return "ZCARD";
    }

    @Override
    public Reply<?> exec(SSCache cache, String[] args) {
        String key = getKey(args);
        return Reply.integer(cache.zcard(key));
    }
}
