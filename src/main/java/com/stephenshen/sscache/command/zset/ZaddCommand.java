package com.stephenshen.sscache.command.zset;

import com.stephenshen.sscache.core.Command;
import com.stephenshen.sscache.core.Reply;
import com.stephenshen.sscache.core.SSCache;

import java.util.Arrays;

/**
 * zadd command.
 * @author stephenshen
 * @date 2024/7/8 07:28:38
 */
public class ZaddCommand implements Command {
    @Override
    public String name() {
        return "ZADD";
    }

    @Override
    public Reply<?> exec(SSCache cache, String[] args) {
        String key = getKey(args);
        String[] scores = getHKeys(args);
        String[] vals = getHVals(args);
        return Reply.integer(cache.zadd(key, vals, toDouble(scores)));
    }

    private double[] toDouble(String[] scores) {
        return Arrays.stream(scores).mapToDouble(Double::parseDouble).toArray();
    }
}
