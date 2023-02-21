package lims.api.integration.annotation;

/**
 * 특별한 기능을 제공하지는 않습니다.
 * Enum으로 관리해야 하는 데이터지만 Enum 타입을 명시적으로 사용하기 애매한 경우 명시하는데 사용합니다.
 */
public @interface EnumType {

    Class<? extends Enum<?>> value();

}