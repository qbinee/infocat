/* Licensed under InfoCat */
package backend.resumerryv2.category.domain.enums;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    IN_BOUND(1, "국내 자기소개서/이력서"),
    OUT_BOUND(2, "국외 자기소개서/이력서"),
    PORTFOLIO(3, "포트폴리오"),
    INTERVIEW(4, "면접"),
    CODING_TEST(5, "코딩 테스트"),
    CAREER_CONSULTING(6, "커리어 상담");
    private Integer code;
    private String name;

    private static final Map<Integer, String> CODE_MAP =
            Collections.unmodifiableMap(
                    Stream.of(values())
                            .collect(Collectors.toMap(Category::getCode, Category::name)));

    public static Category of(Integer code) {
        return Category.valueOf(CODE_MAP.get(code));
    }
}
