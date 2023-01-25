<template>
  <div>
    <input ref="self" type="file" :accept="accept" @change="onChange" />
  </div>
</template>

<script>
let instance = {
  name: 'InputFile',
  emits: ['attachFile'],
  inheritAttrs: false,
  props: {
    value: {
      type: String,
      default: null,
    },
    accept: {
      type: String,
      default: null,
    },
  },
  methods: {
    onChange() {
      const fileInfo = this.$refs.self.files[0];
      this.$emit('attachFile', fileInfo);
    },
    init() {
      this.$refs.self.value = null;
    },
  },
};
export default instance;
</script>
