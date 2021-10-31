package app.jg.og.zamong.entity.dream.comment.recommend.converter;

import app.jg.og.zamong.entity.dream.comment.recommend.RecommendType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RecommendTypeConverter implements AttributeConverter<RecommendType, String> {

    @Override
    public String convertToDatabaseColumn(RecommendType attribute) {
        return attribute.getCode();
    }

    @Override
    public RecommendType convertToEntityAttribute(String dbData) {
        return RecommendType.find(dbData);
    }
}
