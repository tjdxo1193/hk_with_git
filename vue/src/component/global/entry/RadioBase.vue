<template>
  <div :class="['radio-base', radioClass]" :style="styleVariable">
    <input
      v-bind="eventListener"
      type="radio"
      ref="self"
      :checked="checked"
      :name="name"
      :value="checkedValue"
      :disabled="disabled"
      @input="onInput"
      :class="[validateClass]"
    />
    <span class="radio-base__label" @click="check">
      {{ label }}
    </span>
    <!-- <div :class="['radio-base__cover', _$isInvalid && 'invalid']" @click="check"></div> -->

    <div v-if="_$isInvalid" class="radio-base__message--invalid">
      {{ _$validateMessage }}
    </div>
  </div>
</template>

<script>
import { useDisabledEmitter, useFormValidator } from '@/component/decorator';
import { ComponentUtil } from '@/util';

let instance = {
  name: 'RadioBase',
  emits: ['update:modelValue', 'input', 'blur'],
  inheritAttrs: false,
  props: {
    modelValue: {
      type: [String, Number, Boolean],
      default: null,
    },
    name: {
      type: String,
      required: false,
    },
    label: {
      type: String,
      default: '',
    },
    checkedValue: {
      type: [String, Boolean, Number],
      default: true,
    },
    readonly: {
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    width: {
      type: String,
      default: 'auto',
    },
  },
  created() {
    this.eventListener = ComponentUtil.getEventListener(this.$attrs);
    this.styleVariable = {
      '--width': this.$props.width,
    };
  },
  mounted() {
    this._$setGetValueMethodName('getValue');
  },
  data() {
    return {
      eventListener: {},
      styleVariable: {},
    };
  },
  methods: {
    getValue() {
      return this.$refs.self.checked ? this.$props.checkedValue : null;
    },
    onInput(e) {
      this._$validateValue();

      this.$emit('update:modelValue', e.target.value);
      this.$emit('input', e);
    },
    check() {
      this.$refs.self.click();
    },
  },
  computed: {
    checked() {
      return this.$props.modelValue == this.$props.checkedValue;
    },
    radioClass() {
      return {
        readonly: this.$props.readonly,
        disabled: this.$props.disabled,
      };
    },
    validateClass() {
      return this._$isInvalid ? 'input-base__element--invalid' : null;
    },
  },
};

instance = useDisabledEmitter(instance);
instance = useFormValidator(instance);

export default instance;
</script>

<style lang="scss" scope>
.radio-base {
  // width: var(--width);

  &__label {
    cursor: pointer;
  }

  &__message--invalid {
    position: relative;
    top: 4px;
    padding: 3px;
    font-size: 11px;
    font-weight: 400;
    color: $input-color-error;
  }
}
</style>
