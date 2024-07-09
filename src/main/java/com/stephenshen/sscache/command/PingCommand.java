package com.stephenshen.sscache.command;

import com.stephenshen.sscache.core.Command;
import com.stephenshen.sscache.core.Reply;
import com.stephenshen.sscache.core.SSCache;

/**
 * Ping command.
 * @author stephenshen
 * @date 2024/7/8 07:28:38
 */
public class PingCommand implements Command {
    @Override
    public String name() {
        return "PING";
    }

    @Override
    public Reply<?> exec(SSCache cache, String[] args) {
        return Reply.string(args.length >= 5 ? args[4] : "PONG");
    }
}
