package lims.api.integration.vo.intergation;

import lims.api.util.ThreadUtil;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
@Builder
public class TempFile {
    private final File file;

    public File getFile() {
        return file;
    }

    public void delete() {
        boolean isDeletedFile = file.delete();
        if (!isDeletedFile) {
            log.warn("[{}] No deleted temporary file. file: {}", ThreadUtil.getCurrentMethodName(), file.getAbsolutePath());
        }
    }
}