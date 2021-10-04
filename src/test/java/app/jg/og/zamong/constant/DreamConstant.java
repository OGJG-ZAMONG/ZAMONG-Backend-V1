package app.jg.og.zamong.constant;

import app.jg.og.zamong.entity.dream.enums.DreamQuality;

import java.util.Date;

public class DreamConstant {

    public static final String TITLE = "title";
    public static final String CONTENT = "content";

    public static final DreamQuality QUALITY = DreamQuality.SOSO;
    public static final Boolean IS_SHARED = false;
    public static final Date SLEEP_DATE_TIME = new Date(System.currentTimeMillis());
    public static final Integer SLEEP_TIME = 4;
}
