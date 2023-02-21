<template>
  <FormWithHeader v-bind="inputForm" />
</template>

<script>
import values from './values/pwdManage';

export default {
  name: 'PwdManagePage',
  data() {
    const { inputForm } = this.$copy(values);

    return {
      inputForm: {
        ...inputForm.static,
        forms: inputForm.forms(),
      },
    };
  },
};
</script>

<style></style>
