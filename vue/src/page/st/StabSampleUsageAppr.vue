<template>
  <FormWithHeader v-bind="searchForm" @button-click="onClickSearchBtn" />
  <AUIGridWithHeader
    v-bind="gridForSearchResult"
    @grid-created="(proxy) => $setState('gridForSearchResult.$grid', proxy)"
  />
  <DesignateApproversModal
    :show="designateApproversModal.show"
    @close="hideDesignateApproversModal()"
  />
  <FormWithHeader v-bind="inputInfoForm" />
  <ActionBar :buttons="buttonGroups.buttons" @button-click="onEventsButton" />
</template>

<script>
import { DesignateApproversModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/stabSampleUsageAppr';

export default {
  name: 'StabSampleUsageApprPage',
  components: {
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
      //동적그리드..
      inputInfoForm: {
        ...inputInfoForm.static,
        forms: inputInfoForm.forms(),
      },

      buttonGroups: {
        buttons: buttonGroups.buttons,
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

    onClickSearchBtn({ name }) {
      if (name == 'search') {
        this.fetchSearchResult();
      }
    },
    //조회결과 그리드
    onEventsButton({ name }) {
      if (name == 'approve') {
        this.approve();
      }
      if (name == 'reject') {
        this.reject();
      }
      if (name == 'init') {
        this.init();
      }
    },
    getItemInfoForInputForm({ item }) {
      FormUtil.setData(this.inputInfoForm.forms, item);
      FormUtil.enableButtons(this.buttonGroups.buttons, ['approve', 'reject']);
    },
    showDesignateApproversModal() {
      this.$setState('designateApproversModal', { show: true });
    },
    hideDesignateApproversModal() {
      this.$setState('designateApproversModal', { show: false });
    },
    approve() {
      this.showDesignateApproversModal();
      const { forms } = this.inputInfoForm;
      const parameter = FormUtil.getData(forms);
      this.$eSign(() => this.$axios.put('/st/stabSampleUsageAppr', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.fetchSearchResult();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
    reject() {
      this.showDesignateApproversModal();
      const { forms } = this.inputInfoForm;
      const parameter = FormUtil.getData(forms);
      this.$eSign(() => this.$axios.put('/st/stabSampleUsageAppr', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.fetchSearchResult();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
    init() {
      this.inputInfoForm.forms = values.inputInfoForm.forms();
      this.gridForSearchResult.$grid.clearGridData();
      FormUtil.disableButtons(this.buttonGroups.buttons, ['approve', 'reject']);
    },
  },
};
</script>

<style></style>
