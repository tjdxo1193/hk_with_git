<template>
  <div class="form-item">
    <div
      v-if="item.itemLabel"
      :class="['form-item__label', isRequiredItem(item) && 'form-item__label--required']"
      :style="labelStyleVariables"
    >
      <h5>{{ item.itemLabel }}</h5>
    </div>

    <div class="form-item__element">
      <template v-if="item?._multi">
        <template v-for="multiItem in item._multiForms" :key="multiItem.name">
          <FormElement
            v-bind="eventListener"
            :item="multiItem"
            :width="multiItem.width"
            :span="multiItem._colSpan"
            :hide="multiItem._hidden"
            @form-event="$emit('form-event', $event)"
            @enter="$emit('enter', $event)"
          />
        </template>
      </template>

      <template v-else-if="item?._slot">
        <FormElement
          v-bind="eventListener"
          :item="item"
          :width="item.width"
          @form-event="$emit('form-event', $event)"
          @enter="$emit('enter', $event)"
        >
          <template v-slot="slotProps">
            <slot v-bind="slotProps"></slot>
          </template>
        </FormElement>
      </template>

      <template v-else>
        <FormElement
          v-bind="eventListener"
          :item="item"
          :width="item.width"
          @form-event="$emit('form-event', $event)"
          @enter="$emit('enter', $event)"
        />
      </template>
    </div>
  </div>
</template>

<script>
import { ComponentUtil } from '@/util';

import FormElement from './FormElement';

export default {
  name: 'FormItem',
  emits: ['form-event', 'enter'],
  inheritAttrs: false,
  components: {
    FormElement,
  },
  props: {
    item: Object,
    labelWidth: {
      type: String,
      default: '140px',
    },
  },
  data() {
    return {
      eventListener: ComponentUtil.getEventListener(this.$attrs),
      labelStyleVariables: {
        '--label-width': this.$props.labelWidth,
      },
    };
  },
  methods: {
    isRequiredItem(item) {
      return this.isMultiItem(item)
        ? item._multiForms.some((item) => item._required)
        : item._required;
    },
    isMultiItem(item) {
      return item?._multi;
    },
  },
};
</script>

<style lang="scss" scope>
.form-item {
  position: relative;
  display: flex;
  height: 100%;

  &__label {
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    min-width: var(--label-width);
    max-width: var(--label-width);
    height: 100%;
    background-color: rgb(248, 249, 251);
    font-weight: 500;
    color: rgb(88, 88, 88);
    border-right: 1px solid $common-color-grey;

    &--required::before {
      content: '*';
      position: relative;
      color: rgb(255, 0, 0);
      top: 2px;
      margin-right: 5px;
    }
  }

  &__element {
    position: relative;
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
  }
}
</style>
