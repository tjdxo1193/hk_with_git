<template>
  <ModalBase :title="title" :show="show" width="1000px" :top="100" @close="close">
    <template #action>
      <ActionBar :buttons="searchForm.buttons" @button-click="onClickButton"></ActionBar>
    </template>

    <FormBase v-bind="searchForm" @form-event="searchFormEvent" />
    <Space :gap="10" />
    <AUIGrid v-bind="searchForm" @grid-created="(proxy) => $setState('searchForm.$grid', proxy)" />
  </ModalBase>
</template>

<script>
import { FormUtil, TokenUtil } from '@/util';

import values from './values/itemModal';

export default {
  name: 'ItemModal',
  emits: ['close', 'modalReturnDataEvent'],
  props: {
    title: {
      type: String,
      default: '품목조회',
    },
    show: {
      type: Boolean,
      default: false,
    },
    yn: {
      type: String,
      default: null,
    },
  },
  mounted() {},
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
            this.$emit('modalReturnDataEvent', e.item);
          },
        },
      },
    };
  },
  watch: {
    show() {
      if (this.$props.show) {
        this.getItemList();
      }
    },
  },
  methods: {
    async getItemList() {
      const { $grid, forms } = this.searchForm;
      const parameter = FormUtil.getData(forms);
      parameter.opsSpecUseVerYn = this.$props.yn;
      parameter.plntCd = TokenUtil.myPlantCode();
      const data = await $grid
        ._useLoader(() => this.$axios.get('/ms/specManage/pItem', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getItemList();
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        return this.getItemList();
      }
    },
    init() {
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
