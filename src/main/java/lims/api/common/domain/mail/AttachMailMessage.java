package lims.api.common.domain.mail;

import lims.api.common.enums.MailAttachType;
import lims.api.util.FileUtil;
import lombok.Builder;
import lombok.ToString;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

@Builder
@ToString
public class AttachMailMessage implements MailMessage {
    private String address;
    private String subject;
    private String text;
    private boolean htmlType;
    private List<MailAttachFile> files;

    @Override
    public void write(MimeMessage message) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(address);
        helper.setSubject(subject);
        helper.setText(text, htmlType);
        setFiles(helper);
    }

    private void setFiles(MimeMessageHelper helper) throws MessagingException {
        for (MailAttachFile attachFile : files) {
            FileSystemResource resource = new FileSystemResource(attachFile.getFile());

            if (attachFile.getAttachType() == MailAttachType.INLINE) {
                helper.addInline(attachFile.getName(), resource);
            } else {
                helper.addAttachment(attachFile.getName(), resource);
            }
        }
    }


    public static class AttachMailMessageBuilder {
        private String text;
        private boolean htmlType;
        private List<MailAttachFile> files;

        public void text(String text) {
            this.text = text;
            this.htmlType = false;
        }

        public void text(String text, boolean htmlType) {
            this.text = text;
            this.htmlType = htmlType;
        }

        public AttachMailMessageBuilder file(File file) {
            file(FileUtil.getName(file), file, MailAttachType.ATTACHMENT);
            return this;
        }

        public AttachMailMessageBuilder file(File file, MailAttachType attachType) {
            file(FileUtil.getName(file), file, attachType);
            return this;
        }

        public AttachMailMessageBuilder file(String name, File file) {
            file(name, file, MailAttachType.ATTACHMENT);
            return this;
        }

        public AttachMailMessageBuilder file(String name, File file, MailAttachType attachType) {
            MailAttachFile attachFile = MailAttachFile.builder()
                    .name(name)
                    .file(file)
                    .attachType(attachType)
                    .build();
            this.files = List.of(attachFile);
            return this;
        }

        public AttachMailMessageBuilder file(MailAttachFile file) {
            file(List.of(file));
            return this;
        }

        public AttachMailMessageBuilder file(List<MailAttachFile> files) {
            this.files = files;
            return this;
        }
    }
}