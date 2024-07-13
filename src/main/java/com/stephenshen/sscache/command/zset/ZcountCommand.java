package com.stephenshen.sscache.command.zset;

import com.stephenshen.sscache.core.Command;
import com.stephenshen.sscache.core.Reply;
import com.stephenshen.sscache.core.SSCache;

/**
 * zcount command.
 * @author stephenshen
 * @date 2024/7/8 07:28:38
 */
public class ZcountCommand implements Command {
    @Override
    public String name() {
        return "ZCOUNT";
    }

    @Override
    public Reply<?> exec(SSCache cache, String[] args) {
        String key = getKey(args);
        double min = Double.parseDouble(getVal(args));
        double max = Double.parseDouble(args[8]);
        return Reply.integer(cache.zcount(key, min, max));
    }
}
