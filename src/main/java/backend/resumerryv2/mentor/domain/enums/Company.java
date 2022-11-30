/* Licensed under InfoCat */
package backend.resumerryv2.mentor.domain.enums;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Company {
  삼성전자(100, "삼성전자"),
  네이버(101, "네이버"),
  카카오(102, "카카오"),
  넥슨(103, "넥슨"),
  새회사(104, "새회사"),
  a회사(105, "a회사"),
  b회사(106, "b회사"),
  c회사(107, "c회사"),
  d회사(108, "d회사"),
  e회사(109, "e회사"),
  f회사(110, "f회사"),
  ;

  private final int code;
  private final String name;

  private static final Map<Integer, String> CODE_MAP =
      Collections.unmodifiableMap(
          Stream.of(values()).collect(Collectors.toMap(Company::getCode, Company::name)));

  public static Company of(final int code) {
    return Company.valueOf(CODE_MAP.get(code));
  }
}
