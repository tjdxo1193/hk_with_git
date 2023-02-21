<template>
  <ModalBase :title="title" :show="show" width="60vw" @close="close">
    <AUIGridSearch
      v-bind="sampleGrid"
      @grid-created="(proxy) => $setState('sampleGrid.$grid', proxy)"
    />
    <template #footer>
      <ButtonBase @click="onSelect">{{ selectText }}</ButtonBase>
      <ButtonBase @click="close" type="normal">{{ cancelText }}</ButtonBase>
    </template>
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util/index.js';

import values from './values/sampleSearchModal.js';

export default {
  name: 'SampleSearchModal',
  emits: ['close', 'onModalDataReturn'],
  props: {
    title: {
      type: String,
      default: '검체조회',
    },
    show: {
      type: Boolean,
      default: false,
    },
    width: {
      type: String,
      default: '800px',
    },
    height: {
      type: String,
      default: '600px',
    },
    selectText: {
      type: String,
      default: '선택',
    },
    cancelText: {
      type: String,
      default: '취소',
    },
  },
  watch: {
    show() {
      if (this.$props.show) {
        this.sampleGrid.forms = values.sampleGrid.forms();
        this.fetchSampleGrid();
      }
    },
  },
  mounted() {
    this.fetchSampleGrid();
  },
  data() {
    const { sampleGrid } = values;
    return {
      sampleGrid: {
        ...sampleGrid.static,
        forms: sampleGrid.forms(),
        columns: sampleGrid.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.$emit('onModalDataReturn', e.item);
            this.close();
            this.init();
          },
        },
      },
    };
  },
  methods: {
    init() {
      const { $grid } = this.sampleGrid;
      this.sampleGrid.forms = values.sampleGrid.forms();
      $grid.clearGridData();
    },
    async fetchSampleGrid() {
      const { $grid, forms } = this.sampleGrid;
      const param = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/tp/sampleUsage/sample', param))
        .then(({ data }) => data)
        .catch(() => {});
      $grid.setGridData(data);
    },
    close() {
      this.sampleGrid.forms = values.sampleGrid.forms();
      this.$emit('close');
    },
    onSelect() {
      const { $grid } = this.sampleGrid;
      const data = $grid.getSelectedRows();
      const param = { ...data[0] };
      this.$emit('onModalDataReturn', param);
      this.close();
    },
    onClickSampleGridButtons({ name }) {
      if (name === 'search') {
        this.fetchSampleGrid();
      }
    },
  },
};
</script>

<style></style>
