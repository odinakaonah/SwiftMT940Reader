package com.unionbankng.swift.jobs;

import com.unionbankng.swift.service.Mt940Service;
import com.unionbankng.swift.service.ReaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Schedular {

    private ReaderService readerService;

    @Autowired
    public Schedular(ReaderService readerService) {
        this.readerService = readerService;

    }

    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void loadSwiftMessage() {
        log.info("Running job to load new messages");
        readerService.loadMessages();
    }

}
