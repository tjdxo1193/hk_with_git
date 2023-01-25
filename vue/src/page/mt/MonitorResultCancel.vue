<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
  />

  <AUIGridWithHeader
    v-bind="itemList"
    @grid-created="(proxy) => $setState('itemList.$grid', proxy)"
    @button-click="onClickButton"
  />

  <FileAttacherModal
    title="파일등록"
    :show="fileAttacherModal.show"
    @close="hideModal('fileAttacherModal')"
  >
  </FileAttacherModal>

  <ResultHistoryModal
    title="결과이력조회"
    :show="ResultHistoryModal.show"
    @close="hideModal('ResultHistoryModal')"
  >
  </ResultHistoryModal>
</template>

<script>
import { FileAttacherModal, ResultHistoryModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/monitorResultCancel';

export default {
  name: 'MonitorResultCancel',
  components: {
    FileAttacherModal,
    ResultHistoryModal,
  },
  mounted() {},
  data() {
    const { list, itemList } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.init();
            this.getMonitorTestRst(e);
            if (e.item.ansProcNm === '보류') {
              this.enableButtons(['preResultTrend', 'holdOff', 'testCancel', 'init']);
            } else {
              this.enableButtons(['preResultTrend', 'hold', 'init']);
            }
          },
        },
      },
      itemList: {
        ...itemList.static,
        columns: itemList.columns(),
        event: {
          cellDoubleClick: (e) => this.getTestResultCancel(e),
        },
      },
      fileAttacherModal: {
        show: false,
      },
      ResultHistoryModal: {
        show: false,
      },
    };
  },
  methods: {
    async getMonitorTestResultCancel() {
      const parameter = FormUtil.getData(this.list.forms);
      await this.list.$grid
        ._useLoader(() => this.$axios.get('/mt/monitorTestResultCancel', parameter))
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
        ._useLoader(() =>
          this.$axios.get('/mt/monitorTestResultCancel/getMonitorTestRst', parameter),
        )
        .then(({ data }) => this.itemList.$grid.setGridData(data));
    },
    testCancel() {
      const [parameter] = this.itemList.$grid.getGridData();
      this.$eSignWithReason(() => this.$axios.put('/mt/monitorTestResultCancel/cancel', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.getMonitorTestResultCancel();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    holdnOff(name) {
      const [parameter] = this.itemList.$grid.getGridData();
      let param = {};
      if (name === 'hold') {
        param = { hldYn: 'Y' };
      }
      if (name === 'holdOff') {
        param = { hldYn: 'N' };
      }
      Object.assign(parameter, param);
      this.$eSignWithReason(() =>
        this.$axios.put('/mt/monitorTestResultCancel/holdnOff', parameter),
      )
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.getMonitorTestResultCancel();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    searchFormEvent(event) {
      const forms = this.list.forms;
      const value = event.item.value;
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getMonitorTestResultCancel();
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
        this.getMonitorTestResultCancel();
      }
      if (name === 'addFile') {
        this.showModal();
      }
      if (name === 'init') {
        this.init();
      }
      if (name === 'testCancel') {
        this.testCancel();
      }
      if (name === 'hold') {
        this.holdnOff(name);
      }
      if (name === 'holdOff') {
        this.holdnOff(name);
      }
    },
    gridButtonClick(event) {
      if (event.dataField === 'resultHistory') {
        this.showModal('ResultHistoryModal');
      }
      if (event.dataField === 'fileAttacher') {
        this.showModal('fileAttacherModal');
      }
    },
    init() {
      this.itemList.$grid.clearGridData();
      this.disableButtons(['preResultTrend', 'hold', 'holdOff', 'testCancel', 'init']);
    },
    showModal(name) {
      this.$setState(name, { show: true });
    },
    hideModal(name) {
      this.$setState(name, { show: false });
    },
    enableButtons(buttons) {
      FormUtil.enableButtons(this.itemList.buttons, buttons);
    },
    disableButtons(buttons) {
      FormUtil.disableButtons(this.itemList.buttons, buttons);
    },
  },
};
</script>

<style></style>
