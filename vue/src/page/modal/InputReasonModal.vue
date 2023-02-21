<template>
  <ModalBase v-bind="$props" @close="close">
    <FormBase v-bind="reasonForm" />

    <ActionBar :buttons="buttonGroups.buttons" @button-click="onEventsButton" />
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/inputReasonModal';

export default {
  name: 'InputReasonModal',
  emits: ['close', 'check'],
  props: {
    title: {
      type: String,
      default: '사유입력',
    },
    show: Boolean,
    width: {
      type: String,
      default: '500px',
    },
    height: {
      tpye: String,
      default: '300px',
    },
  },
  data() {
    const { reasonForm, buttonGroups } = this.$copy(values);
    return {
      reasonForm: {
        ...reasonForm.static,
        forms: reasonForm.forms(),
      },
      buttonGroups: {
        buttons: buttonGroups.buttons,
      },
    };
  },
  methods: {
    onEventsButton({ name }) {
      if (name == 'check') {
        this.reasonForm.forms
          .validate()
          .then(() => {
            const param = FormUtil.getData(this.reasonForm.forms);
            this.$emit('modalReturnDataEvent', param);
            this.init();
            this.close();
          })
          .catch(() => {});
      }
      if (name == 'close') {
        this.init();
        this.close();
      }
    },
    init() {
      this.reasonForm.forms = values.reasonForm.forms();
    },
    close() {
      this.$emit('close');
    },
    check() {
      this.$emits('check');
    },
  },
};
</script>
