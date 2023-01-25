<template>
  <div class="button-container">
    <button
      type="button"
      :class="['button-base', type, disabled && 'disabled']"
      @click="onClick(name)"
      :disabled="isDisabled"
    >
      <template v-if="label && !isImgType">
        {{ label }}
      </template>

      <template v-else-if="!isImgType">
        <slot></slot>
      </template>
    </button>
  </div>
</template>

<script>
import { CommonUtil } from '@/util';

export default {
  name: 'ButtonBase',
  emits: ['click'],
  inheritAttrs: false,
  props: {
    name: {
      type: String,
      default: null,
    },
    label: {
      type: String,
      default: null,
    },
    type: {
      type: String,
      default: 'default',
      validator: (v) =>
        ['default', 'normal', 'warn', 'danger', 'search', 'clear', 'up', 'down'].includes(v),
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    debounce: {
      type: Number,
      default: 600,
      description: '클릭 후 다음 클릭이 가능할 때까지의 시간',
    },
  },
  data() {
    return {
      onClick: CommonUtil.throttle(
        (name) => this.isActive && this.$emit('click', name),
        this.$props.debounce,
      ),
    };
  },
  computed: {
    isImgType() {
      const { type } = this.$props;
      return type === 'search' || type === 'clear' || type === 'up' || type === 'down';
    },
    isActive() {
      return this.$props.type !== 'disabled';
    },
    isDisabled() {
      return this.$props.disabled;
    },
  },
};
</script>

<style lang="scss" scoped>
.button-container {
  position: relative;
  display: inline-block;
  margin: 0 3px;

  & .button-base {
    color: rgb(255, 255, 255);
    min-width: 52px;

    &.default {
      background-color: $common-selection-color;

      &:hover {
        background-color: rgb(12, 75, 191);
      }
    }

    &.warn {
      background-color: rgb(241, 170, 39);

      &:hover {
        background-color: rgb(215, 144, 14);
      }
    }

    &.danger {
      background-color: rgb(245, 75, 80);

      &:hover {
        background-color: rgb(235, 55, 50);
      }
    }

    &.normal {
      background-color: rgb(255, 255, 255);
      border: 1px solid rgb(170, 170, 170);
      color: rgb(88, 88, 88);

      &:hover {
        background-color: rgb(120, 120, 120);
        border: 1px solid rgb(120, 120, 120);
        color: rgb(255, 255, 255);
      }
    }

    &.disabled {
      cursor: initial;
      background-color: rgb(153, 153, 153) !important;
      color: rgb(255, 255, 255) !important;
    }

    &.search {
      background: rgb(251, 151, 21) url('@/assets/images/searchBtn.png') center no-repeat;

      &:hover {
        background-color: rgb(248, 117, 1);
      }
    }

    &.clear {
      background: rgb(39, 211, 241) url('@/assets/images/initBtn.png') center no-repeat;

      &:hover {
        background-color: rgb(14, 185, 215);
      }
    }

    // TODOD 화살표 모양 버튼 추가 예정
    &.up {
      background: rgb(251, 151, 21) url('@/assets/images/icon-up.png') center no-repeat;

      &:hover {
        background-color: rgb(248, 117, 1);
      }
    }

    // TODOD 화살표 모양 버튼 추가 예정
    &.down {
      background: rgb(251, 151, 21) url('@/assets/images/icon-down.png') center no-repeat;

      &:hover {
        background-color: rgb(248, 117, 1);
      }
    }
  }
}
</style>
