<template>
  <div class="legend-base legend-wrap">
    <div :class="className"></div>
    <h5>{{ value }}</h5>
  </div>
</template>

<script>
export default {
  name: 'LegendBase',
  inheritAttrs: false,
  props: {
    /**
     ** @/assets/scss/legend.scss 참고
     */
    className: {
      type: String,
      default: 'empty',
    },
    value: {
      type: [String, Number, Boolean],
      default: null,
    },
  },
};
</script>

<style lang="scss" scope>
.legend-base {
  position: relative;
  display: flex;
  align-items: center;

  & div {
    font-size: 13px;
    position: relative;
    width: 13px;
    height: 13px;
    margin-left: 15px;
    margin-right: 5px;
  }
}
</style>
