<template>
  <FormWithHeader v-bind="searchForm" @button-click="onClickSearchFormButton" />
  <Horizontal align-itHorizontalems="center" :spans="[5, 5]">
    <AUIGridWithHeader
      v-bind="itemGrid"
      @grid-created="(proxy) => $setState('itemGrid.$grid', proxy)"
    />
    <AUIGridWithHeader
      v-bind="requestGrid"
      @grid-created="(proxy) => $setState('requestGrid.$grid', proxy)"
    />
  </Horizontal>
</template>

<script>
import { FormUtil } from '@/util/index.js';

import values from './values/inspectPurchasingPerformance.js';

export default {
  name: 'InspectPurchasingPerformance',
  data() {
    const { searchForm, itemGrid, requestGrid } = values;
    return {
      searchForm: {
        ...searchForm.static,
        forms: searchForm.forms(),
      },
      itemGrid: {
        ...itemGrid.static,
        columns: itemGrid.columns(),
        event: {
          cellClick: (event) => {
            this.onClickItemGridCell(event);
          },
        },
      },
      requestGrid: {
        ...requestGrid.static,
        columns: requestGrid.columns(),
      },
    };
  },
  methods: {
    initItemGrid() {},
    async fetchItemGrid() {
      const param = FormUtil.getData(this.searchForm.forms);
      const { $grid } = this.itemGrid;
      const data = await $grid
        ._useLoader(() => this.$axios.get('/sc/inspectPurchasingPerformance', param))
        .then(({ data }) => data)
        .catch(() => {
          this.$error(this.$message.error.fetchData);
        });
      $grid.setGridData(data);
    },
    async fetchRequestGrid(param) {
      const { $grid } = this.requestGrid;
      const data = await $grid
        ._useLoader(() => this.$axios.get('/sc/inspectPurchasingPerformance/request', param))
        .then(({ data }) => data)
        .catch(() => {
          this.$error(this.$message.error.fetchData);
        });
      $grid.setGridData(data);
    },
    onClickSearchFormButton({ name }) {
      if (name === 'search') {
        this.fetchItemGrid();
      }
    },
    onClickItemGridCell(event) {
      const param = {
        mtrCd: event.item.mtrCd,
        date: event.item.fiscalYr + '-' + event.item.month,
        phsOrderNo: event.item.phsOrderNo,
      };
      this.fetchRequestGrid(param);
    },
  },
  mounted() {
    this.fetchItemGrid();
  },
};
</script>

<style></style>
