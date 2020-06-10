package com.unionbankng.swift.dto.request;

import lombok.Data;

@Data
public class SwiftBlock1Request {

    private String applicationId;
    private String serviceId;
    private String logicalTerminal;
    private String sessionNumber;
    private String sequenceNumber;
}
