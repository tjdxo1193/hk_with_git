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
    prePrintLabel() {
      const { forms } = this.printLabelInfoForms;
      const { $grid } = this.printLabelGridWithSearchForms;
      const checkedRows = $grid.getCheckedRowItems();
      const rptDiv = FormUtil.getValue(forms, 'rptDiv');

      const parameter = checkedRows.map((row) => {
        return { ...row.item, rptDiv };
      });

      if (!parameter?.length) {
        return this.$warn(this.$message.warn.unCheckedData);
      }

      forms.validate().then(() =>
        this.$eSign(() => this.$axios.put('/pr/printLabel', parameter))
          .then(() => this.printLabel())
          .catch(() => this.$error(this.$message.error.printData)),
      );
    },
    printLabel() {
      const { $grid } = this.printLabelGridWithSearchForms;
      const checkedRows = $grid.getCheckedRowItems();

      const noKeyValue = checkedRows.filter((row) => !row.item.plntCd || !row.item.ansIdx);

      if (!checkedRows?.length || noKeyValue?.length) {
        this.$warn(this.$message.warn.unSelectedData);
        return;
      }

      const plntCd = checkedRows[0].item.plntCd;
      const ansIdxList = checkedRows.map((row) => row.item).map((item) => item.ansIdx);
      const ansIdxListStr = ansIdxList.join();

      RdUtil.openReport(
        '/LABEL_PRINT.mrd',
        `/rp [${plntCd}] [${ansIdxListStr}] [${this.pitmTypList.rawMaterial}]`,
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
