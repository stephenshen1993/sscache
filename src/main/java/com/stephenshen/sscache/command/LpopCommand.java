package com.stephenshen.sscache.command;

import com.stephenshen.sscache.core.Command;
import com.stephenshen.sscache.core.Reply;
import com.stephenshen.sscache.core.SSCache;

/**
 * Lpop command.
 * @author stephenshen
 * @date 2024/7/8 07:28:38
 */
public class LpopCommand implements Command {
    @Override
    public String name() {
        return "LPOP";
    }

    @Override
    public Reply<?> exec(SSCache cache, String[] args) {
        String key = getKey(args);
        int count = 1;
        if (args.length > 6) {
            String val = getVal(args);
            count = Integer.parseInt(val);
            return Reply.array(cache.lpop(key, count));
        }
        String[] lpop = cache.lpop(key, count);
        return Reply.bulkString(lpop == null ? null : lpop[0]);
    }
}
