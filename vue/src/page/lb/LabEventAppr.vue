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

  <RejectionReasonModal
    title="반려사유"
    :show="rejectionReasonModal.show"
    @close="hideModal('rejectionReasonModal')"
    @check="reasonModalReturnDataEvent"
  />
</template>

<script>
import { RejectionReasonModal } from '@/page/modal';
import { FormUtil } from '@/util/index.js';

import values from './values/labEventAppr';

export default {
  name: 'LabEventAppr',
  components: { RejectionReasonModal },
  data() {
    const { searchGridWithForm, reviewInfoForm, detailInfoForm } = this.$copy(values);

    return {
      searchGridWithForm: {
        ...searchGridWithForm.static,
        forms: searchGridWithForm.forms(),
        columns: searchGridWithForm.columns(),
        event: {
          cellDoubleClick: (event) => {
            this.setFormData(event);
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
        ._useLoader(() => this.$axios.get('/lb/labEventAppr', param))
        .then(({ data }) => data)
        .catch(() => this.$error(this.$message.error.fetchData));
      $grid.setGridData(data);
    },
    async approve(param) {
      this.$confirm(this.$message.confirm.approved).then(() => {
        this.$eSign(() => this.$axios.post('/lb/labEventAppr', param))
          .then(() => {
            this.$info(this.$message.info.approve);
            this.doInit();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    async rejectReview(param) {
      this.$confirm(this.$message.confirm.rejected).then(() => {
        this.$eSign(() => this.$axios.post('/lb/labEventAppr/reject', param))
          .then(() => {
            this.$info(this.$message.info.reject);
            this.doInit();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    getCheckedGridData() {
      const { $grid } = this.searchGridWithForm;
      const checkedRows = $grid.getCheckedRowItems().map((checkedRows) => checkedRows.item);

      return checkedRows;
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
      if (name === 'approve') {
        const checkedRows = this.getCheckedGridData();
        if (checkedRows.length > 0) {
          this.approve(checkedRows);
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
      if (name === 'rejectionReasonModal') {
        this.rejectionReasonModal.show = true;
      }
    },
    hideModal(name) {
      if (name === 'rejectionReasonModal') {
        return (this.rejectionReasonModal.show = false);
      }
    },
    reasonModalReturnDataEvent({ rjtRea }) {
      const checkedRows = this.getCheckedGridData();
      const param = checkedRows.map((checkedRow) => ({ ...checkedRow, rjtRea }));

      this.rejectReview(param);
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
    setFormData(event = null) {
      const reviewInfoFormForms = this.reviewInfoForm.forms;
      const detailInfoFormForms = this.detailInfoForm.forms;
      const item = event?.item;

      if (!item) {
        this.$warn(this.$message.warn.unSelectedData);
        return;
      }

      FormUtil.setData(reviewInfoFormForms, item);
      FormUtil.setData(detailInfoFormForms, item);
      return;
    },
  },
  computed: {},
};
</script>

<style></style>
