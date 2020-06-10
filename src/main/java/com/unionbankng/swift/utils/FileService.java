package com.unionbankng.swift.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

public class FileService {


    public static void moveReadFileToBackUpFolder(File file,String newFolder) {

                // renaming the file and moving it to a new location
                boolean moved = file.renameTo(new File(newFolder + "/read_" + file.getName()));
                if (moved) {
                    // if file copied successfully then delete the original file
                    file.delete();
                    System.out.println("File moved successfully");
                } else {
                    System.out.println("Failed to move the file.... trying forced delete");
                    try {
                        moveAndDeleteFile(file,newFolder);
                    } catch (IOException e) {
                        System.out.println("IOException "+ e);
                    }
                }
    }
    public static boolean moveAndDeleteFile(File tempFile, String newFolder) throws IOException {
        File destFile = new File(newFolder+tempFile.getName());


        FileUtils.copyFile(tempFile, destFile);
        try {
            FileUtils.forceDelete(tempFile);
            System.out.println("Force delete  successful!!!!");
        } catch (Exception e) {
            System.out.println("Error description"+ e);
            FileUtils.forceDeleteOnExit(tempFile);
        }
        return true;
    }

}
