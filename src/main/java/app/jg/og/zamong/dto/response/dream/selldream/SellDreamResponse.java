package app.jg.og.zamong.dto.response.dream.selldream;

import app.jg.og.zamong.entity.dream.enums.DreamType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class SellDreamResponse {

    private final UUID uuid;

    private final String defaultPostingImage;

    private final String title;

    private final List<DreamType> dreamTypes;

    private final LocalDateTime updatedAt;

    private final Integer cost;
}
