package app.jg.og.zamong.learning;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalDateTimeLearningTest {

    LocalDateTime beginDateTime;
    LocalDateTime endDateTime;

    @Test
    void hoursDifferent() {
        int beginDateTimeHour = 2;
        int endDateTimeHour = 12;

        beginDateTime = LocalDateTime.of(2021, 10, 7, beginDateTimeHour, 37);
        endDateTime = LocalDateTime.of(2021, 10, 7, endDateTimeHour, 37);

        Long different1 = ChronoUnit.HOURS.between(beginDateTime, endDateTime);
        Long different2 = ChronoUnit.HOURS.between(endDateTime, beginDateTime);

        assertThat(different1).isEqualTo(endDateTimeHour - beginDateTimeHour);
        assertThat(different2).isEqualTo(beginDateTimeHour - endDateTimeHour);
    }

    @Test
    void plusHours() {
        beginDateTime = LocalDateTime.of(2021, 10, 7, 19, 37);

        assertThat(beginDateTime.plusHours(1).getHour()).isEqualTo(beginDateTime.getHour() + 1);
    }

    @Test
    void after() {
        beginDateTime = LocalDateTime.of(2020, 10, 7, 19, 37);
        endDateTime = LocalDateTime.now();

        assertThat(endDateTime.isAfter(beginDateTime)).isTrue();
    }

    @Test
    void before() {
        beginDateTime = LocalDateTime.of(2020, 10, 7, 19, 37);
        endDateTime = LocalDateTime.now();

        assertThat(beginDateTime.isBefore(endDateTime)).isTrue();
    }
}
