import { $axios } from '@/plugin';

export default {
  getDetailUserCommonCd(code) {
    return $axios.get('/combo/detailUserCommonCode', { value: code });
  },
  getUnitCombo() {
    return this.getDetailUserCommonCd('U001');
  },
  getStrgTermsCombo() {
    return this.getDetailUserCommonCd('U002');
  },
  getStrgPlaCombo() {
    return this.getDetailUserCommonCd('U003');
  },
  getStdCtetCombo() {
    return this.getDetailUserCommonCd('U004');
  },
  getSmpVolUnitCombo() {
    return this.getDetailUserCommonCd('U005');
  },
  getRvsReaCombo() {
    return this.getDetailUserCommonCd('U007');
  },
  getSlvJdgCombo() {
    return this.getDetailUserCommonCd('U008');
  },
  getRjtReaCombo() {
    return this.getDetailUserCommonCd('U010');
  },
  getClltMtdCombo() {
    return this.getDetailUserCommonCd('U011');
  },
  getClltMcnCombo() {
    return this.getDetailUserCommonCd('U012');
  },
  getClltPlaCombo() {
    return this.getDetailUserCommonCd('U013');
  },
  getSmpStrgMtdCombo() {
    return this.getDetailUserCommonCd('U014');
  },
  getEqmDivCombo() {
    return this.getDetailUserCommonCd('U015');
  },
  getEqmCrstCombo() {
    return this.getDetailUserCommonCd('U016');
  },
  getEqmSttCombo() {
    return this.getDetailUserCommonCd('U017');
  },
  getIstPlaCombo() {
    return this.getDetailUserCommonCd('U018');
  },
  getChkCylCombo() {
    return this.getDetailUserCommonCd('U019');
  },
  getEqmHisDivCombo() {
    return this.getDetailUserCommonCd('U020');
  },
  getAnsKndCombo() {
    return this.getDetailUserCommonCd('U021');
  },
  getAnsPpsCombo() {
    return this.getDetailUserCommonCd('U022');
  },
  getStabPlanStrgTermsCombo() {
    return this.getDetailUserCommonCd('U023');
  },
  getStabPlanStrgPlaCombo() {
    return this.getDetailUserCommonCd('U024');
  },
  getSmpReqReaCombo() {
    return this.getDetailUserCommonCd('U025');
  },
};
