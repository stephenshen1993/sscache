package com.stephenshen.sscache.core;

import com.stephenshen.sscache.command.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * register commands here.
 * @author stephenshen
 * @date 2024/7/8 07:30:04
 */
public class Commands {

    private static final Map<String, Command> ALL = new LinkedHashMap<>();

    static {
        initCommands();
    }

    private static void initCommands() {
        // common commands
        register(new PingCommand());
        register(new InfoCommand());
        register(new CommandCommand());

        // string
        register(new SetCommand());
        register(new GetCommand());
        register(new StrLenCommand());
        register(new DelCommand());
        register(new ExistsCommand());
        register(new IncrCommand());
        register(new DecrCommand());
        register(new MsetCommand());
        register(new MgetCommand());

        // list
        // Lpush, Rpush, Lpop, Rpop, Llen, Lindex, Lrange
        register(new LpushCommand());
        register(new LpopCommand());
        register(new RpushCommand());
        register(new RpopCommand());
        register(new LlenCommand());
        register(new LindexCommand());
        register(new LrangCommand());
    }

    public static void register(Command command) {
        ALL.put(command.name(), command);
    }

    public static Command get(String name) {
        return ALL.get(name);
    }

    public static String[] getCommandNames() {
        return ALL.keySet().toArray(new String[0]);
    }
}
