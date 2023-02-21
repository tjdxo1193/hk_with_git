package lims.api.common.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 * 키 값은 대문자의 스네이크 케이스로 작성해야 합니다.
 * 카멜 케이스 문자열을 스네이크 케이스로 변경해야 하는 경우 다음 메서드를 사용하세요.
 * StringUtil.camelToSnake()
 */
@Getter
@Setter
public class AuditRowData extends HashMap<String, AuditColumnData> {
}