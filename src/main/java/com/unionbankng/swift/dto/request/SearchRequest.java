package com.unionbankng.swift.dto.request;

import lombok.Data;

@Data
public class SearchRequest {
    private String accIdentification;
    private String transRefNum;
    private String transRelatedRefNum;
    private String fundsCode;
    private String debitCreditMark;
    private String bankCode;
    private String logicalTerminal;
    private String mirSessionNumber;
    private String mirSequenceNumber;
    private String currency;
    private String amount;
    private String mirLogicalTerminal;
    private String startDate;
    private String endDate;
}
