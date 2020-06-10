package com.unionbankng.swift.service;

import com.prowidesoftware.swift.io.RJEReader;
import com.prowidesoftware.swift.model.*;
import com.prowidesoftware.swift.model.field.*;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;
import com.unionbankng.swift.SwiftException;
import com.unionbankng.swift.dto.request.SearchRequest;
import com.unionbankng.swift.dto.request.SwiftBlock1Request;
import com.unionbankng.swift.dto.request.SwiftBlock2Request;
import com.unionbankng.swift.model.*;
import com.unionbankng.swift.repo.Mt940Repo;
import com.unionbankng.swift.repo.SearchRepo;
import com.unionbankng.swift.utils.FileService;
import com.unionbankng.swift.utils.JsonBuilder;
import com.unionbankng.swift.utils.Validation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class Mt940Service {

    private Mt940Repo mt940Repo;
    private SearchRepo searchRepo;

    @Value("${ubn.swiftMessage.newfolder}")
    private String newFolder;

    @Autowired
    public Mt940Service(Mt940Repo mt940Repo, SearchRepo searchRepo) {
        this.mt940Repo = mt940Repo;
        this.searchRepo = searchRepo;
    }

    public Mt940Service() {
    }

    @Async
    public void parseSwiftMessage(File file) throws IOException, SwiftException {
        RJEReader reader = new RJEReader(file);

//        if (file.getName().contains("read")) {
//            System.out.println(file.getName()+" was read previously but was not deleted");
//            fileService.moveReadFileToBackUpFolder(file,0);
//        } else
        while (reader.hasNext()) {
            AbstractMT msg = reader.nextMT();

            if (StringUtils.equals(msg.getServiceId(), "21") && msg.getSwiftMessage().getUnparsedTextsSize() > 0) {
                msg = msg.getSwiftMessage().getUnparsedTexts().getTextAsMessage(0).toMT();
            }

            if (msg.isType(940)) {
                MT940 mt940 = (MT940) msg;
                SwiftMessage swiftMessage = mt940.getSwiftMessage();

                SwiftBlock1 block1 = swiftMessage.getBlock1();
                SwiftBlock2 block2 = swiftMessage.getBlock2();
                SwiftBlock3 block3 = swiftMessage.getBlock3();
                SwiftBlock5 block5 = swiftMessage.getBlock5();


                String jsonBlock1 = block1.toJson();
                SwiftBlock1Request block1Request = JsonBuilder.otherObj(jsonBlock1, SwiftBlock1Request.class);

                String jsonBlock2 = block2.toJson();
                SwiftBlock2Request block2Request = JsonBuilder.otherObj(jsonBlock2, SwiftBlock2Request.class);

                Set<SwiftOptionalBlocks> block3Tags = new HashSet<>();
                Set<SwiftOptionalBlocks> block5Tags = new HashSet<>();
                for (Tag tag : block3.getTags()) {
                    block3Tags.add(new SwiftOptionalBlocks(tag.getName(), tag.getValue(), 3));
                }
                for (Tag tag : block5.getTags()) {
                    block5Tags.add(new SwiftOptionalBlocks(tag.getName(), tag.getValue(), 5));
                }
                System.out.println("Block 3 " + block3Tags);
                System.out.println("Block 3 " + block5Tags);
                /*System.out.println("Block3 test"+ collectBlock3);
                Map<String, String> collect = block5.getTags().stream().collect(Collectors.toMap(tag -> tag.getName(), tag -> tag.getValue()));
                System.out.println("Block5 test"+ collect);
                System.out.println("Block 5  "+ block5.toJson());*/
//                System.out.println("swift Sample "+ swiftMessage);
                SwiftMt940 swiftMt940 = new SwiftMt940();
                swiftMt940.setMessageSenderInfo(new SwiftMessageSenderInfo(block2Request));
                swiftMt940.setFundamentalReference(new SwiftFundamentalRef(block1Request));
                swiftMt940.setUserBlock(block3Tags.stream().filter(Objects::nonNull).collect(Collectors.toSet()));
                swiftMt940.setTrailerBlock(block5Tags.stream().filter(Objects::nonNull).collect(Collectors.toSet()));
                buildSwiftMt940(mt940, swiftMt940);
                if (mt940Repo.getTransRefNum(swiftMt940.getTransRefNum()) != null){
                    System.out.println("This record was previously read... retrying delete operation");
                    FileService.moveReadFileToBackUpFolder(file,newFolder);
                }
                  else {
                    System.out.println(swiftMt940);
                    swiftMt940 = mt940Repo.save(swiftMt940);

                    if (swiftMt940 != null)
                        FileService.moveReadFileToBackUpFolder(file, newFolder);
                }
            }
        }
    }


    public void buildSwiftMt940(MT940 mt940, SwiftMt940 swiftMt940) {

        buildSingleFields(mt940, swiftMt940);

        //Build field 60 (M and F)
        buildOpeningBalance(mt940, swiftMt940);
        List<Field61> field61 = mt940.getField61();
        Set<SwiftStatementLine> statementLines = new HashSet<>();

        //Build field 61
        buildStatementLine(mt940, swiftMt940, field61, statementLines);

        Field62F field62F = mt940.getField62F();

        //Build field 62
        buildClosingBalance(mt940, swiftMt940, field62F);

        Field64 field64 = mt940.getField64();

        //Build field 64
        buildOpeningAvailBalance(swiftMt940, field64);
        List<Field65> field65 = mt940.getField65();

        //Build balance
        buildForwardBalance(swiftMt940, field65);
    }

    public void buildForwardBalance(SwiftMt940 swiftMt940, List<Field65> field65) {
        SwiftAccountBalance swiftAccountBalance;
        if (field65 != null)
            for (Field65 field : field65) {
                swiftAccountBalance = new SwiftAccountBalance(field);
                swiftMt940.setForwardBalance(swiftAccountBalance);
            }
    }

    public void buildOpeningAvailBalance(SwiftMt940 swiftMt940, Field64 field64) {
        SwiftAccountBalance swiftAccountBalance;
        if (field64 != null) {
            swiftAccountBalance = new SwiftAccountBalance(field64);
            swiftMt940.setClosingBalance(swiftAccountBalance);
        }
    }

    public void buildClosingBalance(MT940 mt940, SwiftMt940 swiftMt940, Field62F field62F) {
        SwiftAccountBalance swiftAccountBalance;
        if (field62F != null) {
            swiftAccountBalance = new SwiftAccountBalance(field62F, null);
            swiftMt940.setClosingBalance(swiftAccountBalance);
        }
        Field62M field62M = mt940.getField62M();//Closing Balance
        if (field62M != null) {
            swiftAccountBalance = new SwiftAccountBalance(null, field62M);
            swiftMt940.setClosingBalance(swiftAccountBalance);
        }
    }

    public void buildStatementLine(MT940 mt940, SwiftMt940 swiftMt940, List<Field61> field61, Set<SwiftStatementLine> statementLines) {
        if (field61 != null) {
            for (int i = 0; i < field61.size(); i++) {
                SwiftStatementLine swiftStatementLine = null;
//                        System.out.println("Field 61 : " + field61.get(i));
                StringBuilder components = new StringBuilder();
                List<Field86> field86 = mt940.getField86();
                if (field86 != null && i < field86.size()) {
                    Field86 field861 = field86.get(i);
                    if (field861 != null) {
                        buildComponent(components, field861);
                    }
                }
                swiftStatementLine = new SwiftStatementLine(field61.get(i), components.toString());
                statementLines.add(swiftStatementLine);
            }
            swiftMt940.setStatementLine(statementLines);
        } else {

            List<Field86> field86 = mt940.getField86();
            if (field86 != null)
                for (Field86 f : field86)
                    System.out.println("Field 86 : " + f.toString());
        }
    }

    public void buildOpeningBalance(MT940 mt940, SwiftMt940 swiftMt940) {
        SwiftAccountBalance swiftAccountBalance = null;
        Field60F field60F = mt940.getField60F();//Opening balance
        if (field60F != null) {
            swiftAccountBalance = new SwiftAccountBalance(field60F, null);

        }
        Field60M field60M = mt940.getField60M(); // Opening balance
        if (field60M != null) {
            swiftAccountBalance = new SwiftAccountBalance(null, field60M);

        }
        if (swiftAccountBalance != null) {

            swiftMt940.setOpeningAvailBalance(swiftAccountBalance);
        }
    }

    public void buildSingleFields(MT940 mt940, SwiftMt940 swiftMt940) {
        Field20 field20 = mt940.getField20();
        if (field20 != null)
            swiftMt940.setTransRefNum(field20.getComponent1());
        Field21 field21 = mt940.getField21();
        if (field21 != null)
            swiftMt940.setTransRelatedRefNum(field21.getValue());
        Field25 field25 = mt940.getField25();
        if (field25 != null)
            swiftMt940.setAccIdentification(field25.getValue());
        Field25P field25P = mt940.getField25P();
        if (field25P != null)
            swiftMt940.setAccIdentification(field25P.getValue());
        Field28C field28C = mt940.getField28C();
        if (field28C != null) {
            swiftMt940.setStatementNo1(field28C.getComponent1());
            swiftMt940.setStatementNo2(field28C.getComponent2());
        }
    }

    private void buildComponent(StringBuilder components, Field86 field861) {
        String component1 = field861.getComponent1();
        if (Validation.validData(component1))
            components.append(component1).append("; ");
        String component2 = field861.getComponent2();
        if (Validation.validData(component2))
            components.append(component2).append("; ");
        String component3 = field861.getComponent3();
        if (Validation.validData(component3))
            components.append(component3).append("; ");
        String component4 = field861.getComponent4();
        if (Validation.validData(component4))
            components.append(component4).append("; ");
        String component5 = field861.getComponent5();
        if (Validation.validData(component5))
            components.append(component5).append("; ");
        String component6 = field861.getComponent6();
        if (Validation.validData(component6))
            components.append(component6);
    }

    public List<SwiftMt940> getByAccountId(String accountId) {
        return mt940Repo.getAllByAccIdentification(accountId);
    }

    public Page<SwiftMt940> getAll(Pageable pageable) {
        return mt940Repo.findAllMessages(pageable);
    }

    public Page<SwiftMt940> search(SearchRequest request, Pageable pageable) {
        return searchRepo.searchMt940(request, pageable);
    }
}