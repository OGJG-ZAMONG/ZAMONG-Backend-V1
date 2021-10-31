package app.jg.og.zamong.util;

import app.jg.og.zamong.constant.DreamConstant;
import app.jg.og.zamong.entity.dream.Dream;
import app.jg.og.zamong.entity.dream.comment.Comment;

import java.util.List;
import java.util.UUID;

public class DreamBuilder {

    public static Dream build() {
        UUID dreamId = UUID.randomUUID();

        return new Dream() {
            @Override
            public UUID getUuid() {
                return dreamId;
            }

            @Override
            public List<Comment> getComments() {
                return List.of(Comment.builder()
                        .content(DreamConstant.CONTENT)
                        .depth(0)
                        .user(UserBuilder.build())
                        .build());
            }
        };
    }
}
