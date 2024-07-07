package com.stephenshen.sscache.core;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author stephenshen
 * @date 2024/7/5 06:30:41
 */
public class SSCacheHandler extends SimpleChannelInboundHandler<String> {

    private static final String CRLF = "\r\n";
    private static final String STR_PREFIX = "+";
    private static final String BULK_PREFIX = "$";
    private static final String OK = "OK";
    private static final String INFO = "SSCache server[v1.0.0], created by StephenShen." + CRLF
                                     + "Mock Redis Server, at 2024-06-22 in Beijing." + CRLF;

    private static final SSCache cache = new SSCache();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {

        String[] args = message.split(CRLF);
        System.out.println("SSCacheHandler => " + String.join(",", args));
        String cmd = args[2].toUpperCase();

        if ("COMMAND".equals(cmd)) {
            writeByteBuf(ctx, "*2"
                    + CRLF + "$7"
                    + CRLF + "COMMAND"
                    + CRLF + "$4"
                    + CRLF + "PING"
                    + CRLF);
        } else if ("PING".equals(cmd)) {
            simpleString(ctx, args.length >= 5 ? args[4] : "PONG");
        } else if ("INFO".equals(cmd)) {
            bulkString(ctx, INFO);
        } else if ("SET".equals(cmd)) {
            cache.set(args[4], args[6]);
            simpleString(ctx, OK);
        } else if ("GET".equals(cmd)) {
            String value = cache.get(args[4]);
            bulkString(ctx, value);
        } else if ("STRLEN".equals(cmd)) {
            String value = cache.get(args[4]);
            integer(ctx, value == null ? 0 : value.length());
        } else if ("DEL".equals(cmd)) {
            int len = (args.length - 3) / 2;
            String[] keys = new String[len];
            for (int i = 0; i < len; i++) {
                keys[i] = args[4 + i * 2];
            }
            integer(ctx, cache.del(keys));
        } else if ("EXISTS".equals(cmd)) {
            int len = (args.length - 3) / 2;
            String[] keys = new String[len];
            for (int i = 0; i < len; i++) {
                keys[i] = args[4 + i * 2];
            }
            integer(ctx, cache.exists(keys));
        } else if ("MGET".equals(cmd)) {
            int len = (args.length - 3) / 2;
            String[] keys = new String[len];
            for (int i = 0; i < len; i++) {
                keys[i] = args[4 + i * 2];
            }
            array(ctx, cache.mget(keys));
        } else if ("MSET".equals(cmd)) {
            int len = (args.length - 3) / 4;
            String[] keys = new String[len];
            String[] values = new String[len];
            for (int i = 0; i < len; i++) {
                keys[i] = args[4 + i * 2];
                values[i] = args[6 + i * 2];
            }
            cache.mset(keys, values);
            simpleString(ctx, OK);
        } else if ("INCR".equals(cmd)) {
            String key = args[4];
            try {
                integer(ctx, cache.incr(key));
            } catch (NumberFormatException nfe) {
                error(ctx, "NFE " + key + " value[" + cache.get(key) + "] is not a integer.");
            }
        } else if ("DECR".equals(cmd)) {
            String key = args[4];
            try {
                integer(ctx, cache.decr(key));
            } catch (NumberFormatException nfe) {
                error(ctx, "NFE " + key + " value[" + cache.get(key) + "] is not a integer.");
            }
        } else {
            simpleString(ctx, OK);
        }
    }

    private void error(ChannelHandlerContext ctx, String msg) {
        writeByteBuf(ctx, errorEncode(msg));
    }

    private static String errorEncode(String msg) {
        return "-" + msg + CRLF;
    }

    private void integer(ChannelHandlerContext ctx, int i) {
        writeByteBuf(ctx, integerEncode(i));
    }

    private static String integerEncode(int content) {
        return ":" + content + CRLF;
    }

    private void array(ChannelHandlerContext ctx, String[] array) {
        writeByteBuf(ctx, arrayEncode(array));
    }

    private static String arrayEncode(Object[] array) {
        StringBuilder sb = new StringBuilder();
        if (array == null) {
            sb.append("*-1" + CRLF);
        } else if (array.length == 0) {
            sb.append("*0" + CRLF);
        } else {
            sb.append("*" + array.length + CRLF);
            for (int i = 0; i < array.length; i++) {
                Object obj = array[i];
                if (obj == null) {
                    sb.append("$-1" + CRLF);
                } else {
                    if (obj instanceof Integer) {
                        sb.append(integerEncode((Integer) obj));
                    } else if (obj instanceof String) {
                        sb.append(bulkStringEncode((String) obj));
                    } else if (obj instanceof Object[] objs) {
                        sb.append(arrayEncode(objs));
                    }
                }
            }
        }
        return sb.toString();
    }

    private void bulkString(ChannelHandlerContext ctx, String content) {
        writeByteBuf(ctx, bulkStringEncode(content));
    }

    private static String bulkStringEncode(String content) {
        String ret;
        if (content == null) {
            ret = "$-1";
        } else if (content.isEmpty()) {
            ret = "$0";
        } else {
            ret = BULK_PREFIX + content.getBytes().length + CRLF + content;
        }
        return ret + CRLF;
    }

    private void simpleString(ChannelHandlerContext ctx, String content) {
        writeByteBuf(ctx, stringEncode(content));
    }

    private static String stringEncode(String content) {
        String ret;
        if (content == null) {
            ret = "$-1";
        } else if (content.isEmpty()) {
            ret = "$0";
        } else {
            ret = STR_PREFIX + content;
        }
        return ret + CRLF;
    }

    private void writeByteBuf(ChannelHandlerContext ctx, String content) {
        System.out.println("wrap byte buffer and reply: " + content);
        ByteBuf buffer = Unpooled.buffer(128);
        buffer.writeBytes(content.getBytes());
        ctx.writeAndFlush(buffer);
    }
}
