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

  <FormBase v-bind="versionValueHiddenForm" />
</template>

<script>
import { SpecChangeReasonRegModal, RejectionReasonModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/monitorSpecApprRequest';

export default {
  name: 'monitorSpecApprRequest',
  components: {
    SpecChangeReasonRegModal,
    RejectionReasonModal,
  },
  mounted() {
    this.fetchMonitorSpecVersion();
  },
  data() {
    const { versionList, testItemList, versionValueHiddenForm } = this.$copy(values);
    return {
      versionList: {
        ...versionList.static,
        forms: versionList.forms(),
        columns: versionList.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.fetchMItemSpecAItemList(e.item);
            this.setVersionValueHiddenForm(e.item);
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
      versionValueHiddenForm: {
        forms: versionValueHiddenForm.forms(),
      },
    };
  },
  methods: {
    async fetchMonitorSpecVersion() {
      const { $grid, forms } = this.versionList;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/monitorSpecApprRequest/version', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },

    async fetchMItemSpecAItemList({ aitmSpecIdx }) {
      const { $grid } = this.testItemList;
      const obj = { aitmSpecIdx };
      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/monitorSpecApprRequest/aItem', obj))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },

    setVersionValueHiddenForm(item) {
      FormUtil.setData(this.versionValueHiddenForm.forms, item);
    },

    onClickBtnEvent({ name }) {
      if (name == 'search') {
        this.init();
        this.fetchMonitorSpecVersion();
      }
      if (name == 'approveRequest') {
        const [selectedItem] = this.versionList.$grid.getSelectedRows();
        if (!selectedItem) {
          this.$warn(this.$message.warn.unSelectedData);
          return;
        }
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
        mitmSpecIdx: selectedItem.mitmSpecIdx,
        mitmSpecAprIdx: selectedItem.mitmSpecAprIdx,
      };

      await this.$eSign(() => this.$axios.put('/ms/monitorSpecApprRequest/approvalRequest', item))
        .then(() => {
          this.$info(this.$message.info.approveRequest);
          this.init();
          this.fetchMonitorSpecVersion();
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

      this.$eSign(() => this.$axios.put('/ms/monitorSpecApprRequest/reviewReturn', selectedItem))
        .then(() => {
          this.$info(this.$message.info.reject);
          this.init();
          this.fetchMonitorSpecVersion();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
  },
};
</script>

<style></style>
