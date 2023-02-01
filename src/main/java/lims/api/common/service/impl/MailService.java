package lims.api.common.service.impl;

import lims.api.common.domain.mail.AttachMailMessage;
import lims.api.common.domain.mail.MailMessage;
import lims.api.common.domain.mail.TextMailMessage;
import lims.api.common.properties.MailProperties;
import lims.api.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final MailProperties mailProperties;
    private final JavaMailSenderImpl mailSender;

    public void send(MailMessage mailTarget) {
        mailSender.setHost(mailProperties.getHost());
        log.info("----- send mail info -----");
        log.info(mailSender.toString());
        mailSender.send(makeMessage(mailTarget));
    }

    private MimeMessage makeMessage(MailMessage mailTarget) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            mailTarget.write(message);
            return message;
        } catch (MessagingException e) {
            log.error("[{}] Failed make mail message. {}", ThreadUtil.getCurrentMethodName(), mailTarget);
            throw new RuntimeException(e);
        }
    }
}