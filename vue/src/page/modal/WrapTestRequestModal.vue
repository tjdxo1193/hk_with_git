<template>
  <ModalBase v-bind="$props" @close="close">
    <FormBase v-bind="reasonForm" />
    <ActionBar @button-click="onClickButtonGroup" :buttons="buttonGroup.buttons" />
  </ModalBase>
</template>

<script>
import dayjs from 'dayjs';

import { FormUtil } from '@/util';

import values from './values/wrapTestRequestModal';

export default {
  name: 'WrapTestRequestModal',
  emits: ['close', 'save'],
  props: {
    title: {
      type: String,
      default: '규격변경승인요청',
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
      default: '570px',
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
        this.$props.initData.rvsDivPs = 'S';
        this.$props.initData.enfoDt = this.$props.initData.enfoDt ?? todayDate;
        this.$props.initData.rvsDt = this.$props.initData.rvsDt ?? todayDate;
        FormUtil.setData(this.reasonForm.forms, this.$props.initData);
      }
    },
  },
  data() {
    const { reasonForm, buttonGroup } = this.$copy(values);
    return {
      reasonForm: {
        ...reasonForm.static,
        forms: reasonForm.forms(),
      },
      buttonGroup: {
        buttons: buttonGroup.buttons,
      },
    };
  },
  methods: {
    onClickButtonGroup({ name }) {
      if (name == 'save') {
        this.save();
        return;
      }
      if (name == 'close') {
        this.close();
        return;
      }
    },
    save() {
      this.reasonForm.forms
        .validate()
        .then(() => {
          const item = FormUtil.getData(this.reasonForm.forms);
          this.$emit('save', item);
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
