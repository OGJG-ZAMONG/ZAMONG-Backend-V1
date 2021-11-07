package app.jg.og.zamong.dto.response.dream.interpretationdream;

import app.jg.og.zamong.dto.response.Response;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class InterpretationDreamCategoryResponse implements Response {

    private final List<InterpretationDream> interpretationDreams;

    @Builder
    @Getter
    public static class InterpretationDream {

        private final UUID uuid;

        private final String dreamName;
    }
}
