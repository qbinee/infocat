/* Licensed under InfoCat */
package backend.resumerryv2.util.domain.enums;

import backend.resumerryv2.exception.CustomException;
import backend.resumerryv2.exception.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Company {
  삼성전자(100, "아주대학교", "ajou.ac.kr"),
  네이버(101, "네이버", "naver.com"),
  카카오(102, "구글", "gmail.com"),
  넥슨(103, "넥슨", "nexon.com"),
  새회사(104, "새회사", "newCompany.com"),
  a회사(105, "a회사", "a.com"),
  b회사(106, "b회사", " b.com"),
  c회사(107, "c회사", "c.com"),
  d회사(108, "d회사", "d.com"),
  e회사(109, "e회사", "e.com"),
  f회사(110, "f회사", "f.com"),
  EMPTY(999, "새회사", "empty.com")
  ;

  private final Integer code;
  private final String name;
  private final String email;

  private static final Map<Integer, String> CODE_MAP =
      Collections.unmodifiableMap(
          Stream.of(values()).collect(Collectors.toMap(Company::getCode, Company::name)));

  private static final Map<String, String> EMAIL_MAP =
          Collections.unmodifiableMap(
                  Stream.of(values()).collect(Collectors.toMap(Company::getEmail, Company::name)));

  public static Company of(Integer code) {return Company.valueOf(CODE_MAP.get(code));}

  public static Company of(String email) {
    return Arrays.stream(Company.values())
            .filter(company -> company.email.equals(email))
            .findAny()
            .orElse(EMPTY);
  }

}
