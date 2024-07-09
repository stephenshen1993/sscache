package com.stephenshen.sscache.command;

import com.stephenshen.sscache.core.Command;
import com.stephenshen.sscache.core.Commands;
import com.stephenshen.sscache.core.Reply;
import com.stephenshen.sscache.core.SSCache;

/**
 * Command command.
 * @author stephenshen
 * @date 2024/7/8 07:28:38
 */
public class CommandCommand implements Command {
    @Override
    public String name() {
        return "COMMAND";
    }

    @Override
    public Reply<?> exec(SSCache cache, String[] args) {
        return Reply.array(Commands.getCommandNames());
    }
}
