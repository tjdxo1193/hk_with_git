<template>
  <div class="exchange-panel">
    <component v-bind="alignComponent.props.component" :is="alignComponent.is">
      <div class="exchange-panel__item">
        <ButtonArrow
          v-bind="alignComponent.props.firstArrow"
          @click="({ event }) => $emit('click', { name: 'first', event })"
        />
      </div>
      <div class="exchange-panel__item">
        <ButtonArrow
          v-bind="alignComponent.props.secondArrow"
          @click="({ event }) => $emit('click', { name: 'second', event })"
        />
      </div>
    </component>
  </div>
</template>

<script>
const alignComponentTable = {
  horizontal: {
    is: 'Horizontal',
    props: {
      component: {
        alignItems: 'center',
      },
      firstArrow: {
        direction: 'down',
      },
      secondArrow: {
        direction: 'up',
      },
    },
  },
  vertical: {
    is: 'Vertical',
    props: {
      component: {
        justifyContent: 'space',
        alignItems: 'center',
      },
      firstArrow: {
        direction: 'right',
      },
      secondArrow: {
        direction: 'left',
      },
    },
  },
};

export default {
  name: 'ExchangePanel',
  emits: ['click'],
  inheritAttrs: false,
  props: {
    direction: {
      type: String,
      default: 'horizontal',
      validator: (v) => ['horizontal', 'vertical'].includes(v),
    },
  },
  computed: {
    alignComponent() {
      return alignComponentTable[this.$props.direction];
    },
  },
};
</script>

<style lang="scss" scope>
.exchange-panel {
  width: 100%;
  max-width: 360px;
  height: 130px;

  &__item {
    display: flex;
    justify-content: center;
    align-items: center;
  }
}
</style>
