<template>
  <div class="radio-group" :class="[validateClass]">
    <div>
      <Grid :countPerRow="countPerRow" :space="space" :gap="gap" :rowGap="rowGap" :noGap="noGap">
        <span v-for="radio in groups" :key="radio.value" class="radio-group__label">
          <RadioBase
            v-bind="{ ...eventListener, ...radio }"
            :name="name"
            :readonly="readonly"
            :disabled="disabled"
            :modelValue="value"
            @update:modelValue="onChecked"
            @disabled="$emit('disabled', $event)"
          />
        </span>
      </Grid>
    </div>
    <div v-if="_$isInvalid" class="radio-group__message--invalid">
      {{ _$validateMessage }}
    </div>
  </div>
</template>

<script>
import { useFormValidator } from '@/component/decorator';
import { ComponentUtil } from '@/util';

let instance = {
  name: 'RadioGroup',
  emits: ['update:modelValue', 'disabled'],
  inheritAttrs: false,
  props: {
    noGap: Boolean,
    name: {
      type: String,
      default: null,
    },
    value: {
      type: [String, Number],
      default: null,
    },
    groups: {
      type: Array,
      required: true,
      meta: 'Array<RadioBaseProps>',
    },
    countPerRow: {
      type: Number,
      default: null,
    },
    gap: {
      type: Number,
      default: 50,
    },
    rowGap: {
      type: Number,
      default: 10,
    },
    space: {
      type: String,
      default: 'none',
      validator: (v) => ['none', 'center', 'around', 'between', 'end'].includes(v),
    },
    readonly: {
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
  },
  created() {
    this.eventListener = ComponentUtil.getEventListener(this.$attrs);
    this.validate.value = this.$props.value;
  },
  mounted() {
    this._$setGetValueMethodName('getValue');
  },
  data() {
    return {
      validate: {
        value: null,
      },
      eventListener: {},
    };
  },
  methods: {
    setValidateValue(value) {
      this.validate.value = value;
    },
    getValue() {
      return this.validate.value;
    },
    onChecked(value) {
      this.$emit('update:modelValue', value);

      this.setValidateValue(value);
      this._$validateValue();
    },
  },
  computed: {
    validateClass() {
      return this._$isInvalid ? 'invalid' : null;
    },
  },
};

instance = useFormValidator(instance);

export default instance;
</script>

<style lang="scss" scope>
.radio-group {
  width: 100%;

  &.invalid input:checked ~ .radio-base__cover {
    border-color: $input-color-error;
    box-shadow: 0 0 1px 0 $input-color-error;
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
