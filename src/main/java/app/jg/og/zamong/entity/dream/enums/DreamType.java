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
public enum DreamType {

    LUCID_DREAM("LCD", "루시드 드림"),
    NIGHTMARE("NTM", "악몽"),
    AUSPICIOUS("AUS", "길몽"),
    PARALYSIS("PRS", "가위눌림"),
    FALSE_AWAKE("FAW", "거짓깨어남"),
    TAEMONG("TMG", "태몽"),
    WET_DREAM("WDR", "몽정"),
    HOLY_DREAM("HDR", "영몽");

    private final String code;

    private final String value;

    private static final Map<String, DreamType> types =
            Collections.unmodifiableMap(Arrays.stream(DreamType.values())
                    .collect(Collectors.toMap(DreamType::getCode, Function.identity())));

    public static DreamType find(String code) {
        return Optional.ofNullable(types.get(code))
                .orElseThrow(() -> new AttributeConvertFailedException("Attribute Convert Failed " + code));
    }
}
