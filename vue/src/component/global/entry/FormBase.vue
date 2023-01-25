<template>
  <form ref="form" class="form-base" :style="styleVariable" v-on:submit.prevent>
    <template v-for="item in forms" :key="item.name">
      <template v-if="item._hidden">
        <component :name="item.name" :is="item.is" v-model="item.value"></component>
      </template>

      <template v-else>
        <div
          :class="['form-base-block', item._blank && 'blank']"
          :style="getSpanStyle(item._rowSpan, item._colSpan)"
        >
          <!--//* item이 blank 속성이면 빈 칸을 렌더링합니다. -->
          <template v-if="item._blank" />

          <template v-else-if="item?._slot">
            <FormItem
              :item="item"
              :labelWidth="labelWidth"
              @form-event="$emit('form-event', $event)"
              @enter="$emit('enter', $event)"
            >
              <template v-slot="slotProps">
                <slot :name="`form-${item.name}`" v-bind="slotProps"></slot>
              </template>
            </FormItem>
          </template>

          <template v-else>
            <FormItem
              :item="item"
              :labelWidth="labelWidth"
              @form-event="$emit('form-event', $event)"
              @enter="$emit('enter', $event)"
            />
          </template>
        </div>
      </template>
    </template>
  </form>
</template>

<script>
import { FormItem } from '@/component/entry';

export default {
  name: 'FormBase',
  emits: ['form-event', 'enter'],
  inheritAttrs: false,
  components: {
    FormItem,
  },
  props: {
    labelWidth: String,
    forms: {
      type: Array,
      default: () => [],
    },
    countPerRow: {
      type: Number,
      default: 4,
    },
  },
  data() {
    return {
      styleVariable: {
        '--count-per-row': this.$props.countPerRow,
      },
    };
  },
  methods: {
    getSpanStyle(rowSpan, colSpan) {
      const { countPerRow } = this.$props;
      const columnSpan = countPerRow < colSpan ? countPerRow : colSpan;
      return {
        'grid-row': rowSpan ? `span ${rowSpan}` : 'auto',
        'grid-column': columnSpan ? `span ${columnSpan}` : 'auto',
      };
    },
  },
};
</script>

<style lang="scss" scope>
.form-base {
  overflow: auto;
  display: grid;
  grid-template-columns: repeat(var(--count-per-row), 1fr);
  border-top: 1px solid $common-color-grey;

  &-block {
    height: 100%;
    border-width: 0 0 1px 1px;
    border-style: solid;
    border-color: $common-color-grey;
    margin-left: -1px;

    &.blank {
      background: transparent;
    }

    &:last-of-type {
      border-right: 0;
    }
  }
}
</style>
