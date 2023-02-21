<template>
  <article :class="['card', transparent && 'transparent']" :style="styleVariable">
    <template v-if="useHeader">
      <section :class="['card__header', transparent && 'transparent']">
        <div class="card__title">
          <Title>{{ title }}</Title>
        </div>

        <div class="card__action">
          <slot name="action"></slot>
        </div>
      </section>
    </template>

    <section :class="['card__body', transparent && 'transparent']">
      <slot></slot>
    </section>

    <template v-if="useFooter">
      <section :class="['card__footer', transparent && 'transparent']">
        <slot name="footer"></slot>
      </section>
    </template>
  </article>
</template>

<script>
import { ObjectUtil, StringUtil } from '@/util';

export default {
  name: 'Card',
  props: {
    title: {
      type: String,
      default: null,
    },
    width: {
      type: String,
      default: 'auto',
    },
    height: {
      type: String,
      default: 'auto',
    },
    padding: {
      type: String,
      default: '10px 15px',
    },
    margin: {
      type: String,
      default: '5px 5px 20px 5px',
    },
    transparent: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      styleVariable: {
        '--width': this.$props.width,
        '--height': this.$props.height,
        '--padding': this.$props.padding,
        '--margin': this.$props.margin,
      },
    };
  },
  computed: {
    useHeader() {
      return StringUtil.isNotEmpty(this.$props.title) || ObjectUtil.has(this.$slots, 'action');
    },
    useFooter() {
      return ObjectUtil.has(this.$slots, 'footer');
    },
  },
};
</script>

<style lang="scss" scope>
.card {
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background-color: rgb(255, 255, 255);
  border-radius: $layout-border-radius;
  margin: var(--margin);
  box-shadow: 0 0 5px 0 rgb(0 0 0 / 10%);
  border: 1px solid $common-color-grey;
  width: var(--width);
  height: var(--height);

  &__header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 15px;
    height: 46px;
    border-bottom: 1px solid $common-color-grey;
  }

  &__action {
    display: flex;
  }

  &__body {
    overflow: scroll;
    border: 0;
    width: 100%;
    height: 100%;
    padding: var(--padding);

    &::-webkit-scrollbar {
      display: none;
    }
  }

  &__footer {
    display: flex;
    justify-content: right;
    align-items: center;
    padding: 10px;
    height: 50px;
    border-top: 1px solid $common-color-grey;
    border-radius: 0 0 $layout-border-radius $layout-border-radius;
  }

  &.transparent {
    background-color: transparent;
    box-shadow: none !important;

    &.transparent .card__header.transparent {
      background-color: transparent;
    }

    &.transparent .card__body.transparent {
      background-color: transparent;
    }

    &.transparent .card__footer.transparent {
      background-color: transparent;
      border-top: 0;
    }
  }
}
</style>
