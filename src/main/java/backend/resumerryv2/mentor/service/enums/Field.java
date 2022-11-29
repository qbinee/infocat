package backend.resumerryv2.mentor.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Field {
    IN_BOUND(1, "국내 자기소개서/이력서"),
    OUT_BOUND(2, "국외 자기소개서/이력서"),
    PORTFOLIO(3, "포트폴리오"),
    INTERVIEW(4, "면접"),
    CODING_TEST(5, "코딩 테스트"),
    CAREER_CONSULTING(6, "커리어 상담")
    ;
    private int code;
    private String name;

    private static final Map<Integer, String> CODE_MAP = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(Field::getCode, Field::name))
    );

    public static Field of(final int code){
        return Field.valueOf(CODE_MAP.get(code));
    }
}
