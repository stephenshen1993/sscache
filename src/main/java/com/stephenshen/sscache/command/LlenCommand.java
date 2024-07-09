package com.stephenshen.sscache.command;

import com.stephenshen.sscache.core.Command;
import com.stephenshen.sscache.core.Reply;
import com.stephenshen.sscache.core.SSCache;

/**
 * Llen command.
 * @author stephenshen
 * @date 2024/7/8 07:28:38
 */
public class LlenCommand implements Command {
    @Override
    public String name() {
        return "LLEN";
    }

    @Override
    public Reply<?> exec(SSCache cache, String[] args) {
        String key = getKey(args);
        return Reply.integer(cache.llen(key));
    }
}
