<template>
  <ModalBase :title="title" :show="show" width="700px" @close="close">
    <AUIGridSearch
      v-bind="testSearchGrid"
      @button-click="onClickTestSearchGridButtons"
      @grid-created="(proxy) => $setState('testSearchGrid.$grid', proxy)"
    />
    <template #footer>
      <ButtonBase @click="onSelect">{{ selectText }}</ButtonBase>
      <ButtonBase @click="close" type="normal">{{ cancelText }}</ButtonBase>
    </template>
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/testSearchModal.js';

export default {
  name: 'TestSearchModal',
  emits: ['close', 'onModalDataReturn'],
  props: {
    title: {
      type: String,
      default: '의뢰조회',
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
        this.fetchTestSearchGrid();
      }
    },
  },
  mounted() {
    this.fetchTestSearchGrid();
  },
  data() {
    const { testSearchGrid, modalButtons } = this.$copy(values);
    return {
      testSearchGrid: {
        ...testSearchGrid.static,
        buttons: testSearchGrid.static.buttons,
        forms: testSearchGrid.forms(),
        columns: testSearchGrid.columns(),
      },
      modalButtons: {
        ...modalButtons.buttons,
      },
    };
  },
  methods: {
    async fetchTestSearchGrid() {
      const param = FormUtil.getData(this.testSearchGrid.forms);
      const { $grid } = this.testSearchGrid;
      const data = await $grid
        ._useLoader(() => this.$axios.get('/tp/sampleManage/test', param))
        .then(({ data }) => data)
        .catch(() => {});
      $grid.setGridData(data);
    },
    close() {
      this.testSearchGrid.forms = values.testSearchGrid.forms();
      this.$emit('close');
    },
    onSelect() {
      const { $grid } = this.testSearchGrid;
      const data = $grid.getSelectedRows();
      const param = { ...data[0] };
      this.$emit('onModalDataReturn', param);
      this.close();
    },
    onClickTestSearchGridButtons({ name }) {
      if (name === 'search') {
        this.fetchTestSearchGrid();
      }
    },
  },
};
</script>

<style></style>
