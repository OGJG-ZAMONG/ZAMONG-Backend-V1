package app.jg.og.zamong.entity.user;

import app.jg.og.zamong.entity.dream.Dream;
import app.jg.og.zamong.entity.follow.Follow;
import app.jg.og.zamong.entity.user.profile.Profile;
import lombok.*;
import lombok.experimental.Delegate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
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

    @Column(length = 16)
    private String id;

    private String password;

    private Integer lucyCount;

    @OneToMany(mappedBy = "user")
    private List<Dream> dreams;

    @OneToMany(mappedBy = "following")
    private List<Follow> followers;

    @OneToMany(mappedBy = "follower")
    private List<Follow> followings;

    @Getter(value = AccessLevel.NONE)
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    @Delegate
    private Profile profile;
}
