<template>
  <FormWithHeader v-bind="searchForm" @button-click="onClickSearchBtn" />

  <AUIGridWithHeader
    v-bind="gridForSearchResult"
    @grid-created="(proxy) => $setState('gridForSearchResult.$grid', proxy)"
  />

  <FormWithHeader v-bind="inputInfoForm" @form-event="onEventsButtonInform" />

  <SampleItemSearchModal
    :show="sampleItemSearchModal.show"
    @close="hideSampleItemSearchModal()"
    @select="setItemNmInDetailForm"
  />

  <InputReasonModal :show="inputReasonModal.show" @close="hideInputReasonModal()" />

  <ActionBar :buttons="buttonGroups.buttons" @button-click="onEventsButton" />
</template>

<script>
import { SampleItemSearchModal, InputReasonModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/stabSampleReceipt';

export default {
  name: 'StabSampleReceiptPage',
  components: {
    SampleItemSearchModal,
    InputReasonModal,
  },
  mounted() {},
  data() {
    const { searchForm, gridForSearchResult, inputInfoForm, buttonGroups } = this.$copy(values);
    return {
      searchForm: {
        ...searchForm.static,
        forms: searchForm.forms(),
      },

      gridForSearchResult: {
        ...gridForSearchResult.static,
        columns: gridForSearchResult.columns(),
        event: {
          cellDoubleClick: (e) => this.getItemInfoForInputForm(e),
        },
      },
      inputInfoForm: {
        ...inputInfoForm.static,
        forms: inputInfoForm.forms(),
      },

      buttonGroups: {
        buttons: buttonGroups.buttons,
      },

      sampleItemSearchModal: {
        show: false,
      },
      inputReasonModal: {
        show: false,
      },
    };
  },
  methods: {
    //조회폼
    async fetchSearchResult() {
      const { $grid } = this.gridForSearchResult;
      const { forms } = this.searchForm;
      const parameter = FormUtil.getData(forms);
      await $grid
        ._useLoader(() => this.$axios.get('st/stabSampleReceipt', parameter))
        .then(({ data }) => data);
    },
    getItemInfoForInputForm({ item }) {
      FormUtil.setData(this.inputInfoForm.forms, item);
      this.changeButtonTypeToEdit();
    },
    changeButtonTypeToEdit() {
      FormUtil.enableButtons(this.buttonGroups.buttons, ['update', 'delete']);
      FormUtil.disableButtons(this.buttonGroups.buttons, ['save']);
    },
    changeButtonTypeToSave() {
      FormUtil.enableButtons(this.buttonGroups.buttons, ['save']);
      FormUtil.disableButtons(this.buttonGroups.buttons, ['update', 'delete']);
    },
    onClickSearchBtn({ name }) {
      if (name == 'search') {
        this.searchForm.forms
          .validate()
          .then(() => {
            this.fetchSearchResult();
          })
          .catch(() => {
            this.$error(this.$message.validate.requiredValueNotInput);
          });
      }
    },
    onEventsButtonInform(event) {
      if (event.originEvent === 'search') {
        this.showSampleItemSearchModal();
      }
    },
    showSampleItemSearchModal() {
      this.$setState('sampleItemSearchModal', { show: true });
    },
    hideSampleItemSearchModal() {
      this.$setState('sampleItemSearchModal', { show: false });
    },

    showInputReasonModal() {
      this.$setState('inputReasonModal', { show: true });
    },
    hideInputReasonModal() {
      this.$setState('inputReasonModal', { show: false });
    },

    //조회결과 그리드
    onEventsButton({ name }) {
      if (name == 'save') {
        this.inputInfoForm.forms
          .validate()
          .then(() => {
            this.save();
          })
          .catch(() => {
            this.$error(this.$message.validate.requiredValueNotInput);
          });
      }
      if (name == 'update') {
        this.inputInfoForm.forms
          .validate()
          .then(() => {
            this.update();
          })
          .catch(() => {
            this.$error(this.$message.validate.requiredValueNotInput);
          });
      }
      if (name == 'delete') {
        this.delete();
      }
      if (name == 'init') {
        this.init();
      }
    },

    save() {
      const { forms } = this.inputInfoForm;
      const parameter = FormUtil.getData(forms);
      this.$eSign(() => this.$axios.put('/st/stabSampleReceipt', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.fetchSearchResult();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
    update() {
      const { forms } = this.inputInfoForm;
      const parameter = FormUtil.getData(forms);
      this.$eSignWithReason(() => this.$axios.put('/st/stabSampleReceipt', parameter))
        .then(() => {
          this.$info(this.$message.info.updated);
          this.fetchSearchResult();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    delete() {
      const { forms } = this.inputInfoForm;
      const parameter = FormUtil.getData(forms);
      this.$eSignWithReason(() => this.$axios.put('/st/stabSampleReceipt', parameter))
        .then(() => {
          this.$info(this.$message.info.deleted);
          this.fetchSearchResult();
        })
        .catch(() => {
          this.$error(this.$message.error.deleteData);
        });
    },
    init() {
      this.inputInfoForm.forms = values.inputInfoForm.forms();
      this.gridForSearchResult.$grid.clearGridData();
      this.changeButtonTypeToSave();
    },
    setItemNmInDetailForm(item) {
      FormUtil.setData(this.inputInfoForm.forms, item);
    },
  },
};
</script>

<style></style>
