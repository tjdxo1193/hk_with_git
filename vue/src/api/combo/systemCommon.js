import { $axios } from '@/plugin';

export default {
  getDetailSystemCommonCd(code) {
    return $axios.get('/combo/detailSystemCommonCode', { value: code });
  },
  getVdrDivCombo() {
    return this.getDetailSystemCommonCd('S004');
  },
  getSpecTypCombo() {
    return this.getDetailSystemCommonCd('S006');
  },
  getJdgTypCombo() {
    return this.getDetailSystemCommonCd('S007');
  },
  getSpecProcCombo() {
    return this.getDetailSystemCommonCd('S008');
  },
  getAnsCylDivCombo() {
    return this.getDetailSystemCommonCd('S009');
  },
  getPackageDivCombo() {
    return this.getDetailSystemCommonCd('S010');
  },
  getSytJdgCombo() {
    return this.getDetailSystemCommonCd('S011');
  },
  getRstJdgCombo() {
    return this.getDetailSystemCommonCd('S012');
  },
  getAnsProcCombo() {
    return this.getDetailSystemCommonCd('S013');
  },
  getRstProcCombo() {
    return this.getDetailSystemCommonCd('S014');
  },
  getPitmDivCombo() {
    return this.getDetailSystemCommonCd('S018');
  },
  getPkgMtrDivCombo() {
    return this.getDetailSystemCommonCd('S021');
  },
  getPmSpcmProcCombo() {
    return this.getDetailSystemCommonCd('S022');
  },
  getAnsTypCombo() {
    return this.getDetailSystemCommonCd('S023');
  },
  getRptDivCombo() {
    return this.getDetailSystemCommonCd('S024');
  },
  getNonCfmProcCombo() {
    return this.getDetailSystemCommonCd('S025');
  },
  getSmpDivCombo() {
    return this.getDetailSystemCommonCd('S026');
  },
  getSmpProcCombo() {
    return this.getDetailSystemCommonCd('S027');
  },
  getSmpUseProcCombo() {
    return this.getDetailSystemCommonCd('S028');
  },
  getSbtAnsProcCombo() {
    return this.getDetailSystemCommonCd('S029');
  },
  getSbtPlnSttCombo() {
    return this.getDetailSystemCommonCd('S030');
  },
  getSbtAnsSttCombo() {
    return this.getDetailSystemCommonCd('S031');
  },
  getAddSmpProcCombo() {
    return this.getDetailSystemCommonCd('S032');
  },
};
