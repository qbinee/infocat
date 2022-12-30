/* Licensed under InfoCat */
package backend.resumerryv2.global.converter;

import backend.resumerryv2.category.domain.enums.Field;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

@Convert
public class FieldCodeConverter implements AttributeConverter<Field, Integer> {
  @Override
  public Integer convertToDatabaseColumn(Field field) {
    if (field == null) {
      throw new IllegalArgumentException("Field값이 null 입니다.");
    }
    return field.getCode();
  }

  @Override
  public Field convertToEntityAttribute(Integer dbData) {
    if (dbData == null) {
      throw new IllegalArgumentException("db Data값이 null 입니다.");
    }
    return Field.of(dbData);
  }
}
