package app.jg.og.zamong.entity.dream.attachment.converter;

import app.jg.og.zamong.entity.dream.enums.DreamTag;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DreamTagConverter implements AttributeConverter<DreamTag, String> {

    @Override
    public String convertToDatabaseColumn(DreamTag attribute) {
        return attribute.getCode();
    }

    @Override
    public DreamTag convertToEntityAttribute(String dbData) {
        return DreamTag.find(dbData);
    }
}
