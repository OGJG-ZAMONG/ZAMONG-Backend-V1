package app.jg.og.zamong.util;

import app.jg.og.zamong.constant.DreamConstant;
import app.jg.og.zamong.entity.dream.Dream;

import java.util.UUID;

public class DreamBuilder {

    public static Dream build() {
        return Dream.builder()
                .uuid(UUID.randomUUID())
                .title(DreamConstant.TITLE)
                .content(DreamConstant.CONTENT)
                .build();
    }
}
