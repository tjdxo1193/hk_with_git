package lims.api.util.process;

import java.util.HashMap;

/**
 * KeyGenerator가 생성하는 키 객체입니다.
 * KeyGenerator 구현체의 필드 중 @MappingKey가 선언된 필드를 대상으로 구성됩니다.
 *
 * 즉, 쿼리의 파라미터 중 keyObject를 파라미터로 받는 쿼리가 있다면,
 * 해당 KeyObject를 생성하는 VO의 @MappingKey가 선언되어 있는 필드를 체크하면 됩니다.
 */
public class KeyObject extends HashMap<String, Object> {
}