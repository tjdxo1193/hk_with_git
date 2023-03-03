<template>
  <ModalBase :title="title" :show="show" width="1000px" height="560px" @close="close">
    <template #action>
      <ActionBar :buttons="searchForm.buttons" @button-click="onClickButton"></ActionBar>
    </template>

    <FormBase v-bind="searchForm" @form-event="searchFormEvent" />
    <Space :gap="10" />
    <AUIGrid v-bind="searchForm" @grid-created="(proxy) => $setState('searchForm.$grid', proxy)" />
    <Space :gap="10" />
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/inputPerformanceModal';

export default {
  name: 'InputPerformanceModal',
  emits: ['close'],
  props: {
    title: {
      type: String,
      default: '투입실적조회',
    },
    show: {
      type: Boolean,
      default: false,
    },
    parameter: {},
  },
  data() {
    const { searchForm } = this.$copy(values);
    return {
      searchForm: {
        ...searchForm.static,
        forms: searchForm.forms(),
        columns: searchForm.columns(),
        buttons: searchForm.static.buttons,
        event: {},
      },
    };
  },
  watch: {
    show() {
      if (this.$props.show) {
        const item = this.$props.parameter;
        FormUtil.setData(this.searchForm.forms, item);
        this.getInpPerformanceList();
      }
    },
  },
  methods: {
    async getInpPerformanceList() {
      const { $grid, forms } = this.searchForm;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/ts/testIFModal/getInpPerformanceList', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.searchForm.forms.validate().then(() => {
          this.getInpPerformanceList();
        });
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        this.searchForm.forms.validate().then(() => {
          this.getInpPerformanceList();
        });
      }
    },
    init() {},
    close() {
      this.init();
      this.$emit('close');
    },
  },
};
</script>

<style></style>
