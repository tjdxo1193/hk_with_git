<template>
  <ModalBase :title="title" :show="show" width="1300px" height="560px" @close="close">
    <Horizontal align-items="center" :spans="[10, 0.1, 10]">
      <AUIGridWithHeader
        v-bind="srmOrderList"
        @button-click="onClickButton"
        @grid-created="(proxy) => $setState('srmOrderList.$grid', proxy)"
      />
      <div></div>

      <AUIGridWithHeader
        v-bind="mesOrderList"
        @button-click="onClickButton"
        @grid-created="(proxy) => $setState('mesOrderList.$grid', proxy)"
      />
    </Horizontal>
  </ModalBase>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/finalOrderModal';

export default {
  name: 'FinalOrderModal',
  emits: ['close', 'modalReturnDataEvent'],
  props: {
    title: {
      type: String,
      default: '마감오더조회',
    },
    show: {
      type: Boolean,
      default: false,
    },
    parameter: {},
  },
  mounted() {},
  data() {
    const { srmOrderList, mesOrderList } = this.$copy(values);
    return {
      srmOrderList: {
        ...srmOrderList.static,
        forms: srmOrderList.forms(),
        columns: srmOrderList.columns(),
        buttons: srmOrderList.static.buttons,
        event: {},
      },
      mesOrderList: {
        ...mesOrderList.static,
        forms: mesOrderList.forms(),
        columns: mesOrderList.columns(),
        buttons: mesOrderList.static.buttons,
        event: {},
      },
    };
  },
  watch: {
    show() {
      if (this.$props.show) {
        const item = this.$props.parameter;
        FormUtil.setData(this.srmOrderList.forms, item);
        FormUtil.setData(this.mesOrderList.forms, item);
        this.getSrmFinalOrderList();
        this.getMesFinalOrderList();
      }
    },
  },
  methods: {
    getSrmFinalOrderList() {
      const { $grid, forms } = this.srmOrderList;
      const parameter = FormUtil.getData(forms);
      const data = $grid
        ._useLoader(() => this.$axios.get('/ts/testIFModal/getSrmFinalOrderList', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    getMesFinalOrderList() {
      const { $grid, forms } = this.mesOrderList;
      const parameter = FormUtil.getData(forms);
      const data = $grid
        ._useLoader(() => this.$axios.get('/ts/testIFModal/getMesFinalOrderList', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    OrderListEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getSrmFinalOrderList();
      }
    },
    onClickButton({ name }) {
      if (name === 'selectSRM') {
        return this.getSrmFinalOrderList();
      }
      if (name === 'selectMES') {
        return this.getMesFinalOrderList();
      }
    },
    init() {
      this.srmOrderList.$grid.clearGridData();
      this.mesOrderList.$grid.clearGridData();
    },
    close() {
      this.init();
      this.$emit('close');
    },
  },
};
</script>

<style></style>
