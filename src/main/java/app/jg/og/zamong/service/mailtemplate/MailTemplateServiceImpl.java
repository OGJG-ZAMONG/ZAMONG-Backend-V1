package app.jg.og.zamong.service.mailtemplate;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MailTemplateServiceImpl implements MailTemplateService {

    private static String template = null;

    static {
        try {
            byte[] bytes = new ClassPathResource("static/email_template.html").getInputStream().readAllBytes();
            template = new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String createHTMLTemplate() {
        return template;
    }
}
