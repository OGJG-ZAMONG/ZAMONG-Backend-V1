package app.jg.og.zamong.entity.dream;

import app.jg.og.zamong.entity.dream.enums.DreamType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DreamTypeTest {

    @Test
    void 꿈유형_JSON_변경() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        InnerDream dream = new InnerDream();
        String name = "myDream";
        DreamType dreamType = DreamType.NIGHTMARE;

        dream.setName(name);
        dream.setDreamType(dreamType);

        String jsonString = mapper.writeValueAsString(dream);

        assertThat(jsonString).isEqualTo("{\"name\":\"" + name + "\",\"dreamType\":\"" + dreamType.name() + "\"}");
    }

    static class InnerDream {
        private String name;
        private DreamType dreamType;

        public void setName(String name) {
            this.name = name;
        }

        public void setDreamType(DreamType dreamType) {
            this.dreamType = dreamType;
        }

        public String getName() {
            return name;
        }

        public DreamType getDreamType() {
            return dreamType;
        }
    }
}
