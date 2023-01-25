<template>
  <ModalBase :title="title" :show="show" width="900px" :top="50" @close="close">
    <template #action>
      <ActionBar :buttons="searchForm.buttons" @button-click="onClickButton"></ActionBar>
    </template>

    <FormBase v-bind="searchForm" @form-event="searchFormEvent" />
    <Space :gap="10" />
    <AUIGrid v-bind="searchForm" @grid-created="(proxy) => $setState('searchForm.$grid', proxy)" />
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/resultHistoryModal';

export default {
  name: 'ResultHistoryModal',
  emits: ['close'],
  props: {
    title: {
      type: String,
      default: '결과이력조회',
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
      },
    };
  },
  watch: {
    show() {
      if (this.$props.show) {
        this.getResultHistory();
      }
    },
  },
  methods: {
    async getResultHistory() {
      const { $grid, forms } = this.searchForm;
      const param = this.$props.parameter;
      FormUtil.setData(this.searchForm.forms, param);
      const parameter = FormUtil.getData(forms);

      const data = await $grid
        ._useLoader(() => this.$axios.get('/ts/testResultInput/resultHistory', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getResultHistory();
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        this.getResultHistory();
      }
    },
    close() {
      this.$emit('close');
    },
  },
};
</script>

<style></style>
