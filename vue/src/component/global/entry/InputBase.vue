<template>
  <div class="input-base">
    <input
      ref="self"
      v-bind="{ ...eventListener, ...this.$attrs }"
      :type="type"
      :value="modelValue"
      :readonly="readonly"
      :disabled="disabled"
      :required="_required"
      @input="onInput"
      @blur="onBlur"
      :class="[validateClass]"
    />
    <div v-if="_$isInvalid" class="input-base__message--invalid">
      {{ _$validateMessage }}
    </div>
  </div>
</template>

<script>
import { useDisabledEmitter, useFormValidator } from '@/component/decorator';
import { ComponentUtil } from '@/util';

let instance = {
  name: 'InputBase',
  emits: ['update:modelValue', 'input', 'blur'],
  inheritAttrs: false,
  props: {
    modelValue: {
      type: [String, Number],
      default: null,
    },
    readonly: {
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    type: {
      type: String,
      default: 'text',
      validator: (v) => ['text', 'password', 'number'].includes(v),
    },
  },
  created() {
    this.eventListener = ComponentUtil.getEventListener(this.$attrs);
  },
  mounted() {
    this._$setGetValueMethodName('getValue');
  },
  data() {
    return {
      eventListener: {},
    };
  },
  methods: {
    getValue() {
      return this.$refs.self?.value;
    },
    onInput(e) {
      this._$validateValue();
      this.updateModelValue(e.target.value);
      this.$emit('input', e);
    },
    updateModelValue(value) {
      this.$emit('update:modelValue', value);
    },
    onBlur(e) {
      this._$validateValue();
      this.$emit('blur', e);
    },
  },
  computed: {
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
.input-base {
  width: 100%;

  &__element--invalid {
    border: 1px solid $input-color-error !important;
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
