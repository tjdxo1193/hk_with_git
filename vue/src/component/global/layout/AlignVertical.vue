<template>
  <div class="align-vertical" :style="styleVariable">
    <template v-for="(slot, index) in $slots" :key="index">
      <component :is="slot"></component>
    </template>
  </div>
</template>

<script>
export default {
  name: 'AlignVertical',
  props: {
    alignItems: {
      type: String,
      default: 'default',
      validator: (v) => ['default', 'start', 'center', 'end'].includes(v),
    },
    justifyContent: {
      type: String,
      default: 'default',
      validator: (v) => ['default', 'start', 'center', 'end', 'space'].includes(v),
    },
  },
  data() {
    const alignItemsTable = {
      default: 'stretch',
      start: 'start',
      center: 'center',
      end: 'end',
    };

    const justifyContentTable = {
      default: 'start',
      start: 'start',
      center: 'center',
      end: 'end',
      space: 'space-between',
    };

    return {
      styleVariable: {
        '--align-items': alignItemsTable[this.$props.alignItems],
        '--justify-content': justifyContentTable[this.$props.justifyContent],
      },
    };
  },
};
</script>

<style lang="scss" scope>
.align-vertical {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: var(--justify-content);
  align-items: var(--align-items);
}
</style>
