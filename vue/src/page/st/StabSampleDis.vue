<template>
  <FormWithHeader v-bind="searchForm" @button-click="onClickSearchBtn" />
  <AUIGridWithHeader
    v-bind="gridForSearchResult"
    @grid-created="(proxy) => $setState('gridForSearchResult.$grid', proxy)"
  />
  <ActionBar :buttons="buttonGroups.buttons" @button-click="onEventsButton" />
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/stabSampleDis';

export default {
  name: 'StabSampleDis',
  mounted() {},
  data() {
    const { searchForm, gridForSearchResult, buttonGroups } = this.$copy(values);
    return {
      searchForm: {
        ...searchForm.static,
        forms: searchForm.forms(),
      },

      gridForSearchResult: {
        ...gridForSearchResult.static,
        columns: gridForSearchResult.columns(),
        event: { cellDoubleClick: (e) => this.changeButtonEnabled(e) },
      },
      buttonGroups: {
        buttons: buttonGroups.buttons,
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
        ._useLoader(() => this.$axios.get('st/stabSampleDis', parameter))
        .then(({ data }) => data);
    },

    onClickSearchBtn({ name }) {
      if (name == 'search') {
        this.fetchSearchResult();
      }
    },
    changeButtonAllEnabled() {
      FormUtil.enableButtons(this.buttonGroups.buttons, [
        'disposalRequest',
        'disposalCancelRequest',
        'excel',
      ]);
    },
    changeButtonAllDisabled() {
      FormUtil.disableButtons(this.buttonGroups.buttons, [
        'disposalRequest',
        'disposalCancelRequest',
        'excel',
      ]);
    },
    //조회결과 그리드
    onEventsButton({ name }) {
      if (name == 'disposalRequest') {
        this.disposalRequest();
        return;
      }
      if (name == 'disposalCancelRequest') {
        this.disposalCancelRequest();
        return;
      }
      if (name == 'excel') {
        this.excel();
        return;
      }
      if (name == 'init') {
        this.init();
        return;
      }
    },
    isSelectedItemInGrid() {
      return this.gridForSearchResult.$grid.getSelectedItems().length !== 0;
    },
    disposalRequest() {
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
    disposalCancelRequest() {
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
    excel() {
      //엑셀
    },
    init() {
      this.inputInfoForm.forms = values.inputInfoForm.forms();
      this.gridForSearchResult.$grid.clearGridData();
      this.changeButtonAllDisabled();
    },
  },
};
</script>

<style></style>
