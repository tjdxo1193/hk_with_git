<template>
  <ModalBase v-bind="$props" @close="close">
    <AUIGridSearch
      v-bind="sampleGrid"
      @button-click="fetchSampleGrid"
      @grid-created="(proxy) => $setState('sampleGrid.$grid', proxy)"
    />
    <ActionBar @button-click="onClickBottomButtons" :buttons="bottomButtons.buttons"> </ActionBar>
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
      default: '50%',
    },
  },
  data() {
    const { sampleGrid, bottomButtons } = this.$copy(values);
    return {
      sampleGrid: {
        ...sampleGrid.static,
        forms: sampleGrid.forms(),
        columns: sampleGrid.columns(),
      },
      bottomButtons: {
        ...bottomButtons.static,
      },
    };
  },
  methods: {
    init() {
      const { $grid } = this.sampleGrid;
      this.sampleGrid.forms = values.sampleGrid.forms();
      $grid.sampleGrid;
    },
    async fetchSampleGrid() {
      const { forms, $grid } = this.sampleGrid;
      const param = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/st/stabSampleUsage/sample', param))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    onClickBottomButtons({ name }) {
      if (name === 'select') {
        const { $grid } = this.sampleGrid;
        const selectedRowItem = $grid.getSelectedRows();
        this.$emit('select', selectedRowItem);
        this.close();
      }
      if (name === 'close') {
        this.init();
        this.close();
      }
    },
    close() {
      this.$emit('close');
      this.fetchSampleGrid();
    },
  },
  mounted() {
    this.fetchSampleGrid();
  },
  watch: {
    show() {
      if (this.$props.show) {
        this.fetchSampleGrid();
      }
    },
  },
};
</script>

<style></style>
