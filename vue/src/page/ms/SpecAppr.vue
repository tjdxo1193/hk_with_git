<template>
  <AUIGridSearch
    v-bind="versionList"
    @grid-created="(proxy) => $setState('versionList.$grid', proxy)"
    @button-click="onClickBtnEvent"
  />

  <AUIGridWithHeader
    v-bind="testItemList"
    @grid-created="(proxy) => $setState('testItemList.$grid', proxy)"
    @button-click="onClickBtnEvent"
  />
  <RejectionReasonModal
    title="반려사유"
    :show="rejectionReasonModal.show"
    @close="hideRejectionReasonModal()"
    width="500px"
    @check="reject"
  />
</template>

<script>
import { RejectionReasonModal, DesignateApproversModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/specAppr';

export default {
  name: 'specAppr',
  components: {
    RejectionReasonModal,
    DesignateApproversModal,
  },
  mounted() {
    this.fetchSpecVersion();
  },
  data() {
    const { versionList, testItemList } = this.$copy(values);
    return {
      versionList: {
        ...versionList.static,
        forms: versionList.forms(),
        columns: versionList.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.fetchPItemSpecAItemList(e);
          },
        },
      },

      testItemList: {
        ...testItemList.static,
        columns: testItemList.columns(),
      },

      rejectionReasonModal: {
        show: false,
      },
    };
  },
  methods: {
    async fetchSpecVersion() {
      const { $grid, forms } = this.versionList;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/specAppr/version', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },

    async fetchPItemSpecAItemList({ item }) {
      const { $grid } = this.testItemList;
      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/specAppr/aItem', item))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    onClickBtnEvent({ name }) {
      if (name == 'search') {
        this.init();
        this.fetchSpecVersion();
      }
      if (name == 'approve') {
        const checkedRows = this.versionList.$grid.getCheckedRowItemsAll();
        if (checkedRows.length <= 0) {
          this.$warn(this.$message.warn.unCheckedData);
          return;
        }
        this.approve();
      }
      if (name == 'reject') {
        const checkedItems = this.versionList.$grid.getCheckedRowItemsAll();
        if (checkedItems.length <= 0) {
          this.$warn(this.$message.warn.unSelectedData);
          return;
        }
        this.showRejectionReasonModal();
      }
    },
    init() {
      this.versionList.$grid.clearGridData();
      this.testItemList.$grid.clearGridData();
    },
    approve() {
      const checkedRows = this.versionList.$grid.getCheckedRowItemsAll();
      this.$eSign(() => this.$axios.put('/ms/specAppr/approval', checkedRows))
        .then(() => {
          this.$info(this.$message.info.approve);
          this.init();
          this.fetchSpecVersion();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
    showRejectionReasonModal() {
      this.$setState('rejectionReasonModal', { show: true });
    },
    hideRejectionReasonModal() {
      this.$setState('rejectionReasonModal', { show: false });
    },
    showDesignateApproversModal() {
      this.$setState('designateApproversModal', { show: true });
    },
    hideDesignateApproversModal() {
      this.$setState('designateApproversModal', { show: false });
    },
    reject({ rjtRea }) {
      const checkedItems = this.versionList.$grid.getCheckedRowItemsAll();
      const parameter = checkedItems.map((data) => ({
        ...data,
        rjtRea,
      }));
      this.$eSign(() => this.$axios.put('/ms/specAppr/reject', parameter))
        .then(() => {
          this.$info(this.$message.info.reject);
          this.init();
          this.fetchSpecVersion();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
  },
};
</script>

<style></style>
