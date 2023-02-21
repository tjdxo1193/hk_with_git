export default {
  debounce(fn, sleep = 1000) {
    let debounceFn = null;
    return function (...args) {
      window.clearTimeout(debounceFn);
      const _this = this;
      debounceFn = window.setTimeout(() => fn.apply(_this, args), sleep);
    };
  },
  throttle(fn, sleep = 1000) {
    let running = false;
    return function (...args) {
      if (!running) {
        running = true;
        const _this = this;
        fn.apply(_this, args);
        setTimeout(() => (running = false), sleep);
      }
    };
  },
  clearFocus() {
    window.document.activeElement.blur();
  },
};
