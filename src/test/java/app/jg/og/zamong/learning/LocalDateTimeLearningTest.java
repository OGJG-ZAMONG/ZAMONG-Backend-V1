package app.jg.og.zamong.learning;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalDateTimeLearningTest {

    LocalDateTime beginDateTime;
    LocalDateTime endDateTime;

    @Test
    void test() {
        int beginDateTimeHour = 2;
        int endDateTimeHour = 12;

        beginDateTime = LocalDateTime.of(2021, 10, 7, beginDateTimeHour, 37);
        endDateTime = LocalDateTime.of(2021, 10, 7, endDateTimeHour, 37);

        Long different = ChronoUnit.HOURS.between(beginDateTime, endDateTime);
        assertThat(different).isEqualTo(endDateTimeHour - beginDateTimeHour);
    }

    @Test
    void plusHours() {
        beginDateTime = LocalDateTime.of(2021, 10, 7, 19, 37);

        assertThat(beginDateTime.plusHours(1).getHour()).isEqualTo(beginDateTime.getHour() + 1);
    }

    @Test
    void after() {
        beginDateTime = LocalDateTime.of(2021, 10, 7, 19, 37);
        endDateTime = LocalDateTime.now();

        assertThat(endDateTime.isAfter(beginDateTime)).isTrue();
    }

    @Test
    void before() {
        beginDateTime = LocalDateTime.of(2021, 10, 7, 19, 37);
        endDateTime = LocalDateTime.now();

        assertThat(beginDateTime.isBefore(endDateTime)).isTrue();
    }
}
