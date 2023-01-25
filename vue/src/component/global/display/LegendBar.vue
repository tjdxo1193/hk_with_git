<template>
  <div class="legend-bar">
    <LegendBase v-for="(legend, index) in legends" v-bind="legend" :key="index" />
  </div>
</template>

<script>
export default {
  name: 'LegendBar',
  props: {
    legends: {
      type: Array,
      default: () => [],
    },
  },
};
</script>

<style lang="scss" scope>
.legend-bar {
  display: flex;
  justify-content: space-between;
  width: 100%;
}
</style>
