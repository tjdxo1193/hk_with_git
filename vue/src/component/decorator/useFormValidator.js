import { message as constMessage } from '@/const';
import { StringUtil, ObjectUtil } from '@/util';

export default function (component) {
  return {
    ...component,
    emits: [...(component.emits ?? []), 'initValidate', 'valid', 'invalid'],
    props: {
      ...(component.props ?? {}),
      _required: {
        type: Boolean,
        default: false,
      },
      _validator: {
        type: [Object, Function],
        default: {
          message: null,
          handler: null,
        },
      },
      _validating: {
        type: Boolean,
        default: false,
        description: '유효성 검사를 외부에서 실행시키는 데 사용하는 flag 값입니다.',
      },
      _initValidate: {
        type: Boolean,
        default: true,
        description: '유효성 검사기를 초기화하는데 사용하는 flag 값입니다.',
      },
    },
    created() {
      component.created && component.created.call(this);
      this._$emitInitValiadte();
    },
    data() {
      return {
        ...(component.data ? component.data.call(this) : {}),
        _$getValueMethodName: null,
        _$validate: {
          touched: false,
          message: null,
        },
      };
    },
    watch: {
      ...(component.watch ?? {}),
      _validating(runValidate) {
        if (runValidate) {
          this._$touch();
          this._$validateValue();
        }
      },
      _initValidate(runInit) {
        if (runInit) {
          this._$initValidate();
          this._$emitInitValiadte();
        }
      },
    },
    methods: {
      ...(component.methods ?? {}),
      _$setGetValueMethodName(name) {
        this._$getValueMethodName = name;
      },
      _$emitInitValiadte() {
        this.$emit('initValidate');
      },
      _$emitInvalid() {
        this.$emit('invalid', this._$validate.message);
      },
      _$emitValid() {
        this._$initValidate();
        this.$emit('valid');
      },
      _$touch() {
        if (!this._$validate.touched) {
          this._$validate.touched = true;
        }
      },
      _$initValidate() {
        this._$validate = {
          touched: false,
          message: null,
        };
      },
      _$getValue() {
        const value = this[this._$getValueMethodName]();
        if (Array.isArray(value)) {
          return [...value];
        }
        if (ObjectUtil.isObject(value)) {
          return { ...value };
        }
        return value;
      },
      _$getValidator() {
        const { _validator } = this.$props;
        if (typeof _validator === 'function') {
          return {
            message: null,
            handler: _validator,
          };
        }
        return _validator;
      },
      _$validateValue() {
        const value = this._$getValue();

        if (this._$isValidRequireValue(value) === false) {
          return;
        }
        if (this._$isValidCustomValidator(value) === false) {
          return;
        }
        this._$emitValid();
      },
      _$isValidRequireValue(value) {
        const { _required } = this.$props;

        if (_required && StringUtil.isEmpty(value)) {
          this._$setValidateMessage(constMessage.validate.requireInput);
          this._$emitInvalid();
          return false;
        }
        return true;
      },
      _$isValidCustomValidator(value) {
        const { message, handler } = this._$getValidator();

        if (handler == null || typeof handler !== 'function') {
          return true;
        }

        const isValid = handler(value);
        const validObject = this._$convertValidObjectFromCustomValidatorResult(isValid, message);

        if (!validObject.valid) {
          this._$setValidateMessage(validObject.message);
          this._$emitInvalid();
          return false;
        }
        return true;
      },
      _$setValidateMessage(message) {
        this._$validate.message = message;
      },
      /**
       *
       * @param {String | Boolean} result validator의 handler 메서드를 실행한 결과입니다. string 혹은 boolean 타입입니다.
       * @param {String} message validator의 message property입니다. handler만 정의했을 시 null이며 이 경우 @/const/message.js에 정의된 기본 메시지입니다.
       * @returns {
       *            valid: 유효한 값인지 여부,
       *            message: 값이 유효하지 않을 때 보여주는 메시지
       *          }
       *
       *? 메시지 우선 순위는 다음과 같습니다.
       ** 1. handler 내부에서 객체를 반환할 때, 객체의 message property
       ** 2. validator 객체에 선언한 message property
       ** 3. @/const/message.js 파일에 정의된 기본값
       */
      _$convertValidObjectFromCustomValidatorResult(result, message) {
        const validateMessage = message ?? constMessage.validate.invalidInputValue;

        if (typeof result === 'string') {
          return {
            valid: false,
            message: result,
          };
        }

        return {
          valid: result,
          message: validateMessage,
        };
      },
    },
    computed: {
      ...(component.computed ?? {}),
      _$validateMessage() {
        return this._$validate.message;
      },
      _$isInvalid() {
        return this._$validate.touched && this._$validate.message;
      },
    },
  };
}
