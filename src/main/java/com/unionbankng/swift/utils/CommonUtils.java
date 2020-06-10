package com.unionbankng.swift.utils;

import com.unionbankng.swift.SwiftException;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Henry on 16/07/2019.
 */
@Slf4j
public final class CommonUtils {
    
    private CommonUtils() {}
    public static String  reconstructDate(String date) {
        SimpleDateFormat fromUser = new SimpleDateFormat("yyMMdd");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String reformattedStr = null;
        try {
            if (Validation.validData(date))
             reformattedStr = myFormat.format(fromUser.parse(date));
            return reformattedStr;
        } catch (ParseException e) {
            log.error("ParseException Error occured ",e);
            return date;
        }

    }

    public static LocalDate startOfDay(String  date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate from = LocalDate.parse(date,formatter);

        return from;
    }

    public static LocalDate endOfDay(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate to = LocalDate.parse(date,formatter);

        return to;
    }
    public static String buildUniqueId(String fName,String lName){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");

        String format = LocalDate.now().format(formatter);
        return fName.substring(0,1).toUpperCase()+lName.substring(0,2).toUpperCase()+format;
    }

    public static LocalDateTime dateTimeFormat(String date) throws SwiftException {
        String format = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        if (!Validation.validData(date))
            throw new SwiftException(400,"Invalid date ");
        try{
            return LocalDateTime.parse(date, formatter);
        }catch (Exception e)
        {
            log.error("",e);
            throw new SwiftException(400,e.getMessage()+" Valid date format "+format);
        }
    }
    public static void main(String[] args) {
//        String s = reconstructDate("201131");
//        System.out.println(s);
        String kl = "0990";
        String s1 = kl.substring(0, 2) + "-" + kl.substring(2, 4);
        System.out.println(s1);
    }
}
