<template>
  <ModalBase v-bind="$props" @close="close">
    <FormWithHeader v-bind="regReason" />

    <ActionBar :buttons="button.buttons" @button-click="onClickCloseBtn" />
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/reasonForSpecChangeModal';

export default {
  name: 'ReasonForSpecChangeModal',
  emits: ['close'],
  props: {
    show: Boolean,
    title: {
      type: String,
      default: '규격변경사유등록',
    },
    width: {
      type: String,
      default: '850px',
    },
    height: {
      type: String,
      default: '500px',
    },
  },
  mounted() {
    this.regReason.forms = values.regReason.forms();
  },
  data() {
    const { regReason, button } = this.$copy(values);
    return {
      regReason: {
        ...regReason.static,
        forms: regReason.forms(),
      },
      button: {
        buttons: button.buttons,
      },
    };
  },
  methods: {
    onClickCloseBtn({ name }) {
      if (name == 'reg') {
        this.saveReason();
      }
    },
    saveReason() {
      const parameter = FormUtil.getData(this.regReason.forms);

      this.$eSign(() => this.$axios.post('', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          close();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
          close();
        });
    },
    close() {
      this.$emit('close');
    },
  },
};
</script>
