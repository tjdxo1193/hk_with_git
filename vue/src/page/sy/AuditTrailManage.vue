<template>
  <Card title="Audit 마스터 관리 (개발자용)" transparent padding="0">
    <Horizontal :spans="[4, 6]">
      <AUIGridSearch
        v-bind="menu"
        @grid-created="(proxy) => $setState('menu.$grid', proxy)"
        @button-click="onClickOnMenu"
        @enter="fetchMenus"
      />
      <AUIGridWithHeader
        v-bind="column"
        @grid-created="(proxy) => $setState('column.$grid', proxy)"
        @button-click="onClickOnColumn"
      />
    </Horizontal>
  </Card>
</template>

<script>
import { FormUtil, GridUtil } from '@/util';

import values from './values/auditTrailManage';

export default {
  name: 'AuditTrailManage',
  mounted() {
    this.fetchMenus();
  },
  data() {
    const { menu, column } = this.$copy(values);
    return {
      menu: {
        ...menu.static,
        columns: menu.columns(),
        forms: menu.forms(),
        state: {
          currentMenu: null,
        },
        event: {
          cellDoubleClick: (e) => this.selectMenu(e),
        },
      },
      column: {
        ...column.static,
        columns: column.columns(),
        state: {
          currentTableNm: null,
          currentColumns: [],
        },
        event: {
          cellEditBegin: (e) => this.onEditBeginOnColumn(e),
          cellEditEndBefore: (e) => this.onEditEndBeforeOnColumn(e),
        },
      },
    };
  },
  methods: {
    // menu
    async fetchMenus() {
      const param = FormUtil.getData(this.menu.forms);
      const { data } = await this.$axios.get('/dev/audit/menus', param);
      this.menu.$grid.setGridData(data.menus);
    },
    onClickOnMenu({ name }) {
      if (name === 'search') {
        this.clearSelectMenu();
        this.clearOnColumn();
        this.fetchMenus();
      }
    },
    clearSelectMenu() {
      this.menu.state.currentMenu = null;
    },
    selectMenu(menu) {
      this.clearOnColumn();
      this.menu.state.currentMenu = menu;

      if (menu.item._$depth === 1) {
        return;
      }

      this.$axios.all([
        this.fetchTablesAsDropDownList(),
        this.fetchAuditMaster(menu.item.code, menu.item.name),
      ]);
    },
    isSelectedMenu() {
      const { currentMenu } = this.menu.state;
      if (!currentMenu) {
        this.$info('메뉴를 선택해주세요.');
        return false;
      }
      if (currentMenu.item._$depth === 1) {
        this.$info('하위 메뉴를 선택해주세요.');
        return false;
      }
      return true;
    },
    getSelectedMenu() {
      const { currentMenu } = this.menu.state;
      return currentMenu?.item;
    },
    onClickTable({ name }) {
      if (name === 'search') {
        this.fetchTables();
      }
    },

    // column
    async fetchAuditMaster(menuCd, menuNm) {
      const { data } = await this.$axios.get(`/dev/audit/master/${menuCd}`);

      const mappedData = data.map((item) => ({ ...item, menuNm }));
      this.column.$grid.setGridData(mappedData);

      const tableNames = data
        .filter((item) => item.tableNm != null)
        .map((item) => ({
          value: item.tableNm,
          label: `${item.tableNm} (${item.tableComment})`,
        }));

      if (tableNames.length > 0) {
        this.setCurrentTableNm(tableNames[0].value);
      }
    },
    async fetchTablesAsDropDownList() {
      const { data } = await this.$axios.get('/dev/audit/tables');
      const dropDownTable = GridUtil.findColumn(this.column.columns, 'tableNm');
      const dropDownJoinTable = GridUtil.findColumn(this.column.columns, 'joinTableNm');

      const dropDownList = data.tables.map((item) => ({
        value: item.tableNm,
        label: `${item.tableNm} (${item.tableComment})`,
      }));
      dropDownTable.editRenderer.list = dropDownList;
      dropDownJoinTable.editRenderer.list = this.withDefaultOption(dropDownList);
    },
    async fetchColumnsByTableNm(tableNm) {
      if (!tableNm) {
        return;
      }
      const { data } = await this.$axios.get(`/dev/audit/tables/${tableNm}/columns`);
      this.column.state.currentColumns = data.columns;

      const dropDownColumn = GridUtil.findColumn(this.column.columns, 'columnNm');
      const dropDownList = data.columns.map((item) => ({
        value: item.columnNm,
        label: `${item.columnNm} (${item.columnComment})`,
      }));
      dropDownColumn.editRenderer.list = this.withDefaultOption(dropDownList);
    },
    async fetchJoinColumnsByTableNm(tableNm) {
      if (!tableNm) {
        return;
      }
      const { data } = await this.$axios.get(`/dev/audit/tables/${tableNm}/columns`);
      const dropDownColumn = GridUtil.findColumn(this.column.columns, 'joinColumnNm');
      const dropDownDisplayColumn = GridUtil.findColumn(this.column.columns, 'joinDisplayColumnNm');

      const dropDownList = data.columns.map((item) => ({
        value: item.columnNm,
        label: `${item.columnNm} (${item.columnComment})`,
      }));
      dropDownColumn.editRenderer.list = this.withDefaultOption(dropDownList);
      dropDownDisplayColumn.editRenderer.list = this.withDefaultOption(dropDownList);
    },
    async saveColumn() {
      const { code: menuCd } = this.getSelectedMenu();
      const param = this.column.$grid.getGridData();
      this.$axios.post(`/dev/audit/master/${menuCd}`, param).then(({ data }) => {
        this.$info(this.$message.info.saved);
        this.column.$grid.setGridData(data);
      });
    },
    onClickOnColumn({ name }) {
      if (name === 'save') {
        this.isSelectedMenu() && this.saveColumn();
        return;
      }
      if (name === 'addRow') {
        this.isSelectedMenu() && this.addRowOnColumn();
        return;
      }
      if (name === 'removeRow') {
        this.isSelectedMenu() && this.removeRowOnColumn();
        return;
      }
      if (name === 'init') {
        this.clearOnColumn();
      }
    },
    onEditBeginOnColumn(e) {
      const { dataField } = e;

      if (dataField === 'columnNm') {
        return GridUtil.existsValue(this.column.$grid, e.rowIndex, 'tableNm');
      }
      if (dataField === 'joinColumnNm' || dataField === 'joinDisplayColumnNm') {
        return GridUtil.existsValue(this.column.$grid, e.rowIndex, 'joinTableNm');
      }
    },
    onEditEndBeforeOnColumn(e) {
      const { dataField, oldValue, value, rowIndex } = e;

      if (dataField === 'tableNm') {
        this.clearColumnOnColumn();
        this.setCurrentTableNm(value);
        return oldValue ?? value;
      }
      if (dataField === 'columnNm') {
        this.setColumnCommentOnColumn(value, rowIndex);
        return value;
      }
      if (dataField === 'joinTableNm') {
        this.clearJoinColumnOnColumn(rowIndex);
        this.fetchJoinColumnsByTableNm(value);
        return value;
      }
    },
    clearOnColumn() {
      this.column.state.currentTableNm = null;
      this.column.state.currentColumns = [];
      this.column.$grid.clearGridData();
    },
    getCurrentTableNm() {
      return this.column.state.currentTableNm;
    },
    setCurrentTableNm(tableNm) {
      const currentTableNm = this.column.state.currentTableNm;
      if (currentTableNm) {
        this.$confirm('1개의 테이블만 선택할 수 있습니다. <br/> 데이터를 초기화하시겠습니까?')
          .then(() => {
            this.clearOnColumn();
          })
          .catch(() => null);
      } else {
        this.fetchColumnsByTableNm(tableNm);
        this.column.state.currentTableNm = tableNm;
        const data = this.column.data.map((item) => ({ ...item, tableNm }));
        this.column.$grid.setGridData(data);
      }
    },
    addRowOnColumn() {
      const { code: menuCd, name: menuNm } = this.getSelectedMenu();
      if (!menuCd) {
        return;
      }
      const tableNm = this.getCurrentTableNm();
      const data = [...this.column.data, { menuCd, menuNm, tableNm }];
      this.column.$grid.setGridData(data);
    },
    removeRowOnColumn() {
      const [selectedItem] = this.column.$grid.getSelectedItems();
      if (!selectedItem) {
        return;
      }
      const rowIndex = selectedItem.rowIndex;
      const data = this.column.$grid.getGridData().filter((item, index) => index !== rowIndex);
      this.column.$grid.setGridData(data);
    },
    setColumnCommentOnColumn(value, rowIndex) {
      if (!value) {
        return;
      }
      const selectedColumn = this.column.state.currentColumns.find(
        (column) => column.columnNm === value,
      );
      this.column.$grid.setCellValue(rowIndex, 'columnComment', selectedColumn.columnComment);
    },
    clearColumnOnColumn() {
      const column = GridUtil.findColumn(this.column.columns, 'columnNm');
      column.editRenderer.list = [];
    },
    clearJoinColumnOnColumn(rowIndex) {
      const column = GridUtil.findColumn(this.column.columns, 'joinColumnNm');
      const displayColumn = GridUtil.findColumn(this.column.columns, 'joinDisplayColumnNm');

      column.editRenderer.list = [];
      displayColumn.editRenderer.list = [];

      this.column.$grid.setCellValue(rowIndex, 'joinColumnNm', null);
      this.column.$grid.setCellValue(rowIndex, 'joinDisplayColumnNm', null);
    },
    withDefaultOption(list) {
      return [{ value: '', label: '없음' }, ...list];
    },
  },
};
</script>

<style lang="scss" scope>
.audit-trail-manage {
  &__menu {
    & button {
      margin-bottom: 3px;

      &:hover,
      &.selected {
        color: $common-selection-color;
      }
    }

    &--child {
      padding-left: 12px;
    }
  }

  &__table {
    & button {
      margin-bottom: 3px;

      &:hover,
      &.selected {
        color: $common-selection-color;
      }
    }
  }

  &__column {
    & span {
      margin-bottom: 10px;

      &:hover {
        color: $common-selection-color;
      }
    }
  }
}
</style>
