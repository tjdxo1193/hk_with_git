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

export default {
  name: 'App',
  components: {
    Alert,
    ESign,
    Progress,
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
