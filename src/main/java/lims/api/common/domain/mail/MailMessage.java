package lims.api.common.domain.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public interface MailMessage {

    void write(MimeMessage message) throws MessagingException;

}