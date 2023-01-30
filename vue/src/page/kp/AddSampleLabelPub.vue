<template>
  <AUIGridSearch
    v-bind="sampleGrid"
    @grid-created="(proxy) => $setState('sampleGrid.$grid', proxy)"
  />
  <FormWithHeader v-bind="requestForm" />
</template>

<script>
import { FormUtil } from '@/util/index.js';

import values from './values/addSampleLabelPub.js';

export default {
  name: 'AddSampleLAbelPub',
  data() {
    const { sampleGrid, requestForm } = values;
    return {
      sampleGrid: {
        ...sampleGrid.static,
        forms: sampleGrid.forms(),
        columns: sampleGrid.columns(),
      },
      requestForm: {
        ...requestForm.static,
        forms: requestForm.forms(),
      },
    };
  },
  methods: {
    init() {
      this.requestForm.forms = values.requestForm.forms();
    },
    async fetchSampleGrid() {
      const { forms, $grid } = this.sampleGrid;
      const param = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/kp/addSampleLabelPub', param))
        .then(({ data }) => data)
        .catch(() => {
          this.$error(this.$message.error.fetchData);
        });
      $grid.setGridData(data);
      this.init();
    },
    setDataToform() {},
  },
  mounted() {
    this.fetchSampleGrid();
  },
};
</script>

<style></style>
