<template>
  <AUIGridSearch
    v-bind="authManageGrid"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('authManageGrid.$grid', proxy)"
  />
  <Horizontal align-items="center" :spans="[9, 1.5, 9]">
    <AUIGridWithHeader
      v-bind="allMenuGrid"
      @grid-created="(proxy) => $setState('allMenuGrid.$grid', proxy)"
      @button-click="onClickButton"
    />
    <ExchangePanel direction="vertical" @click="onClickExchange" />
    <AUIGridWithHeader
      v-bind="authMenuGrid"
      @button-click="onClickButton"
      @grid-created="(proxy) => $setState('authMenuGrid.$grid', proxy)"
    />
  </Horizontal>
</template>

<script>
import { FormUtil, GridUtil } from '@/util';

import values from './values/authManage';

export default {
  name: 'authManage',
  mounted() {
    this.getAuthManageGrid();
  },
  data() {
    const { authManageGrid, allMenuGrid, authMenuGrid } = this.$copy(values);
    return {
      authManageGrid: {
        ...authManageGrid.static,
        forms: authManageGrid.forms(),
        columns: authManageGrid.columns(),
        event: {
          cellDoubleClick: (event) => this.selectRow(event),
        },
      },
      allMenuGrid: {
        ...allMenuGrid.static,
        columns: allMenuGrid.columns(),
      },
      authMenuGrid: {
        ...authMenuGrid.static,
        columns: authMenuGrid.columns(),
        cachedDataIdFields: [],
      },
    };
  },
  methods: {
    clearAuthMenuGrid() {
      this.authMenuGrid.$grid.clearGridData();
      this.clearCacheAuthMenuData();
    },
    async getAuthManageGrid() {
      this.authManageGrid.$grid.clearGridData();
      const { $grid, forms } = this.authManageGrid;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('sy/authManage', parameter))
        .then(({ data }) => data);
      this.authManageGrid.$grid.setGridData(data);
      this.allMenuGrid.$grid.clearGridData();
      this.clearAuthMenuGrid();
    },
    async selectRow(event) {
      const athCd = event.item.athCd;
      const crtDs = event.item.crtDs;
      if (crtDs === undefined) {
        return;
      }
      this.setDataToMenuDataGrid(athCd);
      this.setDataToAuthMenuGrid(athCd);
    },
    async setDataToMenuDataGrid(athCd) {
      this.allMenuGrid.$grid.clearGridData();
      const { $grid } = this.allMenuGrid;
      const parameter = { athCd };
      const data = await $grid
        ._useLoader(() => this.$axios.get('sy/authManage/notAthGp', parameter))
        .then(({ data }) => data);
      await this.allMenuGrid.$grid.setGridData(data);
    },
    async setDataToAuthMenuGrid(athCd) {
      const parameter = { athCd };
      const { $grid } = this.authMenuGrid;
      const authData = await $grid
        ._useLoader(() => this.$axios.get('sy/authManage/athGp', parameter))
        .then(({ data }) => data);
      this.authMenuGrid.$grid.setGridData(authData);
      this.cacheAutheMenuData(authData);
    },
    // authMenu의 처음 데이터 값을 캐시 데이터로 저장
    cacheAutheMenuData(data) {
      this.authMenuGrid.cachedDataIdFields = data.map(({ menuCd }) => menuCd);
    },
    // authMenu의 캐시 데이터 삭제
    clearCacheAuthMenuData() {
      this.authMenuGrid.cachedDataIdFields = [];
    },
    // 바뀐 데이터가 있는지 확인
    isNoChangeAuthMenuData() {
      // 현재 그리드의 데이터가 있는지 확인
      const data = this.authMenuGrid.$grid.getGridData();
      // 그리드를 생성할 때 가져온 cachedDataFields와 비교해 포함하고 있으면 true 반환
      const cachedIdFields = this.authMenuGrid.cachedDataIdFields;
      const isIncludes = data.every(({ menuCd }) => cachedIdFields.includes(menuCd));
      const isSameLength = cachedIdFields.length === data.length;
      return isIncludes && isSameLength;
    },
    addNewRow() {
      const { $grid } = this.authManageGrid;
      $grid.addRow([{}]);
    },
    async save() {
      const addedRowItems = this.authManageGrid.$grid.getAddedRowItems();
      const editedRowItems = this.authManageGrid.$grid.getEditedRowItems();
      if (addedRowItems.length === 0 && editedRowItems.length === 0) {
        this.$warn(this.$message.validate.noEditedRowItems);
        return;
      }
      const parameter = {
        addedRowItems: addedRowItems,
        editedRowItems: editedRowItems,
      };
      // 필수 입력 검사
      const isValid = this.authManageGrid.$grid.validateGridData(
        ['athNm', 'useYn'],
        '필수 값입니다.',
      );
      if (isValid) {
        await this.$eSignWithReason(() => this.$axios.post('sy/authManage', parameter))
          .then(() => this.$info(this.$message.info.saved))
          .catch(() => this.$error(this.$message.error.updateData));
        this.getAuthManageGrid();
      }
    },
    async menuSave() {
      const authMenu = this.authMenuGrid.$grid.getGridData();
      const addedRowItems = this.authMenuGrid.$grid.getAddedRowItems();
      const removedRowItems = this.authMenuGrid.$grid.getRemovedItems();
      // 권한 메뉴에 데이터가 0개 일 때
      if (authMenu.length === 0) {
        this.$error(this.$message.warn.unSelectedData);
        return;
      }
      if (this.isNoChangeAuthMenuData()) {
        this.$warn(this.$message.validate.noEditedRowItems);
        return;
      }
      const athCd = this.authMenuGrid.$grid.getGridData()[0].athCd;
      const parameter = {
        athCd,
        addedRowItems: addedRowItems.filter(
          ({ menuCd }) => !this.authMenuGrid.cachedDataIdFields.includes(menuCd),
        ),
        removedRowItems: removedRowItems.filter(({ menuCd }) =>
          this.authMenuGrid.cachedDataIdFields.includes(menuCd),
        ),
      };
      await this.$eSign(() => this.$axios.post('sy/authManage/athGp', parameter))
        .catch(() => this.$error(this.$message.error.createData))
        .then(() => this.$info(this.$message.info.saved));
      await this.setDataToAuthMenuGrid(athCd);
      await this.setDataToMenuDataGrid(athCd);
    },
    onClickButton({ name }) {
      if (name === 'select') {
        this.getAuthManageGrid();
        return;
      }
      if (name === 'save') {
        this.save();
        return;
      }
      if (name === 'new') {
        this.init();
        return;
      }
      if (name === 'addRow') {
        this.addNewRow();
        return;
      }
      if (name === 'menuSave') {
        this.menuSave();
        return;
      }
    },
    onClickExchange({ name }) {
      if (name === 'first') {
        GridUtil.moveToGrid(this.allMenuGrid.$grid, this.authMenuGrid.$grid);
        return;
      }
      if (name === 'second') {
        GridUtil.moveToGrid(this.authMenuGrid.$grid, this.allMenuGrid.$grid);
        return;
      }
    },
  },
};
</script>

<style></style>
