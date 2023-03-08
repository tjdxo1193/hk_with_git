import { getterType } from '@/const';
import store from '@/store';

const claims = () => ({ ...(store.getters[getterType.MY_CLAIMS] ?? {}) });

const expireAtOfRefreshToken = () => claims()?.exp;

export default {
  iamSuper() {
    return claims()?.isSuperUser === 'true';
  },
  isExpiredAuth() {
    const tokenExpireAt = expireAtOfRefreshToken();
    if (tokenExpireAt == null) {
      return false;
    }

    const now = new Date().getTime();
    const nowStr = String(now);
    let exp = tokenExpireAt;

    if (nowStr.length > exp.length) {
      const diff = nowStr.length - exp.length;
      exp = exp * (10 ** diff);
    }
    return now >= exp;
  },
  myId() {
    return claims()?.userId;
  },
  myLoginId() {
    return claims()?.loginId;
  },
  myName() {
    return claims()?.userNm;
  },
  myDeptCode() {
    return claims()?.dptCd;
  },
  myPlantCode() {
    return claims()?.plntCd;
  },
  myAuthority() {
    const { authority } = claims();
    return authority ? { ...JSON.parse(authority) } : null;
  },
  myMenus() {
    return this.myAuthority()?.myMenus ?? [];
  },
};
