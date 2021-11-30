package app.jg.og.zamong.controller;

import app.jg.og.zamong.service.dream.comment.filtering.DreamCommentFilteringService;
import com.amazonaws.services.s3.AmazonS3Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Disabled
public class IntegrationTest {
    @Autowired protected MockMvc mockMvc;
    @Autowired protected ObjectMapper objectMapper;

    @MockBean protected AmazonS3Client amazonS3Client;
    @MockBean protected DreamCommentFilteringService dreamCommentFilteringService;
}
