package com.unionbankng.swift.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.prowidesoftware.swift.model.SwiftBlock2;
import com.unionbankng.swift.dto.request.SwiftBlock2Request;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SwiftMessageSenderInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "swift_seq")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, name = "swift_seq", sequenceName = "swift_seq")
    private Long id;
    private String messageType;
    private String senderInputTime;
    private String mirDate;
    private String mirLogicalTerminal;
    private String receiverOutputDate;
    private String receiverOutputTime;
    private String messagePriority;
    private String mirSessionNumber;
    private String mirSequenceNumber;
    private String direction;


    public SwiftMessageSenderInfo(SwiftBlock2Request block2) {
        this.senderInputTime = block2.getSenderInputTime();
        this.messageType = block2.getMessageType();
        this.mirDate = block2.getMIRDate();
        this.direction = block2.getDirection();
        this.mirLogicalTerminal = block2.getMIRLogicalTerminal();
        this.receiverOutputDate = block2.getReceiverOutputDate();
        this.receiverOutputTime = block2.getReceiverOutputTime();
        this.messagePriority = block2.getMessagePriority();
        this.mirSessionNumber = block2.getMIRSessionNumber();
        this.mirSequenceNumber = block2.getMIRSequenceNumber();
    }
}
