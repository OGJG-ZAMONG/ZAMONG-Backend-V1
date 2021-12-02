package app.jg.og.zamong.entity.user;

import app.jg.og.zamong.entity.dream.Dream;
import app.jg.og.zamong.entity.follow.Follow;
import app.jg.og.zamong.entity.user.profile.Profile;
import app.jg.og.zamong.exception.business.NotSharedException;
import lombok.*;
import lombok.experimental.Delegate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@EqualsAndHashCode(of = {"uuid"})
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @Column(length = 20)
    private String name;

    private String email;

    @Setter
    @Column(length = 16)
    private String id;

    @Setter
    private String password;

    private Integer lucyCount;

    @OneToMany(mappedBy = "user")
    private List<Dream> dreams;

    @OneToMany(mappedBy = "following")
    private List<Follow> followers;

    @OneToMany(mappedBy = "follower")
    private List<Follow> followings;

    @Setter
    @Getter(value = AccessLevel.NONE)
    @OneToOne(mappedBy = "user", optional = false)
    @Delegate
    private Profile profile;

    public void increaseLucy(Integer lucy) {
        this.lucyCount += lucy;
    }

    public void decreaseLucy(Integer lucy) {
        if(this.lucyCount - lucy < 0) {
            throw new NotSharedException("루시 개수가 충분하지 않습니다");
        }
        this.lucyCount -= lucy;
    }
}
