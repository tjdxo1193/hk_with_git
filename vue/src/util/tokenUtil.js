import { getterType } from '@/const';
import store from '@/store';

const claims = () => ({ ...(store.getters[getterType.MY_CLAIMS] ?? {}) });

export default {
  iamSuper() {
    return claims()?.isSuperUser === 'true';
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
