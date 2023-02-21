<template>
  <div class="select-base">
    <select
      ref="self"
      v-bind="eventListener"
      :value="modelValue"
      :disabled="disabled"
      :required="_required"
      @input="onInput"
      @blur="onBlur"
      :class="[selectClass, validateClass]"
    >
      <option v-if="finalDefaultOption.show" :value="finalDefaultOption.value ?? ''">
        {{ finalDefaultOption.label }}
      </option>

      <option v-for="option in data" :key="option.value" :value="option.value">
        {{ option.label }}
      </option>
    </select>
    <div v-if="_$isInvalid" class="input-base__message--invalid">
      {{ _$validateMessage }}
    </div>
  </div>
</template>

<script>
import { useDisabledEmitter, useFormValidator } from '@/component/decorator';
import { ObjectUtil, ComponentUtil } from '@/util';

/**
 * TODO queue에 요청들을 담고 request를 병렬 처리
 * TODO async가 변경되면 api를 재호출하는 로직이 필요 (watch...?)
 * ? async로 초기화했다가 elements로 목록을 변경하는 경우가 있을까?
 */
let instance = {
  name: 'SelectBase',
  emits: ['update:modelValue', 'loaded', 'input', 'blur'],
  inheritAttrs: false,
  props: {
    modelValue: {
      type: [String, Number],
      default: '',
    },
    defaultOption: {
      type: Object,
      default: () => ({}),
      meta: {
        show: Boolean,
        value: [String, Number],
        label: String,
      },
    },
    async: {
      type: Function,
      default: null,
      desc: 'If this props is valid, the "elements" props disabledd',
    },
    elements: {
      type: Array,
      default: () => [],
      meta: [
        { value: 'value1', label: 'display1' },
        { value: 'value2', label: 'display2' },
      ],
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
  watch: {
    elements() {
      if (this.$props.elements.length != 0) {
        this.data = this.$props.elements;
      }
    },
  },
  created() {
    this.setData();

    this.finalDefaultOption = this.getFinalDefaultOption();
    this.eventListener = ComponentUtil.getEventListener(this.$attrs);
  },
  mounted() {
    this._$setGetValueMethodName('getValue');
  },
  data() {
    return {
      data: [],
      finalDefaultOption: {},
      eventListener: {},
    };
  },
  methods: {
    getValue() {
      return this.$refs.self?.value;
    },
    onInput(e) {
      this._$validateValue();
      this.$emit('update:modelValue', e.target.value);
      this.$emit('input', e);
    },
    async setData() {
      const isAsync = this.isAsync();
      this.data = isAsync ? await this.getAsyncElement() : this.getElements();
      this.$emit('loaded', { async: isAsync, data: this.data });
    },
    isAsync() {
      return this.$props.async != null;
    },
    async getAsyncElement() {
      const { data } = await this.async();
      return data;
    },
    getElements() {
      return this.$props.elements;
    },
    getDefaultOptions() {
      return { show: true, value: null, label: '선택' };
    },
    getFinalDefaultOption() {
      const defaultOption = this.getDefaultOptions();
      const defaultNewOption = this.$props.defaultOption;

      if (!defaultNewOption) {
        return defaultOption;
      }

      return Object.keys(defaultOption).reduce((acc, key) => {
        if (ObjectUtil.has(defaultNewOption, key)) {
          acc[key] = defaultNewOption[key];
        }
        return acc;
      }, defaultOption);
    },
  },
  computed: {
    selectClass() {
      return {
        readonly: this.$props.readonly,
      };
    },
    validateClass() {
      return this._$isInvalid ? 'select-base__element--invalid' : null;
    },
  },
};

instance = useDisabledEmitter(instance);
instance = useFormValidator(instance);

export default instance;
</script>

<style lang="scss" scope>
.select-base {
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
