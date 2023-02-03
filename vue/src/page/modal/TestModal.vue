<template>
  <ModalBase :title="title" :show="show" width="1000px" height="650px" @close="close">
    <template #action>
      <ActionBar :buttons="searchForm.buttons" @button-click="onClickButton"></ActionBar>
    </template>

    <FormBase v-bind="searchForm" @form-event="searchFormEvent" />
    <Space :gap="15" />
    <AUIGrid v-bind="searchForm" @grid-created="(proxy) => $setState('searchForm.$grid', proxy)" />
  </ModalBase>
</template>

<script>
import { FormUtil, TokenUtil } from '@/util';

import values from './values/testModal';

export default {
  name: 'TestModal',
  emits: ['close', 'modalReturnDataEvent'],
  props: {
    title: {
      type: String,
      default: '품질시험조회',
    },
    show: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    const { searchForm } = this.$copy(values);
    return {
      searchForm: {
        ...searchForm.static,
        forms: searchForm.forms(),
        columns: searchForm.columns(),
        buttons: searchForm.static.buttons,
        event: {
          cellDoubleClick: (e) => {
            this.init();
            this.$emit('modalReturnDataEvent', e.item);
          },
        },
      },
    };
  },
  watch: {
    show() {
      if (this.$props.show) {
        this.getTestList();
      }
    },
  },
  methods: {
    async getTestList() {
      const { $grid, forms } = this.searchForm;
      const parameter = FormUtil.getData(forms);
      parameter.plntCd = TokenUtil.myPlantCode();
      const data = await $grid
        ._useLoader(() => this.$axios.get('/ts/testRequest/getTestList', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getTestList();
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        return this.getTestList();
      }
    },
    init() {
      this.searchForm.$grid.clearGridData();
      this.searchForm.forms = values.searchForm.forms();
    },
    close() {
      this.init();
      this.$emit('close');
    },
  },
};
</script>

<style></style>
