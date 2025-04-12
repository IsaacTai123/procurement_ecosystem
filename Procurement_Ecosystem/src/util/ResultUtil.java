package util;

import common.Result;

/**
 * @author tisaac
 */
public class ResultUtil {
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(true, message, data);
    }

    public static <T> Result<T> success(String message) {
        return new Result<>(true, message);
    }

    public static <T> Result<T> failure(String message, T data) {
        return new Result<>(false, message, data);
    }

    public static <T> Result<T> failure(String message) {
        return new Result<>(false, message);
    }
}
