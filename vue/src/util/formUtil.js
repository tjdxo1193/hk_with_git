import ObjectUtil from './objectUtil';

const getValue = (v) => (Array.isArray(v) ? v.slice() : v ?? '');

const setValue = (item, v) => (item.value = v);

const isBlank = (item) => item?._blank;

const isHidden = (item) => item?._hidden;

const isMulti = (item) => item?._multi;

const isSlot = (item) => item?._slot;

const isDisabled = (item) => item?.disabled;

const isExists = (obj, key) => ObjectUtil.has(obj, key);

const isBlob = (v) => v instanceof Blob;

const toArray = (v) => (Array.isArray(v) ? v : [v]);

const setPropToItem = (item, prop = {}) => {
  for (let key in prop) {
    item[key] = prop[key];
  }
};

const setPropByFunctionToItem = (item, setPropFn) => setPropFn && setPropFn(item);

const mapArrayData = (forms = [], newItemMap = {}) => {
  return forms.map((item) => {
    if (isMulti(item)) {
      return {
        ...item,
        _multiForms: mapArrayData(item._multiForms, newItemMap),
      };
    }

    if (ObjectUtil.has(newItemMap, item.name)) {
      return {
        ...item,
        ...newItemMap[item.name],
      };
    }
    return item;
  });
};

const mapObjectData = (forms = [], newItem = {}) => {
  return forms.map((item) => {
    if (isMulti(item)) {
      return {
        ...item,
        _multiForms: mapObjectData(item._multiForms, newItem),
      };
    }

    if (item.name === newItem.name) {
      return {
        ...item,
        ...newItem,
      };
    }
    return item;
  });
};

const setFormDataForObject = (formData, dataKey, obj) => {
  for (let key in obj) {
    formData.set(`${dataKey}.${key}`, obj[key]);
  }
};

const setFormDataForArray = (formData, key, arr) => {
  for (let i = 0; i < arr.length; i++) {
    const dataKey = `${key}[${i}]`;

    if (isBlob(arr[i])) {
      formData.set(dataKey, arr[i], arr[i]?.name);
    } else if (ObjectUtil.isObject(arr[i])) {
      setFormDataForObject(formData, dataKey, arr[i]);
    } else {
      formData.set(dataKey, arr[i]);
    }
  }
};

const convertToObject = (obj = {}, items = []) => {
  return items.reduce((acc, item) => {
    acc[item.name] = item;
    return acc;
  }, obj);
};

const getDataByRecursive = (forms = [], acc = {}) => {
  return forms.reduce((acc, item) => {
    if (isBlank(item) || isDisabled(item) || isSlot(item)) {
      return acc;
    }
    if (isMulti(item)) {
      getDataByRecursive(item._multiForms, acc);
    }
    acc[item.name] = getValue(item.value);
    return acc;
  }, acc);
};

const setDataByRecursive = (forms = [], data = {}) => {
  for (let i = 0; i < forms.length; i++) {
    const item = forms[i];
    if (isBlank(item) || isSlot(item)) {
      continue;
    }
    if (isMulti(item)) {
      setDataByRecursive(item._multiForms, data);
      continue;
    }
    if (isExists(data, item.name)) {
      setValue(item, data[item.name]);
      continue;
    }
  }
};

const findItemByRecursive = (forms = [], propName, propValue) => {
  for (let i = 0; i < forms.length; i++) {
    const item = forms[i];

    if (isMulti(item)) {
      const multiItem = findItemByRecursive(item._multiForms, propName, propValue);

      if (multiItem) {
        return { ...multiItem };
      }
    }
    if (item[propName] == propValue) {
      return { ...item };
    }
  }
  return null;
};

const setPropByRecursive = (forms = [], names = [], prop = {}) => {
  for (let i = 0; i < forms.length; i++) {
    const item = forms[i];

    if (isBlank(item) || isSlot(item) || isHidden(item)) {
      continue;
    }
    if (isMulti(item)) {
      setPropByRecursive(item._multiForms, names, prop);
      continue;
    }
    if (names.includes(item.name)) {
      typeof prop === 'function' ? setPropByFunctionToItem(item, prop) : setPropToItem(item, prop);
      continue;
    }
  }
};

const getAllNames = (forms, names = []) => {
  for (let i = 0; i < forms.length; i++) {
    const item = forms[i];

    if (isBlank(item) || isSlot(item) || isHidden(item)) {
      continue;
    }
    if (isMulti(item)) {
      getAllNames(item._multiForms, names);
      continue;
    }
    names.push(item.name);
  }
  return names;
};

export default {
  getData(forms) {
    if (this.isFormDataObject(forms)) {
      return forms;
    }
    return getDataByRecursive(forms, { _formDataObject: true });
  },
  setData(forms, data) {
    setDataByRecursive(forms, data);
  },
  setProp(forms, names, prop) {
    setPropByRecursive(forms, names, prop);
  },
  setPropAll(forms, prop) {
    setPropByRecursive(forms, getAllNames(forms), prop);
  },
  toFormData(data) {
    const formData = new FormData();
    for (let key in data) {
      if (Array.isArray(data[key])) {
        setFormDataForArray(formData, key, data[key]);
      } else {
        formData.set(key, data[key]);
      }
    }
    return formData;
  },
  findItem(forms, name) {
    if (!name) return null;
    return findItemByRecursive(forms, 'name', name);
  },
  findItemByProp(forms, propName, propValue) {
    if (!propName || !propValue) return null;
    return findItemByRecursive(forms, propName, propValue);
  },
  getValue(forms, name) {
    return this.findItem(forms, name)?.value;
  },
  existsValue(forms, name) {
    const value = this.getValue(forms, name);
    return value != null && String(value).trim() != '';
  },
  map(forms = [], newItem) {
    if (!newItem) {
      return [...forms];
    }

    if (Array.isArray(newItem)) {
      const newItemMap = convertToObject({}, newItem);
      return mapArrayData(forms, newItemMap);
    } else {
      return mapObjectData(forms, newItem);
    }
  },
  enable(forms, names = getAllNames(forms)) {
    setPropByRecursive(forms, toArray(names), { readonly: false, disabled: false });
  },
  readonly(forms, names = getAllNames(forms)) {
    setPropByRecursive(forms, toArray(names), { readonly: true, disabled: false });
  },
  disable(forms, names = getAllNames(forms)) {
    setPropByRecursive(forms, toArray(names), { readonly: false, disabled: true });
  },
  mapButtonsType(buttons = [], names = [], type) {
    return buttons.map((button) =>
      !names.includes(button.name)
        ? button
        : {
            ...button,
            type,
          },
    );
  },
  disableButtons(buttons = [], names = []) {
    buttons.forEach((button) => {
      if (names.includes(button.name)) {
        button.disabled = true;
      }
    });
  },
  enableButtons(buttons = [], names = []) {
    buttons.forEach((button) => {
      if (names.includes(button.name)) {
        button.disabled = false;
      }
    });
  },
  selectTab(tabs, name) {
    for (const tab of tabs) {
      tab.selected = tab.name === name;
    }
  },
  isFormDataObject(forms) {
    return forms._formDataObject === true;
  },
  toRequired(forms, names = getAllNames(forms)) {
    setPropByRecursive(forms, toArray(names), { _required: true });
  },
  toOptional(forms, names = getAllNames(forms)) {
    setPropByRecursive(forms, toArray(names), { _required: false });
  },
};
