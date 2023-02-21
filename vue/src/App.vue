<template>
  <div id="root">
    <Alert />
    <ESign />
    <Progress />
    <router-view></router-view>
  </div>
</template>

<script>
import { Alert, ESign, Progress } from '@/component/feedback';
import { actionType, getterType, message, mutationType } from './const';
import { TokenUtil } from './util';
import { mapGetters } from 'vuex';

export default {
  name: 'App',
  components: {
    Alert,
    ESign,
    Progress,
  },
  mounted() {
    document.addEventListener('keydown', this.checkAuthExpire);
    document.addEventListener('mousedown', this.checkAuthExpire);
  },
  beforeUnmount() {
    document.removeEventListener('keydown', this.checkAuthExpire);
    document.removeEventListener('mousedown', this.checkAuthExpire);
  },
  methods: {
    checkAuthExpire() {
      if (this.isLoggedIn && TokenUtil.isExpiredAuth()) {
        this.$warn(message.warn.expireAuthentication).then(() => {
          this.$store.dispatch(actionType.LOGOUT);
          this.$store.commit(mutationType.TO_WELCOME);
        });
      }
    },
  },
  computed: {
    ...mapGetters({
      isLoggedIn: getterType.IS_LOGGED_IN,
    }),
  },
};
</script>

<style>
@import 'reset-css';
@import '@/assets/scss/index.scss';
</style>

<style lang="scss" scope>
#root {
  z-index: 100;
  width: 100%;
  min-height: 100vh;
}
</style>
