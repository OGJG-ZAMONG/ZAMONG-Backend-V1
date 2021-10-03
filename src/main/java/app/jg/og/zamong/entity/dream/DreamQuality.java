package app.jg.og.zamong.entity.dream;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DreamQuality {

    BST("BEST"),
    GD("GOOD"),
    SO("SOSO"),
    BD("BAD"),
    WST("WORST");

    private final String code;
}
