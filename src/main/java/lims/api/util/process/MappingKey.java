package lims.api.util.process;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * KeyGenerator 인터페이스의 generateKey 메서드로 KeyObject를 생성할 때,
 * 키 필드를 명시하는데 사용합니다.
 * 이 어노테이션이 선언된 필드는 KeyGenerator.generateKey로 생성되는 keyObject에 포함됩니다.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MappingKey {
}