<template>
  <AUIGridSearch
    v-bind="printLabelGridWithSearchForms"
    @grid-created="(proxy) => $setState('printLabelGridWithSearchForms.$grid', proxy)"
    @button-click="onPrintLabelGridWithSearchFormsButtonClick"
    @form-event="printLabelGridWithSearchFormsEvent"
  />
  <FormWithHeader
    v-bind="printLabelInfoForms"
    @button-click="onPrintLabelGridWithSearchFormsButtonClick"
  />
</template>

<script>
import { FormUtil, RdUtil } from '@/util';

import values from './values/printLabel';

export default {
  name: 'PrintLabel',
  components: {},
  data() {
    const { pitmTypList, printLabelGridWithSearchForms, printLabelInfoForms } = this.$copy(values);
    return {
      pitmTypList,
      printLabelGridWithSearchForms: {
        ...printLabelGridWithSearchForms.static,
        forms: printLabelGridWithSearchForms.forms(),
        columns: printLabelGridWithSearchForms.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.printLabelGridDoubleClicked(e);
          },
        },
      },
      printLabelInfoForms: {
        ...printLabelInfoForms.static,
        forms: printLabelInfoForms.forms(),
      },
    };
  },
  mounted() {
    this.doInit();
  },
  methods: {
    doInit() {
      this.resetPrintLabelGridWithSearchForms();
      this.fetchPrintLabelGridWithSearchForms();
    },
    async fetchPrintLabelGridWithSearchForms() {
      const { forms, $grid } = this.printLabelGridWithSearchForms;
      const parameter = FormUtil.getData(forms);

      const data = await $grid
        ._useLoader(() => this.$axios.get('/pr/printLabel', parameter))
        .then(({ data }) => data)
        .catch(() => this.$error(this.$message.error.fetchData));

      $grid.setGridData(data);
    },
    printLabelGridDoubleClicked(e) {
      const { forms } = this.printLabelInfoForms;
      const { item } = e;

      FormUtil.setData(forms, item);

      // 재출력 승인 프로세스 후, rptDiv값이 있으면
      const rptDiv = item?.rptDiv;
      if (rptDiv) {
        FormUtil.disable(forms, 'rptDiv');
      } else {
        FormUtil.enable(forms, 'rptDiv');
      }
    },
    onPrintLabelGridWithSearchFormsButtonClick({ name }) {
      if (name === 'select') {
        this.doInit();
      } else if (name === 'printLabel') {
        this.prePrintLabel();
      } else if (name === 'reset') {
        this.resetPrintLabelGridWithSearchForms();
      }
    },
    printLabelGridWithSearchFormsEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.doInit();
      }
    },
    async prePrintLabel() {
      const { forms } = this.printLabelInfoForms;
      const parameter = FormUtil.getData(forms);

      if (!parameter || !parameter.plntCd || !parameter.ansIdx || !this.pitmTypList.rawMaterial) {
        this.$warn(this.$message.warn.unSelectedData);
        return;
      }

      forms.validate().then(() =>
        this.$axios
          .put('/pr/printLabel', parameter)
          .then(({ data }) => data)
          .then(() => this.printLabel())
          .catch(() => this.$error(this.$message.error.printData)),
      );
    },
    printLabel() {
      const { forms } = this.printLabelInfoForms;
      const parameter = FormUtil.getData(forms);

      if (!parameter || !parameter.plntCd || !parameter.ansIdx || !this.pitmTypList.rawMaterial) {
        this.$warn(this.$message.warn.unSelectedData);
        return;
      }

      RdUtil.openReport(
        '/LABEL_PRINT.mrd',
        `/rp [${parameter.plntCd}] [${parameter.ansIdx}] [${this.pitmTypList.rawMaterial}]`,
      );

      this.fetchPrintLabelGridWithSearchForms();
    },
    resetPrintLabelGridWithSearchForms() {
      this.printLabelInfoForms.forms = values.printLabelInfoForms.forms();
    },
  },
};
</script>

<style></style>
