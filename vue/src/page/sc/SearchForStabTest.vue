<template>
  <AUIGridSearch
    v-bind="searchGridWithForm"
    @grid-created="(proxy) => $setState('searchGridWithForm.$grid', proxy)"
    @button-click="onClickSearchGridWithFormButton"
    @grid-button-click="onGridButtonClick"
  />
  <AUIGridWithHeader
    v-bind="aitmSpecGrid"
    @grid-created="(proxy) => $setState('aitmSpecGrid.$grid', proxy)"
    @grid-button-click="onGridButtonClick"
  />
  <FileAttacherModal
    ref="fileAttchterModal"
    :show="fileAttacherModal.show"
    :fileIdx="fileAttacherModal.fileIdx"
    :readonly="fileAttacherModal.readonly"
    @close="hideFileAttacherModal"
  />
</template>

<script>
import { FileAttacherModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/searchForStabTest.js';

export default {
  name: 'searchForStabTest',
  components: {
    FileAttacherModal,
  },
  data() {
    const { searchGridWithForm, aitmSpecGrid } = values;
    return {
      searchGridWithForm: {
        ...searchGridWithForm.static,
        forms: searchGridWithForm.forms(),
        columns: searchGridWithForm.columns(),
        event: {
          cellClick: (event) => {
            this.onClickSearchGridWithFormCell(event);
          },
        },
      },
      aitmSpecGrid: {
        ...aitmSpecGrid.static,
        columns: aitmSpecGrid.columns(),
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
    async fetchSearchGridWithForm() {
      const param = FormUtil.getData(this.searchGridWithForm.forms);
      const { $grid } = this.searchGridWithForm;
      const data = await $grid
        ._useLoader(() => this.$axios.get('/sc/searchForStabTest', param))
        .then(({ data }) => data)
        .catch(() => {
          this.$error(this.$message.error.fetchData);
        });
      $grid.setGridData(data);
    },
    async fetchAitmSpecGrid(ansIdx) {
      const { $grid } = this.aitmSpecGrid;
      const data = await $grid
        ._useLoader(() => this.$axios.get('/sc/searchForStabTest/spec', { ansIdx }))
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
    onClickSearchGridWithFormCell(event) {
      this.fetchAitmSpecGrid(event.item.ansIdx);
    },
    onClickSearchGridWithFormButton({ name }) {
      if (name === 'search') {
        this.fetchSearchGridWithForm();
      }
    },
    showFileAttacherModal(item) {
      this.fileAttacherModal.fileIdx = item.fileIdx;
      this.fileAttacherModal.show = true;
    },
    hideFileAttacherModal() {
      this.fileAttacherModal.show = false;
    },
  },
};
</script>

<style></style>
