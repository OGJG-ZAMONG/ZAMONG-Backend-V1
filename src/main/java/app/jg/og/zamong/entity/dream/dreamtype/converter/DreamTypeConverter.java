package app.jg.og.zamong.entity.dream.dreamtype.converter;

import app.jg.og.zamong.entity.dream.enums.DreamType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DreamTypeConverter implements AttributeConverter<DreamType, String> {

    @Override
    public String convertToDatabaseColumn(DreamType attribute) {
        return attribute.getCode();
    }

    @Override
    public DreamType convertToEntityAttribute(String dbData) {
        return DreamType.find(dbData);
    }
}
