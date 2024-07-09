package com.stephenshen.sscache.command;

import com.stephenshen.sscache.core.Command;
import com.stephenshen.sscache.core.Reply;
import com.stephenshen.sscache.core.SSCache;

/**
 * Info command.
 * @author stephenshen
 * @date 2024/7/8 07:28:38
 */
public class InfoCommand implements Command {

    private static final String INFO = "SSCache server[v1.0.1], created by StephenShen." + CRLF
            + "Mock Redis Server, at 2024-06-19 in Beijing." + CRLF;

    @Override
    public String name() {
        return "INFO";
    }

    @Override
    public Reply<?> exec(SSCache cache, String[] args) {
        return Reply.bulkString(INFO);
    }
}
