export default {
  has(obj, key) {
    return Object.prototype.hasOwnProperty.call(obj, key);
  },
  hasIgnoreCase(obj, key) {
    return (
      Object.keys(obj).filter((objKey) => objKey.toLowerCase() === key.toLowerCase()).length > 0
    );
  },
  get(obj, key) {
    return this.has(obj, key) ? obj[key] : null;
  },
  equals(obj, key, value) {
    return this.get(obj, key) == value;
  },
  setObject(obj, keyChain, value) {
    const keys = keyChain.split('.');
    const lastKey = keys.pop();

    for (const key of keys) {
      if (obj == null || !this.has(obj, key)) {
        obj[key] = {};
      }
      obj = obj[key];
    }

    if (this.isObjectOrArray(value)) {
      obj[lastKey] = Array.isArray(value)
        ? this.mergeArray(obj[lastKey], value)
        : this.shallowMergeObject(obj[lastKey], value);
    } else {
      obj[lastKey] = value;
    }
  },
  copy(obj, isDeep) {
    if (obj == null) {
      return null;
    }
    if (this.isFunction(obj)) {
      return obj;
    }
    if (Array.isArray(obj)) {
      return this.copyArray(obj, isDeep);
    }
    return this.copyObject(obj, isDeep);
  },
  copyArray(arr, isDeep = true) {
    return arr.map((value) => {
      if (isDeep) {
        if (this.isFunction(value)) {
          return value;
        } else if (Array.isArray(value)) {
          return this.copyArray(value);
        } else if (this.isObject(value)) {
          return this.copyObject(value);
        } else {
          return value;
        }
      } else {
        return value;
      }
    });
  },
  copyObject(obj, isDeep = true) {
    return Object.entries(obj).reduce((acc, [key, value]) => {
      if (isDeep) {
        if (Array.isArray(value)) {
          acc[key] = this.copyArray(value);
        } else if (this.isObject(value)) {
          acc[key] = this.copyObject(value);
        } else {
          acc[key] = value;
        }
      } else {
        acc[key] = value;
      }
      return acc;
    }, {});
  },
  mergeMap(target, obj) {
    return this.mergeObject(this.copy(target), this.copy(obj));
  },
  mergeArray(target = [], arr = []) {
    return [...target, ...arr];
  },
  mergeObject(target = {}, obj = {}) {
    return Object.entries(obj).reduce((acc, [k, v]) => {
      if (Array.isArray(v)) {
        acc[k] = this.mergeArray(acc[k], v);
      } else if (this.isObject(v)) {
        acc[k] = this.mergeObject(acc[k], v);
      } else {
        acc[k] = v;
      }
      return acc;
    }, target);
  },
  shallowMergeObject(target = {}, obj = {}) {
    return { ...target, ...obj };
  },
  isObjectOrArray(value) {
    return value != null && (Array.isArray(value) || typeof value === 'object');
  },
  isObject(value) {
    return value != null && !Array.isArray(value) && typeof value === 'object';
  },
  isFunction(value) {
    return value === 'function';
  },
};
