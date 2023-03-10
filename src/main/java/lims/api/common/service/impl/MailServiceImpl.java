package lims.api.common.service.impl;

import lims.api.common.domain.mail.MailMessage;
import lims.api.common.properties.MailProperties;
import lims.api.common.service.MailService;
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
public class MailServiceImpl implements MailService {

    private final MailProperties mailProperties;
    private final JavaMailSenderImpl mailSender;

    public void send(MailMessage mailTarget) {
        mailSender.setHost(mailProperties.getHost());
        MimeMessage message = makeMessage(mailTarget);
        mailSender.send(message);
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