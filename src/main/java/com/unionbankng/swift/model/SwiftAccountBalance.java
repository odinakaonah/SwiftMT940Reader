package com.unionbankng.swift.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.prowidesoftware.swift.model.field.*;
import com.unionbankng.swift.enums.BalanceDescription;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SwiftAccountBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "swift_seq")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, name = "swift_seq", sequenceName = "swift_seq")
    private Long id;
    private String debitCreditMark;
    private String transactionDate;
    private String currency;
    private String amount;
    private String balanceDescription;

    public SwiftAccountBalance(Field64 field64) {
        this.debitCreditMark = field64.getDCMark();
        this.transactionDate = field64.getDate();
        this.currency = field64.getCurrency();
        this.amount = field64.getAmount().replace(",",".");
        this.balanceDescription = BalanceDescription.C_A_BAL_AF.getValue();
    }

    public SwiftAccountBalance(Field65 field65) {
        this.debitCreditMark = field65.getDCMark();
        this.transactionDate = field65.getDate();
        this.currency = field65.getCurrency();
        this.amount = field65.getAmount().replace(",",".");
        this.balanceDescription = BalanceDescription.C_A_BAL_AF.getValue();
    }

    public SwiftAccountBalance(Field60F field60F, Field60M field60M) {
        this.debitCreditMark = field60F==null?field60M.getDCMark():field60F.getDCMark();
        this.transactionDate = field60F==null?field60M.getDate():field60F.getDate();
        this.currency = field60F==null?field60M.getCurrency():field60F.getCurrency();
        this.amount = field60F==null?field60M.getAmount().replace(",","."):field60F.getAmount().replace(",",".");
        this.balanceDescription = field60F==null? BalanceDescription.I_O_BAL.getValue():BalanceDescription.F_O_BAL.getValue();
    }
    public SwiftAccountBalance(Field62F field62F, Field62M field62M) {
        this.debitCreditMark = field62F==null?field62M.getDCMark():field62F.getDCMark();
        this.transactionDate = field62F==null?field62M.getDate():field62F.getDate();
        this.currency = field62F==null?field62M.getCurrency():field62F.getCurrency();
        this.amount = field62F==null?field62M.getAmount().replace(",","."):field62F.getAmount().replace(",",".");
        this.balanceDescription = field62F==null? BalanceDescription.F_C_BAL.getValue():BalanceDescription.I_C_BAL.getValue();
    }
}
