package app.jg.og.zamong.entity.user.profile;

import app.jg.og.zamong.entity.user.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Entity
public class Profile {

    @Id
    private UUID uuid;

    private String host;

    private String path;

    @MapsId("uuid")
    @OneToOne
    @JoinColumn(name = "uuid", nullable = false)
    private User user;

    public String getProfile() {
        return host + path;
    }
}
