package app.jg.og.zamong.constant;

import app.jg.og.zamong.entity.dream.enums.DreamQuality;

import java.time.LocalDateTime;

public class DreamConstant {

    public static final String TITLE = "title";
    public static final String CONTENT = "content";

    public static final DreamQuality QUALITY = DreamQuality.SOSO;
    public static final Boolean IS_SHARED = false;
    public static final LocalDateTime SLEEP_DATE_TIME = LocalDateTime.now();
    public static final Integer SLEEP_TIME = 4;
}
