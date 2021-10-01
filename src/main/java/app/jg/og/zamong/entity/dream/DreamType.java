package app.jg.og.zamong.entity.dream;

import lombok.AllArgsConstructor;
import lombok.Getter;

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

    @Override
    public String toString() {
        return value;
    }
}
