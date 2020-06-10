package com.unionbankng.swift.enums;

public enum BalanceDescription {
    F_O_BAL("60F","First opening balance"),
    I_O_BAL("60M","Intermediate opening balance"),
    F_C_BAL("62F","Final closing balance"),
    I_C_BAL("62M","Intermediate closing balance"),
    C_A_BAL_BF("62a"," Closing Balance Booked Funds"),
    C_A_BAL_AF("64","Closing Available Balance Available Funds"),
    F_A_BAL("65","Forward Available Balance");

    String tag;
    String value;

    BalanceDescription(String tag, String value) {
        this.tag = tag;
        this.value = value;
    }

    public String getTag() {
        return tag;
    }

    public String getValue() {
        return value;
    }
}
