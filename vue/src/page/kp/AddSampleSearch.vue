<template>
  <AUIGridSearch
    v-bind="addSampleGrid"
    @grid-created="(proxy) => $setState('addSampleGrid.$grid', proxy)"
    @button-click="fetchAddSampleGrid"
  />
  <FormWithHeader v-bind="requestForm" />
</template>

<script>
import { TestSearchModal } from '@/page/modal';
import { FormUtil } from '@/util/index.js';

import values from './values/addSampleSearch.js';

export default {
  name: 'AddSampleSearch',
  components: {
    TestSearchModal,
  },
  data() {
    const { addSampleGrid, requestForm } = values;
    return {
      addSampleGrid: {
        ...addSampleGrid.static,
        forms: addSampleGrid.forms(),
        columns: addSampleGrid.columns(),
        event: {
          cellDoubleClick: (event) => {
            this.setDataToForm(event.item);
          },
        },
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
    async fetchAddSampleGrid() {
      const { forms, $grid } = this.addSampleGrid;
      const param = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/kp/addSampleSearch', param))
        .then(({ data }) => data)
        .catch(() => this.$error(this.$message.error.fetchData));
      $grid.setGridData(data);
      this.init();
    },
    setDataToForm(data) {
      FormUtil.setData(this.requestForm.forms, data);
    },
  },
  mounted() {
    this.fetchAddSampleGrid();
  },
};
</script>

<style></style>
