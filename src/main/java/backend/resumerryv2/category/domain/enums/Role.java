/* Licensed under InfoCat */
package backend.resumerryv2.category.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Role {
  SW_ENGINEER(101, "SW 엔지니어"),
  QA(102, "QA 엔지니어"),
  FRONTEND(103, "프론트엔드 엔지니어"),
  BACKEND(104, "서버 엔지니어"),
  DATA_SCIENTIST(105, "데이터 사이언티스트"),
  UX_UI(201, "UX/UI 디자이너"),
  ;
  private Integer code;
  private String name;

  private static final Map<Integer, String> CODE_MAP =
      Collections.unmodifiableMap(
          Stream.of(values()).collect(Collectors.toMap(Role::getCode, Role::name)));

  public static Role of(Integer code) {
    return Role.valueOf(CODE_MAP.get(code));
  }
}
