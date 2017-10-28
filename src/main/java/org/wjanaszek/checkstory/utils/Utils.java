package org.wjanaszek.checkstory.utils;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class Utils {

    public static String getBase64EncodeImage(String path) {
        File file = null;
        try {
            file = ResourceUtils.getFile(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String encode = null;
        try {
            encode = Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encode;
    }
}
