<template>
  <div class="effect-ripple" @click="onClick" :style="styleVariable">
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: 'EffectRipple',
  props: {
    enable: {
      type: Boolean,
      default: true,
    },
    color: {
      type: String,
      default: 'rgba(255, 255, 255, 0.3)',
    },
    duration: {
      type: [String, Number],
      default: '0.6',
    },
  },
  data() {
    return {
      styleVariable: {
        '--duration': `${this.$props.duration}s`,
      },
    };
  },
  methods: {
    isEnable() {
      return this.$props.enable;
    },
    onClick(e) {
      this.isEnable() && this.activeRipple(e);
    },
    activeRipple({ x, y, target }) {
      const { left, top } = target.getBoundingClientRect();
      let ripple = document.createElement('div');

      ripple.classList.add('ripple-active');
      ripple.style.left = `${x - left}px`;
      ripple.style.top = `${y - top}px`;
      ripple.style.setProperty('--color', this.$props.color);
      ripple.style.setProperty('--scale', target.offsetWidth);

      target.appendChild(ripple);

      const listener = () => {
        ripple.removeEventListener('animationend', listener);
        ripple.remove();
        ripple = null;
      };

      ripple.addEventListener('animationend', listener);
    },
  },
};
</script>

<style lang="scss" scope>
.effect-ripple {
  position: relative;
  overflow: hidden;

  & .ripple-active {
    width: 6px;
    height: 6px;
    position: absolute;
    border-radius: 50%;
    background: var(--color);
    animation: ripple var(--duration) linear;
  }

  @keyframes ripple {
    0% {
      transform: scale(1);
    }
    100% {
      transform: scale(var(--scale));
      opacity: 0;
    }
  }
}
</style>
