package com.hydraql.manager.core.hbase.model;

/**
 * @author leojie 2024/2/24 14:57
 */
public class Result {
    private final boolean success;
    private final String result;

    public Result(boolean success, String result) {
        this.success = success;
        this.result = result;
    }

    public static Result of(boolean success, String message) {
        return new Result(success, message);
    }

    public static Result ok(String message) {
        return Result.of(true, message);
    }

    public static Result failed(String message) {
        return Result.of(false, message);
    }

    public static Result ok() {
        return Result.of(true, "ok");
    }

    public static Result failed() {
        return Result.of(false, "error");
    }

    public boolean isSuccess() {
        return success;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", result='" + result + '\'' +
                '}';
    }
}
