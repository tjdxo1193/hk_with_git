<template>
  <AUIGridSearch
    v-bind="commonCode"
    :title="title"
    :height="height"
    :margin="margin"
    :buttons="computedActionButtons"
    @grid-created="(proxy) => $setState('commonCode.$grid', proxy)"
    @button-click="onButtonEvents"
    @form-event="searchFormEvent"
  />
</template>

<script>
import { message } from '@/const';
import { FormUtil } from '@/util';

import values from './values/treeCommonCodeManage';

export default {
  name: 'TreeCommonCodeManagePage',
  emits: ['select'],
  props: {
    margin: String,
    title: String,
    code: {
      type: String,
      required: true,
      description: 'Root를 제외한 tree code',
    },
    height: {
      type: String,
      default: '600px',
    },
    readonly: {
      type: Boolean,
      default: false,
    },
  },
  mounted() {
    this.$axios.all([this.fetchCodesByCode()]);
  },
  data() {
    const { commonCode } = this.$copy(values);

    return {
      commonCode: {
        ...commonCode.static,
        forms: commonCode.forms(),
        columns: commonCode.columns(),
        event: {
          cellEditBegin: (e) => this.onCellEditBegin(e),
          cellDoubleClick: (e) => this.selectRow(e),
        },
      },
    };
  },
  methods: {
    async fetchCodesByCode() {
      const treeNm = FormUtil.getData(this.commonCode.forms, 'treeNm');
      const { data } = await this.commonCode.$grid._useLoader(() =>
        this.$axios.get(`/sy/treeCommonCodeManage/${this.$props.code}`, treeNm),
      );
      this.commonCode.$grid.setGridData(data);
    },
    onButtonEvents({ name }) {
      if (name === 'search') {
        this.fetchCodesByCode();
        return;
      }
      if (name === 'addRow') {
        this.addRow();
        return;
      }
      if (name === 'save') {
        this.saveTreeNode();
      }
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.fetchCodesByCode();
      }
    },
    addRow() {
      const [selectedItem] = this.commonCode.$grid.getSelectedRows();

      if (!selectedItem || this.isLimitDepth(selectedItem)) {
        return;
      }

      this.commonCode.$grid.addTreeRow(
        { _isNew: true, hirTreeCd: selectedItem.treeCd },
        selectedItem._$uid,
        'last',
      );
    },
    saveTreeNode() {
      const addedList = this.commonCode.$grid.getAddedRowItems();
      const updatedList = this.commonCode.$grid.getEditedRowItems();

      if (addedList.length === 0 && updatedList.length === 0) {
        this.$warn(message.validate.noEditedRowItems);
        return;
      }

      if (this.isExistsNoTreeNmItem(addedList) || this.isExistsNoTreeNmItem(updatedList)) {
        this.$warn(message.validate.noInputCodeNm);
        return;
      }

      this.$eSign(() =>
        this.commonCode.$grid._useLoader(() =>
          this.$axios.post('/sy/treeCommonCodeManage/node', { addedList, updatedList }),
        ),
      )
        .then(() => {
          this.$info(this.$message.info.saved);
          this.fetchCodesByCode();
        })
        .catch(() => {
          this.$error(this.$message.error.saveData);
        });
    },
    isExistsNoTreeNmItem(rows = []) {
      return rows.some((item) => !item.treeNm || (item.treeNm && !item.treeNm.trim()));
    },
    onCellEditBegin(e) {
      if (this.isAlwaysEditableColumn(e.dataField)) {
        return true;
      }
      if (!this.isNewItem(e.item)) {
        return false;
      }
      return true;
    },
    selectRow({ item }) {
      if (!this.isLimitDepth(item) || this.isNewItem(item)) {
        return;
      }
      this.$emit('select', item);
    },
    isNewItem({ _isNew = false }) {
      return _isNew;
    },
    isLimitDepth({ _$uid, _$depth }) {
      const ancestors = this.commonCode.$grid.getAscendantsByRowId(_$uid);
      const { deptLmt = 0 } = ancestors.pop() ?? {};
      return deptLmt > 0 && deptLmt <= _$depth;
    },
    isAlwaysEditableColumn(dataField) {
      return ['treeEn', 'treeAbbr', 'treeCdOrd', 'treeRmk', 'useYn'].includes(dataField);
    },
  },
  computed: {
    computedActionButtons() {
      const { buttons } = values.commonCode.static;
      return this.$props.readonly ? buttons.filter(({ name }) => name === 'search') : buttons;
    },
  },
};
</script>

<style></style>
