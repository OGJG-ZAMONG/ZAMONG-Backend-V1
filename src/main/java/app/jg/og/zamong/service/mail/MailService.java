package app.jg.og.zamong.service.mail;

import app.jg.og.zamong.dto.request.SendMailRequest;
import app.jg.og.zamong.dto.request.SendSimpleMailRequest;

public interface MailService {
    String sendEmail(SendMailRequest request);
    void sendSimpleEmail(SendSimpleMailRequest request);
}
