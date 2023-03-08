<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
  />

  <Card>
    <FormWithHeader v-bind="requestInfo" @form-event="formEvent" @button-click="onClickButton" />
    <FormWithHeader v-bind="itemInfo" />
  </Card>

  <TestModal
    :show="testModal.show"
    @close="hideModal('testModal')"
    @modalReturnDataEvent="testModalReturnDataEvent"
  ></TestModal>
</template>

<script>
import { TestModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/testRequest';

export default {
  name: 'TestRequest',
  components: {
    TestModal,
  },
  mounted() {
    this.getTestRequestList();
  },
  data() {
    const { list, itemInfo, requestInfo } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => {
            FormUtil.setData(this.itemInfo.forms, e.item);
            FormUtil.setData(this.requestInfo.forms, e.item);
            this.enableButtons(['init', 'save']);
            this.disableButtons(['requestRegist']);
            FormUtil.disable(this.requestInfo.forms, ['search']);
            this.setTxtinfo();
          },
        },
      },
      itemInfo: {
        ...itemInfo.static,
        forms: itemInfo.forms(),
      },
      requestInfo: {
        ...requestInfo.static,
        forms: requestInfo.forms(),
      },
      testModal: {
        show: false,
      },
    };
  },
  methods: {
    async getTestRequestList() {
      const parameter = FormUtil.getData(this.list.forms);
      await this.list.$grid
        ._useLoader(() => this.$axios.get('/ts/testRequest', parameter))
        .then(({ data }) => {
          this.list.$grid.setGridData(data);
          this.checkPitmType();
        });
    },
    requestRegist() {
      const parameter = FormUtil.getData(this.requestInfo.forms);
      this.$eSign(() => this.$axios.put('/ts/testRequest/requestRegist', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.getTestRequestList();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    request() {
      const checkedRows = this.list.$grid.getCheckedRowItems();
      if (checkedRows.length > 0) {
        const parameter = checkedRows.map((row) => ({
          ...row.item,
        }));
        this.$eSignWithReason(() => this.$axios.put('/ts/testRequest/request', parameter))
          .then(() => {
            this.$info(this.$message.info.request);
            this.init();
            this.getTestRequestList();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      } else {
        this.$warn(this.$message.warn.unSelectedData);
      }
    },
    save() {
      const parameter = FormUtil.getData(this.requestInfo.forms);
      this.$eSign(() => this.$axios.put('/ts/testRequest/save', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.getTestRequestList();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    checkPitmType() {
      const item = FormUtil.getData(this.list.forms);
      if (item.pitmTyp !== '') {
        //headertext 셋팅
        const data = FormUtil.findItem(this.list.forms, 'pitmTyp').elements.filter(
          (row) => row.value === item.pitmTyp,
        );
        if (data.length !== 0) {
          this.list.$grid.setColumnPropByDataField('group_detail', { headerText: data[0].label });
        }
        let showColumns = this.setColmn(item.pitmTyp);
        this.list.$grid.hideColumnGroup('group_detail');
        this.list.$grid.showColumnByDataField(showColumns);
      } else {
        this.list.$grid.showColumnGroup('group_cmmn');
        this.list.$grid.hideColumnGroup('group_detail');
      }
    },
    checkTestType() {
      const item = FormUtil.getData(this.requestInfo.forms);
      return item.ansTyp === null || item.ansTyp === '' ? true : false;
    },
    setColmn(pitmTyp) {
      let columns;
      if (pitmTyp === 'S0180100' || pitmTyp === 'S0180101') {
        //완제품 - 세트, 단품
        columns = [
          'nbr',
          'mkrVol',
          'mkrVolUnit',
          'useTrm',
          'useLmt',
          'csmBpCd',
          'csmNm',
          'brdAbbr',
          'repBomNo',
          'nomMtr',
          'chagVol',
          'chagVolUnit',
          'pdtOrderTyp',
          'pdtOrderNo',
          'itnPrsCompCd',
          'itnPrsCompNm',
          'itmCtg',
          'otcPrd',
          'dmsEptYn',
          'ctrptNo',
          'perNo',
        ];
      } else if (pitmTyp === 'S0180201' || pitmTyp === 'S0180202') {
        //반제품 - 충전품, 타정품
        columns = [
          'nbr',
          'mkrVol',
          'mkrVolUnit',
          'useTrm',
          'useLmt',
          'csmBpCd',
          'csmNm',
          'brdAbbr',
          'repBomNo',
          'nomMtr',
          'chagVol',
          'chagVolUnit',
          'pdtOrderTyp',
          'pdtOrderNo',
          'itnPrsCompCd',
          'itnPrsCompNm',
          'itmCtg',
          'otcPrd',
          'dmsEptYn',
          'ctrptNo',
          'perNo',
          'labNo',
          'ftnYn',
          'qdrugYn',
          'pearDiv',
          'dioYn',
          'prbInYn',
          'prbFeYn',
          'pnxFeYn',
        ];
      } else if (pitmTyp === 'S0180203') {
        //반제품 - bulk
        columns = [
          'nbr',
          'useTrm',
          'useLmt',
          'csmBpCd',
          'csmNm',
          'brdAbbr',
          'pdtOrderTyp',
          'pdtOrderNo',
          'itnPrsCompCd',
          'itnPrsCompNm',
          'itmCtg',
          'otcPrd',
          'dmsEptYn',
          'perNo',
          'vdrRptRcpCrst',
          'strgLmt',
          'labNo',
          'ftnYn',
          'qdrugYn',
          'pcs01',
          'pcs02',
          'pcs03',
          'pcs04',
          'makEqp',
          'wrkNm',
          'pearDiv',
          'dioYn',
          'prbInYn',
          'prbFeYn',
          'pnxFeYn',
          'etnAnsReq',
        ];
      } else if (pitmTyp === 'S0180204') {
        //반제품 - base
        columns = [
          'useTrm',
          'useLmt',
          'csmBpCd',
          'csmNm',
          'brdAbbr',
          'pdtOrderTyp',
          'pdtOrderNo',
          'itnPrsCompCd',
          'itnPrsCompNm',
          'labNo',
          'otcPrd',
        ];
      } else if (pitmTyp === 'S0180400') {
        //원료
        columns = [
          'useTrm',
          'useLmt',
          'vdrRptRcpCrst',
          'dlvYn',
          'vdrCtrtDt',
          'vdrRsvTm',
          'etrCtnQty',
          'rmtrSpec',
          'rmtrCfmul',
          'ftnYn',
          'qdrugYn',
          'phsUnitPre',
          'amtUnit',
          'currCd',
        ];
      } else if (pitmTyp === 'S0180102') {
        //미품포장재
        columns = ['useTrm', 'useLmt', 'brdAbbr', 'vdrRptRcpCrst'];
      } else if (pitmTyp === 'S0180500') {
        //포장재
        columns = [
          'mkrVol',
          'mkrVolUnit',
          'nomMtr',
          'phsOrderQty',
          'phsOrderUnit',
          'vdrRptRcpCrst',
          'strgLmt',
          'dlvYn',
          'vdrCtrtDt',
          'vdrRsvTm',
          'pkgMtrSpec',
          'ctrptNo',
          'phsUnitPre',
          'amtUnit',
          'currCd',
          'amtLoccurr',
        ];
      } else if (pitmTyp === 'S0180600') {
        //상품
        columns = ['useTrm', 'useLmt', 'brdAbbr', 'vdrRptRcpCrst', 'ftnYn', 'qdrugYn'];
      }
      return columns;
    },
    setTxtinfo() {
      let item = this.list.$grid.getSelectedItems();
      item = item[0].item;
      const columns = this.setColmn(item.pitmTyp);
      let txt = columns.map((row) => {
        const header = this.list.$grid.getColumnItemByDataField(row).headerText;
        if (item[row] !== null) {
          return header + ' : ' + item[row];
        } else {
          return header + ' :';
        }
      });
      FormUtil.setData(this.itemInfo.forms, { detailInfo: txt.join().replaceAll(',', '\n') });
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getTestRequestList();
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        this.getTestRequestList();
      }
      if (name === 'init') {
        this.init();
      }
      if (name === 'requestRegist') {
        if (this.checkTestType()) {
          return this.$warn(this.$message.validate.requiredValueNotInput);
        }
        return this.requestRegist();
      }
      if (name === 'request') {
        this.request();
      }
      if (name === 'save') {
        if (this.checkTestType()) {
          return this.$warn(this.$message.validate.requiredValueNotInput);
        }
        return this.save();
      }
    },
    formEvent(event) {
      if (event.originEvent === 'search') {
        this.showModal(event.item.itemLabel);
      }
    },
    testModalReturnDataEvent(data) {
      FormUtil.setData(this.itemInfo.forms, data);
      FormUtil.setData(this.requestInfo.forms, data);
      FormUtil.setData(this.requestInfo.forms, { ansTyp: 'S0230005' });
      this.hideModal('testModal');
      this.enableButtons(['init', 'requestRegist']);
    },
    init() {
      this.itemInfo.forms = values.itemInfo.forms();
      this.requestInfo.forms = values.requestInfo.forms();
      this.disableButtons(['init', 'save', 'requestRegist']);
    },
    showModal(name) {
      this.$setState(name, { show: true });
    },
    hideModal(name) {
      this.$setState(name, { show: false });
    },
    enableButtons(buttons) {
      FormUtil.enableButtons(this.requestInfo.buttons, buttons);
    },
    disableButtons(buttons) {
      FormUtil.disableButtons(this.requestInfo.buttons, buttons);
    },
  },
};
</script>

<style></style>
