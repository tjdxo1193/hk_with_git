<template>
  <AUIGridSearch
    v-bind="sampleGrid"
    @grid-created="(proxy) => $setState('sampleGrid.$grid', proxy)"
    @button-click="onSampleGridButtonClick"
    @form-event="sampleGridEvent"
  />
  <FormWithHeader v-bind="requestForm" @button-click="onRequestFormsButtonClick" />
</template>

<script>
import { FormUtil, RdUtil } from '@/util/index.js';

import values from './values/addSampleLabelPub.js';

export default {
  name: 'AddSampleLAbelPub',
  data() {
    const { pitmTypList, sampleGrid, requestForm } = values;
    return {
      pitmTypList,
      sampleGrid: {
        ...sampleGrid.static,
        forms: sampleGrid.forms(),
        columns: sampleGrid.columns(),
        event: {
          cellDoubleClick: (event) => {
            this.sampleGridDoubleClicked(event);
          },
        },
      },
      requestForm: {
        ...requestForm.static,
        forms: requestForm.forms(),
      },
    };
  },
  mounted() {
    this.fetchSampleGrid();
  },
  methods: {
    doInit() {
      this.resetRequestForm();
      this.fetchSampleGrid();
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
    },
    resetRequestForm() {
      this.requestForm.forms = values.requestForm.forms();
    },
    sampleGridDoubleClicked(event) {
      const { forms } = this.requestForm;
      const { item } = event;
      FormUtil.setData(forms, item);
    },
    onSampleGridButtonClick({ name }) {
      if (name === 'search') {
        this.doInit();
      }
    },
    sampleGridEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.doInit();
      }
    },
    onRequestFormsButtonClick({ name }) {
      if (name === 'printLabel') {
        this.printLabel();
      } else if (name === 'reset') {
        this.resetRequestForm();
      }
    },
    printLabel() {
      const { forms } = this.requestForm;
      const parameter = FormUtil.getData(forms);

      if (
        !parameter ||
        !parameter.plntCd ||
        !parameter.ansIdx ||
        !this.pitmTypList.rawMaterial
      ) {
        this.$warn(this.$message.warn.unSelectedData);
        return;
      }

      RdUtil.openReport(
        '/LABEL_PRINT.mrd',
        `/rp [${parameter.plntCd}] [${parameter.ansIdx}] [${this.pitmTypList.rawMaterial}]`,
      );
    },
  },
};
</script>

<style></style>
