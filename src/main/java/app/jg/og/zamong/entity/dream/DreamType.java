package app.jg.og.zamong.entity.dream;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DreamType {

    LCD("LUCID_DREAM", "루시드 드림"),
    NTM("NIGHTMARE", "악몽"),
    AUS("AUSPICIOUS", "길몽"),
    PRS("PARALYSIS", "가위눌림"),
    FAW("FALSE_AWAKE", "거짓깨어남"),
    TMG("TAEMONG", "태몽"),
    WDR("WET_DREAM", "몽정"),
    HDR("HOLY_DREAM", "영몽");

    private final String code;

    @JsonValue
    private final String value;
}
