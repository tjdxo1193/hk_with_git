package lims.api.integration.annotation;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 이 어노테이션이 선언된 메서드 실행 중 발생한 예외 로그는
 * Post Processor 테이블에 저장됩니다.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Transactional(propagation = Propagation.REQUIRES_NEW)
public @interface PostProcessorRevExceptionHandler {
}