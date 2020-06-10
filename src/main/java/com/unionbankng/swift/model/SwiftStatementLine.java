package com.unionbankng.swift.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.prowidesoftware.swift.model.field.Field61;
import com.unionbankng.swift.utils.CommonUtils;
import com.unionbankng.swift.utils.Validation;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SwiftStatementLine {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "swift_seq")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, name = "swift_seq", sequenceName = "swift_seq")
    private Long id;
    private String valueDate;
    private String entryDate;
    private String debitCreditMark;
    private String fundsCode;
    private String amount;
    private String transTypeIdCode;
    private String refAcOwner;
    private String acServicingRef;
    private String supplementaryDetails;
    private String transactionDescription; // Field 86

    public SwiftStatementLine(Field61 field61, String transDescription) {
        this.valueDate = CommonUtils.reconstructDate(field61.getValueDate());
        if (Validation.validData(field61.getEntryDate()))
            this.entryDate = field61.getEntryDate().substring(0, 2) + "-" + field61.getEntryDate().substring(2, 4);
        this.debitCreditMark = field61.getDCMark();
        this.fundsCode = field61.getFundsCode();
        this.amount = field61.getAmount();
        this.transTypeIdCode = field61.getTransactionType();
        this.refAcOwner = field61.getReferenceForTheAccountOwner();
        this.acServicingRef = field61.getReferenceOfTheAccountServicingInstitution();
        this.supplementaryDetails = field61.getSupplementaryDetails();
        this.transactionDescription = transDescription;
    }

    public static void main(String[] args) {
        LocalDate parse = LocalDate.parse("20-01-31", DateTimeFormatter.ofPattern("yy-MM-dd"));
        System.out.println(parse);
    }
}