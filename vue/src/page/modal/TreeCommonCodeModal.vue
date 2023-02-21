<template>
  <ModalBase :show="show" width="800px" :top="100" plain @close="$emit('close')">
    <TreeCommonCodeManage
      :title="title"
      :code="code"
      :readonly="readonly"
      height="500px"
      margin="0"
    />
  </ModalBase>
</template>

<script>
import TreeCommonCodeManage from '@/page/sy/TreeCommonCodeManage';

export default {
  name: 'TreeCommonCodeModal',
  emits: ['close'],
  components: {
    TreeCommonCodeManage,
  },
  props: {
    title: String,
    code: String,
    readonly: {
      type: Boolean,
      default: true,
    },
    show: {
      type: Boolean,
      default: false,
    },
  },
};
</script>

<style></style>
