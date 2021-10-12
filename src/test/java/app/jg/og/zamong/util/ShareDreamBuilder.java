package app.jg.og.zamong.util;

import app.jg.og.zamong.constant.DreamConstant;
import app.jg.og.zamong.entity.dream.attachment.AttachmentImage;
import app.jg.og.zamong.entity.dream.sharedream.ShareDream;
import app.jg.og.zamong.entity.user.User;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ShareDreamBuilder {

    public static ShareDream build(User user) {
        return ShareDream.builder()
                .uuid(UUID.randomUUID())
                .title(DreamConstant.TITLE)
                .content(DreamConstant.CONTENT)
                .user(user)
                .dreamTypes(Collections.emptyList())
                .shareDreamLucyPoints(Collections.emptyList())
                .attachmentImages(List.of(AttachmentImage.builder()
                        .host(DreamConstant.ATTACHMENT_HOST)
                        .path(DreamConstant.ATTACHMENT_PATH)
                        .build()))
                .quality(DreamConstant.QUALITY)
                .isShared(DreamConstant.IS_SHARED)
                .shareDateTime(DreamConstant.SLEEP_DATE_TIME)
                .sleepTime(5)
                .build();
    }
}
