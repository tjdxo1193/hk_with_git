<template>
  <ModalBase v-bind="$props" @close="close">
    <FormWithHeader v-bind="inputForm" @button-click="onClickButton" />
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/passwordChangeModal';
export default {
  name: 'PasswordChangeModal',
  emits: ['close'],
  props: {
    title: {
      type: String,
      default: '비밀번호변경',
    },
    show: {
      type: Boolean,
      default: false,
    },
  },
  watch: {
    show() {
      if (this.$props.show) {
        this.inputForm.forms = values.inputForm.forms();
        this.fetchUserInfo();
      }
    },
  },
  data() {
    const { inputForm } = this.$copy(values);
    return {
      inputForm: {
        ...inputForm.static,
        forms: inputForm.forms(),
      },
    };
  },
  methods: {
    fetchUserInfo() {
      const { forms } = this.inputForm;
      this.$axios
        .get('sy/pwdChange', null)
        .then(({ data }) => FormUtil.setData(forms, data))
        .catch(() => {
          this.$error(this.$message.error.fetchData);
        });
    },
    onClickButton({ name }) {
      if (name == 'save') {
        this.inputForm.forms
          .validate()
          .then(() => {
            this.update();
          })
          .catch(() => {
            this.$warn(this.$message.warn.pwdNotIncludeRolue);
          });
      }
      if (name === 'close') {
        this.$emit('close');
      }
    },
    close() {
      this.$emit('close');
    },
    returnMessage() {},
    update() {
      const param = FormUtil.getData(this.inputForm.forms);
      this.$eSignWithReason(() => this.$axios.put('/sy/pwdChange', param))
        .then(() => {
          this.$info(this.$message.info.updated);
          this.$emit('close');
        })
        .catch(({ response }) => {
          const code = response.data.message;
          const { errorCodeMessage } = this.$copy(values);
          const matchedErrorCode = errorCodeMessage.find(
            ({ code: errorCode }) => errorCode === code,
          );

          if (matchedErrorCode != null) {
            this.$error(matchedErrorCode.message);
            return;
          }
          this.$error(this.$message.error.updateData);
        });
    },
  },
};
</script>

<style></style>
