<template>
  <ModalBase v-bind="$props" @close="close">
    <FormBase v-bind="reasonForm" />
    <ActionBar v-bind="mainButtons" @button-click="onClickMainButtons" />
  </ModalBase>
</template>

<script>
import dayjs from 'dayjs';

import { FormUtil } from '@/util';

import values from './values/inputReasonForReviseModal.js';

export default {
  name: 'InputReasonForItemReviseModal',
  emits: ['close', 'modalReturnDataEvent'],
  props: {
    title: {
      type: String,
      default: '품목개정승인요청',
    },
    show: {
      type: Boolean,
      default: false,
    },
    width: {
      type: String,
      default: '700px',
    },
    height: {
      type: String,
      default: '600px',
    },
    initData: {
      type: Object,
      default: {},
    },
  },
  watch: {
    show() {
      if (this.$props.show) {
        const todayDate = dayjs().format('YYYY-MM-DD');
        this.$props.initData.rvsDivPs = 'P';
        this.$props.initData.enfoDt = todayDate;
        this.$props.initData.rvsDt = todayDate;
        FormUtil.setData(this.reasonForm.forms, this.$props.initData);
      }
    },
  },
  data() {
    const { reasonForm, mainButtons } = this.$copy(values);
    return {
      reasonForm: {
        ...reasonForm.static,
        forms: reasonForm.forms(),
      },
      mainButtons: {
        ...mainButtons.static,
      },
    };
  },
  methods: {
    onClickMainButtons({ name }) {
      if (name === 'save') {
        this.reasonForm.forms
          .validate()
          .then(() => {
            const data = FormUtil.getData(this.reasonForm.forms);
            this.$emit('modalReturnDataEvent', data);
            this.close();
          })
          .catch(() => {});
      }
      if (name === 'close') {
        this.close();
      }
    },
    init() {
      this.reasonForm.forms = values.reasonForm.forms();
    },
    close() {
      this.init();
      this.$emit('close');
    },
  },
};
</script>
