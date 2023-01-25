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

import values from './values/stabSampleDisAppr';

export default {
  name: 'StabSampleDisApprPage',
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
        event: {},
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
        ._useLoader(() => this.$axios.get('st/stabSampleDisAppr', parameter))
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
        return;
      }
      if (name == 'reject') {
        this.reject();
        return;
      }
      if (name == 'init') {
        this.init();
        return;
      }
    },

    approve() {
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
    reject() {
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
    init() {
      this.inputInfoForm.forms = values.inputInfoForm.forms();
      this.gridForSearchResult.$grid.clearGridData();
      this.changeButtonTypeToSave();
    },
  },
};
</script>

<style></style>
