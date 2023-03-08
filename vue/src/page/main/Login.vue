<template>
  <div class="loginWrap">
    <div class="loginLeft">
      <div class="loginForm">
        <h1><img :src="require('@/assets/images/loginLogo.png')" /></h1>
        <form @keydown.enter.prevent>
          <SelectBase v-model="plantCode" :elements="plantCombo" />
          <div class="loginArea">
            <label for="id">ID</label>
            <ul>
              <li class="id"><img :src="require('@/assets/images/userIcon.png')" /></li>
              <li>
                <input
                  type="text"
                  name="id"
                  id="id"
                  maxlength="20"
                  placeholder=""
                  v-model="username"
                  @keydown.enter="submit"
                />
              </li>
            </ul>
          </div>
          <div class="loginArea">
            <label for="password">Password</label>
            <ul>
              <li class="password">
                <img :src="require('@/assets/images/passwordIcon.png')" />
              </li>
              <li>
                <input
                  type="password"
                  name="password"
                  id="password"
                  maxlength="15"
                  placeholder=""
                  v-model="password"
                  @keydown.enter="submit"
                />
              </li>
            </ul>
          </div>
          <div class="loginSearch">
            <span class="remember">
              <Checkbox
                v-model="isRememberUsername"
                label="Remember Me"
                :uncheckedValue="false"
                @keydown.enter="submit"
              />
            </span>
            <span><a href="#">아이디/비밀번호 찾기</a></span>
          </div>

          <button type="button" @click="submit">LOGIN</button>
        </form>
      </div>
    </div>
    <div class="loginRight"></div>
  </div>
</template>

<script>
import api from '@/api';
import { actionType, mutationType } from '@/const';
import { ObjectUtil } from '@/util';

export default {
  name: 'LoginPage',
  async created() {
    const { data } = await api.combo.common.getPlntCd();
    this.plantCombo = data;
  },
  mounted() {
    if (this.existsUserInfoInCookie()) {
      this.username = this.getCookie('username');
      this.plantCode = this.getCookie('plantCode');
      this.isRememberUsername = true;
    }
  },
  data() {
    return {
      username: null,
      password: null,
      plantCode: null,
      isRememberUsername: false,
      plantCombo: [],
    };
  },
  methods: {
    submit() {
      if (!this.validate()) {
        return;
      }
      this.login();
    },
    validate() {
      if (!this.plantCode) {
        this.$warn(this.$message.validate.pleaseSelectPlant);
        return false;
      }
      if (!this.username) {
        this.$warn(this.$message.validate.pleaseInputId);
        return false;
      }
      if (!this.password) {
        this.$warn(this.$message.validate.pleaseInputPassword);
        return false;
      }
      return true;
    },
    login() {
      this.$store
        .dispatch(actionType.LOGIN, {
          username: this.username,
          password: this.password,
          plntCd: this.plantCode,
        })
        .then(() => {
          this.endLoginFailProcess();
          
          if (this.isRememberUsername) {
            this.setCookie('plantCode', this.plantCode);
            this.setCookie('username', this.username);
          } else {
            this.clearUsernameInCookie();
          }
        })
        .catch((error) => {
          const message = error?.response?.data?.message;
          if (message) {
            this.startLoginFtailPerocess();
            this.$error(message).finally(() => this.endLoginFailProcess());
          }
        });
    },
    startLoginFtailPerocess() {
      this.$store.commit(mutationType.START_LOGIN_FAIL_PROCESS);
    },
    endLoginFailProcess() {
      this.$store.commit(mutationType.END_LOGIN_FAIL_PROCESS);
    },
    existsUserInfoInCookie() {
      const cookie = this.getCookieAsObject();
      return ObjectUtil.has(cookie, 'username') && ObjectUtil.has(cookie, 'plantCode');
    },
    clearUsernameInCookie() {
      const expires = new Date();
      expires.setFullYear(expires.getFullYear() - 1);
      document.cookie = `username=; expires=${expires};`;
    },
    setCookie(name, value) {
      const expires = new Date();
      expires.setFullYear(expires.getFullYear() + 1);
      document.cookie = `${name}=${value}; domain=${window.location.hostname}; path=/; expires=${expires}; SameSite=strict;`;
    },
    getCookie(name) {
      const cookie = this.getCookieAsObject();
      return cookie[name];
    },
    getCookieAsObject() {
      return document.cookie.split(';').reduce((acc, cookie) => {
        const [key, value] = cookie.trim().split('=');
        acc[key] = value;
        return acc;
      }, {});
    },
  },
};
</script>

<style lang="scss" scope>
.loginWrap {
  display: flex;

  & .loginLeft {
    width: 30%;
    height: 100vh;
    padding: 0 113px;
    display: flex;
    align-items: center;

    & .loginForm {
      width: 100%;

      & select {
        height: 46px;
        margin-top: 40px;
        border-radius: 3px;
      }

      & .loginArea {
        margin-top: 30px;
      }

      & .loginArea label {
        font-size: 11pt;
        color: #585858;
        font-weight: 700;
      }

      & ul {
        border: 1px solid #ddd;
        border-radius: 3px;
        padding: 8px 0;
        display: flex;
        margin-top: 5px;
      }

      & ul li:nth-child(1) {
        width: 48px;
        display: flex;
        align-items: center;
        flex-wrap: wrap;
        text-align: center;
      }

      & ul li:nth-child(1) img {
        margin: 0 auto;
      }

      & ul li:nth-child(2) {
        width: calc(100% - 48px);
        padding: 0 10px;
        border-left: 1px solid #ddd;
      }

      & input[type='text'] {
        border: none;
        outline: 0;
        height: 28px;
        display: inline-block;
        vertical-align: middle;
        width: 100%;
      }

      & input[type='text']:hover {
        box-shadow: none;
      }

      & input[type='password'] {
        border: none;
        height: 28px;
        display: inline-block;
        vertical-align: middle;
        width: 100%;
      }
    }

    & .loginSearch {
      margin-top: 15px;

      & span {
        font-size: 9pt;
      }

      & span + span {
        margin-left: 30px;
      }

      & label {
        font-size: 9pt;
        color: #585858;
      }
    }

    & button {
      color: #fff;
      background: #276ef1;
      display: inline-block;
      width: 100%;
      height: 46px;
      border-radius: 3px;
      margin-top: 30px;
      font-size: 11pt;
      font-weight: 700;

      &:hover {
        background: #0e55d7;
      }
    }

    & select {
      border: 1px solid #ddd;

      &:hover {
        box-shadow: none;
      }
    }
  }

  & .loginRight {
    width: 70%;
    height: 100vh;
    background: url('@/assets/images/loginBg.jpg') center no-repeat;
    background-size: cover;
  }
}
</style>
