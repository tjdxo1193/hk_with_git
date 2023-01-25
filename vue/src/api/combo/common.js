import { $axios } from '@/plugin';

export default {
  getUserList() {
    return $axios.get('/combo/user');
  },
  getApproverList(code = {}) {
    return $axios.get('/combo/getApproverList', code);
  },
  getReviewerList(code = {}) {
    return $axios.get('/combo/getReviewerList', code);
  },
  getUpperTreeCd(code = '') {
    return $axios.get('/combo/upperTreeCd', { value: code });
  },
  getTreeCd(code) {
    return $axios.get('/combo/treeCd', { value: code });
  },
  getAnsCyl() {
    return $axios.get('/combo/ansCyl');
  },
  getPlntCd() {
    return $axios.get('/combo/plntCd');
  },
  getDpt() {
    return $axios.get('/combo/dpt');
  },
  getdptByLevel(level) {
    return $axios.get('/combo/dptByLevel', { value: level });
  },
  getAmItmCd() {
    return $axios.get('/combo/amItmCd');
  },
  getYear() {
    return $axios.get('/combo/year');
  },
  getTestMaterialTreeCd(code = '') {
    return $axios.get('/combo/testMaterialTreeCd', { value: code });
  },
  getTestTerm() {
    return $axios.get('/combo/testTerm');
  },
};
