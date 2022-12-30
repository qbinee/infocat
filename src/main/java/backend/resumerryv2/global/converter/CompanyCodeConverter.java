package backend.resumerryv2.global.converter;

import backend.resumerryv2.category.domain.enums.Company;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

@Convert
public class CompanyCodeConverter implements AttributeConverter<Company, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Company company) {
        if (company == null) {
            throw new IllegalArgumentException("Field값이 null 입니다.");
        }
        return company.getCode();
    }

    @Override
    public Company convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            throw new IllegalArgumentException("db Data값이 null 입니다.");
        }
        return Company.of(dbData);
    }
}
