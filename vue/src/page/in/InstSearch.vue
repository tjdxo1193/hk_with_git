<template>
  <AUIGridSearch
    v-bind="searchGridWithForm"
    @grid-created="(proxy) => $setState('searchGridWithForm.$grid', proxy)"
    @button-click="onClickSearchGridWithFormButton"
    @grid-button-click="onGridButtonClick"
  />
  <AUIGridWithHeader
    v-bind="historyGrid"
    @grid-created="(proxy) => $setState('historyGrid.$grid', proxy)"
    @button-Click="onClickHistorGridButton"
  />
  <FileAttacherModal
    :show="fileAttacherModal.show"
    :fileIdx="fileAttacherModal.fileIdx"
    :readonly="fileAttacherModal.readonly"
    @close="hideFileAttacherModal"
  />
</template>

<script>
import { FileAttacherModal } from '@/page/modal';
import { FormUtil } from '@/util/index.js';

import values from './values/instSearch.js';

export default {
  name: 'instSearch',
  components: {
    FileAttacherModal,
  },
  data() {
    const { searchGridWithForm, historyGrid } = this.$copy(values);
    return {
      searchGridWithForm: {
        ...searchGridWithForm.static,
        forms: searchGridWithForm.forms(),
        columns: searchGridWithForm.columns(),
        event: {
          cellDoubleClick: (event) => {
            this.fetchHistoryGrid(event);
          },
        },
      },
      historyGrid: {
        ...historyGrid.static,
        columns: historyGrid.columns(),
      },
      fileAttacherModal: {
        fileIdx: 0,
        show: false,
        readonly: true,
      },
    };
  },
  mounted() {
    this.fetchSearchGridWithForm();
  },
  methods: {
    onClickInit() {
      const { $grid } = this.historyGrid;
      $grid.clearGridData();
    },
    async fetchSearchGridWithForm() {
      const param = FormUtil.getData(this.searchGridWithForm.forms);
      const { $grid } = this.searchGridWithForm;
      const data = await $grid
        ._useLoader(() => this.$axios.get('/in/instSearch', param))
        .then(({ data }) => data)
        .catch(() => {
          this.$error(this.$message.error.fetchData);
        });
      $grid.setGridData(data);
    },
    async fetchHistoryGrid(event) {
      const eqmCd = { eqmCd: event.item.eqmCd };
      const { $grid } = this.historyGrid;
      const data = await $grid
        ._useLoader(() => this.$axios.get('/in/instSearch/history', eqmCd))
        .then(({ data }) => data)
        .catch(() => {
          this.$error(this.$message.error.fetchData);
        });
      $grid.setGridData(data);
    },
    onGridButtonClick(event) {
      if (event.dataField === 'fileAttacher') {
        this.showFileAttacherModal(event.item);
      }
    },
    onClickSearchGridWithFormButton({ name }) {
      if (name === 'search') {
        this.fetchSearchGridWithForm();
      }
    },
    onClickHistorGridButton({ name }) {
      if (name === 'excel') {
        alert('excel');
      }
      if (name === 'init') {
        this.onClickInit();
      }
    },
    showFileAttacherModal(item) {
      this.fileAttacherModal.fileIdx = item.eqmFileIdx;
      this.fileAttacherModal.show = true;
    },
    hideFileAttacherModal() {
      this.fileAttacherModal.show = false;
    },
  },
};
</script>

<style></style>
