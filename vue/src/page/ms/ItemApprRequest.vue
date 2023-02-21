<template>
  <AUIGridSearch
    v-bind="searchGridWithForm"
    @grid-created="(proxy) => $setState('searchGridWithForm.$grid', proxy)"
    @button-click="onClickSearchGridButtons"
  />
  <FormWithHeader v-bind="commonInfoForm" @button-click="onClickCommonFormButtons" />
  <FormWithHeader v-bind="materialInfoForm" />
  <FormWithHeader v-bind="sampleInfoForm" />
  <InputReasonForItemReviseModal
    :show="inputReasonForItemReviseModal.show"
    :initData="inputReasonForItemReviseModal.initData"
    @close="hideModal('inputReasonForItemReviseModal')"
    @modalReturnDataEvent="inputReasonForItemReviseModalDataReturn"
  />
  <InputReasonModal
    :show="inputReasonModal.show"
    @close="hideModal('inputReasonModal')"
    @modalReturnDataEvent="inputReasonModalReturnDataEvent"
  />
</template>

<script>
import { InputReasonModal, InputReasonForItemReviseModal } from '@/page/modal';
import { FormUtil, ObjectUtil, TokenUtil } from '@/util';

import values from './values/itemApprRequest.js';

export default {
  name: 'itemApprRequest',
  components: { InputReasonModal, InputReasonForItemReviseModal },
  mounted() {
    this.setPlntCd();
    this.fetchSearchGrid();
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
      inputReasonForItemReviseModal: {
        show: false,
        initData: {},
      },
      aprReqDiv: 'S0050005',
    };
  },
  methods: {
    setPlntCd() {
      const plntCd = TokenUtil.myPlantCode();
      FormUtil.setData(this.searchGridWithForm.forms, { plntCd });
    },
    setIninDataInputReasonForItemRevise() {
      const formData = FormUtil.getData(this.commonInfoForm.forms);
      this.$setState('inputReasonForItemReviseModal', { initData: formData });
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
        ._useLoader(() => this.$axios.get('/ms/itemApprRequest', param))
        .then(({ data }) => data)
        .catch(() => this.$error(this.$message.error.fetchData));
      $grid.setGridData(data);
    },
    setDataToForms(event) {
      const data = event.item;
      FormUtil.setData(this.commonInfoForm.forms, data);
      FormUtil.setData(this.sampleInfoForm.forms, data);
      FormUtil.setData(this.materialInfoForm.forms, data);
    },
    showModal(name) {
      if (name === 'inputReasonModal') {
        return (this.inputReasonModal.show = true);
      }
      if (name === 'inputReasonForItemReviseModal') {
        return (this.inputReasonForItemReviseModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'inputReasonModal') {
        return (this.inputReasonModal.show = false);
      }
      if (name === 'inputReasonForItemReviseModal') {
        return (this.inputReasonForItemReviseModal.show = false);
      }
    },
    async inputReasonModalReturnDataEvent(data) {
      const formData = FormUtil.getData(this.commonInfoForm.forms);
      const param = ObjectUtil.mergeObject(formData, data);
      await this.$eSign(() => this.$axios.put('/ms/itemApprRequest/returnReview', param))
        .then(() => {
          this.$info(this.$message.info.reject);
          this.fetchSearchGrid();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    async inputReasonForItemReviseModalDataReturn(data) {
      const formData = FormUtil.getData(this.commonInfoForm.forms);
      data = { ...data, aprReqDiv: this.aprReqDiv };
      const param = ObjectUtil.mergeObject(data, formData);
      await this.$eSign(() => this.$axios.put('/ms/itemApprRequest', param))
        .then(() => {
          this.$info(this.$message.info.approveRequest);
          this.initForms();
          this.fetchSearchGrid();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    onClickSearchGridButtons({ name }) {
      if (name === 'search') {
        this.searchGridWithForm.forms
          .validate()
          .then(() => this.fetchSearchGrid())
          .catch(() => {});
      }
    },
    onClickCommonFormButtons({ name }) {
      const isValueExist = FormUtil.existsValue(this.commonInfoForm.forms, 'pitmCd');
      if (isValueExist) {
        if (name === 'requestApprove') {
          const data = FormUtil.getData(this.commonInfoForm.forms);
          this.setIninDataInputReasonForItemRevise(data);
          this.showModal('inputReasonForItemReviseModal');
        }
        if (name === 'reject') {
          this.showModal('inputReasonModal');
        }
      } else {
        this.$error(this.$message.warn.unSelectedData);
      }
    },
  },
};
</script>

<style></style>
