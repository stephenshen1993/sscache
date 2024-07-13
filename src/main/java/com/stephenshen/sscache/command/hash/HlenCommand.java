package com.stephenshen.sscache.command.hash;

import com.stephenshen.sscache.core.Command;
import com.stephenshen.sscache.core.Reply;
import com.stephenshen.sscache.core.SSCache;

/**
 * hlen command.
 * @author stephenshen
 * @date 2024/7/8 07:28:38
 */
public class HlenCommand implements Command {
    @Override
    public String name() {
        return "HLEN";
    }

    @Override
    public Reply<?> exec(SSCache cache, String[] args) {
        String key = getKey(args);
        return Reply.integer(cache.hlen(key));
    }
}
