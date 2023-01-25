package lims.api.integration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 어떤 기능을 제공하지는 않습니다.
 * 매핑 정의서의 필드명과 저장되는 DB의 필드의 이름이 다른 경우,
 * 해당 필드의 저장 및 조회에서 사용되는 VO 내 필드명이 다른 경우가 있습니다.
 * 이 때, 조회에 쓰이는 필드명은 연계 인터페이스 매핑에 사용되지 않으므로 이를 명시하는데 사용합니다.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotMapping {
}