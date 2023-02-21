<template>
  <FormWithHeader v-bind="searchForm" @button-click="onClickSearchBtn" />
  <AUIGridWithHeader
    v-bind="gridForSearchResult"
    @grid-created="(proxy) => $setState('gridForSearchResult.$grid', proxy)"
  />
  <AUIGridWithHeader
    v-bind="gridForItemSetting"
    @grid-created="(proxy) => $setState('gridForItemSetting.$grid', proxy)"
  />
  <ActionBar :buttons="buttonGroups.buttons" @button-click="onEventsButton" />
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/stabResultAppr';

export default {
  name: 'StabResultApprPage',
  mounted() {},
  data() {
    const { buttonGroups, gridForSearchResult, searchForm, gridForItemSetting } =
      this.$copy(values);
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
      //동적그리드..
      gridForItemSetting: {
        ...gridForItemSetting.static,
        columns: gridForItemSetting.columns(),
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
        ._useLoader(() => this.$axios.get('st/stabResultAppr', parameter))
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

    approve() {
      const checkedRows = this.gridForSearchResult.$grid.getCheckedRowItems();

      if (checkedRows.length > 0) {
        this.$eSign(() => this.$axios.put('/st/stabResultAppr', checkedRows))
          .then(() => {
            this.$info(this.$message.info.approve);
            this.fetchSearchResult();
          })
          .catch(() => {
            this.$error(this.$message.error.createData);
          });
      } else {
        this.$warn(this.$message.warn.unSelectedData);
      }
    },
    reject() {
      const checkedRows = this.gridForSearchResult.$grid.getCheckedRowItems();

      if (checkedRows.length > 0) {
        this.$eSign(() => this.$axios.put('/st/stabResultAppr', checkedRows))
          .then(() => {
            this.$info(this.$message.info.reject);
            this.fetchSearchResult();
          })
          .catch(() => {
            this.$error(this.$message.error.createData);
          });
      } else {
        this.$warn(this.$message.warn.unSelectedData);
      }
    },
    init() {
      this.gridForSearchResult.$grid.clearGridData();
      this.gridForItemSetting.$grid.clearGridData();
    },
  },
};
</script>

<style></style>
