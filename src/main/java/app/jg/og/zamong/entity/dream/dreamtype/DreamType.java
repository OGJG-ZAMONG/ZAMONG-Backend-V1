package app.jg.og.zamong.entity.dream.dreamtype;

import app.jg.og.zamong.entity.dream.Dream;
import app.jg.og.zamong.entity.dream.dreamtype.converter.DreamTypeConverter;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "dream_type")
public class DreamType {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @Convert(converter = DreamTypeConverter.class)
    @Column(length = 4, columnDefinition = "char(4)")
    private app.jg.og.zamong.entity.dream.enums.DreamType code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dream_uuid", nullable = false)
    private Dream dream;
}
