package app.jg.og.zamong.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    private UUID uuid;

    @Column(length = 20)
    private String name;

    private String email;

    @Column(length = 16)
    private String id;

    private String password;
}
