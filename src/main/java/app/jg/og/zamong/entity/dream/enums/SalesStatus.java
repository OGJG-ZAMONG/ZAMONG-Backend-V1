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
public enum SalesStatus {

    PENDING("PEND", "판매중"),
    DONE("DONE", "판매완료"),
    CANCEL("CANC", "판매취소");

    private final String code;

    private final String value;

    private static final Map<String, SalesStatus> tags =
            Collections.unmodifiableMap(Arrays.stream(SalesStatus.values())
                    .collect(Collectors.toMap(SalesStatus::getCode, Function.identity())));

    public static SalesStatus find(String code) {
        return Optional.ofNullable(tags.get(code))
                .orElseThrow(() -> new AttributeConvertFailedException("Attribute Convert Failed " + code));
    }
}
