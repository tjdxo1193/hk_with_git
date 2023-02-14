<template>
  <AUIGridSearch
    v-bind="searchGridWithForm"
    @grid-created="(proxy) => $setState('searchGridWithForm.$grid', proxy)"
    @button-click="fetchSearchGridWithForm"
  />
  <FormWithHeader
    v-bind="inputInfoForm"
    @form-event="inputInfoFormEvent"
    @button-click="onClickInputInfoFormButtons"
  />
  <TestSearchModal
    :show="testSearchModal.show"
    @close="hideModal('testSearchModal')"
    @onModalDataReturn="onSelectData"
  />
</template>

<script>
import { TestSearchModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/sampleManage';

export default {
  name: 'SampleManage',
  components: {
    TestSearchModal,
  },
  mounted() {
    this.init();
    this.fetchSearchGridWithForm();
  },
  data() {
    const { searchGridWithForm, inputInfoForm } = this.$copy(values);
    return {
      searchGridWithForm: {
        ...searchGridWithForm.static,
        forms: searchGridWithForm.forms(),
        columns: searchGridWithForm.columns(),
        event: {
          cellDoubleClick: (event) => {
            this.setDataToForm(event);
          },
        },
      },
      inputInfoForm: {
        ...inputInfoForm.static,
        forms: inputInfoForm.forms(),
      },
      testSearchModal: {
        show: false,
      },
    };
  },
  methods: {
    onSelectData(data) {
      const forms = this.inputInfoForm.forms;
      const buttons = this.inputInfoForm.buttons;
      FormUtil.setData(forms, data);
      FormUtil.disableButtons(buttons, ['update', 'delete']);
      FormUtil.enableButtons(buttons, ['save']);
    },
    init() {
      this.inputInfoForm.forms = values.inputInfoForm.forms();
      const buttons = this.inputInfoForm.buttons;
      FormUtil.disableButtons(buttons, ['update', 'delete']);
      FormUtil.enableButtons(buttons, ['save']);
    },
    async fetchSearchGridWithForm() {
      const param = FormUtil.getData(this.searchGridWithForm.forms);
      const { $grid } = this.searchGridWithForm;
      const data = await $grid
        ._useLoader(() => this.$axios.get('/tp/sampleManage', param))
        .then(({ data }) => data)
        .catch(() => this.$error(this.$message.error.fetchData));
      $grid.setGridData(data);
    },
    async save() {
      const param = FormUtil.getData(this.inputInfoForm.forms);
      await this.$eSign(() => this.$axios.post('/tp/sampleManage', param))
        .then(() => {
          this.init();
          this.fetchSearchGridWithForm();
          this.$info(this.$message.info.saved);
        })
        .catch(() => {});
    },
    async update() {
      const param = FormUtil.getData(this.inputInfoForm.forms);
      await this.$eSignWithReason(() => this.$axios.put('/tp/sampleManage', param))
        .then(() => {
          this.init();
          this.fetchSearchGridWithForm();
          this.$info(this.$message.info.updated);
        })
        .catch(() => {});
    },
    async delete() {
      const param = FormUtil.getData(this.inputInfoForm.forms);
      await this.$eSign(() => this.$axios.put('/tp/sampleManage/delete', param))
        .then(() => {
          this.init();
          this.fetchSearchGridWithForm();
          this.$info(this.$message.info.deleted);
        })
        .catch(() => {});
    },
    setDataToForm(event) {
      const forms = this.inputInfoForm.forms;
      const buttons = this.inputInfoForm.buttons;
      FormUtil.setData(forms, event.item);
      FormUtil.disableButtons(buttons, ['save']);
      FormUtil.enableButtons(buttons, ['update', 'delete']);
    },
    inputInfoFormEvent(event) {
      if (event.originEvent === 'search') {
        this.showModal('testSearchModal');
      }
    },
    showModal(name) {
      if (name === 'testSearchModal') {
        return (this.testSearchModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'testSearchModal') {
        return (this.testSearchModal.show = false);
      }
    },
    onClickInputInfoFormButtons({ name }) {
      if (name === 'save') {
        this.inputInfoForm.forms
          .validate()
          .then(() => this.save())
          .catch(() => {});
      }
      if (name === 'update') {
        this.inputInfoForm.forms
          .validate()
          .then(() => this.update())
          .catch(() => {});
      }
      if (name === 'delete') {
        this.delete();
      }
      if (name === 'init') {
        this.init();
      }
    },
  },
};
</script>

<style></style>
