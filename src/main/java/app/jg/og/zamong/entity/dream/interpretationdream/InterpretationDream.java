package app.jg.og.zamong.entity.dream.interpretationdream;

import app.jg.og.zamong.entity.dream.Dream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Getter
@SuperBuilder
@AllArgsConstructor
@Entity
public class InterpretationDream extends Dream {
}
