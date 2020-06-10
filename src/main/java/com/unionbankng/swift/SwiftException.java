package com.unionbankng.swift;

public class SwiftException extends Exception {
    private final Integer httpCode;
    public SwiftException(Integer httpCode, String message)
    {
        super(message);
        this.httpCode = httpCode;
    }
}
