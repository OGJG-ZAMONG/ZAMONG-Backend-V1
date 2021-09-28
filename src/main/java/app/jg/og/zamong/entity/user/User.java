package app.jg.og.zamong.entity.user;

import app.jg.og.zamong.entity.dream.Dream;
import lombok.*;
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

    @OneToMany(mappedBy = "user")
    private List<Dream> dreams;
}
