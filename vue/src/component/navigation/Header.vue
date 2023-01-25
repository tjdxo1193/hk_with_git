<template>
  <div class="header">
    <div class="topLogo">
      <h1>
        <img @click="toHome" :src="require('@/assets/images/header_logo.png')" class="home-link" />
      </h1>
    </div>
    <div class="inner">
      <div class="top_menu">
        <span><a href="#">오늘의 할일</a></span>
        <span><a @click="toAudit">AuditTrail</a></span>
        <span><a @click="changePw">Password change</a></span>
        <span><a @click="logout">LOGOUT</a></span>
      </div>

      <PasswordChangeModal :show="passwordChangeModal.show" @close="hidePasswordChangeModal()" />

      <div class="user">
        <BreadCrumb />
        <span class="user__info">{{ name }}님이 로그인하였습니다</span>
      </div>
    </div>
  </div>
</template>

<script>
import { actionType, mutationType } from '@/const';
import { PasswordChangeModal } from '@/page/modal';
import store from '@/store';
import { TokenUtil } from '@/util';

import BreadCrumb from './BreadCrumb.vue';

export default {
  name: 'DefaultHeader',
  components: {
    BreadCrumb,
    PasswordChangeModal,
  },
  data() {
    return {
      name: TokenUtil.myName(),

      passwordChangeModal: {
        show: false,
      },
    };
  },
  methods: {
    showPopupToDoList() {},
    resetMenuName() {},
    changePw() {
      this.showPasswordChangeModal();
    },
    showPasswordChangeModal() {
      this.$setState('passwordChangeModal', { show: true });
    },
    hidePasswordChangeModal() {
      this.$setState('passwordChangeModal', { show: false });
    },
    logout() {
      store.dispatch(actionType.LOGOUT);
      store.commit(mutationType.TO_LOGIN);
    },
    toHome() {
      this.$router.push('/home');
    },
    toAudit() {
      this.$router.push('/sy/auditTrail');
    },
  },
};
</script>

<style lang="scss" scope>
.header {
  $header-font-color: rgb(245, 245, 245);

  & .home-link {
    cursor: pointer;
  }

  & {
    height: 88px;
    display: flex;
    align-items: center;
    position: fixed;
    width: 100%;
    top: 0;
    left: 0;
    z-index: 1;
    background: rgb(39, 110, 241);
  }

  & .topLogo {
    width: $layout-side-width;
    border-right: 1px solid rgb(55, 125, 255);
    height: 88px;
    display: flex;
    align-items: center;
  }

  & .topLogo h1 {
    margin: 0 auto;
  }

  & .inner {
    width: calc(100% - $layout-side-width);
    height: 88px;
  }

  & .inner .top_menu {
    height: 58px;
    border-bottom: 1px solid rgb(55, 125, 255);
    padding: 0 40px;
    display: flex;
    align-items: center;
    justify-content: right;
  }

  & .inner .top_menu span {
    margin: 0 10px;
  }

  & .inner .top_menu span a {
    cursor: pointer;
    color: $header-font-color;
    font-size: 14.67px;
    font-weight: 400;
  }

  & .inner .top_menu span a:hover {
    color: $common-color-hover-orange;
  }

  & .inner .top_menu span:last-child {
    margin-right: 0;
  }

  & .inner .user {
    height: 30px;
    padding: 0 40px 0 15px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 13px;
    color: $header-font-color;
  }

  & .inner .user .user__info:before {
    content: '';
    width: 15px;
    height: 15px;
    display: inline-block;
    vertical-align: middle;
    background: url('@/assets/images/mainUser.png') no-repeat;
    margin-top: -3px;
    padding-left: 20px;
  }
}
</style>
