package com.unionbankng.swift.enums;

public enum ResponseCode {
    IN_PROGRESS(102,"Processing your Request"),
    SUCCESS(200,"Successful request"),
    AWAITING_AUTHORIZATION(206,"{} successful and awaiting authorization"),
    CREATED(200,"{} successful"),
    PROCEED(200,"Proceed to save; {}"),
    ITEM_NOT_FOUND(404,"Sorry we can't find {} record on our system"),
    ITEM_FOUND(200,"You already have account...Do you wish to continue with it ? "),
    NOT_AUTHORIZED(401,"!Oops seems something went wrong...try again later"),
    REQUEST_TIMEOUT(408,"Session timeout.....please start from the previous step"),
    BAD_REQUEST(400,"Failed; {}"),
    UNKNOWN(500,"An error occurred...please try again later"),
    NOT_IMPLEMENTED(501,"Not Implemented...please try again later"),
    UNAVAILABLE(503,"!Oops...seems like something went wrong ...please try again later");
    String value;
    Integer code;

    ResponseCode(Integer code, String value) {
        this.value = value;
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public Integer getCode() {
        return code;
    }
}
