<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
  />

  <Card>
    <TabBase v-bind="tabs" @common-button-click="onClickButton">
      <template #tab-requestInfo>
        <FormBase v-bind="requestInfo" />
      </template>

      <template #tab-testInfo>
        <AUIGrid v-bind="itemList" @grid-created="(proxy) => $setState('itemList.$grid', proxy)" />
      </template>
    </TabBase>
  </Card>

  <RequestApproverModal
    :title="requestApproverModal.title"
    :show="requestApproverModal.show"
    :aprReqDiv="requestApproverModal.aprReqDiv"
    @close="hideModal('requestApproverModal')"
    @modalReturnDataEvent="receipt"
  />
</template>

<script>
import { RequestApproverModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/monitorTestReceipt';

export default {
  name: 'MonitorTestReceipt',
  components: {
    RequestApproverModal,
  },
  mounted() {
    this.getMonitorTestReceipt();
  },
  data() {
    const { list, requestInfo, itemList, tabs } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.getMonitorTestMitm(e);
            this.enableButtons(['init']);
          },
        },
      },
      requestInfo: {
        ...requestInfo.static,
        forms: requestInfo.forms(),
      },
      itemList: {
        ...itemList.static,
        columns: itemList.columns(),
        event: {},
      },
      requestApproverModal: {
        show: false,
        aprReqDiv: 'S0050007',
        title: '지시요청',
      },
      tabs,
    };
  },
  methods: {
    async getMonitorTestReceipt() {
      const parameter = FormUtil.getData(this.list.forms);
      await this.list.$grid
        ._useLoader(() => this.$axios.get('/mt/monitorTestReceipt', parameter))
        .then(({ data }) => this.list.$grid.setGridData(data));
    },
    async setSelectByUpperCd(forms, upperCd, targetName) {
      let targetForm = FormUtil.findItem(forms, targetName);
      let result = await targetForm.async(upperCd).then(({ data }) => (result = data));
      const resultForm = forms.find((item) => item.name === targetName);
      resultForm.value = '';
      resultForm.elements = result;
    },
    getMonitorTestMitm(event) {
      const parameter = event.item;
      const aitmSpecIdx = event.item.aitmSpecIdx;
      FormUtil.setData(this.requestInfo.forms, parameter);
      this.itemList.$grid
        ._useLoader(() =>
          this.$axios.get('/mt/monitorTestReceipt/getMonitorTestMitm', { aitmSpecIdx }),
        )
        .then(({ data }) => this.itemList.$grid.setGridData(data));
    },
    receipt(approveInfo) {
      const checkedRows = this.list.$grid.getCheckedRowItems();
      const parameter = checkedRows.map((row) => ({
        ...row.item,
        approveInfo,
      }));
      this.$eSignWithReason(() => this.$axios.put('/mt/monitorTestReceipt/receipt', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.getMonitorTestReceipt();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    searchFormEvent(event) {
      const forms = this.list.forms;
      const value = event.item.value;
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getMonitorTestReceipt();
      }
      if (event.type === 'change' && event.item.name === 'mitmWrkStudioDiv') {
        this.setSelectByUpperCd(forms, value, 'upperMitmWrkPlcDiv');
      }
      if (event.type === 'change' && event.item.name === 'upperMitmWrkPlcDiv') {
        this.setSelectByUpperCd(forms, value, 'mitmWrkPlcDiv');
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        this.getMonitorTestReceipt();
      }
      if (name === 'receipt') {
        const checkedRows = this.list.$grid.getCheckedRowItems();
        if (checkedRows.length > 0) {
          this.showModal('requestApproverModal');
        } else {
          this.$warn(this.$message.warn.unSelectedData);
        }
      }
      if (name === 'init') {
        this.init();
      }
    },
    init() {
      this.requestInfo.forms = values.requestInfo.forms();
      this.itemList.$grid.clearGridData();
      this.disableButtons(['init']);
    },
    showModal(name) {
      this.$setState(name, { show: true });
    },
    hideModal(name) {
      this.$setState(name, { show: false });
    },
    enableButtons(buttons) {
      FormUtil.enableButtons(this.tabs.buttons, buttons);
    },
    disableButtons(buttons) {
      FormUtil.disableButtons(this.tabs.buttons, buttons);
    },
  },
};
</script>

<style></style>
