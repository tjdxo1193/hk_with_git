<template>
  <ModalBase :title="title" :show="show" width="400px" @close="close">
    <FormBase v-bind="reasonForm" />
    <template #footer>
      <ButtonBase @click="close" type="normal">{{ cancelText }}</ButtonBase>
      <ButtonBase @click="check">{{ selectText }}</ButtonBase>
    </template>
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util/index.js';

import values from './values/inputReasonForDeleteModal.js';

export default {
  name: 'InputReasonForDeleteModal',
  emits: ['close', 'onModalDataReturn'],
  props: {
    title: {
      type: String,
      default: '삭제사유입력',
    },
    width: {
      type: String,
      default: '400px',
    },
    height: {
      type: String,
      default: '300px',
    },
    show: {
      type: Boolean,
      default: false,
    },
    selectText: {
      type: String,
      default: '선택',
    },
    cancelText: {
      type: String,
      default: '취소',
    },
  },
  data() {
    const { reasonForm } = values;
    return {
      reasonForm: {
        ...reasonForm.static,
        forms: reasonForm.forms(),
      },
    };
  },
  methods: {
    close() {
      this.reasonForm.forms = values.reasonForm.forms();
      this.$emit('close');
    },
    check() {
      const param = FormUtil.getData(this.reasonForm.forms);
      this.$emit('onModalDataReturn', param);
      this.close();
    },
  },
};
</script>

<style></style>
