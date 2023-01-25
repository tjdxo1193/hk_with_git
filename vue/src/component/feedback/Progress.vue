<template>
  <div :class="['progress-container', isLoading && 'progressing']"></div>
</template>

<script>
import { mapGetters } from 'vuex';

import { getterType } from '@/const';

export default {
  name: 'Progress',
  computed: {
    ...mapGetters({
      isLoading: getterType.IS_LOADING,
    }),
  },
};
</script>

<style lang="scss" scope>
.progress-container {
  z-index: -1;
  position: absolute;
  width: 100%;
  height: 100%;
  opacity: 0;
  background-color: transparent;

  &.progressing {
    cursor: wait;
    opacity: 1;
    z-index: 9990 !important;
  }
}
</style>
