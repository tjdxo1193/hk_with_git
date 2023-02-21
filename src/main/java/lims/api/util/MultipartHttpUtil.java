package lims.api.util;

import com.google.gson.Gson;
import lims.api.common.message.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.Part;
import java.util.*;

@Slf4j
public class MultipartHttpUtil {

    public static <T> T getEAIParameter(MultipartHttpServletRequest request, Class<T> targetClazz) {
        printLog(request);
        return getParameter(request, "mainPayload", targetClazz);
    }

    public static <T> T getParameter(MultipartHttpServletRequest request, String parameterName, Class<T> targetClazz) {
        printLog(request);
        String json = request.getParameter(parameterName);
        return new Gson().fromJson(json, targetClazz);
    }

    public static List<MultipartFile> getFiles(MultipartHttpServletRequest request) {
        printLog(request);
        List<MultipartFile> files = new ArrayList<>();

        Iterator<String> names = request.getFileNames();
        while (names.hasNext()) {
            String name = names.next();
            MultipartFile file = request.getFile(name);
            files.add(file);
        }

        if (CollectionUtils.isEmpty(files)) {
            throw new IllegalArgumentException(MessageUtil.getMessage("interface.error.noExistsFile"));
        }

        return files;
    }

    private static void printLog(MultipartHttpServletRequest request) {
        try {
            log.info("============= Content-Type Logging =============");
            log.info("=============== [parameter info] =================");
            Enumeration<String> names = request.getParameterNames();
            while (names.hasMoreElements()) {
                String name = names.nextElement();
                log.info("name: {}, value: {}", name, request.getParameter(name));
            }

            log.info("=============== [part info] =================");
            Iterator<String> fileNames = request.getFileNames();
            while (fileNames.hasNext()) {
                String fileName = fileNames.next();
                MultipartFile file = request.getFile(fileName);
                log.info("name: {}, size: {}.", fileName, file.getSize());
            }
        } catch (Exception e) {
            log.error("Can't print Content-Type header.");
            e.printStackTrace();
        }
    }

}