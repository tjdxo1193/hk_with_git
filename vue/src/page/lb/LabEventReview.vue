<template>
  <AUIGridSearch
    v-bind="searchGridWithForm"
    @grid-created="(proxy) => $setState('searchGridWithForm.$grid', proxy)"
    @button-click="onClickSearchGridButtons"
  />

  <Space :gap="10" />

  <FormWithHeader v-bind="reviewInfoForm" />

  <Space :gap="10" />

  <FormWithHeader v-bind="detailInfoForm" />

  <RequestApproverModal
    :show="requestApproverModal.show"
    @close="hideModal('requestApproverModal')"
    @modalReturnDataEvent="requestApprovalReturnDataEvent"
  />

  <RejectionReasonModal
    title="반려사유"
    :show="rejectionReasonModal.show"
    @close="hideModal('rejectionReasonModal')"
    @check="reasonModalReturnDataEvent"
  />
</template>

<script>
import { RequestApproverModal, RejectionReasonModal } from '@/page/modal';
import { FormUtil } from '@/util/index.js';

import values from './values/labEventReview';

export default {
  name: 'LabEventReview',
  components: { RequestApproverModal, RejectionReasonModal },
  data() {
    const { searchGridWithForm, reviewInfoForm, detailInfoForm } = this.$copy(values);

    return {
      searchGridWithForm: {
        ...searchGridWithForm.static,
        forms: searchGridWithForm.forms(),
        columns: searchGridWithForm.columns(),
        event: {
          cellDoubleClick: (event) => {
            this.clearReviewInfoForm();
            this.setDetailForm(event);
          },
        },
      },
      reviewInfoForm: {
        ...reviewInfoForm.static,
        forms: reviewInfoForm.forms(),
      },
      detailInfoForm: {
        ...detailInfoForm.static,
        forms: detailInfoForm.forms(),
      },
      requestApproverModal: {
        show: false,
      },
      rejectionReasonModal: {
        show: false,
      },
    };
  },
  mounted() {
    this.doInit();
  },
  methods: {
    doInit() {
      this.fetchSearchGrid();
      this.clear();
    },
    async fetchSearchGrid() {
      const { $grid, forms } = this.searchGridWithForm;
      const param = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/lb/labEventReview', param))
        .then(({ data }) => data)
        .catch(() => this.$error(this.$message.error.fetchData));
      $grid.setGridData(data);
    },
    async approveRequest(param) {
      this.$eSign(() => this.$axios.post('/lb/labEventReview', param))
        .then(() => {
          this.$info(this.$message.info.approveRequest);
          this.doInit();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    async rejectReview(param) {
      this.$confirm(this.$message.confirm.rejected).then(() => {
        this.$eSign(() => this.$axios.post('/lb/labEventReview/reject', param))
          .then(() => {
            this.$info(this.$message.info.reject);
            this.doInit();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    clear() {
      this.clearSearchForm();
      this.clearReviewInfoForm();
      this.clearDetailInfoForm();
    },
    clearSearchForm() {
      this.searchGridWithForm.forms = values.searchGridWithForm.forms();
    },
    clearReviewInfoForm() {
      this.reviewInfoForm.forms = values.reviewInfoForm.forms();
    },
    clearDetailInfoForm() {
      this.detailInfoForm.forms = values.detailInfoForm.forms();
    },
    setDetailForm(event = null) {
      const { forms } = this.detailInfoForm;
      const item = event?.item;

      if (!item) {
        this.$warn(this.$message.warn.unSelectedData);
        return;
      }

      FormUtil.setData(forms, item);
      return;
    },
    getCheckedGridData() {
      const { $grid } = this.searchGridWithForm;
      const checkedRows = $grid.getCheckedRowItems().map((checkedRows) => checkedRows.item);

      return checkedRows;
    },
    requestApprovalReturnDataEvent(approveInfo) {
      const checkedRows = this.getCheckedGridData();
      const revwCmmt = FormUtil.getValue(this.reviewInfoForm.forms, 'revwCmmt');
      const param = checkedRows.map((checkedRow) => ({ ...checkedRow, approveInfo, revwCmmt }));

      this.approveRequest(param);
    },
    reasonModalReturnDataEvent({ rjtRea }) {
      const checkedRows = this.getCheckedGridData();
      const param = checkedRows.map((checkedRow) => ({ ...checkedRow, rjtRea }));

      this.rejectReview(param);
    },
    onClickSearchGridButtons({ name = null }) {
      if (name === 'search') {
        this.searchGridWithForm.forms
          .validate()
          .then(
            () => this.fetchSearchGrid(),
            this.clearReviewInfoForm(),
            this.clearDetailInfoForm(),
          )
          .catch(() => {});
      }
      if (name === 'requestApprove') {
        const checkedRows = this.getCheckedGridData();
        if (checkedRows.length > 0) {
          this.reviewInfoForm.forms.validate().then(() => {
            this.showModal('requestApproverModal');
          });
        } else {
          this.$warn(this.$message.warn.unSelectedData);
        }
      }
      if (name === 'return') {
        const checkedRows = this.getCheckedGridData();
        if (checkedRows.length > 0) {
          this.showModal('rejectionReasonModal');
        } else {
          this.$warn(this.$message.warn.unSelectedData);
        }
      }
    },
    showModal(name) {
      if (name === 'requestApproverModal') {
        return (this.requestApproverModal.show = true);
      }
      if (name === 'rejectionReasonModal') {
        this.rejectionReasonModal.show = true;
      }
    },
    hideModal(name) {
      if (name === 'requestApproverModal') {
        return (this.requestApproverModal.show = false);
      }
      if (name === 'rejectionReasonModal') {
        return (this.rejectionReasonModal.show = false);
      }
    },
  },
  computed: {},
};
</script>

<style></style>
