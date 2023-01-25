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

  <FinalOrderModal
    :show="finalOrderModal.show"
    @close="hideModal('finalOrderModal')"
  ></FinalOrderModal>

  <RelapsePreventionPlanModal
    :show="relapsePreventionPlanModal.show"
    @close="hideModal('relapsePreventionPlanModal')"
  ></RelapsePreventionPlanModal>

  <SrmReportModal :show="srmReportModal.show" @close="hideModal('srmReportModal')"></SrmReportModal>

  <PackingSpecificationModal
    :show="packingSpecificationModal.show"
    @close="hideModal('packingSpecificationModal')"
  ></PackingSpecificationModal>

  <NonconformityTestModal
    :show="nonconformityTestModal.show"
    :reqIdx="nonconformityTestModal.reqIdx"
    @close="hideModal('nonconformityTestModal')"
  ></NonconformityTestModal>
</template>

<script>
import {
  FinalOrderModal,
  RelapsePreventionPlanModal,
  SrmReportModal,
  PackingSpecificationModal,
  NonconformityTestModal,
} from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/testReceipt';

export default {
  name: 'TestReceipt',
  components: {
    FinalOrderModal,
    RelapsePreventionPlanModal,
    SrmReportModal,
    PackingSpecificationModal,
    NonconformityTestModal,
  },
  mounted() {
    this.getTestReceiptList();
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
            this.nonconformityTestModal.reqIdx = e.item.reqIdx;
            this.getTestAitm(e);
            this.enableButtons([
              'init',
              'specimen',
              'processSpecimen',
              'packagingSpecimen',
              'finalOrder',
              'relapsePrev',
              'srmReport',
              'packagingSpec',
              'nonconformityTestList',
            ]);
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
      },
      finalOrderModal: {
        show: false,
      },
      relapsePreventionPlanModal: {
        show: false,
      },
      srmReportModal: {
        show: false,
      },
      packingSpecificationModal: {
        show: false,
      },
      nonconformityTestModal: {
        show: false,
        reqIdx: '',
      },
      tabs,
    };
  },
  methods: {
    async getTestReceiptList() {
      const parameter = FormUtil.getData(this.list.forms);
      await this.list.$grid
        ._useLoader(() => this.$axios.get('/ts/testReceipt', parameter))
        .then(({ data }) => this.list.$grid.setGridData(data));
    },
    getTestAitm(event) {
      const parameter = event.item;
      FormUtil.setData(this.requestInfo.forms, parameter);
      this.itemList.$grid
        ._useLoader(() => this.$axios.get('/ts/testReceipt/getTestAitm', parameter))
        .then(({ data }) => this.itemList.$grid.setGridData(data));
    },
    receipt(parameter) {
      this.$eSignWithReason(() => this.$axios.put('/ts/testReceipt/receipt', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.getTestReceiptList();
          this.init();
        })
        .catch(() => this.$error(this.$message.error.createData));
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getTestReceiptList();
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        this.getTestReceiptList();
      }
      if (name === 'init') {
        this.init();
      }
      if (name === 'finalOrder') {
        return (this.finalOrderModal.show = true);
      }
      if (name === 'relapsePrev') {
        return (this.relapsePreventionPlanModal.show = true);
      }
      if (name === 'srmReport') {
        return (this.srmReportModal.show = true);
      }
      if (name === 'packagingSpec') {
        return (this.packingSpecificationModal.show = true);
      }
      if (name === 'nonconformityTestList') {
        return (this.nonconformityTestModal.show = true);
      }
      if (name === 'specimen') {
        const parameter = FormUtil.getData(this.requestInfo.forms);
        this.$router.push({
          name: 'specimenManage',
          params: {
            pitmCd: parameter.pitmCd,
            pitmNm: parameter.pitmNm,
            pitmVer: parameter.pitmVer,
          },
        });
        this.init();
      }
      if (name === 'processSpecimen') {
        const parameter = FormUtil.getData(this.requestInfo.forms);
        this.$router.push({
          name: 'processSpecimenManage',
          params: {
            pitmCd: parameter.pitmCd,
            pitmNm: parameter.pitmNm,
            pitmVer: parameter.pitmVer,
            lotNo: parameter.lotNo,
          },
        });
        this.init();
      }
      if (name === 'packagingSpecimen') {
        const parameter = FormUtil.getData(this.requestInfo.forms);
        this.$router.push({
          name: 'packagingSpecimenManage',
          params: {
            pitmCd: parameter.pitmCd,
            pitmNm: parameter.pitmNm,
          },
        });
        this.init();
      }
      if (name === 'receipt') {
        const checkedRows = this.list.$grid.getCheckedRowItems();
        if (checkedRows.length > 0) {
          const parameter = checkedRows.map((row) => ({
            ...row.item,
          }));
          let bool = new Boolean(true);
          parameter.map((item) => {
            if (item.useVerYn == 'N' || item.specUseVerYn == 'N') {
              this.$warn(this.$message.warn.noAprrovedSpec);
              bool = false;
            }
            if (!item.emgYn) {
              this.$warn(this.$message.validate.pleaseSelectEmgYN);
              bool = false;
            }
          });
          if (bool) {
            this.receipt(parameter);
          }
        } else {
          this.$warn(this.$message.warn.unSelectedData);
        }
      }
    },
    init() {
      this.requestInfo.forms = values.requestInfo.forms();
      this.itemList.$grid.clearGridData();
      this.disableButtons([
        'init',
        'specimen',
        'processSpecimen',
        'packagingSpecimen',
        'finalOrder',
        'relapsePrev',
        'srmReport',
        'packagingSpec',
        'nonconformityTestList',
      ]);
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
