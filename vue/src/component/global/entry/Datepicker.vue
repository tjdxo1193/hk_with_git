<template>
  <div :class="['date-picker', statusClass, _$isInvalid && 'invalid']">
    <VDatepicker
      v-bind="defaultOption"
      :modelValue="modelValue"
      :readonly="readonly"
      :disabled="disabled"
      @update:modelValue="onUpdate"
      @focus="emitEvent('focus')"
      @blur="emitEvent('blur')"
    ></VDatepicker>
    <div v-if="_$isInvalid" class="date-picker__message--invalid">
      {{ _$validateMessage }}
    </div>
  </div>
</template>

<script>
//* reference: https://vue3datepicker.com/
import Datepicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';

import { useDisabledEmitter, useFormValidator } from '@/component/decorator';

let instance = {
  name: 'DatepickerBase',
  emits: ['update:modelValue', 'input', 'focus', 'blur'],
  inheritAttrs: false,
  components: { VDatepicker: Datepicker },
  props: {
    modelValue: {
      type: [String, Number, Array],
      default: null,
    },
    twin: {
      type: Boolean,
      default: false,
      description: `범위 검색용 datepicker를 사용합니다.`,
    },
    type: {
      type: String,
      default: 'day',
      validator: (v) => ['day'].includes(v),
    },
    maxRange: {
      type: Number,
      default: null,
      desscription: `선택 가능한 범위를 제한합니다. 단위는 일(day)`,
    },
    readonly: {
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    required: {
      type: Boolean,
      default: false,
    },
  },
  created() {
    this.defaultOption = {
      locale: 'ko',
      selectText: '선택',
      cancelText: '취소',
      vertical: true,
      maxRange: this.$props.maxRange,
      format: this.getDateFormatOption(),
      ...this.getTwinOption(),
    };
    this.validate.value = this.$props.modelValue;
  },
  mounted() {
    this._$setGetValueMethodName('getValue');
  },
  data() {
    return {
      validate: {
        value: null,
      },
      defaultOption: {},
    };
  },
  methods: {
    setValidateValue(value) {
      this.validate.value = this.isEmpty(value) ? null : value;
    },
    getValue() {
      return this.validate.value;
    },
    emitEvent(type, value) {
      this.$emit(type, { type, target: { value } });
    },
    onUpdate(date) {
      const value = this.isTwinMode() ? this.getTwinResult(date) : this.getResult(date);
      this.$emit('update:modelValue', value);
      this.emitEvent('input', value);

      this.setValidateValue(value);
      this._$validateValue();
    },
    getDateFormatOption() {
      return this.toDateFormat('yyyy', 'MM', 'dd');
    },
    getTwinOption() {
      const isTwin = this.$props.twin;
      return {
        range: isTwin,
        partialRange: false,
        closeOnScroll: true,
      };
    },
    isTwinMode() {
      return this.$props.twin;
    },
    getResult(date) {
      if (!date) {
        return null;
      }
      const str = this.convertDateToString(date);
      return str ? this.toDateFormat(str.year, str.month, str.day) : null;
    },
    getTwinResult(dates = []) {
      if (dates.length === 0) {
        return [null, null];
      }
      return dates.map((date) => {
        const str = this.convertDateToString(date);
        return str ? this.toDateFormat(str.year, str.month, str.day) : null;
      });
    },
    convertDateToString(date) {
      if (!date) {
        return null;
      }
      return {
        year: String(date.getFullYear()),
        month: String(this.padZero(date.getMonth() + 1)),
        day: String(this.padZero(date.getDate())),
      };
    },
    isEmpty(value) {
      if (this.isTwinMode()) {
        return (value ?? []).every((v) => v == null);
      } else {
        return value == null;
      }
    },
    toDateFormat(...args) {
      return args.join('-');
    },
    padZero(v) {
      return v && String(v).length === 1 ? `0${v}` : v;
    },
  },
  computed: {
    statusClass() {
      return {
        required: this.$props._required,
        readonly: this.$props.readonly,
        disabled: this.$props.disabled,
      };
    },
  },
};

instance = useDisabledEmitter(instance);
instance = useFormValidator(instance);

export default instance;
</script>

<style lang="scss" scope>
.date-picker {
  position: relative;
  width: 100%;

  &.readonly {
    pointer-events: none;
  }

  &.disabled {
    pointer-events: none;

    & .dp__input {
      background-color: rgb(240, 240, 240);
      color: rgb(169, 169, 169);
    }
  }

  &.required {
    & .dp__input {
      background-color: rgba(255, 120, 0, 0.2);
    }
  }

  & .dp__input {
    position: relative;
    width: 100%;
    height: 26px;
    word-spacing: 5px;
    font-size: 0.9rem;
    border-width: 0;
    border: 1px solid rgb(187, 187, 187);
    border-radius: 0;
    color: $common-font-color;
  }

  & .dp__input:hover,
  & .dp__input_focus {
    border-color: $input-color-hover;
    box-shadow: 0px 0px 3px $input-color-hover;
  }

  & .dp__clear_icon {
    display: none;
  }

  &.invalid .dp__input {
    border: 1px solid $input-color-error;
    box-shadow: 0 0 1px 0 $input-color-error;
  }

  &__message--invalid {
    padding: 3px;
    font-size: 11px;
    font-weight: 400;
    color: $input-color-error;
  }
}
</style>
