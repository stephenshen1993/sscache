package com.stephenshen.sscache.command.hash;

import com.stephenshen.sscache.core.Command;
import com.stephenshen.sscache.core.Reply;
import com.stephenshen.sscache.core.SSCache;

/**
 * hexists command.
 * @author stephenshen
 * @date 2024/7/8 07:28:38
 */
public class HexistsCommand implements Command {
    @Override
    public String name() {
        return "HEXISTS";
    }

    @Override
    public Reply<?> exec(SSCache cache, String[] args) {
        String key = getKey(args);
        String hkey = getVal(args);
        return Reply.integer(cache.hexists(key, hkey));
    }
}
