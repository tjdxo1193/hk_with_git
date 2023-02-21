<template>
  <div class="space-container" :style="styleVariable"></div>
</template>

<script>
export default {
  name: 'Space',
  props: {
    gap: {
      type: Number,
      default: 20,
    },
  },
  data() {
    return {
      styleVariable: {
        '--gap': `${this.$props.gap}px`,
      },
    };
  },
};
</script>

<style lang="scss" scope>
.space-container {
  position: relative;
  width: 100%;
  height: var(--gap);
}
</style>
