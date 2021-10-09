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
public enum DreamTag {

    SHARE_DREAM("SHAR", "공유꿈"),
    INTERPRETATION_DREAM("IPRE", "해몽꿈"),
    SELL_DREAM("SEL", "판매꿈");

    private final String code;

    private final String value;

    private static final Map<String, DreamTag> tags =
            Collections.unmodifiableMap(Arrays.stream(DreamTag.values())
                    .collect(Collectors.toMap(DreamTag::getCode, Function.identity())));

    public static DreamTag find(String code) {
        return Optional.ofNullable(tags.get(code))
                .orElseThrow(() -> new AttributeConvertFailedException("Attribute Convert Failed " + code));
    }
}
