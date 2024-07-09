package com.stephenshen.sscache.command;

import com.stephenshen.sscache.core.Command;
import com.stephenshen.sscache.core.Reply;
import com.stephenshen.sscache.core.SSCache;

/**
 * Exists command.
 * @author stephenshen
 * @date 2024/7/8 07:28:38
 */
public class ExistsCommand implements Command {
    @Override
    public String name() {
        return "EXISTS";
    }

    @Override
    public Reply<?> exec(SSCache cache, String[] args) {
        String[] keys = getParams(args);
        return Reply.integer(cache.exists(keys));
    }


}
