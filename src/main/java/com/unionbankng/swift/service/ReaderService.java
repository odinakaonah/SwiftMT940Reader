package com.unionbankng.swift.service;

import com.unionbankng.swift.SwiftException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

@Service
public class ReaderService {

    @Value("${ubn.swiftMessage.folder}")
    private String swiftMessage /*= "C:\\Users\\developer5\\Documents\\swiftMessage"*/;


    private Mt940Service mt940Service;

    @Autowired
    public ReaderService(Mt940Service mt940Service) {
        this.mt940Service = mt940Service;
    }

    public ReaderService() {
    }

    public File loadSwiftMessage(final File folder) {

        try {
            File[] obj = folder.listFiles();

            if (obj == null) throw new SwiftException(404,"No message found");
            System.out.println("No file loaded");
            throw new SwiftException(404,"No file was loaded");
        } catch (SwiftException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loadMessages() {
        File folder = new File(swiftMessage);
        loadSwiftMessage(folder);
    }

    public String out() {
        return "Tech expert";
    }
}
