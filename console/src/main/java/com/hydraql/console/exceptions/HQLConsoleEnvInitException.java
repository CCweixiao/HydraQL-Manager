package com.hydraql.console.exceptions;

/**
 * @author leojie 2024/3/15 15:11
 */
public class HQLConsoleEnvInitException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    protected final String message;

    public HQLConsoleEnvInitException(String message)
    {
        this.message = message;
    }

    public HQLConsoleEnvInitException(String message, Throwable e)
    {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
