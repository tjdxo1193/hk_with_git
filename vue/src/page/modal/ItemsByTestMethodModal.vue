<template>
  <ModalBase v-bind="$props" @close="close">
    <AUIGridSearch
      v-bind="searchGrid"
      @button-click="onClickButton"
      @grid-created="(proxy) => $setState('searchGrid.$grid', proxy)"
    />
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/itemsByTestMethodModal';

export default {
  name: 'itemsByTestMethodModal',
  emits: ['close', 'select'],
  props: {
    title: {
      type: String,
      default: '시험항목조회',
    },
    width: {
      type: String,
      default: '800px',
    },
    height: {
      type: String,
      default: '600px',
    },
    show: {
      type: Boolean,
      default: false,
    },
    activedGrid: {
      type: Object,
    },
  },
  watch: {
    show: function () {
      if (this.$props.show) {
        this.searchGrid.forms = values.searchGrid.forms();
        this.fetchItemList();
      }
    },
  },
  mounted() {
    this.fetchItemList();
  },
  data() {
    const { searchGrid } = this.$copy(values);
    return {
      searchGrid: {
        ...searchGrid.static,
        forms: searchGrid.forms(),
        columns: searchGrid.columns(),
        buttons: searchGrid.static.buttons,
      },
    };
  },
  methods: {
    async fetchItemList() {
      const { $grid, forms } = this.searchGrid;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('ms/specManage/itemsByTestMethodModal', parameter))
        .then(({ data }) => data);

      let realData = [...data];
      if (this.$props.activedGrid) {
        const selectedList = this.$props.activedGrid.getGridData();
        realData = data.filter((e) => !selectedList.map((j) => j.amitmCd).includes(e.amitmCd));
      }

      $grid.setGridData(realData);
    },
    getItems() {},
    onClickButton({ name }) {
      if (name === 'search') {
        this.fetchItemList();
      }
      if (name === 'select') {
        this.selectItems();
        this.close();
      }
    },
    close() {
      this.$emit('close');
    },
    selectItems() {
      const items = this.searchGrid.$grid.getCheckedRowItemsAll();
      this.$emit('select', items);
    },
  },
};
</script>

<style></style>
