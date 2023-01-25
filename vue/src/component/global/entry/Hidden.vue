<template>
  <input type="hidden" :value="value" />
</template>

<script>
export default {
  name: 'Hidden',
  inheritAttrs: false,
  props: {
    value: {
      type: [String, Number],
      default: null,
    },
  },
};
</script>

<style></style>
