package app.jg.og.zamong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ZamongApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZamongApplication.class, args);
    }

}
