<template>
  <div :class="['modal', show && 'modal--show']" :style="styleVariables" t>
    <div class="modal-container">
      <div class="modal--close" @click="$emit('close')"></div>

      <Card
        v-bind="card"
        :padding="cardPadding"
        :margin="cardMargin"
        :transparent="isPlain"
        class="modal-wrapper"
      >
        <template v-if="!isPlain && useHeader" #action>
          <slot name="action"></slot>
        </template>

        <template #default>
          <slot></slot>
        </template>

        <template v-if="!isPlain && useFooter" #footer>
          <slot name="footer"></slot>
        </template>
      </Card>
    </div>
  </div>
</template>

<script>
import { ObjectUtil, StringUtil } from '@/util';
export default {
  name: 'ModalBase',
  emits: ['close'],
  inheritAttrs: false,
  props: {
    title: String,
    width: {
      type: String,
      default: 'auto',
    },
    height: {
      type: String,
      default: 'auto',
    },
    show: {
      type: Boolean,
      default: false,
    },
    top: {
      type: Number,
      default: 0,
    },
    right: {
      type: Number,
      default: 0,
    },
    bottom: {
      type: Number,
      default: 0,
    },
    left: {
      type: Number,
      default: 0,
    },
    plain: {
      type: Boolean,
      default: false,
      description: 'Body slot만 렌더링됩니다.',
    },
  },
  data() {
    const { title, width, height, plain } = this.$props;

    return {
      card: {
        title,
        width,
        height,
        plain,
      },
      styleVariables: {
        '--top': `${this.$props.top}px`,
        '--right': `${this.$props.right}px`,
        '--bottom': `${this.$props.bottom}px`,
        '--left': `${this.$props.left}px`,
        '--width': this.$props.width,
        '--width-for-close': this.isDefaultWidth() ? '100%' : this.$props.width,
      },
    };
  },
  methods: {
    isDefaultWidth() {
      return this.$props.width === 'auto';
    },
  },
  computed: {
    isPlain() {
      return this.$props.plain;
    },
    cardPadding() {
      return this.isPlain ? '0px' : undefined;
    },
    cardMargin() {
      return this.isPlain ? '0px' : undefined;
    },
    useHeader() {
      return StringUtil.isNotEmpty(this.$props.title) || ObjectUtil.has(this.$slots, 'action');
    },
    useFooter() {
      return ObjectUtil.has(this.$slots, 'footer');
    },
  },
};
</script>

<style lang="scss" scoped>
.modal {
  background: rgba(0, 0, 0, 0.3);
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  visibility: hidden;
  z-index: 2000;
  opacity: 0;

  &-container {
    position: relative;
    width: fit-content;
    height: fit-content;
    top: calc(50% - var(--top) + var(--bottom));
    left: calc(50% - var(--left) + var(--right));
    transform: translate(-50%, -50%);
  }

  &-wrapper {
    box-shadow: 0 0 5px 0 rgba(39, 110, 241, 0.9);
    margin: 0;
  }

  &--show {
    opacity: 1;
    visibility: visible;
    transition: all 0.2s;
  }

  &--close {
    cursor: pointer;
    position: relative;
    width: 30px;
    height: 30px;
    right: 0;
    left: calc(var(--width-for-close) - 25px);
    background: url('@/assets/images/close.png') no-repeat;
  }
}
</style>
