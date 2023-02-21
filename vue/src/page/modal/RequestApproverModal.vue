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

import values from './values/requestApproverModal';

export default {
  name: 'RequestApproverModal',
  emits: ['close', 'modalReturnDataEvent'],
  props: {
    title: {
      type: String,
      default: '승인요청',
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
      if (name === 'requestApprove') {
        this.hasRequester();
      }
    },
    hasRequester() {
      return this.list.forms.validate().then(() => {
        this.requestApprove();
      });
    },
    requestApprove() {
      const aprReqUid = FormUtil.getValue(this.list.forms, 'useUid');
      const aprUid = FormUtil.getValue(this.list.forms, 'user');
      if (aprReqUid === aprUid) {
        return this.$warn(this.$message.warn.approveRequestEqualApprover);
      } else {
        const approveInfo = { aprReqDiv: this.$props.aprReqDiv, aprReqUid, aprUid };
        this.$emit('modalReturnDataEvent', approveInfo);
        this.close();
      }
    },
    close() {
      FormUtil.setData(this.list.forms, { user: '' });
      this.$emit('close');
    },
  },
};
</script>
