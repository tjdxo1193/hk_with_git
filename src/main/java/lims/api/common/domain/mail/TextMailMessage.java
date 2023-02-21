package lims.api.common.domain.mail;

import lombok.Builder;
import lombok.ToString;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Builder
@ToString
public class TextMailMessage implements MailMessage {
    private String address;
    private String subject;
    private String text;
    private boolean htmlType;

    @Override
    public void write(MimeMessage message) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(address);
        helper.setSubject(subject);
        helper.setText(text, htmlType);
    }


    public static class TextMailMessageBuilder {
        private String text;
        private boolean htmlType;

        public TextMailMessageBuilder text(String text) {
            this.text = text;
            this.htmlType = false;
            return this;
        }

        public TextMailMessageBuilder text(String text, boolean htmlType) {
            this.text = text;
            this.htmlType = htmlType;
            return this;
        }
    }
}