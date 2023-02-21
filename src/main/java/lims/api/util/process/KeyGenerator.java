package lims.api.util.process;

import java.lang.reflect.Field;

/**
 * 이 인터페이스를 구현한 클래스는 @MappingKey 어노테이션이 선언된 필드들로 Key Object를 생성합니다.
 *
 * 예를 들어, SimpleSaveProcess은 템플릿-콜백 패턴이 적용되어 있는데,
 * Oracle의 Merge같은 작업을 처리하는 데 사용됩니다.
 *
 * 이 과정에서 식별값(key)를 기준으로 데이터의 동등성(동일성이 아님)을 비교하여 insert, update를 결정합니다.
 * 여기서 식별값으로 사용되는 식별 객체, 즉 Key 오브젝트를 생성하는데 필요한 퍼블릭 인터페이스를 제공하는 인터페이스입니다.
 *
 * @see
 *   Oracle의 Merge문 대신 비즈니스 로직으로 처리하는 이유는
 *   개발 시 코드의 구분이 명확해지고 에러 발생 시 디버깅이 더 용이하기 때문입니다.
 */
public interface KeyGenerator {

    default KeyObject generateKey() {
        try {
            Field[] fields = this.getClass().getDeclaredFields();
            KeyObject key = new KeyObject();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(MappingKey.class)) {
                    key.put(field.getName(), field.get(this));
                }
            }
            return key;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    };

    boolean equals(Object o);

    int hashCode();

}