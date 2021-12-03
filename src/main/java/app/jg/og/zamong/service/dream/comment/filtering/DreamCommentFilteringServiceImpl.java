package app.jg.og.zamong.service.dream.comment.filtering;

import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DreamCommentFilteringServiceImpl implements DreamCommentFilteringService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    private String[] FILTERING_WORD_LIST;

    @Override
    public String filteringComment(String content) {
        int size = FILTERING_WORD_LIST.length;

        for(int i=0; i<size; i++) {
            String filterWord = FILTERING_WORD_LIST[i];

            if(content.contains(filterWord)) {
                int s = filterWord.length();
                content = content.replace(filterWord, "자" + "몽".repeat(s == 1 ? 1 : s - 1));
            }
        }

        return content;
    }

    @PostConstruct
    void postConstruct() throws IOException {
        String filteringWords = new String(this.amazonS3Client.getObject(bucket, "fword_list.txt").getObjectContent().readAllBytes());
        FILTERING_WORD_LIST = filteringWords.split("\n");
    }
}
