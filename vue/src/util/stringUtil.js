export default {
  isEmpty(v) {
    return v == null || String(v).trim() === '';
  },
  isNotEmpty(v) {
    return !this.isEmpty(v);
  },
};
