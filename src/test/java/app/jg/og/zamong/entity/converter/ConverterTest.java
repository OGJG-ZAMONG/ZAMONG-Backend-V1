package app.jg.og.zamong.entity.converter;

import app.jg.og.zamong.entity.dream.attachment.converter.DreamTagConverter;
import app.jg.og.zamong.entity.dream.dreamtype.converter.DreamTypeConverter;
import app.jg.og.zamong.entity.dream.enums.DreamQuality;
import app.jg.og.zamong.entity.dream.enums.DreamTag;
import app.jg.og.zamong.entity.dream.enums.DreamType;
import app.jg.og.zamong.entity.dream.sharedream.converter.DreamQualityConverter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConverterTest {

    @Test
    void dreamTypeConverterTest() {
        DreamTypeConverter dreamTypeConverter = new DreamTypeConverter();

        assertThat(dreamTypeConverter.convertToDatabaseColumn(DreamType.TAEMONG)).isEqualTo(DreamType.TAEMONG.getCode());
        assertThat(dreamTypeConverter.convertToEntityAttribute(DreamType.TAEMONG.getCode())).isEqualTo(DreamType.TAEMONG);
    }

    @Test
    void dreamQualityConverterTest() {
        DreamQualityConverter dreamQualityConverter = new DreamQualityConverter();

        assertThat(dreamQualityConverter.convertToDatabaseColumn(DreamQuality.BEST)).isEqualTo(DreamQuality.BEST.getCode());
        assertThat(dreamQualityConverter.convertToEntityAttribute(DreamQuality.BEST.getCode())).isEqualTo(DreamQuality.BEST);
    }

    @Test
    void dreamTagConverterTest() {
        DreamTagConverter dreamTagConverter = new DreamTagConverter();

        assertThat(dreamTagConverter.convertToDatabaseColumn(DreamTag.SELL_DREAM)).isEqualTo(DreamTag.SELL_DREAM.getCode());
        assertThat(dreamTagConverter.convertToEntityAttribute(DreamTag.SELL_DREAM.getCode())).isEqualTo(DreamTag.SELL_DREAM);
    }
}
