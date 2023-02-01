package lims.api.common.domain.mail;

import lims.api.common.enums.MailAttachType;
import lombok.Builder;
import lombok.Getter;

import java.io.File;

@Getter
@Builder
public class MailAttachFile {
    private String name;
    private File file;
    private MailAttachType attachType = MailAttachType.ATTACHMENT;
}