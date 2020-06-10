package com.unionbankng.swift;

import com.unionbankng.swift.model.SwiftMt940;
import com.unionbankng.swift.service.Mt940Service;
import com.unionbankng.swift.service.ReaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SwiftApplicationTests {

    private static String swiftMessage = "C:\\Users\\developer5\\Documents\\swiftMessage";
    File folder;


    @Autowired
    private ReaderService readerService;
    @Autowired
    private Mt940Service mt940Service;

    @BeforeEach
    public void init() {
        folder = new File(swiftMessage);

        if (readerService ==null)
            readerService = new ReaderService();
        if (mt940Service ==null)
            mt940Service = new Mt940Service();
    }
    @Test
    public void contextLoads() {
    }

    @Test
    public void loadMessage() {
        folder = new File(swiftMessage);
        readerService.loadSwiftMessage(folder);
    }


    @Test
    public void loadFolder() {
        readerService.out();
    }

    @Test
    public void getMessages(){

        Page<SwiftMt940> all = mt940Service.getAll(new PageRequest(0,10));
        assertTrue("Message is empty ",all.getContent().isEmpty());
    }

}
