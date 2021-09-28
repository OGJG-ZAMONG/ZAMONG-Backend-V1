package app.jg.og.zamong.service.mail;

import app.jg.og.zamong.dto.request.SendMailRequest;
import app.jg.og.zamong.exception.externalinfra.MailSendFailedException;
import app.jg.og.zamong.service.mailtemplate.MailTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    private final MailTemplateService mailTemplateService;

    @Value("${spring.mail.username}")
    private String fromAddress;

    @Override
    public String sendEmail(SendMailRequest request) {
        try {
            sendMailLogic(request);
            return "ok";
        } catch (Exception e) {
            throw new MailSendFailedException("메일 전송에 실패하였습니다");
        }
    }

    private void sendMailLogic(SendMailRequest request) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, false, "UTF-8");

        messageHelper.setTo(request.getAddress());
        messageHelper.setFrom(fromAddress);
        messageHelper.setSubject(request.getTitle());

        final String text = getFormattedString(request.getAuthenticationCode().split(""));
        final boolean isHTML = true;

        messageHelper.setText(text, isHTML);
        mailSender.send(message);
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
