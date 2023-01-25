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
    @check="reject"
  />

  <SpecChangeReasonRegModal
    :show="specChangeReasonRegModal.show"
    :initData="specChangeReasonRegModal.initData"
    @close="hideSpecChangeReasonRegModal()"
    @save="approveRequest"
  />
</template>

<script>
import { SpecChangeReasonRegModal, RejectionReasonModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/specApprRequest';

export default {
  name: 'SpecApprRequest',
  components: {
    SpecChangeReasonRegModal,
    RejectionReasonModal,
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
            this.fetchPItemSpecAItemList(e.item);
          },
        },
      },

      testItemList: {
        ...testItemList.static,
        columns: testItemList.columns(),
      },

      specChangeReasonRegModal: {
        show: false,
        initData: {},
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
        ._useLoader(() => this.$axios.get('ms/specApprRequest/version', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },

    async fetchPItemSpecAItemList({ aitmSpecIdx }) {
      const { $grid } = this.testItemList;
      const obj = { aitmSpecIdx };
      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/specApprRequest/aItem', obj))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    onClickBtnEvent({ name }) {
      if (name == 'search') {
        this.init();
        this.fetchSpecVersion();
      }
      if (name == 'approveRequest') {
        const [selectedItem] = this.versionList.$grid.getSelectedRows();
        if (!selectedItem) {
          this.$warn(this.$message.warn.unSelectedData);
          return;
        }
        selectedItem.mitmWrkPlcDivNm = selectedItem.pitmTypNm;
        selectedItem.mitmPitmDivNm = selectedItem.pitmNm;
        this.setIninDataSpecChangeReasonRegModal(selectedItem);
        this.showSpecChangeReasonRegModal();
      }
      if (name == 'reject') {
        const [selectedItem] = this.versionList.$grid.getSelectedRows();
        if (!selectedItem) {
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
    async approveRequest(item) {
      const [selectedItem] = this.versionList.$grid.getSelectedRows();

      item = {
        ...item,
        pitmSpecIdx: selectedItem.pitmSpecIdx,
        pitmAnsSpecAprIdx: selectedItem.pitmAnsSpecAprIdx,
      };

      await this.$eSign(() => this.$axios.put('/ms/specApprRequest/approvalRequest', item))
        .then(() => {
          this.$info(this.$message.info.approveRequest);
          this.init();
          this.fetchSpecVersion();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
    setIninDataSpecChangeReasonRegModal(selectedItem) {
      this.$setState('specChangeReasonRegModal', { initData: selectedItem });
    },
    showRejectionReasonModal() {
      this.$setState('rejectionReasonModal', { show: true });
    },
    hideRejectionReasonModal() {
      this.$setState('rejectionReasonModal', { show: false });
    },
    showSpecChangeReasonRegModal() {
      this.$setState('specChangeReasonRegModal', { show: true });
    },
    hideSpecChangeReasonRegModal() {
      this.$setState('specChangeReasonRegModal', { show: false });
    },
    reject({ rjtRea }) {
      const [selectedItem] = this.versionList.$grid.getSelectedRows();
      selectedItem.rjtRea = rjtRea;

      this.$eSign(() => this.$axios.put('/ms/specApprRequest/reviewReturn', selectedItem))
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
