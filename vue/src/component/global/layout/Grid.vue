<template>
  <div class="grid-layout" :style="styleVariable">
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: 'GridLayout',
  props: {
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
      default: 0,
    },
    noGap: {
      type: Boolean,
      default: true,
    },
    space: {
      type: String,
      default: 'none',
      validator: (v) => ['none', 'center', 'around', 'between', 'end'].includes(v),
    },
  },
  data() {
    const { noGap } = this.$props;
    return {
      styleVariable: {
        '--count-per-row': this.$props.countPerRow ?? 'auto-fill',
        '--gap': noGap ? 'none' : `${this.$props.gap + 20}px`,
        '--row-gap': `${this.$props.rowGap}px`,
        '--justify': this.getSpaceStyle(),
      },
    };
  },
  methods: {
    getSpaceStyle() {
      const { space } = this.$props;
      if (this.isNoSpacePrefixProp(space)) {
        return space;
      }
      return `space-${space}`;
    },
    isNoSpacePrefixProp(prop) {
      return prop === 'center' || prop === 'none' || prop === 'end';
    },
  },
};
</script>

<style lang="scss" scope>
.grid-layout {
  display: grid;
  justify-content: var(--justify);
  grid-template-columns: repeat(var(--count-per-row), var(--gap));
  row-gap: var(--row-gap);
  width: 100%;
}
</style>
