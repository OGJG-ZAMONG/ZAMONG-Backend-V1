package app.jg.og.zamong.entity.dream.comment.recommend;

import app.jg.og.zamong.exception.externalinfra.AttributeConvertFailedException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum RecommendType {

    DISLIKE("DLK", "싫어요"),
    LIKE("LK", "좋아요");

    private final String code;

    private final String value;

    private static final Map<String, RecommendType> recommendTypes =
            Collections.unmodifiableMap(Arrays.stream(RecommendType.values())
                    .collect(Collectors.toMap(RecommendType::getCode, Function.identity())));

    public static RecommendType find(String code) {
        return Optional.ofNullable(recommendTypes.get(code))
                .orElseThrow(() -> new AttributeConvertFailedException("Attribute Convert Failed " + code));
    }
}
