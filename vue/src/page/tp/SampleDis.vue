<template>
  <AUIGridSearch
    v-bind="sampleDisGrid"
    @grid-created="(proxy) => $setState('sampleDisGrid.$grid', proxy)"
    @button-click="onClickSampleDisGridButtons"
  />
  <RequestApproverModal
    :show="requestDisApproverModal.show"
    :aprReqDiv="requestDisApproverModal.aprReqDiv"
    @close="hideModal('requestDis')"
    @modalReturnDataEvent="requestDisDataReturn"
  />
  <RequestApproverModal
    :show="requestCancelDisApproverModal.show"
    :aprReqDiv="requestCancelDisApproverModal.aprReqDiv"
    @close="hideModal('requestCancelDis')"
    @modalReturnDataEvent="requestCancelDisDataReturn"
  />
</template>

<script>
import { RequestApproverModal } from '@/page/modal';
import { FormUtil } from '@/util/index.js';

import values from './values/sampleDis.js';

const REQUEST_DISPOSAL = 'S0270400';
const REQUEST_DISPOSAL_CANCEL = 'S0270600';

export default {
  name: 'sampleDis',
  components: {
    RequestApproverModal,
  },
  data() {
    const { sampleDisGrid } = values;

    return {
      sampleDisGrid: {
        ...sampleDisGrid.static,
        forms: sampleDisGrid.forms(),
        columns: sampleDisGrid.columns(),
      },
      requestDisApproverModal: {
        show: false,
        aprReqDiv: REQUEST_DISPOSAL,
      },
      requestCancelDisApproverModal: {
        show: false,
        aprReqDiv: REQUEST_DISPOSAL_CANCEL,
      },
    };
  },
  methods: {
    init() {},
    async fetchSampleDisGrid() {
      const { $grid, forms } = this.sampleDisGrid;
      const param = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/tp/sampleDis', param))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    async requestDisDataReturn(data) {
      const checkedRows = this.sampleDisGrid.$grid.getCheckedRowItems();
      const param = checkedRows.map((row) => ({
        ...row.item,
        ...data,
      }));
      await this.$eSign(() => this.$axios.put('/tp/sampleDis/requestDis', param))
        .then(() => {
          this.fetchSampleDisGrid();
          this.$info(this.$message.info.approveRequest);
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    async requestCancelDisDataReturn(data) {
      const checkedRows = this.sampleDisGrid.$grid.getCheckedRowItems();
      const param = checkedRows.map((row) => ({
        ...row.item,
        ...data,
      }));
      await this.$eSign(() => this.$axios.put('/tp/sampleDis/requestCancelDis', param))
        .then(() => {
          this.fetchSampleDisGrid();
          this.$info(this.$message.info.approveRequest);
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    onClickSampleDisGridButtons({ name }) {
      const checkedRows = this.sampleDisGrid.$grid.getCheckedRowItems();
      if (name === 'requestDis') {
        if (checkedRows.length > 0) {
          this.showModal('requestDis');
        } else {
          return this.$warn(this.$message.warn.unSelectedData);
        }
      }
      if (name === 'requestCancelDis') {
        if (checkedRows.length > 0) {
          this.showModal('requestCancelDis');
        } else {
          return this.$warn(this.$message.warn.unSelectedData);
        }
      }
      if (name === 'search') {
        this.init();
        this.fetchSampleDisGrid();
      }
    },
    showModal(name) {
      if (name === 'requestDis') {
        return (this.requestDisApproverModal.show = true);
      }
      if (name === 'requestCancelDis') {
        return (this.requestCancelDisApproverModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'requestDis') {
        return (this.requestDisApproverModal.show = false);
      }
      if (name === 'requestCancelDis') {
        return (this.requestCancelDisApproverModal.show = false);
      }
    },
  },
  mounted() {
    this.fetchSampleDisGrid();
  },
};
</script>

<style></style>
