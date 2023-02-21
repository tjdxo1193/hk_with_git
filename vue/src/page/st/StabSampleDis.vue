<template>
  <AUIGridSearch
    v-bind="sampleGrid"
    @grid-created="(proxy) => $setState('sampleGrid.$grid', proxy)"
    @button-click="onClickGridButtons"
  />
  <RequestApproverModal
    :show="requestDispose.show"
    :aprReqDiv="requestDispose.aprReqDiv"
    @close="hideModal('requestDispose')"
    @modalReturnDataEvent="onReturnRequestDispose"
  />
  <RequestApproverModal
    :show="requestCancelDispose.show"
    :aprReqDiv="requestCancelDispose.aprReqDiv"
    @close="hideModal('requestCancelDispose')"
    @modalReturnDataEvent="onReturnRequestCancelDispose"
  />
</template>

<script>
import { RequestApproverModal } from '@/page/modal';
import { FormUtil } from '@/util/index.js';

import values from './values/stabSampleDis.js';

const REQUEST_DISPOSAL = 'S0270400';
const REQUEST_DISPOSAL_CANCEL = 'S0270600';
const SAMPLE_DISPOSE = 'S0050017';

export default {
  name: 'StabSampleDis',
  components: {
    RequestApproverModal,
  },
  data() {
    const { sampleGrid } = this.$copy(values);
    return {
      sampleGrid: {
        ...sampleGrid.static,
        forms: sampleGrid.forms(),
        columns: sampleGrid.columns(),
      },
      requestDispose: {
        show: false,
        aprReqDiv: SAMPLE_DISPOSE,
      },
      requestCancelDispose: {
        show: false,
        aprReqDiv: SAMPLE_DISPOSE,
      },
    };
  },
  methods: {
    init() {
      this.sampleGrid.forms = values.sampleGrid.forms();
      const { $grid } = this.sampleGrid;
      $grid.clearGridData();
    },
    async fetchSampleGrid() {
      const { forms, $grid } = this.sampleGrid;
      const param = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/st/stabSampleDis', param))
        .then(({ data }) => data)
        .catch();
      $grid.setGridData(data);
    },
    async onReturnRequestDispose(data) {
      const { $grid } = this.sampleGrid;
      const checkedRows = $grid.getCheckedRowItems();
      const param = checkedRows.map((row) => ({
        ...row.item,
        ...data,
      }));
      await this.$eSign(() => this.$axios.put('/st/stabSampleDis/requestDispose', param))
        .then(() => {
          this.fetchSampleGrid();
          this.$info(this.$message.info.approveRequest);
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    async onReturnRequestCancelDispose(data) {
      const { $grid } = this.sampleGrid;
      const checkedRows = $grid.getCheckedRowItems();
      const param = checkedRows.map((row) => ({
        ...row.item,
        ...data,
      }));
      await this.$eSign(() => this.$axios.put('/st/stabSampleDis/requestCancelDispose', param))
        .then(() => {
          this.fetchSampleGrid();
          this.$info(this.$message.info.approveRequest);
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    isWaitingApproved() {
      const { $grid } = this.sampleGrid;
      const checkedRows = $grid.getCheckedRowItems();
      const param = checkedRows.map((row) => row.item.smpDpsProc);
      return param.every(
        (smpDpsProc) => smpDpsProc !== REQUEST_DISPOSAL && smpDpsProc !== REQUEST_DISPOSAL_CANCEL,
      );
    },
    onClickGridButtons({ name }) {
      const { $grid } = this.sampleGrid;
      const checkedRows = $grid.getCheckedRowItems();
      if (name === 'requestDispose') {
        if (checkedRows.length > 0) {
          if (this.isWaitingApproved()) {
            this.showModal('requestDispose');
          } else {
            return this.$warn(this.$message.warn.noWaitingApproved);
          }
        } else {
          return this.$warn(this.$message.warn.unSelectedData);
        }
      }
      if (name === 'requestCancelDispose') {
        if (checkedRows.length > 0) {
          if (this.isWaitingApproved()) {
            this.showModal('requestCancelDispose');
          } else {
            return this.$warn(this.$message.warn.noWaitingApproved);
          }
        } else {
          return this.$warn(this.$message.warn.unSelectedData);
        }
      }
      if (name === 'search') {
        this.fetchSampleGrid();
      }
    },
    showModal(name) {
      if (name === 'requestDispose') {
        return (this.requestDispose.show = true);
      }
      if (name === 'requestCancelDispose') {
        return (this.requestCancelDispose.show = true);
      }
    },
    hideModal(name) {
      if (name === 'requestDispose') {
        return (this.requestDispose.show = false);
      }
      if (name === 'requestCancelDispose') {
        return (this.requestCancelDispose.show = false);
      }
    },
  },
  mounted() {
    this.fetchSampleGrid();
  },
};
</script>

<style></style>
