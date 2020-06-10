package com.unionbankng.swift.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SwiftOptionalBlocks {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "swift_seq")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, name = "swift_seq", sequenceName = "swift_seq")
    private Long id;
    private String key;
    private String value;
    private Integer blockNo;

    public SwiftOptionalBlocks(String key, String value, Integer blockNo) {
        this.key = key;
        this.blockNo = blockNo;
        this.value = value;
    }
}
