package app.jg.og.zamong.service.mail;

import app.jg.og.zamong.dto.request.SendMailRequest;

public interface MailService {
    String sendEmail(SendMailRequest request);
}
