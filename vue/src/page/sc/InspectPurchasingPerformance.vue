<template>
  <AUIGridSearch
    v-bind="performanceGrid"
    @grid-created="(proxy) => $setState('performanceGrid.$grid', proxy)"
  />
  <AUIGridWithHeader v-bind="detailGrid"
  @grid-created="(proxy) => $setState('detailGrid.$grid', proxy)"/>
</template>

<script>
import { FormUtil } from '@/util/index.js';
import values from './values/inspectPurchasingPerformance.js';

export default {
  name: 'InspectPurchasingPerformance',
  data() {
    const { performanceGrid, detailGrid } = values;
    return {
      performanceGrid: {
        ...performanceGrid.static,
        forms: performanceGrid.forms(),
        columns: performanceGrid.columns(),
        event: {
          cellDoubleClick: (event) => {
            this.makeParam(event);
          }
        },
      },
      detailGrid: {
        ...detailGrid.static,
        columns: detailGrid.columns(),
      },
    };
  },
  methods: {
    init() {
      const { $grid } = this.detailGrid;
      $grid.clearGridData();
    },
    async fetchPerformanceGrid() {
      const { $grid, forms } = this.performanceGrid;
      const param = FormUtil.getData(forms);
      const data = await $grid._useLoader(() => this.$axios.get('/sc/inspectPurchasingPerformance', param))
        .then(({ data }) => data)
        .catch(() => {
          this.$error(this.$message.error.fetchData);
        });
      $grid.setGridData(data);
    },
    async fetchDetailGrid(param) {
      const { $grid } = this.detailGrid;
      const data = await $grid._useLoader(() => this.$axios.get('/sc/inspectPurchasingPerformance', param))
        .then(({ data }) => data)
        .catch(() => {
          this.$error(this.$message.error.fetchData);
        });
      $grid.setGridData(data);
    },
    makeParam(event) {
      const param = {
        fiscalYr: event.item.fiscalYr,
        month: event.item.month,
        pitmCd: event.item.pitmCd,
        phsOrderNo: event.item.phsOrderNo,
      };
      this.fetchDetailGrid(param);
    }
  },
  mounted() {
    this.fetchPerformanceGrid();
  },
};
</script>

<style></style>
