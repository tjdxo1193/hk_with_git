package lims.api.common.controller;

import lims.api.auth.annotation.Permit;
import lims.api.common.domain.mail.TextMailMessage;
import lims.api.common.service.impl.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${apiPrefix}/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @Permit
    @GetMapping("send-test")
    public void sendTest() {
        TextMailMessage message = TextMailMessage.builder()
                .address("egjeong@intefaceit.co.kr")
                .subject("메일 발송 테스트")
                .text("내용", true)
                .build();
        mailService.send(message);
    }

}