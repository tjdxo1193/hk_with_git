package lims.api.integration.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum FileType {
    PPT("ppt"),
    PDF("pdf"),
    DOC("doc"),
    DOCX("docx"),
    XLSX("xlsx"),
    XLS("xls"),
    CSV("csv");

    private final String extension;

    FileType(String extension) {
        this.extension = extension;
    }

}