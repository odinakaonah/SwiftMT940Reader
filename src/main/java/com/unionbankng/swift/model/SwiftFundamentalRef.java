package com.unionbankng.swift.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.prowidesoftware.swift.model.SwiftBlock1;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;
import com.unionbankng.swift.dto.request.SwiftBlock1Request;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SwiftFundamentalRef {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "swift_seq")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, name = "swift_seq", sequenceName = "swift_seq")
    private Long id;
    private String sessionNumber;
    private String sequenceNumber;
    private String applicationId;
    private String serviceId;
    private String logicalTerminal;

    public SwiftFundamentalRef(SwiftBlock1Request block1) {
        this.sessionNumber = block1.getSessionNumber();
        this.sequenceNumber = block1.getSequenceNumber();
        this.applicationId = block1.getApplicationId();
        this.serviceId = block1.getServiceId();
        this.logicalTerminal = block1.getLogicalTerminal();
    }
}
