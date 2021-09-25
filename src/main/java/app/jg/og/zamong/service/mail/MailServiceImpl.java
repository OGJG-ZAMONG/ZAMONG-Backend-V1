package app.jg.og.zamong.service.mail;

import app.jg.og.zamong.dto.request.SendMailRequest;
import app.jg.og.zamong.service.mailtemplate.MailTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    private final MailTemplateService mailTemplateService;

    @Value("${spring.mail.username}")
    private String fromAddress;

    @Override
    public String sendEmail(SendMailRequest request) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(request.getAddress());
        message.setFrom(fromAddress);
        message.setSubject(request.getTitle());
        message.setText(getFormattedString(request.getAuthenticationCode().split("")));

        mailSender.send(message);

        return "ok";
    }

    private String getFormattedString(String[] codes) {
        return String.format(mailTemplateService.createHTMLTemplate(),
                codes[0],
                codes[1],
                codes[2],
                codes[3],
                codes[4],
                codes[5]
        );
    }
}
