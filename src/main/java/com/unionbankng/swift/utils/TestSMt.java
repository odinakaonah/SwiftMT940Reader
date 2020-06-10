package com.unionbankng.swift.utils;

import com.prowidesoftware.swift.model.MtSwiftMessage;
import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.Field60F;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;
import org.apache.commons.codec.binary.StringUtils;

import java.io.IOException;

public class TestSMt {

    public void parseMessage(String fin) {
        AbstractMT msg = null;
        try {
            msg = AbstractMT.parse(fin);
            if (StringUtils.equals(msg.getServiceId(), "21") && msg.getSwiftMessage().getUnparsedTextsSize() > 0) {
                msg = msg.getSwiftMessage().getUnparsedTexts().getTextAsMessage(0).toMT();
            }
            String sender = msg.getSender();
            System.out.println("Sender " + sender);
            if (msg.isType(940)) {
                MT940 mt = (MT940) msg;
                // print the message reference
                System.out.println("Reference: " + mt.getField60F());
                // read components of a specific field
                Field60F f = mt.getField60F();
                System.out.println("Fild60F " + f);
//            Calendar date = f.getDateAsCalendar();
//            Number amount = f.getAmountAsNumber();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseSwift(String fin) {
        try {
            SwiftMessage sm = SwiftMessage.parse(fin);
            if (sm.isServiceMessage()) {
                sm = SwiftMessage.parse(sm.getUnparsedTexts().getAsFINString());
                SwiftBlock4 block4 = sm.getBlock4();
                String type = sm.getType();
//                System.out.println("Type "+ type);
//                if ("940".equals(type)) {
//                    MT940 mt940 = new MT940(sm);
//                    System.out.println("======== "+mt940.getField20());
//                }
                Tag tagByNumber = block4.getTagByNumber(20);
//                System.out.println("Tags values "); block4.getTags().parallelStream().forEach(tag -> System.out.println(tag.getValue()));
//                System.out.println("Block 4 "+block4.getTags());
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void parseSwiftMsg(String fin) {
        MtSwiftMessage msg = MtSwiftMessage.parse(fin);
        String sender = msg.getSender();
        String type = msg.getMessageType();
        System.out.println("Sender " + sender);
        System.out.println("Type " + type);
    }

}
