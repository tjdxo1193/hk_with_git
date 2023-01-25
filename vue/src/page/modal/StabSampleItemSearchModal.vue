<template>
  <ModalBase v-bind="$props" @close="close">
    <FormWithHeader v-bind="inquireForm" @button-click="onClickSearchBtn" />

    <AUIGridWithHeader
      v-bind="gridForSearchResult"
      @grid-created="(proxy) => $setState('gridForSearchResult.$grid', proxy)"
    />
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/stabSampleItemSearchModal';
export default {
  name: 'stabSampleItemSearchModal',
  emits: ['close', 'select'],
  props: {
    show: Boolean,
    title: {
      type: String,
      default: '안정성검체조회',
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
    const { inquireForm, gridForSearchResult } = this.$copy(values);
    return {
      inquireForm: {
        ...inquireForm.static,
        forms: inquireForm.forms(),
      },
      gridForSearchResult: {
        ...gridForSearchResult.static,
        columns: gridForSearchResult.columns(),
        event: {
          cellDoubleClick: (e) => this.getStabItemByItemCode(e),
        },
      },
    };
  },
  methods: {
    async fetchSearchResult() {
      const { forms } = this.inquireForm;
      const { $grid } = this.gridForSearchResult;
      const parameter = FormUtil.getData(forms);
      await $grid
        ._useLoader(() => this.$axios.get('st/stabSampleItemSearchModal', parameter))
        .then(({ data }) => data);
    },

    onClickSearchBtn({ name }) {
      if (name === 'search') {
        // test
        this.gridForSearchResult.$grid.setGridData(this.gridForSearchResult.data);
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
};
</script>

<style></style>
