<template>
  <AUIGridSearch
    v-bind="performanceGrid"
    @grid-created="(proxy) => $setState('performanceGrid.$grid', proxy)"
    @button-click="onClickButton"
  />
  <AUIGridWithHeader
    v-bind="detailGrid"
    @grid-created="(proxy) => $setState('detailGrid.$grid', proxy)"
  />

  <InterfaceRecordModal
    :show="interfaceRecordModal.show"
    :ispPhsPfaIdx="interfaceRecordModal.ispPhsPfaIdx"
    @close="hideModal"
  ></InterfaceRecordModal>
</template>

<script>
import dayjs from 'dayjs';

import { InterfaceRecordModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/inspectPurchasingPerformance';

export default {
  name: 'InspectPurchasingPerformance',
  components: {
    InterfaceRecordModal,
  },
  data() {
    const { performanceGrid, detailGrid } = values;
    return {
      performanceGrid: {
        ...performanceGrid.static,
        forms: performanceGrid.forms(),
        columns: performanceGrid.columns(),
        event: {
          cellDoubleClick: (event) => {
            const params = this.makeParam(event);
            this.fetchDetailGrid(params);
          },
          rowAllChkClick: (event) => {
            if (event.checked) {
              const beforeSend = 'S0340001';
              const failSend = 'S0340003';

              this.performanceGrid.$grid.addCheckedRowsByValue('ifStt', [
                'null',
                beforeSend,
                failSend,
              ]);
            } else {
              this.performanceGrid.$grid.setCheckedRowsByValue('ifStt', []);
            }
          },
          cellClick: (event) => {
            if (event.dataField == 'ifSttNm') {
              this.record(event.item);
            }
          },
        },
      },
      detailGrid: {
        ...detailGrid.static,
        columns: detailGrid.columns(),
      },
      interfaceRecordModal: {
        show: false,
        ispPhsPfaIdx: 0,
      },
    };
  },
  mounted() {
    this.fetchPerformanceGrid();
  },
  methods: {
    init() {
      const { $grid } = this.detailGrid;
      $grid.clearGridData();
    },
    async fetchPerformanceGrid() {
      const { $grid, forms } = this.performanceGrid;
      const param = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/sc/inspectPurchasingPerformance', param))
        .then(({ data }) => data)
        .catch(() => {
          this.$error(this.$message.error.fetchData);
        });
      $grid.setGridData(data);
      this.init();
    },
    async fetchDetailGrid(params) {
      const { $grid } = this.detailGrid;
      const data = await $grid
        ._useLoader(() =>
          this.$axios.get(
            `/sc/inspectPurchasingPerformance/${params.plntCd}/${params.mtrCd}/${params.phsOrderNo}/${params.etrDt}`,
          ),
        )
        .then(({ data }) => data)
        .catch(() => {
          this.$error(this.$message.error.fetchData);
        });
      $grid.setGridData(data);
    },
    checkedSearchData() {
      this.performanceGrid.forms.validate().then(() => {
        this.fetchPerformanceGrid();
      });
    },
    dataCheckBeforeIF(checkedRows) {
      if (checkedRows.length == 0) {
        return alert('항목을 선택해 주세요.');
      }
      if (this.isAlreadySended(checkedRows)) {
        return alert('이미 전송된 항목이 있습니다.');
      }
      if (this.isReqDtLessThanOneMonthAgo(checkedRows)) {
        return alert('최소 한달 전 항목만 보낼 수 있습니다.');
      }

      return true;
    },
    isAlreadySended(checkedRows) {
      const succeedSend = 'S0340002';
      return checkedRows.filter((row) => row.ifStt == succeedSend).length > 0 ? true : false;
    },
    isReqDtLessThanOneMonthAgo(checkedRows) {
      const nowYear = dayjs().format('YYYY');
      const nowMonth = dayjs().format('MM');
      return checkedRows.filter((row) => row.fiscalYr >= nowYear && row.month >= nowMonth).length >
        0
        ? true
        : false;
    },
    sendIF(checkedRows) {
      this.$confirm(this.$message.confirm.sended).then(() => {
        this.$eSign(() => this.$axios.post('/sc/inspectPurchasingPerformance', checkedRows))
          .then(({ data }) => {
            const succeed = 'S0340002';
            if (data == succeed) this.$info(this.$message.info.sended);
            else this.$error(this.$message.error.sended);
            this.fetchPerformanceGrid();
          })
          .catch(() => {
            this.$error(this.$message.error.sended);
          });
      });
    },
    onClickButton({ name }) {
      if (name === 'search') {
        return this.checkedSearchData();
      }
      if (name === 'send') {
        const checkedRows = this.performanceGrid.$grid.getCheckedRowItems().map((row) => row.item);
        if (this.dataCheckBeforeIF(checkedRows)) {
          this.sendIF(checkedRows);
        }
      }
    },
    makeParam(event) {
      const plntCd = event.item.plntCd;
      const mtrCd = event.item.mtrCd;
      const phsOrderNo = event.item.phsOrderNo;
      const etrDt = event.item.etrDt;

      return { plntCd, mtrCd, phsOrderNo, etrDt };
    },
    record(item) {
      if (item.ifStt == null) return;

      this.showModal(item);
    },
    showModal(item) {
      this.interfaceRecordModal.ispPhsPfaIdx = item.ispPhsPfaIdx;
      this.interfaceRecordModal.show = true;
    },
    hideModal() {
      this.interfaceRecordModal.show = false;
    },
  },
};
</script>
