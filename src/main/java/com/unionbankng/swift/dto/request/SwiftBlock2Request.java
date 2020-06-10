package com.unionbankng.swift.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SwiftBlock2Request {
    private String senderInputTime;
    @JsonProperty("MIRDate")
    private String MIRDate;
    @JsonProperty("MIRLogicalTerminal")
    private String MIRLogicalTerminal;
    @JsonProperty("MIRSessionNumber")
    private String MIRSessionNumber;
    @JsonProperty("MIRSequenceNumber")
    private String MIRSequenceNumber;
    private String receiverOutputDate;
    private String receiverOutputTime;
    private String messagePriority;
    private String messageType;
    private String direction;
}
