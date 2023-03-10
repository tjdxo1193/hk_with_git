<template>
  <div class="action-bar" :style="styleVariable">
    <div>
      <slot></slot>
    </div>

    <div class="action-bar__button-wrap">
      <ButtonBase
        v-for="(button, index) in buttons"
        :key="button.name ?? index"
        v-bind="button"
        @click="$emit('button-click', { name: button.name })"
      >
        {{ button.label }}
      </ButtonBase>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ActionBar',
  emits: ['button-click'],
  props: {
    buttons: {
      type: Array,
      default: () => [],
    },
    justifyContent: {
      type: String,
      default: 'default',
      validator: (v) => ['default', 'start', 'center', 'end', 'space'].includes(v),
    },
  },
  data() {
    const justifyContentTable = {
      default: 'space-between',
      start: 'start',
      center: 'center',
      end: 'end',
      space: 'space-between',
    };

    return {
      styleVariable: {
        '--justify': justifyContentTable[this.$props.justifyContent],
      },
    };
  },
};
</script>

<style lang="scss" scope>
.action-bar {
  position: relative;
  display: flex;
  justify-content: var(--justify);
  align-items: center;
  width: 100%;
  height: auto;
  margin: 20px 15px;
  gap: 10px;

  &__button-wrap {
    display: flex;
    padding-right: 0px;
  }
}
</style>
