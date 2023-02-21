<template>
  <AUIGridSearch
    v-bind="list"
    :columns="computedListColumns"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
  />

  <AUIGridSearch
    v-bind="itemList"
    @grid-created="(proxy) => $setState('itemList.$grid', proxy)"
    @button-click="onClickButton"
  />

  <FileAttacherModal
    title="파일등록"
    :show="fileAttacherModal.show"
    @close="hideModal('fileAttacherModal')"
    width="60%"
    height="450px"
  >
  </FileAttacherModal>
</template>

<script>
import { FileAttacherModal } from '@/page/modal';
import { FormUtil, StringUtil } from '@/util';

import values from './values/monitorResultAppr';

export default {
  name: 'MonitorResultAppr',
  components: {
    FileAttacherModal,
  },
  mounted() {
    this.getMonitorTestResultAppr();
  },
  data() {
    const { list, itemList } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => {
            FormUtil.setData(this.itemList.forms, e.item);
            this.getMonitorTestRst(e);
            this.enableButtons(['preResultTrend', 'hold', 'reject', 'approve', 'init']);
          },
        },
      },
      itemList: {
        ...itemList.static,
        forms: itemList.forms(),
        columns: itemList.columns(),
      },
      fileAttacherModal: {
        show: false,
      },
    };
  },
  methods: {
    async getMonitorTestResultAppr() {
      const parameter = FormUtil.getData(this.list.forms);
      await this.list.$grid
        ._useLoader(() => this.$axios.get('/mt/monitorTestResultAppr', parameter))
        .then(({ data }) => this.list.$grid.setGridData(data));
    },
    async setSelectByUpperCd(forms, upperCd, targetName) {
      let targetForm = FormUtil.findItem(forms, targetName);
      let result = await targetForm.async(upperCd).then(({ data }) => (result = data));
      const resultForm = forms.find((item) => item.name === targetName);
      resultForm.value = '';
      resultForm.elements = result;
    },
    getMonitorTestRst(event) {
      const parameter = event.item;
      this.itemList.$grid
        ._useLoader(() => this.$axios.get('/mt/monitorTestResultAppr/getMonitorTestRst', parameter))
        .then(({ data }) => this.itemList.$grid.setGridData(data));
    },
    approve() {
      const parameter = FormUtil.getData(this.itemList.forms);
      this.$eSignWithReason(() => this.$axios.put('/mt/monitorTestResultAppr/approve', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.getMonitorTestResultAppr();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    reject() {
      const parameter = FormUtil.getData(this.itemList.forms);
      this.$eSignWithReason(() => this.$axios.put('/mt/monitorTestResultAppr/reject', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.getMonitorTestResultAppr();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    hold() {
      const parameter = FormUtil.getData(this.itemList.forms);
      this.$confirm(this.$message.confirm.hold).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/mt/monitorTestResultAppr/hold', parameter))
          .then(() => {
            this.$info(this.$message.info.saved);
            this.init();
            this.getMonitorTestResultAppr();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    hasNonResultJudge() {
      const item = FormUtil.getData(this.itemList.forms);
      return StringUtil.isNotEmpty(item.sytJdg);
    },
    searchFormEvent(event) {
      const forms = this.list.forms;
      const value = event.item.value;
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getMonitorTestResultAppr();
      }
      if (event.type === 'change' && event.item.name === 'mitmWrkStudioDiv') {
        this.setSelectByUpperCd(forms, value, 'upperMitmWrkPlcDiv');
      }
      if (event.type === 'change' && event.item.name === 'upperMitmWrkPlcDiv') {
        this.setSelectByUpperCd(forms, value, 'mitmWrkPlcDiv');
      }
      if (event.type === 'change' && event.item.name === 'upperMitmPitmDiv') {
        this.setSelectByUpperCd(forms, value, 'mitmPitmDiv');
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        this.getMonitorTestResultAppr();
      }
      if (name === 'addFile') {
        this.showModal();
      }
      if (name === 'init') {
        this.init();
      }
      if (name === 'preResultTrend') {
        alert('이전결과동향');
      }
      if (name === 'approve') {
        if (!this.hasNonResultJudge()) {
          return this.$warn(this.$message.warn.noResultJudge);
        }
        return this.approve();
      }
      if (name === 'reject') {
        this.reject();
      }
      if (name === 'hold') {
        this.hold();
      }
    },
    init() {
      this.itemList.$grid.clearGridData();
      this.disableButtons(['preResultTrend', 'hold', 'reject', 'approve', 'init']);
    },
    showModal() {
      this.$setState('fileAttacherModal', { show: true });
    },
    hideModal() {
      this.$setState('fileAttacherModal', { show: false });
    },
    enableButtons(buttons) {
      FormUtil.enableButtons(this.itemList.buttons, buttons);
    },
    disableButtons(buttons) {
      FormUtil.disableButtons(this.itemList.buttons, buttons);
    },
  },
  computed: {
    computedListColumns() {
      const editableColumns = [];

      return this.list.columns.map((col) => ({
        ...col,
        editable: editableColumns.includes(col.dataField) ? true : false,
      }));
    },
  },
};
</script>

<style></style>
