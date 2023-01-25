<template>
  <div class="datepicker-switch">
    <div class="datepicker-switch__switch">
      <Checkbox v-model="checked" />
    </div>
    <Datepicker
      :disabled="isDisabled"
      :modelValue="value"
      :twin="twin"
      @update:modelValue="emitValue"
      @disabled="$emit('disabled', $event)"
    />
  </div>
</template>

<script>
export default {
  name: 'DatepickerWithSwitch',
  emits: ['update:modelValue', 'disabled'],
  props: {
    value: [String, Number, Array],
    disabled: {
      type: Boolean,
      default: true,
    },
    twin: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      checked: !this.$props.disabled,
    };
  },
  methods: {
    emitValue(value) {
      this.$emit('update:modelValue', value);
    },
  },
  computed: {
    isDisabled() {
      return !this.checked;
    },
  },
};
</script>

<style lang="scss" scope>
.datepicker-switch {
  &__switch {
    margin-top: 3px;
    margin-right: 3px;
  }

  width: 100%;
  display: flex;
  align-items: center;
}
</style>
