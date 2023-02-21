<template>
  <AUIGridSearch
    v-bind="searchGridWithForm"
    @grid-created="(proxy) => $setState('searchGridWithForm.$grid', proxy)"
    @button-click="onClickSearchGridWithFormButton"
    @form-event="onChangeEventSearchGridWithForm"
  />
  <AUIGridWithHeader
    v-bind="itemInfoGrid"
    @grid-created="(proxy) => $setState('itemInfoGrid.$grid', proxy)"
  />
</template>

<script>
import { FormUtil } from '@/util/index.js';

import values from './values/searchForMonitorTest.js';
export default {
  name: 'SearchForMonitorTest',
  mounted() {
    this.fetchSearchGridWithForm();
  },
  data() {
    const { searchGridWithForm, itemInfoGrid } = values;
    return {
      searchGridWithForm: {
        ...searchGridWithForm.static,
        forms: searchGridWithForm.forms(),
        columns: searchGridWithForm.columns(),
        event: {
          cellDoubleClick: (event) => {
            this.onClickGridWithFormCell(event);
          },
        },
      },
      itemInfoGrid: {
        ...itemInfoGrid.static,
        columns: itemInfoGrid.columns(),
      },
    };
  },
  methods: {
    initItemInfoGrid() {
      const { $grid } = this.itemInfoGrid;
      $grid.clearGridData();
    },
    async fetchSearchGridWithForm() {
      const param = FormUtil.getData(this.searchGridWithForm.forms);
      const { $grid } = this.searchGridWithForm;
      const data = await $grid
        ._useLoader(() => this.$axios.get('/sc/searchForMonitorTest', param))
        .then(({ data }) => data)
        .catch(() => {
          this.$error(this.$message.error.fetchData);
        });
      $grid.setGridData(data);
      this.initItemInfoGrid();
    },
    async fetchItemInfoGrid(mitmReqIdx) {
      const { $grid } = this.itemInfoGrid;
      const data = await $grid
        ._useLoader(() => this.$axios.get('/sc/searchForMonitorTest/aitm', { mitmReqIdx }))
        .then(({ data }) => data)
        .catch(() => {
          this.$error(this.$message.error.fetchData);
        });
      $grid.setGridData(data);
    },
    async setSelectByUpperCd(upperCd, targetNm) {
      const forms = this.searchGridWithForm.forms;
      let targetForm = FormUtil.findItem(forms, targetNm);
      let result = await targetForm.async(upperCd).then(({ data }) => (result = data));
      const resultForm = forms.find((item) => item.name === targetNm);
      resultForm.value = '';
      resultForm.elements = result;
    },
    onClickGridWithFormCell(event) {
      this.fetchItemInfoGrid(event.item.mitmReqIdx);
    },
    onClickSearchGridWithFormButton({ name }) {
      if (name === 'search') {
        this.fetchSearchGridWithForm();
      }
    },
    onChangeEventSearchGridWithForm(event) {
      const value = event.item.value;
      if (event.type === 'change' && event.item.name === 'upperMitmPitmDiv') {
        this.setSelectByUpperCd(value, 'mitmPitmDiv');
      }
      if (event.type === 'change' && event.item.name === 'mitmWrkStudioDiv') {
        this.setSelectByUpperCd(value, 'upperMitmWrkPlcDiv');
      }
      if (event.type === 'change' && event.item.name === 'upperMitmWrkPlcDiv') {
        this.setSelectByUpperCd(value, 'mitmWrkPlcDiv');
      }
    },
  },
};
</script>

<style></style>
