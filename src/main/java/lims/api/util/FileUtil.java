package lims.api.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@Slf4j
public class FileUtil {

    public static byte[] getBase64Bytes(File file) {
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encode(bytes);
        } catch (IOException e) {
            log.error("[{}] Failed convert to byte array from file. {}", ThreadUtil.getCurrentMethodName(), file.getAbsolutePath());
            throw new RuntimeException(e.getCause());
        }
    }

    public static byte[] toBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch(IOException e) {
            log.error("[{}] Failed convert multipart file to BLOB", ThreadUtil.getCurrentMethodName());
            throw new RuntimeException(e.getMessage());
        }
    }

    public static byte[] toBytes(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            log.error("[{}] Failed convert file to BLOB. {}", ThreadUtil.getCurrentMethodName(), file.getName());
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String getName(File file) {
        return FilenameUtils.getName(StringUtil.decodeUTF8(file.getName()));
    }

    public static String getName(MultipartFile file) {
        return FilenameUtils.getName(StringUtil.decodeUTF8(file.getOriginalFilename()));
    }

    public static String getExtension(String name) {
        return FilenameUtils.getExtension(name);
    }

    public static String concat(String path, String... others) {
        if (others == null || others.length == 0) {
            return path;
        }
        StringBuilder pathBuilder = new StringBuilder(path);
        for (String other : others) {
            pathBuilder.append(File.separator).append(other);
        }
        return pathBuilder.toString();
    }

}