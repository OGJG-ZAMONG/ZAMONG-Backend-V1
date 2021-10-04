package app.jg.og.zamong.entity.dream.enums;

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
public enum DreamQuality {

    BEST("BST", "아주 좋아요"),
    GOOD("GD", "재밌어요"),
    SOSO("SO", "그저 그래요"),
    BAD("BD", "안좋아요"),
    WORST("WST", "아주 안좋아요");

    private final String code;

    private final String value;

    private static final Map<String, DreamQuality> qualities =
            Collections.unmodifiableMap(Arrays.stream(DreamQuality.values())
                    .collect(Collectors.toMap(DreamQuality::getCode, Function.identity())));

    public static DreamQuality find(String code) {
        return Optional.ofNullable(qualities.get(code))
                .orElseThrow(() -> new AttributeConvertFailedException("Attribute Convert Failed " + code));
    }
}
