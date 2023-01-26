<template>
  <AUIGridSearch
    v-bind="performanceGrid"
    @grid-created="(proxy) => $setState('performanceGrid.$grid', proxy)"
    @button-click="fetchPerformanceGrid"
  />
  <Card>
    <AUIGrid
      v-bind="detailGrid"
      @grid-created="(proxy) => $setState('detailGrid.$grid', proxy)"
    />
  </Card>
</template>

<script>
import { FormUtil } from '@/util/index.js';
import values from './values/inspectProductionPerformance.js';

export default {
  name: 'InspectProductionPerformance',
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
      const data = await $grid
        ._useLoader(() => this.$axios.get('/sc/inspectProductionPerformance', param))
        .then(({ data }) => data)
        .catch(() => {
          this.$error(this.$message.error.fetchData);
        });
        $grid.setGridData(data);
        this.init();
    },
    async fetchDetailGrid(param) {
      const { $grid } = this.detailGrid;
      const data = await $grid
        ._useLoader(() => this.$axios.get('/sc/inspectProductionPerformance', param))
        .then(({ data }) => data)
        .catch(() => {
          this.$error(this.$message.error.fetchData);
        });
      $grid.setGridData(data);
    },
    makeParam(event) {
      const param = {
        pdtOrderNo: event.item.pdtOrderNo,
        batchNo: event.item.batchNo,
        pitmCd: event.item.pitmCd,
      };
      this.fetchDetailGrid(param);
    },
  },
  mounted() {
    this.fetchPerformanceGrid();
  },
};
</script>

<style></style>
