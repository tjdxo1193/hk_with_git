<template></template>
<script>
import { mutationType } from '@/const';
import store from '@/store';

export default {
  name: 'WelcomePage',
  created() {
    this.toLoginPage();
  },
  methods: {
    toLoginPage() {
      store.commit(mutationType.TO_LOGIN);
    },
  },
};
</script>
