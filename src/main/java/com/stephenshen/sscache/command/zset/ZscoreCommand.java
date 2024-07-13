package com.stephenshen.sscache.command.zset;

import com.stephenshen.sscache.core.Command;
import com.stephenshen.sscache.core.Reply;
import com.stephenshen.sscache.core.SSCache;

/**
 * zscore command.
 * @author stephenshen
 * @date 2024/7/8 07:28:38
 */
public class ZscoreCommand implements Command {
    @Override
    public String name() {
        return "ZSCORE";
    }

    @Override
    public Reply<?> exec(SSCache cache, String[] args) {
        String key = getKey(args);
        String val = getVal(args);
        Double zscore = cache.zscore(key, val);
        return Reply.string(zscore == null ? null : zscore.toString());
    }
}
