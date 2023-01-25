<template>
  <AUIGridSearch
    v-bind="printManage"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('printManage.$grid', proxy)"
  />

  <FormWithHeader
    v-bind="{ ...printManageInfo }"
    :buttons="computedPrintInfoButtons"
    @button-click="onEventsByDetailButton"
  />
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/printManage';

export default {
  name: 'printManage',
  mounted() {
    this.fetchPrintManage();
  },
  data() {
    const { printManage, printManageInfo } = this.$copy(values);
    return {
      printManage: {
        ...printManage.static,
        forms: printManage.forms(),
        columns: printManage.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.getPrintManageInfoByPrintManageId(e);
            this.enablePrintInfoButtons();
          },
        },
      },
      printManageInfo: {
        ...printManageInfo.static,
        forms: printManageInfo.forms(),
        isDisabledButton: true,
      },
    };
  },
  methods: {
    async fetchPrintManage() {
      const { $grid, forms } = this.printManage;
      const parameter = FormUtil.getData(forms);
      await $grid._useLoader(() => this.$axios.get('', parameter)).then(({ data }) => data);
    },
    getPrintManageInfoByPrintManageId(event) {
      const selectedItem = event.item;
      FormUtil.setData(this.printManageInfo.forms, selectedItem);
    },
    disablePrintInfoButtons() {
      this.printManageInfo.isDisabledButton = true;
    },
    enablePrintInfoButtons() {
      this.printManageInfo.isDisabledButton = false;
    },
    onClickButton({ name }) {
      if (name === 'search') {
        this.fetchPrintManage();
      }
    },
    onEventsByDetailButton({ name }) {
      if (name === 'init') {
        this.initDetail();
      }
      if (name === 'save') {
        this.createPrintManage();
      }
      if (name === 'delete') {
        this.deletePrintManage();
      }
      if (name === 'update') {
        this.updatePrintManage();
      }
    },
    setDetailData(data) {
      this.initDetail();
      FormUtil.setData(this.printManageInfo.forms, data);
    },
    initDetail() {
      this.printManageInfo.forms = values.printManageInfo.forms();
      this.disablePrintInfoButtons();
    },
    isUpdate() {
      return FormUtil.existsValue(this.printManageInfo.forms, 'ntbIdx');
    },
    createPrintManage() {
      const param = FormUtil.getData(this.printManageInfo.forms);
      this.$axios
        .postByForm('', param)
        .then(({ data }) => {
          this.$info(this.$message.info.saved);
          this.fetchPrintManage();
          this.setDetailData(data);
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },
    updatePrintManage() {
      const param = FormUtil.getData(this.printManageInfo.forms);

      this.$axios
        .patchByForm('', param)
        .then(({ data }) => {
          this.$info(this.$message.info.saved);
          this.fetchPrintManage();
          this.setDetailData(data);
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    deletePrintManage() {
      const param = FormUtil.getData(this.printManageInfo.forms);
      this.$axios
        .patchByForm('', param)
        .then(() => {
          this.$info(this.$message.info.saved);
          this.fetchPrintManage();
          this.initDetail();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
  },
};
</script>
//TODO 버튼 disable 처리 확인 후 변경
