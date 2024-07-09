package com.stephenshen.sscache.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * reply for 5 type.
 * @author stephenshen
 * @date 2024/7/8 07:20:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply<T> {

    private T value;
    private ReplyType type;

    public static Reply<String> string (String value) {
        return new Reply<>(value, ReplyType.SIMPLE_STRING);
    }

    public static Reply<String> bulkString (String value) {
        return new Reply<>(value, ReplyType.BULK_STRING);
    }

    public static Reply<Integer> integer (Integer value) {
        return new Reply<>(value, ReplyType.INT);
    }

    public static Reply<String> error (String value) {
        return new Reply<>(value, ReplyType.ERROR);
    }

    public static Reply<String[]> array (String[] value) {
        return new Reply<>(value, ReplyType.ARRAY);
    }
}
