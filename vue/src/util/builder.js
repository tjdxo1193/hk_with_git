import { errorType } from '@/const';

import formUtil from './formUtil';
import objectUtil from './objectUtil';

import { FormUtil, ObjectUtil } from '.';

/**
 * TODO Builder들의 public interface와 private interface를 구분하기 쉽게 class로 작성해서 inteface 분리
 */

export const ColumnBuilder = {
  builder() {
    return {
      _columns: [],
      col(...args) {
        this._columns.push(this._make(...args));
        return this;
      },
      build() {
        const result = this._columns;
        this._columns = null;
        return result;
      },
      _make(dataField, ...args) {
        let headerText = null;
        let visible = true;
        let option = {};

        //* column('field', visible)
        if (args.length === 1 && typeof args[0] === 'boolean') {
          visible = args[0];

          //* column('field', {})
        } else if (args.length === 1 && objectUtil.isObject(args[0])) {
          visible = false;
          option = args[0];

          //* column('field', 'text')
        } else if (args.length === 1) {
          headerText = args[0];

          //* column('field', 'text', visible)
        } else if (args.length === 2 && typeof args[1] === 'boolean') {
          headerText = args[0];
          visible = args[1];

          //* column('field', 'text', {})
        } else if (args.length === 2 && objectUtil.isObject(args[1])) {
          headerText = args[0];
          option = args[1];

          //* column('field', 'text', visible, {})
        } else if (args.length === 3) {
          headerText = args[0];
          visible = args[1];
          option = args[2];
        }

        return { dataField, headerText, visible, ...option };
      },
      calendar(...args) {
        const makeColumnHeader = this._make(...args);
        const calendarRenderer = {
          width: 100,
          editRenderer: {
            type: 'CalendarRenderer',
            defaultFormat: 'yyyy-mm-dd',
          },
        };

        const makeColumn = { ...makeColumnHeader, ...calendarRenderer };
        this._columns.push(makeColumn);
        return this;
      },
      icon(...args) {
        const makeColumnHeader = this._make(...args);
        const iconRenderer = {
          width: 100,
          renderer: {
            type: 'IconRenderer',
            iconPosition: 'aisleRight',
            iconWidth: 16,
            iconHeight: 16,
            iconTableRef: {
              default: '../assets/images/searchBtn.png',
            },
            onClick: function () {
              //TODO 무슨이벤트를 걸지..
            },
          },
        };

        const makeColumn = { ...makeColumnHeader, ...iconRenderer };
        this._columns.push(makeColumn);
        return this;
      },
      button(...args) {
        const makeColumnHeader = this._make(...args);
        const buttonRenderer = {
          renderer: {
            type: 'ButtonRenderer',
            labelText: args[1],
          },
        };

        const makeColumn = { ...makeColumnHeader, ...buttonRenderer };
        this._columns.push(makeColumn);
        return this;
      },
      checkbox(...args) {
        const makeColumnHeader = this._make(...args);
        const checkboxRenderer = {
          width: 100,
          renderer: {
            type: 'CheckBoxEditRenderer',
            editable: true,
          },
        };

        const makeColumn = { ...makeColumnHeader, ...checkboxRenderer };
        this._columns.push(makeColumn);
        return this;
      },
      combo(...args) {
        const option = args[2];

        const comboRenderer = this._isNormalCombo(option)
          ? this._createCombo(option)
          : this._isAncestorCombo(option)
          ? this._createAncestorCombo(option)
          : this._isDescendantCombo(option)
          ? this._createDescendantCombo(option)
          : {};

        if (objectUtil.has(option, 'async')) {
          option.async().then(({ data }) => (comboRenderer.editRenderer.list = data));
        }

        const makeColumnHeader = this._make(...args);
        const makeColumn = { ...makeColumnHeader, ...comboRenderer };
        this._columns.push(makeColumn);
        return this;
      },
      _isNormalCombo(option) {
        return (
          ((objectUtil.has(option, 'list') && Array.isArray(option.list)) ||
            objectUtil.has(option, 'async')) &&
          !objectUtil.has(option, 'descendants') &&
          !objectUtil.has(option, 'ancestor')
        );
      },
      _isAncestorCombo(option) {
        return objectUtil.has(option, 'descendants') && Array.isArray(option.descendants);
      },
      _isDescendantCombo(option) {
        return objectUtil.has(option, 'ancestor') && objectUtil.has(option, 'async');
      },
      _createCombo({ list = [] }) {
        return {
          editRenderer: {
            type: 'DropDownListRenderer',
            showEditorBtnOver: true,
            list,
            keyField: 'value',
            valueField: 'label',
          },
          labelFunction: function (rowIndex, columnIndex, value) {
            const returnValue = this.editRenderer.list.filter((item) => item.value == value);

            if (returnValue[0] != null) {
              return returnValue[0].label;
            } else {
              return value;
            }
          },
        };
      },
      _createAncestorCombo(option) {
        const renderer = this._createCombo(option);
        const { descendants, descendantDefaultValues } = option;
        return {
          ...renderer,
          editRenderer: {
            ...renderer.editRenderer,
            descendants,
            descendantDefaultValues,
          },
        };
      },
      _createDescendantCombo(option) {
        const renderer = this._createCombo(option);
        const { ancestor, async } = option;
        return {
          _ancestor: {
            target: ancestor,
            value: -1,
          },
          ...renderer,
          editRenderer: {
            ...renderer.editRenderer,
          },
          labelFunction(rowIndex, columnIndex, value, headerText, item = {}) {
            /**
             ** AUIGrid labelFunction 권장사항에 따라 다음 로직은 최대한 메서드를 사용하지 않고 처리합니다.
             * @see https://www.auisoft.net/documentation/auigrid/ColumnLayout/Column.html#labelFunction
             */
            //* 그리드에 처음 데이터가 삽입될 때(초기화, 행 추가 등) 부모 값을 캐싱합니다.
            if (this._ancestor.value === -1) {
              this._ancestor.value = item[this._ancestor.target];
            }

            //* 부모 컬럼의 값이 변경될 때만 실행합니다.
            if (this._ancestor.value != item[this._ancestor.target]) {
              this._ancestor.value = item[this._ancestor.target];
              async({ ...item }).then(({ data }) => (this.editRenderer.list = data));
            }

            const returnValue = this.editRenderer.list.filter((item) => item.value == value);

            if (returnValue[0] != null) {
              return returnValue[0].label;
            } else {
              return value;
            }
          },
        };
      },
    };
  },
};

export const FormBuilder = {
  builder() {
    return {
      _forms: [],
      build() {
        this._setUtilMethods(this._forms);
        const result = this._forms;
        this._forms = null;
        this._bindFormDataToValidator(result);
        return result;
      },
      _setUtilMethods(forms) {
        forms.validate = async function () {
          await formUtil.setPropAll(this, { _validating: true });
          const invalidFormItem = formUtil.findItemByProp(this, '_invalid', true);

          formUtil.setPropAll(this, { _validating: false, _invalid: false });

          if (invalidFormItem) {
            return window.Promise.reject({
              type: errorType.form.VALIDATE,
              name: invalidFormItem.name,
              message: '[FormUtil] Exists form item with an invalid value',
            });
          }
        };
      },
      _bindFormDataToValidator(forms) {
        FormUtil.setPropAll(forms, (item) => {
          if (!item._validator) {
            return;
          }

          const validator = item._validator;

          if (typeof validator === 'function') {
            item._validator = (value, formsParam = forms) =>
              validator(value, FormUtil.getData(formsParam));
            return;
          }

          if (ObjectUtil.isObject(validator)) {
            const { message, handler } = validator;
            item._validator = {
              message,
              handler: (value, formsParam = forms) => handler(value, FormUtil.getData(formsParam)),
            };
            return;
          }
        });
      },
      _lastItem() {
        if (this._forms.length === 0) {
          throw new SyntaxError(`Don't try to call util method before the form item is added`);
        }
        return this._forms[this._forms.length - 1];
      },
      _addItem(item) {
        this._forms.push(item);
        return this;
      },
      _make(componentName, name, ...args) {
        let itemLabel = null;
        let prop = {};

        //* component('name', 'itemLabel')
        if (args.length === 1 && typeof args[0] === 'string') {
          itemLabel = args[0];

          //* component('name', {})
        } else if (args.length === 1 && objectUtil.isObject(args[0])) {
          prop = args[0];

          //* component('name', 'itemLabel', {})
        } else if (args.length === 2) {
          itemLabel = args[0];
          prop = args[1];
        }
        return { name, itemLabel, ...prop, is: componentName, _initValidate: true };
      },
      _addOptionToArgs(args, option) {
        const lastArg = args[args.length - 1];
        if (objectUtil.isObject(lastArg)) {
          Object.entries(option).forEach(([k, v]) => {
            if (!ObjectUtil.has(lastArg, k)) {
              lastArg[k] = v;
            }
          });
        } else {
          args.push(option);
        }
      },
      blank() {
        this._addItem({ _blank: true });
        return this;
      },
      readonly() {
        this._lastItem().readonly = true;
        return this;
      },
      disabled() {
        this._lastItem().disabled = true;
        return this;
      },
      required() {
        this._lastItem()._required = true;
        return this;
      },
      validator(validator = {}) {
        const lastItem = this._lastItem();

        if (typeof validator === 'function') {
          lastItem._validator = validator;
        }

        if (ObjectUtil.isObject(validator)) {
          const { message = null, handler = null } = validator;
          lastItem._validator = { message, handler };
        }

        return this;
      },
      spanCol(span) {
        this._lastItem()._colSpan = span;
        return this;
      },
      spanRow(span) {
        this._lastItem()._rowSpan = span;
        return this;
      },
      suffix(string) {
        this._lastItem().suffix = string;
        return this;
      },
      multiple(name, itemLabel, items) {
        if (!Array.isArray(items)) {
          throw new TypeError('[FormBuilder] not found arguments of multiple()');
        }
        this._addItem({ name, itemLabel, _multi: true, _multiForms: items });
        return this;
      },
      Slot(name, itemLabel, props = {}) {
        this._addItem({ name, itemLabel, ...props, _slot: true });
        return this;
      },
      TextView() {
        return this._addItem(this._make('TextView', ...arguments));
      },
      Input() {
        return this._addItem(this._make('InputBase', ...arguments));
      },
      InputPassword(...args) {
        this._addOptionToArgs(args, { type: 'password' });
        return this._addItem(this._make('InputBase', ...args));
      },
      InputNumber(...args) {
        this._addOptionToArgs(args, { type: 'number' });
        return this._addItem(this._make('InputBase', ...args));
      },
      InputFile() {
        return this._addItem(this._make('InputFile', ...arguments));
      },
      Textarea() {
        return this._addItem(this._make('TextareaBase', ...arguments));
      },
      Select() {
        return this._addItem(this._make('SelectBase', ...arguments));
      },
      Hidden() {
        return this._addItem({ ...this._make('Hidden', ...arguments), _hidden: true });
      },
      Button() {
        return this._addItem(this._make('ButtonBase', ...arguments));
      },
      Radio() {
        return this._addItem(this._make('RadioBase', ...arguments));
      },
      RadioGroup() {
        return this._addItem(this._make('RadioGroup', ...arguments));
      },
      Datepicker() {
        return this._addItem(this._make('Datepicker', ...arguments));
      },
      DatepickerTwin(...args) {
        this._addOptionToArgs(args, { twin: true });
        return this._addItem(this._make('Datepicker', ...args));
      },
      DatepickerWithSwitch(...args) {
        this._addOptionToArgs(args, { disabled: true });
        return this._addItem(this._make('DatepickerWithSwitch', ...args));
      },
      DatepickerTwinWithSwitch(...args) {
        this._addOptionToArgs(args, { twin: true, disabled: true });
        return this._addItem(this._make('DatepickerWithSwitch', ...args));
      },
      Checkbox() {
        return this._addItem(this._make('Checkbox', ...arguments));
      },
      CheckboxGroup() {
        return this._addItem(this._make('CheckboxGroup', ...arguments));
      },
    };
  },
};
