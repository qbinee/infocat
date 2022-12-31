/* Licensed under InfoCat */
package backend.resumerryv2.global.converter;

import backend.resumerryv2.category.domain.enums.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RoleCodeConverter implements AttributeConverter<Role, Integer> {
  @Override
  public Integer convertToDatabaseColumn(Role role) {
    if (role == null) {
      throw new IllegalArgumentException("Category값이 null 입니다.");
    }
    return role.getCode();
  }

  @Override
  public Role convertToEntityAttribute(Integer dbData) {
    if (dbData == null) {
      throw new IllegalArgumentException("db Data값이 null 입니다.");
    }
    return Role.of(dbData);
  }
}
