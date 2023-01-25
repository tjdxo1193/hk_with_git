<template>
  <div class="button-arrow-wrap" @click="$emit('click', { event: $event })">
    <div :class="activeClass"></div>
  </div>
</template>

<script>
export default {
  name: 'ButtonArrow',
  emits: ['click'],
  inheritAttrs: false,
  props: {
    direction: {
      type: String,
      default: 'left',
      validator: (v) => ['up', 'left', 'down', 'right'].includes(v),
    },
  },
  computed: {
    activeClass() {
      return ['button-arrow', `button-arrow--${this.$props.direction}`];
    },
  },
};
</script>

<style lang="scss" scope>
.button-arrow-wrap {
  $arrow-background-color: rgb(160, 190, 250);

  cursor: pointer;
  position: relative;
  box-sizing: border-box;
  width: 54px;
  height: 54px;
  // border-radius: $layout-border-radius;
  border-radius: 50%;
  background-color: #fff;
  box-shadow: 0 1px 2px 0 rgb(160, 160, 160);
  transition: 0.3s;

  & .button-arrow {
    position: relative;
    box-sizing: border-box;
    width: 19px;
    height: 19px;
    border-top: 2px solid $arrow-background-color;
    border-right: 2px solid $arrow-background-color;

    &::after {
      content: '';
      position: absolute;
      width: 26px;
      height: 2px;
      background-color: $arrow-background-color;
      transform: rotate(-45deg);
      top: 8px;
      left: -5px;
    }

    &--up {
      transform: rotate(-45deg);
      top: 17px;
      left: 18px;
    }

    &--left {
      transform: rotate(225deg);
      top: 18px;
      left: 17px;
    }

    &--down {
      transform: rotate(135deg);
      top: 19px;
      left: 18px;
    }

    &--right {
      transform: rotate(45deg);
      top: 18px;
      left: 19px;
    }
  }

  &:hover .button-arrow {
    border-color: rgb(255, 255, 255);

    &::after {
      background-color: #fff;
    }
  }

  &:hover {
    background-color: $common-selection-color;
  }
}
</style>
