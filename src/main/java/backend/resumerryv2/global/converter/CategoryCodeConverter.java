package backend.resumerryv2.global.converter;

import backend.resumerryv2.util.domain.enums.Category;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

@Convert
public class CategoryCodeConverter implements AttributeConverter<Category, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category값이 null 입니다.");
        }
        return category.getCode();
    }

    @Override
    public Category convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            throw new IllegalArgumentException("db Data값이 null 입니다.");
        }
        return Category.of(dbData);
    }
}
