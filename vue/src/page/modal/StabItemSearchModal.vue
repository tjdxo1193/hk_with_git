<template>
  <ModalBase v-bind="$props" @close="close">
    <AUIGridSearch
      v-bind="inquireForm"
      @button-click="onClickSearchBtn"
      @grid-created="(proxy) => $setState('inquireForm.$grid', proxy)"
    />
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/stabItemSearchModal';
export default {
  name: 'StabItemSearchModal',
  emits: ['close', 'select'],
  props: {
    show: Boolean,
    title: {
      type: String,
      default: '안정성대상 자재조회',
    },
    width: {
      type: String,
      default: '100%',
    },
    height: {
      type: String,
      default: '650px',
    },
  },
  data() {
    const { inquireForm } = this.$copy(values);
    return {
      inquireForm: {
        ...inquireForm.static,
        forms: inquireForm.forms(),
        columns: inquireForm.columns(),
        event: {
          cellDoubleClick: (e) => this.getStabItemByItemCode(e),
        },
      },
    };
  },
  methods: {
    onInit() {
      this.fetchSearchResult();
    },
    async fetchSearchResult() {
      const { $grid, forms } = this.inquireForm;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('st/stabPlan/item', parameter))
        .then(({ data }) => data);

      this.inquireForm.$grid.setGridData(data);
    },

    onClickSearchBtn({ name }) {
      if (name === 'search') {
        // test
        this.fetchSearchResult();
      }
    },
    getStabItemByItemCode({ item }) {
      this.$emit('select', item);
      this.close();
    },
    close() {
      this.$emit('close');
    },
  },
  watch: {
    show: function () {
      if (!this.$props.show) return;

      this.onInit();
    },
  },
};
</script>

<style></style>
