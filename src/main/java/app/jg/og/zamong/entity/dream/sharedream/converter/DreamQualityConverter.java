package app.jg.og.zamong.entity.dream.sharedream.converter;

import app.jg.og.zamong.entity.dream.DreamQuality;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DreamQualityConverter implements AttributeConverter<DreamQuality, String> {

    @Override
    public String convertToDatabaseColumn(DreamQuality attribute) {
        return attribute.getCode();
    }

    @Override
    public DreamQuality convertToEntityAttribute(String dbData) {
        return DreamQuality.find(dbData);
    }
}
