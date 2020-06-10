package com.unionbankng.swift.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

@Slf4j
public class Validation {

    public static String validateFile(String file, String type) {
        if (!validData(file)) {
            return "Please select another " + type;
        }
        return null;
    }

    public static boolean validData(Object obj) {
        if (obj == null || String.valueOf(obj).isEmpty()) {
            return false;
        }
        if (obj.equals("null")) {
            return false;
        }
        if (obj instanceof String) {
            String s = ((String) obj).trim();
            if (s.length() < 1) return false;
        }
        if (obj.equals(0))
            return false;
        return true;
    }

    public static boolean validName(String name) {

        if (name.length() < 2) {
            return false;
        }

        if (name.length() > 255) {
            return false;
        }

        //pure alphabets
        if (!(Pattern.compile("[a-zA-Z]+").matcher(name).matches())) {
            return false;
        }

        return true;
    }

    public static boolean validLength(String string, int min, int max) {

        if (!validData(string)) return false;

        if (string.length() < min) {
            return false;
        }

        if (max != 0) {
            if (string.length() > max) {
                return false;
            }
        }

        return true;
    }

    public static boolean validNumberLength(String numbers, int min, int max) {

        //pure number
        if (!validNumber(numbers)) {
            return false;
        }
        if (!validLength(numbers, min, max))
            return false;

        return true;
    }

    public static boolean validNumberLength(String numbers, int digits) {
        if (numbers == null)
            return false;

        //pure number
        if (!validNumber(numbers)) {
            return false;
        }

        if (!validLength(numbers, digits))
            return false;

        return true;
    }

    public static boolean validNumber(String numbers) {
        //pure number
        if (!(Pattern.compile("[0-9]+").matcher(numbers).matches())) {
            return false;
        }
        return true;
    }

    public static boolean validLength(String string, int digits) {

        if (string.length() < digits) {
            return false;
        }

        if (digits != 0) {
            if (string.length() > digits) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String character = "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";
        byte[] bytes = character.getBytes();
        System.out.println("The lenght of character " + bytes.length);
    }
}
