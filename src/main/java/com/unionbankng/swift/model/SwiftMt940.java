package com.unionbankng.swift.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SwiftMt940 implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "swift_seq")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, name = "swift_seq", sequenceName = "swift_seq")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private SwiftFundamentalRef fundamentalReference; //Header block 1
    @OneToOne(cascade = CascadeType.ALL)
    private SwiftMessageSenderInfo messageSenderInfo;
    private String transRefNum;// Field 20
    private String transRelatedRefNum;// Field 21
    private String accIdentification;// Field 25
    private String statementNo1;// Field 28C 1
    private String statementNo2;// Field 28C 2
    @OneToOne(cascade = CascadeType.ALL)
    private SwiftAccountBalance openingBalance;// Field 60F or 60M

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Swift_Mt940_StatementLine", joinColumns = @JoinColumn(name = "MsgId",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name = "StatementId",referencedColumnName = "id"))
    private Set<SwiftStatementLine> statementLine; // Field 61

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Swift_Mt940_User_Block", joinColumns = @JoinColumn(name = "MsgId",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name = "userId",referencedColumnName = "id"))
    private Set<SwiftOptionalBlocks> userBlock; // Field 61

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Swift_Mt940_Trailer_Block", joinColumns = @JoinColumn(name = "MsgId",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name = "trailerId",referencedColumnName = "id"))
    private Set<SwiftOptionalBlocks> trailerBlock; // Field 61

    @OneToOne(cascade = CascadeType.ALL)
    private SwiftAccountBalance closingBalance;// Field 62F or 62M
    @OneToOne(cascade = CascadeType.ALL)
    private SwiftAccountBalance openingAvailBalance;// Field 64
    @OneToOne(cascade = CascadeType.ALL)
    private SwiftAccountBalance forwardBalance;// Field 65
}
