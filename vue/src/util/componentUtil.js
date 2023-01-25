export default {
  getEventListener(attrs = {}) {
    return Object.entries(attrs).reduce((acc, [k, f]) => {
      if (typeof k === 'string' && k.startsWith('on')) {
        acc[k] = f;
      }
      return acc;
    }, {});
  },
};
