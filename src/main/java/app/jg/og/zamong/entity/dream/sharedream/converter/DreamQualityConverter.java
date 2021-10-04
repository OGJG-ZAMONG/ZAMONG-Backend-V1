package app.jg.og.zamong.entity.dream.sharedream.converter;

import app.jg.og.zamong.entity.dream.DreamQuality;
import app.jg.og.zamong.exception.externalinfra.AttributeConvertFailedException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class DreamQualityConverter implements AttributeConverter<DreamQuality, String> {

    @Override
    public String convertToDatabaseColumn(DreamQuality attribute) {
        return attribute.name();
    }

    @Override
    public DreamQuality convertToEntityAttribute(String dbData) {
        return Arrays.stream(DreamQuality.values())
                .filter(dq -> dq.getCode().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new AttributeConvertFailedException("Attribute Convert Failed " + dbData));
    }
}
