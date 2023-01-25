<template>
  <FormWithHeader
    v-bind="searchForm"
    @button-click="onClickSearchFormButton"
    @form-event="searchFormEvent"
  />
  <AUIGridWithHeader
    v-bind="searchResultGrid"
    @grid-created="(proxy) => $setState('searchResultGrid.$grid', proxy)"
    @button-click="onClickSearchGridButton"
  />
  <FormWithHeader v-bind="staffInfoForm" @button-click="onClickStaffInfoFormButton" />
  <InputReasonModal
    :show="inputReasonModal.show"
    @close="hideModal()"
    @modalReturnDataEvent="inputReasonModalReturnDataEvent"
  />
</template>

<script>
import { InputReasonModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/staffJobDelegationApproval';

export default {
  name: 'staffjobDelegationApproval',
  components: {
    InputReasonModal,
  },
  mounted() {
    this.initStaffInfoForm();
    this.fetchSearchResultData();
  },
  data() {
    const { searchForm, searchResultGrid, staffInfoForm } = this.$copy(values);
    return {
      searchForm: {
        ...searchForm.static,
        forms: searchForm.forms(),
      },
      searchResultGrid: {
        ...searchResultGrid.static,
        columns: searchResultGrid.columns(),
        event: {
          cellDoubleClick: (event) => this.setDataToStaffInfoFormByDoubleClick(event),
        },
      },
      staffInfoForm: {
        ...staffInfoForm.static,
        forms: staffInfoForm.forms(),
      },
      inputReasonModal: {
        show: false,
      },
    };
  },
  methods: {
    initStaffInfoForm() {
      this.staffInfoForm.forms = values.staffInfoForm.forms();
    },
    async fetchSearchResultData() {
      const { $grid } = this.searchResultGrid;
      const param = FormUtil.getData(this.searchForm.forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/ms/staffJobDelegationApproval', param))
        .then(({ data }) => data)
        .catch(() => this.$error(this.$message.error.fetchData));
      this.searchResultGrid.$grid.setGridData(data);
    },
    async updateApprove() {
      const approved = 'S0150200';
      const checkedRows = this.searchResultGrid.$grid.getCheckedRowItems();
      const param = checkedRows.map((row) => ({
        ...row.item,
        dlgProcCd: approved,
      }));
      await this.$eSign(() => this.$axios.put('/ms/staffJobDelegationApproval/approve', param))
        .then(() => {
          this.$info(this.$message.info.approve);
          this.initStaffInfoForm();
          this.fetchSearchResultData();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    async updateReject(param) {
      await this.$eSign(() => this.$axios.put('/ms/staffJobDelegationApproval/reject', param))
        .then(() => {
          this.$info(this.$message.info.reject);
          this.initStaffInfoForm();
          this.fetchSearchResultData();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    inputReasonModalReturnDataEvent(data) {
      this.hideModal();
      const delgationRejected = 'S0150110';
      const checkedRows = this.searchResultGrid.$grid.getCheckedRowItems();
      const param = checkedRows.map((row) => ({
        ...row.item,
        dlgProcCd: delgationRejected,
        rjtRea: data.rjtRea,
      }));
      this.updateReject(param);
    },
    showModal() {
      return (this.inputReasonModal.show = true);
    },
    hideModal() {
      return (this.inputReasonModal.show = false);
    },
    setDataToStaffInfoFormByDoubleClick(event) {
      const data = event.item;
      FormUtil.setData(this.staffInfoForm.forms, data);
    },
    searchFormEvent({ originEvent }) {
      if (originEvent.key === 'Enter') {
        this.fetchSearchResultData();
        this.initStaffInfoForm();
      }
    },
    onClickSearchFormButton({ name }) {
      if (name === 'search') {
        this.initStaffInfoForm();
        this.fetchSearchResultData();
        return;
      }
    },
    onClickSearchGridButton({ name }) {
      if (name === 'approve') {
        const checkedRows = this.searchResultGrid.$grid.getCheckedRowItems();
        if (checkedRows.length > 0) {
          return this.updateApprove();
        } else {
          return this.$warn(this.$message.warn.unSelectedData);
        }
      }
      if (name === 'reject') {
        const checkedRows = this.searchResultGrid.$grid.getCheckedRowItems();
        if (checkedRows.length > 0) {
          return this.showModal();
        } else {
          return this.$warn(this.$message.warn.unSelectedData);
        }
      }
    },
    onClickStaffInfoFormButton({ name }) {
      if (name === 'init') {
        this.initStaffInfoForm();
        return;
      }
    },
  },
};
</script>
