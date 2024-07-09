package com.stephenshen.sscache.command;

import com.stephenshen.sscache.core.Command;
import com.stephenshen.sscache.core.Reply;
import com.stephenshen.sscache.core.SSCache;

/**
 * Decr command.
 * @author stephenshen
 * @date 2024/7/8 07:28:38
 */
public class DecrCommand implements Command {
    @Override
    public String name() {
        return "DECR";
    }

    @Override
    public Reply<?> exec(SSCache cache, String[] args) {
        String key = getKey(args);
        return Reply.integer(cache.decr(key));
    }
}
