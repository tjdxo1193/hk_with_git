import { message } from '@/const';
import { FormBuilder } from '@/util';

const regularExpression = {
  korean: /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g,
  number: /[0-9]/g,
  english: /[a-z]/gi,
  specialCharacters: /[`~!@#$%^&*|;:?()<>]/gi,
  blank: /\s/,
  three: /(.)\1\1/,
  two: /(.)\1/,
  residentRegistrationBackNumber: /[1-4]\d{6}/,
};

function pwdContinue(pwd) {
  //연속된 문자, 숫자 체크(3자리)
  let pointerFirst, pointerSecond, pointerThird;
  pwd = pwd.toUpperCase();
  for (let i = 0; i <= pwd.length - 3; i++) {
    pointerFirst = pwd.charCodeAt(i);
    pointerSecond = pwd.charCodeAt(i + 1);
    pointerThird = pwd.charCodeAt(i + 2);
    if (pointerFirst - pointerSecond == 1 && pointerSecond - pointerThird == 1) {
      return true;
    }
    if (pointerFirst - pointerSecond == -1 && pointerSecond - pointerThird == -1) {
      return true;
    }
  }
}

const inputForm = {
  static: {
    title: '사용자 정보',
    countPerRow: 3,
    buttons: [{ name: 'save', label: '저장' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Input('userNm', '사용자')
      .readonly()
      .Input('userLognId', '사용자ID')
      .readonly()
      .Input('emid', '사번')
      .readonly()
      .Input('dptNm', '부서')
      .readonly()
      .Input('titleNm', '직책')
      .readonly()
      .Input('userTel', '전화번호')
      .readonly()
      .Input('athNm', '권한')
      .readonly()
      .Input('gradeNm', '직급')
      .readonly()
      .Input('userMail', '이메일', { autocomplete: 'username' })
      .readonly()
      .Input('pwd', '현재비밀번호', {
        type: 'password',
        minlength: 8,
        autocomplete: 'current-password',
      })
      .required()
      .Input('newPwd', '새비밀번호', {
        type: 'password',
        minlength: 8,
        autocomplete: 'new-password',
      })
      .required()
      .validator({
        message: `비밀번호는 한글,공백 없이 숫자,영문,특수문자로 입력되어야합니다.`,
        handler(value, formData) {
          if (value.length <= 8) {
            return '비밀번호는 8자리 이상입니다.';
          }
          if (value.search(formData.userNm) > -1) {
            return '사용자명이 포함되어 있습니다.';
          }
          if (value.search(formData.userLognId) > -1) {
            return '사용자ID가 포함되어 있습니다.';
          }
          if (value.search(formData.emid) > -1) {
            return '사번이 포함되어 있습니다.';
          }
          if (value.search(formData.userTel) > -1) {
            return '전화번호가 포함되어 있습니다.';
          }
          if (value.search(formData.userMail) > -1) {
            return '이메일이 포함되어 있습니다.';
          }
          if (value.search(regularExpression.three) > -1) {
            return '같은 문자가 3개이상 포함되어있습니다.';
          }
          if (value.search(regularExpression.korean) > -1) {
            return '비밀번호에 한글에 한글이 포함되어있습니다.';
          }
          if (value.search(regularExpression.blank) > -1) {
            return '비밀번호에 공백 포함되어있습니다.';
          }
          if (value.search(regularExpression.residentRegistrationBackNumber) > -1) {
            return '주민등록번호 뒷자리가 포함되어 있습니다.';
          }
          if (pwdContinue(value)) {
            return '연속된 문자 3자리가 포함되어있습니다.';
          }
          if (value.search(regularExpression.number) < 0) {
            return '비밀번호에 숫자를 포함하여주세요.';
          }
          if (value.search(regularExpression.english) < 0) {
            return '비밀번호에 영문을 포함하여주세요.';
          }
          if (value.search(regularExpression.specialCharacters) < 0) {
            return '비밀번호에 특수문자를 포함하여주세요.';
          }
          if (value == formData.pwd) {
            return '이전비밀번호와 일치합니다.';
          }
          return true;
        },
      })
      .Input('newPwdConfirm', '새비밀번호확인', {
        type: 'password',
        minlength: 8,
        autocomplete: 'new-password',
      })
      .validator({
        message: `비밀번호는 한글,공백 없이 숫자,영문,특수문자로 입력되어야합니다.`,
        handler(value, formData) {
          if (value != formData.newPwd) {
            return '비밀번호가 일치하지 않습니다.';
          }
          return true;
        },
      })
      .required()
      .build(),
};

const errorCodeMessage = {
  NOT_INCLUDE_NUMBER: {
    code: 'NOT_INCLUDE_NUMBER_ERROR',
    message: message.error.pwdNotIncludeNumber,
  },
  NOT_INCLUDE_ENGLISH: {
    code: 'NOT_INCLUDE_ENGLISH_ERROR',
    message: message.error.pwdNotIncludeEnglish,
  },
  NOT_INCLUDE_SPECIAL_CHARACTERS: {
    code: 'NOT_INCLUDE_SPECIAL_CHARACTERS_ERROR',
    message: message.error.pwdNotIncludeSpecialCharacters,
  },
  INCLUDE_TRIPLE_LETTER: {
    code: 'INCLUDE_TRIPLE_LETTER_ERROR',
    message: message.error.pwdIncludeTripleLetter,
  },
  INCLUDE_CONSECUTIVE_LETTER: {
    code: 'INCLUDE_CONSECUTIVE_LETTER_ERROR',
    message: message.error.pwdIncludeConsecutiveLetter,
  },
  INCLUDE_KOREAN: {
    code: 'INCLUDE_KOREAN_ERROR',
    message: message.error.pwdIncludeKorean,
  },
  INCLUDE_BLANK: {
    code: 'INCLUDE_BLANK_ERROR',
    message: message.error.pwdIncludeBlank,
  },
  INCLUDE_RESIDENT_REGISTRATION_BACK_NUMBER: {
    code: 'RESIDENT_REGISTRATION_BACK_NUMBER_ERROR',
    message: message.error.pwdIncludeResidentRegistrationBackNumber,
  },
  EIGHT_DIGITS_MORE: {
    code: 'EIGHT_DIGITS_MORE_ERROR',
    message: message.error.pwdLengthUnder,
  },
  INCLUDE_USER_LOGIN_ID: {
    code: 'INCLUDE_USER_LOGIN_ID_ERROR',
    message: message.error.pwdIncludeUserLoginId,
  },
  INCLUDE_USER_NM: {
    code: 'INCLUDE_USER_NM_ERROR',
    message: message.error.pwdIncludeUserNm,
  },
  INCLUDE_USER_TEL: {
    code: 'INCLUDE_USER_TEL_ERROR',
    message: message.error.pwdIncludeUserTel,
  },
  INCLUDE_USER_MAIL: {
    code: 'INCLUDE_USER_MAIL_ERROR',
    message: message.error.pwdIncludeUserMail,
  },
  INCLUDE_EM_ID: {
    code: 'INCLUDE_EM_ID_ERROR',
    message: message.error.pwdIncludeUserEmid,
  },
  OLD_AND_NEW_PWD_EQUAL: {
    code: 'OLD_AND_NEW_PWD_EQUAL_ERROR',
    message: message.error.matchPreviousPassword,
  },
  OLD_PWD_WRONG: {
    code: 'OLD_PWD_WRONG_ERROR',
    message: message.error.wrongPassword,
  },
};

export default {
  inputForm,
  errorCodeMessage,
};
