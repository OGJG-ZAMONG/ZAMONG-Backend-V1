package app.jg.og.zamong.entity.dream;

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

    BEST("BST"),
    GOOD("GD"),
    SOSO("SO"),
    BAD("BD"),
    WORST("WST");

    private final String code;

    private static final Map<String, DreamQuality> qualities =
            Collections.unmodifiableMap(Arrays.stream(DreamQuality.values())
                    .collect(Collectors.toMap(DreamQuality::getCode, Function.identity())));

    public static DreamQuality find(String code) {
        return Optional.ofNullable(qualities.get(code))
                .orElseThrow(() -> new AttributeConvertFailedException("Attribute Convert Failed " + code));
    }
}
