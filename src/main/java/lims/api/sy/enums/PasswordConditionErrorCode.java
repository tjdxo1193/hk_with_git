package lims.api.sy.enums;

import lims.api.sy.vo.PasswordConditionValidVO;
import lims.api.sy.vo.PwdChangeVO;
import lims.api.util.crypto.BcryptCrypto;

import java.util.function.BiFunction;
import java.util.function.Function;

public enum PasswordConditionErrorCode {
    NOT_INCLUDE_NUMBER("NOT_INCLUDE_NUMBER_ERROR", validVO -> validVO.getNewPwd().matches(PasswordConditionRegExpression.NUMBER.getValue())),
    NOT_INCLUDE_ENGLISH("NOT_INCLUDE_ENGLISH_ERROR", validVO -> validVO.getNewPwd().matches(PasswordConditionRegExpression.ENGLISH.getValue())),
    NOT_INCLUDE_SPECIAL_CHARACTERS("NOT_INCLUDE_SPECIAL_CHARACTERS_ERROR", validVO -> validVO.getNewPwd().matches(PasswordConditionRegExpression.SPECIAL_CHARACTERS.getValue())),
    INCLUDE_TRIPLE_LETTER("INCLUDE_TRIPLE_LETTER_ERROR",  validVO -> !(validVO.getNewPwd().matches(PasswordConditionRegExpression.TRIPLE_LETTER.getValue()))),
    INCLUDE_CONSECUTIVE_LETTER("INCLUDE_CONSECUTIVE_LETTER_ERROR",  validVO -> !(isIncludeConsecutiveLetter(validVO.getNewPwd()))),
    INCLUDE_KOREAN("INCLUDE_KOREAN_ERROR", validVO -> !(validVO.getNewPwd().matches(PasswordConditionRegExpression.KOREAN.getValue()))),
    INCLUDE_BLANK("INCLUDE_BLANK_ERROR", validVO -> !(validVO.getNewPwd().matches(PasswordConditionRegExpression.BLANK.getValue()))),
    EIGHT_DIGITS_MORE("EIGHT_DIGITS_MORE_ERROR", validVO -> validVO.getNewPwd().length() >= 8),
    INCLUDE_USER_LOGIN_ID("INCLUDE_USER_LOGIN_ID_ERROR", (validVO, vo) -> !(validVO.getNewPwd().matches(".*" + vo.getUserLognId() + ".*"))),
    INCLUDE_USER_NM("INCLUDE_USER_NM_ERROR", (validVO, vo) ->  !(validVO.getNewPwd().matches(".*" + vo.getUserNm() + ".*"))),
    INCLUDE_USER_TEL("INCLUDE_USER_TEL_ERROR", (validVO, vo) ->  !(validVO.getNewPwd().matches(".*" + vo.getUserTel() + ".*"))),
    INCLUDE_USER_MAIL("INCLUDE_USER_MAIL_ERROR", (validVO, vo) ->  !(validVO.getNewPwd().matches(".*" + vo.getUserMail() + ".*"))),
    INCLUDE_EM_ID("INCLUDE_EM_ID_ERROR", (validVO, vo) ->  !(validVO.getNewPwd().matches(".*" + vo.getEmid() + ".*"))),
    OLD_AND_NEW_PWD_EQUAL("OLD_AND_NEW_PWD_EQUAL_ERROR", (validVO, vo) -> !(new BcryptCrypto().equals(vo.getNewPwd(), validVO.getOldPwd()))),
    OLD_PWD_WRONG("OLD_PWD_WRONG_ERROR", (validVO, vo) -> new BcryptCrypto().equals(vo.getPwd(), validVO.getOldPwd()));
    private final String code;
    private Function<PasswordConditionValidVO, Boolean> validator;
    private BiFunction<PasswordConditionValidVO, PwdChangeVO, Boolean> biValidator;

    PasswordConditionErrorCode(String code, BiFunction<PasswordConditionValidVO, PwdChangeVO, Boolean> biValidator) {
        this.code = code;
        this.biValidator = biValidator;
    }

    PasswordConditionErrorCode(String code, Function<PasswordConditionValidVO, Boolean> validator) {
        this.code = code;
        this.validator = validator;
    }

    public String getCode() {
        return code;
    }

    public boolean isInvalid(PasswordConditionValidVO s, PwdChangeVO vo) {
        return !(isBiValidator() ? biValidator.apply(s, vo) : validator.apply(s));
    }

    private boolean isBiValidator() {
        return biValidator != null;
    }


    private static boolean isIncludeConsecutiveLetter(String pwd){
        int pointerFirst, pointerSecond, pointerThird;
        pwd = pwd.toUpperCase();
        for(int i=0; i <= pwd.length() -3; i++){
            pointerFirst = pwd.charAt(i);
            pointerSecond = pwd.charAt(i+1);
            pointerThird = pwd.charAt(i+2);

            boolean isConsecutiveDesc = pointerFirst - pointerSecond == 1 && pointerSecond - pointerThird == 1;
            if(isConsecutiveDesc){
                return true;
            }

            boolean isConsecutiveAsc = pointerFirst  - pointerSecond == -1 && pointerSecond - pointerThird == -1;
            if(isConsecutiveAsc){
                return true;
            }
        }
        return false;
    }
}