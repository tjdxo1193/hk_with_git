<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
    @grid-button-click="gridButtonClick"
  />

  <FormWithHeader v-bind="unsuitableInfo" @button-click="onClickButton" />

  <AUIGridSearch
    v-bind="resultInputInfo"
    @grid-created="(proxy) => $setState('resultInputInfo.$grid', proxy)"
    @grid-button-click="gridButtonClick"
  />

  <FileAttacherModal
    :fileIdx="fileAttacherModal.fileIdx"
    :show="fileAttacherModal.show"
    :readonly="fileAttacherModal.readonly"
    @close="hideModal('fileAttacherModal')"
  />
</template>

<script>
import { FileAttacherModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/testUnsuitableList';

export default {
  name: 'TestUnsuitableList',
  components: {
    FileAttacherModal,
  },
  mounted() {
    this.getTestUnsuitableList();
  },
  data() {
    const { list, resultInputInfo, unsuitableInfo } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => this.getResultDetail(e),
        },
      },
      resultInputInfo: {
        ...resultInputInfo.static,
        columns: resultInputInfo.columns(),
      },
      unsuitableInfo: {
        ...unsuitableInfo.static,
        forms: unsuitableInfo.forms(),
      },
      fileAttacherModal: {
        show: false,
        fileIdx: 0,
        readonly: true,
      },
    };
  },
  methods: {
    async getTestUnsuitableList() {
      const { $grid, forms } = this.list;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/ts/testUnsuitableList', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);

      this.unsuitableInfo.forms = values.unsuitableInfo.forms();
      this.resultInputInfo.$grid.clearGridData();
    },
    async getResultDetail(event) {
      const { $grid } = this.resultInputInfo;
      const selectedItem = event.item;
      const data = await $grid
        ._useLoader(() => this.$axios.get(`/ts/testUnsuitableList/${selectedItem.ansIdx}`))
        .then(({ data }) => data);
      $grid.setGridData(data);
      FormUtil.setData(this.unsuitableInfo.forms, event.item);
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getTestUnsuitableList();
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        return this.getTestUnsuitableList();
      }
    },
    init() {
      this.resultInputInfo.$grid.clearGridData();
    },
    gridButtonClick(event) {
      if (event.dataField === 'fileAttacher') {
        const ansIdx = Number(event.item.ansIdx);
        const rstSeq = Number(event.item.rstSeq);
        return this.showModal('fileAttacherModal', { ansIdx, rstSeq });
      }
    },
    showModal(name, parameter = {}) {
      if (name === 'fileAttacherModal') {
        this.fileAttacherModal.fileIdx = this.getFildIdx(parameter);
        return (this.fileAttacherModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'fileAttacherModal') {
        return (this.fileAttacherModal.show = false);
      }
    },
    getFildIdx(parameter) {
      const isRstSeqEmpty = parameter.rstSeq == 0 ? true : false;
      const selectedItem = isRstSeqEmpty
        ? this.list.$grid.getSelectedItems()
        : this.resultInputInfo.$grid.getSelectedItems();
      return selectedItem[0].item.fileIdx;
    },
  },
};
</script>
