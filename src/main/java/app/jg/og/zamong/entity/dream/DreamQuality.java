package app.jg.og.zamong.entity.dream;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DreamQuality {

    BEST("BST"),
    GOOD("GD"),
    SOSO("SO"),
    BAD("BD"),
    WORST("WST");

    @JsonValue
    private final String value;
}
