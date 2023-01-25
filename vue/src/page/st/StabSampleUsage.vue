<template>
  <FormWithHeader v-bind="searchForm" @button-click="onClickSearchBtn" />
  <AUIGridWithHeader
    v-bind="gridForSearchResult"
    @grid-created="(proxy) => $setState('gridForSearchResult.$grid', proxy)"
  />
  <FormWithHeader v-bind="inputInfoForm" @form-event="onEventsButtonInform" />

  <StabSampleItemSearchModal
    :show="stabSampleItemSearchModal.show"
    @close="hideStabSampleItemSearchModal()"
    @select="setItemNmInDetailForm"
  />

  <InputReasonModal :show="inputReasonModal.show" @close="hideInputReasonModal()" />
  <DesignateApproversModal
    :show="designateApproversModal.show"
    @close="hideDesignateApproversModal()"
  />
  <ActionBar :buttons="buttonGroups.buttons" @button-click="onEventsButton" />
</template>

<script>
import { StabSampleItemSearchModal, InputReasonModal, DesignateApproversModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/stabSampleUsage';

export default {
  name: 'StabSampleUsagePage',
  components: {
    StabSampleItemSearchModal,
    InputReasonModal,
    DesignateApproversModal,
  },
  mounted() {
    this.gridForSearchResult.$grid.setGridData(this.gridForSearchResult.data);
  },
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

      stabSampleItemSearchModal: {
        show: false,
      },

      inputReasonModal: {
        show: false,
      },
      designateApproversModal: {
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
        ._useLoader(() => this.$axios.get('st/stabSampleUsage', parameter))
        .then(({ data }) => data);
    },

    getItemInfoForInputForm({ item }) {
      FormUtil.setData(this.inputInfoForm.forms, item);
      if (item.div == '1') {
        this.changeButtonTypeToEdit();
        return;
      }
      if (item.div == '2') {
        this.changeButtonTypeToCancel();
        return;
      }
      if (item.div == '3') {
        this.changeButtonTypeToSave();
        return;
      }
      if (item.div == '4') {
        this.changeButtonTypeToDisposal();
        return;
      }
    },
    changeButtonTypeToEdit() {
      FormUtil.enableButtons(this.buttonGroups.buttons, ['update', 'delete', 'useapproveReq']);
      FormUtil.disableButtons(this.buttonGroups.buttons, ['save', 'useCancelReq']);
    },
    changeButtonTypeToCancel() {
      FormUtil.enableButtons(this.buttonGroups.buttons, ['useCancelReq']);
      FormUtil.disableButtons(this.buttonGroups.buttons, [
        'save',
        'update',
        'delete',
        'useapproveReq',
      ]);
    },
    changeButtonTypeToSave() {
      FormUtil.enableButtons(this.buttonGroups.buttons, ['save']);
      FormUtil.disableButtons(this.buttonGroups.buttons, ['update', 'delete', 'useapproveReq']);
    },
    changeButtonTypeToDisposal() {
      FormUtil.disableButtons(this.buttonGroups.buttons, [
        'save',
        'update',
        'delete',
        'useapproveReq',
        'useCancelReq',
      ]);
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
        this.showStabSampleItemSearchModal();
      }
    },
    showStabSampleItemSearchModal() {
      this.$setState('stabSampleItemSearchModal', { show: true });
    },
    hideStabSampleItemSearchModal() {
      this.$setState('stabSampleItemSearchModal', { show: false });
    },
    showInputReasonModal() {
      this.$setState('inputReasonModal', { show: true });
    },
    hideInputReasonModal() {
      this.$setState('inputReasonModal', { show: false });
    },
    showDesignateApproversModal() {
      this.$setState('designateApproversModal', { show: true });
    },
    hideDesignateApproversModal() {
      this.$setState('designateApproversModal', { show: false });
    },
    //조회결과 그리드
    onEventsButton({ name }) {
      if (name == 'useapproveReq') {
        this.useapproveReq();
        return;
      }
      if (name == 'save') {
        this.inputInfoForm.forms
          .validate()
          .then(() => {
            this.save();
          })
          .catch(() => {
            this.$error(this.$message.validate.requiredValueNotInput);
          });
        return;
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
        return;
      }
      if (name == 'delete') {
        this.delete();
        return;
      }
      if (name == 'useCancelReq') {
        this.useCancelReq();
        return;
      }
      if (name == 'init') {
        this.init();
        return;
      }
    },
    useapproveReq() {
      this.showDesignateApproversModal();
      const { forms } = this.inputInfoForm;
      const parameter = FormUtil.getData(forms);
      this.$eSign(() => this.$axios.put('/st/stabSampleUsage', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.fetchSearchResult();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
    useCancelReq() {
      this.showDesignateApproversModal();
      const { forms } = this.inputInfoForm;
      const parameter = FormUtil.getData(forms);
      this.$eSign(() => this.$axios.put('/st/stabSampleUsage', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.fetchSearchResult();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
    save() {
      const { forms } = this.inputInfoForm;
      const parameter = FormUtil.getData(forms);
      this.$eSign(() => this.$axios.put('/st/stabSampleUsage', parameter))
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
      this.$eSignWithReason(() => this.$axios.put('/st/stabSampleUsage', parameter))
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
      this.$eSignWithReason(() => this.$axios.put('/st/stabSampleUsage', parameter))
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
