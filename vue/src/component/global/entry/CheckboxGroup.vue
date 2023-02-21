<template>
  <div class="checkbox-group" :class="[validateClass]">
    <div>
      <Grid :countPerRow="countPerRow" :space="space" :gap="gap" :rowGap="rowGap">
        <span v-for="checkbox in groups" :key="checkbox.value">
          <Checkbox
            v-bind="{ ...eventListener, ...checkbox }"
            :name="name"
            :readonly="readonly"
            :disabled="disabled"
            :modelValue="predicateValue(checkbox.checkedValue)"
            @update:modelValue="
              (value, isChecked) => onChecked(value, isChecked, checkbox.checkedValue)
            "
            @disabled="$emit('disabled', $event)"
          />
        </span>
      </Grid>
    </div>
    <div v-if="_$isInvalid" class="checkbox-group__message--invalid">
      {{ _$validateMessage }}
    </div>
  </div>
</template>

<script>
import { useFormValidator } from '@/component/decorator';
import { ComponentUtil } from '@/util';

let instance = {
  name: 'CheckboxGroup',
  emits: ['update:modelValue', 'disabled'],
  inheritAttrs: false,
  props: {
    name: {
      type: String,
      default: null,
    },
    modelValue: {
      type: Array,
      default: () => [],
    },
    groups: {
      type: Array,
      required: true,
      meta: 'Array<CheckboxProps>',
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
  },
  mounted() {
    this._$setGetValueMethodName('getValue');
  },
  data() {
    return {
      validate: {
        value: [],
      },
      eventListener: {},
    };
  },
  methods: {
    setValidateValue(value = []) {
      this.validate.value = value.length > 0 ? value : null;
    },
    getValue() {
      return this.validate.value;
    },
    onChecked(value, isChecked, checkedValue) {
      const matchedIndex = this.findGroupIndexBy(checkedValue);
      const emitValue = isChecked
        ? this.getAddedValue(matchedIndex, value)
        : this.getRemovedValue(matchedIndex);
      this.$emit('update:modelValue', [...emitValue]);

      this.setValidateValue(emitValue);
      this._$validateValue();
    },
    getAddedValue(index, value) {
      const addedValue = [...this.$props.modelValue];
      if (this.isExists(value)) {
        return addedValue;
      }
      addedValue.splice(index, 0, value);
      return addedValue;
    },
    getRemovedValue(matchedIndex) {
      const removedValue = [...this.$props.modelValue];
      const matchedValue = this.findGroupValueBy(matchedIndex);
      if (!this.isExists(matchedValue)) {
        return removedValue;
      }
      const valueIndex = this.findValueIndexBy(matchedValue);
      removedValue.splice(valueIndex, 1);
      return removedValue;
    },
    findGroupIndexBy(groupCheckedValue) {
      return this.$props.groups.findIndex(({ checkedValue }) => checkedValue == groupCheckedValue);
    },
    findGroupValueBy(index) {
      return this.$props.groups[index].checkedValue;
    },
    findValueIndexBy(value) {
      return this.$props.modelValue.findIndex((v) => v == value);
    },
    predicateValue(value) {
      return this.isExists(value) ? value : null;
    },
    isExists(value) {
      return Array.isArray(this.$props.modelValue) ? this.$props.modelValue.includes(value) : false;
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
.checkbox-group {
  width: 100%;

  &.invalid input:checked ~ .checkbox-base__cover {
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
