<template>
  <ModalBase
    :title="title"
    :show="show"
    :aprReqDiv="aprReqDiv"
    width="600px"
    :top="50"
    @close="close"
  >
    <template #action>
      <ActionBar :buttons="buttonGroups.buttons" @button-click="onClickButton"></ActionBar>
    </template>

    <FormBase v-bind="list" />
  </ModalBase>
</template>

<script>
import dayjs from 'dayjs';

import { FormUtil, TokenUtil } from '@/util';

import values from './values/designateApproversModal';

export default {
  name: 'DesignateApproversModal',
  emits: ['close', 'modalReturnDataEvent'],
  props: {
    title: {
      type: String,
      default: '승인자지정',
    },
    show: {
      type: Boolean,
      default: false,
    },
    width: {
      type: String,
      default: '500px',
    },
    height: {
      type: String,
      default: '300px',
    },
    aprReqDiv: {
      type: String,
      default: null,
    },
    aprYn: {
      type: String,
      default: 'Y',
    },
  },
  mounted() {
    this.setUserInfo();
  },
  data() {
    const { list, buttonGroups } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
      },
      buttonGroups: {
        buttons: buttonGroups.buttons,
      },
    };
  },
  methods: {
    setUserInfo() {
      const todayDate = dayjs().format('YYYY-MM-DD');
      const useNm = TokenUtil.myName();
      const useUid = TokenUtil.myId();
      const data = { todayDate, useNm, useUid };
      FormUtil.setData(this.list.forms, data);
    },
    onClickButton({ name }) {
      if (name === 'approveRequest') {
        this.hasApprover();
      }
    },
    hasApprover() {
      return this.list.forms.validate().then(() => {
        this.approve();
      });
    },
    approve() {
      const aprUid = FormUtil.getValue(this.list.forms, 'useUid');
      const param = { aprReqDiv: this.$props.aprReqDiv, aprUid };
      this.$emit('modalReturnDataEvent', param);
      this.close();
    },
    close() {
      this.$emit('close');
    },
  },
};
</script>
