<template>
  <ModalBase v-bind="$props" @close="close">
    <FormWithHeader v-bind="regReason" />
    <ActionBar :buttons="regButton.buttons" @button-click="onClickButtons" />
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/reasonForItemRevisionModal';

export default {
  name: 'ReasonForItemRevisionModal',
  emits: ['close', 'reg'],
  props: {
    title: {
      type: String,
      default: '자재개정사유입력',
    },
    show: {
      type: Boolean,
      default: false,
    },
    width: {
      type: String,
      default: '800px',
    },
    height: {
      type: String,
      default: '450px',
    },
    initData: {
      type: Object,
      default: {},
    },
  },
  watch: {
    show() {
      if (this.$props.show) {
        FormUtil.setData(this.regReason.forms, this.$props.initData);
      }
    },
  },
  data() {
    const { regReason, regButton } = this.$copy(values);
    return {
      regReason: {
        ...regReason.static,
        forms: regReason.forms(),
      },
      regButton: {
        buttons: regButton.buttons,
      },
    };
  },
  methods: {
    onClickButtons({ name }) {
      if (name === 'close') {
        this.close();
      }
      if (name === 'reg') {
        this.reg();
      }
    },
    reg() {
      this.regReason.forms
        .validate()
        .then(() => {
          const item = FormUtil.getData(this.regReason.forms);
          this.$emit('reg', item);
          this.close();
        })
        .catch(() => {});
    },
    close() {
      this.$emit('close');
    },
  },
};
</script>

<style></style>
