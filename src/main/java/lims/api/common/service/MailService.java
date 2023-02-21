package lims.api.common.service;

import lims.api.common.domain.mail.MailMessage;

public interface MailService {

    void send(MailMessage mailTarget);

}