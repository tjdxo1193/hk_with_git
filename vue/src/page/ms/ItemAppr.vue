<template>
  <AUIGridSearch
    v-bind="searchGridWithForm"
    @grid-created="(proxy) => $setState('searchGridWithForm.$grid', proxy)"
    @button-click="onClickSearchGridButtons"
  />
  <FormWithHeader v-bind="commonInfoForm" />
  <FormWithHeader v-bind="materialInfoForm" />
  <FormWithHeader v-bind="sampleInfoForm" />
  <InputReasonModal
    :show="inputReasonModal.show"
    @close="hideModal('inputReasonModal')"
    @modalReturnDataEvent="reject"
  />
</template>

<script>
import { InputReasonModal } from '@/page/modal';
import { FormUtil, TokenUtil } from '@/util/index.js';

import values from './values/itemAppr.js';

export default {
  name: 'itemAppr',
  components: {
    InputReasonModal,
  },
  mounted() {
    this.fetchSearchGrid();
    this.setPlntCd();
  },
  data() {
    const { searchGridWithForm, commonInfoForm, materialInfoForm, sampleInfoForm } =
      this.$copy(values);
    return {
      searchGridWithForm: {
        ...searchGridWithForm.static,
        forms: searchGridWithForm.forms(),
        columns: searchGridWithForm.columns(),
        event: {
          cellDoubleClick: (event) => {
            this.setDataToForms(event);
          },
        },
      },
      commonInfoForm: {
        ...commonInfoForm.static,
        forms: commonInfoForm.forms(),
      },
      materialInfoForm: {
        ...materialInfoForm.static,
        forms: materialInfoForm.forms(),
      },
      sampleInfoForm: {
        ...sampleInfoForm.static,
        forms: sampleInfoForm.forms(),
      },
      inputReasonModal: {
        show: false,
      },
      aprReqDiv: 'S0050005',
    };
  },
  methods: {
    setPlntCd() {
      const plntCd = TokenUtil.myPlantCode();
      FormUtil.setData(this.searchGridWithForm.forms, { plntCd });
    },
    initForms() {
      this.commonInfoForm.forms = values.commonInfoForm.forms();
      this.sampleInfoForm.forms = values.sampleInfoForm.forms();
      this.materialInfoForm.forms = values.materialInfoForm.forms();
    },
    async fetchSearchGrid() {
      const { $grid } = this.searchGridWithForm;
      const param = FormUtil.getData(this.searchGridWithForm.forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/ms/itemAppr', param))
        .then(({ data }) => data)
        .catch(() => this.$error(this.$message.error.fetchData));
      $grid.setGridData(data);
    },
    async requestApprove(checkedRows) {
      const param = checkedRows.map((rows) => ({
        ...rows.item,
      }));
      await this.$eSign(() => this.$axios.put('/ms/itemAppr', param))
        .then(() => {
          this.$info(this.$message.warn.requestApproveAtSpecManage);
          this.fetchSearchGrid();
          this.initForms();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    async inputReasonModalDataEvent(data) {
      const { $grid } = this.searchGridWithForm;
      const checkedRows = $grid.getCheckedRowItems();
      const param = checkedRows.map((rows) => ({
        ...rows.item,
        data,
      }));
      await this.$eSign(() => this.$axios.put('/ms/itemAppr/reject', param))
        .then(() => {
          this.$info(this.$message.info.reject);
          this.fetchSearchGrid();
          this.initForms();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    setDataToForms(event) {
      const data = event.item;
      FormUtil.setData(this.commonInfoForm.forms, data);
      FormUtil.setData(this.sampleInfoForm.forms, data);
      FormUtil.setData(this.materialInfoForm.forms, data);
    },
    showModal(name) {
      if (name === 'inputReasonModal') {
        this.inputReasonModal.show = true;
      }
    },
    hideModal(name) {
      if (name === 'inputReasonModal') {
        this.inputReasonModal.show = false;
      }
    },
    onClickSearchGridButtons({ name }) {
      if (name === 'search') {
        this.searchGridWithForm.forms
          .validate()
          .then(() => this.fetchSearchGrid())
          .catch(() => {});
      }
      if (name === 'approve') {
        const { $grid } = this.searchGridWithForm;
        const checkedRows = $grid.getCheckedRowItems();
        if (checkedRows.length > 0) {
          this.requestApprove(checkedRows);
        } else {
          this.$warn(this.$message.warn.unSelectedData);
        }
      }
      if (name === 'return') {
        const { $grid } = this.searchGridWithForm;
        const checkedRows = $grid.getCheckedRowItems();
        if (checkedRows.length > 0) {
          this.showModal('inputReasonModal');
        } else {
          this.$warn(this.$message.warn.unSelectedData);
        }
      }
    },
  },
};
</script>

<style></style>
