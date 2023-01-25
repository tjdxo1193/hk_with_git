<template>
  <AUIGridSearch
    v-bind="searchGridWithForm"
    @grid-created="(proxy) => $setState('searchGridWithForm.$grid', proxy)"
    @button-click="onClicklSearchGridWithFormButtons"
  />
  <AUIGridSearch
    v-bind="detailSearchGridWithForm"
    @grid-created="(proxy) => $setState('detailSearchGridWithForm.$grid', proxy)"
    @button-click="onClickDetailSearchGridWithFormButtons"
  />
  <ActionBar :buttons="printButtons.buttons">
    <FormBase v-bind="printForms" />
  </ActionBar>
</template>

<script>
import { FormUtil } from '@/util/index.js';

import values from './values/instManageByItem.js';

export default {
  name: 'instManageByItem',
  data() {
    const { searchGridWithForm, detailSearchGridWithForm, printButtons, printForms } =
      this.$copy(values);
    return {
      searchGridWithForm: {
        ...searchGridWithForm.static,
        forms: searchGridWithForm.forms(),
        columns: searchGridWithForm.columns(),
      },
      detailSearchGridWithForm: {
        ...detailSearchGridWithForm.static,
        forms: detailSearchGridWithForm.forms(),
        columns: detailSearchGridWithForm.columns(),
      },
      printButtons: {
        buttons: printButtons.buttons,
      },
      printForms: {
        ...printForms.static,
        forms: printForms.forms(),
      },
    };
  },
  methods: {
    onClickInit() {
      const { $grid } = this.detailSearchGridWithForm;
      $grid.clearGridData();
    },
    async fetchSearchGridWithForm() {
      this.onClickInit();
      const param = FormUtil.getData(this.searchGridWithForm.forms);
      const { $grid } = this.searchGridWithForm;
      const data = await $grid
        ._useLoader(() => this.$axios.get('/in/instManageByItem', param))
        .then(({ data }) => data)
        .catch(() => {
          this.$error(this.$message.error.fetchData);
        });
      $grid.setGridData(data);
    },
    async fetchDetailSearchGridWithForm(eqmCd) {
      const { $grid } = this.searchGridWithForm;
      const data = await $grid
        ._useLoader(() => this.$axios.get('/in/instManageByItem', { eqmCd }))
        .then(({ data }) => data)
        .catch(() => {
          this.$error(this.$message.error.fetchData);
        });
      $grid.setGridData(data);
    },
    async save() {},
    onClicklSearchGridWithFormButtons({ name }) {
      if (name === 'search') {
        this.fetchSearchGridWithForm();
      }
    },
    onClickDetailSearchGridWithFormButtons({ name }) {
      if (name === 'search') {
        this.fetchDetailSearchGridWithForm();
      }
      if (name === 'save') {
        // 체크한 데이터가 있는지 확인해야함
      }
      if (name === 'init') {
        this.onClickInit();
      }
    },
    onClickPrintButtons({ name }) {
      if (name === 'print') {
        alert('출력');
      }
    },
  },
};
</script>
