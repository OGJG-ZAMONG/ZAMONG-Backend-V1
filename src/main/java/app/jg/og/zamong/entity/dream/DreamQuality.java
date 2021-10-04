package app.jg.og.zamong.entity.dream;

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

    private final String code;
}
