package com.unionbankng.swift.config;

public enum EncryptionHeader {

    AUTHORIZATION("Authorization"),
    CHANNEL_CODE("ChannelCode");

    String name;

    EncryptionHeader(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
