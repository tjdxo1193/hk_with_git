<template>
  <div>{{ value }}</div>
</template>

<script>
export default {
  name: 'TextView',
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
