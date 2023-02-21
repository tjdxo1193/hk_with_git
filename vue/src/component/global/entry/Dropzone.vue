<template>
  <article :id="state.id"></article>
</template>

<script>
//* ref: https://github.com/eonnine/file-attacher
import fileAttacher from 'file-attacher';

import { ObjectUtil } from '@/util';

fileAttacher.config({
  fileIds: ['fileIdx', 'fileSrlno'],
  validate: {
    // * LMIS_SRC의 application.yml 파일의 max-file-size와 같은 크기로 작성해야합니다.
    size: 1024 * 1024 * 10,
  },
});

export default {
  name: 'FileAttacher',
  emits: ['created'],
  inheritAttrs: false,
  components: {
    fileAttacher,
  },
  props: {
    option: {
      type: Object,
      default: () => ({}),
    },
  },
  mounted() {
    const option = ObjectUtil.mergeMap(this.getDefaultOption(), this.$props.option);
    const instance = fileAttacher.create(this.state.id, option);

    instance.fetch = (fileIdx) => this.customFetch(fileIdx);

    this.instance = instance;
    this.$emit('created', instance);
  },
  beforeUnmount() {
    this.instance.clear();
    this.instance.destroy();
  },
  data() {
    return {
      instance: null,
      state: {
        id: this.generateId(),
      },
    };
  },
  methods: {
    getDefaultOption() {
      return {
        fileIdx: ['fileIdx', 'fileSrlno'],
      };
    },
    customFetch(fileIdx) {
      return this.$axios
        .get(`/files/${fileIdx}`)
        .then((response) => {
          this.instance.addFiles(response.data);
          return response;
        })
        .catch(() => {
          this.$error(this.$message.error.fetchFiles);
        });
    },
    generateId() {
      return `auigrid-${Math.trunc(Math.random() * 10 ** 17)}`;
    },
  },
};
</script>

<style></style>
