<template>
  <ModalBase v-bind="$props" @close="close">
    <FormBase v-bind="rejectionReason" />
    <ActionBar @button-click="onClickButtonGroup" :buttons="buttonsGroup.buttons" />
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/rejectionReasonModal';

export default {
  name: 'RejectionReasonModal',
  emits: ['close', 'check'],
  props: {
    title: {
      type: String,
      default: '반려사유',
    },
    show: Boolean,
    width: {
      type: String,
      default: '500px',
    },
    height: {
      type: String,
      default: '300px',
    },
  },
  data() {
    const { rejectionReason, buttonsGroup } = this.$copy(values);
    return {
      rejectionReason: {
        ...rejectionReason.static,
        forms: rejectionReason.forms(),
      },
      buttonsGroup: {
        buttons: buttonsGroup.buttons,
      },
    };
  },
  watch: {
    show() {
      if (this.$props.show) {
        this.rejectionReason.forms = values.rejectionReason.forms();
      }
    },
  },
  methods: {
    onClickButtonGroup({ name }) {
      if (name == 'close') {
        this.close();
        return;
      }
      if (name == 'check') {
        this.check();
        this.close();
        return;
      }
    },
    close() {
      this.$emit('close');
    },
    check() {
      const item = FormUtil.getData(this.rejectionReason.forms);
      this.$emit('check', item);
    },
  },
};
</script>
