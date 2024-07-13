package com.stephenshen.sscache.command.hash;

import com.stephenshen.sscache.core.Command;
import com.stephenshen.sscache.core.Reply;
import com.stephenshen.sscache.core.SSCache;

/**
 * hdel command.
 * @author stephenshen
 * @date 2024/7/8 07:28:38
 */
public class HdelCommand implements Command {
    @Override
    public String name() {
        return this.getClass().getSimpleName()
                .replace("Command", "").toUpperCase();
    }

    @Override
    public Reply<?> exec(SSCache cache, String[] args) {
        String key = getKey(args);
        String[] hkeys = getParamsNoKey(args);
        return Reply.integer(cache.hdel(key, hkeys));
    }
}
