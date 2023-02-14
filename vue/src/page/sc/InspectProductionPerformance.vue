<template>
  <AUIGridSearch
    v-bind="performanceGrid"
    @grid-created="(proxy) => $setState('performanceGrid.$grid', proxy)"
    @button-click="onClickButton"
  />
  <Card>
    <AUIGrid v-bind="detailGrid" @grid-created="(proxy) => $setState('detailGrid.$grid', proxy)" />
  </Card>

  <InterfaceRecordModal
    :show="interfaceRecordModal.show"
    :ispPdtPfaIdx="interfaceRecordModal.ispPdtPfaIdx"
    @close="hideModal"
  ></InterfaceRecordModal>
</template>

<script>
import dayjs from 'dayjs';

import { InterfaceRecordModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/inspectProductionPerformance';

export default {
  name: 'InspectProductionPerformance',
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
        ispPdtPfaIdx: 0,
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
        ._useLoader(() => this.$axios.get('/sc/inspectProductionPerformance', param))
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
            `/sc/inspectProductionPerformance/${params.mtrCd}/${params.pdtOrderNo}/${params.batchNo}/${params.etrDt}`,
          ),
        )
        .then(({ data }) => data)
        .catch(() => {
          this.$error(this.$message.error.fetchData);
        });
      $grid.setGridData(data);
    },
    dataCheckBeforeIF(checkedRows) {
      if (checkedRows.length == 0) {
        return alert('항목을 선택해 주세요.');
      }
      if (this.isAlreadySended(checkedRows)) {
        return alert('이미 전송된 항목이 있습니다.');
      }
      if (this.isReqDtLessThanYesterday(checkedRows)) {
        return alert('최소 1일전 의뢰건만 보낼 수 있습니다.');
      }
      return true;
    },
    isAlreadySended(checkedRows) {
      const succeedSend = 'S0340002';
      return checkedRows.filter((row) => row.ifStt == succeedSend).length > 0 ? true : false;
    },
    isReqDtLessThanYesterday(checkedRows) {
      const yesterday = dayjs().subtract(1, 'day').format('YYYY-MM-DD');
      return checkedRows.filter((row) => row.etrDt > yesterday).length > 0 ? true : false;
    },
    sendIF(checkedRows) {
      this.$confirm(this.$message.confirm.sended).then(() => {
        this.$eSign(() => this.$axios.post('/sc/inspectProductionPerformance', checkedRows))
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
        return this.fetchPerformanceGrid();
      }
      if (name === 'send') {
        const checkedRows = this.performanceGrid.$grid.getCheckedRowItems().map((row) => row.item);
        if (this.dataCheckBeforeIF(checkedRows)) {
          this.sendIF(checkedRows);
        }
      }
    },
    makeParam(event) {
      const mtrCd = event.item.mtrCd;
      const pdtOrderNo = event.item.pdtOrderNo;
      const batchNo = event.item.batchNo;
      const etrDt = event.item.etrDt;
      return { mtrCd, pdtOrderNo, batchNo, etrDt };
    },
    record(item) {
      if (item.ifStt == null) return;

      this.showModal(item);
    },
    showModal(item) {
      this.interfaceRecordModal.ispPdtPfaIdx = item.ispPdtPfaIdx;
      this.interfaceRecordModal.show = true;
    },
    hideModal() {
      this.interfaceRecordModal.show = false;
    },
  },
};
</script>

<style>
.success {
  color: blue;
}
.fail {
  color: red;
}
</style>
