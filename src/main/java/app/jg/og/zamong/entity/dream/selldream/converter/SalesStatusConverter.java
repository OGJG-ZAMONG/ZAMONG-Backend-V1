package app.jg.og.zamong.entity.dream.selldream.converter;

import app.jg.og.zamong.entity.dream.enums.SalesStatus;

import javax.persistence.AttributeConverter;

public class SalesStatusConverter implements AttributeConverter<SalesStatus, String> {

    @Override
    public String convertToDatabaseColumn(SalesStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public SalesStatus convertToEntityAttribute(String dbData) {
        return SalesStatus.find(dbData);
    }
}
