<script>
import { actionType, mutationType } from '@/const';
import router from '@/router';
import store from '@/store';

export default {
  name: 'SSOLogin',
  async created() {
    try {
      this.login(this.$route.query);
      const isVerified = await store.dispatch(actionType.VERIFY_TOKEN);
      isVerified ? this.goHome() : this.logout();
    } catch {
      this.logout();
    }
  },
  methods: {
    login({ accessToken = null, refreshToken = null, claims = null }) {
      store.commit(mutationType.AUTHENTICATE, {
        accessToken,
        refreshToken,
        claims: JSON.parse(claims),
      });
    },
    logout() {
      store.commit(mutationType.DE_AUTHENTICATE);
      store.commit(mutationType.TO_LOGIN);
    },
    goHome() {
      router.push('/');
    },
  },
};
</script>
