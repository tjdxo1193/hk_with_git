<template>
  <div class="align-horizontal" :style="styleVariable">
    <template v-for="({ slot, style }, index) in slots" :key="index">
      <div :style="style">
        <component :is="slot"></component>
      </div>
    </template>
  </div>
</template>

<script>
export default {
  name: 'AlignHorizontal',
  props: {
    spans: {
      type: Array,
      default: () => [10],
      description: 'Specify the width percent of horizontally placed elements.',
    },
    gap: {
      type: Number,
      default: 0,
      validator: (v) => Number.isInteger(v) && v >= 0,
    },
    alignItems: {
      type: String,
      default: 'default',
      validator: (v) => ['default', 'start', 'center', 'end'].includes(v),
    },
  },
  data() {
    const alignItemsTable = {
      default: 'stretch',
      start: 'start',
      center: 'center',
      end: 'end',
    };

    return {
      styleVariable: {
        '--align-items': alignItemsTable[this.$props.alignItems],
        '--gap': `${this.$props.gap}px`,
      },
    };
  },
  methods: {
    getDefaultSlots() {
      return this.$slots.default ? this.$slots.default() : [];
    },
    getSlotSpans() {
      const { spans } = this.$props;
      const sum = spans.reduce((acc, span) => (acc += span), 0);
      const avg = sum / spans.length;

      return this.getDefaultSlots().map((slot, index) => {
        return {
          slot,
          span: spans[index] ?? avg,
        };
      });
    },
    getSlotSpansSum() {
      return this.getSlotSpans().reduce((acc, { span }) => (acc += span), 0);
    },
    getSlots() {
      const spansSum = this.getSlotSpansSum();
      return this.getSlotSpans().map(({ slot, span }) => {
        return {
          slot,
          style: {
            width: `${(span / spansSum) * 100}%`,
          },
        };
      });
    },
  },
  computed: {
    slots() {
      return this.getSlots();
    },
  },
};
</script>

<style lang="scss" scope>
.align-horizontal {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: var(--align-items);
  gap: var(--gap);
}
</style>
