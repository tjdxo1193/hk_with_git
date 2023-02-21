<template>
  <div class="mr15 checkbox_base checkbox-base">
    <input
      v-bind="eventListener"
      type="checkbox"
      ref="self"
      :name="name"
      :checked="checked"
      :disabled="disabled"
      @input="onInput"
      :class="[validateClass]"
    />
    <label :for="name" @click="check">
      {{ label }}
    </label>
    <div v-if="_$isInvalid" class="radio-base__message--invalid">
      {{ _$validateMessage }}
    </div>
  </div>
</template>

<script>
import { useDisabledEmitter, useFormValidator } from '@/component/decorator';
import { ComponentUtil } from '@/util';

let instance = useDisabledEmitter({
  name: 'CheckBox',
  emits: ['update:modelValue', 'input'],
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
    uncheckedValue: {
      type: [String, Boolean, Number],
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
    required: {
      type: Boolean,
      default: false,
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
      return this.$refs.self.checked ? this.$props.checkedValue : this.$props.uncheckedValue;
    },
    onInput(e) {
      this._$validateValue();

      const { checked } = e.target;
      const value = checked ? this.$props.checkedValue : this.$props.uncheckedValue;
      this.$emit('update:modelValue', value, checked);
      this.$emit('input', e, value, checked);
    },
    check() {
      this.$refs.self.click();
    },
  },
  computed: {
    checked() {
      return this.$props.modelValue == this.$props.checkedValue;
    },
    checkboxClass() {
      return {
        readonly: this.$props.readonly,
        disabled: this.$props.disabled,
      };
    },
    validateClass() {
      return this._$isInvalid ? 'checkbox-base__element--invalid' : null;
    },
  },
});

instance = useDisabledEmitter(instance);
instance = useFormValidator(instance);

export default instance;
</script>

<style lang="scss" scope>
.checkbox-base {
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
