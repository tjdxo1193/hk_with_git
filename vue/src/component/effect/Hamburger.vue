<template>
  <div class="hamburger" :class="isOpen ? 'open' : 'close'" @click="onClick">
    <div class="bar"></div>
    <div class="bar"></div>
    <div class="bar"></div>
  </div>
</template>

<script>
export default {
  name: 'Hamburger',
  emits: ['toggle'],
  data() {
    return {
      isOpen: false,
    };
  },
  methods: {
    onClick() {
      this.toggle();
      this.emitToggle();
    },
    emitToggle() {
      this.$emit('toggle', this.isOpen);
    },
    toggle() {
      this.isOpen = !this.isOpen;
    },
  },
};
</script>

<style lang="scss" scope>
.hamburger {
  $duration: 0.3s;
  $second-bar-duration: 0.4s;

  cursor: pointer;
  position: relative;
  width: 36px;
  height: 30px;

  & .bar {
    position: relative;
    width: 100%;
    height: 3px;
    background-color: rgb(255, 255, 255);
    border-radius: 3px;

    &:nth-of-type(1) {
      position: relative;
      top: 0;
      transform: rotate(0deg);
      transition: transform $duration, top $duration;
    }
    &:nth-of-type(2) {
      opacity: 1;
      margin: 6px 0;
    }
    &:nth-of-type(3) {
      position: relative;
      top: 0;
      transform: rotate(0deg);
      transition: transform $duration, top $duration;
    }
  }

  &.open .bar {
    height: 2px;

    &:nth-of-type(1) {
      width: 25px;
      top: 14px;
      transform: rotate(45deg);
      transition: transform $duration, top $duration;
    }
    &:nth-of-type(2) {
      opacity: 0;
      margin: 0;
    }
    &:nth-of-type(3) {
      width: 25px;
      top: 10px;
      transform: rotate(-45deg);
      transition: transform $duration, top $duration;
    }
  }
}
</style>
