<script>
import { actionType, mutationType } from '@/const';
import store from '@/store';

export default {
  name: 'WelcomePage',
  created() {
    if (this.isNoSSOLogin()) {
      this.toLoginPage();
    } else {
      this.logout();
      this.toSSOLoginPage();
    }
  },
  methods: {
    async toSSOLoginPage() {
      const { data: ssoLoginPageUrl } = await this.$axios.get('/sso/login');
      location.href = ssoLoginPageUrl;
    },
    isNoSSOLogin() {
      const env = process.env.CURRENT_ENV;
      return env === 'local';
    },
    toLoginPage() {
      store.commit(mutationType.TO_LOGIN);
    },
    logout() {
      store.commit(actionType.LOGOUT);
    },
  },
};
</script>
